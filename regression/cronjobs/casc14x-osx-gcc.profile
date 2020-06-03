
export SH=/bin/bash

export PACKAGE=babel

export SNAPSHOT_NUMBER=`date '+%Y%m%d'`

export PATH=/bin:/sbin:/usr/sbin:/usr/bin

# export LD_LIBRARY_PATH=/usr/casc/babel/apps/sun/lib:/usr/apps/lib
test -r /sw/bin/init.sh && . /sw/bin/init.sh


export SVN=svn

if test `whoami` == "epperly2"; then
 SVNUSER=epperly@
else
 SVNUSER=
fi
#export SVNROOT=svn+ssh://${SVNUSER}www.cca-forum.org/svn/babel/trunk
export PACKAGE=babel14x
export SVNROOT=svn+ssh://${SVNUSER}www.cca-forum.org/svn/babel/branches/release-1-4-0-branch

export MAKE=make
export MAKE_FLAGS="-j3 -l 3.1"

export MAIL=/usr/bin/mail

export MAIL_SERVER=poptop.llnl.gov

export PERL=perl

export ACLOCAL=aclocal

export AUTOMAKE=automake

export AUTOCONF=autoconf

export CD=cd

# export PYTHON=python

export MKDIR=mkdir

export CC=gcc

export CFLAGS='-g -Wall'

export CXX=g++

export CXXFLAGS='-g -Wall'

export F77=gfortran

export FFLAGS='-g'

export MV=mv

export CHMOD=chmod

export CHGRP=chgrp
export TESTGID=babel

export FIND=find

export JNI_INCLUDES='-I/System/Library/Frameworks/JavaVM.framework/Headers'

export PACKAGING_BUILDDIR=${HOME}/casc_scratch/babel

export CHASMPREFIX=${HOME}/chasm_prefix

. setup/gccversion.sh
export PROFILE_NAME="casc14x-osx-gcc${GCCVERSION}"

export CONFIG_FLAGS="--with-F90-vendor=GNU"
