
# Babel generates this babel.make file to define
# macros for all the files that are generated
include babel.make
include ../settings.make 

ALLSIDL=../wave2d.sidl ../libCxx/wave2d_cxx.sidl ../libF90/wave2d_f90.sidl

all: python-stamp 

# make doesn't handle tasks that generate lots of files
# this handy idiom creates touches a bogus file if babel
# executes without errors
babel-stamp: $(ALLSIDL)
	${BABEL} --client=python $(ALLSIDL)  && \
	touch babel-stamp
	$(RM) babel.make.depends babel.make.package
	${MAKE}

python-stamp: babel-stamp 
	env CC=cc ${PYTHON} setup.py				        \
	  --include-dirs=${BABEL_INCLDIR} 				\
	  --include-dirs=${BABEL_PYEXTENSION_INCLDIR}			\
	`echo $(PYTHONINC) | sed -e 's/-I/--include-dirs=/g'`           \
	  --include-dirs=.						\
	  --library-dirs=${BABEL_LIBDIR}				\
	  --library-dirs=${BABEL_PYEXTENSION_LIBDIR}			\
	  build_ext --inplace --force && \
	touch python-stamp

clean:
	${RM} babel-stamp python-stamp ${ALLOBJS} *~
	${RM} `find . -name '*.so' -o -name '*.o' -print`

veryclean: clean
	${RM} ${IORHDRS} ${IORSRCS} ${PYMOD_HDRS} *.pyc 
	${RM} -r build f90 cxx c wave2d ex 

new: veryclean
	${RM} babelenv.py


py: python-stamp
	PYTHONPATH=${BABEL_PYEXTENSION_LIBDIR}				\
	LD_LIBRARY_PATH=${LD_LIBRARY_PATH}:${BABEL_LIBDIR} 		\
	SIDL_DEBUG_DLOPEN=1						\
	SIDL_DLL_PATH='${BABEL_LIBDIR}/libsidl.scl;${BABEL_LIBDIR}/libsidlx.scl;${TOPDIR}/wave2d.scl' $(PYTHON) $(MYSCRIPT)
