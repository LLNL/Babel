#!/usr/local/bin/python
#
# File:        helloclient
# Copyright:   (c) 2001 Lawrence Livermore National Security, LLC
# Revision:    @(#) $Revision: 6171 $
# Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
# Description: Simple Hello World Python client 
#

#import sidl.BaseClass
import Hello.World

if __name__ == '__main__':
  h = Hello.World.World ()
  print h.getMsg ()
