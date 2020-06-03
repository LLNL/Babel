/*
 * File:          sidl_Enforcer.c
 * 
 * Produced at the Lawrence Livermore National Laboratory by 
 * T. Dahlgren as part of the work towards a Ph.D. thesis.
 *
 * Copyright (c) 2005-2009, Lawrence Livermore National Security, LLC.
 * All rights reserved.
 */

#include "sidl_Enforcer.h"
#include "sidl_EnfPolicy_Impl.h"
#include "sidl_ContractClass_IOR.h"
#include "sidl_EnforceFreq_IOR.h"

#include <math.h>
#include <stdlib.h>
#include <time.h>
#include <sys/time.h>
#include <stdio.h>
#include "sidl_String.h"

/*
 **********************************************************************
 *                             MACRO(S)                               *
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
 *                          PRIVATE DATA                              *
 **********************************************************************
 */
/*
 * ------------------------------------------------------------------ *
 * Defaults for contract enforcement tracing.                         *
 * ------------------------------------------------------------------ *
 */
const char* S_ENF_TRACE_FILENAME = "ContractTrace.out";

/*
 * ------------------------------------------------------------------ *
 * Defaults for contract enforcement.                                 *
 * ------------------------------------------------------------------ *
 */
static enum sidl_ContractClass__enum 
                S_ENF_CONTRACT_CLASS = sidl_ContractClass_ALLCLASSES;
static enum sidl_EnforceFreq__enum   
              S_ENF_ENFORCE_FREQ   = sidl_EnforceFreq_ALWAYS;

/*
 * ------------------------------------------------------------------ *
 * For enforcement...  Policy options are maintained here to ensure   *
 * overrides of invalid settings are retained AND to make ensure the  *
 * decision process is more efficient than going back to              *
 * sidl.EnfPolicy to get options.                                     *
 * ------------------------------------------------------------------ *
 */

/*
 * Enforcement Policy.  The policy is based on a combination of the 
 * contract classification and enforcement frequency options.
 */
static enum sidl_ContractClass__enum 
              s_contractClass = sidl_ContractClass_ALLCLASSES;
static enum sidl_EnforceFreq__enum   
              s_enforceFreq   = sidl_EnforceFreq_ALWAYS;

/*
 * Countdowns of the periodic and random policies are dictated by the
 * user-specified interval, which maps to a period or random window,
 * respectively.  
 */
static int32_t s_interval = (int32_t) 0;

/*
 * The overhead and annealing limits are needed for the performance-
 * driven enforcement frequencies.  The program average time is used 
 * to make better informed decisions more in line with the actual 
 * overhead --- the more accurate the estimate the better.
 */
static double s_overheadLimit  = (double) 0.0;
static double s_annealLimit    = (double) 0.0;
static double s_avgPerMethCall = (double) 0.0;

/*
 * Traditional sampling extras:
 *   s_randSkip  = needed to ensure uniformity in random window
 *   s_countdown = countdown (for PERIODIC and RANDOM)
 */
static int32_t s_randSkip  = (int32_t) 0;
static int32_t s_countdown = (int32_t) 0;

/*
 * Accumulators:
 *    s_totalRequests   = number of enforcement requests
 *    s_totalAllowed    = number of times enforcement requests approved
 *    s_totalMethTime   = (estimated) method execution time
 *    s_totalClauseTime = (estimated) assertion enforcement time
 */
static int32_t s_totalRequests    = (int32_t) 0;
static int32_t s_totalAllowed     = (int32_t) 0;
static double  s_totalMethTime    = (double) 0.0;
static double  s_totalClauseTime  = (double) 0.0;

/*
 * ------------------------------------------------------------------ *
 * For enforcement-related timing...                                  *
 * ------------------------------------------------------------------ *
 */
static struct timeval  s_firstTimeStamp;
static struct timeval  s_lastTimeStamp;

/*
 * ------------------------------------------------------------------ *
 * For enforcement tracing...                                         *
 *                                                                    *
 * Note:  Refer to the header file for public defaults.               *
 * ------------------------------------------------------------------ *
 */
static FILE*  s_tracePtr      = NULL;
static char*  s_traceFilename = NULL;

static enum sidl_EnfTraceLevel__enum s_tracingLevel = sidl_EnfTraceLevel_NONE;

static double s_totalCheckOverhead  = (double) 0.0;
static double s_totalDecisions      = (double) 0.0;

/*
 * EXPERIMENTAL FEATURE SUPPORT
 *   Interval used for runtime timing and resetting of execution time
 *   estimates.
 */
static int32_t          s_estimatesUpdateInterval   = (int32_t) 0;


/*
 **********************************************************************
 *                        PRIVATE METHODS                             *
 **********************************************************************
 */

/*
 * Reset the countdown.
 */
#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_resetCountdown"
static void 
sidl_Enforcer_resetCountdown(void) {
  int32_t randCd;

  if (s_enforceFreq == sidl_EnforceFreq_PERIODIC) {
    s_countdown = s_interval;
    s_randSkip  = (int32_t) 0;
  } else if (s_enforceFreq == sidl_EnforceFreq_RANDOM) {
    randCd = (int32_t)(ceil( ((double)rand()/(double)RAND_MAX)
                           * ((double)s_interval) ) );
    s_countdown = s_randSkip + randCd;
    s_randSkip  = s_interval - randCd;
  }

  return;
}

/* 
 * Return TRUE if the annealing function requirement is met.
 */
#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_annealFuncOkay"
static sidl_bool 
sidl_Enforcer_annealFuncOkay(int64_t clauseTime) {
  double    func, cTime;
  sidl_bool isOkay = FALSE;

  cTime  = (s_totalClauseTime) + (double)clauseTime;
  func   = exp(cTime/(s_totalMethTime)) / sqrt(s_totalMethTime);
  isOkay =  (func >= (s_annealLimit)) 
         && (((double)rand()/(double)RAND_MAX) < 0.5);

  return isOkay;
}


#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_enforceClauseNoTr"
static sidl_bool 
sidl_Enforcer_enforceClauseNoTr(
  /* in */ sidl_bool                  firstPerCall,
  /* in */ enum sidl_ClauseType__enum clauseType,
  /* in */ int32_t                    complexity,
  /* in */ sidl_bool                  hasMethCall,
  /* in */ sidl_bool                  hasResults,
  /* in */ double                     methTime,
  /* in */ double                     clauseTime) 
{
  double at, oh;
  sidl_bool enforceIt   = FALSE;
  sidl_bool matchesClass = FALSE;

  s_totalRequests += 1;
  if (firstPerCall) {
    (s_totalMethTime) += (methTime + s_avgPerMethCall);
  }

  /*
   * First determine if the contract classification requirement is met.
   *
   * Note: The ordering is meant to reflect the likelihood that the
   * option will be used, with the most likely first and the least
   * last.  Also, using IF statement (instead of switch) since it
   * appears to have a "significant" impact on performance -- at 
   * least it did for the early mesh experiments.
   */
  if (s_contractClass == sidl_ContractClass_ALLCLASSES) {
    matchesClass = TRUE;
  } else if (s_contractClass == sidl_ContractClass_PRECONDS) {
    if (clauseType == sidl_ClauseType_PRECONDITION) {
      matchesClass = TRUE;
    }
  } else if (s_contractClass == sidl_ContractClass_POSTCONDS) {
    if (clauseType == sidl_ClauseType_POSTCONDITION) {
      matchesClass = TRUE;
    }
  } else if (s_contractClass == sidl_ContractClass_RESULTS) {
    if ( (clauseType == sidl_ClauseType_POSTCONDITION) && (hasResults) ) {
      matchesClass = TRUE;
    }
  } else if (s_contractClass == sidl_ContractClass_SIMPLEEXPRS) {
    if ( (complexity == 0) && (!hasMethCall) ) {
      matchesClass = TRUE;
    }
  } else if (s_contractClass == sidl_ContractClass_INVARIANTS) {
    if (clauseType == sidl_ClauseType_INVARIANT) {  
      matchesClass = TRUE;
    }
  } else if (s_contractClass == sidl_ContractClass_CONSTANT) {
    if (complexity == 0) {               
      matchesClass = TRUE;
    }
  } else if (s_contractClass == sidl_ContractClass_METHODCALLS) {
    if (hasMethCall) {
      matchesClass = TRUE;
    }
  } else if (s_contractClass == sidl_ContractClass_LINEAR) {
    if (complexity == 1) {
      matchesClass = TRUE;
    }
  } else if (s_contractClass == sidl_ContractClass_QUADRATIC) {
    if (complexity == 2) {
      matchesClass = TRUE;
    }
  } else if (s_contractClass == sidl_ContractClass_PREPOST) {
    if (  (clauseType == sidl_ClauseType_PRECONDITION) 
       || (clauseType == sidl_ClauseType_POSTCONDITION) ) {
      matchesClass = TRUE;
    }
  } else if (s_contractClass == sidl_ContractClass_INVPRE) {
    if (  (clauseType == sidl_ClauseType_INVARIANT) 
       || (clauseType == sidl_ClauseType_PRECONDITION) ) {
      matchesClass = TRUE;
    }
  } else if (s_contractClass == sidl_ContractClass_INVPOST) {
    if (  (clauseType == sidl_ClauseType_INVARIANT) 
       || (clauseType == sidl_ClauseType_POSTCONDITION) ) {
      matchesClass = TRUE;
    }
  } else if (s_contractClass == sidl_ContractClass_CUBIC) {
    if (complexity == 3) {
      matchesClass = TRUE;
    }
  } else if (s_contractClass == sidl_ContractClass_QUARTIC) {
    if (complexity == 4) {
      matchesClass = TRUE;
    }
  } else if (s_contractClass == sidl_ContractClass_QUINTIC) {
    if (complexity == 5) {
      matchesClass = TRUE;
    }
  } else if (s_contractClass == sidl_ContractClass_SEXTIC) {
    if (complexity == 6) {
      matchesClass = TRUE;
    }
  } else if (s_contractClass == sidl_ContractClass_SEPTIC) {
    if (complexity == 7) {
      matchesClass = TRUE;
    }
  }

  /*
   * Now determine whether or not a classification match should be 
   * enforced based on the enforcement frequency.
   *
   * Note: The ordering is a combination of likely usage and timing
   * sensitivity.
   */
  if (matchesClass) {
    if (s_enforceFreq == sidl_EnforceFreq_ALWAYS) {
      enforceIt = TRUE;
    } else if (s_enforceFreq == sidl_EnforceFreq_ADAPTFIT) {
      at = (double)(s_totalClauseTime) + clauseTime;
      oh = (double)(s_totalMethTime) * s_overheadLimit;
      if (at <= oh) {
        enforceIt = TRUE;
      }
    } else if (s_enforceFreq == sidl_EnforceFreq_ADAPTTIMING) {
      if (clauseTime <= (methTime * (s_overheadLimit))) {
        enforceIt = TRUE;
      } else if (  (clauseTime <= 1.0)
                && ( (s_totalClauseTime) 
                   < ((s_totalMethTime)*(s_overheadLimit)) )) {
        enforceIt = TRUE;
      }
    } else if (  (s_enforceFreq == sidl_EnforceFreq_PERIODIC) 
              || (s_enforceFreq == sidl_EnforceFreq_RANDOM) ) {
      if (s_countdown > 1) {
        enforceIt = FALSE;
        (s_countdown) -= 1;
      } else {
        enforceIt = TRUE;
        sidl_Enforcer_resetCountdown();
      }
    } else if (s_enforceFreq == sidl_EnforceFreq_SIMANNEAL) {
      at = (double)(s_totalClauseTime) + clauseTime;
      oh = (double)(s_totalMethTime) * s_overheadLimit;
      if (at <= oh) {
        enforceIt = TRUE;
      } else if (sidl_Enforcer_annealFuncOkay(clauseTime)) {
        enforceIt = TRUE;
      } 
    } 
  } 

  /*
   * If they're going to be enforced, then register the relevant
   * information before returning the approval.  
   */
  if (enforceIt) {
    (s_totalAllowed) += 1;
    (s_totalClauseTime) += clauseTime;
  }

  return enforceIt;
}


#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_enforceClauseTr"

#ifdef __cplusplus
extern "C"
#endif
static sidl_bool 
sidl_Enforcer_enforceClauseTr(
  /* in */ sidl_bool                  firstPerCall,
  /* in */ enum sidl_ClauseType__enum clauseType,
  /* in */ int32_t                    complexity,
  /* in */ sidl_bool                  hasMethCall,
  /* in */ sidl_bool                  hasResults,
  /* in */ double                     methTime,
  /* in */ double                     clauseTime) 
{
  sidl_bool      enforceIt = FALSE;
  struct timeval t0, t1;

  if (s_tracingLevel == sidl_EnfTraceLevel_OVERHEAD) {
    gettimeofday(&t0, NULL);
  }

  /*
   * Make the enforcement decision.
   */
  enforceIt = sidl_Enforcer_enforceClauseNoTr(firstPerCall, clauseType, 
                   complexity, hasMethCall, hasResults, methTime, 
                   clauseTime);

  /*
   * Now determine how much time was spent on the enforcement decision.
   */
  if (s_tracingLevel == sidl_EnfTraceLevel_OVERHEAD) {
    gettimeofday(&t1, NULL);
    s_totalCheckOverhead += DIFFT(t1, t0);
    s_totalDecisions     += 1.0;
  }

  return enforceIt;
}

/*
 **********************************************************************
 *                 PUBLIC (INTERNAL USE ONLY) METHODS                 *
 **********************************************************************
 */

/**
 * Initializes the private data.  Note that policy settings are 
 * maintained here instead of in sidl.EnfPolicy for two reasons.
 * First, doing so ensures overrides of invalid settings (to
 * published defaults) are maintained in one place.  Second,
 * it improves enforcement decision efficiency by having the 
 * options locally accessible.
 *
 * @param contractClass  Contract classification
 * @param enforceFreq    Enforcement frequency
 * @param interval       Sampling interval representing the
 *                         period (for PERIODIC) or maximum
 *                         number/window (for RANDOM)
 * @param overheadLimit  Limit on performance overhead
 * @param appAvgPerCall  Average extra, application-specific
 *                         execution time, normalized by calls
 *                         to annotated methods
 * @param annealLimit    Limit on simulated annealing function
 *                         to ensure its termination
 * @param clearStats     TRUE if enforcement statistics are to be
 *                         cleared; FALSE otherwise.
 */
#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_init"

#ifdef __cplusplus
extern "C"
#endif
void 
sidl_Enforcer_init(
  /* in */ enum sidl_ContractClass__enum contractClass,
  /* in */ enum sidl_EnforceFreq__enum   enforceFreq,
  /* in */ int32_t                       interval,
  /* in */ double                        overheadLimit,
  /* in */ double                        appProgAvgPerCall,
  /* in */ double                        annealLimit,
  /* in */ sidl_bool                     clearStats)
{
  /*
   * Now make appropriate changes, if any, to the policy settings
   * based on defaults documented/described in sidl.sidl.
   */
  s_contractClass  = contractClass;
  s_enforceFreq    = enforceFreq;
  s_interval       = interval > 0 ? interval : 0;
  s_overheadLimit  = overheadLimit > 0.0 ? overheadLimit : 0.0;
  s_avgPerMethCall = appProgAvgPerCall > 0.0 ? appProgAvgPerCall : 0.0;
  s_annealLimit    = ( (0.0 < annealLimit) && (annealLimit < 2.72) )
                     ? annealLimit : 2.72;

  /*
   * Now initialize enforcement drivers and accumulators.
   */
  if (clearStats) {
    if (  (s_enforceFreq == sidl_EnforceFreq_PERIODIC)
       || (s_enforceFreq == sidl_EnforceFreq_RANDOM) ) {
      sidl_Enforcer_resetCountdown();
    } else {
      s_countdown             = (int32_t) 0;
      s_randSkip              = (int32_t) 0;
    }
    s_estimatesUpdateInterval = (int32_t) 0;
    s_totalRequests           = (int32_t) 0;
    s_totalAllowed            = (int32_t) 0;
    s_totalMethTime           = (double) 0.0;
    s_totalClauseTime         = (double) 0.0;

    /*
     * Also ensure terminate existing enforcement tracing,
     * which is assumed to reset trace-specific statistics.
     */
    sidl_Enforcer_endTrace();
  }

  return;
}

/*
 * Reset enforcement options.  Initialize to defaults.
 */
#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_reset"

#ifdef __cplusplus
extern "C"
#endif
void 
sidl_Enforcer_reset(
  void)
{
  sidl_Enforcer_init(S_ENF_CONTRACT_CLASS, S_ENF_ENFORCE_FREQ, (int32_t)0,
       (double)0.0, (double)0.0, (double)0.0, TRUE);
}


/*
 * Returns TRUE if contract enforcement is enabled; FALSE otherwise.
 */
#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_areEnforcing"
#ifdef __cplusplus
extern "C"
#endif
sidl_bool
sidl_Enforcer_areEnforcing(
  void)
{
  return (s_enforceFreq != sidl_EnforceFreq_NEVER);
}


/*
 * Returns TRUE if contract enforcement is set to a performance-based
 * strategy; FALSE otherwise.
 */
#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_usingTimingData"
#ifdef __cplusplus
extern "C"
#endif
sidl_bool
sidl_Enforcer_usingTimingData(
  void)
{
  return (  (s_enforceFreq == sidl_EnforceFreq_ADAPTFIT)
         || (s_enforceFreq == sidl_EnforceFreq_ADAPTTIMING) );
}


/**
 * Returns the contract classification policy option.
 */
#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_getContractClass"

#ifdef __cplusplus
extern "C"
#endif
enum sidl_ContractClass__enum
sidl_Enforcer_getContractClass(
  void)
{
  return s_contractClass;
}

/**
 * Returns the enforcement frequency policy option.
 */
#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_getEnforceFreq"

#ifdef __cplusplus
extern "C"
#endif
enum sidl_EnforceFreq__enum
sidl_Enforcer_getEnforceFreq(
  void)
{
  return s_enforceFreq;
}

/**
 * Returns the interval for PERIODIC (i.e., the period) or
 * RANDOM (i.e., the maximum number, or random window).
 * Returns 0 by default.
 */
#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_getSamplingInterval"

#ifdef __cplusplus
extern "C"
#endif
int32_t
sidl_Enforcer_getSamplingInterval(
  void)
{
  return s_interval;
}

/**
 * Returns the desired enforcement overhead limit for
 * performance-driven frequency options (i.e., ADAPTFIT,
 * ADAPTTIMING, and SIMANNEAL).  Returns 0.0 by default.
 */
#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_getOverheadLimit"

#ifdef __cplusplus
extern "C"
#endif
double
sidl_Enforcer_getOverheadLimit(
  void)
{
  return s_overheadLimit;
}

/**
 * Returns the average assumed execution time associated
 * with the program or application.  Returns 0.0 by default.
 */
#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_getAppAvgPerCall"

#ifdef __cplusplus
extern "C"
#endif
double
sidl_Enforcer_getAppAvgPerCall(
  void)
{
  return s_avgPerMethCall;
}


/**
 * Returns the annealing limit for SIMANNEAL enforcement
 * frequency option.  Returns 0.0 by default.
 */
#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_getAnnealLimit"

#ifdef __cplusplus
extern "C"
#endif
double
sidl_Enforcer_getAnnealLimit(
  void)
{
  return s_annealLimit;
}


/*
 * Returns TRUE if the clause should be enforced; FALSE otherwise.
 * 
 * This method is invoked automatically through the generated 
 * enforcement infrastructure and should NOT be invoked directly 
 * (see WARNING below).
 * 
 * @param firstPerCall  TRUE if it is the first enforcement request 
 *                        for the given invocation.  This is needed 
 *                        for proper execution time maintenance.
 * @param clauseType    The type of contract clause being checked.
 * @param complexity    Degree of complexity of the assertion
 *                        expressions within the clause.
 * @param hasMethCall   TRUE if the clause involves one or more method
 *                        calls; else FALSE.
 * @param hasResults    TRUE if the clause involves the method's
 *                        result or one or more in or inout arguments; 
 *                        else FALSE.
 * @param methTime      Estimated execution time for the method 
 *                        associated with the contract clause.
 * @param clauseTime    Estimated execution time for the clause.
 *
 * @return              TRUE if the contracts should be enforced; else 
 *                        FALSE.
 * 
 * WARNING:  This call has side-effects associated with enforcement 
 * state in that it assumes the request will be carried out if 
 * approved.
 */
#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_enforceClause"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
sidl_Enforcer_enforceClause(
  /* in */ sidl_bool                  firstPerCall,
  /* in */ enum sidl_ClauseType__enum clauseType,
  /* in */ int32_t                    complexity,
  /* in */ sidl_bool                  hasMethCall,
  /* in */ sidl_bool                  hasResults,
  /* in */ double                     methTime,
  /* in */ double                     clauseTime)
{
  sidl_bool enforceIt = FALSE;

  if (s_tracingLevel == sidl_EnfTraceLevel_NONE) {
    enforceIt = sidl_Enforcer_enforceClauseNoTr(firstPerCall, 
                     clauseType, complexity, hasMethCall, hasResults,
                     methTime, clauseTime);
  } else {
    enforceIt = sidl_Enforcer_enforceClauseTr(firstPerCall, clauseType,
                     complexity, hasMethCall, hasResults, methTime, 
                     clauseTime);
  }

  return enforceIt;
}

/*
 * Prints statistics data to the file with the specified name.  
 * The file is opened (for append) and closed on each call.
 * 
 * @param filename   Name of the file to which the statistics data 
 *                     should be written.
 * @param header     TRUE if the header line is to be printed prior 
 *                     to the statistics line (for compressed output
 *                     only).
 * @param prefix    String description for identifying information, 
 *                     if any, intended to preceed the statistics data.  
 *                     Useful for distinguishing between different 
 *                     objects, for example.
 * @param compressed TRUE if the enforcer state is to be dumped on a 
 *                     single line with semi-colon separators between 
 *                     fields.
 */

#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_dumpStats"

#ifdef __cplusplus
extern "C"
#endif
void
sidl_Enforcer_dumpStats(
  /* in */ const char* filename,
  /* in */ sidl_bool   header,
  /* in */ const char* prefix,
  /* in */ sidl_bool   compressed)
{
  FILE*              fptr = NULL;
  char*              timeStr;
  char*              pName;
  time_t             currTime;
  sidl_BaseInterface tae = NULL;

  fptr = fopen(filename, "a");
  if (fptr == NULL) {
    printf("Cannot open file %s to dump enforcement statistics\n", 
           filename);
    return;
  }

  fprintf(fptr, "\n");
  if (compressed) {
    if (header) {
      sidl_Enforcer_dumpStatsHeader(fptr, TRUE);
    }
    
    sidl_Enforcer_dumpStatsData(fptr, prefix, TRUE);
  } else {
    currTime    = time(NULL);
    timeStr     = ctime(&currTime);
    pName       = impl_sidl_EnfPolicy_getPolicyName(FALSE, &tae);

    fprintf(fptr, "CONTRACT ENFORCEMENT (%s) AT %s", prefix, 
            timeStr);
    fprintf(fptr, "Policy             = %s\n", pName);
    fprintf(fptr, "Period/Random Max. = %d\n", s_interval);
    fprintf(fptr, "Overhead Limit     = %f\n", s_overheadLimit);
    fprintf(fptr, "Annealing Limit    = %f\n", s_annealLimit);
    fprintf(fptr, "Current Statistics:\n");
    fprintf(fptr, "  Random Skip              = %d\n", s_randSkip);
    fprintf(fptr, "  Countdown                = %d\n", s_countdown);
    fprintf(fptr, "  Est. Program+Method Time = ");
    fprintf(fptr, "%f\n", s_totalMethTime);
    fprintf(fptr, "  Est. Contract Time       = ");
    fprintf(fptr, "%f\n", s_totalClauseTime);
    fprintf(fptr, "  Enforce Requests         = ");
    fprintf(fptr, "%d\n", s_totalRequests);
    fprintf(fptr, "  Enforce Granted          = ");
    fprintf(fptr, "%d\n", s_totalAllowed);
    fprintf(fptr, "\n");

    if (s_tracingLevel >= sidl_EnfTraceLevel_OVERHEAD) {
      fprintf(fptr, "Enforcement Decision:\n");
      fprintf(fptr, "  Total Overhead  = ");
      fprintf(fptr, "%f\n", s_totalCheckOverhead);
      fprintf(fptr, "  Total Decisions = ");
      fprintf(fptr, "%f ", s_totalDecisions);
      fprintf(fptr, "(%.2f)\n", (s_totalCheckOverhead/s_totalDecisions));
    }

    sidl_String_free(pName);
  }

  fclose(fptr);

  return;
}

/*
 * Prints the header that describes the statistics in compressed
 * output format to the specified file.  Each field is semi-colon 
 * separated to match the compressed output.  
 * 
 * This function is needed/used by the local statistics dump
 * routines to minimize the amount of generated output.
 * 
 * @param filePtr   Pointer to the (already opened) output/statistics 
 *                    file.
 * @param newLine   TRUE if the output is to include a new line 
 *                    character.
 */
#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_dumpStatsHeader"

#ifdef __cplusplus
extern "C"
#endif
void
sidl_Enforcer_dumpStatsHeader(
  /* in */ void*     filePtr,
  /* in */ sidl_bool newLine)
{
  static const char* pt1 = "Prefix; Timestamp; Policy; Interval; ";
  static const char* pt2 = "AnnealLimit; OHLimit; procPerCall; ";
  static const char* pt3 = "RandSkip; CD; methTime; clauseTime; ";
  static const char* pt4 = "TotalRequested; TotalAllowed";
  static const char* pt5 = "; DecisionOH; Decisions; meanDecOH";

  FILE* fptr = (FILE*)filePtr;
  if (fptr != NULL) {
    fprintf(fptr, "%s%s%s%s", pt1, pt2, pt3, pt4);
    if (s_tracingLevel >= sidl_EnfTraceLevel_OVERHEAD) {
      fprintf(fptr, "%s", pt5);
    }
    if (newLine) {
      fprintf(fptr, "\n");
    }
  }
  
  return;
}

/*
 * Prints the statistics data in compressed format in the specified 
 * file.  Each field is output in the same order specified in 
 * getDumpStatsHeader().  In addition, the fields are semi-colon 
 * separated as well.  
 * 
 * This function is needed/used by the local statistics dump
 * routines to minimize the amount of generated output.
 * 
 * @param filePtr   Pointer to the (already opened) output/statistics 
 *                    file.
 * @param prefix    String description for identifying information, 
 *                    if any, intended to preceed the statistics data.
 *                    Useful for distinguishing between different 
 *                    objects, for example.
 * @param newLine   TRUE if the output is to include a new line 
 *                    character.
 */
#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_dumpStatsData"

#ifdef __cplusplus
extern "C"
#endif
void
sidl_Enforcer_dumpStatsData(
  /* in */ void*       filePtr,
  /* in */ const char* prefix,
  /* in */ sidl_bool   newLine)
{
  FILE*              fptr = (FILE*)filePtr;
  time_t             currTime;
  char*              timeStr;
  char*              pName;
  sidl_BaseInterface tae = NULL;

  if (fptr != NULL) {
    currTime    = time(NULL);
    timeStr     = ctime(&currTime);  /* Static so don't free(). */
    timeStr[24] = '\0';  /* Only need 1st 24 chars of the timestamp. */
    pName       = impl_sidl_EnfPolicy_getPolicyName(TRUE, &tae);

    fprintf(fptr, "%s; %s; %s; ", prefix, timeStr, pName);
    fprintf(fptr, "%d; %.2f; ", s_interval, s_annealLimit);
    fprintf(fptr, "%.2f; %.3f; ", s_overheadLimit, s_avgPerMethCall);
    fprintf(fptr, "%d; %d; ", s_randSkip, s_countdown);
    fprintf(fptr, "%.0f; %.0f; ", s_totalMethTime, s_totalClauseTime);
    fprintf(fptr, "%d; %d", s_totalRequests, s_totalAllowed);

    if (s_tracingLevel == sidl_EnfTraceLevel_OVERHEAD) {
      fprintf(fptr, "%f; ", s_totalCheckOverhead);
      fprintf(fptr, "%f; ", s_totalDecisions);
      fprintf(fptr, "%.2f ", (s_totalCheckOverhead/s_totalDecisions));
    }

    if (newLine) {
      fprintf(fptr, "\n");
    } 
    fflush(fptr);

    sidl_String_free(pName);
  } 

  return;
}

/*
 * Starts enforcement trace file generation.
 *
 * @param filename    Name of the destination trace file.
 * @param traceLevel  Level of trace timing and reporting required.
 *                      [Default = NONE]
 */

#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_startTrace"

#ifdef __cplusplus
extern "C"
#endif
void
sidl_Enforcer_startTrace(
  /* in */ const char* filename,
  /* in */ enum sidl_EnfTraceLevel__enum traceLevel)
{
  char* fname = NULL;

  if (s_tracePtr != NULL) {
    sidl_Enforcer_endTrace();
  }

  /*
   * Support default parameters.
   */
  if (  (traceLevel < sidl_EnfTraceLevel_NONE)
     || (traceLevel > sidl_EnfTraceLevel_OVERHEAD) ) {
    s_tracingLevel = sidl_EnfTraceLevel_NONE;
  } else {
    s_tracingLevel = traceLevel;
  }

  if (filename == NULL) {
    fname = sidl_String_strdup(S_ENF_TRACE_FILENAME);
  } else {
    fname = sidl_String_strdup(filename);
  }

  /*
   * Replace any existing file name with the new name.
   */
  if (s_traceFilename != NULL) {
    sidl_String_free(s_traceFilename);
  }
  s_traceFilename = fname;

  /*
   * Now open the trace file.
   */
  if ((s_tracePtr=fopen(s_traceFilename, "w")) == NULL) {
    printf("Cannot open file %s for trace input.\n", s_traceFilename);
    return;
  }

  gettimeofday(&s_lastTimeStamp, NULL);
  s_firstTimeStamp = s_lastTimeStamp;

  /*
   * Also initialize trace accumulators.
   */
  s_totalCheckOverhead  = (double) 0.0;
  s_totalDecisions      = (double) 0.0;

  return;
}

/*
 * Returns TRUE if contract enforcement tracing is enabled;
 * FALSE otherwise.
 */
#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_areTracing"

#ifdef __cplusplus
extern "C"
#endif
sidl_bool
sidl_Enforcer_areTracing(
  void)
{
  return s_tracingLevel != sidl_EnfTraceLevel_NONE;
}


/**
 * Returns the name of the trace file.  If one was not provided,
 * the default name is returned.
 */
#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_getTraceFilename"

#ifdef __cplusplus
extern "C"
#endif
char*
sidl_Enforcer_getTraceFilename(
  void)
{
  return s_traceFilename;
}

/**
 * Returns the level of enforcement tracing.
 */
#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_getTraceLevel"

#ifdef __cplusplus
extern "C"
#endif
enum sidl_EnfTraceLevel__enum
sidl_Enforcer_getTraceLevel(
  void)
{
  return s_tracingLevel;
}


/*
 * Logs a method call entry into the trace file.
 * 
 * This method is invoked automatically through the generated 
 * enforcement infrastructure and should NOT be invoked directly.
 * 
 * @param className  Name of the class whose timing data is to be 
 *                     logged.
 * @param methName   Name of the method whose timing data is to be 
 *                     logged.
 * @param methIndex  Index of the method within the IOR.
 * @param preComp    Known precondition complexity.
 * @param postComp   Known postcondition complexity.
 * @param invComp    Known invariants complexity.
 * @param methTime   Method execution time.
 * @param preTime    Execution time required for preconditions.
 * @param postTime   Execution time required for postconditions.
 * @param inv1Time   Execution time required for pre-call pass on 
 *                     invariants.
 * @param inv2Time   Execution time required for post-call pass.
 */

#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_logTrace"

#ifdef __cplusplus
extern "C"
#endif
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
  /* in */ double      inv2Time)
{
  struct timeval ts;
  double bbDiff, diff, offset;

  if (  (s_tracingLevel >= sidl_EnfTraceLevel_BASIC) 
     && (s_tracePtr != NULL) ) 
  {
    /*
     * First output a Basic Block.
     *
     * ASSUMPTION:  With this approach, there is a risk of generating 
     * basic block entries in the wrong places (i.e., when stub methods
     * are called from within implementations).  Alternatives must 
     * be more sophisticated in order to avoid double-counting one or 
     * more aspects of enforcement or method dispatch execution times.
     * Given the current state of interface contracts, the approach 
     * taken here should be sufficient in most cases.
     */
     gettimeofday(&ts, NULL);
     diff = DIFFT(ts, s_lastTimeStamp);
     s_lastTimeStamp = ts;

    offset = methTime + preTime + postTime + inv1Time + inv2Time;
    if (offset < diff) {
      bbDiff = diff - offset;
      fprintf(s_tracePtr, "BB %.0f\n", bbDiff);
    }

    /*
     * Now output the method-specific execution time information.
     */
    fprintf(s_tracePtr, "FMC %s %s", className, methName);
    fprintf(s_tracePtr, " %d %d", methIndex, preComp);
    fprintf(s_tracePtr, " %d %d", postComp, invComp);
    fprintf(s_tracePtr, " %.0f", methTime);
    fprintf(s_tracePtr, " %.0f %.0f", preTime, postTime);
    fprintf(s_tracePtr, " %.0f %.0f\n", inv1Time, inv2Time);
  }

  return;
}


/*
 * Logs a summary of the current trace decision information.
 */
#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_logTraceDecisions"

#ifdef __cplusplus
extern "C"
#endif
void
sidl_Enforcer_logTraceDecisions(
  void)
{
  if (  (s_tracingLevel >= sidl_EnfTraceLevel_OVERHEAD) 
     && (s_tracePtr != NULL) ) 
  {
    fprintf(s_tracePtr, "DEC %f; ", s_totalCheckOverhead);
    fprintf(s_tracePtr, "%f; ", s_totalDecisions);
    fprintf(s_tracePtr, "%.2f\n", (s_totalCheckOverhead/s_totalDecisions));
  }

  return;
}


/*
 * Terminates enforcement trace file generation.  Takes a final 
 * timestamp and logs the remaining trace information.
 */
#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_endTrace"

#ifdef __cplusplus
extern "C"
#endif
void
sidl_Enforcer_endTrace(
  void)
{
  struct timeval ts;
  double diff, total;

  if (  (s_tracingLevel != sidl_EnfTraceLevel_NONE) 
     && (s_tracePtr != NULL) ) 
  {
    gettimeofday(&ts, NULL);
    diff = DIFFT(ts, s_lastTimeStamp);

    sidl_Enforcer_logTraceDecisions();

    fprintf(s_tracePtr, "BB %.0f\n\n", diff);
    total = DIFFT(ts, s_firstTimeStamp);
    fprintf(s_tracePtr, "TT %.0f\n", total);
    fclose(s_tracePtr);

    /*
     * Initialize trace file pointer and accumulators in case
     * subsequent calls are made.
     */
    s_tracePtr           = NULL;
    s_totalCheckOverhead = (double) 0.0;
    s_totalDecisions     = (double) 0.0;
  }

  return;
}


/*
 * EXPERIMENTAL FEATURE  (Should eventually be moved to sidl_EnfPolicy.)
 *
 * Sets the optional interval for updating execution time estimates.
 * That is, timing instrumentation is used to periodically update
 * execution time estimates at runtime.  Only the method invoked
 * on the interval will have its execution time estimates updated.
 * 
 * Note:  This capability is separate from tracing.
 */
#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_setEstimatesInterval"

#ifdef __cplusplus
extern "C"
#endif
void 
sidl_Enforcer_setEstimatesInterval(
  /* in */ int32_t interval)
{
  s_estimatesUpdateInterval = interval > 0 ? interval : 0;
  return;
}

/*
 * Returns the interval, if any, used for updating method enforcement
 * cost estimates.  
 */
#undef __FUNC__
#define __FUNC__ "sidl_Enforcer_getEstimatesInterval"

#ifdef __cplusplus
extern "C"
#endif
int32_t 
sidl_Enforcer_getEstimatesInterval(void) 
{
  return s_estimatesUpdateInterval;
}
