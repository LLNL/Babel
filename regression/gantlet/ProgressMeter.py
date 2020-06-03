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
from Tkinter import *

class ProgressMeter(Frame):
    def __init__( self, parent=None, w=500, h=20, max=100, bgcolor='grey', fgcolor='green'):
        self.bd = 2
        self.width = w
        self.height = h
        self.max = max
        self.color = fgcolor
        Frame.__init__( self, parent )
        self.pack()
        self.canvas = Canvas( self, relief=GROOVE, bg=bgcolor,bd=self.bd )
        self.canvas.pack()
        self.canvas.config(width=self.width-self.bd, height=self.height-self.bd)
        self.completed = 0
        self.meter = self.canvas.create_rectangle(0,0,0, self.height,fill=self.color)
        self.text = self.canvas.create_text(self.width/2, 2*self.height/3, text='0 %',
                                            font=('courier',12,'bold'))


    def updateCompleted( self, completed ):
        #prevent overflow
        completed = max(0, completed)
        completed = min( self.max, completed)
        #calculate new rectangle
        self.completed = completed
        percent_done = float(self.completed) / self.max
        x = int(percent_done * self.width)
        new_meter = self.canvas.create_rectangle(0,0,x,self.height,fill=self.color)
        self.canvas.delete(self.meter)        
        self.meter = new_meter
        newtext = "%d %%" % int( percent_done * 100 )
        self.canvas.itemconfigure( self.text, text=newtext)
        self.canvas.tkraise(self.text)

    def add( self, toadd ):
        self.updateCompleted(self.completed + toadd )

    def subtract( self, tosubtract ):
        self.updateCompleted(self.completed + tosubtract )

    def newMaximum( self, max ):
        self.max = max
        self.updateCompleted( self.completed )

    def tenMoreCompleted( self ):
        self.updateCompleted( self.completed + 10 )

    def tenLessCompleted( self ):
        self.updateCompleted( self.completed - 10 )

    def setGraphically( self, evt ):
        x = evt.x
        percent_done = float(x) / self.width
        completed = int( percent_done * self.max )
        self.updateCompleted( completed )
        
       
if __name__ == '__main__':
    root = Tk()
    pg = ProgressMeter(root)
    Button(root, text='+10', command=pg.tenMoreCompleted ).pack()
    Button(root, text='-10', command=pg.tenLessCompleted ).pack()  
    pg.canvas.bind('<ButtonPress-1>', pg.setGraphically )    
    root.mainloop()
      

