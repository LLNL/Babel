# Babel generates this babel.make file to define
# macros for all the files that are generated
include babel.make
IMPLOBJS=${IMPLSRCS:.c=.lo}
SKELOBJS=${SKELSRCS:.c=.lo}
IOROBJS=${IORSRCS:.c=.lo}
STUBOBJS=${STUBSRCS:.c=.lo}
ALLOBJS=${IMPLOBJS} ${SKELOBJS} ${IOROBJS} ${STUBOBJS}

all: libcconway.la

# make doesn't handle tasks that generate lots of files
# this handy idiom creates touches a bogus file if babel
# executes without errors
babel-stamp: ../life.sidl
	@rm -f babel-temp
	@touch babel-temp
	babel --server=C ../life.sidl clife.sidl 
	@mv -f babel-temp $@

${SKELSRCS} ${SKELHDRS} ${IORSRCS} ${IORHDRS} ${STUBSRCS} ${STUBHDRS} : babel-stamp
	@if test -f $@; then \
	   touch $@; \
	else \
	  rm -f babel-stamp; \
	  $(MAKE) $(MAKEFLAGS) babel-stamp; \
	fi

libcconway.la: babel-stamp  ${ALLOBJS}
	babel-cc -g -o libcconway.la --installdir=`pwd`/.libs --release=1.0 ${ALLOBJS} 

clean:
	$(RM) babel-stamp ${ALLOBJS} libcconway.so *~ *.lo *.o *.la
	$(RM) -rf .libs

new:	clean
	$(RM) ${SKELSRCS} ${SKELHDRS} ${IORSRCS} ${IORHDRS} ${STUBSRCS} ${STUBHDRS}

.SUFFIXES:
.SUFFIXES: .c .lo .o

.c.lo:
	babel-cc -g -c ${CFLAGS} $<
