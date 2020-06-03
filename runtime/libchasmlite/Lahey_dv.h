/**
 * This information inferred from a dump of a set of array descriptors.
 */
#include "chasmlite_config.h"
#if CHASM_ARCH_64
#include <stdint.h>
typedef int64_t lahey_dv_word;
#else
typedef int lahey_dv_word;
#endif


/* dope_vec_Lahey: the F90 array descriptor for the Lahey compiler */

typedef struct dope_vec_Lahey_ {
  void*             base_addr;	  /* base address of the array		*/
  lahey_dv_word     alloc_size;	  /* size ?	*/
  lahey_dv_word     size;	  /* size of the array (in elements)	*/

  struct {
    lahey_dv_word lower_bound;  /* first array index for a given dimension	*/
    lahey_dv_word upper_bound;  /* last array index for a given dimension		*/
    lahey_dv_word stride_mult;  /* distance between successive elements (bytes)   */
    lahey_dv_word extent;       /* number of elements for a given dimension	*/
  } dim[7];
} dope_vec_Lahey;


/**
 * dope_vec_hidden_Lahey: hidden form of array descriptor.  This form is
 * passed at the end of the formal argument list for arrays that are NOT
 * passed by pointer.  The address of the array is passed in the normal
 * formal parameter slot.
 */
typedef struct dope_vec_hidden_Lahey_ {
  struct {
    lahey_dv_word extent;       /* number of elements for a given dimension	*/
    lahey_dv_word stride_mult;  /* distance between successive elements (bytes)   */
  } dim[7];
} dope_vec_hidden_Lahey;

