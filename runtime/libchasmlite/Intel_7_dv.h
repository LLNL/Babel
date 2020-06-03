/**
 * This information inferred from a dump of a set of array descriptors and
 * help from colleagues at Rice University (John Mellor-Crummey,
 * Yuri Dotsenko, and Cristian Coarfa).
 *
 * This file for Intel Fortran version 7.
 *
 */

#define USE_MARK_OFFSET

/* dope_vec_Intel: the F90 array descriptor for the Intel compiler */

typedef struct {
#ifdef USE_MARK_OFFSET
  long    mark_offset;	  /* elem_size * sumof(lower_bound*stride_mult) */
#endif
  char*  marked_base;		  /* base_addr - mark_offset		*/
  char*  base_addr;		  /* base address of the array		*/
  long   size;			  /* size of the array (in elements)	*/
  long   elem_size;		  /* size of an element (in bytes)	*/
  long   elem_size_2;		  /* always same as element_size???	*/

  unsigned long rank        :  8; /* number of dimensions		*/
  unsigned long non_contig  :  8; /* 0 = contiguous ???			*/
  unsigned long unknown_1   :  8; /* always 0x0 ???			*/
  unsigned long not_pointer :  8; /* 0 = pointer ???			*/

  struct {
    long lower_bound;  /* first array index for a given dimension	*/
    long upper_bound;  /* last array index for a given dimension	*/
    long stride_mult;  /* distance between successive elements (bytes)  */
  } dim[7];
} dope_vec_Intel_7;
