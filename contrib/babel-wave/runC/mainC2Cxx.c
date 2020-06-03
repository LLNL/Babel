#include <stdio.h>
#include "wave2d.h"
/* TODO: add a #include to load everything in the cxx package */
#include "sidl_Exception.h"

/**
 * output the pressure for a step
 * no changes needed
 */
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
  cxx_ScalarField d = NULL;
  wave2d_ScalarField wd = NULL;
  cxx_WavePropagator wp = NULL;
  struct sidl_double__array *p = NULL;
  d = /* TODO: create an instance of a cxx.ScalarField */ NULL;
  SIDL_REPORT(exception);
  wd = /* TODO: cast d to a wave2d.ScalarField */ NULL;
  SIDL_REPORT(exception);
  wp = /* TODO: initialize wp to a new instance of cxx.WavePropagator */ = NULL;
  SIDL_REPORT(exception);
  p = sidl_double__array_create2dRow(2, 2);
  cxx_ScalarField_init(d, 0.0, 0.0, 1.0, 1.0, 0.5, 0.2, &exception);
  SIDL_REPORT(exception);
  for(i = 0; i < 2; ++i) {
    for(j = 0; j < 2; ++j) {
      sidlArrayElem2(p, i, j) = 1.0;
    }
  }
  /* TODO: call cxx.WavePropagator.init on wp with arguments wd, p */
  SIDL_REPORT(exception);
  wave2d_ScalarField_deleteRef(wd, &exception); SIDL_REPORT(exception);
  /* TODO: delete ref the array p */
  /* TODO: set p equal to the result of a call to cxx.WavePropagator.getPressure */
  SIDL_REPORT(exception);
  printPressure(0, p);
  /* TODO: delete ref the array p */
  for(i = 0; i < 10; ++i) {
    /* TODO: call cxx.WavePropagator.step on wp with argument 1 */
    SIDL_REPORT(exception);
    /* TODO: set p equal to the result of a call to cxx.WavePropagator.getPressure */
    SIDL_REPORT(exception);
    printPressure(i, p);
    sidl_double__array_deleteRef(p);
  }
 EXIT:;
  if (wp) cxx_WavePropagator_deleteRef(wp, &exception);
  if (d) cxx_ScalarField_deleteRef(d, &exception);
}
