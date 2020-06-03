/*
 * File:          sidl_Enforcer.h
 * 
 * Produced at the Lawrence Livermore National Laboratory by 
 * T. Dahlgren as part of the work towards a Ph.D. thesis.
 *
 * Copyright (c) 2005-2009, Lawrence Livermore National Security, LLC.
 * All rights reserved.
 */

#ifndef included_sidl_Enforcer_h
#define included_sidl_Enforcer_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_sidl_ClauseType_h
#include "sidl_ClauseType.h"
#endif
#ifndef included_sidl_ContractClass_h
#include "sidl_ContractClass.h"
#endif
#ifndef included_sidl_EnforceFreq_h
#include "sidl_EnforceFreq.h"
#endif
#ifndef included_sidl_EnfTraceLevel_h
#include "sidl_EnfTraceLevel.h"
#endif

/*
 **********************************************************************
 *                          PUBLIC METHODS                            *
 **********************************************************************
 */


#ifdef __cplusplus
extern "C" {
#endif

extern
void
sidl_Enforcer_init(
  /* in */ enum sidl_ContractClass__enum contractClass,
  /* in */ enum sidl_EnforceFreq__enum   enforceFreq,
  /* in */ int32_t                       interval,
  /* in */ double                        overheadLimit,
  /* in */ double                        appProgAvgPerCall,
  /* in */ double                        annealLimit,
  /* in */ sidl_bool                     clearStats);

extern
void
sidl_Enforcer_reset(
  void);

extern
sidl_bool
sidl_Enforcer_areEnforcing(
  void);

extern
sidl_bool
sidl_Enforcer_usingTimingData(
  void);

extern
enum sidl_ContractClass__enum
sidl_Enforcer_getContractClass(
  void);

extern
enum sidl_EnforceFreq__enum
sidl_Enforcer_getEnforceFreq(
  void);

extern
int32_t
sidl_Enforcer_getSamplingInterval(
  void);

extern
double
sidl_Enforcer_getOverheadLimit(
  void);

extern
double
sidl_Enforcer_getAppAvgPerCall(
  void);

extern
double
sidl_Enforcer_getAnnealLimit(
  void);

extern
sidl_bool
sidl_Enforcer_enforceClause(
  /* in */ sidl_bool                  firstPerCall,
  /* in */ enum sidl_ClauseType__enum clauseType,
  /* in */ int32_t                    complexity,
  /* in */ sidl_bool                  hasMethCall,
  /* in */ sidl_bool                  hasResults,
  /* in */ double                     methTime,
  /* in */ double                     clauseTime);

extern
void
sidl_Enforcer_dumpStats(
  /* in */ const char* filename,
  /* in */ sidl_bool   header,
  /* in */ const char* prefix,
  /* in */ sidl_bool   compressed);

extern
void
sidl_Enforcer_dumpStatsHeader(
  /* in */ void*     filePtr,
  /* in */ sidl_bool newLine);

extern
void
sidl_Enforcer_dumpStatsData(
  /* in */ void*       filePtr,
  /* in */ const char* prefix,
  /* in */ sidl_bool   newLine);

extern
void
sidl_Enforcer_startTrace(
  /* in */ const char* filename,
  /* in */ enum sidl_EnfTraceLevel__enum traceLevel);

extern
sidl_bool
sidl_Enforcer_areTracing(
  void);

extern
char*
sidl_Enforcer_getTraceFilename(
  void);

extern
enum sidl_EnfTraceLevel__enum
sidl_Enforcer_getTraceLevel(
  void);

extern
void
sidl_Enforcer_logTrace(
  /* in */ const char* className,
  /* in */ const char* methName,
  /* in */ int32_t     methIndex,
  /* in */ int32_t     preComp,
  /* in */ int32_t     postComp,
  /* in */ int32_t     invComp,
  /* in */ double      methTime,
  /* in */ double      preTime,
  /* in */ double      postTime,
  /* in */ double      inv1Time,
  /* in */ double      inv2Time);

extern
void 
sidl_Enforcer_logTraceDecisions(
  void);

extern
void
sidl_Enforcer_endTrace(
  void);

extern
void 
sidl_Enforcer_setEstimatesInterval(
  /* in */ int32_t interval);

extern
int32_t 
sidl_Enforcer_getEstimatesInterval(
  void);

#ifdef __cplusplus
}
#endif
#endif
