#include <stdio.h>
/* TODO: add a #include to load everything in the wave2d package */
#include "f90.h"
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
  f90_ScalarField d = NULL;
  wave2d_ScalarField wd = NULL;
  f90_WavePropagator wp = NULL;
  struct sidl_double__array *p = NULL;
  d = /* TODO: create an instance of f90.ScalarField */ NULL;
  SIDL_REPORT(exception);
  wd = /* TODO: cast d to a wave2d.ScalarField */ NULL; 
  SIDL_REPORT(exception);
  wp = /* TODO: create a new instance of f90.WavePropagator */ NULL;
  SIDL_REPORT(exception);
  p = sidl_double__array_create2dRow(2, 2);
  f90_ScalarField_init(d, 0.0, 0.0, 1.0, 1.0, 0.5, 0.2, &exception);
  SIDL_REPORT(exception);
  for(i = 0; i < 2; ++i) {
    for(j = 0; j < 2; ++j) {
      /* TODO: set element i, j to 1.0 */
    }
  }
  /* call f90.WavePropagator.init on wp with arguments wd, p */n
  SIDL_REPORT(exception);
  wave2d_ScalarField_deleteRef(wd, &exception); SIDL_REPORT(exception);
  /* TODO: deleteRef the array p */
  /* TODO: set p equal to the result of a call to f90.WavePropagator.getPressure */
  SIDL_REPORT(exception);
  printPressure(0, p);
  sidl_double__array_deleteRef(p);
  for(i = 0; i < 10; ++i) {
    /* TODO: call f90.WavePropagator.step on wp with argument 1 */
    SIDL_REPORT(exception);
    /* TODO: set p equal to the result of a call to f90.WavePropagator.getPressure */
    SIDL_REPORT(exception);
    printPressure(i, p);
    sidl_double__array_deleteRef(p);
  }
 EXIT:;
  if (wp) f90_WavePropagator_deleteRef(wp, &exception);
  if (d) f90_ScalarField_deleteRef(d, &exception);
}
