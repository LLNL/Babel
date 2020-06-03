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
from gantlet.events import *
_have_gui=1
try:
    from Tkinter import * 
    from gantlet.ProgressMeter import ProgressMeter
except:
    _have_gui=0
import gantlet

class gui_display :

    def update_ratio( self ):
        if self.scoreboard.tests_outstanding < 0:
            print("Ugh!")
            self.scoreboard.tests_outstanding = 0
        newtext = "%d/%d" % ( self.scoreboard.tot_tests, self.scoreboard.tot_tests + \
                              self.scoreboard.tests_outstanding )
        self.ratio.config(text=newtext)

    def test_begin( self, test ):
        self.filename.config(text=test.name)
        self.update_ratio()
        self.root.update()

    def add_test( self, n_new_tests ):
        self.meter.newMaximum( self.meter.max + n_new_tests )
        self.update_ratio()        
        
    def finish_test( self, test ):
        self.meter.add( 1 )
        self.tests_total.config(text=self.scoreboard.tot_tests)
        self.tests_passed.config(text=self.scoreboard.tot_tests_passed)
        self.tests_xfailed.config(text=self.scoreboard.tot_tests_xfailed)
        self.tests_failed.config(text=self.scoreboard.tot_tests_failed)
        self.tests_broken.config(text=self.scoreboard.tot_tests_broken)
        self.parts_total.config(text=self.scoreboard.tot_parts)
        self.parts_passed.config(text=self.scoreboard.tot_parts_passed)
        self.parts_xfailed.config(text=self.scoreboard.tot_parts_xfailed)
        self.parts_failed.config(text=self.scoreboard.tot_parts_failed)

    def finalize( self, scoreboard ):
        self.filename.config(text="Tests Completed.")
        self.update_ratio()
        self.root.update()

    def __init__( self, scoreboard ):
        self.scoreboard = scoreboard
        if not _have_gui:
            return
        self.root = Tk()

        # row 1
        f = Frame( self.root )
        f.master.title("Gantlet %s" % gantlet.VERSION)
        f.pack(side=TOP, expand=NO, fill=X)
        l = Label( f, text="File:", width=10, justify=RIGHT)
        l.pack(side=LEFT, expand=NO, fill=X)
        self.ratio = Label(f, width=10)
        self.ratio.pack(side=RIGHT, expand=NO )
        self.filename = Label( f, relief=SUNKEN, width=60, justify=LEFT)
        self.filename.pack(side=RIGHT, expand=YES, fill=X )

        # row 2
        f = Frame( self.root )
        f.pack(side=TOP, expand=NO, fill=X)
        l = Label(f, text="Progress:", width=10, justify=RIGHT)        
        l.pack(side=LEFT, expand=NO, fill=X)
        self.meter = ProgressMeter(f, max=scoreboard.tests_outstanding, fgcolor='#aaf')
        self.meter.pack(side=RIGHT, expand=YES, fill=X)

        # row 3
        f = Frame( self.root )
        f.pack(side=TOP, expand=NO, fill=X )
        l = Label(f,text="Stats:", width=10, justify=RIGHT)
        l.pack(side=LEFT, expand=NO, fill=X )
        f = Frame( f, relief=GROOVE, bd=5)
        f.pack(side=TOP, expand=YES, fill=BOTH)
        Label( f, text="Total" , width=10).grid(row=0, column=1)
        Label( f, text="Pass"  , width=10).grid(row=0, column=2)
        Label( f, text="Xfail" , width=10).grid(row=0, column=3)
        Label( f, text="Fail"  , width=10).grid(row=0, column=4)
        Label( f, text="Broken", width=10).grid(row=0, column=5)
        Label( f, text="Tests" ).grid(row=1, column=0)
        self.tests_total   = Label( f, relief=SUNKEN, width=10, justify=RIGHT, fg="#000" )
        self.tests_passed  = Label( f, relief=SUNKEN, width=10, justify=RIGHT, fg="#0a0" )
        self.tests_xfailed = Label( f, relief=SUNKEN, width=10, justify=RIGHT, fg="#990" )
        self.tests_failed  = Label( f, relief=SUNKEN, width=10, justify=RIGHT, fg="#a00" )
        self.tests_broken  = Label( f, relief=SUNKEN, width=10, justify=RIGHT, fg="#a00" )
        self.tests_total.grid(row=1,column=1)
        self.tests_passed.grid(row=1,column=2)
        self.tests_xfailed.grid(row=1,column=3)
        self.tests_failed.grid(row=1,column=4)
        self.tests_broken.grid(row=1,column=5)
        Label( f, text="Parts" ).grid(row=2, column=0)
        self.parts_total   = Label( f, relief=SUNKEN, width=10, justify=RIGHT, fg="#000" )
        self.parts_passed  = Label( f, relief=SUNKEN, width=10, justify=RIGHT, fg="#0a0" )
        self.parts_xfailed = Label( f, relief=SUNKEN, width=10, justify=RIGHT, fg="#990" )
        self.parts_failed  = Label( f, relief=SUNKEN, width=10, justify=RIGHT, fg="#a00" )
        self.parts_total.grid(row=2,column=1)
        self.parts_passed.grid(row=2,column=2)
        self.parts_xfailed.grid(row=2,column=3)
        self.parts_failed.grid(row=2,column=4)
        
        Button(self.root, text='Quit', command=self.root.quit ).pack()        
        
        scoreboard.event['test_begin'].subscribe(
            test_begin_event( self.test_begin ) )
        scoreboard.event['test_added'].subscribe(
            test_added_event( self.add_test ) )
        scoreboard.event['test_end'].subscribe(
            test_end_event( self.finish_test ) )
        scoreboard.event['suite_finish'].subscribe(
            suite_finish_event( self.finalize ) )
