#include <iostream>
#include "wave2d.hxx"
#include "cxx_WavePropagator.hxx"
#include "cxx_ScalarField.hxx"

using namespace std;

int main() { 
  cxx::ScalarField d = cxx::ScalarField::_create();
  d.init( 0.0, 0.0, 1.0, 1.0, 0.5, 0.2);
  
  cxx::WavePropagator wp = cxx::WavePropagator::_create();
  sidl::array<double> p = sidl::array<double>::create2dRow(2,2);
  p.set(0,0,1.0);
  p.set(0,1,1.0);
  p.set(1,0,1.0);
  p.set(1,1,1.0);
  wp.init( d, p );
 
  sidl::array<double> p2 = wp.getPressure();  
  cout << "step 0" << endl;
  cout << p2.get(0,0) << "  " << p2.get(0,1) << endl;
  cout << p2.get(1,0) << "  " << p2.get(1,1) << endl << endl;

  for( int i=0; i<10; ++i ) { 
    wp.step(1);    
    p2 = wp.getPressure();
    cout << "step " << i << endl;
    cout << p2.get(0,0) << "  " << p2.get(0,1) << endl;
    cout << p2.get(1,0) << "  " << p2.get(1,1) << endl << endl;
  }

  
}
