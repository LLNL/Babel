
include babel.make
SIDLFILES = hello.sidl
TESTDRIVERS = test1 test2

# Babel generates this babel.make file to define
# macros for all the files that are generated
ALLSRCS = $(HANDSRCS) $(HANDHDRS) $(IMPLSRCS) $(IMPLHDRS) $(SKELSRCS) \
	$(IORSRCS) $(IORHDRS) $(STUBSRCS) $(STUBHDRS) \
	$(x_IORHDRS) $(x_STUBHDRS) $(x_STUBSRCS)

HANDOBJS = $(HANDSRCS:.c=.lo) 
IMPLOBJS = $(IMPLSRCS:.c=.lo) 
SKELOBJS = $(SKELSRCS:.c=.lo) 
IOROBJS = $(IORSRCS:.c=.lo) 
STUBOBJS = $(STUBSRCS:.c=.lo) $(x_STUBSRCS:.c=.lo)
ALLOBJS = $(HANDOBJS) $(IMPLOBJS) $(SKELOBJS) $(IOROBJS) $(STUBOBJS) 

all :  $(TESTDRIVERS)

help:
	@echo "test1 runs hello world locally"
	@echo "test2 looks for a bos running on localhost:9999."
	@echo "Be sure to run an orb in a different terminal on port 9999 before"
	@echo "you run test2, or you'll get a connect error!"

libhello.la : $(ALLOBJS)
	$(BABEL_LIBTOOL) --mode=link $(CC) -o libhello.la  -no-undefined -rpath `pwd` -release 2.0 -no-install $(ALLOBJS) $(CLIBS) $(BABEL_LIBDIR)/libsidl.la

test1 : libhello.la test1.o
	$(BABEL_LIBTOOL) --mode=link $(CC) -o test1 test1.o  -no-install libhello.la $(BABEL_LIBDIR)/libsidl.la

test2 : libhello.la test2.o
	$(BABEL_LIBTOOL) --mode=link $(CC) -o test2 test2.o -no-install libhello.la $(BABEL_LIBDIR)/libsidl.la -L${BABEL_LIBDIR} -lsidlx -lsidl

clean:
	$(RM) babel-stamp $(ALLOBJS) *.so *~ *.lo *.o *.la
	$(RM) -rf .libs

new:    clean 
	$(RM)  $(SKELSRCS) $(IORSRCS) $(IORHDRS) $(STUBSRCS) $(STUBHDRS) $(TESTDRIVERS) $(x_STUBHDRS) $(x_STUBSRCS) $(x_IORHDRS)

# make doesn't handle tasks that generate lots of files
# this handy idiom creates a bogus file dated before babel
# outputs only iff babel ran to completion.
babel-stamp: $(SIDLFILES)
	@rm -f babel-temp
	@touch babel-temp
	$(BABEL) --server=C  $(SIDLFILES)
	sed -e s%@DIR@%`pwd`%g hello.scl.in > hello.scl
	@mv -f babel-temp $@

$(ALLSRCS) : babel-stamp
	@if test -f $@; then\
	  touch $@;\
	else \
	  $(MAKE) babel-stamp; \
	fi 

include ../settings.make
