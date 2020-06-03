import conway.Environment
e = conway.Environment.Environment()
e.init(3,5)


try:
    e.isAlive(2,2)
except conway.BoundsException.Exception, e:
    dir(e)
    print e.getNote()
