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
import time
import smtplib
import string
import gantlet
from gantlet.events import *
from gantlet.xml_display import xml_display
from email.mime.text import MIMEText
import getopt
import pwd
import sys
import re
import socket

class misc_obj:
    pass


def rfc822time():    
    if time.daylight and (time.localtime()[8] == 1):
        offset = -time.altzone / 3600
    else:
        offset = -time.timezone / 3600            
    date = time.strftime("%a, %d %b %Y %H:%M:%S %%+03d00 (%Z)") % offset
    return date

class email_display ( xml_display ):

    def __init__( self, scoreboard, email_to, mailserver ):
        if scoreboard:
            file = os.tmpfile( ) # some temporary file.
            xml_display.__init__( self, scoreboard, file )
        else:
            self.file = None
        self.email_to = email_to
        self.email_from = None
        self.mailserver = mailserver
        self.closefile = 0 # don't close file at xml_display.display_final()
        self.verbose = None
            
    def display_final( self, scoreboard ):
        """ This overrides the method registered to receive suite_finish events"""
        xml_display.display_final( self, scoreboard )
        # now email the XML file
        self.email_file( scoreboard )
        return

    def email_file( self, scoreboard ):
        """Email a non-broken Session

        Called either in the process of receiving a suite_finish event
        or from wrap_n_send_xml()
        """
        self.file.seek(0) # rewind file to beginning
        msg = MIMEText(self.file.read(), "xml", "iso-8859-1")
        self.email_from = scoreboard.whoami + "@" + scoreboard.hostname
        msg['From'] = self.email_from
        date = rfc822time()
        if scoreboard.tot_tests_failed + scoreboard.tot_tests_broken > 0:
            result = 'FAIL'
        elif scoreboard.tot_tests_xfailed > 0:
            result = 'XFAIL'
        else:
            result = 'PASS'            
        msg['Subject'] = '<gantlet-xml package="%s" result="%s" profile="%s" session="%s" >' % \
                  ( scoreboard.package, result, scoreboard.profile, scoreboard.session)
        msg["To"] = self.email_to
        msg["Precedence"] = "bulk"
        self.email_to_SMTP( msg )

    def print_header(self, msg):
        for k in msg.keys():
            print k + ": " + msg[k]

    def email_to_SMTP(self, msg):
        # now send the email
        server = smtplib.SMTP( self.mailserver )
        if self.verbose:
            print "Ready to send email..."
            self.print_header(msg)
            print "Content: %d bytes " % len( msg.get_payload() )
            server.set_debuglevel(1)
        failed = server.sendmail( msg['From'], [ msg['To'] ], msg.as_string() )
        server.quit()
        if self.verbose:
            if failed: # SMTP may raise exceptions, but let them pass
                print 'Failed Email Recipients:', failed
            else:
                print 'Email Sent'

    def email_file_via_SMTP(  self, header, f=None ):
        if f == None:
            file = self.file
        file.seek(0) # rewind file
        text = file.readlines() #read lines
        text = header + string.join( text, "" )
        if f!=None:
            file.close()
        

    def fake_scoreboard_from_file(self):
        """Creates a bogus scoreboard object so that send_email() can create the email headers

        Used in wrap_n_send_xml(), which implies being run by the commandline, instead of
        a real testing session.
        """
        _have_attribute = re.compile(r"<attribute key=\"([^\"]*)\" value=\"([^\"]*)\"")
        _end_header = re.compile("<head/>")
        o = misc_obj
        self.file.seek(0)        
        for i in range(50):
            line = self.file.readline()
            b = _end_header.search(line)
            if b:
                break
            m = _have_attribute.search(line)
            if m:
                setattr(o,m.group(1),m.group(2))
        setattr(o,"tot_tests_failed", int(o.failed_tests) )
        setattr(o,"tot_tests_broken", int(o.broken_tests) )
        setattr(o,"tot_tests_xfailed", int(o.xfailed_tests) )
        setattr(o,"tot_tests_passed", int(o.passed_tests) )
        return o

    def email_broken(self,package,profile,session):
        self.file.seek(0) # rewind
        msg = MIMEText(self.file.read(), "plain", "iso-8859-1")
        msg['Subject'] = '<gantlet-xml package="%s" result="%s" profile="%s" session="%s">' % \
                  ( package, 'BROKEN', profile, session)
        msg['To'] = self.email_to
        msg['From'] = self.email_from
        msg['Precedence'] = "bulk"
        self.email_to_SMTP(msg)

help_string="""
USAGE:
   ( -h ) | ( [-v] -e<> -m<> (-f<> | -b<> -p<> -k<> -n<>) )
 -h   | --help                       Display this message
 -v   | --verbose                    Verbose
 -e<> | --email=<>                   <>=string, send email to this address
 -m<> | --mailserver=<>              <>=string, domain name of SMTP server
 -x<> | --xmlsrc=<>                  <>=file, wrap up the XML report generated by GANTLET
 -b<> | --broken=<>                  <>=logfile.  at least send why it failed.
 -k<> | --package=<>                 <>=string, name of the package being tested
 -p<> | --profile=<>                 <>=string, configuration info (e.g. ix86-linux-gcc)
 -n<> | --name=<>                    <>=string, name of the test suit (e.g. unit, regression, all)
"""

def wrap_n_send_xml():
    verbose = None
    xmlsrc = None
    broken = None
    mailserver = None
    package = ''
    profile = ''
    name = ''
    shortopts="hvb:e:m:x:k:p:n:"
    longopts=["help","verbose","email=","xmlsrc=","mailserver=","broken"]
    (opts,args) = getopt.getopt( sys.argv[1:], shortopts, longopts )
    for (o,v) in opts:
        if o in ("-h", "--help"):
            print help_string
            sys.exit(0)
        elif o in ("-v", "--verbose"):
            verbose = 1
        elif o in ("-e", "--email"):
            email = v
        elif o in ("-m", "--mailserver"):
            mailserver=v
        elif o in ("-x", "--xmlsrc"):
            xmlsrc = v
        elif o in ("-b", "--broken"):
            broken = 1
            xmlsrc = v
        elif o in ("-k", "--package"):
            package = v
        elif o in ("-p", "--profile"):
            profile = v
        elif o in ("-n", "--name"):
            name = v

    e = email_display( None, email, mailserver  )
    e.verbose = verbose
    e.file = open(xmlsrc,'r')
    if broken:
        e.email_from =  "%s@%s" %  (pwd.getpwuid(os.getuid())[0],
                                    socket.gethostbyaddr(socket.gethostname())[0])
        e.email_broken(package,profile,name)
        pass # email contents of file with broken header
    elif xmlsrc:
        s = e.fake_scoreboard_from_file()
        e.email_file(s)
    
if __name__ == '__main__':
    wrap_n_send_xml()
    
