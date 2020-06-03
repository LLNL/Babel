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

import os
import gantlet.events

class Part:
    pass

class result_base:
    """
    result_base:  A Mixin class for all test results

    Has the following member data:
       name             - (string)  
       n_parts          - (int) number of parts in the test
       n_parts_passed   - (int) number of parts passed in the test
       n_parts_xfailed  - (int) number of parts having expected failures
       n_parts_failed   - (int) number of parts having failed in the test
       is_broken        - (0 or 1) if the test was broken
       exit_status      - (int or None) exit status of the program
       errmsg           - (string) accumulated error messages
       canon            - (string) list of failed parts
       part             - list of parts
       number           - (int)
       result           - (string) 'PASS','XFAIL','FAIL',or 'BROKEN'
       type             - (string) type of test
       
    """
    def __init__( self, raw_name, scoreboard, type):
        """
        result_base( \"symbolic test name\", \"file\",
                     \"entire test string\", \"type\" )
        """
        self.scoreboard               = scoreboard
        self.name                     = raw_name
        self.raw_name                 = raw_name
        self.n_parts                  = 0
        self.n_parts_passed           = 0
        self.n_parts_xfailed          = 0
        self.n_parts_failed           = 0
        self.is_broken                = 0
        self.exit_status              = None
        self.errmsg                   = ''
        self.canon                    = ''
        self.part                     = []        
        self.number                   = -1
        self.type                     = type
                
    def broken( self, msg, canon ):
        """
        Set the test as broken
        """
        self.errmsg += "ERROR: " + msg
        self.is_broken = 1
        self.result = 'BROKEN'
        if ( canon != "" ):
            self.canon += "[%s] " % canon
        self.scoreboard.event['test_error'].publish(
            gantlet.events.test_error_event, (self, msg, canon) )
        
    def warning( self, msg, canon ):
        """
        Associate a warning with the test, but do not break
        """
        self.errmsg = "WARNING: " + msg
        if ( canon != "" ):
            self.canon += "[%s] " % canon
        self.scoreboard.event['test_warning'].publish(
            gantlet.events.test_warning_event, (self, msg, canon) )

    #
    # The following methods beginning with PTC should be considered
    # protected methods of this class
    #
    def PTC_confirm_nparts( self ):
        """
        Double check the number of parts in the test

        PTC_ prefix implies the method is protected.
        """
        self.n_parts = len( self.part )
        if self.declared_nparts == -1:
            pass
        elif self.declared_nparts == len ( self.part ):
            pass
        elif self.declared_nparts > len(self.part):
            msg = "declared %d parts, but found only %d." % \
                  (self.declared_nparts, len(self.part))
            self.warning( msg , 'NPARTS high' ) 
        elif self.declared_nparts < len(self.part):
            msg = "declared %d parts, but found %d." % \
                  (self.declared_nparts, len(self.part))
            self.warning( msg , 'NPARTS low' ) 
                    
    def PTC_confirm_result( self ):
        """
        Confirm the declared result is the same as the computed one.

        The result of the test can be computed by the results of
        the constituent parts.

        PTC_ prefix implies the method is protected.
        """
        # 1. If we already know its broken, go no farther
        if self.is_broken:
            return

        # 2. tally up the results of all the parts
        n_pass = 0
        n_xfail = 0
        n_fail = 0
        for part in self.part:
            if part.result == 'PASS':
                n_pass = n_pass + 1
            elif part.result == 'XFAIL' or \
                 part.result == 'UNTESTED' or \
                 part.result == 'UNSUPPORTED':
                n_xfail = n_xfail + 1
            elif part.result == 'FAIL' or \
                 part.result == 'XPASS' or \
                 part.result == 'UNRESOLVED':
                n_fail = n_fail + 1

        # 3. confirm that all parts are accounted for
        if n_pass + n_xfail + n_fail != self.n_parts:
            msg = "only %d out of %d part results recognized" % \
                  n_pass + n_xfail + n_fail, self.n_parts
            self.broken( msg, 'state error')
            state = 'broken'
            return

        # 4. compute result
        if n_fail > 0:
            self.result = 'FAIL'
        elif n_xfail > 0:
            self.result = 'XFAIL'
        elif n_pass != 0:
            self.result = 'PASS'
        else:
            self.result = 'BROKEN'
        # 5. compare against declared result
        if self.result != self.declared_result:
            if self.declared_result == 'BROKEN':
                self.result = 'BROKEN'
            elif self.declared_result == 'FAIL' and not self.result == 'BROKEN':
                self.result = 'FAIL'
            elif self.declared_result == 'XFAIL' and self.result == 'PASS':
                self.result = 'XFAIL'
            msg = "declared result \"%s\" != computed result \"%s\"" % \
                (self.declared_result, self.result)
            self.warning( msg, 'declared/computed test result mismatch')
        # 6. save counts
        self.n_parts_passed = n_pass
        self.n_parts_xfailed = n_xfail
        self.n_parts_failed = n_fail

def _test():
    """
    This regression test needs to be redone with unittest
    """
    pass
    # t = test('autoexec.bat',r'C:\autoexec.bat',r'C:\autoexec.bat')
    # print( t.__dict__)
    # 
    # t = test('winhelp.exe',r'C:\WINNT\winhelp.exe',r'C:\WINNT\winhelp.exe')
    # print( t.__dict__)
    # 
    # t2 = test("`test 2`",'test2', 'test2')
    # print( t2.__dict__)
    # 
    # t2 = test('foo','test',"`PETF_NAME=foo test 2`")
    # print( t2.__dict__)


if __name__ == '__main__':
    _test()
