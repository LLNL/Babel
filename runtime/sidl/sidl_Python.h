/*
 * File:        sidl_Python.h
 * Copyright:   (c) 2001 Lawrence Livermore National Security, LLC
 * Revision:    @(#) $Revision: 7188 $
 * Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
 * Description: API to initialize a Python language interpretter
 *
 * Copyright (c) 2000-2001, Lawrence Livermore National Security, LLC
 * Produced at the Lawrence Livermore National Laboratory.
 * Written by the Components Team <components@llnl.gov>
 * UCRL-CODE-2002-054
 * All rights reserved.
 * 
 * This file is part of Babel. For more information, see
 * http://www.llnl.gov/CASC/components/. Please read the COPYRIGHT file
 * for Our Notice and the LICENSE file for the GNU Lesser General Public
 * License.
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License (as published by
 * the Free Software Foundation) version 2.1 dated February 1999.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the IMPLIED WARRANTY OF
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the terms and
 * conditions of the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

#ifndef included_sidl_Python_h
#define included_sidl_Python_h

#include "babel_config.h"
#ifdef PYTHON_DISABLED
#error This installation of Babel Runtime was configured without Python support.
#endif

#ifdef __cplusplus
extern "C" { /* } */
#endif
/**
 * Initialize a Python language interpretter in the global symbol space.
 * Python modules expect the Python symbols to be in the global symbol
 * space.
 */
void sidl_Python_Init(void);

void sidl_Python_IncGlobalRef(void);

void sidl_Python_DecGlobalRef(void);

void sidl_Python_LogGILEnsure(const char *func,
                                 const char *file,
                                 const int  line,
                                 const int state);

void sidl_Python_LogGILRelease(const char *func,
                                  const char *file,
                                  const int   line,
                                  const int   state);

void sidl_Python_LogUnlock(const char *func,
                           const char *file,
                           const int   line);

void sidl_Python_LogRelock(const char *func,
                           const char *file,
                           const int   line);

void sidl_Python_LogControlling(const char *func,
                                const char *file,
                                const int line);

#ifndef SIDL_PYTHON_THREAD_DEBUGGING
#define sidl_Python_LogGILEnsure(i,j,k,l)
#define sidl_Python_LogGILRelease(i,j,k,l)
#define sidl_Python_LogUnlock(i,j,k)
#define sidl_Python_LogRelock(i,j,k)
#define sidl_Python_LogControlling(i,j,k)
#endif
                             
#ifdef __cplusplus
}
#endif
#endif
