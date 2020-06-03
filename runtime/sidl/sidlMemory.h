/*
 * File:        memory.h
 * Copyright:   (c) 2004 Lawrence Livermore National Security, LLC
 * Revision:    @(#) $Revision: 6171 $
 * Date:        $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
 * Description: Replacement memory allocation functions
 *
 */

#ifndef included_sidlMemory_h
#define included_sidlMemory_h
#include "babel_config.h"
#ifdef __cplusplus
extern "C" {
#endif
#ifdef malloc
#undef malloc
#endif
#ifdef realloc
#undef realloc
#endif
#include <sys/types.h>

#if ! HAVE_MALLOC
void *rpl_malloc(size_t n);
#endif

#if ! HAVE_REALLOC
void *rpl_realloc(void *ptr, size_t n);
#endif

#ifdef __cplusplus
}
#endif
#endif /*  included_sidlMemory_h */
