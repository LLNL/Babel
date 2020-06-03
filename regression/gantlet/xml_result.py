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
import re
import gantlet.events
from gantlet.result_base import result_base, Part

class xml_result( result_base ):
    """
    This class encapsulates a single XML encoded test
    """
    def __init__( self, invocation, scoreboard ):
        self.invocation = invocation
        result_base.__init__(self, invocation.symbolic_name, scoreboard, "xml_result" )
        self.n_parts = 0
        self.scoreboard = scoreboard

    def test_begin( self, attrs ):
        self.number = self.scoreboard.get_id()
        attrname = attrs['name']
        if self.invocation.filename == self.name: # if name not explicitly altered
            if self.invocation.basename == "index.xml":
                # strip 'index.xml' from symbolic name
                self.name = self.invocation.dirname + '/'        
        if len(self.name) < 1 :
            self.name = attrname           
        elif attrname[0] == '/' or self.name[-1] == '/':
            self.name = self.name + attrname
        else:
            self.name = "%s[%s]" % ( self.name, attrname )
        self.declared_result = attrs['result']
        self.result = self.declared_result  # assume its correct (for now)
        self.declared_nparts = int(attrs['nparts'])
        self.n_parts = self.declared_nparts # assume its correct (for now)
        self.type = attrs['type']
        self.part = []
        self.scoreboard.event['test_begin'].publish(
            gantlet.events.test_begin_event, self )

    def part_begin( self, attrs ):
        part = Part()
        part.number = int(attrs['number'])
        part.result = attrs['result']
        self.part.append(part)
        self.scoreboard.event['part_begin'].publish(
            gantlet.events.part_begin_event, ( self, part.number ) )

    def part_end( self ):
        number = self.part[-1].number
        self.scoreboard.event['part_end'].publish(
            gantlet.events.part_end_event, (self, number ) )

    def test_end( self ):
        self.PTC_confirm_nparts()
        self.PTC_confirm_result()
        self.scoreboard.event['test_end'].publish(
            gantlet.events.test_end_event, self )

    def warning_begin( self, attrs ):
        self.canon = attrs['canon']
            
    def warning_end( self, msg ):
        self.warning( msg, self.canon )
        self.canon = ''

    def error_begin( self, attrs ):
        self.canon = attrs['canon']
        #match = re.search("exit_status=(\d*)", self.canon )
        #if match:
        #    self.exit_status = int( match.group(1) )
        
    def error_end( self, msg ):
        self.broken( msg, self.canon )
        self.canon = ''

    def comment_begin( self, attrs ):
        pass

    def comment_end( self, msg ):
        if len( msg ):
            self.scoreboard.event['comment'].publish(
                gantlet.events.comment_event, msg )

    def PVT_start_stub( self, attrs ):
        pass

    def PVT_end_stub( self ):
        pass



