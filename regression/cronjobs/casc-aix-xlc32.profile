export PROFILE_NAME='casc-aix-xlc'

export MAIL_SERVER=poptop.llnl.gov

export SH=bash

export PACKAGE=babel

export SNAPSHOT_NUMBER=`date '+%Y%m%d'`

export PATH=/usr/casc/babel/apps/aix/bin:/usr/java14/bin:/usr/local/bin:/usr/bin:/usr/sbin:/usr/ucb:/usr/local/gnu/bin:/usr/local/scripts:/usr/apps/bin
export LIBPATH=/usr/casc/babel/apps/aix/lib:/usr/lib

export SVN=svn

if test `whoami` == "epperly2"; then
 SVNUSER=epperly@
else
 SVNUSER=
fi
export SVNROOT=svn+ssh://${SVNUSER}www.cca-forum.org/svn/babel/trunk

export MAKE=gmake
export MAKE_FLAGS="-j2 -l 2.1"

export MAIL=mail

export PERL=perl

export ACLOCAL=aclocal

export AUTOMAKE=automake

export AUTOCONF=autoconf

export CD=cd

export MKDIR=mkdir

export CC=newcc

export CPP='gcc -E'

export CFLAGS='-g -qalias=noansi -q32'

export CXX=newxlC

export CXXFLAGS='-g -qstaticinline -qalias=noansi -q32'

export F77=newf77

export FFLAGS='-g -q32'

export FC=newxlf90

export FCFLAGS='-qsuffix=f=f90 -qsuffix=cpp=F90 -Wl,-bbigtoc -q32'

export PYTHON=python

export MV=mv

export CHMOD=chmod

export CHGRP=chgrp
export TESTGID=babel

export FIND=find

export TEST=/usr/local/gnu/bin/test

export PACKAGING_BUILDDIR=/usr/casc_scratch/babel

#export PACKAGING_BUILDDIR=/home/kumfert/cronjob/babel

export CHASMPREFIX=/usr/casc/babel/apps/aix/chasm_HEAD32

export JNI_INCLUDES='-I/usr/java14/include'

# export CONFIG_FLAGS="--with-jni-header-dir=/usr/java14/include"
if test "X$CONFIG_FLAGS" == "X" ; then
  export CONFIG_FLAGS="--with-F90-vendor=IBMXL"
else
  export CONFIG_FLAGS="$CONFIG_FLAGS --with-F90-vendor=IBMXL"
fi
