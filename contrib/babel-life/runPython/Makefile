
# Babel generates this babel.make file to define
# macros for all the files that are generated
include babel.make
include ../settings.make

IMPLOBJS=${IMPLSRCS:.cc=.lo}
SKELOBJS=${SKELSRCS:.cc=.lo}
IOROBJS=${IORSRCS:.c=.lo}
STUBOBJS=${STUBSRCS:.cc=.lo}
ALLOBJS=${IMPLOBJS} ${SKELOBJS} ${IOROBJS} ${STUBOBJS}
#ifeq ($(BABEL_WITH_F90),1)
ALLSIDL=../life.sidl ../libCxx/cxxlife.sidl ../libF90/f90life.sidl ../libC/clife.sidl
#else
#ALLSIDL=../life.sidl ../libCxx/cxxlife.sidl ../libC/clife.sidl
#endif

all: python-stamp 

# make doesn't handle tasks that generate lots of files
# this handy idiom creates touches a bogus file if babel
# executes without errors
babel-stamp: ../life.sidl
	${BABEL} --client=python $(ALLSIDL)  && \
	touch babel-stamp
	${MAKE}

python-stamp: babel-stamp 
	env CC=cc ${PYTHON} setup.py					\
	  --include-dirs=${BABEL_INCLDIR} 				\
	  --include-dirs=${BABEL_PYEXTENSION_INCLDIR}			\
	  --include-dirs=/home/kumfert/local/python/2.5.1/lib/python2.5/site-packages/numpy/core/include/numpy						\
	  --include-dirs=.						\
	  --library-dirs=${BABEL_LIBDIR}				\
	  --library-dirs=${BABEL_PYEXTENSION_LIBDIR}			\
	  build_ext --inplace --force && \
	touch python-stamp

clean:
	${RM} babel-stamp python-stamp ${ALLOBJS} libconway.so *~
	${RM} `find . -name '*.so' -o -name '*.o' -print`

new: clean
	${RM} ${IORHDRS} ${IORSRCS} ${PYMOD_HDRS}
	${RM} -r build conway f90 cxx c

py: python-stamp
	PYTHONPATH=${BABEL_PYEXTENSION_LIBDIR}				\
	LD_LIBRARY_PATH=${LD_LIBRARY_PATH}:${BABEL_LIBDIR} 		\
	SIDL_DEBUG_DLOPEN=1						\
	SIDL_DLL_PATH='${BABEL_LIBDIR}/libsidl.scl;${BABEL_LIBDIR}/libsidlx.scl;${TOPDIR}/libdemo.scl' $(PYTHON) $(MYSCRIPT)
