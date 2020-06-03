#!/bin/sh

wd=`pwd`
if test "x$1" = "x" ; then prog=libtool
else prog=$1; fi

find $wd -name $prog -exec \
    sed -i1 -e 's|^export_dynamic_flag_spec=.*$|export_dynamic_flag_spec=""|g' \
        -e 's|^whole_archive_flag_spec=.$|whole_archive_flag_spec=""|g' \
    -e 's|^pic_flag=.*$|pic_flag=" -DPIC -qpic"|g' \
    -e 's|^archive_cmds="\(....\).*|archive_cmds="\1 \\$libobjs \\$deplibs \\$compiler_flags -qmkshrobj -G -e \\$soname  -o \\$lib"|g' \
    -e 's|^hardcode_libdir_flag_spec=.*$|hardcode_libdir_flag_spec="-R\\$libdir -L\\$libdir"|g' {} \; -print

## fix *.make stubs in include/regression

prog="*.make"
find ./regression/include -name $prog -exec \
    sed -i1 -e 's|^runAll_LDFLAGS=.*$|runAll_LDFLAGS=""|g' \
    {} \; -print

# Regression fix for running (individual) tests on BG/P

prog=runAll

find `pwd` -type d \( -name .libs \) -prune -o -name "runAll" -exec \
    sed -il -e 's|exec "$progdir/$program" ${1+"$@"}|if [ "X${PYTHONPATH}" != "X" ]; then \
	  ADDITIONAL_ARG="PYTHONPATH=${PYTHONPATH}" \
      fi \
      cobalt-mpirun -mode vn -np 1 -verbose 2 -cwd `pwd` -env "LD_LIBRARY_PATH=${LD_LIBRARY_PATH} SIDL_DLL_PATH=${SIDL_DLL_PATH} $ADDITIONAL_ARG" "$progdir/$program" ${1+"$@"}|' {} \; -print

find `pwd` -type d \( -name .svn \) -prune -o -name "runPy2*.in" -prune -o -name "runPy2*" -exec \
    sed -il -e 's|^$PYTHON \(.*\)|cobalt-mpirun -mode vn -np 1 -verbose 2 -cwd `pwd` -env "LD_LIBRARY_PATH=${LD_LIBRARY_PATH} SIDL_DLL_PATH=${SIDL_DLL_PATH} PYTHONPATH=${PYTHONPATH}" $PYTHON \1|' {} \; -print
