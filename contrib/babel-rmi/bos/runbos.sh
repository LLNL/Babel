#!/bin/bash
BABEL_LIB="`babel-config --libdir`"

if [ ! -d $BABEL_LIB ] ; then
    echo "can't find $BABEL_LIB";
    exit 1
fi

if [ ! "$#" == "2" ] ; then
    echo "usage: runbos.sh <libdir> <subdir>"
    exit 1
fi

SIDL_DLL_PATH="$BABEL_LIB/libsidl.scl;$BABEL_LIB/libsidlx.scl"
for d in `find $1 -type d -name $2` ; do
    for lib in $d/*.scl ; do
        if [ -f $lib ] ; then
            SIDL_DLL_PATH="$SIDL_DLL_PATH;$lib";
        fi
    done
done

export SIDL_DLL_PATH
echo "SIDL_DLL_PATH=\"$SIDL_DLL_PATH\""
./bos_server 9999
exit $?
