/*
 * File:        pathscale.h
 * Copyright:   (c) 2008 Lawrence Livermore National Security, LLC
 * Revision:    @(#) $Revision$
 * Date:        $Date$
 * Description: 
 *
 */

#ifndef _PATHSCALE_H_
#define _PATHSCALE_H_
#include "CompilerCharacteristics.h"

#ifdef __cplusplus
extern "C" {
#endif

/**
 * Set CompilerCharacteristics function pointers for a particular compiler.
 *
 * This function is called by the generic (and global)
 * F90_SetCompilerCharacteristics() function to provide a generic interface
 * to the Chasm array-descriptor library.
 *
 * @param cc  the F90_CompilerCharacteristics struct (out parameter).
 */
void F90_SetCCFunctions_PathScale(F90_CompilerCharacteristics* cc);


#ifdef __cplusplus
}
#endif

#endif /* _PATHSCALE_H_ */

