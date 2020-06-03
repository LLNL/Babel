# Babel generates this babel.make file to define
# macros for all the files that are generated
include babel.make
IMPLOBJS=${IMPLSRCS:.cxx=.lo}
SKELOBJS=${SKELSRCS:.cxx=.lo}
IOROBJS=${IORSRCS:.c=.lo}
STUBOBJS=${STUBSRCS:.cxx=.lo}
ALLOBJS=${IMPLOBJS} ${SKELOBJS} ${IOROBJS} ${STUBOBJS}

SIDLFILES= ../wave2d.sidl wave2d_cxx.sidl wave2d_cxx_internal.sidl

all: libwave2d.cxx.la 

# make doesn't handle tasks that generate lots of files
# this handy idiom creates touches a bogus file if babel
# executes without errors
babel-stamp: $(SIDLFILES)
	@rm -f babel-temp
	@touch babel-temp
	babel --server=C++ $(SIDLFILES)
	@rm -f babel.make.depends babel.make.package
	@mv -f babel-temp $@

${SKELSRCS} ${IORSRCS} ${STUBSRCS} ${SKELHDRS} ${IORHDRS} ${STUBHDRS} : babel-stamp
	@if test -f $@; then \
	  touch $@; \
	else \
	  rm -f babel-stamp; \
	  $(MAKE) $(MAKEFLAGS) babel-stamp; \
	fi

libwave2d.cxx.la: babel-stamp  ${ALLOBJS}
	babel-cxx -g -o libwave2d.cxx.la --installdir=`pwd`/.libs --release=1.0 ${ALLOBJS}

clean:
	$(RM) babel-stamp ${ALLOBJS} *.so *~ *.lo *.o *.la
	$(RM) -rf .libs

new:	clean
	$(RM) ${SKELSRCS} ${IORSRCS} ${STUBSRCS} ${SKELHDRS} ${IORHDRS} ${STUBHDRS} 


.SUFFIXES:
.SUFFIXES: .c .cxx .lo .o

.c.lo:
	babel-cc $(CFLAGS) -c $<

.cxx.lo:
	babel-cxx $(CXXFLAGS) -c $<
