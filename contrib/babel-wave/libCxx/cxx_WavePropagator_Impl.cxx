// 
// File:          cxx_WavePropagator_Impl.cxx
// Symbol:        cxx.WavePropagator-v1.0
// Symbol Type:   class
// Babel Version: 1.2.0
// Description:   Server-side implementation for cxx.WavePropagator
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "cxx_WavePropagator_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_wave2d_ScalarField_hxx
#include "wave2d_ScalarField.hxx"
#endif
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(cxx.WavePropagator._includes)
#include <iostream>
using namespace std;
void disp_array( std::string name, sidl::array<double> array );
// DO-NOT-DELETE splicer.end(cxx.WavePropagator._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
cxx::WavePropagator_impl::WavePropagator_impl() : StubBase(reinterpret_cast< 
  void*>(::cxx::WavePropagator::_wrapObj(reinterpret_cast< void*>(this))),
  false) , _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(cxx.WavePropagator._ctor2)
  // Insert-Code-Here {cxx.WavePropagator._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(cxx.WavePropagator._ctor2)
}

// user defined constructor
void cxx::WavePropagator_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(cxx.WavePropagator._ctor)
  // Insert-Code-Here {cxx.WavePropagator._ctor} (constructor)
  // DO-NOT-DELETE splicer.end(cxx.WavePropagator._ctor)
}

// user defined destructor
void cxx::WavePropagator_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(cxx.WavePropagator._dtor)
  // Insert-Code-Here {cxx.WavePropagator._dtor} (destructor)
  // DO-NOT-DELETE splicer.end(cxx.WavePropagator._dtor)
}

// static class initializer
void cxx::WavePropagator_impl::_load() {
  // DO-NOT-DELETE splicer.begin(cxx.WavePropagator._load)
  // Insert-Code-Here {cxx.WavePropagator._load} (class initialization)
  // DO-NOT-DELETE splicer.end(cxx.WavePropagator._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * Method:  init[]
 */
void
cxx::WavePropagator_impl::init_impl (
  /* in */::wave2d::ScalarField& density,
  /* in array<double,2> */::sidl::array<double>& pressure ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.WavePropagator.init)
  ::sidl::array<double> dfield = density.getData();
  if ( dfield._not_nil() && 
       pressure._not_nil() &&
       (dfield.dimen() == pressure.dimen()) && 
       (dfield.length(0) == pressure.length(0)) && 
       (dfield.length(1) == pressure.length(1))) { 
    d_density = density;
    d_lower[0] = dfield.lower(0)-1;
    d_lower[1] = dfield.lower(1)-1;
    d_upper[0] = dfield.upper(0)+1;
    d_upper[1] = dfield.upper(1)+1;
    p_0 = ::sidl::array<double>::createRow(2, d_lower, d_upper );
    p_1 = ::sidl::array<double>::createRow(2, d_lower, d_upper );
    p_2 = ::sidl::array<double>::createRow(2, d_lower, d_upper );

    // init p_0 and p_2 to all zeros.
    for( int32_t i = d_lower[0]; i<= d_upper[0]; ++i ) { 
      for( int32_t j = d_lower[1]; j<= d_upper[1]; ++j ) { 
	p_0.set(i,j,0.0);
	p_2.set(i,j,0.0);
      }
    }

    // set border of p_1 to all zeros
    for( int32_t i = d_lower[0]; i<= d_upper[0]; ++i ) { 
      p_1.set(i,d_lower[1],0.0);
      p_1.set(i,d_upper[1],0.0);
    } 
    for( int32_t j = d_lower[1]; j<= d_upper[1]; ++j ) { 
      p_1.set(d_lower[0],j,0.0);
      p_1.set(d_upper[0],j,0.0); 
    }

    // copy pressure field to interior
    p_1.copy( pressure );

  }
 
  // DO-NOT-DELETE splicer.end(cxx.WavePropagator.init)
}

/**
 * Method:  step[]
 */
void
cxx::WavePropagator_impl::step_impl (
  /* in */int32_t n ) 
{
  // DO-NOT-DELETE splicer.begin(cxx.WavePropagator.step)
  sidl::array<double> density = d_density.getData();

  for( int32_t t=0 ; t<n ; ++t ) { 
    for ( int32_t i=d_lower[0]+1; i<=d_upper[0]-1; ++i ) { 
      for ( int32_t j=d_lower[1]+1; j<=d_upper[1]-1; ++j ) { 
	int32_t c_i = i-(d_lower[0]+1)+density.lower(0);
	int32_t c_j = j-(d_lower[1]+1)+density.lower(1);
	double c = density.get(c_i, c_j);
	double val = (2-4*c)*p_1.get(i,j)
	  + c*(p_1.get(i,j-1) + p_1.get(i,j+1) + p_1.get(i-1,j) + p_1.get(i+1,j))
	  - p_0.get(i,j);
	p_2.set(i,j,val);
      }
    }
    sidl::array<double> tmp = p_0;
    p_0 = p_1;
    p_1 = p_2;
    p_2 = tmp;
  }
  // DO-NOT-DELETE splicer.end(cxx.WavePropagator.step)
}

/**
 * Method:  getPressure[]
 */
::sidl::array<double>
cxx::WavePropagator_impl::getPressure_impl () 

{
  // DO-NOT-DELETE splicer.begin(cxx.WavePropagator.getPressure)
  int32_t n_elem[2];
  n_elem[0] = p_1.length(0)-2;
  n_elem[1] = p_1.length(1)-2;
  int32_t srcStart[2];
  srcStart[0] = p_1.lower(0)+1;
  srcStart[1] = p_1.lower(1)+1;
  int32_t srcStride[2];
  srcStride[0] = p_1.stride(0);
  srcStride[1] = p_1.stride(1);
  return p_1.slice(2, n_elem, srcStart, NULL, NULL );
  // DO-NOT-DELETE splicer.end(cxx.WavePropagator.getPressure)
}


// DO-NOT-DELETE splicer.begin(cxx.WavePropagator._misc)
void disp_array( std::string name, sidl::array<double> array ) { 
  cout << name << "[" << array.lower(0) << "," << array.lower(1) << "] to [" 
       << array.upper(1) << ", " << array.upper(1) << "]" << endl << "\t" ; 
    for ( int32_t i=array.lower(0); i<=array.upper(0); ++i ) { 
      for ( int32_t j=array.lower(1); j<=array.upper(1); ++j ) { 
	cout << array.get(i,j) << "\t" ;
      }
      cout << endl << "\t";
  }
  cout << endl;
}

// DO-NOT-DELETE splicer.end(cxx.WavePropagator._misc)

