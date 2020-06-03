/**
 * This information inferred from a dump of a set of array descriptors.
 */


/* dope_vec_PGI: the F90 array descriptor for the PGI compiler */

typedef struct dope_header_PGI_ {
  int     start_flag;	/* 35					*/
  int     rank;			  /* number of dimensions		*/
  int     type_code;		  /* type of data in the array		*/
  int     elem_size;		  /* byte size of data type		*/
  int     ten_flag;		  /* 10	(or 0x1000a in second block)	*/
  int     size;			  /* size of the array (in elements)	*/
  int     size_dup;		  /* same as above			*/
  int     sum_d;		  /* -sumof(lower*mult) + 1             */
  int     zero_1[4];		  /* {0 0}				*/
} dope_header_PGI;

typedef struct dope_dim_PGI_ {
  int lower_bound;    /* first array index for a given dimension	*/
  int extent;
  int one;
  int zero;
  int stride_mult;    /* distance between successive elements (bytes)	*/
  int upper_bound;    /* last array index for a given dimension		*/
} dope_dim_PGI;

typedef struct dope_header_old_PGI_ {
  int     start_flag;		  /* 35					*/
  int     rank;			  /* number of dimensions		*/
  int     type_code;		  /* type of data in the array		*/
  int     elem_size;		  /* byte size of data type		*/
  int     ten_flag;		  /* 10	(or 0x1000a in second block)	*/
  int     size;			  /* size of the array (in elements)	*/
  int     size_dup;		  /* same as above			*/
  int     zero_1[2];		  /* {0 0}				*/
  int     sum_d;		  /* -sumof(lower*mult) + 1             */
  int     block_1[7];		  /* {0 0 0 0 0 1 0}			*/
  int     block_2[7];		  /* {1 2 3 0 0 0 0} e.g., for rank 3	*/
  int     stop_flag_1;		  /* 0x808c278 = 134791800		*/
  int     stop_flag_2;		  /* 0x80892b8 = 134779576		*/
  int     zero_2[2];		  /* {0 0}				*/
} dope_header_old_PGI;

typedef struct dope_dim_old_PGI_ {
  int lower_bound;    /* first array index for a given dimension	*/
  int upper_bound;    /* last array index for a given dimension		*/
  int stride_mult;    /* distance between successive elements (bytes)	*/
  int trailer[41];
} dope_dim_old_PGI;

typedef struct dope_vec_hidden_PGI_ {
  dope_header_PGI header;
  dope_dim_PGI    dim[7];
} dope_vec_hidden_PGI;


typedef struct dope_vec0d_PGI_ {
  char*   base_addr;  /* base address of the array			*/
  void*   unkn_addr;  /* I'll bet this is 32 bit padding		*/
  dope_header_PGI header;
} dope_vec0d_PGI;

/*
 * 1D - 7D dope vectors
 */

typedef struct dope_vec1d_PGI_ {
  char*   base_addr;  /* base address of the array			*/
  void*   unkn_addr;  /* I'll bet this is 32 bit padding		*/
  dope_header_PGI header;
  dope_dim_PGI    dim[1];
} dope_vec1d_PGI;

typedef struct dope_vec2d_PGI_ {
  void*   base_addr;  /* base address of the array			*/
  void*   unkn_addr;  /* unknown address of the array			*/
  dope_header_PGI header;
  dope_dim_PGI    dim[2];
} dope_vec2d_PGI;

typedef struct dope_vec3d_PGI_ {
  void*   base_addr;  /* base address of the array			*/
  void*   unkn_addr;  /* unknown address of the array			*/
  dope_header_PGI header;
  dope_dim_PGI    dim[3];
} dope_vec3d_PGI;

typedef struct dope_vec4d_PGI_ {
  void*   base_addr;  /* base address of the array			*/
  dope_header_PGI header;
  dope_dim_PGI    dim[4];
  int             trailer[2*4];
  dope_header_PGI header_2;
  dope_dim_PGI    dim_2[4];
  int             trailer_2[2*4 + 3];
} dope_vec4d_PGI;

typedef struct dope_vec5d_PGI_ {
  void*   base_addr;  /* base address of the array			*/
  dope_header_PGI header;
  dope_dim_PGI    dim[5];
  int             trailer[2*5];
  dope_header_PGI header_2;
  dope_dim_PGI    dim_2[5];
  int             trailer_2[2*5 + 3];
} dope_vec5d_PGI;

typedef struct dope_vec6d_PGI_ {
  void*   base_addr;  /* base address of the array			*/
  dope_header_PGI header;
  dope_dim_PGI    dim[6];
  int             trailer[2*6];
  dope_header_PGI header_2;
  dope_dim_PGI    dim_2[6];
  int             trailer_2[2*6 + 3];
} dope_vec6d_PGI;

typedef struct dope_vec7d_PGI_ {
  void*   base_addr;  /* base address of the array			*/
  dope_header_PGI header;
  dope_dim_PGI    dim[7];
  int             trailer[2*7];
  dope_header_PGI header_2;
  dope_dim_PGI    dim_2[7];
  int             trailer_2[2*7 + 3];
} dope_vec7d_PGI;

