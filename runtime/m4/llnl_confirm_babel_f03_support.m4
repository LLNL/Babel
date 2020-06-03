dnl -*- sh -*-
dnl @synopsis LLNL_CONFIRM_BABEL_F03_SUPPORT
dnl
dnl  This checks for support of the iso_c_binding intrinsic
dnl  modules that is used in the Fortran 2003 bindings.
dnl
dnl  If Babel support for F03 is enabled:
dnl     the cpp macro FORTRAN03_DISABLED is undefined
dnl     the automake conditional SUPPORT_FORTRAN03 is true
dnl
dnl  If Babel support for F03 is disabled:
dnl     the cpp macro FORTRAN03_DISABLED is defined as true
dnl     the automake conditional SUPPORT_FORTRAN03 is false
dnl
dnl  @author ebner

AC_DEFUN([LLNL_CONFIRM_BABEL_F03_SUPPORT], [
  AC_REQUIRE([AC_PROG_FC])
  #begin LLNL_CONFIRM_BABEL_F03_SUPPORT
  if test \( -z "$FC" \) -a \( -n "$F03" \); then
	AC_MSG_WARN([FC environment variable is preferred over F03.  compensating])
	FC="$F03"
  fi
  if test \( -z "$FC" \) -a \( -n "$F03" \); then
	AC_MSG_WARN([FCFLAGS environment variable is preferred over F03FLAGS.  compensating])
	FCFLAGS="$F03FLAGS"
  fi

  AC_ARG_ENABLE([fortran03],
        AS_HELP_STRING(--enable-fortran03@<:@=FC@:>@,fortran 03 language bindings @<:@default=yes@:>@),
               [enable_fortran03="$enableval"],
               [enable_fortran03=yes])
  test -z "$enable_fortran03" && enable_fortran03=yes
  if test "$enable_fortran03" != no; then
    if test "$enable_fortran03" != yes; then
      FC=$enable_fortran03
      enable_fortran03=yes
    fi
  fi
  if test "X$enable_fortran03" != "Xno" -a "X$enable_fortran03" != "Xbroken" ; then
    AC_PROG_FC(,2000)
    AC_LANG_PUSH(Fortran)
    if test -n "$FC"; then
      if test "X$F03CPPSUFFIX" = "X" ; then
        LLNL_LIB_CHASM
	case "X$CHASM_FORTRAN_VENDOR" in
	"XIntel"|"XPGI"|"XPathScale")    AC_FC_SRCEXT([f90],[]) ;;
	*)				 AC_FC_SRCEXT([f03],[]) ;;
        esac
      fi
    else
      if echo $enable_auto_disable | egrep -i "yes|f03" 2>&1 >/dev/null ; then
        enable_fortran03=broken
      else
	AC_MSG_ERROR([Halting configure: Fortran 03 is required])
      fi
    fi
    AC_LANG_POP(Fortran) dnl gkk Do I need this?
  else
    echo "Fortran 2003 support disabled. The Fortran 90/95 compiler remains $FC."
  fi
  #end LLNL_CONFIRM_BABEL_F03_SUPPORT
])

AC_DEFUN([LLNL_CONFIRM_BABEL_F03_SUPPORT2],[
  AC_REQUIRE([LLNL_CONFIRM_BABEL_F03_SUPPORT])
    #begin LLNL_CONFIRM_BABEL_F03_SUPPORT2
    if test \( -n "$FC" \) ; then
	F03="$FC"
        # confirm that that F03 compiler can compile a trivial file issue146
	AC_MSG_CHECKING([if the Fortran 2003 compiler works])
        AC_LANG_PUSH(Fortran)dnl
        AC_COMPILE_IFELSE([AC_LANG_PROGRAM([],[       write (*,*) 'Hello world'])],
	  AC_MSG_RESULT([yes]),[
          AC_MSG_RESULT([no])
	  AC_MSG_WARN([The F03 compiler $FC fails to compile a trivial program (see config.log).])
      	  if echo $enable_auto_disable | egrep -i "yes|f03" 2>&1 >/dev/null ; then
	    AC_MSG_WARN([Disabling F03 Support])
            enable_fortran03=broken
     	  else
	    AC_MSG_ERROR([Halting configure: Fortran 03 is required])
	  fi
        ])
        AC_LANG_POP([])
        if test "X$enable_fortran03" = "Xyes"; then
          AC_CACHE_CHECK([if the Fortran 2003 compiler has support for the iso_c_binding intrinsic module],
          llnl_cv_has_bindc,
          [AC_LANG_PUSH(Fortran)dnl
          AC_COMPILE_IFELSE([
            program main
                use, intrinsic :: iso_c_binding
                type, bind(c) :: ftest
                  real(c_double)            :: d_dbl
                  real(c_float)             :: d_flt
                  complex(c_float_complex)  :: d_fcmplx
       	          complex(c_double_complex) :: d_dcmplx
                  integer(c_int32_t)        :: d_int
                  integer(c_int64_t)        :: d_long
                  type(c_ptr)               :: d_opaque
                end type ftest
            end
            ],
            [llnl_cv_has_bindc="yes"],
            [llnl_cv_has_bindc="no"])
          AC_LANG_POP(Fortran)dnl
          ])

	  # test for broken LDFLAGS (happens on some IBM XL compilers)
          AC_CACHE_CHECK([if the Fortran 2003 compiler can link hello world],
          llnl_cv_f03_link,
          [AC_LANG_PUSH(Fortran)dnl
          AC_LINK_IFELSE([
		  program hello
 		    print *, "Hello World!"
		  end program hello
	      ],
	      [llnl_cv_f03_link="yes"],
	      [llnl_cv_f03_link="no"
               AC_MSG_ERROR([Halting configure: Fortran linker flags are broken])])
          AC_LANG_POP(Fortran)dnl
          ])

          #this tests for an ifort bug up to 12.1.015
          # see http://software.intel.com/en-us/forums/showthread.php?t=78482
          # see http://software.intel.com/en-us/forums/showthread.php?t=78591

          AC_CACHE_CHECK([if the Fortran 2003 compiler implements complex numbers correctly],
          llnl_cv_f03_complex,
          [AC_LANG_PUSH(Fortran)dnl
          AC_RUN_IFELSE([
            program complex 
              complex :: x 
              x = cmplx(0.0,1.0) 
              call func(x)               
              contains 
              subroutine func(x) 
                complex, value :: x 
                if (abs(imag(x) - 1.0) .ge. 0.1) then
                   call exit(1)
                else
                   call exit(0)
                endif
              end subroutine func             
            end program complex
            ],
            [llnl_cv_f03_complex="yes"],
            [llnl_cv_f03_complex="no"],
	    [llnl_cv_f03_complex="yes"
	     AC_MSG_WARN([skipped because we are cross-compiling])]
	    )
          AC_LANG_POP(Fortran)dnl
          ])


          #this tests for a gfortran bug up to 4.5.1
          #see http://gcc.gnu.org/bugzilla/show_bug.cgi?id=45674
          AC_CACHE_CHECK([if the Fortran 2003 compiler supports type extensions],
          llnl_cv_f03_type_ext,
          [AC_LANG_PUSH(Fortran)dnl
          AC_LINK_IFELSE([
            module fails_mod
              implicit none
              type :: a_t
                 integer :: a
              end type
              type, extends(a_t) :: b_t
                 integer :: b
              end type
            contains
              subroutine foo(a)
                class(a_t) :: a
              end subroutine foo
            end module fails_mod
            module fails_test
              implicit none
            contains
              subroutine bar
                use fails_mod
                type(b_t) :: b
                call foo(b)
              end subroutine bar
            end module fails_test
            end
            ],
            [llnl_cv_f03_type_ext="yes"],
            [llnl_cv_f03_type_ext="no"])
	  rm -f fails_test* fails_mod*
          AC_LANG_POP(Fortran)dnl
          ])


	  #this tests for an IBM fortran bug
          AC_CACHE_CHECK([if the Fortran 2003 compiler has a broken namespace implementation],
          llnl_cv_f03_name_clash,
          [AC_LANG_PUSH(Fortran)dnl
          AC_COMPILE_IFELSE([
              module clash
                use, intrinsic :: iso_c_binding
              contains
                subroutine foo(a)
                  interface
                     type(c_ptr) function fmalloc(size) &
                          bind(c, name="malloc")
                       use iso_c_binding
                       integer(c_size_t), value :: size
                     end function fmalloc
                    end interface
                  end subroutine foo
                  
                  subroutine bar(a)
                    interface
                       type(c_ptr) function fmalloc(size) &
                            bind(c, name="malloc")
                         use, intrinsic :: iso_c_binding
                         integer(c_size_t), value :: size
                       end function fmalloc
                    end interface
                end subroutine bar
              end module clash
            ],
            [llnl_cv_f03_name_clash="no"],
            [llnl_cv_f03_name_clash="yes"])
	  rm -f clash*
          AC_LANG_POP(Fortran)dnl
          ])

          #this tests for an ifort bug
	  #see http://software.intel.com/en-us/forums/showthread.php?t=78968
	  #
          # For this test we need to link a F2003 module with a C program
          AC_CACHE_CHECK([if the Fortran 2003 compiler implements function pointers correctly],
          llnl_cv_f03_fnptr,
          [AC_LANG_PUSH(C)dnl

	   AC_COMPILE_IFELSE([AC_LANG_SOURCE([
              #include <sys/types.h>
              #include <stdlib.h>
              void funcptr(void(*fptr) (int32_t));
              void test2(int32_t i) {
	       	if (i == 42) exit(0);
	       	else exit(1);
              }
	      
	      int main(int argc, char** argv) {
		  funcptr(test2); 
		  return 2;
	      }
	    ])],
	    [cp conftest.$OBJEXT conftestc.$OBJEXT], dnl save the object
	    [llnl_cv_f03_fnptr="no"])
	    AC_LANG_POP(C)dnl
	    AC_LANG_PUSH(Fortran)dnl
	    OLD_LIBS=$LIBS
	    LIBS=conftestc.$OBJEXT
	    OLD_LD=$LD
	    LD=$FC
	    FC_save=$FC
	    if test "X$CHASM_FORTRAN_VENDOR" == "XIntel"; then
		FC="$FC -nofor-main"
	    fi
	    AC_RUN_IFELSE([AC_LANG_SOURCE([
            subroutine funcptr(c_test2) bind(c)
              use, intrinsic :: iso_c_binding
              interface
                 subroutine test2(intval) bind(c)
                   use iso_c_binding
                   integer(c_int32_t), value :: intval
                 end subroutine test2
              end interface

              type(c_funptr), value :: c_test2
              procedure(test2), pointer :: f_test2
              integer(c_int32_t) :: localint

              call c_f_procpointer(c_test2, f_test2)
              localint = 42

              call f_test2(localint)
              return
            end subroutine funcptr
	    ])],
	    [llnl_cv_f03_fnptr="yes"],
	    [llnl_cv_f03_fnptr="no"],
	    [llnl_cv_f03_fnptr="yes"
	     AC_MSG_WARN([skipped because we are cross-compiling])]
	    )
	    rm -f conftestc.$OBJEXT
	    FC=$FC_save
	    LD=$OLD_LD
	    LIBS=$OLD_LIBS
	  AC_LANG_POP(Fortran)dnl
          ])
      fi
    else
      if echo $enable_auto_disable | egrep -i "yes|f03" 2>&1 > /dev/null ; then
        AC_MSG_WARN([Disabling F03 Support])
        if test \( -n "$FC" \); then
          enable_fortran03="no_chasm"
        else
          enable_fortran03="broken"
        fi
      else
        AC_MSG_ERROR([Halting configure: Fortran 03 is required])
      fi
  fi
    #end LLNL_CONFIRM_BABEL_F03_SUPPORT2
])

AC_DEFUN([LLNL_CONFIRM_BABEL_F03_SUPPORT3],[
  #begin LLNL_CONFIRM_BABEL_F03_SUPPORT3
  if test "X$llnl_cv_has_bindc"    != "Xyes" \
       -o "X$llnl_cv_f03_type_ext" != "Xyes" \
       -o "X$llnl_cv_f03_complex"  != "Xyes" \
       -o "X$llnl_cv_f03_fnptr"    != "Xyes" ; then
    enable_fortran03="broken"
  fi
  if test "X$enable_fortran03" = "Xno"; then
    msgs="$msgs
	  Fortran03 disabled by request.";
  elif test "X$enable_fortran03" = "Xyes"; then
    msgs="$msgs
	  Fortran03 enabled.";
  elif test "X$enable_fortran03" = "Xno_chasm"; then
    msgs="$msgs
 	  Fortran03 disabled against user request: CHASMLITE config failed.";
  elif test "X$enable_fortran03" = "Xbroken" -a "X$llnl_cv_f03_type_ext" != "Xyes"; then
    msgs="$msgs
          ** Fortran03 disabled against user request:
             The compiler ($FC) does not fully support Fortran 2003 type extensions.";
  elif test "X$enable_fortran03" = "Xbroken" -a "X$llnl_cv_f03_complex" != "Xyes"; then
    msgs="$msgs
          ** Fortran03 disabled against user request:
             The compiler ($FC) does not implement complex numbers correctly.";
  elif test "X$enable_fortran03" = "Xbroken" -a "X$llnl_cv_f03_complex" != "Xyes"; then
    msgs="$msgs
          ** Fortran03 disabled against user request:
             The compiler ($FC) does not implement function pointers correctly."
  else
    msgs="$msgs
         ** Fortran03 disabled against user request:
            No viable compiler found.";
  fi
  if test "X$enable_fortran03" != "Xyes"; then
    AC_DEFINE(FORTRAN03_DISABLED, 1, [If defined, F03 support was disabled at configure time])
    echo "Fortran 2003 support disabled. The Fortran 90/95 compiler remains $FC."
  fi
  AM_CONDITIONAL(SUPPORT_FORTRAN03, test "X$enable_fortran03" = "Xyes")
  case "X$CHASM_FORTRAN_VENDOR" in
    "XIntel"|"XPGI"|"XPathScale")   F03CPPSUFFIX=".f90" ;;
    *)                              F03CPPSUFFIX=".f03" ;;
  esac
  case "$target_os" in
      # Darwin may have a case-preserving (insensitive) HFS+ file system
      # so we need to use a different name
      "darwin"*)  F03CPPSUFFIX=".f95" ;;
      *) : ;;
  esac
  AC_SUBST(F03CPPSUFFIX)
  AM_CONDITIONAL(F03_NAME_CLASH, test "X$llnl_cv_f03_name_clash" = "Xyes")
  AS_IF([test "X$llnl_cv_f03_name_clash" = "Xyes"], [CPPFLAGS="$CPPFLGS -DFORTRAN_BINDC_PRIVATE"])
  LLNL_WHICH_PROG(WHICH_FC)
  #end LLNL_CONFIRM_BABEL_F03_SUPPORT3
])
