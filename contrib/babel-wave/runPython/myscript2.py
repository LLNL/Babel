import babelenv
import sys
import time
import sidlPyArrays
import plot
import math
if sidlPyArrays.type == "numpy":
    from numpy import *
    double_t=double
else:
    from Numeric import *
    double_t='d'

from cxx.ScalarField import ScalarField
from f90.WavePropagator import WavePropagator

def setup( c, blocks):
    n = int(blocks)
    blockL=1
    blockR=2*n /5
    blockT=n/3
    blockB=2*n/3
    c[blockT:blockB,blockL:blockR]=0.5

    channelL=4*n/5
    channelR=n
    channelH1=3*n/8
    channelH2=5*n/8
    c[blockT:channelH1,channelL:channelR]=0.0
    c[channelH2:blockB,channelL:channelR]=0.0

    p = ones((n,n),double_t)
    cr =n/2.0
    cc=7.0*n/8.0
    s2=64*9.0/((n/2.0)**2)
    for i in range(n):
        for j in range(n):
            p[i,j]=math.exp(-((i-cr)**2 + (j-cc)**2) * s2)
            
    return (p,c)

height=200.0
width=200.0
stepsize=10

d = ScalarField()
d.init(0,0,height,width,1.0,0.2)
c=d.getData()
p,c = setup(c,height)
d.setData(c)

wp = WavePropagator()
wp.init( d, p )
f = plot.FieldView(c,'wave speed')

p2 = wp.getPressure()

for i in range(40):
    wp.step(stepsize)
    p2 = wp.getPressure()
    f = plot.FieldView(p2,'step %d' %(i*stepsize,), color_range=(-1,2))
