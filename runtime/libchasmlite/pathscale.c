/*
 * File:        pathscale.c
 * Copyright:   (c) 2008 Lawrence Livermore National Security, LLC
 * Revision:    @(#) $Revision$
 * Date:        $Date$
 * Description: 
 *
 */

#include "pathscale.h"
#include "pathscale_dv.h"

static unsigned
isContigColMajor(const int rank,
                 const int elem_size,
                 const unsigned long *extent,
                 const long *strideMult)
{
  int i;
  long accum = elem_size;
  for(i = 0; i < rank; ++i) {
    if (extent[i] != accum) return 0;
    accum *= extent[i];
  }
  return 1;
}

static int
setArrayDesc_PathScale(void *desc,
                       void *base_addr,
                       int rank,
                       F90_DescType desc_type,
                       F90_ArrayDataType data_type,
                       unsigned long element_size,
                       const long *lowerBound,
                       const unsigned long *extent,
                       const long * strideMult
                       )
{
  DopeVectorType *dv = (DopeVectorType *)desc;
  const signed long divisor = 4L;
  int i;
  dv->orig_base = dv->base_addr.a.ptr;
  dv->orig_size = dv->base_addr.a.el_len;
  dv->base_addr.a.ptr = base_addr;
  dv->type_lens.int_len = (unsigned int)
    (dv->base_addr.a.el_len = (unsigned long)(element_size * 8));

  dv->assoc = 1;
  dv->ptr_alloc = 0;
  dv->p_or_a = POINTTR;
  dv->alloc_cpnt = 0;
  dv->a_contig = (unsigned int)isContigColMajor(rank, element_size, 
                                                extent, strideMult);
  dv->n_dim = (unsigned int)rank;
  dv->type_lens.dpflag = 0;
  switch(data_type) {
  case F90_Integer4:
    dv->type_lens.type = DVTYPE_INTEGER;
    dv->type_lens.kind_or_star = DVD_KIND_CONST;
    dv->type_lens.dec_len = 4;
    break;
  case F90_Integer8:
    dv->type_lens.type = DVTYPE_INTEGER;
    dv->type_lens.kind_or_star = DVD_KIND_CONST;
    dv->type_lens.dec_len = 8;
    break;
  case F90_Real:
    dv->type_lens.type = DVTYPE_REAL;
    dv->type_lens.kind_or_star = DVD_KIND_CONST;
    dv->type_lens.dec_len = 4;
    break;
  case F90_Double:
    dv->type_lens.type = DVTYPE_REAL;
    dv->type_lens.kind_or_star = DVD_KIND_CONST;
    dv->type_lens.dec_len = 8;
    break;
  case F90_Complex:
    dv->type_lens.type = DVTYPE_COMPLEX;
    dv->type_lens.kind_or_star = DVD_KIND_CONST;
    dv->type_lens.dec_len = 8;
    break;
  case F90_DComplex:
    dv->type_lens.type = DVTYPE_COMPLEX;
    dv->type_lens.kind_or_star = DVD_KIND_CONST;
    dv->type_lens.dec_len = 16;
    break;
  default:
    return -1;
  }
  for(i = 0; i < rank; ++i) {
    dv->dimension[i].low_bound = lowerBound[i];
    dv->dimension[i].extent = extent[i];
    dv->dimension[i].stride_mult = strideMult[i]/divisor;
  }
  return 0;
}

static unsigned long
getArrayDescSize_PathScale(int rank)
{
  if (rank < 0 || rank > 7) return 0;
  return sizeof(DopeVectorType) - sizeof(struct DvDimen)*(7-rank);
}

static int
nullifyArrayDesc_PathScale(void *desc, int rank)
{
  int i;
  DopeVectorType *dv = (DopeVectorType *)desc;
  /* this appears to be overkill */
  dv->base_addr.a.ptr = 0;
  dv->base_addr.a.el_len = 0;
  dv->assoc = 0;
  dv->ptr_alloc = 0;
  dv->p_or_a = NOT_P_OR_A;
  dv->a_contig = 0;
  dv->alloc_cpnt = 0;
  dv->n_dim = (unsigned)rank;
  dv->orig_base = 0;
  dv->orig_size = 0;
  if ((rank >= 1) && (rank <= 7)) {
    for (i = 0; i < rank; ++i) {
      dv->dimension[i].low_bound = 1;
      dv->dimension[i].extent = 0;
      dv->dimension[i].stride_mult = 0;
    }
  }
  return ((rank >= 1) && (rank <= 7)) ? 0 : -1;
}

void F90_SetCCFunctions_PathScale(F90_CompilerCharacteristics *cc)
{
  cc->setArrayDesc = setArrayDesc_PathScale;
  cc->getArrayDescSize = getArrayDescSize_PathScale;
  cc->nullifyArrayDesc = nullifyArrayDesc_PathScale;
}
