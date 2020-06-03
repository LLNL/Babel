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
 * This version has been modified by Lawrence Livermore National Laboratory.
 * All the functions/methods/types/features not needed for Babel have been
 * deleted. For the full version of chasm, please see
 * http://chasm-interop.sourceforge.net/.
 */
#ifndef _CompilerCharacteristics_h_
#define _CompilerCharacteristics_h_

#include "F90ArrayDataType.h"

#ifdef __cplusplus
extern "C" {
#endif


/**
 * F90_DescType describes the type of the array descriptor.  The descriptor
 * layout can depend on how the array-valued paramater is declared.
 */
typedef enum
{
  F90_Array,		    /**< an array (e.g., integer :: a(:)) */
  F90_Pointer,		    /**< a pointer (e.g., integer, pointer :: p) */
  F90_ArrayPointer,	   /**< an array pointer
                                (e.g., integer, pointer :: pa(:) )*/
  F90_ArrayPointerInDerived,/**< an array pointer within a derived type */
  F90_DerivedPointer,	    /**< pointer to a derived type */
  F90_NonArray		    /**< not an array or pointer type */
} F90_DescType;


/**
 * CompilerCharacteristics contains function pointers that provide a generic
 * interface to the array descriptor library.
 *
 * This struct contains a pointer for each function that is needed for
 * manipulating fortran array descriptors. The
 * F90_SetCompilerCharacteristics() function is used to initialize the
 * function pointers to the correct vendor-specific function.
 *
 * @see F90_SetCompilerCharacteristics()
 */
typedef struct
{
  int (*setArrayDesc) (void* desc,
		       void* base_addr,
		       int rank,
		       F90_DescType kind,
		       F90_ArrayDataType data_type,
		       unsigned long element_size,
		       const long* lowerBound,
		       const unsigned long* extent,
		       const long* strideMult
		       );

  unsigned long (*getArrayDescSize)   (int rank);

  int (*nullifyArrayDesc) (void* desc, int rank);
} F90_CompilerCharacteristics;


/**
 * Sets the CompilerCharacteristics function pointers for the given compiler.
 * Delegates the actual setting of function pointers to the vendor-specific
 * function chasmlite_SetCCFunctions_F90Vendor().  The current set of supported
 * compilers is {"Absoft", "Alpha", "IBMXL", "Intel", "Lahey", "MIPSpro",
 * "NAG", "SUNWspro"}.
 *
 * @param  cc        the CompilerCharacteristics struct
 * @param  compiler  the name of the compiler
 * @return 0         if successful (nonzero on error)
 */
int 
chasmlite_SetCompilerCharacteristics(F90_CompilerCharacteristics* cc,
                                     const char* compiler
                                     );


#ifdef __cplusplus
} /* extern "C" */
#endif

#endif /* _CompilerCharacteristics_h_ */
