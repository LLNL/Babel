#!/bin/sh
# 
# Profile: anl-osx-gcc4.0.1-gfortran
#

export FORTRAN="gfortran"
export PROFILE_NAME="anl-osx-gcc4.0.1-${FORTRAN}-dbg.profile"
export TESTGID=dialer
export REPLYTO="norris@mcs.anl.gov"

#
# . setup/casc-linux-general.sh
# setup the general Linux flags

export MAIL_SERVER=smtp.mcs.anl.gov
export SH=/bin/bash
export PACKAGE=babel
export SNAPSHOT_NUMBER=`date '+%Y%m%d'`
JAVAHOME=/usr
#export PATH=/bin:/usr/bin:/usr/local/bin:/opt/local/bin:/usr/X11R6/bin
export PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/opt/local/bin:/usr/X11R6/bin
export LD_LIBRARY_PATH=
export JNI_INCLUDES="/System/Library/Frameworks/JavaVM.framework/Versions/1.5.0/Headers"

export SVN=svn
if test `whoami` == "test"; then
 SVNUSER=norris@
else
 SVNUSER=
fi
export SVNROOT=svn+ssh://${SVNUSER}www.cca-forum.org/svn/babel/trunk
export URL=http://www.cca-forum.org/download/babel
export MAKE=make
export MAKE_FLAGS="-j3"
export MAIL=/usr/bin/mail
export PERL=perl
export ACLOCAL=aclocal
export AUTOMAKE=automake
export AUTOCONF=autoconf
export CD=cd
export MKDIR=mkdir
export MV=mv
export CHMOD=chmod
export CHGRP=chgrp
export FIND=find
export PYTHON=/usr/bin/python
# export PACKAGING_BUILDDIR=/export/0/babel_test
# export PACKAGING_BUILDDIR=${HOME}/babel_scratch
export PACKAGING_BUILDDIR=${HOME}/cca/babel_testing/obj-trunk-regression-${FORTRAN}
mkdir -p ${PACKAGING_BUILDDIR}

#
# . setup/casc-linux-gccnew-dbg.sh
# This shell script sets up GCC new (using the symbolic link to the
# latest available version of GCC) with debugging for the CASC Linux
# cluster. 

export CC=gcc
export CFLAGS='-g -Wall -Wtrigraphs'
export CXX=g++
export CXXFLAGS='-g -Wall'

#
# . setup/casc-linux-g77-dbg.sh
# This shell script sets up GNU F77 with optimization
# for the CASC Linux cluster.

export F77=g77
export FFLAGS=-g

#
# . setup/casc-linux-absoft-dbg.sh
# This sets up pgi 6.2 f90 static

# LD_LIBRARY_PATH=/opt/pgi/linux86-64/6.2/libso:"$LD_LIBRARY_PATH"
export FC=${FORTRAN}
export F90=${FORTRAN}
export F77=${FORTRAN}
export CHASMPREFIX=$HOME/cca/chasm-${FORTRAN}
export FCFLAGS=-g


#
# . setup/casc-linux-final.sh
# Settings that should be at the end of the setup
if test "X$CONFIG_FLAGS" == "X" ; then
  export CONFIG_FLAGS="--disable-static --without-sidlx --with-F90-vendor=GNU"
else
  export CONFIG_FLAGS="$CONFIG_FLAGS --disable-static --without-sidlx --with-F90-vendor=GNU"
fi

