#!/bin/sh
# 
# Profile: casc-linux-icc-ifc-dbg.profile
#
. setup/casc-linux-general.sh
. setup/casc-linux-icc-dbg.sh
. setup/casc-linux-ifc-dbg.sh
. setup/casc-linux-final.sh
export PATH=/usr/casc/babel/apps/autotools_2009/bin:${PATH}
export PACKAGE=babel14x
export SVNROOT=svn+ssh://${SVNUSER}www.cca-forum.org/svn/babel/branches/release-1-4-0-branch
export PROFILE_NAME="casc14x-linux-icc-ifc-dbg"
export MAKE_FLAGS=""
