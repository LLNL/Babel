
# Babel generates this babel.make file to define
# macros for all the files that are generated
include babel.make
include ../settings.make

LAUNCHOBJS=${LAUNCHSRCS:.c=.lo}
IMPLOBJS=${IMPLSRCS:.c=.lo}
SKELOBJS=${SKELSRCS:.c=.lo}
IOROBJS=${IORSRCS:.c=.lo}
STUBOBJS=${STUBSRCS:.c=.lo}
LAUNCHLIBOBJS=${IOROBJS} ${LAUNCHOBJS}
IMPLLIBOBJS=${SKELOBJS}
# ALLOBJS=${IMPLOBJS} ${SKELOBJS} ${IOROBJS} ${STUBOBJS}
ALLSIDL=../life.sidl pythonlife.sidl
CPPFLAGS=-I$(BABEL_PYEXTENSION_INCLDIR) -I`python -c "import sys; print sys.prefix + '/include/python' + str(sys.version_info[0]) + '.' + str(sys.version_info[1])"`

all: libpythonconway.la libpythonconwaypy.la python-stamp

# make doesn't handle tasks that generate lots of files
# this handy idiom creates touches a bogus file if babel
# executes without errors
babel-stamp: ../life.sidl pythonlife.sidl
	${BABEL} --server=python $(ALLSIDL)  && \
	touch babel-stamp
	${MAKE}

python-stamp: babel-stamp 
	env CC=cc ${PYTHON} setup.py					\
	  --include-dirs=${BABEL_INCLDIR} 				\
	  --include-dirs=${BABEL_PYEXTENSION_INCLDIR}			\
	  --include-dirs=.						\
	  --library-dirs=${BABEL_LIBDIR}				\
	  --library-dirs=${BABEL_PYEXTENSION_LIBDIR}			\
	  build_ext --inplace --force && \
	touch python-stamp

libpythonconway.la: babel-stamp $(LAUNCHLIBOBJS)
	${BABEL_LIBTOOL} --mode=link $(CC) -module -no-undefined -o libpythonconway.la -rpath `pwd`/.libs -release 2.0 $(LAUNCHLIBOBJS) -L$(PYTHON_SHARED_LIBRARY_DIR) -lpython$(PYTHON_VERSION) -L$(BABEL_LIBDIR) -rpath $(BABEL_LIBDIR) -lsidl

libpythonconwaypy.la: babel-stamp $(IMPLLIBOBJS)
	${BABEL_LIBTOOL} --mode=link $(CC) -module -no-undefined -o libpythonconwaypy.la -rpath `pwd`/.libs -release 2.0 $(IMPLLIBOBJS) -L$(PYTHON_SHARED_LIBRARY_DIR) -lpython$(PYTHON_VERSION) -L$(BABEL_LIBDIR) -rpath $(BABEL_LIBDIR) -lsidl

clean:
	${RM} babel-stamp python-stamp ${ALLOBJS} libpythonconway.so libpythonconwaypy.so *~
	${RM} `find . \( -name '*.so' -o -name '*.o' -o -name '*.lo' \) -print`

new: clean
	${RM} ${IORHDRS} ${IORSRCS} ${PYMOD_HDRS}
	${RM} -r build conway f90 cxx c
