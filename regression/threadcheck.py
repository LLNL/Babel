#!/usr/bin/env python
import exceptions
from re import compile
from string import atoi

controlling = compile(r'babel:\s+THREAD_CONTROLLING_GIL\s+(\S+)\s+(\S+)\s+(\d+)\s+thread\s+(\d+)')
ensure_lock = compile(r'babel:\s+ACQUIRE_THREAD_LOCK\s+(\S+)\s+(\S+)\s+(\d+)\s+(\d+)\s+thread\s+(\d+)')
release_lock = compile(r'babel:\s+RELEASE_THREAD_LOCK\s+(\S+)\s+(\S+)\s+(\d+)\s+(\d+)\s+thread\s+(\d+)')
begin_allow_threads=compile(r'babel:\s+BEGIN_ALLOW_THREADS\s+(\S+)\s+(\S+)\s+(\d+)\s+thread\s+(\d+)')
end_allow_threads=compile(r'babel:\s+END_ALLOW_THREADS\s+(\S+)\s+(\S+)\s+(\d+)\s+thread\s+(\d+)')

def fixState(state):
  if (state == 0):
    return 1
  else:
    return 0

def addLine(record, line):
  m = ensure_lock.match(line)
  if m:
    record.append({ 'op' : 'E', 'function' : m.group(1), 'file' : m.group(2),
                    'line' : atoi(m.group(3)),
                    'state' : fixState(atoi(m.group(4))),
                    'thread' : atoi(m.group(5)) } )
  else:
    m = release_lock.match(line)
    if m:
      record.append({ 'op' : 'R', 'function' : m.group(1), 'file' : m.group(2),
                      'line' : atoi(m.group(3)),
                      'state' : fixState(atoi(m.group(4))),
                      'thread' : atoi(m.group(5)) } )
    else:
      m = begin_allow_threads.match(line)
      if m:
        record.append({ 'op' : 'O', 'function' : m.group(1),
                        'file' : m.group(2),
                        'line' : atoi(m.group(3)),
                        'thread' : atoi(m.group(4)),
                        'state' : None } )
      else:
        m = end_allow_threads.match(line)
        if m:
          record.append({ 'op' : 'C', 'function' : m.group(1),
                          'file' : m.group(2),
                          'line' : atoi(m.group(3)),
                          'thread' : atoi(m.group(4)),
                          'state' : None} )
        else:
          m = controlling.match(line)
          if m:
            record.append({ 'op' : 'S', 'function' : m.group(1),
                            'file' : m.group(2),
                            'line' : atoi(m.group(3)),
                            'thread' : atoi(m.group(4)),
                            'state' : None} )

class AnalysisError(exceptions.Exception):
  def __init__(self, msg=None):
    self.args = msg

class GILState:
  def __init__(self):
    self.controls_GIL = -1 # start in unknown state
    self.op_stack = []
    self.state_stack = []

  def changeState(self, op, state=None):
    if op == 'E':
      self.op_stack.append(op)
      self.state_stack.append(state)
      oldstate = self.controls_GIL
      self.controls_GIL = 1
      if (oldstate >= 0) and (state != oldstate):
        raise AnalysisError, "Simulation state doesn't match reported state"
    elif op == 'R':
      top_op = self.op_stack.pop()
      top_state = self.state_stack.pop()
      oldstate = self.controls_GIL
      if len(self.op_stack) == 0:
        self.controls_GIL = -1
      else:
        self.controls_GIL = top_state
      if not oldstate:
        raise AnalysisError, "Release when GIL not controlled"
      if top_op != 'E':
        raise AnalysisError, "Release balanced with " + op + " rather than ensure"
    elif op == 'O':
      oldstate = self.controls_GIL
      self.controls_GIL = 0
      self.op_stack.append(op)
      if oldstate != 1:
        raise AnalysisError, "Begin allow threads when GIL not controlled"
    elif op == 'C':
      top_op = self.op_stack.pop()
      oldstate = self.controls_GIL
      self.controls_GIL = 1
      if top_op != 'O':
        raise AnalysisError, "End allow threads balanced with " + op + " rather than begin"
      if oldstate != 0:
        raise AnalysisError, "End allow threads while GIL controlled"
    elif op == 'S':
      self.controls_GIL = 1

def threadsControllingGIL(state):
  count = 0
  for i in state.values():
    if i.controls_GIL == 1:
      count = count + 1
  return count
  
def analyzeRecord(record):
  state = { }
  count = 0
  for r in record:
    count = count + 1
    if not state.has_key(r['thread']):
      state[r['thread']] = GILState()
    try:
      state[r['thread']].changeState(r['op'], r['state'])
    except AnalysisError, msg:
      print msg, "\n", r, "\nrecord = ", count
    except IndexError, msg:
      print msg, "\n", r, "\nrecord = ", count
    if threadsControllingGIL(state) > 1:
      print "More than one thread appears to own the GIL record = ", count

if __name__ == '__main__':
  import sys
  record = []
  for line in sys.stdin.readlines():
    addLine(record, line)
  analyzeRecord(record)
