import babelenv
import sys
import time
import sidlPyArrays
if sidlPyArrays.type == "numpy":
    from numpy import *
    double_t=double
else:
    from Numeric import *
    double_t='d'

from cxx.WavePropagator import WavePropagator
from f90.ScalarField import ScalarField

d = ScalarField()
d.init(0.0, 0.0, 1.0, 1.0, 0.5, 0.2)

p = ones((2,2),double_t)

wp = WavePropagator()
wp.init( d, p )

p2 = wp.getPressure()

print p2
for i in range(10):
    wp.step(1)
    p2 = wp.getPressure()
    print p2
