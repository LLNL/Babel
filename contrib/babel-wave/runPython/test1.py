import babelenv
import plot
import cxx.ShapeFactory
from cxx.ScalarField import ScalarField

s = ScalarField()
s.init(0,0,200,200,1,0.2)


r = cxx.ShapeFactory.createRectangle(20,20,40,80)
s.render(r,2.0)

r = cxx.ShapeFactory.createRectangle(100,150,150,100)
s.render(r,1.0)

# plot.FieldView(s.getData(),"field view")
f = open("test.dat","w")

z=s.getData();
print z[3,2]
for i in range(200):
    for j in range (200):
        f.write("%g " % (z[i][j],))
    f.write("\n")
f.close()
