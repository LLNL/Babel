import babelenv
import sidlPyArrays
if sidlPyArrays.type == "numpy":
    from numpy import * 
else:
    from Numeric import * 
from Tkinter import *
from tkMessageBox import *
from tkSimpleDialog import *
from tkFileDialog import *
import Pmw
import AppShell
import arena
from cxx.Environment import Environment
from c.Ruleset import Ruleset
from cxx.TimeStepper import TimeStepper

class Life( AppShell.AppShell ):
    appname = 'Babel-Life: Conway\'s Life done with multiple languages'
    appversion = '2.0'
    copyright = """
    Copyright 2003 & 2004 Lawrence Livermore National Laboratory.
    All rights reserved."""
    contactname = 'Gary Kumfert'
    contactphone = '(925) 424-2580'
    contactemail='kumfert@llnl.gov'
    gameinfo='No game to have info on'

    usecommandarea=1
    frameWidth=800
    frameHeight=600
    padx=5
    pady=5

    def createMenuBar(self):
        """File and Help menus come by default"""
        self.menuBar.addmenuitem('File','command','Create anew',
                                 label='New', command=self.start_over)
        self.menuBar.addmenuitem('File','command','Load a new game',
                                 label='Open', command=self.load_file)
        self.menuBar.addmenuitem('File','separator')
        self.menuBar.addmenuitem('File','command',
                                 'Quit the entire application',
                                 label='Quit', command=self.quit)

        self.menuBar.addmenu('Info','Meta information about known games')
        self.menuBar.addmenuitem('Info','command','Get information',
                                 label='Game info', command=self.display_info)
        
        self.toggleBalloonVar=IntVar()
        self.toggleBalloonVar.set(1)
        self.menuBar.addmenuitem('Help','checkbutton','Toggle Tooltips',
                                 label='Tooltips',
                                 variable=self.toggleBalloonVar,
                                 command=self.toggleBalloon)
        self.menuBar.addmenuitem('Help','separator')
        self.menuBar.addmenuitem('Help','command',
                                 'Get information about this GUI',
                                 label='About...', command=self.showAbout)

        return

    def createButtons(self):
        self.buttonAdd('New...',
                       helpMessage='Create a new grid',
                       statusMessage='Create a new grid with new size',
                       command=self.start_over)
        
        self.runBtn=self.buttonAdd('Run ',
                       helpMessage='Toggle the simulation',
                       statusMessage='Start/Stop the simulation',
                       command=self.contin)

        self.stepBtn=self.buttonAdd('Step',
                       helpMessage='Run only one step of the simulation',
                       statusMessage='Take exactly one timestep',
                       command=self.step)

        if 'setRules' in dir(Ruleset()):
            self.buttonAdd('Rules',
                           helpMessage='Default game is Survival=23,Birth=3',
                           statusMessage='Change Rules for Survival/Birth',
                           command=self.change_rules)
            
        self.buttonAdd('Libs',
                       helpMessage='What objects were loaded',
                       statusMessage='Display exactly what objects wer loaded',
                       command=self.libs)

        self.buttonAdd('Quit',
                       helpMessage='Quit Babel-Life',
                       statusMessage='Quit the Babel-Life program',
                       command=self.quit)
        return

    def createInterface(self):
        AppShell.AppShell.createInterface(self)
        self.createMain()
        self.createButtons()
        return

    def createMain(self):
        self.arena = self.createcomponent('arena',(),None,
                                          arena.arena, (self.interior(),))
        self.ts = TimeStepper()
        self.rules = Ruleset()
        self.progressBar().labelFormat="Pop=%d"
        self.progressBar().labelColor='black'
        return

    def contin(self):
        """Run simulation without stopping"""
        if not hasattr(self.arena,'cell'):
            return
        self.arena.allow_flip = not self.arena.allow_flip
        if self.arena.allow_flip: 
            self.runBtn.configure(text="Run ")
        else:
            self.runBtn.configure(text="Stop")
        while not self.arena.allow_flip:
            self._running_step()
            self.update()
        return

    def _running_step(self):
        """a single step within a run"""
        self.gui2env()
        t1 = self.env.getGrid()
        pop=self.ts.step()
        self.env2gui()
        t2 = self.env.getGrid()
        if alltrue(alltrue(t1==t2)):
            self.runBtn.configure(text="Run ")
            self.arena.allow_flip = 1
            showwarning('Stable State','This simulation has achieved a stable state of %d cells at iteration %d.'%(pop,self.ts.nStepsTaken()-1))
        return

    def libs(self):
        showinfo('Loaded Libraries',
                 '''You are using
                 %s
                 %s
                 %s
                 ''' % (Environment().getClassInfo().getName(),
                        Ruleset().getClassInfo().getName(),
                        TimeStepper().getClassInfo().getName()))

    def change_rules(self):
        [cur_survivals, cur_births] = self.rules.getRules()
        survivals = askinteger('New Survivals',
                               'Please enter the digits for survival (1-8)',
                               initialvalue=int(cur_survivals))
        if not survivals:
            return
        births = askinteger('New Births',
                            'Please enter the number of neighbors for birth(1-8)',
                            initialvalue=int(cur_births))
        if not births:
            return
        success = self.rules.setRules(str(survivals),str(births))
        if not success:
            showerror(title="Rules Unchanged",
                      message="Rule change was not successful, check syntax and try again!")
        return
    
    def display_info(self):
        showinfo('Game Info',self.gameinfo)
        return
    
    def step(self):
        """Run one step of the simulation"""
        if not hasattr(self.arena,'cell'):
            return
        if not self.arena.allow_flip:
            return
        self.gui2env()
        t1 = self.env.getGrid()
        pop=self.ts.step()
        self.env2gui()
        t2 = self.env.getGrid()
        if alltrue(alltrue(t1==t2)):
            showwarning('Stable State','This simulation has achieved a stable state of %d cells at iteration %d.'%(pop,self.ts.nStepsTaken()-1))
        return

    def gui2env(self):
        """copies gui state to env"""
        a=self.env.getGrid()
        for i in range(self.arena.h):
            for j in range(self.arena.w):
                a[i][j]=self.arena.cell[i][j].alive
        self.env.setGrid(a)
        return

    def env2gui(self):
        """copies env state to gui"""
        a=self.env.getGrid()
        for i in range(self.arena.h):
            for j in range(self.arena.w):
                self.arena.cell[i][j].alive=a[i][j]
                self.arena.cell[i][j]._render()
        self.updateProgress(sum(sum(a)))
        return

    def start_over(self):
        """reinitialize the grid"""
        if  self.arena.allow_flip==0:
            self.arena.allow_flip=1
            self.update()
            self.runBtn.configure(text="Run ")
        h = askinteger('New Height',
                       'Please enter the height of the new grid',
                       minvalue=1)
        if not h:
            return
        w = askinteger('New Width',
                       'Please enter the width of the new grid',
                       minvalue=1)
        if not w:
            return
        self.height = h
        self.width=w
        self.root.title('Babel-Life %d x %d grid' %(self.height,self.width))
        self.gameinfo='User defined %d x %d grid' %(self.height,self.width)
        self.arena.create(self.height, self.width)
        self.updateProgress(0,self.width*self.height)
        self.env=Environment()
        self.env.init(self.height, self.width)
        self.ts=TimeStepper()
        self.ts.init(self.env,self.rules)
        self.gui2env()
        return

    def parse_file(self, filename):
        p = open(filename)
        name=''
        info=[]
        data=[]
        for line in p.readlines():
            l = line.strip()
            if not l: continue
            if l[0]=='#':
                if not name:
                    name =l[1:].strip()
                else:
                    info.append(l[1:].strip())
            else:
                data.append(l)
        p.close()
        nrows=len(data)
        ncols=max([len(i) for i in data])
        if min([len(i) for i in data]) != ncols:
            print "error, data doesn't seem to be rectangular"
        #pad data with extra
        nrows=nrows+2
        ncols=ncols+2
        data2=[ '.'+k+'.' for k in data]
        data2.insert(0,'.'*ncols)
        data2.append('.'*ncols)
        return (nrows, ncols, data2, name, info)
    
    def load_file(self):
        """read file and reinitialize the grid"""
        if  self.arena.allow_flip==0:
            self.arena.allow_flip=1
            self.update()
            self.runBtn.configure(text="Run ")
        # get the file
        filename = askopenfilename(defaultextension="cgl", initialdir="../lib")
        (h,w,data,name,info) = self.parse_file( filename )
        self.gameinfo=' '.join(info)
        self.height = h
        self.width = w
        self.root.title('Babel-Life:  %s ' %(name))
        self.arena.create(self.height, self.width)
        self.updateProgress(0,self.width*self.height)
        self.env=Environment()
        self.env.init(self.height, self.width)
        a=self.env.getGrid()
        for i in range(self.arena.h):
            for j in range(self.arena.w):
                a[i][j]=(data[i][j]=='O') or (data[i][j]=='*')
        self.env.setGrid(a)
        self.env2gui()
        self.ts=TimeStepper()
        self.ts.init(self.env,self.rules)
        self.gui2env()
        return

    def quit(self):
        """quit the entire application"""
        if askyesno('Quit Babel-Life Confirmation',
                       'Are you sure you want to quit Babel-Life now?'):
            self.arena.allow_flip=1
            self.update()
            sys.exit(0)
        return
    
    def unimplemented(self):
        showerror('Not implemented','Sorry, not yet available')
        return

if __name__ == '__main__':
    l = Life()
    l.run()
