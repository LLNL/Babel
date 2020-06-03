#!/bin/sh
# 
# Profile: casc-osx-gcc46-sunj.profile
#
export MAIL=/usr/bin/mail
export MAIL_SERVER=poptop.llnl.gov
export SH=bash
export PACKAGE=babel
export SNAPSHOT_NUMBER=`date '+%Y%m%d'`
export PREFIX=$HOME/babel/install
export PATH=$PREFIX/bin:$HOME/sw/bin:$PATH
export LD_LIBRARY_PATH=$PREFIX/lib:$HOME/sw/lib
export PATH=System/Library/Frameworks/JavaVM.framework/Versions/1.6/Home/bin:$PATH
export LD_LIBRARY_PATH=/System/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Libraries:$LD_LIBRARY_PATH
export JAVAC='/System/Library/Frameworks/JavaVM.framework/Versions/1.6/Home/bin/javac'
export JAVA='/System/Library/Frameworks/JavaVM.framework/Versions/1.6/Home/bin/java'
export JNI_INCLUDES="-I/System/Library/Frameworks/JavaVM.framework/Versions/1.6/Home/include"
export JAVAPREFIX="/System/Library/Frameworks/JavaVM.framework/Versions/1.6/Home"
export BABEL_JVM_FLAGS=-Xmx2048M
host=`hostname`
export SVN=svn
export SVNUSER=
export SVNROOT=svn+ssh://${SVNUSER}svn.cca-forum.org/svn/babel/trunk
export URL=http://www.cca-forum.org/download/babel
export MAKE=make
JOBS=`sysctl hw.ncpu | awk '{print $2}'`
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
export PACKAGING_BUILDDIR=/tmp/babel_scratch
mkdir -p ${PACKAGING_BUILDDIR}
export CPP='gcc-fsf-4.6 -E'
export CC=gcc-fsf-4.6
export CXX=g++-fsf-4.6
export FC=gfortran-fsf-4.6
export F77=gfortran-fsf-4.6
FLAGS='-O2 -ggdb' # -pipe -march=native -funroll-loops -ftree-vectorize'
export CPP='gcc -E'
export CFLAGS="$FLAGS"
export CXXFLAGS="$FLAGS"
export FFLAGS="$FLAGS"
export FCFLAGS="$FLAGS"
export CONFIG_FLAGS="" 
. setup/casc-linux-final.sh
export PROFILE_NAME="casc-osx-gcc46-sunj.profile"
