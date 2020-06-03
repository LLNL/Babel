#!/bin/sh
# 
# Profile: casc-linux-gccdefault-g77-lahey-opt.profile
#
. setup/casc-linux-general.sh
. setup/casc-linux-gccdefault-opt.sh
. setup/casc-linux-g77-opt.sh
. setup/casc-linux-lahey-opt.sh
. setup/casc-linux-final.sh
export PROFILE_NAME="casc-linux-gcc${GCCVERSION}-g77-lahey-opt"
