#ifndef __PATHSCALE_DV_H_LOADED__
#define __PATHSCALE_DV_H_LOADED__
/* These type definitions are taken from the PathScale compiler
 * documentation
 */


typedef struct _FCD {
  char *c_pointer;              /* C character pointer */
  unsigned long byte_len;       /* Length of item (in bytes) */
} _fcd;

enum typecodes {
  DVTYPE_UNUSED = 0,
  DVTYPE_TYPELESS = 1,
  DVTYPE_INTEGER = 2,
  DVTYPE_REAL = 3,
  DVTYPE_COMPLEX = 4,
  DVTYPE_LOGICAL = 5,
  DVTYPE_ASCII = 6,
  DVTYPE_DERIVEDBYTE = 7,
  DVTYPE_DERIVEDWORD = 8
};

enum dec_codes {
  DVD_DEFAULT = 0,            /* KIND and *n absent */
  DVD_KIND = 1,               /* KIND=expression (non-default) */
  DVD_STAR = 2,               /* *n is specified (old F77 style) */
  DVD_KIND_CONST = 3,         /* KIND=constant expression */
  DVD_KIND_DOUBLE = 4         /* KIND=expression which evaluates to
                                 KIND(1.0D0) for real access all
                                 implementations. This code may be passed
                                 for real or complex types. */
};

typedef struct f90_type {
  unsigned int : 32;            /* used for future development */
  unsigned int type : 8;            /* type code (use value from typescodes */

  unsigned int dpflag : 1;      /* set if declared double precision or
                                   double complex  */

  unsigned int kind_or_star : 3; /* Set if KIND= or *n appears in the
                                   variable declaration.  Use value from
                                   dec_codes */

  unsigned int int_len : 12;    /* internal length in bits of iolist entity.
                                   8 for character data to indicate size of
                                   each character */

  unsigned int dec_len : 8;     /* declared length in bytes for *n or KIND
                                   value. Ignored if
                                   kind_or_star=DVD_DEFAULT. */
} f90_type_t;


enum ptrarray {
  NOT_P_OR_A = 0,
  POINTTR = 1,
  ALLOC_ARRY = 2
};

typedef struct DopeVector {
  union {
    _fcd charptr;               /* Fortran character descriptor */
    struct {
      void *ptr;                /* pointer to base address */
      unsigned long el_len;     /* element len in bits */
    } a;
  } base_addr;

  /*
   * flags and information fields with word 3 of the header
   */
  unsigned int assoc : 1;     /* associated flag */
  unsigned int ptr_alloc : 1; /* set if allocated by pointer */
  unsigned int p_or_a : 2;    /* pointer or allocatable array */
  unsigned int a_contig : 1;  /* array storage contiguous flag */
  unsigned int alloc_cpnt : 1;/* this is an allocable array whose element
                                 type is a derived type having
                                 allocatable component(s) */
  unsigned int : 26;          /* pad for first 32 bits */
  unsigned int : 29;          /* pad for second 32 bits */
  unsigned int n_dim : 3;     /* number of dimensions */
  f90_type_t type_lens;       /* data type and lengths */
  void *orig_base;            /* original base address */
  unsigned long orig_size;    /* original size */
  /*
   * per dimension information -- array will contain only the
   * necessary number of elements.
   */
  struct DvDimen {
    signed long low_bound;    /* lower bound for i'th dimension (may be <
                                 0) */
    signed long extent;       /* number of elements for i'th dimension */
    /* 
     * The stride mult is not defined in constant units so that address
     * calculations do not always require a divide by 8 or 64. For double
     * and complex, stride mult has a factor of 2 in it. For double
     * complex, strude mult has a factor of 4 in it.
     */
    signed long stride_mult;  /* stride multiplier */
  } dimension[7];
} DopeVectorType;

#endif /*  __PATHSCALE_DV_H_LOADED__ */
