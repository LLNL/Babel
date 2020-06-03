/*********************************************************************
* File:        sidl_dcomplex_fStub.c
* Copyright:   (C) 2001-2004 Lawrence Livermore National Security, LLC
* Description: FORTRAN API for arrays of dcomplex
* Revision:    $Revision$
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
#ifndef FORTRAN03_DISABLED
#include "sidlf03array.h"
#endif
#include <stdlib.h>
#include <stddef.h>


#ifndef FORTRAN03_DISABLED
/*---------------------------------------------------------------------*
 * Fortran 03 Array Routines
 *---------------------------------------------------------------------*/

#include "sidl_dcomplex_fAbbrev.h"

static struct sidl_dcomplex__array *
smartCp(struct sidl_dcomplex__array *a) {
  sidl_dcomplex__array_addRef(a);
  return a;
}

static struct sidl__array_vtable CommonBlockVtable = {
  NULL, NULL, NULL
};

struct sidl_dcomplex__array *
sidl_dcomplex__array_cborrow_f03(struct sidl_dcomplex *firstElement,
                           int32_t dimen,
                           int32_t lower[],
                           int32_t upper[],
                           int32_t stride[])
{
  struct sidl_dcomplex__array *a = 
    sidl_dcomplex__array_borrow(firstElement, dimen, lower, upper, stride);
  if (a) {
    if (CommonBlockVtable.d_destroy == NULL) {
      CommonBlockVtable.d_destroy = a->d_metadata.d_vtable->d_destroy;
      CommonBlockVtable.d_smartcopy = (struct sidl__array *(*)(struct sidl__array *))smartCp;
      CommonBlockVtable.d_arraytype = a->d_metadata.d_vtable->d_arraytype;
    }
    a->d_metadata.d_vtable = &CommonBlockVtable;
  }
  return a;
}


void
SIDLFortran90Symbol(sidl_dcomplex__array_bindc_cast_c,
                    SIDL_DCOMPLEX__ARRAY_BINDC_CAST_C,
                    sidl_dcomplex__array_bindc_cast_c)
  (struct sidl__array **array, int32_t *dimen, struct sidl_fortran2003_array *result)
{
  struct sidl_dcomplex__array *tmp = sidl_dcomplex__array_cast(*array);
  if(tmp && sidlArrayDim(tmp) != *dimen) tmp = NULL;
  (void) sidl_dcomplex__array_convert2f03(tmp, *dimen, result);
}



#endif /* not FORTRAN03_DISABLED */