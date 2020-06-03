#! /bin/env python

import babelenv
import sys
import time
import sidlPyArrays
if sidlPyArrays.type == "numpy":
    from numpy import *
else:
    from Numeric import *
from cxx.Environment import Environment
# Those of you with a supported F90 compiler
# can uncomment this line
# from f90.TimeStepper import TimeStepper
# and comment out this one.
from cxx.TimeStepper import TimeStepper
from c.Ruleset import Ruleset
import sidl.rmi.ProtocolFactory
import sidl.SIDLException
import sidl.rmi.NetworkException
import sidl.rmi.ServerRegistry
import sidl.rmi.ServerInfo
import sidlx.rmi.SimpleOrb


height=20
width=50

temp = sidl.rmi.ProtocolFactory.addProtocol("simhandle","sidlx.rmi.SimHandle")
orb = sidlx.rmi.SimpleOrb.SimpleOrb()
orb.init("simhandle://localhost:9997", 0)
tid = orb.run()
si = sidl.rmi.ServerInfo.ServerInfo(orb)
sidl.rmi.ServerRegistry.registerServer(si)

env = Environment(url = "simhandle://localhost:9999")
env.init(height,width)
g = zeros((height,width),'i')

#g[0][0]=1
#g[0][3]=1
#g[0][4]=1
#g[1][0]=1
#g[1][2]=1
#g[2][0]=1
#g[2][2]=1
#g[2][3]=1

# square is self-sustaining
#g[1][1]=1
#g[1][2]=1
#g[2][1]=1
#g[2][2]=1
#pop=4

#pinwheel rotates
#g[0][2]=1
#g[1][2]=1
#g[2][2]=1
#pop=3

# 5 groups of 5 does fun things
pop=0
for i in range(0,45,6):
    for k in range(5):
        print i+k
        g[10][i+k]=1
        pop+=1

env.setGrid(g)
print "step 0: pop=",pop
print env.getGrid()

rules = Ruleset(url = "simhandle://localhost:9999")
ts = TimeStepper(url = "simhandle://localhost:9999")
ts.init( env, rules )

#
#while pop > 0:
#    pop = ts.step()
#    print "step %d: pop=%d" %(ts.nStepsTaken(), pop)
#    print env.getGrid()
#    time.sleep(1)

while pop > 0:
    pop = ts.step()
    print "step %d: pop=%d" %(ts.nStepsTaken(), pop)
    a = env.getGrid()
    for x in range(height):
        str=''
        for y in range(width):
            if a[x][y] > 0:
                str+='#'
            else:
                str+='.'
        print str
    print
    time.sleep(1)

