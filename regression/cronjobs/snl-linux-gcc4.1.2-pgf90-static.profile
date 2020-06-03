#!/bin/sh
# 
# Profile: snl-linux-gcc4.1.2-pgf90.profile
#
# started from:
# . setup/casc-linux-general.sh
# . setup/casc-linux-gccnew-dbg.sh
# . setup/casc-linux-g77-dbg.sh
# . setup/casc-linux-absoft-dbg.sh
# . setup/casc-linux-final.sh
# but none of it is reusable.

export PROFILE_NAME="snl-linux-gcc4.1.2-pgf90-static.profile"
export TESTGID=dialout

#
# . setup/casc-linux-general.sh
# setup the general Linux flags

export MAIL_SERVER=localhost
export SH=/bin/bash
export PACKAGE=babel
export SNAPSHOT_NUMBER=`date '+%Y%m%d'`
JAVAHOME=/usr/lib64/jvm/java-1.5.0-sun-1.5.0_update13
export PATH=$JAVAHOME/bin:/opt/pgi/linux86-64/6.2/bin:/usr/local/bin:/usr/bin:/usr/X11R6/bin:/bin
export LD_LIBRARY_PATH=
export JNI_INCLUDES="$JAVAHOME/include"

export SVN=svn
if test `whoami` == "test"; then
 SVNUSER=baallan@
else
 SVNUSER=
fi
export SVNROOT=svn+ssh://${SVNUSER}www.cca-forum.org/svn/babel/trunk
export URL=http://www.cca-forum.org/download/babel
export MAKE=make
export MAKE_FLAGS="-j8"
export MAIL=mail
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
# export PACKAGING_BUILDDIR=/export/0/babel_test
# export PACKAGING_BUILDDIR=${HOME}/babel_scratch
export PACKAGING_BUILDDIR=${HOME}/babel_testing/obj-trunk-regression-static-pgf
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
export FFLAGS=

#
# . setup/casc-linux-absoft-dbg.sh
# This sets up pgi 6.2 f90 static


# LD_LIBRARY_PATH=/opt/pgi/linux86-64/6.2/libso:"$LD_LIBRARY_PATH"
export FC=pgf90
export CHASMPREFIX=$HOME/chasms/chasm_pgi62_rc2
export PGI=/opt/pgi
export LM_LICENSE_FILE=/opt/pgi/license.dat:/opt/pgi/license-combined.dat
export FCFLAGS=
export F90LIBS="-L/opt/pgi/linux86-64/6.2/lib -L/usr/lib64 -L/usr/lib64/gcc/x86_64-suse-linux/4.1.2/  -lpgf90 -lpgf90_rpm1 -lpgf902 -lpgf90rtl -lpgftnrtl -lnspgc -lpgc -lm"
export FCLIBS="-L/opt/pgi/linux86-64/6.2/lib -L/usr/lib64 -L/usr/lib64/gcc/x86_64-suse-linux/4.1.2/  -lpgf90 -lpgf90_rpm1 -lpgf902 -lpgf90rtl -lpgftnrtl -lnspgc -lpgc -lm"


#
# . setup/casc-linux-final.sh
# Settings that should be at the end of the setup
export CONFIG_FLAGS="--disable-shared --without-sidlx --with-F90-vendor=PGI"

