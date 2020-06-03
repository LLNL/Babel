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
#include "Intel.h"
#include "Intel_dv.h"

#define dope_vec dope_vec_Intel

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
resetArrayDesc_Intel(void* desc,
                     void* base_addr,
                     int rank,
                     const long* lowerBound,
                     const unsigned long* extent,
                     const long* strideMult
                     )
{
  int i;
  long offset = 0L;
  dope_vec* dv = (dope_vec*) desc;

  if (rank < 0 || rank > 7) return 1;

  if (rank > 0) {
    dv->assoc     = 1;
    dv->ptr_alloc = 1;
    dv->p_or_a    = 1;
  }

  dv->base_addr = base_addr;
  for (i = 0; i < rank; i++) {
    dv->dim[i].extent      = extent[i];
    dv->dim[i].stride_mult = strideMult[i];
    dv->dim[i].lower_bound = lowerBound[i];
    offset += dv->dim[i].lower_bound * dv->dim[i].stride_mult;
  }
  dv->offset = -offset;

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
setArrayDesc_Intel(void* desc,
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
  dope_vec* dv = (dope_vec*) desc;

  if (rank < 0 || rank > 7) return 1;

  dv->rank        = rank;
  dv->offset      = 0;			/* set later in reset */

  if (rank == 0) {
    dv->elem_size = 0;
    dv->assoc     = 0;
    dv->ptr_alloc = 0;
    dv->p_or_a    = 0;
  } else {
    dv->elem_size = element_size;
    dv->assoc     = 1;
    dv->ptr_alloc = 1;
    dv->p_or_a    = 1;
  }

  dv->non_contig  = 0;
  dv->reserved_1  = 0;
  dv->reserved_2  = 0;
  dv->reserved_3  = 0;

#if CHASM_ARCH_64
  dv->reserved64  = 0;
#endif

  return resetArrayDesc_Intel(desc, base_addr, rank,
			      lowerBound, extent, strideMult);
}


/**
 * Returns the size of an array descriptor (in bytes).
 *
 * @param rank   the rank of the array
 * @return       descriptor size (in bytes)
 */
static unsigned long 
getArrayDescSize_Intel(int rank)
{
  if (rank < 0 || rank > 7) return 0L;
  return sizeof(dope_vec) - 3*(7-rank)*sizeof(long);
}


/**
 * Nullify an array descriptor (associated intrinsic will return false).
 *
 * @param desc   array descriptor 
 * @param rank   the rank of the array
 * @return       0 if successful (nonzero on error)
 */
static int 
nullifyArrayDesc_Intel(void* desc, int rank)
{
  dope_vec* dv = (dope_vec*) desc;
  dv->assoc = 0;

  if (rank == 0) {
    dv->base_addr = 0x0;
  }

  return 0;
}

/**
 * Set CompilerCharacteristics function pointers for Intel.
 */
void F90_SetCCFunctions_Intel(F90_CompilerCharacteristics* cc)
{
  cc->setArrayDesc              = setArrayDesc_Intel;
  cc->getArrayDescSize	        = getArrayDescSize_Intel;
  cc->nullifyArrayDesc          = nullifyArrayDesc_Intel;
}


#ifdef __cplusplus
}
#endif
