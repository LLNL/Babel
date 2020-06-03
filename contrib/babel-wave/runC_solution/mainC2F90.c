#include <stdio.h>
#include "wave2d.h"
#include "f90.h"
#include "sidl_Exception.h"

static void printPressure(int step,
			  const struct sidl_double__array *pres)
{
  int i, j;
  printf("step %d\n", step);
  for(i = sidlLower(pres, 0); i <= sidlUpper(pres, 0); ++i) {
    for(j = sidlLower(pres, 1); j <= sidlUpper(pres, 1); ++j) {
      printf(" %10g", sidlArrayElem2(pres, i, j));
    }
    putc('\n', stdout);
  }
}

int main(int argc, char **argv)
{
  int i, j;
  sidl_BaseInterface exception = NULL;
  f90_ScalarField d = NULL;
  wave2d_ScalarField wd = NULL;
  f90_WavePropagator wp = NULL;
  struct sidl_double__array *p = NULL;
  d = f90_ScalarField__create(&exception); SIDL_REPORT(exception);
  wd = wave2d_ScalarField__cast(d, &exception); SIDL_REPORT(exception);
  wp = f90_WavePropagator__create(&exception); SIDL_REPORT(exception);
  p = sidl_double__array_create2dRow(2, 2);
  f90_ScalarField_init(d, 0.0, 0.0, 1.0, 1.0, 0.5, 0.2, &exception);
  SIDL_REPORT(exception);
  for(i = 0; i < 2; ++i) {
    for(j = 0; j < 2; ++j) {
      sidlArrayElem2(p, i, j) = 1.0;
    }
  }
  f90_WavePropagator_init(wp, wd, p, &exception);
  wave2d_ScalarField_deleteRef(wd, &exception); SIDL_REPORT(exception);
  sidl_double__array_deleteRef(p);
  p = f90_WavePropagator_getPressure(wp, &exception);
  SIDL_REPORT(exception);
  printPressure(0, p);
  sidl_double__array_deleteRef(p);
  for(i = 0; i < 10; ++i) {
    f90_WavePropagator_step(wp, 1, &exception); SIDL_REPORT(exception);
    p = f90_WavePropagator_getPressure(wp, &exception);
    SIDL_REPORT(exception);
    printPressure(i, p);
    sidl_double__array_deleteRef(p);
  }
 EXIT:;
  if (wp) f90_WavePropagator_deleteRef(wp, &exception);
  if (d) f90_ScalarField_deleteRef(d, &exception);
}
