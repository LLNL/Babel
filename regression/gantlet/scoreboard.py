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
This object represents the scoreboard of tests
"""

import pwd
import os
import os.path
import sys
import time
import socket # to get hostname
import inspect
try:
    import ConfigParser
except:
    import configparser
    ConfigParser = configparser

#import gantlet.config
from gantlet.tparser_factory import tparser_factory
from gantlet.events import *

_config_defaults = { 'verbose':0,
                     'srcdir':os.curdir,
                     'switches':'',
                     'name':'',
                     'profile':'(default)',
                     'environ':'',
                     'histfile':'',
                     'allow_exit_status_only':None,
                     'linewidth':80,
                     'warn_deprecated':1,
                     'quiet':0,
                     'gui':0,
                     'email':0,
                     'cd_to_file':1 }

class _empty :
    pass

class scoreboard :
    def __init__( self, session ):
        # init status of tests
        self.tot_parts               = 0
        self.tot_parts_passed        = 0
        self.tot_parts_xfailed       = 0
        self.tot_parts_failed        = 0
        self.tot_tests               = 0
        self.tot_tests_passed        = 0
        self.tot_tests_xfailed       = 0
        self.tot_tests_failed        = 0
        self.tot_tests_broken        = 0
        self.tests_outstanding       = 0
        self.starttime               = time.time()
        self.elapsedtime             = 0.0
        self.date_text               = time.strftime("%a, %d %b %Y")
        self.date                    = time.strftime("%Y-%m-%d")
        self.shorttime               = time.strftime("%H:%M:%S")
        # init configuration based information

        d = os.path.dirname( inspect.getabsfile(scoreboard))
        cfg = ConfigParser.ConfigParser(_config_defaults)
        cfg.read([ os.path.join(d,'gantlet.cfg'),
                   os.path.join(d,'gantlet.site.cfg'),
                   os.path.expanduser('~/.gantlet.cfg'),
                   os.path.join(os.curdir,'gantlet.cfg')])
        # cfg.write(sys.stdout)
        cfg_obj = _empty()
        optlist = cfg.options('GANTLET')
        boolargs = ['allow_exit_status_only', 'warn_deprecated', 'quiet',
                    'gui', 'cd_to_file']
        intargs = ['verbose', 'linewidth' ]
        val=None
        for o in optlist:
            val=cfg.get('GANTLET',o)
            if val=='None': val=None            
            setattr( cfg_obj, o, val)
        for o in boolargs:
            if o in optlist:
                val=cfg.getboolean('GANTLET',o)
                if val=='None': val=None
                setattr( cfg_obj, o, val )
        for o in intargs:
            if o in optlist:
                val=cfg.getint('GANTLET',o)
                if val=='None': val=None
                setattr( cfg_obj, o, val )
        self.package                 = ''
        self.profile                 = cfg_obj.profile
        self.session                 = session
        self.config                  = cfg_obj
        self._id_no                  = 0
        
        # find my login
        try:
            self.whoami = pwd.getpwuid(os.getuid())[0]
        except:
            self.whoami = 'unknown'           
        
        # find my host name
        self.hostname = socket.getfqdn()
        
        # find my host platform
        self.platform = sys.platform
        
        # prepare a list of tests
        self.tests = []

        # prepare a series of events
        
        self.event = { 
        "test_begin" : event_handler( test_begin_event ),
        "test_end" : event_handler( test_end_event ),
        "test_added" : event_handler( test_added_event ),
        "part_begin" :  event_handler( part_begin_event ),
        "part_end" :  event_handler( part_end_event ),
        "text" : event_handler( text_event ),
        "comment" : event_handler( comment_event ),
        "test_warning" : event_handler( test_warning_event ),
        "test_error" : event_handler( test_error_event ),
        "test_interrupt" : event_handler( test_interrupt_event ),
        "test_exitstatus" : event_handler( test_exitstatus_event ),
        "suite_finish": event_handler( suite_finish_event) }
        
        self.event["test_end"].subscribe(
            test_end_event( self.increment_count ) )

        self.event["test_added"].subscribe(
            test_added_event( self.increment_tests_outstanding ) )

    def get_id( self ):
        self._id_no = self._id_no + 1
        return self._id_no

    def increment_tests_outstanding( self, n_additional ):
        if n_additional > 0:            
            self.tests_outstanding += n_additional
        
    def increment_count( self, test ):
        self.tot_tests += 1
        self.tests_outstanding -= 1
        #print( " %d / %d " % ( self.tot_tests, self.tot_tests + self.tests_outstanding ))
        if test.result == 'PASS':
            self.tot_tests_passed += 1
        elif test.result == 'XFAIL':
            self.tot_tests_xfailed += 1
        elif test.result == 'FAIL':
            self.tot_tests_failed += 1
        elif test.result == 'BROKEN':
            self.tot_tests_broken += 1
        if test.result != 'BROKEN':
            self.tot_parts += test.n_parts
            self.tot_parts_passed += test.n_parts_passed
            self.tot_parts_xfailed += test.n_parts_xfailed
            self.tot_parts_failed += test.n_parts_failed
        self.tests.append( test )
        
    def finish( self ):
        self.endtime               = time.time()
        self.event["suite_finish"].publish( suite_finish_event, self )        


