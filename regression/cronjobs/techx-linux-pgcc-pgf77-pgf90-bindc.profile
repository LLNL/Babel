#!/bin/sh
# 
# Profile: anl-osx-gcc4.0.1-gfortran
#

export FORTRAN="pgf90"
export CC="pgcc"
export F77="pgf77"
export PROFILE_NAME="techx-linux-${CC}-${F77}-${FORTRAN}-bindc.profile"
export TESTGID=research
export REPLYTO="tramer@txcorp.com"

#
# . setup/casc-linux-general.sh
# setup the general Linux flags

export MAIL_SERVER=mail.txcorp.com
export SH=/bin/bash
export PACKAGE=babel
export SNAPSHOT_NUMBER=`date '+%Y%m%d'`
JAVAHOME=/usr/local/contrib/jdk1.6.0_16
JAVAPREFIX=/usr/local/contrib/jdk1.6.0_16
#export PATH=/bin:/usr/bin:/usr/local/bin:/opt/local/bin:/usr/X11R6/bin
export PATH=/usr/lib64/qt-3.3/bin:/usr/kerberos/sbin:/usr/kerberos/bin:/usr/lib64/ccache:/usr/local/bin:/bin:/usr/bin:/usr/local/sbin:/usr/sbin:/sbin:/usr/NX/bin:/home/research/tramer/bin:/usr/local/contrib/jdk1.6.0_16/bin
export LD_LIBRARY_PATH=
export JNI_INCLUDES=$JAVAHOME/include

export SVN=svn
if test `whoami` == "tramer"; then
 SVNUSER=sptramer@
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
export PACKAGING_BUILDDIR=${HOME}/babel/nightly-run
mkdir -p ${PACKAGING_BUILDDIR}

#
# . setup/casc-linux-gccnew-dbg.sh
# This shell script sets up GCC new (using the symbolic link to the
# latest available version of GCC) with debugging for the CASC Linux
# cluster. 

source /usr/local/bin/pgi.sh

export CPP='gcc -E'
export CXX=pgcpp

#
# . setup/casc-linux-absoft-dbg.sh
# This sets up pgi 6.2 f90 static

# LD_LIBRARY_PATH=/opt/pgi/linux86-64/6.2/libso:"$LD_LIBRARY_PATH"
export FC=${FORTRAN}
export F90=${FORTRAN}


#
# . setup/casc-linux-final.sh
# Settings that should be at the end of the setup
#if test "X$CONFIG_FLAGS" == "X" ; then
#  export CONFIG_FLAGS="--disable-static --without-sidlx --with-F90-vendor=GNU"
#else
#  export CONFIG_FLAGS="$CONFIG_FLAGS --disable-static --without-sidlx --with-F90-vendor=GNU"
#fi

export CONFIG_FLAGS="--enable-java=/usr/local/contrib/jdk1.6.0_16 JAR=/usr/local/contrib/jdk1.6.0_16/bin/jar --disable-shared --enable-static --with-pic --with-bindc"
