/** LANL:license
 * -------------------------------------------------------------------------
 * This SOFTWARE has been authored by an employee or employees of the
 * University of California, operator of the Los Alamos National Laboratory
 * under Contract No. W-7405-ENG-36 with the U.S. Department of Energy.
 * The U.S. Government has rights to use, reproduce, and distribute this
 * SOFTWARE.  The public may copy, distribute, prepare derivative works and
 * publicly display this SOFTWARE without charge, provided that this Notice
 * and any statement of authorship are reproduced on all copies.  Neither
 * the Government nor the University makes any warranty, express or implied,
 * or assumes any liability or responsibility for the use of this SOFTWARE.
 * If SOFTWARE is modified to produce derivative works, such modified
 * SOFTWARE should be clearly marked, so as not to confuse it with the
 * version available from LANL.
 * -------------------------------------------------------------------------
 * LANL:license
 * -------------------------------------------------------------------------
 */
#ifndef _F90ArrayDataType_h_
#define _F90ArrayDataType_h_

#ifdef __cplusplus
extern "C" {
#endif

/**
 * F90_ArrayDataType contains all of the basic fortran data types which are
 * are supported by Chasm. It allows the programmer to get and set the data
 * type of an array in a user friendly manner.
 */
typedef enum
{
  F90_Integer1,		/* 8 bit, char */
  F90_Integer2,		/* 16 bits, short */
  F90_Integer,
  F90_Integer4,
  F90_Integer8,
  F90_Logical1,
  F90_Logical2,
  F90_Logical,
  F90_Logical4,
  F90_Logical8,
  F90_Real,
  F90_Double,
  F90_QReal,
  F90_Complex,
  F90_DComplex,
  F90_QComplex,
  F90_Derived,
  F90_Unknown
} F90_ArrayDataType;

#ifdef __cplusplus
} /* extern "C" */
#endif

#endif /* _F90ArrayDataType_h_ */
