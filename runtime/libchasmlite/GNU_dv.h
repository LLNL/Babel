#ifndef GNU_DV_H_
#define GNU_DV_H_

#include <stdlib.h>

/**
 * This information from gcc/libgfortran/libgfortran.h and reverse engineering
 */

/* dope_vec_GNU: the F90 array descriptor for the GNU compiler */

typedef struct dope_vec_GNU_ {
  void*   base_addr;     /* base address of the array */
  void*   base;          /* base offset */
  size_t  dtype;         /* elem_size, type (3 bits) and rank (3 bits) */

  struct {
    size_t stride_mult;  /* distance between successive elements (elements) */
    size_t lower_bound;  /* first array index for a given dimension */
    size_t upper_bound;  /* last array index for a given dimension */
  } dim[7];
} dope_vec_GNU;

#define GFC_DTYPE_RANK_MASK 0x07
#define GFC_DTYPE_TYPE_SHIFT 3
#define GFC_DTYPE_TYPE_MASK 0x38
#define GFC_DTYPE_SIZE_SHIFT 6

enum
  {
    GFC_DTYPE_UNKNOWN = 0,
    GFC_DTYPE_INTEGER,
    /* TODO: recognize logical types.  */
    GFC_DTYPE_LOGICAL,
    GFC_DTYPE_REAL,
    GFC_DTYPE_COMPLEX,
    GFC_DTYPE_DERIVED,
    GFC_DTYPE_CHARACTER
  };

#define GFC_DESCRIPTOR_RANK(desc) ((desc)->dtype & GFC_DTYPE_RANK_MASK)
#define GFC_DESCRIPTOR_TYPE(desc) (((desc)->dtype & GFC_DTYPE_TYPE_MASK) \
                                   >> GFC_DTYPE_TYPE_SHIFT)
#define GFC_DESCRIPTOR_SIZE(desc) ((desc)->dtype >> GFC_DTYPE_SIZE_SHIFT)
#define GFC_DESCRIPTOR_DATA(desc) ((desc)->base_addr)
#define GFC_DESCRIPTOR_DTYPE(desc) ((desc)->dtype)

#endif /* GNU_DV_H_ */
