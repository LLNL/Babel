if SUPPORT_CXX
CXX_SUBDIR=Cxx
else
CXX_SUBDIR=
endif

if SUPPORT_FORTRAN77
F77_SUBDIR=F77
else
F77_SUBDIR=
endif

if SUPPORT_FORTRAN90
F90_SUBDIR=F90
else
F90_SUBDIR=
endif

if SUPPORT_FORTRAN03
F03_SUBDIR=F03
else
F03_SUBDIR=
endif

if SUPPORT_JAVA
JAVA_SUBDIR=Java
else
JAVA_SUBDIR=
endif


SUBDIRS = $(CXX_SUBDIR) $(F77_SUBDIR) $(F90_SUBDIR) $(JAVA_SUBDIR) $(F03_SUBDIR)

install-data-hook:
	@if test ! -h $(DESTDIR)$(includedir)/c; then 		\
	  (cd $(DESTDIR)$(includedir) ; ln -s . c ); 		\
	fi

uinstall-hook:
	rm -f $(DESTDIR)$(includedir)/c
