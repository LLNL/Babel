/**
 * This information obtained from the Compaq Fortran user manual for
 * Tru64 UNIX and Linux Alpha systems, chapter 11.
 * <http://h18009.www1.hp.com/fortran/docs/unix-um/dfumproclang.htm>
 */

/* dope_vec: the F90 array descriptor for Compaq Alpha compiler */
#include "chasmlite_config.h"

typedef struct dope_vec0d_Alpha_
{
  void* base_addr;
} dope_vec0d_Alpha;


typedef struct dope_vec_Alpha_
{
  unsigned int rank	  :8;	/* number of dimensions	                  */
  unsigned int one	  :2;	/* always one				  */
  unsigned int ptr	  :1;	/* 1 = is a pointer			  */
  unsigned int alloc	  :1;	/* 1 = has been allocated		  */
  unsigned int zero	  :4;	/* always zero				  */
  unsigned int type_code  :8;	/* code for the data type                 */
  unsigned int reserved1  :8;	/* reserved                               */
  unsigned int reserved2  :32;	/* reserved                               */
#if !CHASM_BROKEN_BITFIELD
  long	elem_size         :64;	/* size of a single element in the array  */
  void* base_addr;		/* base address                           */
  long 	reserved3         :64;	/* reserved	                          */
  long	reserved4         :64;	/* reserved                               */
#else
  /*
   * Some compilers don't accept sizes greater than sizeof(long). This
   * section is compiled on all platforms but is only used if F90_ALPHA
   * is defined.
   */
  long	elem_size         :32;	/* size of a single element in the array  */
  void* base_addr;		/* base address                           */
  long 	reserved3         :32;	/* reserved	                          */
  long	reserved4         :32;	/* reserved                               */
#endif

  /* array bounds information */
  struct {
#if !CHASM_BROKEN_BITFIELD
    long stride_mult :64;   /* distance between successive elements (bytes)  */
    long upper_bound :64;   /* last array index for a given dimension        */
    long lower_bound :64;   /* first array index for a given dimension       */
#else
    long stride_mult :32;   /* distance between successive elements (bytes)  */
    long upper_bound :32;   /* last array index for a given dimension        */
    long lower_bound :32;   /* first array index for a given dimension       */
#endif
  } dim[7];
} dope_vec_Alpha;
