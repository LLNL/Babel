// 
// File:          cxx_TimeStepper_Impl.cc
// Symbol:        cxx.TimeStepper-v2.0
// Symbol Type:   class
// Babel Version: 0.9.6
// sidl Created:  20041004 21:44:22 PDT
// Generated:     20041004 21:44:24 PDT
// Description:   Server-side implementation for cxx.TimeStepper
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// babel-version = 0.9.6
// source-line   = 7
// source-url    = file:/home/kumfert/scdemo/hands-on/task_0/babel-life/libCxx/cxxlife.sidl
// 
#include "cxx_TimeStepper_Impl.hh"

// DO-NOT-DELETE splicer.begin(cxx.TimeStepper._includes)
// Put additional includes or other arbitrary code here...
// DO-NOT-DELETE splicer.end(cxx.TimeStepper._includes)

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

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  init[]
 */
void
cxx::TimeStepper_impl::init (
  /*in*/ ::conway::Environment env,
  /*in*/ ::conway::Ruleset rules ) 
throw () 
{
  // DO-NOT-DELETE splicer.begin(cxx.TimeStepper.init)
  d_env = env;
  d_rules = rules;
  d_step = 0;
  // DO-NOT-DELETE splicer.end(cxx.TimeStepper.init)
}

/**
 * advance one more timestep 
 * returns population count at new timestep
 */
int32_t
cxx::TimeStepper_impl::step () 
throw () 

{
  // DO-NOT-DELETE splicer.begin(cxx.TimeStepper.step)
  if (d_env._is_nil() || d_rules._is_nil() ) { 
    return -1;
  }
  ::sidl::array<int> current = d_env.getGrid();
  if ( d_step==0 ) { 
    current.ensure(2,::sidl::column_major_order);
    d_env.setGrid( current );
  } 
  int lower[2];
  int upper[2];
  lower[0]=current.lower(0);
  lower[1]=current.lower(1);
  upper[0]=current.upper(0);
  upper[1]=current.upper(1);

  if ( d_next._is_nil() ) { 
    d_next = ::sidl::array<int>::createCol(2,lower,upper);  
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
 * check the number of steps taken 
 */
int32_t
cxx::TimeStepper_impl::nStepsTaken () 
throw () 

{
  // DO-NOT-DELETE splicer.begin(cxx.TimeStepper.nStepsTaken)
  return d_step;
  // DO-NOT-DELETE splicer.end(cxx.TimeStepper.nStepsTaken)
}


// DO-NOT-DELETE splicer.begin(cxx.TimeStepper._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(cxx.TimeStepper._misc)

