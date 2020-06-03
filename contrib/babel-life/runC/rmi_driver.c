#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

/* language independent interfaces */
#include "conway_TimeStepper.h"
#include "conway_Ruleset.h"
#include "conway_Environment.h"

/*
 * Static linking requires the actual classes to be declared at compile
 * time. Dynamic linking can defer this decision to run-time. We'll do
 * static for now.
 */
#include "c_Ruleset.h"
#include "cxx_Environment.h"
#include "cxx_TimeStepper.h"
#include "sidl_Exception.h"
#include "sidl_BaseInterface.h"
#include "sidl_rmi_ProtocolFactory.h"
#include "sidl_rmi_ServerRegistry.h"
#include "sidl_rmi_ServerInfo.h"
#include <pthread.h>
#include "sidlx_rmi_SimpleOrb.h"

/* Create a 20x50 grid, where live cells appear as runs of five on the
 * middle row 
 */
int32_t init_grid(conway_Environment env, sidl_BaseInterface* ex) {
  const int32_t height=20, width=50, i = 10;
  int32_t j, k;                 /* loop indices */
  int32_t population = 0;
  struct sidl_int__array *grid=NULL;
  conway_Environment_init(env, height, width, ex); SIDL_CHECK(*ex);
  grid = conway_Environment_getGrid(env,ex); SIDL_CHECK(*ex);
  for (j = 0; j < 45; j+=6) {
    for(k = 0; k < 5; ++k) {
      /* ADVANCED: change this function call into a macro */
      sidl_int__array_set2(grid, i, j + k, 1);
      ++population;
    }
  }
  conway_Environment_setGrid( env, grid, ex); SIDL_CHECK(*ex);
  sidl_int__array_deleteRef(grid); grid = NULL;
  
  return population;
 EXIT:
  if ( grid != NULL ) { 
    sidl_int__array_deleteRef(grid); grid = NULL;
  } 
  return -1;
}

void print_grid( conway_Environment env, sidl_BaseInterface* ex) {
  static const char icon[] = { '.', '0' };
  struct sidl_int__array* grid = 
    conway_Environment_getGrid(env, ex); SIDL_CHECK(*ex);
  const int32_t
    low0 = sidl_int__array_lower(grid, 0),
    up0 = sidl_int__array_upper(grid, 0),
    low1 = sidl_int__array_lower(grid, 1),
    up1 = sidl_int__array_upper(grid, 1);
  int32_t i, j;                 /* array indices */
  for(i = low0; i <= up0; ++i) {
    for(j = low1; j <= up1; ++j) {
      putchar(icon[sidl_int__array_get2(grid, i, j)]);
    }
    putchar('\n');
  }
 EXIT:
  return;
}

void main_loop(conway_Ruleset     rs,
               conway_Environment env,
               conway_TimeStepper ts, 
	       sidl_BaseInterface* ex) {
  /* int32_t corresponds to a sidl integer */
  int32_t population = init_grid( env, ex); SIDL_CHECK(*ex);
  int32_t step = 0;
  
  conway_TimeStepper_init(ts, env, rs, ex); SIDL_CHECK(*ex);

  while (population > 0) {
    population = conway_TimeStepper_step(ts, ex); SIDL_CHECK(*ex);
    step = conway_TimeStepper_nStepsTaken(ts, ex); SIDL_CHECK(*ex);
    printf("step %d:  population %d\n", step, population);
    print_grid(env, ex); SIDL_CHECK(*ex);
    sleep(1);
  }
 EXIT:
  ;  
}

int main(int argc, char **argv) {
  /* create concrete classes but immediately cast them to their parent
   * interfaces */
  sidl_BaseInterface ex = NULL;

  sidl_BaseException s_b_e = NULL;
  sidlx_rmi_SimpleOrb echo = NULL;
  int tid;
  sidl_rmi_ServerInfo si = NULL;
  echo = sidlx_rmi_SimpleOrb__create(&ex);SIDL_CHECK(ex);
  
  sidlx_rmi_SimpleOrb_requestPort( echo, 9997, &ex);SIDL_CHECK(ex);
  
  tid = sidlx_rmi_SimpleOrb_run( echo, &ex );SIDL_CHECK(ex);
  si = sidl_rmi_ServerInfo__cast(echo, &ex );SIDL_CHECK(ex);
  sidl_rmi_ServerRegistry_registerServer(si, &ex);SIDL_CHECK(ex);

  if(!sidl_rmi_ProtocolFactory_addProtocol("simhandle","sidlx.rmi.SimHandle",&ex )){
    printf("Error in addProtocol\n");
    exit(2);
  }
  c_Ruleset cr = c_Ruleset__createRemote("simhandle://localhost:9999",&ex); SIDL_CHECK(ex);
  conway_Ruleset rs = conway_Ruleset__cast(cr,&ex); SIDL_CHECK(ex);
  c_Ruleset_deleteRef(cr,&ex); SIDL_CHECK(ex);

  cxx_Environment cxxenv= cxx_Environment__createRemote("simhandle://localhost:9999",&ex); SIDL_CHECK(ex);
  conway_Environment env =  conway_Environment__cast(cxxenv,&ex); SIDL_CHECK(ex);
  cxx_Environment_deleteRef(cxxenv,&ex); SIDL_CHECK(ex);

  cxx_TimeStepper cxxts = cxx_TimeStepper__createRemote("simhandle://localhost:9999",&ex); SIDL_CHECK(ex);
  conway_TimeStepper ts = conway_TimeStepper__cast(cxxts,&ex); SIDL_CHECK(ex);
  cxx_TimeStepper_deleteRef(cxxts,&ex); SIDL_CHECK(ex);

  main_loop(rs, env, ts, &ex); SIDL_CHECK(ex);

  /* delete references when done */
  conway_Ruleset_deleteRef(rs,&ex); SIDL_CHECK(ex);
  conway_Environment_deleteRef(env,&ex); SIDL_CHECK(ex);
  conway_TimeStepper_deleteRef(ts,&ex); SIDL_CHECK(ex);

  return 0; /* indicate success */
 EXIT:
  {
  sidl_BaseInterface throwaway_exception = NULL;
  sidl_BaseException sbe = sidl_BaseException__cast(ex,&throwaway_exception);
  if ( throwaway_exception != NULL) { 
    fprintf(stderr,"failed to cast ex to BaseException. (should not happen)\n");
    return -1;
  }
  char* note = sidl_BaseException_getNote(sbe,&throwaway_exception);
  if ( throwaway_exception != NULL) { 
    fprintf(stderr,"failed to get note from BaseException. (should not happen)\n");
    return -1;
  }
  fprintf(stderr,note);
  conway_Ruleset_deleteRef(rs,&throwaway_exception); 
  conway_Environment_deleteRef(env,&throwaway_exception);
  conway_TimeStepper_deleteRef(ts,&throwaway_exception); 
  }
  return 1;
}

