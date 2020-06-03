#!/bin/sh
# a script to provide a pns.h with any prefix desired.

function gen() {
cat <<EOF
/* generated code. do not edit. */
/*===========================================================================
  Parsifal XML Parser namespace definition
  Copyright (c) 2007 Benjamin Allan

  This header allows parsifal to be moved to any prefix
  by rerunning makeNamespaceHeader.sh.

  We choose to do this by redefining the public symbols found in the compiled
  binary rather than changing every line in the library source.

  If _PNS is not defined in the compiler flags, this file does nothing.

  To use the prefix in parsifal development mode (defining new 
  public functions), do the following:

  (.) Complete the regular build with the options you need to obtain a 
  library (.a or .so) with the symbols that need prefixes added.

  (.) Regenerate pns.h with the new prefix, e.g.
	makeNamespaceHeader.sh libparsifal.a > include/libparsifal/pns.h babel_
   
  (.) To build with the prefix support:
      make clean; ./configure CFLAGS=-D_PNS {otheroptions}; make; make install

  Regular libparsifal and one in an alternate namespace should 
  be non-overlapping at link time with the exception of stricmp replacements. 
  We're assuming stricmp won't be changing across libparsifal versions.
  We're assuming libparsifal defines no functions starting with _.

  We should incorporate prefix into the library name, but this requires
  significant autotools regeneration to achieve.

  DISCLAIMER
  ----------

  This program is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  Merchantability or fitness for a particular purpose. Please use it AT
  YOUR OWN RISK.
===========================================================================*/

#ifndef PNS__H
#define PNS__H

#ifdef _PNS
#define PNS_PREFIX $2

EOF
# get public symbols, except those starting with _ and  stricmp and macroize
	sub="s/^.*/#define & $2&/g"
	nm $1 | grep ' T ' | \
		sed -e 's/.* T //g' |\
		grep -v '^_' |\
		grep -v stricmp |\
		sed -e "$sub"

cat <<EOF
#endif /* _PNS */
#endif /* PNS__H */
EOF
}

# begin main program

if ! test "x$#" = "x2"; then
	echo "$0: usage: $0 <inputlibrary> <prefix>"
	echo "generates pns.h to stdout from public functions in library."
	exit 1
fi

if ! test -e $1; then
	echo "$0: input library $1 not found."
        exit 1;
fi

if test "x$2" = "x"; then
 	echo "$0: prefix cannot be the empty string."
	exit 1;
fi

gen $*

exit 0
