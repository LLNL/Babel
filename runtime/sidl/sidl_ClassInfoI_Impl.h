/*
 * File:          sidl_ClassInfoI_Impl.h
 * Symbol:        sidl.ClassInfoI-v0.9.17
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463 trunk)
 * Release:       $Name:  $
 * Revision:      @(#) $Id: sidl_ClassInfoI_Impl.h 7465 2012-05-02 20:05:00Z adrian $
 * Description:   Server-side implementation for sidl.ClassInfoI
 * 
 * Copyright (c) 2000-2002, Lawrence Livermore National Security, LLC.
 * Produced at the Lawrence Livermore National Laboratory.
 * Written by the Components Team <components@llnl.gov>
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
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_sidl_ClassInfoI_Impl_h
#define included_sidl_ClassInfoI_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_sidl_BaseClass_h
#include "sidl_BaseClass.h"
#endif
#ifndef included_sidl_BaseInterface_h
#include "sidl_BaseInterface.h"
#endif
#ifndef included_sidl_ClassInfo_h
#include "sidl_ClassInfo.h"
#endif
#ifndef included_sidl_ClassInfoI_h
#include "sidl_ClassInfoI.h"
#endif
#ifndef included_sidl_RuntimeException_h
#include "sidl_RuntimeException.h"
#endif
/* DO-NOT-DELETE splicer.begin(sidl.ClassInfoI._hincludes) */
/* Put additional include files here... */
/* DO-NOT-DELETE splicer.end(sidl.ClassInfoI._hincludes) */

/*
 * Private data for class sidl.ClassInfoI
 */

struct sidl_ClassInfoI__data {
  /* DO-NOT-DELETE splicer.begin(sidl.ClassInfoI._data) */
  char *d_classname;
  char *d_classversion;
  int32_t d_IOR_major;
  int32_t d_IOR_minor;
  /* DO-NOT-DELETE splicer.end(sidl.ClassInfoI._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct sidl_ClassInfoI__data*
sidl_ClassInfoI__get_data(
  sidl_ClassInfoI);

extern void
sidl_ClassInfoI__set_data(
  sidl_ClassInfoI,
  struct sidl_ClassInfoI__data*);

extern
void
impl_sidl_ClassInfoI__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidl_ClassInfoI__ctor(
  /* in */ sidl_ClassInfoI self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidl_ClassInfoI__ctor2(
  /* in */ sidl_ClassInfoI self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidl_ClassInfoI__dtor(
  /* in */ sidl_ClassInfoI self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sidl_ClassInfoI_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern
void
impl_sidl_ClassInfoI_setName(
  /* in */ sidl_ClassInfoI self,
  /* in */ const char* name,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidl_ClassInfoI_setVersion(
  /* in */ sidl_ClassInfoI self,
  /* in */ const char* ver,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidl_ClassInfoI_setIORVersion(
  /* in */ sidl_ClassInfoI self,
  /* in */ int32_t major,
  /* in */ int32_t minor,
  /* out */ sidl_BaseInterface *_ex);

extern
char*
impl_sidl_ClassInfoI_getName(
  /* in */ sidl_ClassInfoI self,
  /* out */ sidl_BaseInterface *_ex);

extern
char*
impl_sidl_ClassInfoI_getVersion(
  /* in */ sidl_ClassInfoI self,
  /* out */ sidl_BaseInterface *_ex);

extern
char*
impl_sidl_ClassInfoI_getIORVersion(
  /* in */ sidl_ClassInfoI self,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sidl_ClassInfoI_fconnect_sidl_BaseInterface(const char* url, sidl_bool 
  ar, sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* Insert-Code-Here {_hmisc} (miscellaneous things) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
