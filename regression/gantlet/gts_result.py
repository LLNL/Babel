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

"""
Module of the Gantlet Testing Framework
"""

import events
from result_base import result_base, Part
import gts_tparser


class gts_result(result_base):
    """
    The results of a test emitting Gantlet Testing Syntax (GTS)

    Methods of this class are called by gts test as it parses the
    test's output.  The two classes coordinate closely by passing
    a state back and forth.
    """
    
    def __init__( self, raw_name, scoreboard, type ):
        """
        Create the test
        """
        self.declared_nparts = None
        result_base.__init__( self, raw_name, scoreboard, type )
        return

    def test_begin( self, state, test_begin ):
        """
        Invoked by gts_tparser when it parses a new test
        """
        if state != 'between tests' and state != 'before tests' and state != 'broken' :
            # if we're in the wrong state, flag as broken
            msg = "TEST_BEGIN encountered in \"%s\" state." % state
            self.broken( msg, 'state error')
            return 'broken'
        self.number = self.scoreboard.get_id()
        try:
            testname = test_begin.group('testname')
            # this is a named test, possibly a multitest
            if testname[0] == '/':
                self.name = self.raw_name + testname
            else:
                self.name = "%s[%s]" % ( self.raw_name, testname )
        except IndexError:
            pass
        self.declared_nparts = int(test_begin.group('nparts'))
        self.n_parts = self.declared_nparts # assume its correct (for now)
        self.scoreboard.event['test_begin'].publish( events.test_begin_event, self )
        return 'before parts'


    def part_begin( self, state, part_begin ):
        """
        Invoked by gts_tparser when it parses a part begin
        """
        partno = int(part_begin.group('partno'))
        if (state == 'in part') and (partno == len(self.part)+1):
            msg = "Missing \"RESULT %d\" statement: set to UNRESOLVED" % (partno-1,)
            self.warning(msg,'RESULT %d missing' % (partno-1,))
            part_end = gts_tparser._part_end.search( "RESULT %d UNRESOLVED" % (partno-1,))
            self.part_end( state, part_end )
        elif state != 'between parts' and state != 'before parts':
            msg = "PART %d encountered in \"%s\" state." % (partno, state)
            self.broken( msg, 'state error')
            return 'broken'        
        part = Part()
        part.number = partno
        self.part.append(part)
        self.scoreboard.event['part_begin'].publish( events.part_begin_event, (self,partno) )
        return 'in part'

    def part_end( self, state, part_end ):
        """
        Invoked by gts_tparser when it parses a parts end        
        """
        resultno = int(part_end.group('partno'))
        if  state != 'in part':
            msg = "result %d encountered in \"%s\" state." % (resultno, state)
            self.broken( msg, 'state error')
            return 'broken'
        part = self.part[-1]
        if resultno != part.number: 
            msg = "part begin #%d != result #%d." % (part.number, resultno)
            self.broken( msg, 'state error')
            return 'broken'
        part.result = part_end.group('result')
        self.part[-1] = part
        self.scoreboard.event['part_end'].publish( events.part_end_event, (self, resultno) )
        return 'between parts'

    def test_end( self, state, test_end ):
        """
        Invoked by gts_tparser when it parses the end of a test
        """
        if not self.exit_status and test_end.group('exitstatus'):
            self.exit_status = int( test_end.group('exitstatus') )
        if self.exit_status and not self.is_broken:
            msg = "Abnormal Termination %d" % self.exit_status
            canon = "exit_status=%d" % self.exit_status
            self.broken( msg, canon )
        if state == 'before parts':
            msg = "No \"PART\" Directive found"
            self.broken( msg, 'parse error')
        elif state != 'between parts':
            msg = "test end encountered in \"%s\" state." % state
            self.broken( msg, 'state error')
        self.declared_result = test_end.group('result')
        self.PTC_confirm_nparts()
        self.PTC_confirm_result()
        self.scoreboard.event['test_end'].publish( events.test_end_event, self )
        return 'between tests'

    def try_recover_premature_end( self, state ):
        """
        Sometimes tests just don't end where you wnat them.
        And ya gotta go back and figure out why.
        """
        if state == 'before tests': # declare its begin and end
            test_begin = gts_tparser._test_begin.search('TEST_BEGIN 0')
            self.test_begin( state, test_begin)
            msg = "No \"TEST_BEGIN\" Directive found"
            self.broken( msg, 'parse error')
            if self.exit_status:
                msg = "Abnormal Temination %d" % self.exit_status
                self.broken( msg, 'exit_status=%d' % self.exit_status )
            test_end = gts_tparser._test_end.search('TEST_RESULT BROKEN')
            self.test_end('between parts', test_end )
        elif state == 'before parts': # missing part declaration
            msg = "No \"PART\" Directive found"
            self.broken( msg, 'parse error')
            if self.exit_status:
                msg = "Abnormal Temination %d" % self.exit_status
                self.broken( msg, "exit_status=%d" % self.exit_status )
            test_end = gts_tparser._test_end.search('TEST_RESULT BROKEN')
            self.test_end('between parts', test_end )
        elif state == 'between parts': # missing test end event
            if self.exit_status:
                msg = "Abnormal Temination %d" % self.exit_status
                self.broken( msg, "exit_status=%d" % self.exit_status )
            if ( self.n_parts == len( self.part ) ):
                msg = "No \"TEST_RESULT\" Directive found"
                self.warning( msg, 'TEST_RESULT missing')
            else:
                msg = "No \"TEST_RESULT\" Directive found"
                self.broken( msg, 'parse error')
            test_end = gts_tparser._test_end.search('TEST_RESULT FAIL')
            self.test_end('between parts', test_end )
        elif state == 'in part': # missing part end and test end
            partno = len(self.part)
            msg = "Missing \"RESULT %d\" statement: set to UNRESOLVED" % (partno,)
            self.warning(msg,'RESULT %d missing' % (partno,))
            part_end = gts_tparser._part_end.search( "RESULT %d UNRESOLVED" % (partno,))
            self.part_end( state, part_end )
            msg = "No \"TEST_RESULT\" Directive found"
            self.broken( msg, 'parse error')
            if self.exit_status:
                msg = "Abnormal Temination %d in part %d of %d" % ( self.exit_status, len(self.part), self.n_parts)
                self.broken(  msg, 'exit_status=%d'% self.exit_status )
            self.scoreboard.event['test_end'].publish( events.test_end_event, self )     
        elif state == 'broken':
            self.scoreboard.event['test_end'].publish( events.test_end_event, self )     
        else:
            msg = "test ended in state \"%s\"" % state
            self.broken( msg, 'premature end')

