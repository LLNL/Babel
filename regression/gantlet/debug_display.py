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

class debug_display:
    def display_test( self, test ):
        print("** Test %s, %d parts" % (test.name, test.nparts))

    def display_result( self, test ):
        print("** Result = %s (%d, %d, %d)" % (test.result,
                                               test.n_pass, test.n_xfail, test.n_fail))

    def display_part( self, test ):
        print("** part begin %d" % test.part[-1].number)

    def display_partend( self, data ):
        test = data[0]
        resultno = data[1]
        print("** part end %d %s" % (resultno,test.part[-1].result))

    def display_warning( self, data ):
        test = data[0]
        msg = data[1]
        print("** WARNING: %s" % msg)

    def display_error( self, data ):
        test = data[0]
        msg = data[1]
        print("** ERROR: %s" % msg)


    def __init__( self, scoreboard ):
        scoreboard.event['test_begin'].subscribe(
            test_begin_event( self.display_test ) )
        scoreboard.event['test_end'].subscribe(
            test_end_event( self.display_result ) )
        # scoreboard.event['part_begin'].subscribe(
        #      part_begin_event( self.display_part ) )
        # scoreboard.event['part_end'].subscribe(
        #      part_end_event( self.display_partend ) )
        
