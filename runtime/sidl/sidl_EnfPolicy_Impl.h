/*
 * File:          sidl_EnfPolicy_Impl.h
 * Symbol:        sidl.EnfPolicy-v0.9.17
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463 trunk)
 * Release:       $Name:  $
 * Revision:      @(#) $Id: $
 * Description:   Server-side implementation for sidl.EnfPolicy
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

#ifndef included_sidl_EnfPolicy_Impl_h
#define included_sidl_EnfPolicy_Impl_h

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
#ifndef included_sidl_EnfPolicy_h
#include "sidl_EnfPolicy.h"
#endif
#ifndef included_sidl_RuntimeException_h
#include "sidl_RuntimeException.h"
#endif
/* DO-NOT-DELETE splicer.begin(sidl.EnfPolicy._hincludes) */
/* insert code here (include files) */
/* DO-NOT-DELETE splicer.end(sidl.EnfPolicy._hincludes) */

/*
 * Private data for class sidl.EnfPolicy
 */

struct sidl_EnfPolicy__data {
  /* DO-NOT-DELETE splicer.begin(sidl.EnfPolicy._data) */
  /*
   * This is a singleton class with all private data 
   * maintained in the source file.
   */
  int ignore;  /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(sidl.EnfPolicy._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct sidl_EnfPolicy__data*
sidl_EnfPolicy__get_data(
  sidl_EnfPolicy);

extern void
sidl_EnfPolicy__set_data(
  sidl_EnfPolicy,
  struct sidl_EnfPolicy__data*);

extern
void
impl_sidl_EnfPolicy__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidl_EnfPolicy__ctor(
  /* in */ sidl_EnfPolicy self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidl_EnfPolicy__ctor2(
  /* in */ sidl_EnfPolicy self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidl_EnfPolicy__dtor(
  /* in */ sidl_EnfPolicy self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

extern
void
impl_sidl_EnfPolicy_setEnforceAll(
  /* in */ enum sidl_ContractClass__enum contractClass,
  /* in */ sidl_bool clearStats,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidl_EnfPolicy_setEnforceNone(
  /* in */ sidl_bool clearStats,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidl_EnfPolicy_setPolicy(
  /* in */ enum sidl_ContractClass__enum contractClass,
  /* in */ enum sidl_EnforceFreq__enum enforceFreq,
  /* in */ int32_t interval,
  /* in */ double overheadLimit,
  /* in */ double appAvgPerCall,
  /* in */ double annealLimit,
  /* in */ sidl_bool clearStats,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_sidl_EnfPolicy_areEnforcing(
  /* out */ sidl_BaseInterface *_ex);

extern
int64_t
impl_sidl_EnfPolicy_getContractClass(
  /* out */ sidl_BaseInterface *_ex);

extern
int64_t
impl_sidl_EnfPolicy_getEnforceFreq(
  /* out */ sidl_BaseInterface *_ex);

extern
int32_t
impl_sidl_EnfPolicy_getSamplingInterval(
  /* out */ sidl_BaseInterface *_ex);

extern
double
impl_sidl_EnfPolicy_getOverheadLimit(
  /* out */ sidl_BaseInterface *_ex);

extern
double
impl_sidl_EnfPolicy_getAppAvgPerCall(
  /* out */ sidl_BaseInterface *_ex);

extern
double
impl_sidl_EnfPolicy_getAnnealLimit(
  /* out */ sidl_BaseInterface *_ex);

extern
char*
impl_sidl_EnfPolicy_getPolicyName(
  /* in */ sidl_bool useAbbrev,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidl_EnfPolicy_dumpStats(
  /* in */ const char* filename,
  /* in */ sidl_bool header,
  /* in */ const char* prefix,
  /* in */ sidl_bool compressed,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidl_EnfPolicy_startTrace(
  /* in */ const char* filename,
  /* in */ enum sidl_EnfTraceLevel__enum traceLevel,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_sidl_EnfPolicy_areTracing(
  /* out */ sidl_BaseInterface *_ex);

extern
char*
impl_sidl_EnfPolicy_getTraceFilename(
  /* out */ sidl_BaseInterface *_ex);

extern
int64_t
impl_sidl_EnfPolicy_getTraceLevel(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_sidl_EnfPolicy_endTrace(
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sidl_EnfPolicy_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar,
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_sidl_EnfPolicy_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar,
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/

/* DO-NOT-DELETE splicer.begin(_hmisc) */

/*
 * There is nothing needed here since the real, internal enforcement
 * work is delegated to the internal sidl.Enforcer class.
 */

/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
