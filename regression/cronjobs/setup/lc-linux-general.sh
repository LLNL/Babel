#!/bin/sh
#
# setup the general Linux flags for LC machines

export MAIL_SERVER=mailhost
export SH=bash
export PACKAGE=babel
export SNAPSHOT_NUMBER=`date '+%Y%m%d'`
export PREFIX=$HOME/babel/install
export PATH=$PREFIX/bin:$HOME/sw/bin:$PATH
export LD_LIBRARY_PATH=$PREFIX/lib:$HOME/sw/lib
export JAVAPREFIX=/usr/lib/jvm/java-1.6.0-sun.x86_64
export JNI_INCLUDES='$JAVAPREFIX/include'
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
#export PACKAGING_BUILDDIR=/p/lscratchd/$USER/babel_nightly
export PACKAGING_BUILDDIR=/tmp/$USER/babel_nightly
mkdir -p ${PACKAGING_BUILDDIR}
