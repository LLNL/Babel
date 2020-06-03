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
import os.path
import cPickle
import gzip
import fcntl
from sys import maxint
from time import strptime, mktime
# from fcntl import LOCK_UN, LOCK_EX
from gantlet.session_email_parser import session_email_parser

class EntrySort:
    """ Sort based primarily on key, and secondarily on some other sort
    
    e.g.  EntrySort( 'package', EntrySort('profile', EntrySort('date')))
    sorts by package, then profile, then date attributes
    """

    def __init__(self, key, secondary=None):
        self.key = key
        self.secondary = secondary
    
    def set_secondary( self, secondary ):
        self.secondary = secondary
        
    def __call__( self, x, y ):
        if x[self.key] == y[self.key] and self.secondary:
            return self.secondary.__call__(x,y)
        else:
            return cmp( x[self.key], y[self.key] )
                   
class session_archiver:
    """Maintains an archive of Gantlet Messages

    Maintains a 'index.gantlet' file at the root of the archive.
    Will fetch read-only filehandles based on attibutes such as
    package, platform, config, date.  Files are stored in
    the repository as packagename/platformname/date-confix.

    """
    def __init__( self, root, create=None ):
        root = os.path.expanduser(root)
        root = os.path.expandvars(root)
        root = os.path.normpath(root)
        root = os.path.normcase(root)
        if not os.path.exists( root ):
            os.mkdir( root )
        if os.path.isdir(root):
            self.root = root
        self.index = []
        self.lockf = None
        if not create:
            self.load_index()


    def rescan(self):
        """Rescan directory and rebuild index"""
        os.path.walk( self.root, self._visit, self )
        return

    def _visit( self, foo, dirname, names ):
        """ Used in by self.rescan() to walk the repository
        """
        print(self)
        print(foo)
        for name in names:
            if name[-7:]=='.xml.gz':
                print(name)
                f = gzip.open( os.path.join( dirname, name ), 'r' )
                s = session_email_parser( f, id=len(self.index) )
                p = self.createpath(s)
                entry = s.attr.copy()
                entry['path']=p
                self.index.append( entry )
                f.close()
        return

    def load_index(self):
        # self.lockf = open(  os.path.join( self.root, 'gantlet.index.lock' ), 'r' )
        # fcntl.flock(self.lockf.fileno(), LOCK_EX)
        f = gzip.open( os.path.join( self.root, 'gantlet.index.gz' ), 'r' )
        p = cPickle.Unpickler( f )
        self.index = p.load()
        f.close()
        
    def store_index(self):
        f = gzip.open( os.path.join( self.root, 'gantlet.index.gz' ), 'w' )
        p = cPickle.Pickler( f ) 
        p.dump( self.index )
        f.close()
        # if self.lockf:
        #     fcntl.flock(self.lockf.fileno(), LOCK_UN)
        #     self.lockf = None

    def select(self, package='.*', profile='.*', session='.*',
               starttime=0, endtime=maxint):
        """ get a list of entries corresponding to query of attributes via re
and a date range.
        """
        matches = []
        for entry in self.index:
            entrytime = mktime(strptime("%s %s" %
                                        (entry['date'], entry['time']),
                                        "%Y-%m-%d %H:%M:%S"))
            if ( re.match(package,entry['package']) and
                 re.match(profile,entry['profile']) and
                 re.match(session,entry['session']) and
                 (starttime <= entrytime <= endtime)):
                matches.append( entry )
        return matches
                
    def createpath( self, msg ):
        """Create path based on msg data.

        currently:  (package)/(profile)/(date)_(time)_(session).xml.gz
        """
        filename = (msg.attr['date'] + '_'+ msg.attr['time'] +
                    '_' + msg.attr['session'] +  '.xml.gz')
        relpath = os.path.join( msg.attr['package'], msg.attr['profile'], filename )
        return relpath
        
    def insert(self, msg):
        """Insert new message into repository"""
        packagedir = os.path.join(self.root, msg.attr['package'] )
        if not os.path.isdir( packagedir ):
            os.mkdir( packagedir )
        profiledir = os.path.join( packagedir, msg.attr['profile'] )
        if not os.path.isdir( profiledir ):
            os.mkdir( profiledir )
        relpath = self.createpath( msg ) 
        fullpath = os.path.join ( self.root, relpath )
        gz = gzip.open( fullpath, 'wb')
        gz.write( msg.msg )
        gz.close()
        entry = msg.attr.copy()
        entry['path']=relpath
        self.index.append( entry )
            
        
