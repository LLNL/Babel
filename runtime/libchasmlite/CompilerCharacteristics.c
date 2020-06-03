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
 * This code has been modified by Lawrence Livermore National Laboratory.
 * Every function/method/type/feature not needed by Babel has been deleted.
 * For the complete CHASM please see http://chasm-interop.sourceforge.net/.
 */

#include "CompilerCharacteristics.h"
#include "Absoft.h"
#include "Alpha.h"
#include "Cray.h"
#include "GNU.h"
#include "G95.h"
#include "IBMXL.h"
#include "Intel.h"
#include "Intel_7.h"
#include "Lahey.h"
#include "MIPSpro.h"
#include "NAG.h"
#include "PGI.h"
#include "pathscale.h"
#include "SUNWspro.h"
#include <string.h>

#ifdef __cplusplus
extern "C"{
#endif


/**
 * Sets the CompilerCharacteristics function pointers for the given compiler.
 * Returns 1 on error, 0 otherwise.
 *
 * cc - the CompilerCharacteristics struct
 * compiler - the name of the compiler
 */
int chasmlite_SetCompilerCharacteristics(F90_CompilerCharacteristics* cc,
				   const char* compiler
				   )
{
  if      ( strcmp(compiler, "Absoft")   == 0 ) F90_SetCCFunctions_Absoft(cc);
  else if ( strcmp(compiler, "Alpha")    == 0 ) F90_SetCCFunctions_Alpha(cc);
  else if ( strcmp(compiler, "Cray")     == 0 ) F90_SetCCFunctions_Cray(cc);
  else if ( strcmp(compiler, "GNU")      == 0 ) F90_SetCCFunctions_GNU(cc);
  else if ( strcmp(compiler, "G95")      == 0 ) F90_SetCCFunctions_G95(cc);
  else if ( strcmp(compiler, "IBMXL")    == 0 ) F90_SetCCFunctions_IBMXL(cc);
  else if ( strcmp(compiler, "Intel")    == 0 ) F90_SetCCFunctions_Intel(cc);
  else if ( strcmp(compiler, "Intel_7")  == 0 ) F90_SetCCFunctions_Intel_7(cc);
  else if ( strcmp(compiler, "Lahey")    == 0 ) F90_SetCCFunctions_Lahey(cc);
  else if ( strcmp(compiler, "MIPSpro")  == 0 ) F90_SetCCFunctions_MIPSpro(cc);
  else if ( strcmp(compiler, "NAG")      == 0 ) F90_SetCCFunctions_NAG(cc);
  else if ( strcmp(compiler, "PGI")      == 0 ) F90_SetCCFunctions_PGI(cc);
  else if ( strcmp(compiler, "SUNWspro") == 0 ) F90_SetCCFunctions_SUNWspro(cc);
  else if ( strcmp(compiler, "PathScale") == 0 ) F90_SetCCFunctions_PathScale(cc);
  else {
    return 1;
  }
  return 0;
}

#ifdef __cplusplus
}
#endif
