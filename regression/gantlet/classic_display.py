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

"""Contains the classic_display class.
"""

from gantlet.events import *

class classic_display  :
    """Subscribes to scoreboard events and pretty-prints them to a file.

    Many of the class methods are subscribed to the
    gantlet.scoreboard.scoreboard by __init__ in a publish-subscribe
    pattern.  The different kinds of events are defined in gantlet.events
    """
    
    def __init__( self, scoreboard, file ):
        """
        scoreboard - an instance of gantlet.scoreboard.scoreboard
        file       - a file to write to
        """
        self.linewidth = scoreboard.config.linewidth - 10
        self.file = file
        scoreboard.event['test_begin'].subscribe(
            test_begin_event( self.display_test ) )
        scoreboard.event['test_end'].subscribe(
            test_end_event( self.display_result ) )
        scoreboard.event['suite_finish'].subscribe(
            suite_finish_event( self.display_final ) )
        scoreboard.event['test_error'].subscribe(
            test_error_event( self.display_error ) )
        scoreboard.event['test_warning'].subscribe(
            test_warning_event( self.display_warning ) )
        # scoreboard.event['part_begin'].subscribe(
        #      part_begin_event( self.display_part ) )
        # scoreboard.event['part_end'].subscribe(
        #      part_end_event( self.display_partend ) )
        return
    
    def display_test( self, test ):
        """subscribed to test_begin events"""
        line = "%3d. %s " % (test.number, test.name)
        dots = self.linewidth - len(line)
        self.file.write( line + '.'*dots )
        self.file.flush()
        return 
    
    def display_result( self, test ):
        """subscribed to test_result events"""
        self.file.write( "%7s\n" % test.result)
        return
        
    def display_part( self, test ):
        """subscribed to part_begin events (currently disabled)"""
        print("** part begin %d" % test.part[-1].number)
        return 

    def display_partend( self, data ):
        """subscribed to part_end events (currently disabled)"""
        test = data[0]
        resultno = data[1]
        print( "** part end %d %s" % (resultno,test.part[-1].result))
        return

    def display_error( self, data ):
        """subscribed to test_error events"""
        test = data[0]
        msg = data[1]
        i = msg.rfind('\n')   # no \n ? then i = -1
        width = len(msg[i+1:])# this works for i==-1 too
        spaces = max(self.linewidth-width-12,0)* ' '
        self.file.write("\n     ERROR: %s%s" % ( msg, spaces ))
        self.file.flush()
        return

    def display_warning( self, data ):
        """subscribed to test_warning events"""
        test = data[0]
        msg = data[1]
        i = msg.rfind('\n')   # no \n ? then i = -1
        width = len(msg[i+1:])# this works for i==-1 too
        spaces = max(self.linewidth-width-14,0)* ' '
        self.file.write("\n     WARNING: %s%s" % ( msg, spaces ))
        self.file.flush()
        return
        
    def display_final( self, scoreboard ):
        """subscribed to suite_finish events"""
        scoreboard.elapsedtime = scoreboard.endtime - scoreboard.starttime
        linebreak = (scoreboard.config.linewidth-1) * '*' 
        header = """
%(date_text)s  at  %(shorttime)s 
by %(whoami)s@%(hostname)s
Wallclock Time: %(elapsedtime)g secs
            Total      Passed      Xfailed      Failed      Broken
Tests     %(tot_tests)7d     %(tot_tests_passed)7d      %(tot_tests_xfailed)7d     %(tot_tests_failed)7d     %(tot_tests_broken)7d
Parts     %(tot_parts)7d     %(tot_parts_passed)7d      %(tot_parts_xfailed)7d     %(tot_parts_failed)7d
""" % scoreboard.__dict__
        self.file.write(linebreak)
        self.file.write(header)
        self.file.write(linebreak + '\n')
        self.failed_tests(scoreboard)
        return

    def failed_tests( self, scoreboard ):
        """Prints a formatted table of failed tests

        Utility for display_final()"""

        if scoreboard.tot_tests_failed + scoreboard.tot_tests_broken == 0:
            pass
        self.file.write("   Broken|Failed|Warning                      Exit  Tot %Fail List of Failed")
        self.file.write("\n")
        self.file.write('-'*(scoreboard.config.linewidth-1))
        self.file.write("\n")
        for test in scoreboard.tests:
            if test.errmsg != '' or test.n_parts_failed > 0:
                self.print_failed_test( test )
        return


    def print_failed_test( self, test ):
        """Prints a line of data in a tabel of failed tests

        Utility for failed_tests()"""
        number = test.number
        name = test.name
        bfw = 'W'
        status = ''
        if test.result == 'FAIL':
            bfw = 'F'
        elif test.result == 'BROKEN':
            bfw = 'B'
        if test.exit_status:
            status = "%d" % test.exit_status
        total = test.n_parts
        fail = test.n_parts_failed
        failed = 0.0
        if total != 0:
            failed = 100 * fail/total 
        list_of_failed = test.canon
        if test.result != 'BROKEN':
            list_of_failed = list_of_failed + self.get_cannon( test )
        if len( list_of_failed) > 18:
            list_of_failed = list_of_failed[:15] + '...'
        self.file.write("%3d %c %-40s %3s %4d %3d%% %s\n" %
                        (number, bfw, name, status, total, failed, list_of_failed))
        return
        
    def get_cannon( self, test ):
        """returns a formatted string of failed part numbers in test

        Utility for print_failed_test()"""
        start = -1
        current = -1
        canon = ''
        state = 'before runs' # also 'between runs' and 'in runs'
        for part in test.part:
            if part.result == 'FAIL':
                current = part.number
                if state == 'between runs':
                    start = part.number
                    canon = canon + ', %d' % part.number
                    state = 'in run'
                elif state == 'before runs':
                    start = part.number
                    canon = canon + "%d" % part.number
                    state = 'in run'
            else:
                if state == 'between runs' or state == 'before runs':
                    pass
                elif state == 'in run':
                    if current > start: # current 
                        canon = canon + '-%d' % current
                    state = 'between runs'
                else:
                    state = 'between runs'
                current = -1
        if state == 'in run':
            canon = canon + '-%d' % len( test.part )
        return canon
            
