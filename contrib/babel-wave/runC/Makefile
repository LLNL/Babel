SIDLFILES=../wave2d.sidl ../libCxx/wave2d_cxx.sidl ../libF90/wave2d_f90.sidl
ALLLIBS=../libCxx/libwave.cxx.la
OPT=-g
include babel.make
STUBOBJS=$(STUBSRCS:.c=.lo)
ALLOBJS=$(STUBOBJS)
ALLSOBJS=$(STUBSRCS:.c=.o)
ALLSTATICS=$(ALLOBJS)
ALLSRCS=$(STUBSRCS)

all : runC2Cxx runC2F90

babel-stamp: $(SIDLFILES)
	@rm -f babel-temp
	@touch babel-temp
	babel --client=c $(SIDLFILES)
	@rm -f babel.make.package babel.make.depends
	@mv -f babel-temp $@

$(ALLSRCS) : babel-stamp
	@if test -f $@; then \
	  touch $@; \
	else \
	  rm -f babel-stamp; \
	  $(MAKE) $(MAKEFLAGS) babel-stamp; \
	fi

runC2Cxx : babel-stamp libstubs.la mainC2Cxx.lo ../libCxx/libwave2d.cxx.la
	babel-cxx $(OPT) -o runC2Cxx -static mainC2Cxx.lo libstubs.la ../libCxx/libwave2d.cxx.la

runC2F90: babel-stamp  libstubs.la mainC2F90.lo ../libF90/libwave2d.f90.la 
	babel-cc $(OPT) -o runC2F90 --with-f90 -static mainC2F90.lo libstubs.la ../libF90/libwave2d.f90.la

libstubs.la : $(ALLOBJS)
	babel-cc $(OPT) -o libstubs.la $(ALLOBJS)

.SUFFIXES: .c .lo .o
.c.lo:
	babel-cc $(OPT) -c --with-f90 ${CFLAGS} $< 

clean :
	rm -f $(ALLOBJS) $(ALLSRCS) $(STUBHDRS) $(IORHDRS) *.o *.lo *~ \
	  babel-stamp *.la runC2Cxx runC2F90
