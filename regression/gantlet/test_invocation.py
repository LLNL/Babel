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
import re
import string

import filesys

_hasno_equals = re.compile(r'^[^=]*$')
_have_gantlet_name = re.compile(r'\bGANTLET_NAME=(\S*)')
_have_gantlet_cd = re.compile(r'\bGANTLET_CD=(\S*)')
_have_gantlet_noflags = re.compile(r'\bGANTLET_NOFLAGS=(\S*)')
_in_backticks = re.compile(r'^`(.*)`$');

class test_invocation:
    """
    test_invocation: The encapsulation of an entire evocation of a test.

    Has the following member data:
    raw_string    - (string) the entire original string
    symbolic_name - (string) the assigned symbolic name
    envsettings   - (string) environment settings (from raw)
    arguments     - (string) arguments (from raw)
    filename      - (string) complete filename (from raw possibly with vpath)
    is_srcdir     - (boolean) true if file is in srcdir
    cd_to_file    - (boolean) true if we must cd to the file's directory before executing
    dirname       - (string) directory prefix of filename
    basename      - (string) basename (from filename)
    extension     - (string) extension (from filename)
    errmgs        - (string) error messages
    """

    def __init__( self, raw_string, srcdir, scoreboard ):
        self.errmsgs = []
        # assign raw string
        cmd = _in_backticks.search(raw_string);
        if cmd:
            self.raw_string = cmd.group(1)
            self.is_command = 1
        else:
            self.raw_string = raw_string 
            self.is_command = 0
            
        # split raw_string = [ENV1=val1 [...]] /some/file.ext [-arg1 [...]]
        tokens = self.raw_string.split()
        index=0
        for token in tokens:
            if ( _hasno_equals.match( token ) ):
                break
            else:
                index = index + 1

        # set some basic attributes
        self.switches = ""
        self.envsettings =" ".join(tokens[:index])
        self.filename = os.path.expandvars( tokens[index] )
        self.arguments = " ".join(tokens[index+1:])
        self.dirname = os.path.dirname( self.filename )
        (self.basename, self.extension) = \
                        os.path.splitext( os.path.basename( self.filename ))

        self.cd_to_file = 1
        
        self.symbolic_name = self.PVT_get_name()

        if not self.is_command:
            self.PVT_confirm_file( srcdir )

        self.PVT_check_cd()

        self.PVT_set_options( scoreboard )

    #
    # The following methods with the PVT_ prefix should be considered private methods
    #

    def PVT_get_name( self ):
        # symbolic_name = raw filename or GANTLET_NAME=<arg>
        gotname = _have_gantlet_name.search(self.raw_string)
        if gotname:  #okay set the name and remove the explicit set from the string
            return gotname.group(1)
        else:
            return self.filename


    def PVT_check_cd( self ):
        # symbolic_name = raw filename or GANTLET_CD=<arg>
        gotcd = _have_gantlet_cd.search(self.raw_string)
        if gotcd:  #okay set the name and remove the explicit set from the string
            if (gotcd.group(1)[0] in [ 'Y', 'y' ]):
                self.cd_to_file = 1
            else:
                self.cd_to_file = 0


    def PVT_confirm_file(self, srcdir):
        # now determine file's location
        isSrcdir = ''
        if ( os.path.isfile( self.filename ) ):
            self.is_srcdir = 0
        elif ( os.path.isfile( os.path.join( srcdir, self.filename ) ) ):
            self.is_srcdir = 1
            self.filename = os.path.normpath( os.path.join( srcdir, self.filename ) )
        else:
            file = os.path.basename( self.filename )
            path1 = os.path.dirname( file )
            path2 = os.path.dirname(  os.path.join( srcdir, file ) )
            errmsg = "file '%s' not found in \n\t\t'%s' or\n\t\t'%s'" % \
                     ( file, path1, path2 )
            self.errmsgs.append( (errmsg,"file not found") )

    def PVT_set_options( self, scoreboard ):
        """
        set addtional options to the commands
        """
        if  _have_gantlet_noflags.search(self.raw_string):
            return
        if ( scoreboard.config.switches != '' ):
            self.switches += ' ' + scoreboard.config.switches

        if ( scoreboard.config.name != '' ):
            self.switches += " --config=%s" % scoreboard.config.name

        if ( scoreboard.config.verbatim_args != '' ):
            self.switches += ' ' + scoreboard.config.verbatim_args

        if ( scoreboard.config.srcdir != os.curdir ):
            bindir = filesys.check_vpath_build(scoreboard.config,self.dirname)
            if ( bindir != os.curdir ):
                self.switches += " --bindir=%s" % bindir
