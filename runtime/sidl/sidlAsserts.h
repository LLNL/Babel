/*
 * File:        sidlAsserts.h
 * Revision:    @(#) $Revision: 7427 $
 * Date:        $Date: 2011-12-16 15:16:22 -0800 (Fri, 16 Dec 2011) $
 * Description: convenience C macros for managing SIDL Assertions
 *
 * Copyright (c) 2004-2007, Lawrence Livermore National Security, LLC
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
 *
 ****************************************************************************
 * WARNINGS:
 * 1) SIDL_FULL_ASTATS
 *    This macro is used here to determine whether a full set of statistics
 *    are going to be employed or only the bare minimum.  It is assumed that
 *    this macro is kept in sync with the contents of S_FULL_STATS_MACRO in 
 *    IOR.java.
 ****************************************************************************
 *
 * The following include files are needed:
 *    math.h                   For the ceiling function used by the 
 *                               random and timing-based policies.
 *    stdlib.h                 For random number generation (including 
 *                               RAND_MAX).
 *    time.h                   For processing associated with the 
 *                               timing-based policy.
 *    sidl_PreViolation.h   
 *    sidl_PostViolation.h 
 *    sidl_InvViolation.h      For ease-of-use since this (single) header
 *                               is currently providing both the IOR
 *                               and (C) applications with enforcement
 *                               options.
 */

#ifndef included_sidlAsserts_h
#define included_sidlAsserts_h
#include <math.h>
#include <sys/time.h>
#include <stdlib.h>
#ifndef included_sidl_PreViolation_h
#include "sidl_PreViolation.h"
#endif
#ifndef included_sidl_PostViolation_h
#include "sidl_PostViolation.h"
#endif
#ifndef included_sidl_InvViolation_h
#include "sidl_InvViolation.h"
#endif

/****************************************************************************
 * SIDL Assertion macros originally intended for use in generated interface
 * contract enforcement routines.
 ****************************************************************************/

/*
 * SIDL_ARRAY_ALL_BOTH	all(a1 r a2), where a1 and a2 are arrays, r is the
 *                      relation
 */
#define SIDL_ARRAY_ALL_BOTH(AC1, AV1, AC2, AV2, REL, I, NUM, C, BRES) \
   SIDL_ARRAY_COUNT_BOTH(AC1, AV1, AC2, AV2, REL, I, NUM, C);\
   BRES = (C == NUM);\
}

/*
 * SIDL_ARRAY_ALL_VL	all(vr a), where a is array, vr is value + relation
 */
#define SIDL_ARRAY_ALL_VL(AC, AV, REL, I, NUM, C, BRES) {\
   SIDL_ARRAY_COUNT_VL(AC, AV, REL, I, NUM, C);\
   BRES = (C == NUM);\
}

/*
 * SIDL_ARRAY_ALL_VR	all(a rv), where a is array, rv is relation + value
 */
#define SIDL_ARRAY_ALL_VR(AC, AV, REL, I, NUM, C, BRES) {\
   SIDL_ARRAY_COUNT_VR(AC, AV, REL, I, NUM, C);\
   BRES = (C == NUM);\
}

/*
 * SIDL_ARRAY_ANY_BOTH	any(a1 r a2), where a1 and a2 are arrays, r is the
 *                      relation
 *
 *   NOTE: Will return FALSE if the arrays are not the same size.
 */
#define SIDL_ARRAY_ANY_BOTH(AC1, AV1, AC2, AV2, REL, I, NUM, BRES) {\
   BRES = FALSE;\
   NUM  = SIDL_ARRAY_SIZE(AC1, (AV1));\
   if (SIDL_ARRAY_SIZE(AC2, (AV2)) == NUM) {\
     for (I=0; (I<NUM) && (!BRES); I++) {\
       SIDL_INCR_IF_TRUE((AC1##_get1((AV1),I) REL AC2##_get1((AV2),I)), BRES)\
     }\
   }\
}

/*
 * SIDL_ARRAY_ANY_VL	any(vr a), where a is array, vr is value + relation
 */
#define SIDL_ARRAY_ANY_VL(AC, AV, REL, I, NUM, BRES) {\
   BRES = FALSE;\
   NUM  = SIDL_ARRAY_SIZE(AC, (AV));\
   for (I=0; (I<NUM) && (!BRES); I++) {\
     SIDL_INCR_IF_TRUE((REL AC##_get1((AV),I)), BRES)\
   }\
}

/*
 * SIDL_ARRAY_ANY_VR	any(a rv), where a is array, rv is relation + value
 */
#define SIDL_ARRAY_ANY_VR(AC, AV, REL, I, NUM, BRES) {\
   BRES = FALSE;\
   NUM  = SIDL_ARRAY_SIZE(AC, (AV));\
   for (I=0; (I<NUM) && (!BRES); I++) {\
     SIDL_INCR_IF_TRUE((AC##_get1((AV),I) REL), BRES)\
   }\
}

/*
 * SIDL_ARRAY_COUNT_BOTH  count(a1 r a2), where a1 and a2 are arrays, r is 
 *                        the relation.
 *
 *   NOTE: Will return FALSE if the arrays are not the same size.
 */
#define SIDL_ARRAY_COUNT_BOTH(AC1, AV1, AC2, AV2, REL, I, NUM, IRES) {\
   IRES = 0;\
   NUM = SIDL_ARRAY_SIZE(AC1, (AV1));\
   if (SIDL_ARRAY_SIZE(AC2, (AV2)) == NUM) {\
     for (I=0; I<NUM; I++) {\
       SIDL_INCR_IF_TRUE((AC1##_get1((AV1),I) REL AC2##_get1((AV2),I)), IRES)\
     }\
   }\
}

/*
 * SIDL_ARRAY_COUNT_VL	count(vr a), where a is array, vr is value + relation
 */
#define SIDL_ARRAY_COUNT_VL(AC, AV, REL, I, NUM, IRES) {\
   IRES = 0;\
   NUM = SIDL_ARRAY_SIZE(AC, (AV));\
   for (I=0; I<NUM; I++) {\
     SIDL_INCR_IF_TRUE((REL AC##_get1((AV),I)), IRES)\
   }\
}

/*
 * SIDL_ARRAY_COUNT_VR	count(a rv), where a is array, rv is relation + value
 */
#define SIDL_ARRAY_COUNT_VR(AC, AV, REL, I, NUM, IRES) {\
   IRES = 0;\
   NUM = SIDL_ARRAY_SIZE(AC, (AV));\
   for (I=0; I<NUM; I++) {\
     SIDL_INCR_IF_TRUE((AC##_get1((AV),I) REL), IRES)\
   }\
}

/*
 * SIDL_ARRAY_DIMEN	dimen(a), where a is the array
 */
#define SIDL_ARRAY_DIMEN(AC, AV) sidlArrayDim(AV)

/*
 * SIDL_ARRAY_IRANGE	irange(a, v1, v2), where a is array whose integer
 *                      values are to be in v1..v2.
 */
#define SIDL_ARRAY_IRANGE(AC, AV, V1, V2, I, NUM, C, BRES) {\
   C   = 0;\
   NUM = SIDL_ARRAY_SIZE(AC, (AV));\
   for (I=0; I<NUM; I++) {\
     SIDL_INCR_IF_TRUE(\
       SIDL_IRANGE((double)AC##_get1((AV),I), (double)V1, (double)V2), C)\
   }\
   BRES = (C == NUM);\
}

/*
 * SIDL_ARRAY_LOWER	lower(a, d), where a is the array and d is the dimension
 */
#define SIDL_ARRAY_LOWER(AC, AV, D) AC##_lower((AV), D)

/*
 * SIDL_ARRAY_MAX	max(a), where a is the array of scalar
 */
#define SIDL_ARRAY_MAX(AC, AV, I, NUM, T, RES) {\
   RES  = AC##_get1((AV),0);\
   NUM = SIDL_ARRAY_SIZE(AC, (AV));\
   for (I=1; I<NUM; I++) {\
     T _SAMAXV = AC##_get1((AV),I);\
     if (_SAMAXV > RES) { RES = _SAMAXV; } \
   }\
}

/*
 * SIDL_ARRAY_MIN	min(a), where a is the array of scalar
 */
#define SIDL_ARRAY_MIN(AC, AV, I, NUM, T, RES) {\
   RES  = AC##_get1((AV),0);\
   NUM = SIDL_ARRAY_SIZE(AC, (AV));\
   for (I=1; I<NUM; I++) {\
     T _SAMINV = AC##_get1((AV),I);\
     if (_SAMINV < RES) { RES = _SAMINV; } \
   }\
}

/*
 * SIDL_ARRAY_NEAR_EQUAL	nearEqual(a, b, tol), where a and b are arrays
 *                              whose scalar values are to be compared.
 */
#define SIDL_ARRAY_NEAR_EQUAL(AC1, AV1, AC2, AV2, TOL, I, NUM, C, BRES) {\
   C = 0;\
   NUM = SIDL_ARRAY_SIZE(AC1, (AV1));\
   for (I=0; I<NUM; I++) {\
     SIDL_INCR_IF_TRUE(\
       SIDL_NEAR_EQUAL(AC1##_get1((AV1),I), AC2##_get1((AV2),I), TOL), C)\
   }\
   BRES = (C == NUM);\
}

/*
 * SIDL_ARRAY_NON_INCR 	nonIncr(a), where a is array of numeric values
 *                      to be checked for being in decreasing order.
 */
#define SIDL_ARRAY_NON_INCR(AC, AV, I, NUM, V, BRES) {\
   BRES = TRUE;\
   V    = ((AV) != NULL) ? (double) AC##_get1((AV),0) : 0.0;\
   NUM  = SIDL_ARRAY_SIZE(AC, (AV));\
   for (I=0; (I<NUM) && (BRES); I++) {\
     if ((double)AC##_get1((AV),I) > V) {\
       BRES = FALSE; \
     } else {\
       V = (double) AC##_get1((AV),0);\
     }\
   }\
}

/*
 * SIDL_ARRAY_NONE_BOTH		none(a1 r a2), where a1 and a2 are arrays, r is
 *                       	the relation.
 */
#define SIDL_ARRAY_NONE_BOTH(AC1, AV1, AC2, AV2, REL, I, NUM, C, BRES) {\
   SIDL_ARRAY_COUNT_BOTH(AC1, AV1, AC2, AV2, REL, I, NUM, C);\
   BRES = (C == 0);\
}

/*
 * SIDL_ARRAY_NONE_VL	none(vr a), where a is array, vr is value + relation
 */
#define SIDL_ARRAY_NONE_VL(AC, AV, REL, I, NUM, C, BRES) {\
   SIDL_ARRAY_COUNT_VL(AC, AV, REL, I, NUM, C);\
   BRES = (C == 0);\
}

/*
 * SIDL_ARRAY_NONE_VR	none(a rv), where a is array, rv is relation + value
 */
#define SIDL_ARRAY_NONE_VR(AC, AV, REL, I, NUM, C, BRES) {\
   SIDL_ARRAY_COUNT_VR(AC, AV, REL, I, NUM, C);\
   BRES = (C == 0);\
}

/*
 * SIDL_ARRAY_RANGE	range(a, v1, v2, tol), where a is array whose scalar
 *                      values are to be in v1..v2 within tolerance tol.
 */
#define SIDL_ARRAY_RANGE(AC, AV, V1, V2, TOL, I, NUM, C, BRES) {\
   C   = 0;\
   NUM = SIDL_ARRAY_SIZE(AC, (AV));\
   for (I=0; I<NUM; I++) {\
     SIDL_INCR_IF_TRUE(\
       SIDL_RANGE((double)AC##_get1((AV),I), (double)V1, (double)V2, TOL), C)\
   }\
   BRES = (C == NUM);\
}

/*
 * SIDL_ARRAY_SIZE	size(a), where a is the array 
 */
#define SIDL_ARRAY_SIZE(AC, AV) sidlLength(AV, 0)

/*
 * SIDL_ARRAY_STRIDE	stride(a, d), where a is the array and d is the 
 *                      dimension
 */
#define SIDL_ARRAY_STRIDE(AC, AV, D) sidlStride(AV, D)

/*
 * SIDL_ARRAY_SUM	sum(a), where a is the array of scalar
 */
#define SIDL_ARRAY_SUM(AC, AV, I, NUM, RES) {\
   RES = AC##_get1((AV),0);\
   NUM = SIDL_ARRAY_SIZE(AC, (AV));\
   for (I=1; I<NUM; I++) { RES += AC##_get1((AV),I); }\
}

/*
 * SIDL_ARRAY_NON_DECR	nonDecr(a), where a is array of numeric values
 *                              to be checked for being in increasing order.
 */
#define SIDL_ARRAY_NON_DECR(AC, AV, I, NUM, V, BRES) {\
   BRES = TRUE;\
   V    = ((AV) != NULL) ? (double) AC##_get1((AV), 0) : 0.0;\
   NUM  = SIDL_ARRAY_SIZE(AC, (AV));\
   for (I=0; (I<NUM) && (BRES); I++) {\
     if ((double)AC##_get1((AV),I) < V) {\
       BRES = FALSE; \
     } else {\
       V = (double) AC##_get1((AV), 0);\
     }\
   }\
}

/*
 * SIDL_ARRAY_UPPER	upper(a, d), where a is the array and d is the dimension
 */
#define SIDL_ARRAY_UPPER(AC, AV, D) sidlUpper(AV, D)

/*
 * SIDL_IRANGE		irange(v, v1, v2), where determine if v in the 
 *                      range v1..v2.
 */
#define SIDL_IRANGE(V, V1, V2) \
   (  ((double)V1 <= (double)V) && ((double)V  <= (double)V2) ) 

/*
 * SIDL_NEAR_EQUAL	nearEqual(v1, v2, tol), where v1 and v2 are scalars 
 *                      being checked for being equal within the specified 
 *                      tolerance, tol.
 */
#define SIDL_NEAR_EQUAL(V1, V2, TOL)  \
   (fabs((double)V1 - (double)V2) <= (double)TOL)

/*
 * SIDL_RANGE		range(v, v1, v2, tol), where determine if v in
 *                      the range v1..v2, within the specified tolerance, tol.
 */
#define SIDL_RANGE(V, V1, V2, TOL) {\
   (  (((double)V1 - (double)TOL) <= (double)V) \
   && ((double)V                  <= ((double)V2 + (double)TOL)) ) \
}


/****************************************************************************
 * Additional macros
 ****************************************************************************/

/*
 *  SIDL_DIFF_MICROSECONDS	"Standard" time difference
 */
#define SIDL_DIFF_MICROSECONDS(T2, T1) \
  (1.0e6*(double)(T2.tv_sec-T1.tv_sec)) + (T2.tv_usec-T1.tv_usec)

/*
 *  SIDL_INCR_IF_THEN		Increment V1 if EXPR is TRUE; otherwise,
 *                              increment V2.
 */
#define SIDL_INCR_IF_THEN(EXPR, V1, V2) \
  if (EXPR) { (V1) += 1; } else { (V2) += 1; }

/*
 *  SIDL_INCR_IF_TRUE		Increment V if EXPR is TRUE.
 */
#define SIDL_INCR_IF_TRUE(EXPR, V)  if (EXPR) { (V) += 1; } 

#endif /* included_sidlAsserts_h */
