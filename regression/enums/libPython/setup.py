#! /usr/bin/env python
# Build file for Python modules
import sys
from re import compile
from distutils.core import setup, Extension

inc_re = compile('^--include-dirs=(.*)$')
shortinc_re = compile('^-I(.*)$')
lib_re = compile('^--library-dirs=(.*)$')
shortlib_re = compile('^-L(.*)$')
exlib_re = compile('^--extra-library=(.*)$')
shortexlib_re = compile('^-l(.*)$')
old_argv = sys.argv
sys.argv = []
inc_dirs = ['.']
lib_dirs = []
libs = ['sidl']
if sys.platform[0:3] == "aix":
  extra_args=[ "-qlanglvl=extc99" ]
else:
  extra_args=[ ]

for i in old_argv:
  m = inc_re.match(i) or shortinc_re.match(i)
  if (m):
    if (len(m.group(1))): inc_dirs.extend(m.group(1).split(':'))
  else:
    m = lib_re.match(i) or shortlib_re.match(i)
    if (m):
      if (len(m.group(1))): lib_dirs.extend(m.group(1).split(':'))
    else:
      m = exlib_re.match(i) or shortexlib_re.match(i)
      if (m):
        if (len(m.group(1))): libs.extend(m.group(1).split(':'))
      else:
        sys.argv.append(i)
setup(name='llnl_babel_enums',
  include_dirs=inc_dirs,
  headers = [
    'enums_cartest_Module.h',
    'enums_colorwheel_Module.h',
    'enums_numbertest_Module.h'
  ],
  packages = [
    'enums'
  ],
  ext_modules = [
    Extension('enums.cartest',
      ["enums/enums_cartest_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs,extra_compile_args=extra_args)
      ,
    Extension('enums.colorwheel',
      ["enums/enums_colorwheel_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs,extra_compile_args=extra_args)
      ,
    Extension('enums.numbertest',
      ["enums/enums_numbertest_Module.c"
      ],
      library_dirs=lib_dirs,
      libraries=libs,extra_compile_args=extra_args)

  ])
