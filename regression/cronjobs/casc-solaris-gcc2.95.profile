export SH=/usr/local/bin/bash

export PACKAGE=babel

export SNAPSHOT_NUMBER=`date '+%Y%m%d'`

export PATH=/usr/local/java/java1.3/bin:/usr/local/python/2.1/bin:/usr/local/gcc/2.95.3/bin:/home/casc/bin:/opt/SUNWspro/bin:/usr/local/bin:/usr/ccs/bin:/bin:/usr/sbin:/usr/ucb:/etc:/usr/dt/bin:/usr/openwin/bin:/usr/local/scripts:/usr/local/ar/bin

export LD_LIBRARY_PATH=/usr/local/lib

#export CLASSPATH=.:/usr/local/java/java1.2/lib/rt.jar:/usr/local/java/java1.2/lib/tools.jar

export SVN=svn

if test `whoami` == "epperly2"; then
 SVNUSER=epperly@
else
 SVNUSER=
fi
export SVNROOT=svn+ssh://${SVNUSER}www.cca-forum.org/svn/babel/trunk

export MAKE=/usr/local/bin/gmake
export MAKE_FLAGS=""

export MAIL=/usr/ucb/Mail

export MAIL_SERVER=poptop.llnl.gov

export PERL=perl

export ACLOCAL=aclocal

export AUTOMAKE=automake

export AUTOCONF=autoconf

export CD=cd

export MKDIR=mkdir

export CC=gcc

export CFLAGS='-g -Wall -Wstrict-prototypes -pedantic'

export CXX=g++

export CXXFLAGS='-g -Wall -Wstrict-prototypes -pedantic '

export F77=g77

export FFLAGS='-g'

export MV=mv

export CHMOD=chmod

export CHGRP=chgrp
export TESTGID=babel

export FIND=find

export PACKAGING_BUILDDIR=/usr/casc_scratch/babel

export CONFIG_FLAGS="--without-fortran90"

. setup/gccversion.sh
export PROFILE_NAME="casc-solaris-gcc${GCCVERSION}"
