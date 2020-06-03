#!/bin/sh

machine=`hostname`;

case $machine in 
  up*)
       export PATH=/usr/apps/babel/dev_2008/bin:${PATH}
       ;;
  lemur.*) 
	;;
  ingot*)
	;;
  tux316*)
	;;
  tux*)
        export PATH=/usr/casc/babel/apps/autotools_2011/bin:${PATH}
	;;
  ubgl*|udawn*)
        export PATH=$HOME/sw_ubgl/bin:${PATH}
	;;
  driftcreek*)
	;;
  *)
	;;
esac

echo "**** `which autoreconf` ****"
autoreconf || exit 1
(cd runtime/sidl ; mv -f babel_internal.h.in babel_internal.h.in.bak ; awk -f changeundef.awk babel_internal.h.in.bak > babel_internal.h.in && rm -f babel_internal.h.in.bak )
