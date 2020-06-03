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
#include "PGI.h"
#include "PGI_dv.h"

#define dope_vec dope_vec1d_PGI
#define dope_vec_hidden dope_vec_hidden_PGI

#define dv0d dope_vec0d_PGI
#define dv1d dope_vec1d_PGI
#define dv2d dope_vec2d_PGI
#define dv3d dope_vec3d_PGI
#define dv4d dope_vec4d_PGI
#define dv5d dope_vec5d_PGI
#define dv6d dope_vec6d_PGI
#define dv7d dope_vec7d_PGI

#define COOKIE 536936448

#ifdef __cplusplus
extern "C"{
#endif



/**
 * Returns the type code for the given data type
 */
 static int 
 typeCode(F90_ArrayDataType data_type)
 {
    switch (data_type) {
    case F90_Integer1: return 32;
    case F90_Integer2: return 24;
    case F90_Integer:  return 25;
    case F90_Integer4: return 25;
    case F90_Integer8: return 26;
    case F90_Logical1: return 17;
    case F90_Logical2: return 18;
    case F90_Logical:  return 19;
    case F90_Logical4: return 19;
    case F90_Logical8: return 20;
    case F90_Real:     return 27;
    case F90_Double:   return 28;
    case F90_QReal:    return 29; /* not tested */
    case F90_Complex:  return 9;
    case F90_DComplex: return 10;
    case F90_QComplex: return 11; /* not tested */
    case F90_Derived:  return 33;
    case F90_Unknown:
    default:          return -1;
  }
  
  return -1;
  
 }


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
 * @param lowerBound    array[rank] of lower bounds for the array
 * @param extent        array[rank] of extents for the array (in elements)
 * @param strideMult    array[rank] of distances between successive elements
 *                      (in bytes)
 * @return              0 if successful (nonzero on error)
 */
static int 
setArrayDescStage1_PGI(void* desc,
                       int rank,
                       F90_DescType desc_type,
                       F90_ArrayDataType data_type,
                       unsigned long element_size
                       )
{
  int rc = 0;
  dope_header_PGI* h; 
  dope_vec1d_PGI* dv = (dope_vec1d_PGI*) desc;

  h             = &dv->header;
  if (rank == 0) {
  	h->start_flag = typeCode(data_type);
  } else {
    h->start_flag = 35;
    h->rank       = rank;
    h->type_code  = typeCode(data_type);
    h->elem_size  = element_size;
    h->ten_flag   = COOKIE;
    h->zero_1[0]  = 0;
    h->zero_1[1]  = 0;
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
resetArrayDesc_PGI(void* desc,
                   void* base_addr,
                   int rank,
                   const long* lowerBound,
                   const unsigned long* extent,
                   const long* strideMult
                   )
{
  int i;
  long sum;
  unsigned long size;
  dope_vec1d_PGI* dv = (dope_vec1d_PGI*) desc;
  dope_header_PGI* h;

  if (rank < 0 || rank > 7) return 1;

  dv->base_addr = base_addr;
  dv->unkn_addr = 0x0;
  
  if (rank == 0) return 0;	/* descriptors for pointers have little data? */

  for (i = 0; i < rank; i++) {
    dv->dim[i].lower_bound = lowerBound[i];
    dv->dim[i].extent      = extent[i];
    dv->dim[i].one         = 1;
    dv->dim[i].zero        = 0;   
    dv->dim[i].upper_bound = lowerBound[i] + extent[i] - 1;
    dv->dim[i].stride_mult = strideMult[i]/dv->header.elem_size;
  }

  h = &dv->header;

  sum = 0;
  size = 1;
  for (i = 0; i < rank; i++) {
    size *= 1 + dv->dim[i].upper_bound - dv->dim[i].lower_bound;
    sum += dv->dim[i].lower_bound * dv->dim[i].stride_mult;
  }
  h->size     = size;
  h->size_dup = size;
  h->sum_d    = 1 - sum;

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
 * @param data_type     data type of an array element
 * @param element_size  size of an array element
 * @param lowerBound    array[rank] of lower bounds for the array
 * @param extent        array[rank] of extents for the array (in elements)
 * @param strideMult    array[rank] of distances between successive elements
 *                      (in bytes)
 * @return              0 if successful (nonzero on error)
 */
static int 
setArrayDesc_PGI(void* desc,
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
  int rc;

  if (rank < 0 || rank > 7) return 1;

  rc = setArrayDescStage1_PGI(desc, rank, desc_type, data_type, element_size);
  if (rc) return rc;
  return resetArrayDesc_PGI(desc, base_addr, rank,
			    lowerBound, extent, strideMult);
}




/**
 * Returns the size of an array descriptor (in bytes).
 *
 * @param rank   the rank of the array
 * @return       descriptor size (in bytes)
 */
static unsigned long 
getArrayDescSize_PGI(int rank)
{
  switch (rank) {
  	case 0:  return sizeof(dv0d);
    case 1:  return sizeof(dv1d);
    case 2:  return sizeof(dv2d);
    case 3:  return sizeof(dv3d);
    case 4:  return sizeof(dv4d);
    case 5:  return sizeof(dv5d);
    case 6:  return sizeof(dv6d);
    case 7:  return sizeof(dv7d);
  }
  return 0;
}


/**
 * Nullify an array descriptor (associated intrinsic will return false).
 *
 * @param desc   array descriptor 
 * @param rank   the rank of the array
 * @return       0 if successful (nonzero on error)
 */
static int 
nullifyArrayDesc_PGI(void* desc, int rank)
{
  dope_vec1d_PGI* dv = (dope_vec1d_PGI*) desc;
  dv->base_addr = 0x0;
  return 0;
}



/**
 * Set CompilerCharacteristics function pointers for PGI.
 */
void F90_SetCCFunctions_PGI(F90_CompilerCharacteristics* cc)
{
  cc->setArrayDesc              = setArrayDesc_PGI;
  cc->getArrayDescSize	        = getArrayDescSize_PGI;
  cc->nullifyArrayDesc          = nullifyArrayDesc_PGI;
}


#ifdef __cplusplus
}
#endif
