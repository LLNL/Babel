#!/bin/sh
# 
# Profile: lc-bgl-ibmj-opt.profile
#
export SH=bash
export PACKAGE=babel
export SNAPSHOT_NUMBER=`date '+%Y%m%d'`
export PREFIX=$HOME/babel/install
export PATH=$HOME/sw_ubgl/bin:$PREFIX/bin:$PATH
export LD_LIBRARY_PATH=$PREFIX/lib
host=`hostname`
export SVN=svn
export SVNUSER=
export SVNROOT=svn+ssh://${SVNUSER}svn.cca-forum.org/svn/babel/trunk
export URL=http://www.cca-forum.org/download/babel
export MAKE=make
JOBS=`cat /proc/cpuinfo |sed 's/ //g' |awk 'BEGIN {FS=":"; n=0} {if ($1 ~ "processor") n=$2} END {print n+1}'`
export MAKE_FLAGS="-j $JOBS -l $JOBS"
export MAIL=mail
export PERL=perl
export CD=cd
export MKDIR=mkdir
export MV=mv
export CHMOD=chmod
export CHGRP=chgrp
export TESTGID=babel
export FIND=find
export PACKAGING_BUILDDIR=/nfs/tmp2/`whoami`/babel_scratch
mkdir -p ${PACKAGING_BUILDDIR}
export JAVAPREFIX=/usr/global/tools/sdm/java/Linux/ppc64/ibm-java-ppc-60/
export JNI_INCLUDES="$JAVAPREFIX/include"
export PATH=${JAVAPREFIX}/bin:${PATH}
#PYTHON=/usr/apps/python2.6/bin/python
#PYTHONPATH=/usr/apps/python2.6/lib
export CPP='/usr/bin/gcc -E'
export CC=babel_mpixlc_r
export CXX=babel_mpixlcxx_r
export FC=babel_mpixlf2003_r
export F77=babel_mpixlf77_r
FLAGS='-O2 -g'
export CFLAGS="$FLAGS"
export CXXFLAGS="$FLAGS"
export FFLAGS="$FLAGS"
export FCFLAGS="$FLAGS"
export CONFIG_FLAGS="--target=powerpc64-ibm-bgl --host=powerpc64-ibm-bgl --with-F90-vendor=IBMXL --disable-shared --disable-pdt" 
. setup/casc-linux-final.sh
export PROFILE_NAME="lc-bgp-ibmj-opt"
export MAIL_SERVER=localhost