#!/bin/sh
#
# $Id: fix_timestamps.sh 5991 2007-04-26 15:23:51Z epperly $
# Unfortunately, when you "cvs commit" after running ./autotools_rebuild.sh
# the timestamps end up screwed up. When you run "make", it reruns all
# kinds of autotools tools because it thinks files are out of date.
#
# This script tries to fix the time stamps.

touch -c acinclude.m4
touch -c runtime/m4/*.m4
touch -c runtime/acinclude.m4
touch -c runtime/configure.ac configure.ac 
touch -c aclocal.m4
touch -c runtime/aclocal.m4
touch -c runtime/configure configure 
touch -c `find . \( -name .svn -prune \) -o \( -name "Makefile.am" -print \)`
touch -c `find . \( -name .svn -prune \) -o \( -name "Makefile.in" -print \)`
touch -c runtime/sidl/ignore_me.h.in runtime/sidl/babel_internal.h.in
