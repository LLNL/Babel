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
Entry point for running tests with Gantlet
"""

import sys
import getopt
import os.path
import re

import gantlet
#import gantlet.config
from scoreboard import scoreboard
from tparser_factory import tparser_factory
from classic_display import classic_display
from xml_display import xml_display
from email_display import email_display
from gui_display import gui_display

help_string="""
HELP:

 -h   | --help
 -q   | --quiet                   Suppresses classic display to screen
 -g   | --gui                     Enable GUI progress meter
 -s<> | --srcdir=<>               <>=directory, for VPATH builds
 -i<> | --histfile=<>             <>=filename, one line appended about results
 -c<> | --config=<>               <>=string, passed as argument to all tests
 -a<> | --args=<>                 <>=string, passed verbatim as argument to all tests
 -e   | --allow_exitstatus_only   
 -v<> | --verbose=<>              <>=integer, highernumbers more output
 -f<> | --testfile=<>             <>=filename, one test per line
 -f-  | --testfile=-                           reads from stdin instead of file
 -d   | --nowarn_deprecated       do not issue warnings about deprecated syntax
 -x<> | --xmlout=<>               <>=filename, produce XML output
 -m<> | --email=<>                <>=email address, email XML output
 -p<> | --profile=<>              <>=string, a distinguishing name associated with the suite
 -k<> | --package=<>              <>=string, name of the package you are testing
"""
        
def create_suite( argv ):
    """
    creates a gantlet.scoreboard initialized by the argument list
    returns a (scoreboard, remaining_args) tuple
    """
    shortopts = "qgs:hi:c:a:ev:f:dx:m:p:k:"
    longopts = ["quiet", "gui", "srcdir=", "help", "histfile=", "config=", "args=",
                "allow_exitstatus_only", "verbose=", "testfile=",
                "nowarn_deprecated","xmlout=", "email=","profile=","package="];

    (opts, args) = getopt.getopt( argv, shortopts, longopts )

    if len( args ) > 0:
        s = scoreboard(args[0])
    else:
        s = scoreboard('empty')

    for (o, v) in opts:
        if o in ("-h","--help"):
            print(help_string)
            sys.exit(0)
        elif o in ("-q", "--quiet"):
            s.config.quiet = 1
        elif o in ("-g", "--gui"):
            s.config.gui = 1
        elif o in ("-s", "--srcdir"):
            s.config.srcdir = v
        elif o in ("-i", "--histfile"):
            s.config.histfile = v
        elif o in ("-c", "--config"):
            s.config.name = v
        elif o in ("-a", "--args"):
            s.config.verbatim_args = v
        elif o in ("-e", "--allow_exitstatus_only"):
            s.config.allow_exit_status_only = 1
        elif o in ("-v", "--verbose"):
            s.config.verbose = int(v)
        elif o in ("-f", "--testfile"):
            if v == '-':
                file = stdin
            else:
                file = open(v,'r')
            args = args + gettests( file ) 
        elif o in ("-d", "--nowarn_deprecated"):
            s.config.warn_deprecated = 0
        elif o in ("-x", "--xmlout"):
            s.config.xmlout = v
        elif o in ("-m", "--email"):
            s.config.email = v
        elif o in ("-p", "--profile"):
            s.profile = v
        elif o in ("-k", "--package"):
            s.package = v
    return s, args[1:]

def expandvars( args ):
    """returns a matching argument list with paths and environment variables expanded"""
    result = [ os.path.expandvars( os.path.expanduser( arg ) ) for arg in args ]
    return result

def group_brackets( args ):
    """
       args = group_parens( args )

       groups a list of strings according to parens, e.g.
        ['hi', 'there', '[', 'one', 'two', ']', 'hi', '[', 'three', 'four', ']', 'foo']
       to 
        ['hi', 'there', ' one two', 'hi', ' three for', 'foo']

       Very useful after fighting with all kinds of yucky quoting rules in make.
       Does not handle nested brackets.
    """
    result = []
    temp = args[:]
    temp.reverse()
    while ( len(temp) != 0 ):
        arg = temp.pop()
        new_arg = ''
        if ( arg == '[' ):
            while( len(temp) != 0 ):
                arg = temp.pop()
                if ( arg == ']'):
                    break
                new_arg = new_arg + ' ' + arg 
        else:
            new_arg = arg
        result.append(new_arg.strip())
    return result

def gettests( file ):
    """
    tests[] = gettests( file );

    Parses input file.
    Strips '#' to end of line.
    Strips blank lines.
    """
    tests = []
    rough_tests = file.readlines()
    for line in rough_tests:
        line = re.split("#", line,1)[0]
        line = line.strip()
        if len(line):
            tests.append(line)
    return tests


def runall( s, tests ):
    """
    runs each test, then tells scoreboard to finish
    """
    for test in tests:
        # print(test.brief_info())
        test.runtest()
        
    s.finish()
    return
    

def main():
    """
    the main entry point
    """
    (s, args) = create_suite( sys.argv[1:] )
    if not s.config.quiet:
        classic_display( s, sys.stdout )

    args = expandvars( args )
    args = group_brackets( args )        
    tf = tparser_factory( args, s )
    tests = tf.get_tests()
    s.event['test_added'].publish( gantlet.events.test_added_event, len(tests) )
    
    if s.config.xmlout :
        xml_display( s, open( s.config.xmlout,'w' ) )

    if s.config.email :
        email_display(s, s.config.email, s.config.smtpserver )

    if s.config.gui :
        gui = gui_display( s )
        gui.root.after( 1000, runall, s, tests )
        gui.root.mainloop()
    else:
        runall( s, tests )

    errcode = s.tot_tests_broken + s.tot_tests_failed
    if errcode:
        sys.stderr.write( \
            "Error: gantlet: %d failed tests and %d broken tests.\n"% \
            ( s.tot_tests_failed, s.tot_tests_broken ) )
        sys.exit( errcode )
    return
    
if __name__ == '__main__':
    main()

