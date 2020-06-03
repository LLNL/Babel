#ifndef _SUNWSPRO_DV_H_
#define _SUNWSPRO_DV_H_

/**
 * This information inferred from a dump of a set of array descriptors
 * and from PETsC descriptor files.
 */

/* dope_vec: the F90 array descriptor for SUNWspro */

typedef struct {
  void* base_addr;         /* base address of the array                    */
  long  extent[1];         /* number of elements for a given dimension     */
  long  stride_mult[1];    /* distance between successive elements (bytes) */
  void* marked_base;       /* base_addr -sumof(lower_bound*stride_mult)    */
  long  lower_bound[1];    /* first array index for a given dimension      */
} dope_vec1d_SUNWspro;

typedef struct {
  void* base_addr;         /* base address of the array                    */
  long  extent[2];         /* number of elements for a given dimension     */
  long  stride_mult[2];    /* distance between successive elements (bytes) */
  void* marked_base;       /* base_addr -sumof(lower_bound*stride_mult)    */
  long  lower_bound[2];    /* first array index for a given dimension      */
} dope_vec2d_SUNWspro;

typedef struct {
  void* base_addr;         /* base address of the array                    */
  long  extent[3];         /* number of elements for a given dimension     */
  long  stride_mult[3];    /* distance between successive elements (bytes) */
  void* marked_base;       /* base_addr -sumof(lower_bound*stride_mult)    */
  long  lower_bound[3];    /* first array index for a given dimension      */
} dope_vec3d_SUNWspro;

typedef struct {
  void* base_addr;         /* base address of the array                    */
  long  extent[4];         /* number of elements for a given dimension     */
  long  stride_mult[4];    /* distance between successive elements (bytes) */
  void* marked_base;       /* base_addr -sumof(lower_bound*stride_mult)    */
  long  lower_bound[4];    /* first array index for a given dimension      */
} dope_vec4d_SUNWspro;

typedef struct {
  void* base_addr;         /* base address of the array                    */
  long  extent[5];         /* number of elements for a given dimension     */
  long  stride_mult[5];    /* distance between successive elements (bytes) */
  void* marked_base;       /* base_addr -sumof(lower_bound*stride_mult)    */
  long  lower_bound[5];    /* first array index for a given dimension      */
} dope_vec5d_SUNWspro;

typedef struct {
  void* base_addr;         /* base address of the array                    */
  long  extent[6];         /* number of elements for a given dimension     */
  long  stride_mult[6];    /* distance between successive elements (bytes) */
  void* marked_base;       /* base_addr -sumof(lower_bound*stride_mult)    */
  long  lower_bound[6];    /* first array index for a given dimension      */
} dope_vec6d_SUNWspro;

typedef struct {
  void* base_addr;         /* base address of the array                    */
  long  extent[7];         /* number of elements for a given dimension     */
  long  stride_mult[7];    /* distance between successive elements (bytes) */
  void* marked_base;       /* base_addr -sumof(lower_bound*stride_mult)    */
  long  lower_bound[7];    /* first array index for a given dimension      */
} dope_vec7d_SUNWspro;

#endif /* _SUNWSPRO_DV_H_ */
