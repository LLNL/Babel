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
This class creates instances of test 
(or more precisely, children of test).
"""

#import re
import os
from gantlet.test_invocation import test_invocation
from gantlet.gts_tparser_exec import gts_tparser_exec
from gantlet.gts_tparser_script import gts_tparser_script, known_extensions
from gantlet.gts_tparser_read import gts_tparser_read
from gantlet.unknown_tparser import unknown_tparser
from gantlet.xml_tparser import xml_tparser, xml_extension

class tparser_factory:
    def __init__( self, args, scoreboard, srcdir = os.curdir):
        self.args = args
        self.srcdir = srcdir
        self.tests = []
        for arg in self.args:
            test = self.create_test( arg, scoreboard )
            self.tests.append( test )

    def create_test( self, test_string, scoreboard ):
        """
        test = create_test( test_string, scoreboard )

        creates an appropriate derived class inheriting from 
        gantlet.test for the given test_string
        """

        invocation = test_invocation( test_string, self.srcdir, scoreboard )

        # now start checking the file system.
        if invocation.errmsgs:
            return unknown_tparser( invocation, scoreboard )
        if invocation.is_command:
            return gts_tparser_exec( invocation, scoreboard )
        if os.access( invocation.filename, os.R_OK ):
            if xml_extension( invocation.extension ):
                return xml_tparser( invocation, scoreboard )
            elif invocation.extension in known_extensions.keys():
                return gts_tparser_script( invocation, scoreboard )
        if os.access( invocation.filename, os.X_OK ):
            return gts_tparser_exec( invocation, scoreboard )
        elif os.access( invocation.filename, os.R_OK ):
            return gts_tparser_read( invocation, scoreboard )
        else:
            invocation.errmsgs.append( ("File not Readable","unreadable") )
            return unknown_tparser( invocation, scoreboard )

    def append_test( self, test_string ):
        self.tests.append( self.create_test( test_string ) )

    def get_tests( self ):
        return self.tests
