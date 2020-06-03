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
#include "GNU.h"
#include "GNU_dv.h"
#include <stdlib.h>

#define dope_vec dope_vec_GNU

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
resetArrayDesc_GNU(void* desc,
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
  if (rank != GFC_DESCRIPTOR_RANK(dv)) return 1;

  dv->base_addr = base_addr;

  for (i = 0; i < rank; i++) {
    dv->dim[i].stride_mult = strideMult[i] / GFC_DESCRIPTOR_SIZE(dv);
    dv->dim[i].lower_bound = lowerBound[i];
    dv->dim[i].upper_bound = lowerBound[i] + extent[i] - 1;
  }

  for (i = 0; i < rank; i++) {
    if (dv->dim[i].stride_mult == 0) return 1;
    offset += dv->dim[i].lower_bound * dv->dim[i].stride_mult;
  }
  dv->base = (void*) -offset;

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
setArrayDesc_GNU(void* desc,
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
  size_t dtype_rank, dtype_type, dtype_size;
  dope_vec *dv = (dope_vec*) desc;

  if (rank < 0 || rank > 7) return 1;

  dv->base = 0x0;	/* base offset */

  dtype_rank = rank;
  dtype_size = element_size;

  switch (data_type) {
    case F90_Integer1:
    case F90_Integer2:
    case F90_Integer:
    case F90_Integer4:
    case F90_Integer8:
      dtype_type = GFC_DTYPE_INTEGER;  break;
    case F90_Logical1:
    case F90_Logical2:
    case F90_Logical:
    case F90_Logical4:
    case F90_Logical8:
      dtype_type = GFC_DTYPE_LOGICAL;  break;
    case F90_Real:
    case F90_Double:
    case F90_QReal:
      dtype_type = GFC_DTYPE_REAL;  break;
    case F90_Complex:
    case F90_DComplex:
    case F90_QComplex:
      dtype_type = GFC_DTYPE_COMPLEX;  break;
    case F90_Derived:
      dtype_type = GFC_DTYPE_DERIVED;  break;
    case F90_Unknown:
    default:
      dtype_type = GFC_DTYPE_UNKNOWN;  break;
  }

  dtype_rank = dtype_rank & GFC_DTYPE_RANK_MASK;
  dtype_type = (dtype_type << GFC_DTYPE_TYPE_SHIFT) & GFC_DTYPE_TYPE_MASK;
  dtype_size = (dtype_size << GFC_DTYPE_SIZE_SHIFT);

  dv->dtype = dtype_size | dtype_type | dtype_rank;

  return resetArrayDesc_GNU(desc, base_addr, rank,
			    lowerBound, extent, strideMult);
}



/**
 * Returns the size of an array descriptor (in bytes).
 *
 * @param rank   the rank of the array
 * @return       descriptor size (in bytes)
 */
static unsigned long
getArrayDescSize_GNU(int rank)
{
  if (rank < 0 || rank > 7) return 0;

  return sizeof(dope_vec) - 3*(7-rank)*sizeof(size_t);
}


/**
 * Nullify an array descriptor (associated intrinsic will return false).
 *
 * @param desc   array descriptor 
 * @param rank   the rank of the array
 * @return       0 if successful (nonzero on error)
 */
static int 
nullifyArrayDesc_GNU(void* desc, int rank)
{
  dope_vec* dv = (dope_vec*) desc;
  dv->base_addr = 0x0;
  dv->base = 0x0;
  return 0;
}


/**
 * Set CompilerCharacteristics function pointers for GNU.
 */
void F90_SetCCFunctions_GNU(F90_CompilerCharacteristics* cc)
{
  cc->setArrayDesc              = setArrayDesc_GNU;
  cc->getArrayDescSize	        = getArrayDescSize_GNU;
  cc->nullifyArrayDesc          = nullifyArrayDesc_GNU;
}


#ifdef __cplusplus
}
#endif
