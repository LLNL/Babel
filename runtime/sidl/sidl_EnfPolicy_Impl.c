/*
 * File:          sidl_EnfPolicy_Impl.c
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

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "sidl.EnfPolicy" (version 0.9.17)
 * 
 * <code>EnfPolicy</code> maintains the current interface
 * contract enforcement policy.
 */

#include "sidl_EnfPolicy_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(sidl.EnfPolicy._includes) */
#include <math.h>
#include <stdlib.h>
#include <time.h>
#include <sys/time.h>
#include <stdio.h>
#include "sidl_String.h"

/*
 * Must have handle on singleton that actually controls 
 * enforcement.  This is necessary to pass policy options
 * and to dispatch request made through this public
 * interface.
 */
#include "sidl_Enforcer.h"

/*
 **********************************************************************
 *                               MACRO(S)                             *
 **********************************************************************
 */

#ifndef NULL
#define NULL 0
#endif
#ifndef TRUE
#define TRUE 1
#endif
#ifndef FALSE
#define FALSE 0
#endif

#define DIFFT(T2, T1) \
  1.0e6*(double)((T2).tv_sec - (T1).tv_sec) \
  + ((T2).tv_usec-(T1).tv_usec)

/*
 **********************************************************************
 *                            PRIVATE DATA                            *
 **********************************************************************
 */

/*
 * ------------------------------------------------------------------ *
 * Attributes of the singleton instance of the policy.                *
 *                                                                    *
 * This class serves as the front-end for sidl.Enforcer.  Since       *
 * invalid user settings can get overriden with defaults, all of the  *
 * logic and attributes are actually maintained in sidl.Enforcer.     *
 * However, user-friendly policy name conversions, for instance,      *
 * remain in this class (since they're not readily added to the       *
 * enumeration class.                                                 *
 * ------------------------------------------------------------------ *
 */

/*
 * The following MUST match the ordering in sidl_ContractClass__enum.
 */
static const char* S_CONTRACT_CLASS_ABBREV[18] = {
  "All", 
  "Constant", 
  "Cubic", 
  "Invariants", 
  "InvPost",
  "InvPre",
  "Linear", 
  "MC", 
  "Post",
  "Pre", 
  "PrePost", 
  "Quadratic", 
  "Quartic", 
  "Quintic", 
  "Results", 
  "Septic", 
  "Sextic", 
  "SE",
};

static const char* S_CONTRACT_CLASS_ABBREV_ABBREV[18] = {
  "", 
  "Con", 
  "Cub", 
  "I", 
  "IPost",
  "IPre",
  "L", 
  "MC", 
  "Post",
  "Pre", 
  "PP", 
  "Quad", 
  "Quar", 
  "Quin", 
  "R", 
  "Sept",
  "Sext",
  "SE",
};

static const char* S_CONTRACT_CLASS_NAME[18] = {
  "All Classifications", 
  "Constant-time", 
  "Cubic-time", 
  "Invariants", 
  "Invariants+Postconditions",
  "Invariants+Preconditions", 
  "Linear-time", 
  "Method Calls", 
  "Postconditions",
  "Preconditions", 
  "Preconditions+Postconditions", 
  "Quadratic-time", 
  "Quartic-time", 
  "Quintic-time", 
  "Results", 
  "Septic-time", 
  "Sextic-time", 
  "Simple Expressions",
};

static const int S_CONTRACT_CLASS_NAME_MIN_IND = 0;
static const int S_CONTRACT_CLASS_NAME_MAX_IND = 17;

static const char* S_FREQ_ABBREV[7] = {
  "Never",
  "Always",
  "AF",
  "AT",
  "Periodic",
  "Random",
  "SA",
};

static const char* S_FREQ_NAME[7] = {
  "Never",
  "Always",
  "Adaptive Fit",
  "Adaptive Timing",
  "Periodic",
  "Random",
  "Simulated Annealing",
};

static const int S_FREQ_NAME_MIN_IND = 0;
static const int S_FREQ_NAME_MAX_IND = 6;

/*
 **********************************************************************
 *                         PRIVATE METHODS                            *
 **********************************************************************
 */

/**
 * Tells sidl.Enforcer to reset (or re-initialize) its settings.
 */
#undef __FUNC__
#define __FUNC__ "sidl_EnfPolicy_reset"
static void 
sidl_EnfPolicy_reset(void) {
  /*
   * Dispatch the request to sidl.Enforcer since it maintains 
   * Enforcement Policy business rules.
   */
  sidl_Enforcer_reset();
  return;
}


/**
 * Returns the name associated with the ContractClass enumeration.
 */
#undef __FUNC__
#define __FUNC__ "sidl_EnfPolicy_getContractClassName"
static char* 
sidl_EnfPolicy_getContractClassName(sidl_bool useAbbrev) {
  const char*                   name     = NULL;
  enum sidl_ContractClass__enum conClass = sidl_Enforcer_getContractClass();
  enum sidl_EnforceFreq__enum   enfFreq  = sidl_Enforcer_getEnforceFreq();
  char buffer[128];

  if (  (S_CONTRACT_CLASS_NAME_MIN_IND <= conClass)
     && (conClass <= S_CONTRACT_CLASS_NAME_MAX_IND)   ) {
    if (useAbbrev) {
      if (enfFreq != sidl_EnforceFreq_ADAPTFIT) {
        name = S_CONTRACT_CLASS_ABBREV[conClass];
      } else {
        name = S_CONTRACT_CLASS_ABBREV_ABBREV[conClass];
      } 
    } else {
      name = S_CONTRACT_CLASS_NAME[conClass];
    } 
  } else {
    sprintf(buffer, "Unknown (%d)", conClass);
    name = buffer;
  }
    
  return sidl_String_strdup(name);
}

/**
 * Returns the name associated with the EnforceFreq enumeration.
 */
#undef __FUNC__
#define __FUNC__ "sidl_EnfPolicy_getEnforceFreqName"
static char* 
sidl_EnfPolicy_getEnforceFreqName(sidl_bool useAbbrev) {
  const char*                 name    = NULL;
  enum sidl_EnforceFreq__enum enfFreq = sidl_Enforcer_getEnforceFreq();
  char buffer[128];

  if (  (S_FREQ_NAME_MIN_IND     <= enfFreq)
     && (enfFreq <= S_FREQ_NAME_MAX_IND)   ) {
    if (useAbbrev) {
      name = S_FREQ_ABBREV[enfFreq];
    } else {
      name = S_FREQ_NAME[enfFreq];
    } 
  } else {
    sprintf(buffer, "Unknown (%d)", enfFreq);
    name = buffer;
  }
    
  return sidl_String_strdup(name);
}

/*
 **********************************************************************
 *                          PUBLIC METHODS                            *
 **********************************************************************
 */

/* DO-NOT-DELETE splicer.end(sidl.EnfPolicy._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_EnfPolicy__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_EnfPolicy__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.EnfPolicy._load) */

  /*
   * Ensure the policy is initialized.
   */
  sidl_EnfPolicy_reset();

  return;
    /* DO-NOT-DELETE splicer.end(sidl.EnfPolicy._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_EnfPolicy__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_EnfPolicy__ctor(
  /* in */ sidl_EnfPolicy self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.EnfPolicy._ctor) */

  /*
   * This is a static (singleton) class since all methods in the specification
   * are declared static.  Consequently, there is nothing to be done here.
   */

  return;
    /* DO-NOT-DELETE splicer.end(sidl.EnfPolicy._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_EnfPolicy__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_EnfPolicy__ctor2(
  /* in */ sidl_EnfPolicy self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.EnfPolicy._ctor2) */

  /*
   * This is a static (singleton) class since all methods in the specification
   * are declared static.  Consequently, there is nothing to be done here.
   */

  return;
    /* DO-NOT-DELETE splicer.end(sidl.EnfPolicy._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_EnfPolicy__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_EnfPolicy__dtor(
  /* in */ sidl_EnfPolicy self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.EnfPolicy._dtor) */

  /*
   * This is a static (singleton) class since all methods in the
   * specification are declared static.  Consequently, there is 
   * nothing to be done here.
   */

  return;
    /* DO-NOT-DELETE splicer.end(sidl.EnfPolicy._dtor) */
  }
}

/*
 * Sets the enforcement policy to always check the specified
 * type(s) of contracts.  This is equivalent to calling
 * setPolicy() with ALWAYS as the enforcement frequency
 * and the specified (or default) contract class.
 * 
 * @param contractClass  Contract classification
 * [Default = ALLCLASSES]
 * @param clearStats      TRUE if enforcement statistics are to be
 * cleared; FALSE otherwise.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_EnfPolicy_setEnforceAll"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_EnfPolicy_setEnforceAll(
  /* in */ enum sidl_ContractClass__enum contractClass,
  /* in */ sidl_bool clearStats,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.EnfPolicy.setEnforceAll) */
  /*
   * Since the business logic associated with enforcement options
   * is maintained solely in sidl.Enforcer, go ahead and dispatch
   * directly to it.
   */
  sidl_Enforcer_init(contractClass, sidl_EnforceFreq_ALWAYS, (int32_t)0,
       (double)0.0, (double)0.0, (double)0.0, TRUE);

  return;
    /* DO-NOT-DELETE splicer.end(sidl.EnfPolicy.setEnforceAll) */
  }
}

/*
 * Sets the policy options to disable all contract enforcement.
 * This is equivalent to calling setPolicy() with NEVER as the
 * enforcement frequency.
 * 
 * @param clearStats  TRUE if enforcement statistics are to be
 * cleared; FALSE otherwise.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_EnfPolicy_setEnforceNone"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_EnfPolicy_setEnforceNone(
  /* in */ sidl_bool clearStats,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.EnfPolicy.setEnforceNone) */
  /*
   * Since the business logic associated with enforcement options
   * is maintained solely in sidl.Enforcer, go ahead and dispatch
   * directly to it.
   */
  sidl_Enforcer_init(sidl_ContractClass_ALLCLASSES, sidl_EnforceFreq_NEVER, 
                     (int32_t)0, (double)0.0, (double)0.0, (double)0.0, TRUE);

  return;
    /* DO-NOT-DELETE splicer.end(sidl.EnfPolicy.setEnforceNone) */
  }
}

/*
 * Sets enforcement policy and options.  This method should be
 * invoked directly to avoid the default enforcement behavior.
 * 
 * @param contractClass  Contract classification
 * [Default = ALLCLASSES]
 * @param enforceFreq    Enforcement frequency
 * [Default = ALWAYS]
 * @param interval       Sampling interval representing the
 * period (for PERIODIC) or maximum
 * random number/window (for RANDOM)
 * [Default = 0 if negative specified]
 * @param overheadLimit  Limit on performance overhead [0.0 .. 1.0)
 * [Default = 0.0 (or 0%) if negative]
 * @param appAvgPerCall  Average extra, application-specific
 * execution time, normalized by calls
 * to annotated methods
 * [Default = 0.0 if negative]
 * @param annealLimit    Limit on simulated annealing function
 * to ensure its termination
 * (0.0 .. 2.72]
 * [Default = 2.72 if negative specified]
 * @param clearStats      TRUE if enforcement statistics are to be
 * cleared; FALSE otherwise.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_EnfPolicy_setPolicy"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_EnfPolicy_setPolicy(
  /* in */ enum sidl_ContractClass__enum contractClass,
  /* in */ enum sidl_EnforceFreq__enum enforceFreq,
  /* in */ int32_t interval,
  /* in */ double overheadLimit,
  /* in */ double appAvgPerCall,
  /* in */ double annealLimit,
  /* in */ sidl_bool clearStats,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.EnfPolicy.setPolicy) */
    /*
     * Since the business logic associated with enforcement options
     * is maintained solely in sidl.Enforcer, go ahead and dispatch
     * directly to it.
     */
    sidl_Enforcer_init(contractClass, enforceFreq, interval, overheadLimit, 
		       appAvgPerCall, annealLimit, clearStats);
    return;
    /* DO-NOT-DELETE splicer.end(sidl.EnfPolicy.setPolicy) */
  }
}

/*
 * Returns TRUE if contract enforcement is enabled; FALSE otherwise.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_EnfPolicy_areEnforcing"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_sidl_EnfPolicy_areEnforcing(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.EnfPolicy.areEnforcing) */
  return sidl_Enforcer_areEnforcing();
    /* DO-NOT-DELETE splicer.end(sidl.EnfPolicy.areEnforcing) */
  }
}

/*
 * Returns the contract classification policy option.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_EnfPolicy_getContractClass"

#ifdef __cplusplus
extern "C"
#endif
int64_t
impl_sidl_EnfPolicy_getContractClass(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.EnfPolicy.getContractClass) */
  return sidl_Enforcer_getContractClass();
    /* DO-NOT-DELETE splicer.end(sidl.EnfPolicy.getContractClass) */
  }
}

/*
 * Returns the enforcement frequency policy option.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_EnfPolicy_getEnforceFreq"

#ifdef __cplusplus
extern "C"
#endif
int64_t
impl_sidl_EnfPolicy_getEnforceFreq(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.EnfPolicy.getEnforceFreq) */
  return sidl_Enforcer_getEnforceFreq();
    /* DO-NOT-DELETE splicer.end(sidl.EnfPolicy.getEnforceFreq) */
  }
}

/*
 * Returns the interval for PERIODIC (i.e., the interval) or
 * RANDOM (i.e., the maximum random number).  Returns 0 by default.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_EnfPolicy_getSamplingInterval"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_sidl_EnfPolicy_getSamplingInterval(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.EnfPolicy.getSamplingInterval) */
  return sidl_Enforcer_getSamplingInterval();
    /* DO-NOT-DELETE splicer.end(sidl.EnfPolicy.getSamplingInterval) */
  }
}

/*
 * Returns the desired enforcement overhead limit for
 * performance-driven frequency options (i.e., ADAPTFIT,
 * ADAPTTIMING, and SIMANNEAL).  Returns 0.0 by default.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_EnfPolicy_getOverheadLimit"

#ifdef __cplusplus
extern "C"
#endif
double
impl_sidl_EnfPolicy_getOverheadLimit(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.EnfPolicy.getOverheadLimit) */
  return sidl_Enforcer_getOverheadLimit();
    /* DO-NOT-DELETE splicer.end(sidl.EnfPolicy.getOverheadLimit) */
  }
}

/*
 * Returns the average assumed execution time associated
 * with the program or application.  Returns 0.0 by default.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_EnfPolicy_getAppAvgPerCall"

#ifdef __cplusplus
extern "C"
#endif
double
impl_sidl_EnfPolicy_getAppAvgPerCall(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.EnfPolicy.getAppAvgPerCall) */
  return sidl_Enforcer_getAppAvgPerCall();
    /* DO-NOT-DELETE splicer.end(sidl.EnfPolicy.getAppAvgPerCall) */
  }
}

/*
 * Returns the annealing limit for SIMANNEAL enforcement
 * frequency option.  Returns 0.0 by default.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_EnfPolicy_getAnnealLimit"

#ifdef __cplusplus
extern "C"
#endif
double
impl_sidl_EnfPolicy_getAnnealLimit(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.EnfPolicy.getAnnealLimit) */
  return sidl_Enforcer_getAnnealLimit();
    /* DO-NOT-DELETE splicer.end(sidl.EnfPolicy.getAnnealLimit) */
  }
}

/*
 * Returns the name, or description, of the enforcement policy.
 * The caller is responsible for calling sidl_String_free()
 * on the name when done with it.
 * 
 * @param useAbbrev   TRUE if the abbreviated name is to be
 * returned.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_EnfPolicy_getPolicyName"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_sidl_EnfPolicy_getPolicyName(
  /* in */ sidl_bool useAbbrev,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.EnfPolicy.getPolicyName) */
  char* name = NULL;
  char* freq = NULL;
  char* type = NULL;

  enum sidl_ContractClass__enum conClass = sidl_Enforcer_getContractClass();
  enum sidl_EnforceFreq__enum   enfFreq  = sidl_Enforcer_getEnforceFreq();

  if (  (conClass == sidl_ContractClass_ALLCLASSES) 
     || (enfFreq  == sidl_EnforceFreq_NEVER) ) {
    name = sidl_EnfPolicy_getEnforceFreqName(useAbbrev);
  } else if (enfFreq == sidl_EnforceFreq_ALWAYS) {
    name = sidl_EnfPolicy_getContractClassName(useAbbrev);
  } else {
    type = sidl_EnfPolicy_getContractClassName(useAbbrev);
    freq = sidl_EnfPolicy_getEnforceFreqName(useAbbrev);
    if (useAbbrev) {
      name = sidl_String_concat2(freq, type);
    } else {
      name = sidl_String_concat3(freq, "-", type);
    }
    sidl_String_free(type);
    sidl_String_free(freq);
  }

  return name;
    /* DO-NOT-DELETE splicer.end(sidl.EnfPolicy.getPolicyName) */
  }
}

/*
 * Prints statistics data to the file with the specified name.
 * The file is opened (for append) and closed on each call.
 * 
 * @param filename   Name of the file to which the statistics
 * data should be written.
 * @param header     TRUE if the header line is to be printed
 * prior to the statistics line (for compressed
 * output only).
 * @param prefix     String description for identifying information,
 * if any, intended to preceed the statistics
 * data.  Useful for distinguishing between
 * different objects, for example.
 * @param compressed TRUE if the enforcer state is to be dumped
 * on a single line with semi-colon separators
 * between fields.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_EnfPolicy_dumpStats"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_EnfPolicy_dumpStats(
  /* in */ const char* filename,
  /* in */ sidl_bool header,
  /* in */ const char* prefix,
  /* in */ sidl_bool compressed,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.EnfPolicy.dumpStats) */
  /*
   * Dispatch this to sidl.Enforcer since it maintains the 
   * statistics.
   */
  sidl_Enforcer_dumpStats(filename, header, prefix, compressed);
  return;
    /* DO-NOT-DELETE splicer.end(sidl.EnfPolicy.dumpStats) */
  }
}

/*
 * Starts enforcement trace file generation.
 * 
 * @param filename    Name of the destination trace file.
 * @param traceLevel  Level of trace timing and reporting required.
 * [Default = NONE]
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_EnfPolicy_startTrace"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_EnfPolicy_startTrace(
  /* in */ const char* filename,
  /* in */ enum sidl_EnfTraceLevel__enum traceLevel,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.EnfPolicy.startTrace) */
  /*
   * Dispatch this to sidl.Enforcer since it manages the actual process
   * so may have to override invalid values.
   */
  sidl_Enforcer_startTrace(filename, traceLevel);
  return;
    /* DO-NOT-DELETE splicer.end(sidl.EnfPolicy.startTrace) */
  }
}

/*
 * Returns TRUE if contract enforcement tracing is enabled;
 * FALSE otherwise.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_EnfPolicy_areTracing"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
impl_sidl_EnfPolicy_areTracing(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.EnfPolicy.areTracing) */
  /*
   * Dispatch this to sidl.Enforcer since it manages the actual process
   * so may have overriden invalid values.
   */
  return sidl_Enforcer_areTracing();
    /* DO-NOT-DELETE splicer.end(sidl.EnfPolicy.areTracing) */
  }
}

/*
 * Returns the name of the trace file.  If one was not provided,
 * the default name is returned.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_EnfPolicy_getTraceFilename"

#ifdef __cplusplus
extern "C"
#endif
char*
impl_sidl_EnfPolicy_getTraceFilename(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.EnfPolicy.getTraceFilename) */
  /*
   * Dispatch this to sidl.Enforcer since it manages the actual process
   * so may have overriden invalid values.
   */
  return sidl_Enforcer_getTraceFilename();
    /* DO-NOT-DELETE splicer.end(sidl.EnfPolicy.getTraceFilename) */
  }
}

/*
 * Returns the level of enforcement tracing.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_EnfPolicy_getTraceLevel"

#ifdef __cplusplus
extern "C"
#endif
int64_t
impl_sidl_EnfPolicy_getTraceLevel(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.EnfPolicy.getTraceLevel) */
  /*
   * Dispatch this to sidl.Enforcer since it manages the actual process
   * so may have overriden invalid values.
   */
  return sidl_Enforcer_getTraceLevel();
    /* DO-NOT-DELETE splicer.end(sidl.EnfPolicy.getTraceLevel) */
  }
}

/*
 * Terminates enforcement trace file generation.  Takes a final
 * timestamp and logs the remaining trace information.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_EnfPolicy_endTrace"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_EnfPolicy_endTrace(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.EnfPolicy.endTrace) */
  /*
   * Dispatch this to sidl.Enforcer since it manages the actual process.
   */
  sidl_Enforcer_endTrace();
  return;
    /* DO-NOT-DELETE splicer.end(sidl.EnfPolicy.endTrace) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */

/*
 * There is nothing needed here since the real, internal enforcement
 * work is delegated to the internal sidl.Enforcer class.
 */

/* DO-NOT-DELETE splicer.end(_misc) */

