#! /bin/sh
## File:        runCxx.sh
## Package:     Babel cca hello examples
## Copyright:   (c) 2000-2001 Lawrence Livermore National Security, LLC
## Revision:    $Revision: 6171 $
## Modified:    $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
## Description: script to run F77 dynamically loading CCA hello implementations
##

SIDL_DLL_PATH="libDriver.scl;../libdecaf/libdecaf.scl;../hello-server/libHelloServer.scl;../hello-client/libHelloClient.scl"
export SIDL_DLL_PATH

./HelloDriver

./HelloDriver2
