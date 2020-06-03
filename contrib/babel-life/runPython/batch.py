#! /bin/env python

import babelenv
import sys
import time

from re import compile
import sidlPyArrays
if sidlPyArrays.type == "numpy":
    from numpy import *
else:
    from Numeric import *
from cxx.Environment import Environment
from cxx.TimeStepper import TimeStepper
from c.Ruleset import Ruleset

def printGeneration(grid, population, iteration):
    icons = ( '.', '#', '#', '#', '#', '#')
    print "step %d: population=%d" % (iteration, population)
    for i in xrange(grid.shape[0]):
        for j in xrange(grid.shape[1]):
            sys.stdout.write(icons[grid[i][j]])
        sys.stdout.write("\n")

def readFile(filename):
    population = 0
    blankline = compile(r'^((#.*\n)|(\s*))$')
    rows = [ ]
    input = open(filename)
    for line in input.readlines():
        if not blankline.search(line):
            row = [ ]
            line = line.strip()
            for c in line:
                if (c == '.'):
                    row.append(0)
                else:
                    population = population + 1
                    row.append(1)
            rows.append(row)
    input.close()
    return array(rows, Int32), population

if __name__ == '__main__':
    if not sys.argv[1:]:
        sys.argv = sys.argv + [ '../lib/default.cgl' ]
    for arg in sys.argv[1:]:
        iteration = 0
        grid, population = readFile(arg)
        env = Environment()
        env.init(grid.shape[0], grid.shape[1])
        env.setGrid(grid)
        rules = Ruleset()
        ts = TimeStepper()
        ts.init(env, rules)
        printGeneration(grid, population, iteration)
        while population > 0:
            population = ts.step()
            grid = env.getGrid()
            printGeneration(grid, population, ts.nStepsTaken())
            time.sleep(1)
