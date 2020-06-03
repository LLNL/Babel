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
Contains the events and event handlers used in Gantlet
"""

class event_base:
    """
    Base class for all events
    """
    def __init__( self, method ):
        """
        Create with hook for method to call
        """
        self.method = method
        return

    def fire( self, data ):
        """
        Fire predetermined method with data tuple
        """
        self.method( data )
        return

class test_begin_event( event_base ):
    pass

class test_end_event( event_base ):
    pass

class test_added_event( event_base ):
    pass

class part_begin_event( event_base ):
    pass

class part_end_event( event_base ):
    pass

class test_interrupt_event( event_base ):
    pass

class test_exitstatus_event( event_base ):
    pass

class test_warning_event( event_base ):
    pass

class test_error_event( event_base ):
    pass

class suite_finish_event( event_base ):
    pass

class text_event( event_base ):
    pass

class comment_event( event_base ):
    pass

class event_handler:
    """
    Generic handler for all events
    """
    def __init__( self, event_type ):
        """
        Create a handler for a specific event type

        event_type must be a subclass of event_base
        """
        assert issubclass(event_type, event_base), 'type must inherit from event_base'
        self.event_type = event_type
        self.subscribers = []
        return

    def subscribe( self, event ):
        """
        subscribe a new event listener 
        """
        if isinstance( event, self.event_type ):
            self.subscribers.append(event)
            return 1
        else:
            return 0

    def publish( self, event_type, data ):
        """
        calls fire on all matching subscribed events
        """
        if issubclass( event_type, self.event_type ):
            for e in self.subscribers:
                e.fire(data)
            return 1
        else:
            return 0

                
def _test():
    """
    Simple test driver.
    """
    def myprint1( data ):
        print( "1 ", data)

    def myprint2( data ):
        print( "2 ", data)

    def myprint3( data ):
        print( "3 ", data)

    def myprint4( data ):
        print( "4 ", data)

    class test_begin_event2(test_begin_event):
        pass

    print( "ready to start")
    x = event_handler( test_begin_event )
    print( "subscribe")
    results = { 
      "parent" : x.subscribe( event_base( myprint1 ) ),
      "self" : x.subscribe( test_begin_event( myprint2 ) ),
      "sibling" : x.subscribe( test_end_event( myprint3 ) ),
      "child" : x.subscribe( test_begin_event2( myprint4 ) )
    }
    print( results)

    print("publish")
    tuple = [
      x.publish( event_base, "parent" ),
      x.publish( test_begin_event, "self"),
      x.publish( test_end_event, "sibling"),
      x.publish( test_begin_event2, "child")
    ]
    print( tuple)
    return

if __name__ == '__main__':
    _test()

