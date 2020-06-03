/*********************************************************************
* File:        sidl_float_fStub.c
* Copyright:   (C) 2001-2004 Lawrence Livermore National Security, LLC
* Description: FORTRAN API for arrays of float
* Revision:    $Revision: 6655 $
*
* AUTOMATICALLY GENERATED BY genfortranarrays.py
**********************************************************************/

#include "sidl_header.h"
#ifndef included_babel_config_h
#include "babel_config.h"
#endif
#include "sidlfortran.h"
#ifndef included_sidlType_h
#include "sidlType.h"
#endif
#ifndef included_sidlArray_h
#include "sidlArray.h"
#endif
#ifndef FORTRAN90_DISABLED
#include "sidlf90array.h"
#endif
#include <stdlib.h>
#include <stddef.h>


#ifndef FORTRAN90_DISABLED
/*---------------------------------------------------------------------*
 * Fortran 90 Array Routines
 *---------------------------------------------------------------------*/

#include "sidl_float_fAbbrev.h"

void
SIDLFortran90Symbol(sidl_float__array_createcol_m,
                    SIDL_FLOAT__ARRAY_CREATECOL_M,
                    sidl_float__array_createCol_m)
  (int32_t *dimen, int32_t lower[], int32_t upper[], 
   struct sidl_fortran_array *result)
{
  (void)sidl_float__array_convert2f90(
    sidl_float__array_createCol(*dimen, lower, upper),
    *dimen, result);
    
}


void
SIDLFortran90Symbol(sidl_float__array_createrow_m,
                    SIDL_FLOAT__ARRAY_CREATEROW_M,
                    sidl_float__array_createRow_m)
  (int32_t *dimen, int32_t lower[], int32_t upper[], 
   struct sidl_fortran_array *result)
{
  (void)sidl_float__array_convert2f90(
    sidl_float__array_createRow(*dimen, lower, upper),
    *dimen, result);
    
}


void
SIDLFortran90Symbol(sidl_float__array_create1d_m,
                    SIDL_FLOAT__ARRAY_CREATE1D_M,
                    sidl_float__array_create1d_m)
  (int32_t *len, struct sidl_fortran_array *result)
{
  (void)sidl_float__array_convert2f90(
    sidl_float__array_create1d(*len), 1, result);
}


void
SIDLFortran90Symbol(sidl_float__array_create2drow_m,
                    SIDL_FLOAT__ARRAY_CREATE2DROW_M,
                    sidl_float__array_create2dRow_m)
  (int32_t *m, int32_t *n, struct sidl_fortran_array *result)
{
  (void)sidl_float__array_convert2f90(
    sidl_float__array_create2dRow(*m, *n), 2, result);
}


void
SIDLFortran90Symbol(sidl_float__array_create2dcol_m,
                    SIDL_FLOAT__ARRAY_CREATE2DCOL_M,
                    sidl_float__array_create2dCol_m)
  (int32_t *m, int32_t *n, struct sidl_fortran_array *result)
{
  (void)sidl_float__array_convert2f90(
    sidl_float__array_create2dCol(*m, *n), 2, result);
}


void
SIDLFortran90Symbol(sidl_float__array_ensure_m,
                    SIDL_FLOAT__ARRAY_ENSURE_M,
                    sidl_float__array_ensure_m)
  (int64_t *src, int32_t *dimen, int32_t *ordering, 
   struct sidl_fortran_array *result)
{
  (void)sidl_float__array_convert2f90(
     sidl_float__array_ensure((struct sidl_float__array*)(ptrdiff_t)*src,
                           *dimen, (int)*ordering),
     *dimen, result);
}


void
SIDLFortran90Symbol(sidl_float__array_smartcopy_m,
                    SIDL_FLOAT__ARRAY_SMARTCOPY_M,
                    sidl_float__array_smartCopy_m)
  (int64_t *src, int32_t *dimen, struct sidl_fortran_array *result)
{
  (void)sidl_float__array_convert2f90(
    sidl_float__array_smartCopy((struct sidl_float__array*)(ptrdiff_t)*src),
    *dimen, result);
}


void
SIDLFortran90Symbol(sidl_float__array_slice_m,
                    SIDL_FLOAT__ARRAY_SLICE_M,
                    sidl_float__array_slice_m)
  (int64_t *src, int32_t *dimen, int32_t numElem[], int32_t srcStart[],
   int32_t srcStride[], int32_t newStart[], struct sidl_fortran_array *result)
{
  (void)sidl_float__array_convert2f90(
    sidl_float__array_slice((struct sidl_float__array *)(ptrdiff_t)*src,
                         *dimen, numElem, srcStart, srcStride, newStart),
    *dimen, result);
}


void
SIDLFortran90Symbol(sidl_float__array_cast_m,
                    SIDL_FLOAT__ARRAY_CAST_M,
                    sidl_float__array_cast_m)
  (int64_t *array, int32_t *dimen, struct sidl_fortran_array *result)
{
  (void)sidl_float__array_convert2f90(
    sidl_float__array_cast((struct sidl__array *)(ptrdiff_t)*array),
    *dimen, result);
}


void
SIDLFortran90Symbol(sidl_float__array_copy_m,
                    SIDL_FLOAT__ARRAY_COPY_M,
                    sidl_float__array_copy_m)
  (int64_t *src, int64_t *dest)
{
  sidl_float__array_copy((struct sidl_float__array*)(ptrdiff_t)*src,
                      (struct sidl_float__array*)(ptrdiff_t)*dest);
}


void
SIDLFortran90Symbol(sidl_float__array_iscolumnorder_m,
                    SIDL_FLOAT__ARRAY_ISCOLUMNORDER_M,
                    sidl_float__array_isColumnOrder_m)
  (int64_t *array, SIDL_F90_Bool *result)
{
  *result = (
    sidl_float__array_isColumnOrder((struct sidl_float__array *)(ptrdiff_t)*array)
    ? SIDL_F90_TRUE : SIDL_F90_FALSE);
}

void
SIDLFortran90Symbol(sidl_float__array_isroworder_m,
                    SIDL_FLOAT__ARRAY_ISROWORDER_M,
                    sidl_float__array_isRowOrder_m)
  (int64_t *array, SIDL_F90_Bool *result)
{
  *result = (
    sidl_float__array_isRowOrder((struct sidl_float__array *)(ptrdiff_t)*array)
    ? SIDL_F90_TRUE : SIDL_F90_FALSE);
}


void
SIDLFortran90Symbol(sidl_float__array_dimen_m,
                    SIDL_FLOAT__ARRAY_DIMEN_M,
                    sidl_float__array_dimen_m)
  (int64_t *array, int32_t *result)
{
  *result =
    sidl_float__array_dimen((struct sidl_float__array *)(ptrdiff_t)*array);
}


void
SIDLFortran90Symbol(sidl_float__array_stride_m,
                    SIDL_FLOAT__ARRAY_STRIDE_M,
                    sidl_float__array_stride_m)
  (int64_t *array, int32_t *index, int32_t *result)
{
  *result =
    sidl_float__array_stride((struct sidl_float__array *)(ptrdiff_t)*array, *index);
}


void
SIDLFortran90Symbol(sidl_float__array_lower_m,
                    SIDL_FLOAT__ARRAY_LOWER_M,
                    sidl_float__array_lower_m)
  (int64_t *array, int32_t*ind, int32_t *result)
{
  *result =
    sidl_float__array_lower((struct sidl_float__array *)(ptrdiff_t)*array, *ind);
}


void
SIDLFortran90Symbol(sidl_float__array_upper_m,
                    SIDL_FLOAT__ARRAY_UPPER_M,
                    sidl_float__array_upper_m)
  (int64_t *array, int32_t *ind, int32_t *result)
{
  *result =
    sidl_float__array_upper((struct sidl_float__array *)(ptrdiff_t)*array, *ind);
}

void
SIDLFortran90Symbol(sidl_float__array_length_m,
                    SIDL_FLOAT__ARRAY_LENGTH_M,
                    sidl_float__array_length_m)
  (int64_t *array, int32_t *ind, int32_t *result)
{
  *result =
    sidl_float__array_length((struct sidl_float__array *)(ptrdiff_t)*array, *ind);
}

void
SIDLFortran90Symbol(sidl_float__array_deleteref_m,
                    SIDL_FLOAT__ARRAY_DELETEREF_M,
                    sidl_float__array_deleteRef_m)
  (int64_t *array)
{
  sidl_float__array_deleteRef((struct sidl_float__array*)(ptrdiff_t)*array);
}


void
SIDLFortran90Symbol(sidl_float__array_addref_m,
                    SIDL_FLOAT__ARRAY_ADDREF_M,
                    sidl_float__array_addRef_m)
  (int64_t *array)
{
  sidl_float__array_addRef((struct sidl_float__array*)(ptrdiff_t)*array);
}


void
SIDLFortran90Symbol(sidl_float__array_borrow_m,
                    SIDL_FLOAT__ARRAY_BORROW_M,
                    sidl_float__array_borrow_m)
  (float *firstElement, int32_t *dimen, int32_t lower[], int32_t upper[], int32_t stride[], struct sidl_fortran_array *result)
{
  sidl_float__array_convert2f90(
    sidl_float__array_borrow(firstElement, 
                               *dimen,
                               lower,
                               upper,
                               stride),
    *dimen, result);
}


static struct sidl_float__array *
smartCp(struct sidl_float__array *a) {
  sidl_float__array_addRef(a);
  return a;
}

static struct sidl__array_vtable CommonBlockVtable = {
  NULL, NULL, NULL
};

void
SIDLFortran90Symbol(sidl_float__array_cborrow_m,
                    SIDL_FLOAT__ARRAY_CBORROW_M,
                    sidl_float__array_cborrow_m)
  (float *firstElement, int32_t *dimen, int32_t lower[], int32_t upper[], int32_t stride[], struct sidl_fortran_array *result)
{
  struct sidl_float__array *a = 
    sidl_float__array_borrow(firstElement, 
                               *dimen,
                               lower,
                               upper,
                               stride);
  if (a) {
    if (CommonBlockVtable.d_destroy == NULL) {
      CommonBlockVtable.d_destroy = a->d_metadata.d_vtable->d_destroy;
      CommonBlockVtable.d_smartcopy = (struct sidl__array *(*)(struct sidl__array *))smartCp;
      CommonBlockVtable.d_arraytype = a->d_metadata.d_vtable->d_arraytype;
    }
    a->d_metadata.d_vtable = &CommonBlockVtable;
  }
  sidl_float__array_convert2f90(a, *dimen, result);
}


/**
 * NOTE: THERE IS THE POTENTIAL FOR POINTER ALIGNMENT PROBLEMS WITH
 * THIS FUNCTION.  If index is zero on return, the array pointer
 * and ref are not properly aligned!
 */
void
SIDLFortran90Symbol(sidl_float__array_access_m,
                    SIDL_FLOAT__ARRAY_ACCESS_M,
                    sidl_float__array_access_m)
  (int64_t *array,
   float *ref,
   int32_t * restrict lower,
   int32_t * restrict upper,
   int32_t * restrict stride,
   int64_t * restrict index)
{
  struct sidl_float__array *ary = (struct sidl_float__array *)
    (ptrdiff_t)*array;
  const int32_t dimen = sidlArrayDim(ary);
  int32_t i;
  for(i = 0; i < dimen; ++i){
    lower[i] = sidlLower(ary, i);
    upper[i] = sidlUpper(ary, i);
    stride[i] = sidlStride(ary, i);
  }
  if (((((ptrdiff_t)(ary->d_firstElement)) -
        ((ptrdiff_t)ref)) % sizeof(float)) == 0) {
    *index = ((float *)(ary->d_firstElement) - ref) + 1;
  }
  else {
    *index = 0;
  }
}


void
SIDLFortran90Symbol(sidl_float__array_get1_m,
                    SIDL_FLOAT__ARRAY_GET1_M,
                    sidl_float__array_get1_m)
  (int64_t *array,   int32_t *i1,
   float *result)
{
  *result = 
    sidl_float__array_get1((struct sidl_float__array *)(ptrdiff_t)*array,
     *i1);
}


void
SIDLFortran90Symbol(sidl_float__array_set1_m,
                    SIDL_FLOAT__ARRAY_SET1_M,
                    sidl_float__array_set1_m)
  (int64_t *array,   int32_t *i1,
   float *value)
{
  sidl_float__array_set1((struct sidl_float__array *)(ptrdiff_t)*array,
   *i1,
   *value);
}


void
SIDLFortran90Symbol(sidl_float__array_get2_m,
                    SIDL_FLOAT__ARRAY_GET2_M,
                    sidl_float__array_get2_m)
  (int64_t *array,   int32_t *i1,
   int32_t *i2,
   float *result)
{
  *result = 
    sidl_float__array_get2((struct sidl_float__array *)(ptrdiff_t)*array,
     *i1,
     *i2);
}


void
SIDLFortran90Symbol(sidl_float__array_set2_m,
                    SIDL_FLOAT__ARRAY_SET2_M,
                    sidl_float__array_set2_m)
  (int64_t *array,   int32_t *i1,
   int32_t *i2,
   float *value)
{
  sidl_float__array_set2((struct sidl_float__array *)(ptrdiff_t)*array,
   *i1,
   *i2,
   *value);
}


void
SIDLFortran90Symbol(sidl_float__array_get3_m,
                    SIDL_FLOAT__ARRAY_GET3_M,
                    sidl_float__array_get3_m)
  (int64_t *array,   int32_t *i1,
   int32_t *i2,
   int32_t *i3,
   float *result)
{
  *result = 
    sidl_float__array_get3((struct sidl_float__array *)(ptrdiff_t)*array,
     *i1,
     *i2,
     *i3);
}


void
SIDLFortran90Symbol(sidl_float__array_set3_m,
                    SIDL_FLOAT__ARRAY_SET3_M,
                    sidl_float__array_set3_m)
  (int64_t *array,   int32_t *i1,
   int32_t *i2,
   int32_t *i3,
   float *value)
{
  sidl_float__array_set3((struct sidl_float__array *)(ptrdiff_t)*array,
   *i1,
   *i2,
   *i3,
   *value);
}


void
SIDLFortran90Symbol(sidl_float__array_get4_m,
                    SIDL_FLOAT__ARRAY_GET4_M,
                    sidl_float__array_get4_m)
  (int64_t *array,   int32_t *i1,
   int32_t *i2,
   int32_t *i3,
   int32_t *i4,
   float *result)
{
  *result = 
    sidl_float__array_get4((struct sidl_float__array *)(ptrdiff_t)*array,
     *i1,
     *i2,
     *i3,
     *i4);
}


void
SIDLFortran90Symbol(sidl_float__array_set4_m,
                    SIDL_FLOAT__ARRAY_SET4_M,
                    sidl_float__array_set4_m)
  (int64_t *array,   int32_t *i1,
   int32_t *i2,
   int32_t *i3,
   int32_t *i4,
   float *value)
{
  sidl_float__array_set4((struct sidl_float__array *)(ptrdiff_t)*array,
   *i1,
   *i2,
   *i3,
   *i4,
   *value);
}


void
SIDLFortran90Symbol(sidl_float__array_get5_m,
                    SIDL_FLOAT__ARRAY_GET5_M,
                    sidl_float__array_get5_m)
  (int64_t *array,   int32_t *i1,
   int32_t *i2,
   int32_t *i3,
   int32_t *i4,
   int32_t *i5,
   float *result)
{
  *result = 
    sidl_float__array_get5((struct sidl_float__array *)(ptrdiff_t)*array,
     *i1,
     *i2,
     *i3,
     *i4,
     *i5);
}


void
SIDLFortran90Symbol(sidl_float__array_set5_m,
                    SIDL_FLOAT__ARRAY_SET5_M,
                    sidl_float__array_set5_m)
  (int64_t *array,   int32_t *i1,
   int32_t *i2,
   int32_t *i3,
   int32_t *i4,
   int32_t *i5,
   float *value)
{
  sidl_float__array_set5((struct sidl_float__array *)(ptrdiff_t)*array,
   *i1,
   *i2,
   *i3,
   *i4,
   *i5,
   *value);
}


void
SIDLFortran90Symbol(sidl_float__array_get6_m,
                    SIDL_FLOAT__ARRAY_GET6_M,
                    sidl_float__array_get6_m)
  (int64_t *array,   int32_t *i1,
   int32_t *i2,
   int32_t *i3,
   int32_t *i4,
   int32_t *i5,
   int32_t *i6,
   float *result)
{
  *result = 
    sidl_float__array_get6((struct sidl_float__array *)(ptrdiff_t)*array,
     *i1,
     *i2,
     *i3,
     *i4,
     *i5,
     *i6);
}


void
SIDLFortran90Symbol(sidl_float__array_set6_m,
                    SIDL_FLOAT__ARRAY_SET6_M,
                    sidl_float__array_set6_m)
  (int64_t *array,   int32_t *i1,
   int32_t *i2,
   int32_t *i3,
   int32_t *i4,
   int32_t *i5,
   int32_t *i6,
   float *value)
{
  sidl_float__array_set6((struct sidl_float__array *)(ptrdiff_t)*array,
   *i1,
   *i2,
   *i3,
   *i4,
   *i5,
   *i6,
   *value);
}


void
SIDLFortran90Symbol(sidl_float__array_get7_m,
                    SIDL_FLOAT__ARRAY_GET7_M,
                    sidl_float__array_get7_m)
  (int64_t *array,   int32_t *i1,
   int32_t *i2,
   int32_t *i3,
   int32_t *i4,
   int32_t *i5,
   int32_t *i6,
   int32_t *i7,
   float *result)
{
  *result = 
    sidl_float__array_get7((struct sidl_float__array *)(ptrdiff_t)*array,
     *i1,
     *i2,
     *i3,
     *i4,
     *i5,
     *i6,
     *i7);
}


void
SIDLFortran90Symbol(sidl_float__array_set7_m,
                    SIDL_FLOAT__ARRAY_SET7_M,
                    sidl_float__array_set7_m)
  (int64_t *array,   int32_t *i1,
   int32_t *i2,
   int32_t *i3,
   int32_t *i4,
   int32_t *i5,
   int32_t *i6,
   int32_t *i7,
   float *value)
{
  sidl_float__array_set7((struct sidl_float__array *)(ptrdiff_t)*array,
   *i1,
   *i2,
   *i3,
   *i4,
   *i5,
   *i6,
   *i7,
   *value);
}


void
SIDLFortran90Symbol(sidl_float__array_set_m,
                    SIDL_FLOAT__ARRAY_SET_M,
                    sidl_float__array_set_m)
  (int64_t *array, int32_t indices[], float *value)
{
  sidl_float__array_set((struct sidl_float__array *)(ptrdiff_t)*array, indices,  *value);
}


void
SIDLFortran90Symbol(sidl_float__array_get_m,
                    SIDL_FLOAT__ARRAY_GET_M,
                    sidl_float__array_get_m)
  (int64_t *array, int32_t indices[], float *value)
{
  *value = 
     sidl_float__array_get((struct sidl_float__array *)(ptrdiff_t)*array, indices);
}



#endif /* not FORTRAN90_DISABLED */
