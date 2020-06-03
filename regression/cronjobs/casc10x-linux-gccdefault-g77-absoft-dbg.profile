#!/bin/sh
# 
# Profile: casc-linux-gcc3.2.2-g77-absoft-dbg.profile
#
. setup/casc-linux-general.sh
. setup/casc-linux-gccdefault-dbg.sh
. setup/casc-linux-g77-dbg.sh
. setup/casc-linux-absoft-dbg.sh
. setup/casc-linux-final.sh
export PATH=/usr/casc/babel/apps/autotools_babel1_0/bin:${PATH}
export PACKAGE=babel10x
export SVNROOT=svn+ssh://${SVNUSER}www.cca-forum.org/svn/babel/branches/release-1-0-8-branch
export PROFILE_NAME="casc10x-linux-gcc${GCCVERSION}-g77-absoft-dbg"
export MAKE_FLAGS=""
