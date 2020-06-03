/**
 * This information inferred from a dump of a set of array descriptors
 * as well as info from PETSc
 */

#include "chasmlite_config.h"
#define long_type long

/* dope_vec (F90 array descriptor) */

typedef struct dope_vec_IBMXL_ {
  void* base_addr;            /* base address of the array               */
  unsigned int p_or_a    : 8; /* pointer (=1) or array (=3)              */
  unsigned int type_code : 8; /* integer id representing the datatype    */
  unsigned int not_ptr   : 1; /* pointer (=0 if pointer)                 */
  int cookie             : 3; /* 4 (rank=0), 5 otherwise                 */
  unsigned int zero      : 4; /* always 0                                */
  unsigned int flag64    : 1; /* 1 if 64 bit object files (-q64)         */
  unsigned int one       : 7; /* always 1                                */
#if CHASM_ARCH_64
  unsigned int       rank;       /* number of dimensions                    */
  unsigned long_type elem_size;  /* size of datatype                        */
#else
  unsigned long_type elem_size;  /* size of datatype                        */
  unsigned int       rank;       /* number of dimensions                    */
#endif
  long_type          sum_d;      /* -sumof(lower*mult)                      */

  /* array bounds information */
  struct {
    long_type lower_bound;  /* first array index for a given dimension      */
    long_type extent;       /* number of elements for a given dimension     */
    long_type stride_mult;  /* distance between successive elements (bytes) */
  } dim[7];
} dope_vec_IBMXL;


/**
 * dope_vec_hidden_IBMXL: hidden form of array descriptor.  This form is
 * passed at the end of the formal argument list for arrays that are NOT
 * passed by pointer.  The address of the array is passed in the normal
 * formal parameter slot.
 */
typedef struct dope_vec_hidden_IBMXL_ {
  unsigned int p_or_a    : 8; /* pointer (=1) or array (=3)              */
  unsigned int type_code : 8; /* integer id representing the datatype    */
  unsigned int not_ptr   : 1; /* pointer (=0 if pointer)                 */
  int cookie             : 3; /* unknown                                 */
  unsigned int zero      : 4; /* always 0                                */
  unsigned int flag64    : 1; /* 1 if 64 bit object files (-q64)         */
  unsigned int one       : 7; /* always 1                                */
#if CHASM_ARCH_64
  unsigned int       rank;       /* number of dimensions                    */
  unsigned long_type elem_size;  /* size of datatype                        */
#else
  unsigned long_type elem_size;  /* size of datatype                        */
  unsigned int       rank;       /* number of dimensions                    */
#endif
  long_type          sum_d;      /* -sumof(lower*mult)                      */

  /* array bounds information */
  struct {
    long_type lower_bound;  /* first array index for a given dimension      */
    long_type extent;       /* number of elements for a given dimension     */
    long_type stride_mult;  /* distance between successive elements (bytes) */
  } dim[7];
} dope_vec_hidden_IBMXL;
