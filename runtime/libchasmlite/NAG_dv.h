/**
 * This information from NAGWare F95 compiler manual (pg. 113), also see
 * the file, dope.h, installed in /usr/local/lib/NAGWare.
 */

/* dope_vec_NAG: the F90 array descriptor for the NAG compiler */

typedef struct dope_vec_NAG_ {
  char*   base_addr;		  /* base address of the array		*/
  int     offset;		  /* offset to add to subscript calc.   */

  struct {
    long extent;       /* number of elements for a given dimension	*/
    long stride_mult;  /* distance between successive elements (bytes)  */
    long lower_bound;  /* first array index for a given dimension	*/
  } dim[7];
} dope_vec_NAG;
