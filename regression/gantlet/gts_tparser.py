#
# Copyright(c) 2001-2003, Lawrence Livermore National Security, LLC.
# Produced at the Lawrence Livermore National Laboratory.
# Written by Gary Kumfert <kumfert@llnl.gov>.
# UCRL-CODE-2002-043.
# All rights reserved.
#
# This file is part of Gantlet.  For details, see 
# http://www.llnl.gov/CASC/components/software.html or contact the author.
#
# Gantlet is free software; you can redistribute it and/or modify it
# under the terms of the GNU Lesser General Public License (as published by 
# the Free Software Foundation) version 2.1 dated February 1999.
#
# Gantlet is distributed in the hope that it will be useful, but
# WITHOUT ANY WARRANTY; without even IMPLIED WARRANTY OF MERCHANTABILITY
# or FITNESS FOR A PARTICULAR PURPOSE.  See the terms and conditions of
# the GNU Lesser General Public License for more details.
#
# You should have received a copy of the GNU Lesser General Public License
# along with this software; if not, write to the Free Software Foundation, 
# Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
#
# ADDITIONAL NOTICE:
# 
# A. This notice is required to be provided under our contract with the
#    U.S. Department of Energy (DOE).  This work was produced at the 
#    University of California, Lawrence Livermore National Laboratory
#    under Contract No. W-7405-ENG-48 with the DOE.
#
# B. Neither the United States Government nor the University of California
#    nor any of their employees make any warranty, express or implied, or
#    assumes any liability or responsibility for the accuracy, completeness, 
#    or usefulness of any information, apparatus, product, or process 
#    disclosed, or represents that its use would not infringe on 
#    privately-owned rights.
# 
# C. Also, reference herein to any specific commercial products, process, or
#    services by trade name, trademark, manufacturer or otherwise does not
#    necessarily constitute or imply its endoresement, recommendation, or
#    favoring by the United States Government or the University of California.
#    The views and opinions of authors expressed herein do not necessarily 
#    state or reflect those of the United States Government or the University
#    of California, and shall not be used for advertising or product 
#    endorsement purposes.
import re
import string
import sys

import gantlet.events
from gantlet.tparser_base import tparser_base
import gantlet.gts_result
from gantlet.exit_status_result import exit_status_result

_old_compound_begin = re.compile(r'^\s?TEST_BEGIN\s+(?P<testname>\D\S*)')
_old_test_begin = re.compile(r'^\s?NPARTS\s+(?P<nparts>[0-9\-]+)')
_test_begin = re.compile(r'^\s?(TEST_BEGIN|NPARTS)\s+(?P<nparts>[0-9\-]+)\s*(?P<testname>\S*)')
_test_end = re.compile(r'^\s?TEST_RESULT\s+(?P<result>PASS|FAIL|XFAIL|BROKEN)\s*(?P<exitstatus>\d*)')
_part_begin = re.compile(r'^\s?PART\s+(?P<partno>\d+)')
_part_end = re.compile(r'^\s?RESULT\s+(?P<partno>\d+)\s+(?P<result>PASS|XFAIL|UNTESTED|UNSUPPORTED|FAIL|XPASS|UNRESOLVED)')
_comment = re.compile(r'^\s?COMMENT\:\s+(?P<text>.*)')

class gts_tparser( tparser_base ):
    """
    This is the base class for all Gantlet Testing Syntax (GTS) tests
    """
   
    def __init__( self, invocation, scoreboard ):
        tparser_base.__init__(self, invocation, scoreboard )
        self.results = []   # list of tests
        self.buffer = []  # array of text strings
        self.comment_buffer = [] # array of text strings

    def get_type(self):
        return "gts_tparser"
        
    def collect_results(self, filehandle):
        # state = 'old compound begin' ||
        #         'before tests'  ||
        #         'between tests' ||
        #         'before parts'  ||
        #         'between parts'
        #         'in part'
        state = 'before tests'
        nparts = 0
        partno = 0
        resultno = 0
        compound_name = None
        lineno = 0
        try:
            while filehandle != None:
                loc = filehandle.tell()
                line = filehandle.readline()
                lineno = lineno + 1
                if line=='':
                    break
                # iterate over regex engines looking for a match
                old_test_begin = _old_test_begin.search(line)
                old_compound_begin = _old_compound_begin.search(line)
                test_begin = _test_begin.search(line)
                test_end = _test_end.search(line)
                part_begin = _part_begin.search(line)
                part_end = _part_end.search(line)
                if old_compound_begin and (not test_begin):
                    compound_name = old_compound_begin.group('testname')
                    self.PVT_flush_buffers()
                    state = 'old compound begin'
                elif old_test_begin:
                    if state not in ['old compound begin', 'before tests', 'between tests']:
                        print("Error: old-style test begin found in state=\"%s\"" %(state,))
                        print("   line %d=\"%s\"" %(lineno,line))
                    self.PVT_flush_buffers()
                    if state == 'old compound begin':
                        old_test_begin = _test_begin.search( "TEST_BEGIN %s %s" %
                                                             (old_test_begin.group('nparts'),
                                                              compound_name))
                        compound_name=None
                        state="between tests"
                    else:
                        state="before tests"
                    # now create new test
                    self.results.append( gantlet.gts_result.gts_result( self.invocation.symbolic_name,
                                                       self.scoreboard, self.get_type() ))
                    if len( self.results ) > 1:
                        self.scoreboard.event['test_added'].publish(gantlet.events.test_added_event, 1)
                    self.results[-1].exit_status = self.exit_status
                    state = self.results[-1].test_begin( state, old_test_begin )
                    if self.scoreboard.config.warn_deprecated :
                        self.results[-1].warning( "Old-style test beginning (deprecated)", "")
                elif test_begin:
                    if state not in ['before tests', 'between tests']:
                        print("Error: test begin found in state=\"%s\"" %(state,))
                        print("   line %d=\"%s\"" %(lineno,line))
                    self.PVT_flush_buffers()
                    self.results.append( gantlet.gts_result.gts_result( self.invocation.symbolic_name,
                                                       self.scoreboard, self.get_type() ))
                    if len( self.results ) > 1:
                        self.scoreboard.event['test_added'].publish(gantlet.events.test_added_event, 1)
                    self.results[-1].exit_status = self.exit_status
                    state = self.results[-1].test_begin( state, test_begin )
                elif part_begin and state != 'broken' :
                    if state not in ['before parts', 'between parts']:
                        print("Error: part begin found in state=\"%s\"" %(state,))
                        print("   line %d=\"%s\"" %(lineno,line))
                    self.PVT_flush_buffers()
                    if len(self.results)==0:
                        print("here")
                    state = self.results[-1].part_begin( state, part_begin )
                elif part_end and state != 'broken' :
                    if state not in ['in part']:
                        print("Error: part end found in state=\"%s\"" %(state,))
                        print("   line %d=\"%s\"" %(lineno,line))
                    self.PVT_flush_buffers()
                    state = self.results[-1].part_end( state, part_end )
                elif test_end and state != 'broken' :
                    #if (state not in ['between parts']) and not self.results[-1].exit_status :
                    #    print("Error: test end found in state=\"%s\"" %(state,))
                    #    print("   line %d=\"%s\"" %(lineno,line)                )
                    if state in ['old compound begin']:
                        old_test_begin = _test_begin.search( "TEST_BEGIN -1 %s" %
                                                             (compound_name,))
                        compound_name=None
                        self.results.append( gantlet.gts_result.gts_result( self.invocation.symbolic_name,
                                                         self.scoreboard, self.get_type() ))
                        if len( self.results ) > 1:
                            state="between tests"
                        else:
                            state="before tests"
                        self.scoreboard.event['test_added'].publish(gantlet.events.test_added_event, 1)
                        self.results[-1].exit_status = self.exit_status
                        state = self.results[-1].test_begin( state, old_test_begin )
                        
                    self.PVT_flush_buffers()
                    if len( self.results ) == 0:
                        self.results.append( gantlet.gts_result.gts_result( self.invocation.symbolic_name, \
                                                         self.scoreboard, self.get_type() ))
                        self.results[-1].exit_status = self.exit_status
                        self.results[-1].broken("Missing Test Begin","syntax error")
                    state = self.results[-1].test_end( state, test_end )
                    compound_name = None
                else:  # some other kind of text
                    comment = _comment.search(line)
                    if comment:
                        if len( self.buffer ):
                            self.PVT_flush_buffers()
                        text_state = 'comment'
                        self.comment_buffer.append(comment.group('text'))
                    else:
                        if len( self.comment_buffer ):
                            self.PVT_flush_buffers()
                        text_state = ''
                        self.buffer.append(line)
        except Exception:
            print("Unexpected error:", sys.exc_info()[0])
#            print("                :", details)

        if state == 'before tests' and len(self.results) == 0 and self.scoreboard.config.allow_exit_status_only :
            self.results.append( exit_status_result(  self.invocation.symbolic_name,
                                                  self.scoreboard, "exit_status", self.exit_status) )
        elif state != 'between tests' and state != 'broken':
            if len(self.results) == 0:
                self.results.append(gantlet.gts_result.gts_result( self.invocation.symbolic_name,
                                                self.scoreboard, self.get_type() ))
            self.results[-1].exit_status = self.exit_status
            self.results[-1].try_recover_premature_end( state )
        elif state == 'broken':
            self.results[-1].try_recover_premature_end( state )

    def PVT_flush_buffers( self ):
        if len( self.buffer ):
            s = "\n".join(self.buffer).strip()
            if len(s):
                self.scoreboard.event['text'].publish(gantlet.events.text_event, s)
            self.buffer = []
        elif len( self.comment_buffer ):
            s = "\n".join(self.comment_buffer)
            if len(s):
                self.scoreboard.event['comment'].publish(gantlet.events.comment_event, s)
            self.comment_buffer = []


def _test():
   # t = test('autoexec.bat',r'C:\autoexec.bat',r'C:\autoexec.bat')
   # print(t.__dict__)
   # 
   # t = test('winhelp.exe',r'C:\WINNT\winhelp.exe',r'C:\WINNT\winhelp.exe')
   # print( t.__dict__)
   # 
   # t2 = test("`test 2`",'test2', 'test2')
   # print(t2.__dict__)
   # 
   # t2 = test('foo','test',"`PETF_NAME=foo test 2`")
   # print(t2.__dict__)
   pass


if __name__ == '__main__':
    _test()
