export PROFILE_NAME='alphaev56-dec-osf4.0f'

export SH=bash

export PACKAGE=babel

export SNAPSHOT_NUMBER=`date '+%Y%m%d'`

export PATH=/usr/opt/java121/bin:/usr/bin:/usr/ccs/bin:/usr/bin/X11:/usr/local/bin:/usr/apps/bin:/usr/gapps/bin

#export LD_LIBRARY_PATH=/home/epperly/pub/python/lib:/usr/local/lib:/usr/apps/lib

#export CLASSPATH=

export SVN=svn

if test `whoami` == "epperly2"; then
 SVNUSER=epperly@
else
 SVNUSER=
fi
export SVNROOT=svn+ssh://${SVNUSER}www.cca-forum.org/svn/babel/trunk

export MAKE=gmake
export MAKE_FLAGS=""

export MAIL=mail

export PERL=perl

export ACLOCAL=aclocal

export AUTOMAKE=automake

export AUTOCONF=autoconf

export CD=cd

export MKDIR=mkdir

export CC=gcc

export CFLAGS='-g -Wall -Wtrigraphs -pedantic'

export CXX=g++

export CXXFLAGS='-g -Wall -pedantic'

export F77=g77

export FFLAGS=-g

export MV=mv

export CHMOD=chmod

export CHGRP=chgrp
export TESTGID=babel

export FIND=find

export PACKAGING_BUILDDIR=/usr/casc/babel/scratch
