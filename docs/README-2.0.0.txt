Babel 2.0.0 README                             <!-- -*- markdown -*- -->
==================

*** Released 6 January 2012 ***

Contents
--------

  Introduction
  Contact Information
  Overview for the Impatient
  Running Tests
  Supported Platforms
  Directories
  Features
  Outstanding Issues
  Future Work
  Further Information
  Project Team


Introduction
------------

  Babel is a language interoperability tool intended for use by
the high-performance scientific computing community.  Developed
by the Components project ( http://www.llnl.gov/CASC/components )
at Lawrence Livermore National Laboratory, Babel supports the
Scientific Interface Definition Language (SIDL) for the language-
independent declaration of interfaces associated with scientific
software packages.

  The Babel tool, applied to a SIDL file, results in the automatic
generation of the associated skeleton and stub source files.  The
Babel user then need only add the necessary code to the _Impl source
files to complete the provision of a language-independent interface to
the package described by the SIDL file.  The languages currently
supported by Babel are C, C++, FORTRAN 77, Fortran 90/95, Fortran
2003/2008, Java and Python.

  The remainder of this document provides basic information about
Babel including features, outstanding issues, and future work.


Contact Information
-------------------

  If you have any questions or concerns with the installation
process or usage of Babel, feel free to contact the project team
at components@llnl.gov .  To report bugs or suggest feature
enhancements, please submit a report in the bug database at
https://www.cca-forum.org/bugs/babel/ or send email to
babel-bugs@cca-forum.org .


Overview for the Impatient
--------------------------

  Once you have completed the installation process (see the INSTALL
file in this directory), you are ready to proceed with using Babel.
A complete tutorial that steps you through the process of building
the ubiquitious "Hello World!" example for a single client and server
is available at ./doc/manuals/users_guide/html/users_guide.html

  Briefly, now that you have the Babel compiler available, you'll
need to have a sidl file defined before running the compiler.
Below is a skeleton of the command line required to build the stubs,
skeletons, Makefile fragment, and GNUmakefile.

    $ ./<babel>/bin/babel --makefile -s<lang> <name>.sidl

      where <lang> is the desired language,
            <name> is the name of the SIDL file, and
            <babel> is the installation's top directory

  Now you'll need to add the appropriate code within the splicer
pairs in the _Impl files.  Once you've finished filling in the
implementation details, you can simply compile and link the
appropriate libraries.  Again, refer to the tutorial for more
information.


Running Tests
-------------

  If you are interested in running the regression tests that
are packaged in this distribution you will need to build them
since they are excluded from the basic installation process.
As in the example in the INSTALL file, given that the Babel
distribution is in $HOME/babel, the process for building and
running the regression tests is as follows:

    $ cd $HOME/babel
    $ make check

Various diagnostics and results (e.g., PASS, XFAIL) will be
displayed to indicate the number and status of each set of
tests.  At this time, there are three sets of tests each with
three instances (9 total) that are expected to fail (i.e., XFAIL)
These are for arrays in Python, arrays in Java, and exceptions in
Java.


Supported Platforms
-------------------

  The Babel compiler has been successfully run on the platform/
compiler pairs listed below.

    Linux             / Intel Compiler Suite 12.1 (icc & ifc)
    Linux             / GCC 4.6.2
    Linux             / PGI 10.9
    Linux             / PathScale 3.2.99
    Linux             / IBM XL Fortran Advanced Edition for Blue Gene/L, V11.1
    Linux             / IBM XL Fortran Advanced Edition for Blue Gene/P, V11.1
	
    Linux             / GCC & Absoft F90
    Linux             / GCC & Lahey F90
 
    AIX               / GCC
    AIX               / IBM compiler suite


    Sparc-Solaris2.9  / gcc 3.1 or higher (use SHELL=/bin/ksh )
    Sparc-Solaris2.9  / g77 3.1 or higher (use SHELL=/bin/ksh )
    Sparc-Solaris2.9  / f77 (Sun WorkShop 5.0)  (use SHELL=/bin/ksh )
    Sparc-Solaris2.9  / f95 (SUNSWspro) (use SHELL=/bin/ksh )

    The Fortran 2003/2008 binding requires GCC >= 4.6.1,
	IBM XL Fortran >= 11.1, or 
    Intel Compiler Suite >= 12.1.032 on any platform.


Directories
-----------

  The following represents the source directory tree
associated with this release, relative to the top
level of the installation directory, and a brief
description of the contents of each:

    bin/                    # The Babel compiler
    compiler/               # The compiler source
    doc/                    # Supplemental documentation
    doc/manuals/users_guide # PS, PDF, and HTML documentation
    examples/               # Simple examples:
    examples/hello/         #   Hello World examples
    examples/cca            #   CCA-0.5 framework implementation
    lib/                    # Supporting libraries
    regression/             # Regression tests:
    regression/args/        #   Data Type+Passing Mode
    regression/arrays/      #   Arrays
    regression/contracts/   #   Contracts (i.e., interface assertions)
    regression/enums/       #   Enumerations
    regression/exceptions/  #   Exceptions
    regression/hooks/       #   Pre- and post-method hooks
    regression/inherit/     #   Inheritance
    regression/objarg/      #   Passing objects as arguments
    regression/ordering/    #   Array ordering test
    regression/overload/    #   Function name overloading
    regression/sort/        #   Abstract class and interface args
    regression/strings/     #   Strings
    regression/struct/      #   Structs
    runtime/                # Runtime support files
                              (e.g., sidl.sidl)
    share/                  # Shared config files


Features
--------

  Key features, or capabilities, of the Babel toolkit
are described in this section.

- SIDL Data Types
  SIDL defines the following data types:

    arrays      double        interface
    bool        enum          long
    char        fcomplex      opaque
    class       float         string
    dcomplex    int

  All data types are currently supported in C, C++, F77, F90, F03,
  Python, and Java with the additional exception of long in Python.
  It is important, however, to note that the binding for enum in F77
  and F90 is currently INTEGER due to Babel's dependence upon C for
  its internal object representation (IOR).

  Refer to ./regression/args for clients and/or servers in C, C++,
  F77, F90, F03, Python, and Java that exploit most data types. Use of
  arrays can be found in ./regression/arrays (including in Python),
  enums in ./regression/enums, and strings in ./regression/strings.  A
  crash course in SIDL arrays can be found in the users guide.

- SIDL Data Passing Modes
  The following data passing modes are defined:

    in      inout      out      return

  Note that return values in F77 and F90 are translated to out
  arguments.  Refer to ./regression/args for clients and
  servers in C, C++, F77, F90 and F03 that exploit these modes.

- SIDL Exceptions
  SIDL provides support for exceptions.  Clients are,
  however, required to check the return values.  A set
  of macros are defined in ./runtime/sidl/SIDL_Exception.h
  to facilitate throwing and catching exceptions in C.
  Refer to ./regression/exceptions for C, C++, F77, F90, F03, and
  Python servers and C, C++, F77, F90, F03, Java, and Python clients.

- SIDL Inheritance
  SIDL supports Java-like inheritance.  Refer to
  ./regression/inherit for servers in C, C++, F77, F90 and F03
  and clients in C, C++, F77, F90, F03, Java, and Python that
  exploit these features.

- SIDL Method Overloading
  SIDL supports method overloading on the client and server
  side for object-oriented languages using an exact match of
  the arguments.  Languages that support method overloading
  make use of the method name from the SIDL file; whereas,
  those that do not support overloading rely on the optional
  method name extension to build unique method names.  Refer
  to ./regression/overload for examples of the specification
  and use of overloaded methods in C, C++, F77, F90 and F03
  servers and C, C++, F77, F90, F03, Java, and Python clients 
  that exploit these features.

- Makefile Generation
  Babel generates a lot of code.  To assist the developer
  in managing the code, Babel will generate fragments of
  makefiles.  These files are called "babel.make" and
  define standard macros that list all the relevant source
  code generated in that directory.

- Code Splicing
  Although Babel generates a lot of code, some code (notably)
  "impl" files, require hand editing to produce useful
  libraries.  Babel preserves your previous edits in certain
  files.  This is done with some special CodeSplicer directives
  embedded in comments in the generated code.  All code
  nested between matching CodeSplicer directives will be
  preserved by the Babel compiler.

- Direct Access to Numerics in FORTRAN 77
  "Pointers" are provided for direct access to numeric
  data types (i.e., dcomplex, double, fcomplex, float,
  int, and long).  There is a potential for an alignment
  problem with arrays of dcomplex and double, in
  particular, so check the return value of the pointer
  before using it.

- Array Element Access in FORTRAN 77 and Fortran 90
  Array elements are accessible via subroutine calls.  For
  more information, see the users manual.

- Strings in FORTRAN 77
  When implementing a method with an inout or out
  string, the size of the outgoing string is at least
  512 characters long.

- Enums in FORTRAN 77 and Fortran 90
  Due to Babel's dependence upon C for its internal object
  representation, the binding for an enumerated type is
  INTEGER.  The good news is that if your Fortran and C compilers
  use the same word size for C int and Fortran INTEGER, everything
  should work fine.  However, if the sizes are different, then
  you may have problems with using enums.


Outstanding Issues
------------------

  There are several aspects of the distribution that still
need work.  The outstanding issues that need to be addressed
include features as well as documentation.

- Python 'long' Data Type
  Arrays of long are treated as arrays of int32_t in Python.
  Outside of arrays, SIDL long is mapped to Python's indefinite
  precision integer data type.

- Support for Multiple Fortran Compilers
  In order to use SIDL with multiple Fortran compilers, there
  currently must be a separate Babel installation for each
  compiler.  You will also need different versions of the
  runtime library.

- Build Processing
  A mechanism for simplifying the build process needs to be
  explored, especially to facilitate user builds.

- C++ Shared Libraries and libtool
  In general, the creation of shared libraries in C++ seems to
  be problematic.  It was necessary to make compiler-specific
  modifications to libtool.  Consequently, this distribution
  includes our modified libtool.


Future Work
-----------

  The following is a list of key activities that will be
explored for incorporation into subsequent releases:

- Expanded platform and compiler support
- Simplified Build processing
- Data Redistribution
- Expanded interface contract specification and enforcement capabilities


Further Information
-------------------

  The following files are available at the top of the
release directory structure provide additional information
on the Babel release:

    BUGS       Lists known bugs
    COPYRIGHT  Lawrence Livermore National Security, LLC notice
    INSTALL    Provides installation instructions
    README     This file

  Additional background information can be found in the
./doc/papers and ./doc/talks subdirectories including the
outdated specification (./doc/papers/specification.ps).
More recent papers and talks can be found at our web site
at

  http://www.llnl.gov/CASC/components/

  Additional documentation including a tutorial, command
line arguments, SIDL grammar, and crash courses on SIDL
arrays and Fortran can be found in ./doc/babel101.


Project Team
------------

The following individuals are current members of the
development team on the LLNL Components Project:

      Tammy Dahlgren             Tom Epperly
     Adrian Prantl

Community code contributors:

     Boyana Norris               Ben Allan
     Stefan Muszala

Our alpha testers are:

       Bill Bosl                Jeff Painter
       Andy Cleary             Steve Smith

and our alumni are:

    Melvina Blackgoat         Nathan Dykman
      Kevin Durrenberger     Dietmar Ebner
      Sarah Knoop              Scott Kohn
       Gary Kumfert              Jim Leek
      Brent Smolinski

