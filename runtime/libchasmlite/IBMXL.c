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
#include "IBMXL.h"
#include "IBMXL_dv.h"

#define dope_vec dope_vec_IBMXL
#define dope_vec_hidden dope_vec_hidden_IBMXL

#ifdef __cplusplus
extern "C"{
#endif


/**
 * Stage 1 of setting the elements of a preallocated array descriptor.
 *
 * NOTE, this function is static (private).
 *
 * @param desc           pointer to memory for the array descriptor
 *                       (caller must have allocated)
 * @param rank          the rank of the array
 * @param desc_type     type of the descriptor
 * @param data_type     data type of an array element
 * @param element_size  size of an array element
 * @return              0 if successful (nonzero on error)
 */
static int
setArrayDescStage1_IBMXL(void* desc,
                         int rank,
                         F90_DescType desc_type,
                         F90_ArrayDataType data_type,
                         unsigned long element_size
                         )
{
  int rc = 0;
  dope_vec* dv = (dope_vec*) desc;

  dv->zero = 0;
  dv->one  = 1;
#if CHASM_ARCH_64
  dv->flag64 = 1;
#else
  dv->flag64 = 0;
#endif

  switch (desc_type) {
    case F90_Array:			dv->not_ptr = 1;  break;
    case F90_Pointer:
    case F90_ArrayPointer:
    case F90_ArrayPointerInDerived:
    case F90_DerivedPointer:
    case F90_NonArray:
    default:				dv->not_ptr = 0;  break;
  }

  if (rank == 0) {
    dv->p_or_a    = 1;
    dv->cookie    = 4;   /* looks like 0D pointers may end here */
    dv->elem_size = 0;   /* from here on desc invalid on some tests */
    dv->rank      = 0;
    dv->sum_d     = 0;
  } else {
    dv->p_or_a    = 3;
    dv->cookie    = 5;
    dv->elem_size = element_size;
    dv->rank      = rank;
    dv->sum_d     = 0;
  }

  /*
   * WARNING, dv->cookie setting a bit questionable from here
   */
  switch (data_type) {
    case F90_Integer1:
    case F90_Integer2:
    case F90_Integer:
    case F90_Integer4:
    case F90_Integer8:
    case F90_Logical1:
    case F90_Logical2:
    case F90_Logical:
    case F90_Logical4:
    case F90_Logical8:
      if (dv->not_ptr) dv->cookie = 7;
      dv->type_code = 13;  break;
    case F90_Real:
    case F90_Double:
    case F90_QReal:
      if (dv->not_ptr) dv->cookie = 1;
      dv->type_code = 14;  break;
    case F90_Complex:
    case F90_DComplex:
    case F90_QComplex:
      if (dv->not_ptr) dv->cookie = 1;
      dv->type_code = 15;  break;
    case F90_Derived:
      if (dv->not_ptr) dv->cookie = 1;
      dv->type_code = 1;   break;
    case F90_Unknown:
      if (dv->not_ptr) dv->cookie = 1;
      dv->type_code = 1;   break;
    default:
      dv->type_code = 1;   break;
  }
  return rc;
}

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
resetArrayDesc_IBMXL(void* desc,
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

  dv->base_addr = base_addr;

  for (i = 0; i < rank; i++) {
    int ii = dv->rank - (i+1);
    dv->dim[ii].extent      = extent[i];
    dv->dim[ii].stride_mult = strideMult[i];
    dv->dim[ii].lower_bound = lowerBound[i];
  }

  for (i = 0; i < rank; i++) {
    offset += dv->dim[i].lower_bound * dv->dim[i].stride_mult;
  }
  dv->sum_d = -offset;

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
setArrayDesc_IBMXL(void* desc,
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
  int rc = 0;

  if (rank < 0 || rank > 7) return 1;

  rc = setArrayDescStage1_IBMXL(desc, rank, desc_type, data_type, element_size);
  if (rc) return rc;
  return resetArrayDesc_IBMXL(desc, base_addr, rank,
			      lowerBound, extent, strideMult);
}




/**
 * Returns the size of an array descriptor (in bytes).
 *
 * @param rank   the rank of the array
 * @return       descriptor size (in bytes)
 */
static unsigned long 
getArrayDescSize_IBMXL(int rank)
{
  if (rank < 0 || rank > 7) return 0L;
  return sizeof(dope_vec) - 3L*(7L-rank)*sizeof(long_type);
}


/**
 * Nullify an array descriptor (associated intrinsic will return false).
 *
 * @param desc   array descriptor 
 * @param rank   the rank of the array
 * @return       0 if successful (nonzero on error)
 */
static int
nullifyArrayDesc_IBMXL(void* desc, int rank)
{
  int i;
  dope_vec* dv = (dope_vec*) desc;

  if (rank < 0 || rank > 7) return 1L;

  dv->p_or_a    = 0;
  dv->type_code = 0;
  dv->cookie    = 0;
  dv->zero      = 0;
  dv->one       = 0;
  dv->flag64    = 0;
  dv->elem_size = 0;
  dv->rank      = 0;
  dv->sum_d     = 0;

  for (i = 0; i < rank; i++) {
    dv->dim[i].lower_bound = 0;
    dv->dim[i].extent      = 0;
    dv->dim[i].stride_mult = 0;
  }
  return 0;
}



/**
 * Set CompilerCharacteristics function pointers for IBMXL.
 */
void F90_SetCCFunctions_IBMXL(F90_CompilerCharacteristics* cc)
{
  cc->setArrayDesc              = setArrayDesc_IBMXL;
  cc->getArrayDescSize	        = getArrayDescSize_IBMXL;
  cc->nullifyArrayDesc          = nullifyArrayDesc_IBMXL;
}



#ifdef __cplusplus
}
#endif
