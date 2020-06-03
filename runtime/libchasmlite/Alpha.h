/** LANL:license
 * -------------------------------------------------------------------------
 * This SOFTWARE has been authored by an employee or employees of the
 * University of California, operator of the Los Alamos National Laboratory
 * under Contract No. W-7405-ENG-36 with the U.S. Department of Energy.
 * The U.S. Government has rights to use, reproduce, and distribute this
 * SOFTWARE.  The public may copy, distribute, prepare derivative works and
 * publicly display this SOFTWARE without charge, provided that this Notice
 * and any statement of authorship are reproduced on all copies.  Neither
 * the Government nor the University makes any warranty, express or implied,
 * or assumes any liability or responsibility for the use of this SOFTWARE.
 * If SOFTWARE is modified to produce derivative works, such modified
 * SOFTWARE should be clearly marked, so as not to confuse it with the
 * version available from LANL.
 * -------------------------------------------------------------------------
 * LANL:license
 * -------------------------------------------------------------------------
 *
 * This version of CHASM has been modified by Lawrence Livermore National
 * Lab.  All functions/methods/types/features not needed for Babel have been
 * deleted.
 */
#ifndef _ALPHA_H_
#define _ALPHA_H_

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
void F90_SetCCFunctions_Alpha(F90_CompilerCharacteristics* cc);


#ifdef __cplusplus
}
#endif

#endif /*_ALPHA_H_*/
