# Babel generates this babel.make file to define
# macros for all the files that are generated
include babel.make
IMPLOBJS=${IMPLSRCS:.cxx=.lo}
SKELOBJS=${SKELSRCS:.cxx=.lo}
IOROBJS=${IORSRCS:.c=.lo}
STUBOBJS=${STUBSRCS:.cxx=.lo}
ALLOBJS=${IMPLOBJS} ${SKELOBJS} ${IOROBJS} ${STUBOBJS}

all: libcxxconway.la

# make doesn't handle tasks that generate lots of files
# this handy idiom creates touches a bogus file if babel
# executes without errors
babel-stamp: ../life.sidl
	@rm -f babel-temp
	@touch babel-temp
	babel --server=C++ ../life.sidl cxxlife.sidl
	@mv -f babel-temp $@

${SKELSRCS} ${IORSRCS} ${STUBSRCS} ${SKELHDRS} ${IORHDRS} ${STUBHDRS} : babel-stamp
	@if test -f $@; then \
	  touch $@; \
	else \
	  rm -f babel-stamp; \
	  $(MAKE) $(MAKEFLAGS) babel-stamp; \
	fi

libcxxconway.la: babel-stamp  ${ALLOBJS}
	babel-cxx -g -o libcxxconway.la --installdir=`pwd`/.libs --release=1.0 ${ALLOBJS}

clean:
	$(RM) babel-stamp ${ALLOBJS} *.so *~ *.lo *.o *.la
	$(RM) -rf .libs

new:	clean
	$(RM) ${SKELSRCS} ${IORSRCS} ${STUBSRCS} ${SKELHDRS} ${IORHDRS} ${STUBHDRS} 


.SUFFIXES:
.SUFFIXES: .c .cxx .lo .o

.c.lo:
	babel-cc -g -c $<

.cxx.lo:
	babel-cxx -g -c $<
