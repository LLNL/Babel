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
Test result based solely on the exit status of a process.
"""


import gantlet.events
from gantlet.result_base import result_base, Part

class exit_status_result(result_base):
    """
    Test result based solely on the exit status of a process.
    """
    def __init__( self, raw_name, scoreboard, type, exit_status ):
        """
        create, parse, and store result of a process

        raw_name - the unmodified argument
        scoreboard - the communication hub for events
        type -
        exit_status - the integer exit status
        """
        result_base.__init__( self, raw_name, scoreboard, type )
        self.name = "%s (%s)" % ( self.raw_name, "exit" )
        self.exit_status = exit_status
        # declare the test
        self.number = self.scoreboard.get_id()
        self.declared_nparts = 1
        self.n_parts = 1
        self.scoreboard.event['test_begin'].publish( gantlet.events.test_begin_event, self )
        # create a single part
        partno = 1
        part = Part()        
        part.number = partno
        self.part.append(part)
        self.scoreboard.event['part_begin'].publish( gantlet.events.part_begin_event, (self, partno) )
        # declare the result
        result = ''
        if self.exit_status == None:
            result = 'PASS'
        elif self.exit_status == 77:
            result = 'XFAIL'
        else:
            result = 'FAIL'
        part.result = result
        self.scoreboard.event['part_end'].publish( gantlet.events.part_end_event, (self, partno) )
        # now deal with the end
        self.declared_result = result
        self.PTC_confirm_nparts()
        self.PTC_confirm_result()
        self.scoreboard.event['test_end'].publish( gantlet.events.test_end_event, self )
