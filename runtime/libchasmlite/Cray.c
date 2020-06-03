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
#include "Cray.h"
#include "Cray_dv.h"

#define dope_vec dope_vec_Cray

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
setArrayDescStage1_Cray(void* desc,
                        int rank,
                        F90_DescType desc_type,
                        F90_ArrayDataType data_type,
                        unsigned long element_size
                        )
{
  int rc = 0;

  dope_vec* dv = (dope_vec*) desc;

  dv->rank      = rank;

  dv->elem_size = 8 * element_size;
  if (dv->elem_size < 32) dv->elem_size = 32;
  dv->max_len   = dv->elem_size;

  dv->assoc     = 1;
  dv->ptr_alloc = 0;
  dv->p_or_a    = 1;

  if (rank == 0) dv->a_contig = 0;
  else           dv->a_contig = 1;

  dv->dp_flag    = 0;
  dv->star_type  = 0;
  dv->kind_value = 0;

  if (dv->elem_size < 64)  dv->unused_1   = 36;
  else                     dv->unused_1   = 40;

  dv->unused_2   = 0;
  dv->unused_3   = 0;
  dv->unused_4   = 0;
  dv->unused_5   = 0;

  switch (data_type) {
    case F90_Integer1:
      dv->type_code = 2; dv->star_type = 3; dv->kind_value = 1; break;
    case F90_Integer2:
      dv->type_code = 2; dv->star_type = 3; dv->kind_value = 2; break;
    case F90_Integer:
      dv->type_code = 2; dv->star_type = 0; dv->kind_value = 0; break;
    case F90_Integer4:
      dv->type_code = 2; dv->star_type = 3; dv->kind_value = 4; break;
    case F90_Integer8:
      dv->type_code = 2; dv->star_type = 3; dv->kind_value = 8; break;
    case F90_Logical1:
      dv->type_code = 5; dv->star_type = 3; dv->kind_value = 1; break;
    case F90_Logical2:
      dv->type_code = 5; dv->star_type = 3; dv->kind_value = 2; break;
    case F90_Logical:
      dv->type_code = 5; dv->star_type = 0; dv->kind_value = 0; break;
    case F90_Logical4:
      dv->type_code = 5; dv->star_type = 3; dv->kind_value = 4; break;
    case F90_Logical8:
      dv->type_code = 5; dv->star_type = 3; dv->kind_value = 8; break;
    case F90_Real:
      dv->type_code = 3; dv->star_type = 0; dv->kind_value = 0; break;
    case F90_Double:
      dv->type_code = 3; dv->star_type = 4; dv->kind_value = 8; break;
    case F90_QReal:
      dv->type_code = 3; dv->star_type = 3; dv->kind_value = 16; break;
    case F90_Complex:
      dv->type_code = 4; dv->star_type = 0; dv->kind_value = 0; break;
    case F90_DComplex:
      dv->type_code = 4; dv->star_type = 1; dv->kind_value = 8; break;
    case F90_QComplex:
      dv->type_code = 4; dv->star_type = 3; dv->kind_value = 32; break;
    case F90_Derived:
      dv->type_code = 8; dv->elem_size = 0; break;
    case F90_Unknown:
      dv->type_code = 1;  break;
    default:
      dv->type_code = 1;  break;
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
resetArrayDesc_Cray(void* desc,
                    void* base_addr,
                    int rank,
                    const long* lowerBound,
                    const unsigned long* extent,
                    const long* strideMult
                    )
{
  int i;
  dope_vec* dv = (dope_vec*) desc;
  
  if (rank < 0 || rank > 7) return 1;

  dv->assoc = 1;
  if (rank == 0) dv->a_contig = 0;
  else           dv->a_contig = 1;

  dv->base_addr = (long) base_addr;
  for (i = 0; i < rank; i++) {
    dv->dim[i].lower_bound = lowerBound[i];
    dv->dim[i].extent      = extent[i];
    if (dv->max_len < 64) {
      dv->dim[i].stride_mult = strideMult[i]/4L;
    } else {
      dv->dim[i].stride_mult = strideMult[i]/8L;
    }
  }
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
setArrayDesc_Cray(void* desc,
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

  rc = setArrayDescStage1_Cray(desc, rank, desc_type, data_type, element_size);
  if (rc) return rc;
  return resetArrayDesc_Cray(desc, base_addr, rank,
			     lowerBound, extent, strideMult);
}



/**
 * Returns the size of an array descriptor (in bytes).
 *
 * @param rank   the rank of the array
 * @return       descriptor size (in bytes)
 */
static unsigned long
getArrayDescSize_Cray(int rank)
{
  if (rank < 0 || rank > 7) return 0;

  return sizeof(dope_vec) - 3*(7-rank)*sizeof(long);
}


/**
 * Nullify an array descriptor (associated intrinsic will return false).
 *
 * @param desc   array descriptor 
 * @param rank   the rank of the array
 * @return       0 if successful (nonzero on error)
 */
int nullifyArrayDesc_Cray(void* desc, int rank)
{
  dope_vec* dv = (dope_vec*) desc;
  dv->assoc = 0;
  return 0;
}

/**
 * Set CompilerCharacteristics function pointers for Cray.
 */
void F90_SetCCFunctions_Cray(F90_CompilerCharacteristics* cc)
{
  cc->setArrayDesc              = setArrayDesc_Cray;
  cc->getArrayDescSize	        = getArrayDescSize_Cray;
  cc->nullifyArrayDesc          = nullifyArrayDesc_Cray;
}



#ifdef __cplusplus
}
#endif
