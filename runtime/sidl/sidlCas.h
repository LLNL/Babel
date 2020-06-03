#ifndef __SIDL_CAS_H__
#define __SIDL_CAS_H__

#ifndef included_babel_config_h
#include "babel_config.h"
#endif

/* So far, compare and swap is only defined for x86 */
#if defined(BABEL_PROCESSOR_GNU)
#define CAS32(_a, _o, _n) __sync_val_compare_and_swap((_a), (_o), (_n))
#define CAS64(_a, _o, _n) __sync_val_compare_and_swap((_a), (_o), (_n))
#elif defined(BABEL_PROCESSOR_SPARC)
/*#include "sparc_defns.h"*/
#elif defined(BABEL_PROCESSOR_X86)
#define CAS32(_a, _o, _n)                                  \
({ __typeof__(_o) __o = _o;                                \
   __asm__ __volatile__(                                   \
       "lock cmpxchg %3,%1"                                \
       : "=a" (__o), "=m" (*(volatile unsigned int *)(_a)) \
       :  "0" (__o), "r" (_n) );                           \
   __o;                                                    \
})
#define CAS64(_a, _o, _n)                                       \
({ __typeof__(_o) __o = _o;                                     \
  __asm__ __volatile__(                                         \
      "movl %3, %%ecx;"                                         \
      "movl %4, %%ebx;"                                         \
      "lock cmpxchg8b %1"                                       \
      : "=A" (__o), "=m" (*(volatile unsigned long long *)(_a)) \
      : "0" (__o), "m" (_n >> 32), "m" (_n)                     \
      : "ebx", "ecx" );                                         \
  __o;                                                          \
})
#elif defined(BABEL_PROCESSOR_PPC)
/*#include "ppc_defns.h"*/
#elif defined(BABEL_PROCESSOR_IA64)
/*#include "ia64_defns.h"*/
#elif defined(BABEL_PROCESSOR_MIPS)
/*#include "mips_defns.h"*/
#elif defined(BABEL_PROCESSOR_ALPHA)
/*#include "alpha_defns.h"*/
#endif

#endif /* __SIDL_CAS_H__ */
