# This block sets variables about where Babel stuff
# is located.  One could use babel-config to query this info
include ../settings.make

all: 
	$(MAKE) MYSCRIPT=Life.py py

py: 
	PYTHONPATH=${BABEL_PYEXTENSION_LIBDIR}:../runPython		\
	LD_LIBRARY_PATH=${LD_LIBRARY_PATH}:${BABEL_LIBDIR} 		\
	sidl_DEBUG_DLOPEN=1						\
	SIDL_DLL_PATH="${BABEL_LIBDIR}/libsidl.scl;${TOPDIR}/libdemo.scl" $(PYTHON) $(MYSCRIPT)


