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

import sys
import getopt
import getpass
import poplib
import string
import StringIO
import xml.sax

#import gantlet.config
from gantlet.session_email_parser import session_email_parser
from gantlet.session_archiver import session_archiver

help_string="""
HELP:

 -h   | --help                       Display this message
 -v   | --verbose                    Verbose
 -E   | --encrypted                  Connect to POP service via SSL
 -s<> | --pop-server=<>              <>=string, name of server (e.g. mail.llnl.gov)
 -u<> | --userid=<>                  <>=string, userid of mail account
 -p<> | --passwd=<>                  <>=string, password (not recommended)
 -r<> | --repository=<>              <>=string, path to where Gantlet msgs are stored
 -c   | --count                      Count total messages
 -d   | --delete                     Delele Gantlet msgs from server
 -j   | --delete-junk                Delete non Gantlet msgs from server
 
"""

class session_pop_client:
    """Pull gantlet messages from a Pop Server.

    This module provides a user interface for pulling
    Gantlet messages from a POP server and handing
    them off to the gantlet.file_manager.

    pop_client = session_pop_client( popserver, popuser, passwd ) # create client, but does not connect
    all_msgs = pop_client.load_msgs( ) # connect, get messages, disconnect
    (msgs, junk) = pop_client.split_msgs( all_msgs ) # sort messages

    messages are encapsulated as an array of session_email_parsers 
    
    """

    def __init__(self, popserver, popuser, passwd, debug=0, encrypted=None):
        """Create instance based on server, userid, and passwd"""
        self.popserver = popserver
        self.popuser = popuser
        self.passwd = passwd
        self.connection = None
        self.debug = debug
        self.encrypted = encrypted

    def connect(self):
        """Return live connection to the server"""
        if self.debug > 1: print( 'Connecting...')
        if self.encrypted:
            server = poplib.POP3_SSL( self.popserver )
        else:
            server = poplib.POP3( self.popserver )
        server.user( self.popuser )
        server.pass_( self.passwd )
        if self.debug > 1: print(server.getwelcome())
        return server
    
    def load_msgs( self, loadfrom=1 ):
        """Connect, load session_email_parser's into memory, and disconnect.

        A connection is attempted and all messages starting
        from 'loadfrom' (default=1) are put into a list of         
        gantlet.session_email_parser and returned.  The messages are NOT
        removed from the POP server in the process.

        """
        n_failed=0
        server = self.connect( )
        try:
            if self.debug > 1 : print(server.list())
            msgs = []
            (n_msgs, bytes) = server.stat()
            if self.debug > 1 :
                print("Retrieving %d messages (%d bytes)" % ( n_msgs, bytes ))
            for i in range( loadfrom, n_msgs+1 ):
                (hdr, content, bytes) = server.retr(i)
                content = '\n'.join( content)
                strfile = StringIO.StringIO(content)  # make string look like file
                try:
                    msg =  session_email_parser( strfile, i, content )
                    msgs.append(msg)
                except xml.sax._exceptions.SAXParseException:
                    print("Message %d had parse exception" %(i,))
                    print("Exception message: " + sys.exc_value.getMessage())
                    print( content[0:4096] + "\n")
                    # print(content)
                    n_failed = n_failed + 1
        finally:
            server.quit()
        assert len(msgs) == (n_msgs - loadfrom - n_failed + 1)
        return msgs 

    def del_msgs( self, msgs ):
        """Connect to POP server, delete msgs.id, and disconnect
        
        msgs - array of items with an integer 'id' attribute

        Messages are deleted on the basis of their id number
        matching on the POP server.  This is not a problem as
        long as another client doesn't delete the messages
        out from under you.

        """
        if len( msgs ) == 0 : return
        server = self.connect( )
        try:
            print(server.list())
            for i in msgs:
                if self.debug > 1 :
                    print("deleting msg %d \"%s\"" % (i.id, i.subject))
                server.dele(i.id)
        finally:
            server.quit()

    def split_msgs( self, msgs ):
        """Sort messages between gantlet msgs and chaff.

        Gantlet messages have an 'is_gantlet' attribute
        that evaluates to true
        """
        valid_msgs = []
        invalid_msgs = []
        for i in msgs:
            if i.is_gantlet:
                valid_msgs.append(i)
            else:
                invalid_msgs.append(i)
        return ( valid_msgs, invalid_msgs )
        

def _commandline():
    popserver=None
    popuser=None
    passwd = None
    ssl_encrypted = None
    delete_junk = None
    delete_msgs = None
    count = None
    repository = None
    verbose = None
    shortopts = "hvs:u:p:r:cdjE"
    longopts = ["help", "verbose" "pop-server=", "userid=", "passwd=", "repository=",
                "count", "delete", "delete-junk", "encrypted" ]

    (opts,args) = getopt.getopt( sys.argv[1:], shortopts, longopts )
    for (o, v) in opts:
        if o in ("-h", "--help"):
            print(help_string)
            sys.exit(0)
        elif o in ("-v", "--verbose"):
            verbose = 1
        elif o in ("-s", "--pop-server"):
            popserver = v
        elif o in ("-u", "--userid"):
            popuser = v
        elif o in ("-p", "--passwd"):
            passwd = v
        elif o in ("-r", "--repository"):
            repository = v
        elif o in ("-c", "--count"):
            count = 1
        elif o in ( "-d", "--delete" ):
            delete_msgs = 1
        elif o in ("-j", "--delete-junk"):
            delete_junk = 1
        elif o in ("-E", "--encrypted"):
            ssl_encrypted = 1
            
    if not passwd:
        passwd = getpass.getpass( "Password for %s@%s : " % (popuser, popserver) )

    # Now do the interesting stuff
    mail_reader = session_pop_client( popserver, popuser, passwd, debug=3,
                                      encrypted=ssl_encrypted)
    all_msgs = mail_reader.load_msgs( )
    (msgs, junk) = mail_reader.split_msgs( all_msgs )
    
    if count or verbose :
        print("%d messages total:  %d gantlet, %d other" % (
                len( all_msgs), len( msgs ), len (junk ) ))

    if verbose:
        for msg in msgs:
            #print (msg.id, msg.attr)
            if '/' in msg.attr['session']:
                print( '*********************')
                msg.attr['session']='ask_epperly'
            else:
                print('**** NO RENAMING NECESSARY ****')
            print (msg.id, msg.attr)

    if repository and len(msgs):
        if verbose: print("Loading into repository ('%s')" % repository)
        mgr = session_archiver(repository)
        for msg in msgs:
            mgr.insert( msg )
        mgr.store_index()

    if delete_junk and delete_msgs:
        mail_reader.del_msgs( all_msgs )
    elif delete_junk:
        mail_reader.del_msgs( junk )
    elif delete_msgs:
        mail_reader.del_msgs( msgs )

    if verbose: print( 'Bye.')
    
if __name__ == '__main__': _commandline()

