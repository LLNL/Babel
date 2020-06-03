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

import gantlet
from gantlet.events import *

import sys
if sys.version_info[0] == 2 and sys.version_info[1] >= 2:
    from xml.sax.saxutils import escape, quoteattr
else:
    from xml.sax.saxutils import escape
    def quoteattr(s):
        return "\"" + escape(s) + "\""

class xml_tree:
    """
    A base class for XML entities.  This base class is only
    meant for printing out the XML file.  The xml_display
    receives Gantlet events and builds this tree.  The final
    step in displaying the tree is simply printing it to a file.
    """
    def __init__( self, element_name, parent ):
        self.element_name = element_name
        self.parent = parent
        self.children = []
        if parent != None:
            parent.children.append( self )

    def printTree( self, file, tab, newline="\n" ):
        if len(self.children) == 0 :
            self.printPrefix( file, tab, "/" )
        else:
            if len( self.children ) == 1 and self.children[-1].isOneLine():
                newline = ""
                tab2=0
                incr=0
            else:
                newline = "\n"
                tab2=tab
                incr=4
            self.printPrefix(file, tab, "" )
            for i in self.children:
                file.write(newline)
                i.printTree( file, tab+incr, newline )
            file.write(newline)    
            self.printPostfix( file, tab2 )

    def isOneLine(self):
        "returns true if can be formatted to one line"
        return len(self.children)==0

    def printPrefix( self, file, tab, inline):
        file.write( "%*s<%s%s>" % ( tab, "", self.element_name, inline) )

    def printPostfix( self, file, tab ):
        file.write( "%*s</%s>" %( tab, "", self.element_name) )

    def depth(self):
        "return integer depth of tree, root=1"
        if self.parent:
            return 1 + self.parent.depth()
        else:
            return 1

    def path(self):
        "return string path of self to root"
        if self.parent:
            return self.element_name + "->" + self.parent.path()
        else:
            return self.element_name

class xml_gantlet( xml_tree ):
    def __init__( self ):
        xml_tree.__init__( self, "gantlet", None)

    def printPrefix( self, file, tab, inline):
        file.write( "%*s<%s version=%s%s>" % ( tab, "", self.element_name, \
                                                   quoteattr( gantlet.VERSION), inline) )

class xml_gantlet_head( xml_tree ):
    def __init__( self, parent ):
        xml_tree.__init__( self, "head", parent)

class xml_gantlet_body( xml_tree ):
    def __init__( self, parent ):
        xml_tree.__init__( self, "body", parent)

class xml_attribute( xml_tree ):
    def __init__( self, parent, key, value ):
        xml_tree.__init__( self, "attribute", parent )
        self.key = key
        self.value = value

    def printPrefix( self, file, tab, inline):
        file.write( "%*s<%s key=%s value=%s%s>" % ( tab, "", self.element_name, \
                                                            quoteattr( self.key ), \
                                                            quoteattr( self.value ), inline) )
        
class xml_gantlet_test ( xml_tree ) :
    def __init__( self, parent ):
        xml_tree.__init__( self, "test", parent )
        test = None

    def printPrefix( self, file, tab, inline ):
        start = "%*s<test number=%s name=%s result=%s type=%s nparts=%s%s>" \
                % ( tab, "", quoteattr("%d"%self.test.number), quoteattr(self.test.name), \
                    quoteattr(self.test.result), quoteattr(self.test.type), quoteattr("%d"%len(self.test.part)),\
                    inline)
        file.write( start )
       
class xml_part ( xml_tree ):
    def __init__( self, parent ):
        xml_tree.__init__( self, "part", parent )
        self.result = "FAIL"
        self.number = -1

    def printPrefix( self, file, tab, inline ):
        file.write("%*s<part number=%s result=%s%s>" % \
                   (tab, "", quoteattr("%d"%self.number), quoteattr(self.result), inline ))

class xml_error ( xml_tree ):
    def __init__( self, parent, canon ):
        xml_tree.__init__( self, "error", parent )
        self.canon = canon

    def printPrefix( self, file, tab, inline ):
        start = "%*s<error canon=%s%s>" \
                % ( tab, "", quoteattr(self.canon), inline)
        file.write( start )


class xml_warning ( xml_tree ):
    def __init__( self, parent, canon ):
        xml_tree.__init__( self, "warning", parent )
        self.canon = canon

    def printPrefix( self, file, tab, inline ):
        start = "%*s<warning canon=%s%s>" \
                % ( tab, "", quoteattr(self.canon), inline)
        file.write( start )

class xml_comment ( xml_tree ):
    def __init__( self, parent ):
        xml_tree.__init__( self, "comment", parent )

class xml_pcdata ( xml_tree ) :
    def __init__( self, parent, buffer='' ):
        self.buffer = buffer
        if len( parent.children ) and \
           isinstance( parent.children[-1], xml_pcdata ):
            parent.children[-1].buffer += buffer
            self = parent.children[-1]
        else:
            xml_tree.__init__( self, "PCDATA", parent )
            self.buffer = buffer

    def isOneLine(self):
        return self.buffer.strip().rfind('\n') == -1

    def printPrefix( self, file, tab, inline ):
        file.write( escape(self.buffer).strip() )

    def printPostfix( self, file, tab ):
        pass
    
class xml_display:
    def test_begin( self, test ):
        self.tests.append( xml_gantlet_test( self.body ) )
        self.current = self.tests[-1]
        # xml_pcdata( self.current, "depth = %d" % self.current.depth() )
        
    def test_end( self, test ):
        self.tests[-1].test = test
        self.current = self.body
        
    def part_begin( self, data ):
        test = data[0]
        number = data[1]
        self.parts.append(xml_part(self.tests[-1]))
        self.parts[-1].number = number
        self.current = self.parts[-1]
        # xml_pcdata( self.current, "depth = %d" % self.current.depth() )        

    def part_end( self, data ):
        test = data[0]
        number = data[1]
        self.parts[-1].result = test.part[-1].result
        self.current = self.tests[-1]

    def display_error( self, data ):
        test = data[0]
        msg = data[1]
        canon = data[2]
        error = xml_error( self.current, canon )
        xml_pcdata( error, msg )
        
    def display_warning( self, data ):
        test = data[0]
        msg = data[1]
        canon = data[2]
        warning = xml_warning( self.current, canon )
        xml_pcdata( warning, msg )

    def text( self, buffer ):
        xml_pcdata( self.current, buffer )

    def comment( self, buffer ):
        comment = xml_comment( self.current )
        xml_pcdata( comment, buffer )
        # xml_pcdata( comment, "depth = %d, path = %s" % (comment.depth(),comment.path() ) )
        
    def display_final( self, scoreboard ):
        elapsedtime = scoreboard.endtime - scoreboard.starttime
        xml_attribute( self.head, "package", scoreboard.package )
        xml_attribute( self.head, "profile", scoreboard.profile )
        xml_attribute( self.head, "session", scoreboard.session )
        xml_attribute( self.head, "date", scoreboard.date )
        xml_attribute( self.head, "date_text", scoreboard.date_text )
        xml_attribute( self.head, "time", scoreboard.shorttime )
        xml_attribute( self.head, "whoami", scoreboard.whoami )
        xml_attribute( self.head, "hostname", scoreboard.hostname )
        xml_attribute( self.head, "elapsedtime", "%g" % elapsedtime )
        xml_attribute( self.head, "total_tests", "%d" % scoreboard.tot_tests )
        xml_attribute( self.head, "passed_tests", "%d" % scoreboard.tot_tests_passed )
        xml_attribute( self.head, "xfailed_tests", "%d" % scoreboard.tot_tests_xfailed )
        xml_attribute( self.head, "failed_tests", "%d" % scoreboard.tot_tests_failed )
        xml_attribute( self.head, "broken_tests", "%d" % scoreboard.tot_tests_broken )
        xml_attribute( self.head, "total_parts", "%d" % scoreboard.tot_parts )
        xml_attribute( self.head, "passed_parts", "%d" % scoreboard.tot_parts_passed )
        xml_attribute( self.head, "xfailed_parts", "%d" % scoreboard.tot_parts_xfailed )
        xml_attribute( self.head, "failed_parts", "%d" % scoreboard.tot_parts_failed )
        
        self.gantlet.printTree( self.file, 0 )
        if self.closefile:
            self.file.close()
        return

    def __init__( self, scoreboard, file ):
        self.linewidth = scoreboard.config.linewidth - 10
        self.file = file
        self.tests = []
        self.parts = []
        self.buffer = ''
        self.gantlet = xml_gantlet()
        self.head = xml_gantlet_head( self.gantlet )
        self.body = xml_gantlet_body( self.gantlet )
        self.current = self.body # || self.test[-1] || self.part[-1]
        self.closefile = 1 # true if close file in display_final()

        scoreboard.event['test_begin'].subscribe(
            test_begin_event( self.test_begin ) )
        scoreboard.event['test_end'].subscribe(
            test_end_event( self.test_end ) )
        scoreboard.event['suite_finish'].subscribe(
            suite_finish_event( self.display_final ) )
        scoreboard.event['test_error'].subscribe(
            test_error_event( self.display_error ) )
        scoreboard.event['test_warning'].subscribe(
            test_warning_event( self.display_warning ) )
        scoreboard.event['text'].subscribe(text_event( self.text ) )
        scoreboard.event['comment'].subscribe( comment_event( self.comment ) )
        scoreboard.event['part_begin'].subscribe(
            part_begin_event( self.part_begin ) )
        scoreboard.event['part_end'].subscribe(
            part_end_event( self.part_end ) )
        




