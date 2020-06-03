#!/bin/sh

# Regression fix for running (individual) tests on BG/P

find `pwd` -type d \( -name .libs \) -prune -o -name "runAll" -exec \
    sed -il -e 's|exec "$progdir/$program" ${1+"$@"}|if [ "X${PYTHONPATH}" != "X" ]; then \
          ADDITIONAL_ARG="PYTHONPATH=${PYTHONPATH}" \
      fi \
      cobalt-mpirun -mode vn -np 1 -verbose 2 -cwd `pwd` -env "SIDL_DEBUG_OPEN=1 LD_LIBRARY_PATH=${LD_LIBRARY_PATH} SIDL_DLL_PATH=${SIDL_DLL_PATH} $ADDITIONAL_ARG" "$progdir/$program" ${1+"$@"}|' {} \; -print

find `pwd` -type d \( -name .svn \) -prune -o -name "runPy2*.in" -prune -o -name "runPy2*" -exec \
    sed -il -e 's|^$PYTHON \(.*\)|cobalt-mpirun -mode vn -np 1 -verbose 2 -cwd `pwd` -env "SIDL_DEBUG_OPEN=1 LD_LIBRARY_PATH=${LD_LIBRARY_PATH} SIDL_DLL_PATH=${SIDL_DLL_PATH} PYTHONPATH=${PYTHONPATH}" $PYTHON \1|' {} \; -print
