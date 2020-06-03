import sidlPyArrays
if sidlPyArrays.type == "numpy":
    from numpy import *
else:
    from Numeric import *
from Tkinter import *

class arena( Canvas ):
    def __init__( self, parent,*args, **kwargs ):
        Canvas.__init__(self, parent, *args, **kwargs)
        
        self.pack( expand=YES, fill=BOTH )
        self.allow_flip = 1
        self.flip_to_alive = 1
        self.bind('<ButtonPress-1>',self.flip_one)
        self.bind('<B1-Motion>',self.flip_more)
        self.bind('<ButtonRelease-1>',self.done_flipping)

        self.scale = kwargs.get('scale',20)
        # x is colums, width=n, j
        # y is rows, height=m, i
        self.x = 0
        self.y = 0
        self.dx= kwargs.get('dx',self.scale)
        self.dy= kwargs.get('dy',self.scale)
        self.h=0
        self.w=0
        return

    def create(self, height, width):
        if hasattr(self,'cell'):
            for i in self.cell:
                for j in i:
                    j.delete()
            delattr(self,'cell')
        self.cell=[]
        self.h=height
        self.w=width
        for i in range(self.h):
            tmp=[]
            for j in range(self.w):
                tmp.append( cell(self, i, j, scale=self.scale))
            self.cell.append(tmp)
        return

    def flip_one(self,event):

        if not self.allow_flip:
            return
        (i,j) = self.which_cell(event.x,event.y)
        if i==-1 or j==-1:
            return
        self.flip_to_alive = not self.cell[i][j].alive
        self.cell[i][j].alive = self.flip_to_alive
        self.cell[i][j]._render()
        return

    def flip_more(self, event):
        if not self.allow_flip:
            return        
        (i,j) = self.which_cell(event.x,event.y)
        if i==-1 or j==-1:
            return
        if self.flip_to_alive == self.cell[i][j].alive:
            return
        self.cell[i][j].alive=self.flip_to_alive
        self.cell[i][j]._render()
        return

    def done_flipping(self,event):
        pass
    
    def which_cell(self,x,y):
        (i,j)=(-1,-1)
        if self.h != 0 and self.w != 0:
            j = (x-self.x)/self.dx
            i = (y-self.y)/self.dy
        if i<0 or i>=self.h or j<0 or j>=self.w:
            return(-1,-1)
        else:
            return(i,j)
            
class cell:
    def __init__( self, parent, i=0, j=0, **kwargs):
        self.parent = parent
        self.i = i
        self.j = j
        self.tag = '%dx%d' %(i,j)
        scale=kwargs.get('scale',30)
        self.x = kwargs.get('x',j*scale)
        self.y = kwargs.get('y',i*scale)
        self.dx= kwargs.get('dx',scale)
        self.dy= kwargs.get('dy',scale)
        self.fgcolor = kwargs.get('fgcolor','black')
        self.bgcolor = kwargs.get('bgcolor','white')
        self.alivecolor = kwargs.get('alivecolor','blue')
        self.alive=0
        self.guiID = self.parent.create_rectangle(self.x,self.y,
                                                  self.x+self.dx,
                                                  self.y+self.dy,
                                                  fill=self.bgcolor,
                                                  outline=self.fgcolor)
        self._render()

    def delete(self):
        self.parent.delete(self.guiID)

    def _render(self):
        if self.alive:
            self.parent.itemconfigure(self.guiID,fill=self.alivecolor)
        else:
            self.parent.itemconfigure(self.guiID,fill=self.bgcolor)
        return
