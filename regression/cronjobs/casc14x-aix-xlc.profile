export PROFILE_NAME='casc14x-aix-xlc'

export MAIL_SERVER=poptop.llnl.gov

export SH=bash
export SHELL=bash

export PACKAGE=babel14x

export SNAPSHOT_NUMBER=`date '+%Y%m%d'`

export PATH=/usr/java14_64/bin:/usr/local/bin:/usr/bin:/usr/sbin:/usr/ucb:/usr/local/gnu/bin:/usr/local/scripts:/usr/apps/bin
# /usr/casc/babel/apps/aix/bin:
export LIBPATH=""
# /usr/casc/babel/apps/aix/lib


export SVN=svn

if test `whoami` == "epperly"; then
 SVNUSER=epperly@
else
 SVNUSER=
fi
export SVNROOT=svn+ssh://${SVNUSER}www.cca-forum.org/svn/babel/branches/release-1-4-0-branch

export MAKE=gmake
export MAKE_FLAGS="-j3 "

export MAIL=mail

export PERL=perl

export ACLOCAL=aclocal

export AUTOMAKE=automake

export AUTOCONF=autoconf

export CD=cd

export MKDIR=mkdir

export CC=newcc

export CPP='gcc -E'

export CFLAGS='-g -qalias=noansi -qwarn64'

export CXX=newxlC

export CXXFLAGS='-g -qstaticinline -qalias=noansi -qwarn64 -qrtti=all'

export F77=newf77

export FFLAGS='-g -Wl,-bbigtoc'

export FC=newxlf90

export FCFLAGS='-qsuffix=f=f90 -qsuffix=cpp=F90 -Wl,-bbigtoc -qwarn64'

export PYTHON=python64

export MV=mv

export CHMOD=chmod

export CHGRP=chgrp
export TESTGID=babel

export FIND=find

export TEST=/usr/local/gnu/bin/test

export CRONROOT=/usr/casc/babel/scratch/cronjobs
export PACKAGING_BUILDDIR=/p/gscratcha/`whoami`

#export PACKAGING_BUILDDIR=/home/kumfert/cronjob/babel

export JNI_INCLUDES='-I/usr/java14_64/include'

# export CONFIG_FLAGS="--with-jni-header-dir=/usr/java14_64/include"
if test "X$CONFIG_FLAGS" == "X" ; then
  export CONFIG_FLAGS="--with-F90-vendor=IBMXL --with-arch=aix64 --with-bindc "
else
  export CONFIG_FLAGS="$CONFIG_FLAGS --with-F90-vendor=IBMXL --with-arch=aix64 --with-bindc "
fi


# temporary work around until we add "-bM:UR"
export LDR_CNTRL=USERREGS
