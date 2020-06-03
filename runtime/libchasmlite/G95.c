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
 *
 * This code has been modified by Lawrence Livermore National Laboratory.
 * Every function/method/type/feature not needed by Babel has been deleted.
 * For the complete CHASM please see http://chasm-interop.sourceforge.net/.
 * -------------------------------------------------------------------------
 * LANL:license
 * -------------------------------------------------------------------------
 */
#include "G95.h"
#include "G95_dv.h"
#include <stdlib.h>

#define dope_vec dope_vec_G95

#ifdef __cplusplus
extern "C"{
#endif


/**
 * Resets the elements of a preallocated array descriptor.  The descriptor
 * must have been previously initialized by either setArrayDesc() or
 * createArrayDesc().  The rank, data type and element size of the array
 * MUST NOT change.  NOTE, assumes that, at least, ArrayDescSize() bytes
 * have been allocated in the desc pointer.
 *
 * @param desc           pointer to memory for the array descriptor
 *                       (caller must have allocated)
 * @param base_addr     the base address of the array
 * @param rank          the rank of the array
 * @param lowerBound    array[rank] of lower bounds for the array
 * @param extent        array[rank] of extents for the array (in elements)
 * @param strideMult    array[rank] of distances between successive elements
 *                      (in bytes)
 * @return              0 if successful (nonzero on error)
 */
static int
resetArrayDesc_G95(void* desc,
                   void* base_addr,
                   int rank,
                   const long* lowerBound,
                   const unsigned long* extent,
                   const long* strideMult
                   )
{
  int i;
  size_t offset = 0L;
  dope_vec *dv = (dope_vec*) desc;

  if (rank < 0 || rank > 7) return 1;
  if (rank != dv->rank) return 1;

  dv->base_addr = base_addr;

  for (i = 0; i < rank; i++) {
    dv->dim[i].stride_mult = strideMult[i];
    dv->dim[i].lower_bound = lowerBound[i];
    dv->dim[i].upper_bound = lowerBound[i] + extent[i] - 1;
  }

  for (i = 0; i < rank; i++) {
    if (dv->dim[i].stride_mult == 0) return 1;
    offset += dv->dim[i].lower_bound * dv->dim[i].stride_mult;
  }
  dv->offset = (char*) base_addr - offset;

  return 0;
}


/**
 * Sets the elements of a preallocated array descriptor.  This function is
 * used when passing a C array to Fortran.  NOTE, assumes that, at least,
 * ArrayDescSize bytes have been allocated in the desc pointer.
 *
 * @param desc           pointer to memory for the array descriptor
 *                       (caller must have allocated)
 * @param base_addr     the base address of the array
 * @param rank          the rank of the array
 * @param desc_type     type of the descriptor
 * @param data_type     data type of an array element
 * @param element_size  size of an array element
 * @param lowerBound    array[rank] of lower bounds for the array
 * @param extent        array[rank] of extents for the array (in elements)
 * @param strideMult    array[rank] of distances between successive elements
 *                      (in bytes)
 * @return              0 if successful (nonzero on error)
 */
static int 
setArrayDesc_G95(void* desc,
                 void* base_addr,
                 int rank,
                 F90_DescType desc_type,
                 F90_ArrayDataType data_type,
                 unsigned long element_size,
                 const long* lowerBound,
                 const unsigned long* extent,
                 const long* strideMult
                 )
{
  dope_vec *dv = (dope_vec*) desc;

  if (rank < 0 || rank > 7) return 1;

  dv->offset = 0x0;
  dv->elem_size = element_size;
  dv->rank = rank;

  return resetArrayDesc_G95(desc, base_addr, rank,
			    lowerBound, extent, strideMult);
}


/**
 * Returns the size of an array descriptor (in bytes).
 *
 * @param rank   the rank of the array
 * @return       descriptor size (in bytes)
 */
static unsigned long 
getArrayDescSize_G95(int rank)
{
  if (rank < 0 || rank > 7) return 0;

  return sizeof(dope_vec) - 3*(7-rank)*sizeof(int);
}


/**
 * Nullify an array descriptor (associated intrinsic will return false).
 *
 * @param desc   array descriptor 
 * @param rank   the rank of the array
 * @return       0 if successful (nonzero on error)
 */
static int
nullifyArrayDesc_G95(void* desc, int rank)
{
  dope_vec* dv = (dope_vec*) desc;
  dv->base_addr = 0x0;
  dv->offset = 0x0;
  return 0;
}


/**
 * Set CompilerCharacteristics function pointers for G95.
 */
void F90_SetCCFunctions_G95(F90_CompilerCharacteristics* cc)
{
  cc->setArrayDesc              = setArrayDesc_G95;
  cc->getArrayDescSize	        = getArrayDescSize_G95;
  cc->nullifyArrayDesc          = nullifyArrayDesc_G95;
}

#ifdef __cplusplus
}
#endif
