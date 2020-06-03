#!/bin/sh
# 
# setup the general Linux flags

export MAIL_SERVER=smtp.llnl.gov
export SH=bash
export PACKAGE=babel
export SNAPSHOT_NUMBER=`date '+%Y%m%d'`
export PATH=/usr/local/bin:/usr/bin:/bin
# export JNI_INCLUDES='/usr/casc/babel/apps/java/include /usr/casc/babel/apps/java/include/linux'
host=`hostname`
#if [ -f /usr/apps/subversion/new/setup.sh ] ; then
#  . /usr/apps/subversion/new/setup.sh
#fi
export SVN=svn
if test `whoami` == "epperly2"; then
 SVNUSER=epperly@
else
 SVNUSER=
fi
export SVNROOT=svn+ssh://${SVNUSER}www.cca-forum.org/svn/babel/trunk
export URL=http://www.cca-forum.org/download/babel
export MAKE=make
JOBS=`cat /proc/cpuinfo |sed 's/ //g' |awk 'BEGIN {FS=":"; n=0} {if ($1 ~ "processor") n=$2} END {print n+1}'`
export MAKE_FLAGS="-j $JOBS -l $JOBS"
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
export TESTGID=babel
export FIND=find
# export PACKAGING_BUILDDIR=/export/0/babel_test
# export PACKAGING_BUILDDIR=${HOME}/babel_scratch
export PACKAGING_BUILDDIR=/usr/casc/babel/scratch
mkdir -p ${PACKAGING_BUILDDIR}
