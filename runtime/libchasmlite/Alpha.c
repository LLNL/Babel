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
#include "Alpha.h"
#include "Alpha_dv.h"


#define dope_vec   dope_vec_Alpha
#define dope_vec0d dope_vec0d_Alpha

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
static int setArrayDescStage1_Alpha(void* desc,
				    int rank,
				    F90_DescType desc_type,
				    F90_ArrayDataType data_type,
				    unsigned long element_size
				    )
{
  int rc = 0;
  dope_vec* dv = (dope_vec*) desc;

  dv->rank       = rank;
  dv->one        = 1;
  dv->ptr        = 1;
  dv->alloc      = 1;
  dv->zero       = 0;
  dv->elem_size  = element_size;

  dv->reserved1 = 10;

  switch (data_type) {
    case F90_Integer1:	dv->type_code = 1; break;
    case F90_Integer2:	dv->type_code = 2; break;
    case F90_Integer:	dv->type_code = 3; break;
    case F90_Integer4:	dv->type_code = 3; break;
    case F90_Integer8:	dv->type_code = 4; break;
    case F90_Logical1:	dv->type_code = 5; break;
    case F90_Logical2:	dv->type_code = 6; break;
    case F90_Logical:	dv->type_code = 7; break;
    case F90_Logical4:	dv->type_code = 7; break;
    case F90_Logical8:	dv->type_code = 8; break;
    case F90_Real:	dv->type_code = 9; break;
    case F90_Double:	dv->type_code = 10; break;
    case F90_QReal:	dv->type_code = 11; break;
    case F90_Complex:	dv->type_code = 12; break;
    case F90_DComplex:	dv->type_code = 13; break;
    case F90_QComplex:	dv->type_code = 17; break;
/*  case F90_Character:	dv->type_code = 14; break; */
    case F90_Derived:	dv->type_code = 15; break;
    case F90_Unknown:	dv->type_code =  0; break;
    default:
      dv->type_code = 0;  break;
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
resetArrayDesc_Alpha(void* desc,
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

  if (rank == 0) {
    dope_vec0d* dv = (dope_vec0d*) desc;
    dv->base_addr = base_addr;
    return 0;
  }

  dv->base_addr  = base_addr;

  for (i = 0; i < rank; i++) {
    dv->dim[i].stride_mult = strideMult[i];
    dv->dim[i].upper_bound = lowerBound[i] + extent[i] - 1;
    dv->dim[i].lower_bound = lowerBound[i];
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
setArrayDesc_Alpha(void* desc,
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

  rc = setArrayDescStage1_Alpha(desc, rank, desc_type, data_type, element_size);
  if (rc) return rc;
  return resetArrayDesc_Alpha(desc, base_addr, rank,
			      lowerBound, extent, strideMult);
}



/**
 * Returns the size of an array descriptor (in bytes).
 *
 * @param rank   the rank of the array
 * @return       descriptor size (in bytes)
 */
static unsigned long
getArrayDescSize_Alpha(int rank)
{
  if (rank < 0 || rank > 7) return 0;
  if (rank == 0) return sizeof(dope_vec0d);
  else           return sizeof(dope_vec) - 3*(7-rank)*8;
}


/**
 * Nullify an array descriptor (associated intrinsic will return false).
 *
 * @param desc   array descriptor 
 * @param rank   the rank of the array
 * @return       0 if successful (nonzero on error)
 */
static int 
nullifyArrayDesc_Alpha(void* desc, int rank)
{
  if (rank < 0 || rank > 7) return 1;

  if (rank == 0) {
    dope_vec0d* dv = (dope_vec0d*) desc;
    dv->base_addr = 0x0;
  } else {
    dope_vec* dv = (dope_vec*) desc;
    dv->one   = 0;
    dv->ptr   = 0;
    dv->alloc = 0;
    dv->zero  = 0;
  }

  return 0;
}


/**
 * Set CompilerCharacteristics function pointers for Alpha.
 */
void F90_SetCCFunctions_Alpha(F90_CompilerCharacteristics* cc)
{
  cc->setArrayDesc              = setArrayDesc_Alpha;
  cc->getArrayDescSize	        = getArrayDescSize_Alpha;
  cc->nullifyArrayDesc          = nullifyArrayDesc_Alpha;
}




#ifdef __cplusplus
}
#endif
