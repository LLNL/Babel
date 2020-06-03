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
#include "SUNWspro.h"
#include "SUNWspro_dv.h"

#define dv1d dope_vec1d_SUNWspro
#define dv2d dope_vec2d_SUNWspro
#define dv3d dope_vec3d_SUNWspro
#define dv4d dope_vec4d_SUNWspro
#define dv5d dope_vec5d_SUNWspro
#define dv6d dope_vec6d_SUNWspro
#define dv7d dope_vec7d_SUNWspro


#ifdef __cplusplus
extern "C"{
#endif

/* local functions to get dimension pointers */

static unsigned long* extents(void* desc, int rank)
{
  return (unsigned long*) ((dv1d*) desc)->extent;
}

static long* lowerBounds(void* desc, int rank)
{
  long* lower_bound;
  switch (rank) {
    case 1:  lower_bound = ((dv1d*)desc)->lower_bound;  break;
    case 2:  lower_bound = ((dv2d*)desc)->lower_bound;  break;
    case 3:  lower_bound = ((dv3d*)desc)->lower_bound;  break;
    case 4:  lower_bound = ((dv4d*)desc)->lower_bound;  break;
    case 5:  lower_bound = ((dv5d*)desc)->lower_bound;  break;
    case 6:  lower_bound = ((dv6d*)desc)->lower_bound;  break;
    case 7:  lower_bound = ((dv7d*)desc)->lower_bound;  break;
  }
  return lower_bound;
}

static long* strides(void* desc, int rank)
{
  long* stride_mult;
  switch (rank) {
    case 1:  stride_mult = ((dv1d*)desc)->stride_mult;  break;
    case 2:  stride_mult = ((dv2d*)desc)->stride_mult;  break;
    case 3:  stride_mult = ((dv3d*)desc)->stride_mult;  break;
    case 4:  stride_mult = ((dv4d*)desc)->stride_mult;  break;
    case 5:  stride_mult = ((dv5d*)desc)->stride_mult;  break;
    case 6:  stride_mult = ((dv6d*)desc)->stride_mult;  break;
    case 7:  stride_mult = ((dv7d*)desc)->stride_mult;  break;
  }
  return stride_mult;
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
resetArrayDesc_SUNWspro(void* desc,
                        void* base_addr,
                        int rank,
                        const long* lowerBound,
                        const unsigned long* extent,
                        const long* strideMult
                        )
{
  int i;
  long offset = 0L;
  void **marked_base;
  long *stride_mult, *lower_bound;
  unsigned long* dim_extent;

  if (rank < 0 || rank > 7) return 1;

  ((dv1d*)desc)->base_addr = base_addr;
  if (rank == 0) return 0;

  for (i = 0; i < rank; i++) {
    dim_extent = extents(desc, rank);
    stride_mult = strides(desc, rank);
    lower_bound = lowerBounds(desc, rank);

    dim_extent[i]  = extent[i];
    lower_bound[i] = lowerBound[i];
    stride_mult[i] = strideMult[i];
  }

  dim_extent = extents(desc, rank);
  stride_mult = strides(desc, rank);
  lower_bound = lowerBounds(desc, rank);

  switch (rank) {
    case 1:  marked_base = & ((dv1d*)desc)->marked_base;  break;
    case 2:  marked_base = & ((dv2d*)desc)->marked_base;  break;
    case 3:  marked_base = & ((dv3d*)desc)->marked_base;  break;
    case 4:  marked_base = & ((dv4d*)desc)->marked_base;  break;
    case 5:  marked_base = & ((dv5d*)desc)->marked_base;  break;
    case 6:  marked_base = & ((dv6d*)desc)->marked_base;  break;
    case 7:  marked_base = & ((dv7d*)desc)->marked_base;  break;
  }

  if ( ((dv1d*)desc)->base_addr == 0x0) return 1;

  for (i = 0; i < rank; i++) {
    if (dim_extent[i]  == 0) return 1;
    if (stride_mult[i] == 0) return 1;
    offset += lower_bound[i] * stride_mult[i];
  }
  *marked_base = (void*) ( (long)((dv1d*)desc)->base_addr - offset );

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
setArrayDesc_SUNWspro(void* desc,
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
  if (rank < 0 || rank > 7) return 1;
  return resetArrayDesc_SUNWspro(desc, base_addr, rank,
				 lowerBound, extent, strideMult);
}



/**
 * Returns the size of an array descriptor (in bytes).
 *
 * @param rank   the rank of the array
 * @return       descriptor size (in bytes)
 */
static unsigned long 
getArrayDescSize_SUNWspro(int rank)
{
  unsigned long size=0;
  switch (rank) {
    case 0:  size = sizeof(void*); break;
    case 1:  size = sizeof(dv1d);  break;
    case 2:  size = sizeof(dv2d);  break;
    case 3:  size = sizeof(dv3d);  break;
    case 4:  size = sizeof(dv4d);  break;
    case 5:  size = sizeof(dv5d);  break;
    case 6:  size = sizeof(dv6d);  break;
    case 7:  size = sizeof(dv7d);  break;
  }
  return size;
}


/**
 * Nullify an array descriptor (associated intrinsic will return false).
 *
 * @param desc   array descriptor 
 * @param rank   the rank of the array
 * @return       0 if successful (nonzero on error)
 */
static int
nullifyArrayDesc_SUNWspro(void* desc, int rank)
{
  dv1d* dv = (dv1d*) desc;
  if (rank < 0 || rank > 7) return 0;

  dv->base_addr = 0x0;
  if (rank == 0) return 0;
  dv->marked_base = 0;

  return 0;
}


/**
 * Set CompilerCharacteristics function pointers for SUNWspro.
 */
void F90_SetCCFunctions_SUNWspro(F90_CompilerCharacteristics* cc)
{
  cc->setArrayDesc              = setArrayDesc_SUNWspro;
  cc->getArrayDescSize	        = getArrayDescSize_SUNWspro;
  cc->nullifyArrayDesc          = nullifyArrayDesc_SUNWspro;
}

#ifdef __cplusplus
}
#endif
