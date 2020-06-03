/*********************************************************************
* File:        sidl_int_fStub.c
* Copyright:   (C) 2001-2004 Lawrence Livermore National Security, LLC
* Description: FORTRAN API for arrays of int
* Revision:    $Revision: 6171 $
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
#include <stdlib.h>
#include <stddef.h>


#ifndef FORTRAN77_DISABLED
/*---------------------------------------------------------------------*
 * Fortran 77_31 Array Routines
 *---------------------------------------------------------------------*/

#include "sidl_int_fAbbrev.h"

void
SIDLFortran77Symbol(sidl_int__array_createcol_f,
                    SIDL_INT__ARRAY_CREATECOL_F,
                    sidl_int__array_createCol_f)
  (int32_t *dimen, int32_t lower[], int32_t upper[], int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_int__array_createCol(*dimen, lower, upper);
}


void
SIDLFortran77Symbol(sidl_int__array_createrow_f,
                    SIDL_INT__ARRAY_CREATEROW_F,
                    sidl_int__array_createRow_f)
  (int32_t *dimen, int32_t lower[], int32_t upper[], int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_int__array_createRow(*dimen, lower, upper);
}


void
SIDLFortran77Symbol(sidl_int__array_create1d_f,
                    SIDL_INT__ARRAY_CREATE1D_F,
                    sidl_int__array_create1d_f)
  (int32_t *len, int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_int__array_create1d(*len);
}


void
SIDLFortran77Symbol(sidl_int__array_create2drow_f,
                    SIDL_INT__ARRAY_CREATE2DROW_F,
                    sidl_int__array_create2dRow_f)
  (int32_t *m, int32_t *n, int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_int__array_create2dRow(*m, *n);
}


void
SIDLFortran77Symbol(sidl_int__array_create2dcol_f,
                    SIDL_INT__ARRAY_CREATE2DCOL_F,
                    sidl_int__array_create2dCol_f)
  (int32_t *m, int32_t *n, int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_int__array_create2dCol(*m, *n);
}


void
SIDLFortran77Symbol(sidl_int__array_ensure_f,
                    SIDL_INT__ARRAY_ENSURE_F,
                    sidl_int__array_ensure_f)
  (int64_t *src, int32_t *dimen, int32_t *ordering, int64_t *result)
{
  *result = (ptrdiff_t)
     sidl_int__array_ensure((struct sidl_int__array*)(ptrdiff_t)*src,
                           *dimen, (int)*ordering);
}


void
SIDLFortran77Symbol(sidl_int__array_smartcopy_f,
                    SIDL_INT__ARRAY_SMARTCOPY_F,
                    sidl_int__array_smartCopy_f)
  (int64_t *src, int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_int__array_smartCopy((struct sidl_int__array*)(ptrdiff_t)*src);
}


void
SIDLFortran77Symbol(sidl_int__array_slice_f,
                    SIDL_INT__ARRAY_SLICE_F,
                    sidl_int__array_slice_f)
  (int64_t *src, int32_t *dimen, int32_t numElem[], int32_t srcStart[],
   int32_t srcStride[], int32_t newStart[], int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_int__array_slice((struct sidl_int__array *)(ptrdiff_t)*src,
                         *dimen, numElem, srcStart, srcStride, newStart);
}


void
SIDLFortran77Symbol(sidl_int__array_cast_f,
                    SIDL_INT__ARRAY_CAST_F,
                    sidl_int__array_cast_f)
  (int64_t *array, int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_int__array_cast((struct sidl__array *)(ptrdiff_t)*array);
}


void
SIDLFortran77Symbol(sidl_int__array_copy_f,
                    SIDL_INT__ARRAY_COPY_F,
                    sidl_int__array_copy_f)
  (int64_t *src, int64_t *dest)
{
  sidl_int__array_copy((struct sidl_int__array*)(ptrdiff_t)*src,
                      (struct sidl_int__array*)(ptrdiff_t)*dest);
}


void
SIDLFortran77Symbol(sidl_int__array_iscolumnorder_f,
                    SIDL_INT__ARRAY_ISCOLUMNORDER_F,
                    sidl_int__array_isColumnOrder_f)
  (int64_t *array, SIDL_F77_Bool *result)
{
  *result = (
    sidl_int__array_isColumnOrder((struct sidl_int__array *)(ptrdiff_t)*array)
    ? SIDL_F77_TRUE : SIDL_F77_FALSE);
}

void
SIDLFortran77Symbol(sidl_int__array_isroworder_f,
                    SIDL_INT__ARRAY_ISROWORDER_F,
                    sidl_int__array_isRowOrder_f)
  (int64_t *array, SIDL_F77_Bool *result)
{
  *result = (
    sidl_int__array_isRowOrder((struct sidl_int__array *)(ptrdiff_t)*array)
    ? SIDL_F77_TRUE : SIDL_F77_FALSE);
}


void
SIDLFortran77Symbol(sidl_int__array_dimen_f,
                    SIDL_INT__ARRAY_DIMEN_F,
                    sidl_int__array_dimen_f)
  (int64_t *array, int32_t *result)
{
  *result =
    sidl_int__array_dimen((struct sidl_int__array *)(ptrdiff_t)*array);
}


void
SIDLFortran77Symbol(sidl_int__array_stride_f,
                    SIDL_INT__ARRAY_STRIDE_F,
                    sidl_int__array_stride_f)
  (int64_t *array, int32_t *index, int32_t *result)
{
  *result =
    sidl_int__array_stride((struct sidl_int__array *)(ptrdiff_t)*array, *index);
}


void
SIDLFortran77Symbol(sidl_int__array_lower_f,
                    SIDL_INT__ARRAY_LOWER_F,
                    sidl_int__array_lower_f)
  (int64_t *array, int32_t*ind, int32_t *result)
{
  *result =
    sidl_int__array_lower((struct sidl_int__array *)(ptrdiff_t)*array, *ind);
}


void
SIDLFortran77Symbol(sidl_int__array_upper_f,
                    SIDL_INT__ARRAY_UPPER_F,
                    sidl_int__array_upper_f)
  (int64_t *array, int32_t *ind, int32_t *result)
{
  *result =
    sidl_int__array_upper((struct sidl_int__array *)(ptrdiff_t)*array, *ind);
}

void
SIDLFortran77Symbol(sidl_int__array_length_f,
                    SIDL_INT__ARRAY_LENGTH_F,
                    sidl_int__array_length_f)
  (int64_t *array, int32_t *ind, int32_t *result)
{
  *result =
    sidl_int__array_length((struct sidl_int__array *)(ptrdiff_t)*array, *ind);
}

void
SIDLFortran77Symbol(sidl_int__array_deleteref_f,
                    SIDL_INT__ARRAY_DELETEREF_F,
                    sidl_int__array_deleteRef_f)
  (int64_t *array)
{
  sidl_int__array_deleteRef((struct sidl_int__array*)(ptrdiff_t)*array);
}


void
SIDLFortran77Symbol(sidl_int__array_addref_f,
                    SIDL_INT__ARRAY_ADDREF_F,
                    sidl_int__array_addRef_f)
  (int64_t *array)
{
  sidl_int__array_addRef((struct sidl_int__array*)(ptrdiff_t)*array);
}


void
SIDLFortran77Symbol(sidl_int__array_borrow_f,
                    SIDL_INT__ARRAY_BORROW_F,
                    sidl_int__array_borrow_f)
  (int32_t *firstElement, int32_t *dimen, int32_t lower[], int32_t upper[], int32_t stride[], int64_t *result)
{
  *result = (ptrdiff_t)
    sidl_int__array_borrow(firstElement, 
                               *dimen,
                               lower,
                               upper,
                               stride);
}


static struct sidl_int__array *
smartCp(struct sidl_int__array *a) {
  sidl_int__array_addRef(a);
  return a;
}

static struct sidl__array_vtable CommonBlockVtable = {
  NULL, NULL, NULL
};

void
SIDLFortran77Symbol(sidl_int__array_cborrow_f,
                    SIDL_INT__ARRAY_CBORROW_F,
                    sidl_int__array_cborrow_f)
  (int32_t *firstElement, int32_t *dimen, int32_t lower[], int32_t upper[], int32_t stride[], int64_t *result)
{
  struct sidl_int__array *a = 
    sidl_int__array_borrow(firstElement, 
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
  *result = (ptrdiff_t)a;
}


/**
 * NOTE: THERE IS THE POTENTIAL FOR POINTER ALIGNMENT PROBLEMS WITH
 * THIS FUNCTION.  If index is zero on return, the array pointer
 * and ref are not properly aligned!
 */
void
SIDLFortran77Symbol(sidl_int__array_access_f,
                    SIDL_INT__ARRAY_ACCESS_F,
                    sidl_int__array_access_f)
  (int64_t *array,
   int32_t *ref,
   int32_t * restrict lower,
   int32_t * restrict upper,
   int32_t * restrict stride,
   int64_t * restrict index)
{
  struct sidl_int__array *ary = (struct sidl_int__array *)
    (ptrdiff_t)*array;
  const int32_t dimen = sidlArrayDim(ary);
  int32_t i;
  for(i = 0; i < dimen; ++i){
    lower[i] = sidlLower(ary, i);
    upper[i] = sidlUpper(ary, i);
    stride[i] = sidlStride(ary, i);
  }
  if (((((ptrdiff_t)(ary->d_firstElement)) -
        ((ptrdiff_t)ref)) % sizeof(int32_t)) == 0) {
    *index = ((int32_t *)(ary->d_firstElement) - ref) + 1;
  }
  else {
    *index = 0;
  }
}


void
SIDLFortran77Symbol(sidl_int__array_get1_f,
                    SIDL_INT__ARRAY_GET1_F,
                    sidl_int__array_get1_f)
  (int64_t *array,   int32_t *i1,
   int32_t *result)
{
  *result = 
    sidl_int__array_get1((struct sidl_int__array *)(ptrdiff_t)*array,
     *i1);
}


void
SIDLFortran77Symbol(sidl_int__array_set1_f,
                    SIDL_INT__ARRAY_SET1_F,
                    sidl_int__array_set1_f)
  (int64_t *array,   int32_t *i1,
   int32_t *value)
{
  sidl_int__array_set1((struct sidl_int__array *)(ptrdiff_t)*array,
   *i1,
   *value);
}


void
SIDLFortran77Symbol(sidl_int__array_get2_f,
                    SIDL_INT__ARRAY_GET2_F,
                    sidl_int__array_get2_f)
  (int64_t *array,   int32_t *i1,
   int32_t *i2,
   int32_t *result)
{
  *result = 
    sidl_int__array_get2((struct sidl_int__array *)(ptrdiff_t)*array,
     *i1,
     *i2);
}


void
SIDLFortran77Symbol(sidl_int__array_set2_f,
                    SIDL_INT__ARRAY_SET2_F,
                    sidl_int__array_set2_f)
  (int64_t *array,   int32_t *i1,
   int32_t *i2,
   int32_t *value)
{
  sidl_int__array_set2((struct sidl_int__array *)(ptrdiff_t)*array,
   *i1,
   *i2,
   *value);
}


void
SIDLFortran77Symbol(sidl_int__array_get3_f,
                    SIDL_INT__ARRAY_GET3_F,
                    sidl_int__array_get3_f)
  (int64_t *array,   int32_t *i1,
   int32_t *i2,
   int32_t *i3,
   int32_t *result)
{
  *result = 
    sidl_int__array_get3((struct sidl_int__array *)(ptrdiff_t)*array,
     *i1,
     *i2,
     *i3);
}


void
SIDLFortran77Symbol(sidl_int__array_set3_f,
                    SIDL_INT__ARRAY_SET3_F,
                    sidl_int__array_set3_f)
  (int64_t *array,   int32_t *i1,
   int32_t *i2,
   int32_t *i3,
   int32_t *value)
{
  sidl_int__array_set3((struct sidl_int__array *)(ptrdiff_t)*array,
   *i1,
   *i2,
   *i3,
   *value);
}


void
SIDLFortran77Symbol(sidl_int__array_get4_f,
                    SIDL_INT__ARRAY_GET4_F,
                    sidl_int__array_get4_f)
  (int64_t *array,   int32_t *i1,
   int32_t *i2,
   int32_t *i3,
   int32_t *i4,
   int32_t *result)
{
  *result = 
    sidl_int__array_get4((struct sidl_int__array *)(ptrdiff_t)*array,
     *i1,
     *i2,
     *i3,
     *i4);
}


void
SIDLFortran77Symbol(sidl_int__array_set4_f,
                    SIDL_INT__ARRAY_SET4_F,
                    sidl_int__array_set4_f)
  (int64_t *array,   int32_t *i1,
   int32_t *i2,
   int32_t *i3,
   int32_t *i4,
   int32_t *value)
{
  sidl_int__array_set4((struct sidl_int__array *)(ptrdiff_t)*array,
   *i1,
   *i2,
   *i3,
   *i4,
   *value);
}


void
SIDLFortran77Symbol(sidl_int__array_get5_f,
                    SIDL_INT__ARRAY_GET5_F,
                    sidl_int__array_get5_f)
  (int64_t *array,   int32_t *i1,
   int32_t *i2,
   int32_t *i3,
   int32_t *i4,
   int32_t *i5,
   int32_t *result)
{
  *result = 
    sidl_int__array_get5((struct sidl_int__array *)(ptrdiff_t)*array,
     *i1,
     *i2,
     *i3,
     *i4,
     *i5);
}


void
SIDLFortran77Symbol(sidl_int__array_set5_f,
                    SIDL_INT__ARRAY_SET5_F,
                    sidl_int__array_set5_f)
  (int64_t *array,   int32_t *i1,
   int32_t *i2,
   int32_t *i3,
   int32_t *i4,
   int32_t *i5,
   int32_t *value)
{
  sidl_int__array_set5((struct sidl_int__array *)(ptrdiff_t)*array,
   *i1,
   *i2,
   *i3,
   *i4,
   *i5,
   *value);
}


void
SIDLFortran77Symbol(sidl_int__array_get6_f,
                    SIDL_INT__ARRAY_GET6_F,
                    sidl_int__array_get6_f)
  (int64_t *array,   int32_t *i1,
   int32_t *i2,
   int32_t *i3,
   int32_t *i4,
   int32_t *i5,
   int32_t *i6,
   int32_t *result)
{
  *result = 
    sidl_int__array_get6((struct sidl_int__array *)(ptrdiff_t)*array,
     *i1,
     *i2,
     *i3,
     *i4,
     *i5,
     *i6);
}


void
SIDLFortran77Symbol(sidl_int__array_set6_f,
                    SIDL_INT__ARRAY_SET6_F,
                    sidl_int__array_set6_f)
  (int64_t *array,   int32_t *i1,
   int32_t *i2,
   int32_t *i3,
   int32_t *i4,
   int32_t *i5,
   int32_t *i6,
   int32_t *value)
{
  sidl_int__array_set6((struct sidl_int__array *)(ptrdiff_t)*array,
   *i1,
   *i2,
   *i3,
   *i4,
   *i5,
   *i6,
   *value);
}


void
SIDLFortran77Symbol(sidl_int__array_get7_f,
                    SIDL_INT__ARRAY_GET7_F,
                    sidl_int__array_get7_f)
  (int64_t *array,   int32_t *i1,
   int32_t *i2,
   int32_t *i3,
   int32_t *i4,
   int32_t *i5,
   int32_t *i6,
   int32_t *i7,
   int32_t *result)
{
  *result = 
    sidl_int__array_get7((struct sidl_int__array *)(ptrdiff_t)*array,
     *i1,
     *i2,
     *i3,
     *i4,
     *i5,
     *i6,
     *i7);
}


void
SIDLFortran77Symbol(sidl_int__array_set7_f,
                    SIDL_INT__ARRAY_SET7_F,
                    sidl_int__array_set7_f)
  (int64_t *array,   int32_t *i1,
   int32_t *i2,
   int32_t *i3,
   int32_t *i4,
   int32_t *i5,
   int32_t *i6,
   int32_t *i7,
   int32_t *value)
{
  sidl_int__array_set7((struct sidl_int__array *)(ptrdiff_t)*array,
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
SIDLFortran77Symbol(sidl_int__array_set_f,
                    SIDL_INT__ARRAY_SET_F,
                    sidl_int__array_set_f)
  (int64_t *array, int32_t indices[], int32_t *value)
{
  sidl_int__array_set((struct sidl_int__array *)(ptrdiff_t)*array, indices,  *value);
}


void
SIDLFortran77Symbol(sidl_int__array_get_f,
                    SIDL_INT__ARRAY_GET_F,
                    sidl_int__array_get_f)
  (int64_t *array, int32_t indices[], int32_t *value)
{
  *value = 
     sidl_int__array_get((struct sidl_int__array *)(ptrdiff_t)*array, indices);
}



#endif /* not FORTRAN77_DISABLED */
