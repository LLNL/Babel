#!/bin/sh
#
# This shell script sets up Intel C/C++ with optimization
# for the CASC Linux cluster.

if ( uname -r | grep EL 2>&1 > /dev/null ) then
  export PATH=`echo "$PATH" | sed -e 's,/usr/casc/babel/apps/linux_el/bin,/usr/casc/babel/apps/linux_el_intel/bin:/usr/casc/babel/apps/linux_el/bin,g'`
  export LD_LIBRARY_PATH=`echo "$LD_LIBRARY_PATH" | sed -e 's,/usr/casc/babel/apps/linux_el/lib,/usr/casc/babel/apps/linux_el_intel/lib:/usr/casc/babel/apps/linux_el/lib,g'`
else
  export PATH=`echo "$PATH" | sed -e 's,/usr/casc/babel/apps/linux/bin,/usr/casc/babel/apps/linux_intel_compiler/bin:/usr/casc/babel/apps/linux/bin,g'`
  export LD_LIBRARY_PATH=`echo "$LD_LIBRARY_PATH" | sed -e 's,/usr/casc/babel/apps/linux/lib,/usr/casc/babel/apps/linux_intel_compiler/lib:/usr/casc/babel/apps/linux/lib,g'`
fi

if test `uname -m` == 'x86_64' ; then
  arch=intel64
else
  arch=ia32
fi

if test -f /usr/apps/intel/latest/bin/iccvars.sh ; then
  . /usr/apps/intel/latest/bin/iccvars.sh $arch
else
  . /usr/apps/intel/cc/9.1.045/setup.sh
fi

export CC='icc -std=gnu99'
export CFLAGS='-g -O -Wall -wd869,1419'
export CXX='icpc'
export CXXFLAGS='-g -O -Wall'
export CPP='gcc -E'
