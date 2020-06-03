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
import string
import xml.sax
import xml.sax.handler
import gantlet.events
from gantlet.unknown_tparser import unknown_tparser
from gantlet.xml_result import xml_result
from gantlet.tparser_base import tparser_base

class xml_tparser (  xml.sax.handler.ContentHandler, tparser_base ):
    """
    This class parses an XML file containing multiple tests
    """
    def __init__( self, invocation, scoreboard ):
        tparser_base.__init__(self, invocation, scoreboard )
        self.state = []          # a stack to keep track of state
        self.buffer = []    # buffer to accumulate character data
        self.tests = []          # a queue of xml_result instances
        self.n_promised_tests = 1

    def startElement( self, name, attrs ):
        self.PVT_flush_buffer()
        self.state.append( name )
        if name == 'test':
            self.tests.append( xml_result(self.invocation, self.scoreboard ) )
            if len(self.tests) > self.n_promised_tests:
                diff = len(self.tests) - self.n_promised_tests
                self.n_promised_tests = len(self.tests)
                self.scoreboard.event["test_added"].publish( \
                    gantlet.events.test_added_event, diff )                
        if name == 'attribute':
            self.attributes( attrs )
        elif name in ( 'test', 'part', 'error', 'warning', 'comment'):
            start_element[name]( self.tests[-1], attrs )

    def endElement( self, name ):        
        if name != self.state[-1]:
            print("error ending element\"%s\" but expected \"%s\"" % \
                      ( name, self.state[-1] ))
        self.state.pop()
        if name in ('test', 'part'):
            self.PVT_flush_buffer()
            end_element[ name ]( self.tests[-1] )
        elif name in ('error', 'warning', 'comment'):
            msg = "".join(self.buffer).strip()
            end_element[ name ]( self.tests[-1], msg )
            self.buffer = []
        else:
            self.PVT_flush_buffer()
                    
    def attributes( self, attrs ):
        if attrs['key'] == 'total_tests':
            value = int(attrs['value'])
            self.n_promised_tests = value
            self.scoreboard.event["test_added"].publish( \
                    gantlet.events.test_added_event, value-1 )
        
    def characters( self, content ): 
        self.buffer.append(content)

    def runtest( self ):
        try:
            tparser = xml.sax.make_tparser()
        except:
            #create bogus test and mark it broken
            self.invocation.errmsgs = [ ( str(sys.exc_info()[0]), \
                                          "missing SAX") ]
            t = unknown_tparser(self.invocation, self.scoreboard ) 
            self.tests.append( t )
            t.runtest()
        else:
            tparser.setContentHandler( self )
            tparser.parse( self.invocation.filename )

            

    def PVT_flush_buffer(self):
        if len ( self.buffer ):
            s = "".join( self.buffer).strip()
            if len (s):
                self.scoreboard.event['text'].publish( \
                    gantlet.events.text_event, s)
            self.buffer= []
            
                                    

def xml_extension( ex ):
    if ( ex == '.xml' or ex == '.XML' ):
        return 1
    else:
        return 0



start_element = {}
start_element['gantlet'] = xml_result.PVT_start_stub
start_element['head'] = xml_result.PVT_start_stub
start_element['attribute'] = xml_result.PVT_start_stub
start_element['body'] = xml_result.PVT_start_stub
start_element['test'] = xml_result.test_begin
start_element['part'] = xml_result.part_begin
start_element['comment'] = xml_result.comment_begin
start_element['warning'] = xml_result.warning_begin
start_element['error'] = xml_result.error_begin

end_element = {}
end_element['gantlet'] = xml_result.PVT_end_stub
end_element['head'] = xml_result.PVT_end_stub
end_element['attribute'] = xml_result.PVT_end_stub
end_element['body'] = xml_result.PVT_end_stub
end_element['test'] = xml_result.test_end
end_element['part'] = xml_result.part_end
end_element['comment'] = xml_result.comment_end
end_element['warning'] = xml_result.warning_end
end_element['error'] = xml_result.error_end

if __name__ == '__main__':
    print('hi')
