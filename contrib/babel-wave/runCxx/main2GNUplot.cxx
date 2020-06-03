#include <iostream>
#include <fstream>
#include <sstream>
#include <iomanip>
#include <cstdio>
#include "wave2d.hxx"
#include "cxx_WavePropagator.hxx"
#include "cxx_ScalarField.hxx"
#include "cxx_ShapeFactory.hxx"

using namespace std;

::std::string
gen_filename(const ::std::string root, 
	     const int iteration, 
	     const ::std::string suffix ) { 
  ::std::stringstream s;
  s << root;
  if ( iteration > 0 ) { 
    s << std::setfill('0') << std::setw(5) << iteration;
  }
  s << suffix; 
  return s.str();
}

void write_array( const sidl::array<double>& a, ::std::string filename, int iteration =-1 ) { 
  ::std::ofstream ostr;
  ::std::string file = gen_filename( filename, iteration, ".dat" );
  ostr.open(file.c_str());
  ostr << "# A GNUplot grid in matrix format" << std::endl;
  int i_min = a.lower(0);
  int j_min = a.lower(1);
  int i_max = a.upper(0);
  int j_max = a.upper(1);
  for( int i=i_min; i<= i_max; ++i ) { 
    for ( int j = j_min; j<= j_max; ++j ) { 
      ostr << a.get(i,j) << " " ;
    }
    ostr << ::std::endl;
  }
  ostr.close();
}

void plot_data( const ::std::string& file, 
		int iteration=-1 ) { 
  using namespace std;
  ::std::string dat_file = gen_filename( file, iteration, ".dat" );
  ::std::string gif_file = gen_filename( file, iteration, ".gif" );
  ::std::string title = gen_filename( file, iteration, "" );
  FILE* fp = popen("gnuplot","w");
  fprintf(fp,"set terminal gif\n");
  fprintf(fp,"set output '%s'\n",gif_file.c_str());
  fprintf(fp,"set border 4095 lt 1.000\n");
  fprintf(fp,"set view map\n");
  fprintf(fp,"set samples 50,50\n");
  fprintf(fp,"unset surface\n");
  fprintf(fp,"set style data pm3d\n");
  fprintf(fp,"set style function pm3d\n");
  fprintf(fp,"set ticslevel 0\n");
  fprintf(fp,"set pm3d at b\n");
  fprintf(fp,"set cbrange [-0.6:0.6]\n");
  fprintf(fp,"set title \"%s\"\n", title.c_str());
  fprintf(fp,"splot '%s' matrix\n", dat_file.c_str());
  fclose(fp);
}

void pulse( sidl::array<double> a,
	    int32_t center_x, 
	    int32_t center_y, 
	    double size ) { 
  int32_t min_i = a.lower(0);
  int32_t min_j = a.lower(1);
  int32_t max_i = a.upper(0);
  int32_t max_j = a.upper(1);
  for (int32_t i=min_i; i<=max_i; ++i ) { 
    for (int32_t j=min_j; j<=max_j; ++j ) { 
      a.set(i,j, 1.0 );
    }
  }  
  for (int32_t i=min_i; i<=max_i; ++i ) { 
    for (int32_t j=min_j; j<=max_j; ++j ) { 
      double delta_x_sq = (j-center_x) * (j-center_x);
      double delta_y_sq = (i-center_y) * (i-center_y);
      double h_times_w = (double) (max_j * max_i);
      a.set(i,j, exp( -(delta_x_sq+delta_y_sq))*4 );
    }
  }
}

int main() { 
  int height = 200;
  int width = 200;

  cxx::ScalarField sf = cxx::ScalarField::_create();
  { 
    sf.init( 0.0, 0.0, (double) width, (double) height, 1.0, 0.2); 
  
    // a barrier across the bottom
    ::wave2d::Shape r = cxx::ShapeFactory::createRectangle(0.0, 30.0, 200.0, 40.0 );
    sf.render( r, 0.0 );
  
    // cut two channels through it
    r = cxx::ShapeFactory::createRectangle(40.0, 30.0, 60.0, 40.0 ); 
    sf.render( r, 0.2 );
    r = r.translate(100.0, 0.0);
    sf.render( r, 0.2 );
  }

  write_array( sf.getData(), "field" );
  plot_data("field");

  // set up a pressure array with an initial gaussian pulse
  sidl::array<double> p = sidl::array<double>::create2dRow(width,height);
  { 
    pulse(p,15,100, 32*9/100000);
  }

  cxx::WavePropagator wp = cxx::WavePropagator::_create();
  wp.init( sf, p );

  sidl::array<double> p2 = wp.getPressure();  
  write_array( p2, "pressure", 0 );
  plot_data("pressure", 0);
 
  int32_t stepsize=1;
  for( int i=0; i<60; ++i ) { 
    wp.step(i*stepsize);    
    p2 = wp.getPressure();
    write_array( p2, "pressure", i*stepsize );
    plot_data("pressure", i*stepsize);

  }
}
