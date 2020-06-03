#!/bin/sh
# 
# Profile: casc-linux-gcc2.96-g77-lahey-dbg.profile
#
. setup/casc-linux-general.sh
. setup/casc-linux-gcc2.96-dbg.sh
. setup/casc-linux-g77-dbg.sh
. setup/casc-linux-lahey-dbg.sh
. setup/casc-linux-final.sh
export PACKAGING_BUILDDIR=/usr/casc_scratch/babel
export PROFILE_NAME="casc-linux-gcc${GCCVERSION}-g77-lahey-dbg"
