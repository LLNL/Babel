#!/bin/sh

# Regression fix for getting examples in examples/hello to run on the BG/P Surveyor
# Only care about Python -> C, C++ 

find `pwd`/examples/hello -type d \( -name .libs \) -prune -o -name "runAll" -exec \
    sed -il -e 's|exec "$progdir/$program" ${1+"$@"}|if [ "X${PYTHONPATH}" != "X" ]; then \
          ADDITIONAL_ARG="PYTHONPATH=${PYTHONPATH}" \
      fi \
      cobalt-mpirun -mode vn -np 1 -verbose 2 -cwd `pwd` -env "SIDL_DEBUG_OPEN=1 LD_LIBRARY_PATH=${LD_LIBRARY_PATH} SIDL_DLL_PATH=${SIDL_DLL_PATH} $ADDITIONAL_ARG" "$progdir/$program" ${1+"$@"}|' {} \; -print

find `pwd`/examples/hello -type d \( -name .svn \) -prune -o -name "runPy2C*.in" -prune -o -name "runPy2C*" -exec \
    sed -il -e 's|^python helloclient.py.*$|cobalt-mpirun -mode vn -np 1 -verbose 2 -cwd `pwd` -env "SIDL_DEBUG_OPEN=1 LD_LIBRARY_PATH=${LD_LIBRARY_PATH} SIDL_DLL_PATH=${SIDL_DLL_PATH} PYTHONPATH=${PYTHONPATH}" /bgsys/drivers/ppcfloor/gnu-linux/bin/python helloclient.py|' \
            -e 's|^PYTHONPATH=.*$|PYTHONPATH="../../../runtime/python:/usr/lib64:/soft/apps/python/python-2.6-cnk-gcc/numpy-1.3.0/lib/python2.6/site-packages"|g' \
            -e 's|^PYTHONLIB=.*$|PYTHONLIB="../../../runtime/sidl/.libs:../../../runtime/libchasmlite/.libs"|g' {} \; -print
