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
Module in the Gantlet Testing Framework
"""

import os.path
import string

def check_vpath_build( config, dirname ):
    """
    Checks if VPATH mode is enabled.  If so,
    return bindir, which is a relative path from
    the srcdir back to the build directory.

    NOTE: that dirname should already have srcdir
    prepended to it.
    """

    if ( config.srcdir == os.curdir ):
        return os.curdir

    if ( config.verbose > 0 ):
        print("vpath build detected...")

    fullpath = os.getcwd().split( os.sep );
    testdirs = dirname.split( os.sep );
    bindirs = []
    n_dirs_up = 0
    # now build bindirs by iterating over testdirs in reverse
    for i in range(len(testdirs)-1,-1,-1):
        if ( testdirs[i] == os.pardir ):
            n_dirs_up += 1
        elif (testdirs[i] == os.curdir ):
            print("WARNING: %s found in result of path_to_list." % os.curdir)
        else:
            bindirs.append( os.pardir )

    # the last n_dirs_up go into bindir
    # which takes us back down the directory srcdir goes up.
    bindir = os.sep.join( bindirs + fullpath[-n_dirs_up:])

    if ( config.verbose > 1 ):
        print("  current working directory (absolute) = " + \
              os.sep.join( fullpath))
        print( "  directory where tests (scripts) are run (relative) = " + \
              os.sep.join( testdirs))
        print("  directory for scripts to find compiled binaries = " + \
              bindir)

    return bindir
