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
A module of the Gantlet Testing Framework
"""

import tempfile
import os

from gantlet.gts_tparser import gts_tparser
from launch import LaunchTest

class gts_tparser_exec(gts_tparser):
   """
   A GTS tparsers specific to reading the output of executables.
   """
   
   def __init__( self, invocation, scoreboard ):
      """
      create an instance with an invocation object and scoreboard
      """
      gts_tparser.__init__(self, invocation, scoreboard )
      self.exit_status = 0

   def get_type( self ):
      """
      This can be redone with introspection
      """
      return "gts_tparser_exec"
      
   def runtest( self ):
      """
      fork the test and read its stderr and stdout
      """
      dirname = self.invocation.dirname
      basename = self.invocation.basename
      extension = self.invocation.extension
      switches = self.invocation.switches
      arguments = self.invocation.arguments
      
      if ( self.invocation.cd_to_file != None ):
         cd_to_file = self.invocation.cd_to_file
      elif not self.invocation.is_command:
         cd_to_file = self.scoreboard.config.cd_to_file
      else:
         cd_to_file = 1
      
      if cd_to_file:
         if dirname != "" and dirname != ".":
            command = "cd %s; ./%s%s %s %s" % ( dirname, basename, extension,
                                                switches, arguments)
            tmpfile = "%s/%s.testresult"%( dirname, basename)
         else:
            command = "./%s%s %s %s" % ( basename, extension,
                                            switches, arguments)
            tmpfile = "./%s.testresult"%( basename)
      else:
         if dirname != "" :
            command = "%s/%s%s %s %s" % ( dirname, basename, extension, switches, arguments )
            tmpfile = "%s/%s.testresult"%( dirname, basename)
         else:
            command = "%s%s %s %s" % ( basename, extension, switches, arguments )
            tmpfile = "./%s.testresult"%( basename)

      filehandle2 = os.fdopen(tempfile.mkstemp('txt','ganttmp')[0],"wb")
      filehandle3 = open( tmpfile, "wb");
      self.exit_status = LaunchTest(command, filehandle2, filehandle3)
      if self.exit_status:
         self.exit_status = self.exit_status >> 8
      filehandle2.seek(0)
      self.collect_results( filehandle2 )
      filehandle2.close()
      filehandle3.close()
