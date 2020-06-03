if WITH_SIDLX

LIBSIDLX     = $(top_builddir)/runtime/sidlx/libsidlx.la
BABELDIR    = $(top_builddir)/bin
INCLUDEDIR  = $(top_builddir)/runtime/sidl
INCLUDEDIRX  = $(top_builddir)/runtime/sidlx
AM_CPPFLAGS = -I. -I$(INCLUDEDIR) -I$(INCLUDEDIRX)

check_PROGRAMS = bos
bos_SOURCES = bos.c
bos_LDFLAGS = -static-libtool-libs -export-dynamic
bos_LDADD = $(LIBSIDLX) -lpthread

endif
