#! /bin/sh
## File:        runAll.sh
## Package:     Babel hello examples
## Copyright:   (c) 2000-2001 Lawrence Livermore National Security, LLC
## Revision:    $Revision: 6171 $
## Modified:    $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
## Description: script to run C++ dynamically loading hello implementations
##


BC="/bin/sh ../../../bin/babel-config"
echo "Hello from C..."
SIDL_DLL_PATH="../libC/libImpl.scl" ; export SIDL_DLL_PATH
./runAll

echo "Hello from C++..."
SIDL_DLL_PATH="../libCxx/libImpl.scl" ; export SIDL_DLL_PATH
./runAll

if $BC --with-f77; then
echo "Hello from Fortran77..."
SIDL_DLL_PATH="../libF77/libImpl.scl" ; export SIDL_DLL_PATH
./runAll
else
echo "f77 support not enabled"
fi
