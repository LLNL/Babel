export SH=/usr/bin/bash

export SHELL=/bin/ksh

export PACKAGE=babel

export SNAPSHOT_NUMBER=`date '+%Y%m%d'`

export PATH=/usr/casc/babel/apps/sun/bin:/usr/apps/bin:/usr/apps/binutils/2.12/bin:/usr/java/bin:/opt/SUNWspro/bin:/bin:/usr/sbin:/usr/bin:/usr/ucb:/usr/local/bin

export LD_LIBRARY_PATH=/usr/casc/babel/apps/sun/lib:/usr/apps/lib


export SVN=svn

if test `whoami` == "epperly2"; then
 SVNUSER=epperly@
else
 SVNUSER=
fi
export SVNROOT=svn+ssh://${SVNUSER}www.cca-forum.org/svn/babel/trunk

export MAKE=make
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

export CFLAGS='-g -Wall'

export CXX=g++

export CXXFLAGS='-g -Wall'

export F77=g77

export FFLAGS='-g'

export FC=f95

export FCFLAGS=-g

export MV=mv

export CHMOD=chmod

export CHGRP=chgrp
export TESTGID=babel

export FIND=find

export PACKAGING_BUILDDIR=/usr/casc_scratch/babel

export CHASMPREFIX=/usr/casc/babel/apps/sun/chasm_120_SUNWspro

. setup/gccversion.sh
export PROFILE_NAME="casc-solaris-gcc${GCCVERSION}"

