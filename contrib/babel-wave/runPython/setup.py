#! /usr/bin/env python
# Build file for Python modules
import sys
from re import compile
from distutils.core import setup, Extension

inc_re = compile('^--include-dirs=(.*)$')
lib_re = compile('^--library-dirs=(.*)$')
exlib_re = compile('^--extra-library=(.*)$')
old_argv = sys.argv
sys.argv = []
inc_dirs = ['.']
lib_dirs = []
libs = ['sidl']

for i in old_argv:
  m = inc_re.match(i)
  if (m):
    if (len(m.group(1))): inc_dirs.append(m.group(1))
  else:
    m = lib_re.match(i)
    if (m):
      if (len(m.group(1))): lib_dirs.append(m.group(1))
    else:
      m = exlib_re.match(i)
      if (m):
        if (len(m.group(1))): libs.append(m.group(1))
      else:
        sys.argv.append(i)
setup(name='babel',
  include_dirs=inc_dirs,
  headers = [
    'cxx_ScalarField_Module.h',
    'cxx_ShapeFactory_Module.h',
    'cxx_WavePropagator_Module.h',
    'f90_ScalarField_Module.h',
    'f90_WavePropagator_Module.h',
    'wave2d_ScalarField_Module.h',
    'wave2d_Shape_Module.h',
    'wave2d_WavePropagator_Module.h'
  ],
  packages = [
    'cxx',
    'f90',
    'wave2d'
  ],
  ext_modules = [
    Extension('cxx.ScalarField',
      ["cxx/cxx_ScalarField_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs),
    Extension('cxx.ShapeFactory',
      ["cxx/cxx_ShapeFactory_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs),
    Extension('cxx.WavePropagator',
      ["cxx/cxx_WavePropagator_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs),
    Extension('f90.ScalarField',
      ["f90/f90_ScalarField_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs),
    Extension('f90.WavePropagator',
      ["f90/f90_WavePropagator_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs),
    Extension('wave2d.ScalarField',
      ["wave2d/wave2d_ScalarField_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs),
    Extension('wave2d.Shape',
      ["wave2d/wave2d_Shape_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs),
    Extension('wave2d.WavePropagator',
      ["wave2d/wave2d_WavePropagator_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs)
  ])
