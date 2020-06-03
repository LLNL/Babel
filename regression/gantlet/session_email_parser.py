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
import string
import StringIO
import xml.sax
import xml.sax.handler
from email import message_from_string
from email.utils import parsedate
from email.utils import parseaddr

class SAXShortCircuitException( xml.sax.SAXException ):
    """Exception used to keep from parsing the entire document(only need header)"""
    pass

class session_email_parser ( xml.sax.handler.ContentHandler ) :
    """A XML-encoded Gantlet report received via email"""

    def __init__( self, f, id=0, content='' ):
        """Initialize from an email message encoded as a single long string
        
        f - the entire email file as one big string, hopefully containing xml
        id  - a unique id number (email message id)
        """
        #        self.msg = msg
        if not content:
            content = f.read()
        self.msg = content
        self.id = id
        hdr = message_from_string(content)
        if not hdr.has_key('Subject'):
            self.is_gantlet = None
            self.subject='(no subject)'
            return
        self.subject = hdr['Subject']
        if (self.subject[:13] != "<gantlet-xml " and
	   self.subject[:14] != "<gauntlet-xml "):  #backward compatable
            self.is_gantlet = None
            return
        self.fromwhom = hdr['From']
        self.is_gantlet = 1
        self.date = hdr['Date']
        self.date_tuple = parsedate(self.date)
        self.fp = StringIO.StringIO(hdr.get_payload(decode=True))
        self.attr = {}
        self.attr['package'] = ''
        self.attr['profile'] = ''
        self.attr['session'] = ''
        self.attr['elapsedtime'] = '0'
        self.attr['total_tests'] = '0'
        self.attr['passed_tests'] = '0'
        self.attr['xfailed_tests'] = '0'
        self.attr['failed_tests' ] = '0'
        self.attr['broken_tests' ] = '0'
        self.attr['total_parts' ] = '0'
        self.attr['passed_parts' ] = '0'
        self.attr['xfailed_parts' ] = '0'
        self.attr['failed_parts' ] = '0'
        self.PVT_parse_attr()
        self.PVT_clean_date()

    def startElement( self, name, attrs ):
        """Overrides xml.sax.handler.ContentHandler.startElement()"""
        if name == 'attribute':
            self.attr[ str( attrs['key'] )  ] = str( attrs['value'] )

    def endElement( self, name ):
        """Overrides xml.sax.handler.ContentHandler.startElement()"""
        if name == 'head':
            raise SAXShortCircuitException('no more attributes here')

    def PVT_parse_attr( self ):
        """PRIVATE: Load attributes at front of XML file using SAX"""
        if re.search('result="BROKEN"', self.subject):
            self.PVT_infer_broken_attrs()
        else:
            self.attr['result'] = re.search('result="([^"]*)"',
                                            self.subject ).group(1)
            tparser = xml.sax.make_parser()
            tparser.setContentHandler( self )
            try:
                tparser.parse( self.fp )
            except SAXShortCircuitException:
                # exception raised to stop before parsing whole document
                pass

    def PVT_clean_date(self):
        """PRIVATE: convert 'Thu, 18 Apr 2002' to '2002-04-18'"""
        date = str(self.attr['date']) + ' 00:00:00'
        s = parsedate( date )
        if s:
            self.attr['date_text'] = self.attr['date']
            self.attr['date'] = '%04d-%02d-%02d' % s[0:3]
            
    def PVT_infer_broken_attrs( self ):
        """PRIVATE: Infer needed attributes from email header"""
        self.attr['package'] = re.search('package="([^"]*)"', self.subject ).group(1)
        self.attr['profile'] = re.search('profile="([^"]*)"', self.subject ).group(1)
        self.attr['session'] = re.search('session="([^"]*)"', self.subject ).group(1)
        self.attr['date_text'] = self.date
        self.attr['date'] = "%04d-%02d-%02d" % self.date_tuple[0:3]
        self.attr['time'] = "%d:%d:%d" % self.date_tuple[3:6]
        email = parseaddr( self.fromwhom )[1]
        self.attr['whoami'] = re.search('^([^@]*)@', email ).group(1)
        self.attr['hostname'] = re.search('@(.*)', email ).group(1)        
        self.attr['result'] = 'BROKEN'
