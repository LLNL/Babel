include babel.make

# Babel generates this babel.make file to define
# macros for all the files that are generated
ARRAYMODULEOBJS=${ARRAYMODULESRCS:.F90=.lo}
IMPLMODULEOBJS=${IMPLMODULESRCS:.F90=.lo}
IMPLOBJS=${IMPLSRCS:.F90=.lo}
SKELOBJS=${SKELSRCS:.c=.lo}
IOROBJS=${IORSRCS:.c=.lo}
STUBOBJS=${STUBSRCS:.c=.lo}
STUBMODULEOBJS=${STUBMODULESRCS:.F90=.lo}
TYPEMODULEOBJS=${TYPEMODULESRCS:.F90=.lo}

ALLOBJS=${STUBOBJS} ${TYPEMODULEOBJS} \
			  ${ARRAYMODULEOBJS} \
			  ${STUBMODULEOBJS} ${SKELOBJS} \
			  ${IOROBJS} ${IMPLOBJS} ${IMPLMODULEOBJS}

ALLSRCS=${STUBSRCS} ${TYPEMODULESRCS} ${ARRAYMODULESRCS} \
	${STUBMODULESRCS} ${SKELSRCS} ${IORSRCS} ${IMPLSRCS} ${IMPLMODULESRCS}

SIDLFILES=../wave2d.sidl wave2d_f90.sidl

all: libwave2d.f90.la

# boilerplate dependencies to get things built
# in the correct order
${ARRAYMODULEOBJS} : ${TYPEMODULEOBJS}
${STUBMODULEOBJS}: ${TYPEMODULEOBJS} ${ARRAYMODULEOBJS}
${IMPLOBJS} : ${STUBMODULEOBJS} ${IMPLMODULEOBJS}


# make doesn't handle tasks that generate lots of files
# this handy idiom creates touches a bogus file if babel
# executes without errors
babel-stamp: $(SIDLFILES)
	@rm -f babel-temp
	@touch babel-temp
	babel --server=F90 $(SIDLFILES)
	@rm -f babel.make.depends babel.make.package
	@mv -f babel-temp $@

${ALLSRCS} : babel-stamp
	@if test -f $@; then \
	  touch $@; \
	else \
	  rm -f babel-stamp; \
	  ${MAKE} ${MAKEFLAGS} babel-stamp; \
	fi

libwave2d.f90.la: babel-stamp ${ALLOBJS}
	babel-f90  -o libwave2d.f90.la --no-quiet --installdir=`pwd` --release=1.0 ${ALLOBJS}

clean:
	${RM} ${ALLOBJS} *~ *.o *.lo *.la *.so *.tmp *.f90
	${RM} -rf .libs

new:    clean
	${RM} ${STUBSRCS} ${TYPEMODULESRCS} \
			  ${ARRAYMODULESRCS} \
			  ${STUBMODULESRCS} ${SKELSRCS} \
			  ${IORSRCS} *.mod *.h babel-stamp 

.SUFFIXES:
.SUFFIXES: .c .F90 .lo .o

.c.lo:
	babel-cc $(FCFLAGS) -c --with-f90 $<

.F90.lo:
	babel-f90 $(FCFLAGS) -c $<
