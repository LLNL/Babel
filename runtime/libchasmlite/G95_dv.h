#ifndef G95_DV_H_
#define G95_DV_H_

/**
 * This information obtained from G95 file trans-array.c
 */

/* dope_vec_G95: the F90 array descriptor for the G95 compiler */

typedef struct dope_vec_G95_ {
  char*   offset;        /* base offset */
  int     rank;
  int     elem_size;
  void*   base_addr;     /* base address of the array */

  struct {
    int stride_mult;  /* distance between successive elements (elements) */
    int lower_bound;  /* first array index for a given dimension */
    int upper_bound;  /* last array index for a given dimension */
  } dim[7];
} dope_vec_G95;

#endif /* G95_DV_H_ */
