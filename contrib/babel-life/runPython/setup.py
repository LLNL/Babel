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
    'c_BoundsException_Module.h',
    'c_Ruleset_Module.h',
    'conway_BoundsException_Module.h',
    'conway_Environment_Module.h',
    'conway_Ruleset_Module.h',
    'conway_TimeStepper_Module.h',
    'cxx_BoundsException_Module.h',
    'cxx_Environment_Module.h',
    'cxx_Ruleset_Module.h',
    'cxx_TimeStepper_Module.h',
    'f90_BoundsException_Module.h',
    'f90_Environment_Module.h',
    'f90_Ruleset_Module.h',
    'f90_TimeStepper_Module.h'
  ],
  packages = [
    'c',
    'conway',
    'cxx',
    'f90'
  ],
  ext_modules = [
    Extension('c.BoundsException',
      ["c/c_BoundsException_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs),
    Extension('c.Ruleset',
      ["c/c_Ruleset_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs),
    Extension('conway.BoundsException',
      ["conway/conway_BoundsException_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs),
    Extension('conway.Environment',
      ["conway/conway_Environment_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs),
    Extension('conway.Ruleset',
      ["conway/conway_Ruleset_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs),
    Extension('conway.TimeStepper',
      ["conway/conway_TimeStepper_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs),
    Extension('cxx.BoundsException',
      ["cxx/cxx_BoundsException_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs),
    Extension('cxx.Environment',
      ["cxx/cxx_Environment_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs),
    Extension('cxx.Ruleset',
      ["cxx/cxx_Ruleset_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs),
    Extension('cxx.TimeStepper',
      ["cxx/cxx_TimeStepper_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs),
    Extension('f90.BoundsException',
      ["f90/f90_BoundsException_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs),
    Extension('f90.Environment',
      ["f90/f90_Environment_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs),
    Extension('f90.Ruleset',
      ["f90/f90_Ruleset_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs),
    Extension('f90.TimeStepper',
      ["f90/f90_TimeStepper_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs)
  ])
