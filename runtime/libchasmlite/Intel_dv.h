/**
 * This information from Intel documentation,
 * http://intel.com/software/products/compilers/flin/docs/f_ug1/index.htm
 */

#include "chasmlite_config.h"
/* dope_vec_Intel: the F90 array descriptor for the Intel (v8.0 and greater)
 * compiler.  The meaning of the bit flags in the fourth long work are
 * uncertain.
 */

typedef struct dope_vec_Intel_ {
  char*   base_addr;		  /* base address of the array		*/
  long    elem_size;		  /* size of a single element           */
  long    offset;		  /* base_addr + offset is array start  */

  unsigned int    assoc     :  1; /* 1 = has been associated		*/
  unsigned int    ptr_alloc :  1; /* 1 = has been allocated		*/
  unsigned int    p_or_a    :  2; /* 1 = pointer, 2 = allocatable	*/
  unsigned int    non_contig:  1; /* 0 = is contiguous			*/
  unsigned int    reserved_1: 11;
  unsigned int    reserved_2: 16;
#if CHASM_ARCH_64
  unsigned int    reserved64: 16;
#endif
  long rank;                      /* number of dimensions		*/
  long reserved_3;

  struct {
    long extent;       /* number of elements for a given dimension	*/
    long stride_mult;  /* distance between successive elements (bytes)  */
    long lower_bound;  /* first array index for a given dimension	*/
  } dim[7];

} dope_vec_Intel;
