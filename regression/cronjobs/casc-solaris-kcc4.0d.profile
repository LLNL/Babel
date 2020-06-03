export PROFILE_NAME='casc-solaris-kcc4.0d'

export SH=/usr/local/bin/bash

export PACKAGE=babel

export SNAPSHOT_NUMBER=`date '+%Y%m%d'`

export PATH=/usr/local/automake/1.5/bin:home/kumfert/local/autoconf/2.52/solaris/bin:/usr/local/java/java1.2/bin:/home/casc/bin:/opt/SUNWspro/bin:/usr/local/bin:/usr/ccs/bin:/bin:/usr/sbin:/usr/ucb:/etc:/usr/dt/bin:/usr/openwin/bin:/usr/local/scripts:/usr/local/ar/bin

#export LD_LIBRARY_PATH=/usr/local/lib

export CLASSPATH=.:/usr/local/java/java1.2/lib/rt.jar:/usr/local/java/java1.2/lib/tools.jar

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

export PERL=perl

export ACLOCAL=aclocal

export AUTOMAKE=automake

export AUTOCONF=autoconf

export CD=cd

export MKDIR=mkdir

export CC=cc

export CFLAGS='-g'

export CXX=KCC

export CXXFLAGS='--strict -D__KAI_STRICT -g +K0'

export F77=f77

export FFLAGS='-g'

export MV=mv

export CHMOD=chmod

export CHGRP=chgrp
export TESTGID=babel

export FIND=find

export PACKAGING_BUILDDIR=/usr/casc_scratch/babel
