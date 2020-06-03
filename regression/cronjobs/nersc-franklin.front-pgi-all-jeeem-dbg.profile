#!/bin/sh
# 
# Profile: nersc-franklin-pgi6.1.6-all-jeeem-dbg.profile
#

# setup the general Linux flags

export MAIL_SERVER=localhost
export SH=bash
export PACKAGE=babel
export SNAPSHOT_NUMBER=`date '+%Y%m%d'`

#
# To Set Proper PATH for Jaguar,
# you must externally invoke (in a tcsh or csh... :-b)
# the following commands before calling the gantlet_cron.sh
# script:
#
# module load subversion
# module load pgi
# module load gcc
# module load java
# module load moab
# module load xt-os
# module load java/jdk-1.5.0.06
# module load xt-mpt
#

# export PATH=/apps/wget/1.10.2/jaguar:/usr/local/bin:/bin:/usr/bin:/etc:/usr/etc:/usr/local/lib:/sbin:/opt/pgi/6.1.6/linux86-64/6.1/bin/:/apps/java/jdk-1.5.0.06/jaguar/bin
# export PATH=/usr/local/bin:/bin:/usr/bin:/etc:/usr/etc:/usr/local/lib:/sbin:/opt/pgi/6.1.6/linux86-64/6.1/bin/:/apps/java/jdk-1.5.0.06/jaguar/bin
export LD_LIBRARY_PATH=/usr/local/lib
export JNI_INCLUDES='/opt/java/jdk1.5.0_10/include'

host=franklin.nersc.gov

export SVN=svn
export SVNROOT=svn+ssh://kohl@www.cca-forum.org/svn/babel/trunk
export URL=http://www.cca-forum.org/download/babel
export MAKE=make
export MAKE_FLAGS="-j2 -l 2.1"
export MAIL=mail
export PERL=perl
export ACLOCAL=aclocal
export AUTOMAKE=automake
export AUTOCONF=autoconf
# export CONFIG_FLAGS="--enable-auto-disable"
export CONFIG_FLAGS="--disable-sidlx --without-sidlx --disable-python --disable-java --disable-shared --enable-static --enable-pure-static-runtime=yes --with-F90-vendor=PGI"
export CD=cd
export MKDIR=mkdir
export MV=mv
export CHMOD=chmod
export CHGRP=chgrp
export TESTGID=users
export FIND=find

# export PACKAGING_BUILDDIR=${HOME}/babel_scratch
# export PACKAGING_BUILDDIR=${HOME}/franklin_babel_testing/babel_scratch
WORK=/scratch/scratchdirs/kohl
export PACKAGING_BUILDDIR=${WORK}/babel_scratch
mkdir -p ${PACKAGING_BUILDDIR}

# set the PGI version
export PGIVERSION=`pgc -version /dev/null 2>&1 | head -n 1 | awk ' { print $3}'`

export CC=pgcc
export CFLAGS='-g'
export CXX=pgCC
export CXXFLAGS='-g'

# fortran...
export F77=pgf77
export FFLAGS=-g

# fortran 90...
export CPP='gcc -E'
export FC=pgf90

export PROFILE_NAME="nersc-franklin-pgi${PGIVERSION}-all-jeeem-dbg"

