/**
 * This information inferred from a dump of a set of array descriptors.
 */


/* dope_vec: the F90 array descriptor for Cray */

typedef struct dope_vec_Cray {
  long    base_addr;		  /* base address of the array             */
  long    max_len;		  /* max length of an element (in bits)    */

  unsigned int    assoc     :  1; /* 1 = has been associated		   */
  unsigned int    ptr_alloc :  1; /* 1 = has been allocated		   */
  unsigned int    p_or_a    :  2; /* 1 = pointer, 2 = allocatable	   */
  unsigned int    a_contig  :  1; /* 1 = is contiguous			   */
  unsigned int    unused_1  : 11;
  unsigned int    unused_2  : 16;
  unsigned int    rank      : 32; /* number of dimensions		   */

  unsigned int    unused_3  : 32; /* type of data in array		   */
  unsigned int    type_code :  8; /* type of data in array		   */
  unsigned int    dp_flag   :  1; /* Double precision flag (-N113)	   */
  unsigned int    star_type :  3; /* Kind star type			   */
  unsigned int    elem_size : 12; /* byte (or word) size of data type	   */
  unsigned int    kind_value:  8; /* Value of the star or kind declaration */

  /* may be orig_base and orig_size (?, unused?) */
  long unused_4;
  long unused_5;

  /* array bounds information */
  struct {
    long lower_bound;  /* first array index for a given dimension          */
    long extent;       /* number of elements for a given dimension         */
    long stride_mult;  /* distance between successive elements (bytes)     */
  } dim[7];
} dope_vec_Cray;
