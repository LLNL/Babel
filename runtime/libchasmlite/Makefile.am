##
## File:        Makefile.am 
## Package:     Babel makefiles
## Revision:    $Revision: 6353 $
## Modified:    $Date: 2008-04-14 11:25:31 -0700 (Mon, 14 Apr 2008) $
## Description: automake makefile to build the sidl runtime library
##
## Copyright (c) 2008, Lawrence Livemore National Security, LLC.
## Produced at the Lawrence Livermore National Laboratory.
## Written by the Components Team <components@llnl.gov>
## UCRL-CODE-2002-054
## All rights reserved.
## 
## This file is part of Babel. For more information, see
## http://www.llnl.gov/CASC/components/. Please read the COPYRIGHT file
## for Our Notice and the LICENSE file for the GNU Lesser General Public
## License.
## 
## This program is free software; you can redistribute it and/or modify it
## under the terms of the GNU Lesser General Public License (as published by
## the Free Software Foundation) version 2.1 dated February 1999.
## 
## This program is distributed in the hope that it will be useful, but
## WITHOUT ANY WARRANTY; without even the IMPLIED WARRANTY OF
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the terms and
## conditions of the GNU Lesser General Public License for more details.
## 
## You should have received a copy of the GNU Lesser General Public License
## along with this program; if not, write to the Free Software Foundation,
## Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

if SUPPORT_FORTRAN90
lib_LTLIBRARIES=libchasmlite.la
else
if SUPPORT_FORTRAN03
lib_LTLIBRARIES=libchasmlite.la
else
all:
	@mkdir -p .libs # otherwise all the python babel-stamp rules would break down
endif
else
all:
	@mkdir -p .libs # otherwise all the python babel-stamp rules would break down
endif

AM_LDFLAGS = -no-undefined -release $(VERSION)

libchasmlite_la_LDFLAGS=$(AM_LDFLAGS)
nodist_libchasmlite_la_SOURCES=chasmlite_config.h 
libchasmlite_la_SOURCES=Absoft.c Absoft_dv.h Absoft.h \
	Alpha.c Alpha_dv.h Alpha.h \
	CompilerCharacteristics.c CompilerCharacteristics.h \
	Cray.c Cray_dv.h Cray.h \
	F90ArrayDataType.h \
	G95.c G95_dv.h G95.h \
	GNU.c GNU_dv.h GNU.h \
	IBMXL.c IBMXL_dv.h IBMXL.h \
	Intel_7.c Intel_7_dv.h Intel_7.h \
	Intel.c Intel_dv.h Intel.h \
	Lahey.c Lahey_dv.h Lahey.h \
	MIPSpro.c MIPSpro_dv.h MIPSpro.h \
	NAG.c NAG_dv.h NAG.h \
	PGI.c PGI_dv.h PGI.h \
	SUNWspro.c SUNWspro_dv.h SUNWspro.h \
	pathscale.h pathscale_dv.h pathscale.c
