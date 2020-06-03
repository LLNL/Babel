// 
// File:          cxx_TimeStepper_Impl.cxx
// Symbol:        cxx.TimeStepper-v2.0
// Symbol Type:   class
// Babel Version: 1.1.1
// Description:   Server-side implementation for cxx.TimeStepper
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "cxx_TimeStepper_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_conway_Environment_hxx
#include "conway_Environment.hxx"
#endif
#ifndef included_conway_Ruleset_hxx
#include "conway_Ruleset.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(cxx.TimeStepper._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(cxx.TimeStepper._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
cxx::TimeStepper_impl::TimeStepper_impl() : StubBase(reinterpret_cast< void*>(
  ::cxx::TimeStepper::_wrapObj(reinterpret_cast< void*>(this))),false) , 
  _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(cxx.TimeStepper._ctor2)
  // Insert-Code-Here {cxx.TimeStepper._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(cxx.TimeStepper._ctor2)
}

// user defined constructor
void cxx::TimeStepper_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(cxx.TimeStepper._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(cxx.TimeStepper._ctor)
}

// user defined destructor
void cxx::TimeStepper_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(cxx.TimeStepper._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(cxx.TimeStepper._dtor)
}

// static class initializer
void cxx::TimeStepper_impl::_load() {
  // DO-NOT-DELETE splicer.begin(cxx.TimeStepper._load)
  // Insert-Code-Here {cxx.TimeStepper._load} (class initialization)
  // DO-NOT-DELETE splicer.end(cxx.TimeStepper._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  init[]
 */
void
cxx::TimeStepper_impl::init_impl (
  /* in */::conway::Environment& env,
  /* in */::conway::Ruleset& rules ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.TimeStepper.init)
  d_env = env;
  d_rules = rules;
  d_step = 0;
  // DO-NOT-DELETE splicer.end(cxx.TimeStepper.init)
}

/**
 *  
 * advance one more timestep 
 * returns population count at new timestep
 */
int32_t
cxx::TimeStepper_impl::step_impl () 

{
  // DO-NOT-DELETE splicer.begin(cxx.TimeStepper.step)
  if (d_env._is_nil() || d_rules._is_nil() ) { 
    return -1;
  }
  UCXX::sidl::array<int> current = d_env.getGrid();
  if ( d_step==0 ) { 
    current.ensure(2,UCXX::sidl::column_major_order);
    d_env.setGrid( current );
  } 
  int lower[2];
  int upper[2];
  lower[0]=current.lower(0);
  lower[1]=current.lower(1);
  upper[0]=current.upper(0);
  upper[1]=current.upper(1);

  if ( d_next._is_nil() ) { 
    d_next = UCXX::sidl::array<int>::createCol(2,lower,upper);  
  }

  int population_count=0;
  for ( int x=lower[0]; x<=upper[0]; x++ ) { 
    for ( int y=lower[1]; y<=upper[1]; y++ ) { 
      int isalive = d_rules.setAlive(x,y,d_env);
      d_next.set(x,y,isalive);
      population_count += (isalive) ? 1 : 0;
    }
  }
  d_env.setGrid( d_next );
  //  d_next = current; // save last grid for next time.
  ++d_step;
  return population_count;
  // DO-NOT-DELETE splicer.end(cxx.TimeStepper.step)
}

/**
 *  check the number of steps taken 
 */
int32_t
cxx::TimeStepper_impl::nStepsTaken_impl () 

{
  // DO-NOT-DELETE splicer.begin(cxx.TimeStepper.nStepsTaken)
  return d_step;
  // DO-NOT-DELETE splicer.end(cxx.TimeStepper.nStepsTaken)
}


// DO-NOT-DELETE splicer.begin(cxx.TimeStepper._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(cxx.TimeStepper._misc)

