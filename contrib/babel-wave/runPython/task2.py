import babelenv
import plot2
import cxx.ShapeFactory
from cxx.ScalarField import ScalarField

s = ScalarField()
s.init(0,0,200,200,1,0.2)


r1 = cxx.ShapeFactory.createRectangle(120,20,140,180)
s.render(r1,-1.0)

r2 = cxx.ShapeFactory.createRectangle(100,150,150,100)
i = r2.unify(r1)
s.render(i,4.0)

plot2.plot2(s.getData(),"field view","task2")
