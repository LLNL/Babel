#!/bin/sh
# 
# setup the general Linux flags

export MAIL_SERVER=smtp.llnl.gov
export SH=bash
export PACKAGE=babel
export SNAPSHOT_NUMBER=`date '+%Y%m%d'`
export PATH=/usr/casc/babel/apps/autotools/bin:/usr/casc/babel/apps/ia64/bin:/etc/alternatives/java_sdk/bin:/usr/gapps/babel/apps/bin:/usr/kerberos/bin:/usr/local/bin:/bin:/usr/bin:/usr/bin/X11:/usr/lib/mpi/bin:/usr/apps/bin
export LD_LIBRARY_PATH=/usr/casc/babel/apps/ia64/lib:/usr/gapps/babel/apps/lib

host=`hostname`

#export JNI_INCLUDES=-I/usr/lib/jvm/java-1.4.2-bea-1.4.2.04/include
#export JNI_LDFLAGS=-L/usr/lib/jvm/java-1.4.2-bea-1.4.2.04/jre/lib/i386/jrockit

export SVN=svn
if test `whoami` == "epperly2"; then
 SVNUSER=epperly@
else
 SVNUSER=
fi
export SVNROOT=svn+ssh://${SVNUSER}www.cca-forum.org/svn/babel/trunk
export MAKE=make
export MAKE_FLAGS='-j3'
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
