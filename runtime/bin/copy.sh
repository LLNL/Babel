#! /bin/sh
## File:        copy.sh
## Package:     Babel binary
## Copyright:   (c) 2000-2001 Lawrence Livermore National Security, LLC
## Revision:    $Revision: 6171 $
## Modified:    $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
## Description: copy files for build: copy.sh SRCDIR DSTDIR files...

srcdir=$1 ; shift
dstdir=$1 ; shift

if test "X$srcdir" != "X$dstdir"; then
  for f in $*; do
    echo cp $srcdir/$f $dstdir
    cp $srcdir/$f $dstdir
  done
fi

for f in $*; do
  echo chmod +w $dstdir/$f
  chmod +w $dstdir/$f
done

exit 0
