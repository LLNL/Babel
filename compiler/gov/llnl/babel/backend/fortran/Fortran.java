//
// File:        Fortran.java
// Package:     gov.llnl.babel.backend.fortran
// Revision:    @(#) $Revision: 7421 $
// Description: Collection of static methods for the FORTRAN binding
//
// Copyright (c) 2000-2002, Lawrence Livermore National Security, LLC
// Produced at the Lawrence Livermore National Laboratory.
// Written by the Components Team <components@llnl.gov>
// UCRL-CODE-2002-054
// All rights reserved.
//
// This file is part of Babel. For more information, see
// http://www.llnl.gov/CASC/components/. Please read the COPYRIGHT file
// for Our Notice and the LICENSE file for the GNU Lesser General Public
// License.
//
// This program is free software; you can redistribute it and/or modify it
// under the terms of the GNU Lesser General Public License (as published by
// the Free Software Foundation) version 2.1 dated February 1999.
//
// This program is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the IMPLIED WARRANTY OF
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the terms and
// conditions of the GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program; if not, write to the Free Software Foundation,
// Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA


package gov.llnl.babel.backend.fortran;

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.mangler.NameMangler;
import gov.llnl.babel.backend.mangler.NonMangler;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.backend.writers.LanguageWriterForFortran;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Inverter;
import gov.llnl.babel.symbols.FortranExprString;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import gov.llnl.babel.symbols.Version;
import gov.llnl.babel.symbols.Struct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.Collection;

/**
 * Provide a collection of static methods to provide the mapping of sidl
 * concepts into FORTRAN.  This class provides the mapping of symbol names
 * to FORTRAN symbols, the mapping of the wrapper code to a set of files,
 * and the mapping of types.
 */
public class Fortran implements CodeConstants
{
  /**
   * The dummy argument name for return values.
   */
  public static final String s_return = "retval";

  /**
   * The argument name that holds the object/interface pointer in
   * a call to an object method. It's conceptually like the this pointer in
   * C++.  In FORTRAN, the object has to be passed explicitly.
   */
  public static final String s_self = "self";

  /**
   * The argument name that holds the exception pointer which a method may
   * throw an exception.
   */
  public static final String s_exception = "exception";


  public static final String s_ensure[] = {
    "sidl_general_order",
    "sidl_column_major_order",
    "sidl_row_major_order"
  };
  
  /**
   * The order of all of the following arrays must match the ordering of
   * the constants in the Type class.  A separate version is maintained to
   * facilitate translation for each version of fortran.
   *
   * @see gov.llnl.babel.symbols.Type
   */
  private final static String s_f77_types[] = {
    "void",                     // void
    "logical",                  // bool
    "character",                // character
    "double complex",           // dcomplex
    "double precision",         // double
    "complex",                  // fcomplex
    "real",                     // float
    "integer*4",                // int
    "integer*8",                // long
    "integer*8",                // opaque
    "character*(*)",            // string
    "integer*8",                // enum
    "integer*8",                // struct
    "integer*8",                // class
    "integer*8",                // interface
    "void",                     // package
    "integer*8"                 // symbol
  };

  private final static String s_f90_types[] = {
    "void",  					// void
    "logical",  				// bool
    "character (len=1)",  			// character
    "complex (kind=sidl_dcomplex)",             // dcomplex
    "real (kind=sidl_double)",          	// double
    "complex (kind=sidl_fcomplex)",     	// fcomplex
    "real (kind=sidl_float)",		        // float
    "integer (kind=sidl_int)",		        // int
    "integer (kind=sidl_long)",		        // long
    "integer (kind=sidl_opaque)",		// opaque
    "character (len=*)",       			// string
    "integer (kind=sidl_enum)",		        // enum
  };

  private final static String s_f03_types[] = {
    "void",  					// void
    "logical",                                  // bool
    "character (len=1)",                        // character
    "complex (kind=sidl_dcomplex)",             // dcomplex
    "real (kind=sidl_double)",                  // double
    "complex (kind=sidl_fcomplex)",     	// fcomplex
    "real (kind=sidl_float)",                   // float
    "integer (kind=sidl_int)",                  // int
    "integer (kind=sidl_long)",                 // long
    "type(sidl_opaque_t)",                      // opaque
    "character (len=*)",                        // string
    "integer (kind=sidl_enum)"                  // enum
  };

  /**
   * This array stores the name of the C type that corresponds to the FORTRAN
   * type for a particular sidl type.  For example, an opaque maps into
   * <code>integer*8</code> in FORTRAN.  When the <code>integer*8</code>
   * comes into C, it is a <code>int64_t</code>.
   */
  private final static String[] s_cTypeMatchingFortran = {
    "void",                     // void
    "SIDL_F77_Bool",            // bool
    "char",                     // character
    "struct sidl_dcomplex",     // dcomplex
    "double",                   // double
    "struct sidl_fcomplex",     // fcomplex
    "float",                    // float
    "int32_t",                  // int
    "int64_t",                  // long
    "int64_t",                  // opaque
    "char *",                   // string
    "int64_t",                  // enum
    "void",                     // struct
    "int64_t",                  // class
    "int64_t",                  // interface
    "void",                     // package
    "int64_t",                  // symbol
    "int64_t"                   // array
  };

  /**
   * The same as above, but for Bind(C)
   */
  private final static String[] s_cTypeMatchingFortranBindC = {
    "void",                     // void
    "_Bool",                    // bool
    "char",                     // character
    "struct sidl_dcomplex",     // dcomplex
    "double",                   // double
    "struct sidl_fcomplex",     // fcomplex
    "float",                    // float
    "int32_t",                  // int
    "int64_t",                  // long
    "void *",                   // opaque
    "char *"                    // string
  };

  /**
   * These are Fortran types that match Bind(C) interfaces
   */
  private final static String s_bindc_types[] = {
    "void",  					// void
    "integer(c_int)",                           // bool
    // "character(1, kind=c_char)",             // character
    "integer(c_signed_char)",                   // character
    "complex(c_double_complex)",                // dcomplex
    "real(c_double)",                           // double
    "complex(c_float_complex)",          	// fcomplex
    "real(c_float)",                            // float
    "integer(c_int32_t)",                       // int
    "integer(c_int64_t)",                       // long
    "type(c_ptr)",                              // opaque
    "character(kind=c_char), dimension(*)",     // string (mode IN only!)
    "integer(c_int64_t)",                       // enum
    "type(c_ptr)",                              // struct
    "type(c_ptr)",                              // class
    "type(c_ptr)",                              // interface
    "type(c_ptr)",                              // package
    "type(c_ptr)",                              // symbol
    "type(c_ptr)"                               // array
  };

  private final static int    s_Fortran_Bool = 1;
  private final static String s_F90_Bool = "SIDL_F90_Bool";
  private final static String s_F90_Array = "struct sidl_fortran_array";
  private final static String s_F77_Struct = "int64_t";

  /** Our suffix used by private procedures */
  public final static String s_privateSuffix = "_p";

  /**
   * This class is not intended for public instantiation.
   */
  private Fortran()
  {
    // not intended for instantiation
  }

  /**
   * Convert a symbol name into string with the pieces of the symbol joined
   * together with underline characters.
   *
   * @param id  the symbol id to convert.
   * @return a string representation of the symbol with periods replaced
   * with underline characters.
   */
  public static String getSymbolName(SymbolID id) {
      return id.getFullName().replace('.', '_');
  }

  /**
   * Convert a symbol name into string with the pieces of the symbol joined
   * together with underline characters. Generate short file names
   * if the options for generating code in package-dependent subdirectories
   * and excluding external symbols are enabled (--generate-subdirs and
   * --exclude-external).
   *
   * @param id  the symbol id to convert.
   * @return a string representation of the symbol with periods replaced
   * with underline characters.
   */
  public static String getSymbolNameForFile(SymbolID id) {
    return id.getFullName().replace('.', '_');
  }

  /**
   * Same as above method, but use an extra argument for determining
   * when the file whose name is to be generated corresponds to an impl.
   */
  public static String getSymbolNameForFile(SymbolID id, boolean isImpl,
                                            Context context) {
    if (context.getConfig().makePackageSubdirs() &&
        context.getConfig().getShortFileNames()) {
      return id.getShortName().replace('.','_');
    } else {
      return id.getFullName().replace('.', '_');
    }
  }

  /**
   * Create a new SymbolID for a basic type
   */
  public static SymbolID getSymbolIDforBasicType(Type basicType) {
    return new SymbolID("sidl." + basicType.getTypeString(),
                        new Version());
  }

  /**
   * Return the name of the stub file for a particular symbol.
   * The stub file is a C file that receives calls from FORTRAN client.
   *
   * @param id  the symbol whose stub file will be returned
   * @return the filename of a C file containing the implementation of
   *         the FORTRAN stubs.
   */
  public static String getStubFile(SymbolID id) {
    return getSymbolNameForFile(id) + "_fStub.c";
  }

  public static String getStubNameFile(SymbolID id) {
    return getSymbolNameForFile(id) + "_fAbbrev.h";
  }

  public static String getArrayBindCdeclFile(SymbolID id) {
    return getSymbolNameForFile(id) + "_array_bindcdecls.h";
  }

  public static String getModBindCdeclFile(SymbolID id) {
    return getSymbolNameForFile(id) + "_mod_bindcdecls.h";
  }

  /**
   * Return the name of the stub header file for a particular symbol.
   * The stub header file is a C file that globablly externs a few
   * special functions in the fStub.
   *
   * @param id  the symbol whose stub file will be returned
   * @return the filename of a C file containing the implementation of
   *         the FORTRAN stubs.
   */
  public static String getHeaderFile(SymbolID id) {
    return getSymbolNameForFile(id) + "_fStub.h";
  }

  /**
   * Return the name of the F77 stub documentation file for a particular
   * symbol. The documentation file is FORTRAN pseudo-code to
   * document the calling interface for FORTRAN clients.
   *
   * @param id  the symbol whose stub documentation file will be returned
   * @return the filename of a text file containing the documentation
   *         of the FORTRAN stubs.
   */
  public static String getStubDocFile(SymbolID id) {
    return getSymbolNameForFile(id) + ".fif";
  }

  /**
   * Return the name of the F90 stub module file for a particular
   * symbol. The module file is an F90 file used to package "global
   * data, derived types and their associated operations, interface
   * blocks, and namelist groups."
   *
   * @param id  the symbol whose stub documentation file will be returned
   * @return the filename of a text file containing the documentation
   *         of the FORTRAN stubs.
   */
  public static String getModuleFile(SymbolID id, Context context) {
    return getSymbolNameForFile(id) + "." + getImplExtension(context);
  }

  /**
   * Return the name of the F90 type module file for a particular
   * symbol. The module file is an F90 file used to package the
   * derived type for the symbol.
   *
   * @param id  the symbol whose stub documentation file will be returned
   * @return the filename of a text file containing the documentation
   *         of the FORTRAN stubs.
   */
  public static String getTypeFile(SymbolID id, Context context) {
    return getSymbolNameForFile(id) + "_type." + getImplExtension(context);
  }

  public static String getTypeModule(SymbolID id, Context context) {
    return getSymbolName(id) + "_type";
  }

  public static String getArrayModule(SymbolID id, Context context) {
    return getSymbolName(id) + "_array";
  }

  public static String getArrayFile(SymbolID id, Context context) {
    return getSymbolNameForFile(id) + "_array." + getImplExtension(context);
  }

  public static String getTypeName(SymbolID id) {
    return getSymbolName(id) + "_t";
  }

  public static String getImplTypeName(SymbolID id) {
    return getSymbolName(id) + "_impl_t";
  }

  public static String getImplModule(SymbolID id) {
    return getSymbolName(id) + "_Impl";
  }


  public static String getArrayName(SymbolID id, int dim) {
    if(dim == 0) {//If dim == 0, we'll assume it's a generic array
      return "sidl__array";
    }
    return getSymbolName(id) + "_" + dim + "d";
  }

  public static String getModule(SymbolID id, Context context) {
    return getSymbolName(id);
  }


  /**
   * Return the SymbolID associated with an array type
   */
  public static SymbolID getArraySymbolID(Type t, Context d_context)
  {
    if (t.getDetailedType() == Type.ARRAY) {
      final Type arrayType = t.getArrayType();
      if (null != arrayType) {
        final int detailedType = arrayType.getDetailedType();

        if ((detailedType > Type.VOID) && (detailedType <= Type.STRING)) {
          return Fortran.getSymbolIDforBasicType(arrayType);
        }
        else if(Fortran.hasBindC(d_context)) {
          //For F03, we require the array module also for user-defined types.
          //It's in the _type file therefore.
          return arrayType.getSymbolID();
        }
      }
      else return new SymbolID("sidl.array", new Version());
    }
    return null;
  }

  /**
   * Reorder an argument list to guarantee that all raw arrays occur at the
   * end of the argument list. The relative position of non-raw arrays
   * arguments is unchanged by this function.
   *
   * @param args  the incoming list of arguments
   * @return  the incoming list has been reordered such that all raw arrays
   *          appear at the end.
   */
  public static List reorderArguments(List args)
  {
    ArrayList result = new ArrayList(args.size());
    ArrayList deferred = new ArrayList(args.size());
    Iterator i = args.iterator();
    while (i.hasNext()) {
      Argument a = (Argument)i.next();
      if (a.getType().isRarray()) {
        deferred.add(a);
      }
      else {
        result.add(a);
      }
    }
    result.addAll(deferred);
    return result;
  }

  /**
   * Returns a suffix that is appended to utility functions defined in the
   * Babel stub in order to avoid duplicated symbols during linking.
   */
  static public String getStubSuffix(Context context) {
    if(isFortran77(context))
      return "_f77";
    else if(isFortran90(context))
      return "_f90";
    else if(hasBindC(context))
      return "_f03";
    return "";
  }

  static public String structSerializeStub(SymbolID id,
                                           boolean serialize,
                                           Context context)
  {
    return id.getFullName().replace('.', '_') +
      (serialize ? "__serialize" : "__deserialize") +
      getStubSuffix(context);
  }


  static public String arrayIndices(List indices, String indexExprPrefix)
  {
    StringBuffer result = new StringBuffer();
    Iterator i = indices.iterator();
    result.append('(');
    while (i.hasNext()) {
      AssertionExpression ae = (AssertionExpression)i.next();
      result.append("0:");
      result.append(ae.accept(new FortranExprString(indexExprPrefix), null).toString());
      result.append("-1");
      if (i.hasNext()) {
        result.append(", ");
      }
    }
    result.append(')');
    return result.toString();
  }

  public static boolean hasDirectAccess(Type t)
  {
    if (t.getDetailedType() == Type.ARRAY) {
      final Type arrayType = t.getArrayType();
      if (null != arrayType) {
        switch(arrayType.getDetailedType()) {
        case Type.DCOMPLEX:
        case Type.DOUBLE:
        case Type.FCOMPLEX:
        case Type.FLOAT:
        case Type.INT:
        case Type.LONG:
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Return the name of the stub file for a particular enumeration.
   * The stub file is a DEC FORTRAN include file that defines
   * integer parameters that hold the values of the enumerations.
   *
   * @param id  the symbol whose stub file will be returned
   * @return the filename of a FORTRAN inc file containing the implementation of
   *         the FORTRAN stubs.
   */
  public static String getEnumStubFile(SymbolID id) {
    return getSymbolNameForFile(id) + ".inc";
  }

  public static String getEnsureArray(Type arrayType) {
    switch(arrayType.getDetailedType()) {
    case Type.INTERFACE:
    case Type.CLASS:
      return "sidl_interface__array_ensure((struct sidl_interface__array *)";
    case Type.ENUM:
      return "sidl_long__array_ensure((struct sidl_long__array *)";
    default:
      return "sidl_" + arrayType.getTypeString() + "__array_ensure(";
    }
  }

  public static String getEnsureArrayFunction(Type arrayType) {
    switch(arrayType.getDetailedType()) {
    case Type.INTERFACE:
    case Type.CLASS:
      return "sidl_interface__array_ensure";
    case Type.ENUM:
      return "sidl_long__array_ensure";
    default:
      return "sidl_" + arrayType.getTypeString() + "__array_ensure";
    }
  }
  
  public static String getInitArray(Type arrayType) {
    return "sidl_" + arrayType.getTypeString() + "__array_init(";
  }

  public static String getDelRefArray(Type arrayType) {
    return "sidl__array_deleteRef((struct sidl__array *)";
  }

  /**
   * Return the name of the stub impl file for a particular enumeration.
   * The stub file is a C source file that holds the array of enumeration
   * stubs.
   *
   * @param id  the symbol whose stub file will be returned
   * @return the filename of a C file containing the implementation of
   *         the FORTRAN enum array stubs.
   */
  public static String getEnumStubImpl(SymbolID id) {
    return getSymbolNameForFile(id) + "_fStub.c";
  }

  /**
   * Return the name of the file that holds the implementation of the
   * skeletons for FORTRAN. The skeleton file is written in C, and it is
   * the glue between the IOR and a FORTRAN.
   */
  public static String getSkelFile(SymbolID id) {
    return getSymbolNameForFile(id) + "_fSkel.c";
  }

  /**
   * Return the name of the file that holds the implementation of the
   * skeletons for FORTRAN. This skeleton file is written in Fortran and it is
   * the glue between the C Skel and FORTRAN for F90 structs.
   */
  public static String getfSkelFile(SymbolID id, Context context) {
    return getSymbolNameForFile(id) + "_fSkelf." + getImplExtension(context);
  }

  /**
   * Return the appropriate FORTRAN extension.
   *
   * @return the FORTRAN file extension.
   */
  public static String getImplExtension(Context context) {
    if(hasBindC(context))
      return CodeConstants.C_F03_IMPL_EXTENSION;
    else if (isFortran90(context) || hasBindC(context))
      return CodeConstants.C_F90_IMPL_EXTENSION;
    else {
      if(isFortran7731(context))
        return CodeConstants.C_F7731_IMPL_EXTENSION;
      else
        return CodeConstants.C_F77_IMPL_EXTENSION;
    }
  }

  /**
   * Return the name of the file that hold the implementation of the
   * FORTRAN sidl object. The BABEL system generates the subroutine
   * declarations, but the user needs to fill in the subroutine bodies.
   *
   * @param id the name of the symbol
   * @return the filename for the sidl object implementation.
   */
  public static String getImplFile(SymbolID id, Context context) {
    return getSymbolNameForFile(id, true, context) + "_Impl." +
      getImplExtension(context);
  }

  /**
   * Return the name of the file that hold the derived types for
   * implementation of the FORTRAN sidl object.
   *
   * @param id the name of the symbol
   * @return the filename for the sidl impl module file.
   */
  public static String getImplModuleFile(SymbolID id, Context context) {
    return getSymbolNameForFile(id, true, context) + "_Mod." +
      getImplExtension(context);
  }

  /**
   * Return the name of the file that hold the derived types for
   * Fortran/BindC to C/C++ interoperability.
   *
   * @param id the name of the symbol
   * @return the filename for the sidl struct module file.
   */
  public static String getStructModuleFile(SymbolID id, Context context) {
    return getSymbolNameForFile(id, true, context) + "."+
      getImplExtension(context);
  }

  /**
   * Get the function name that should be used for the FORTRAN stubs to the
   * sidl object methods.  This method does not take into account the
   * compiler specific issues (i.e. whether the symbol should be all upper
   * or lower case or whether it has underscores appended); it provides
   * the starting name with potentially mixed case.
   *
   * @param  id     the name of the symbol who has the method.
   * @param  method information about the method to be named.
   * @return the name of the function to be used in the FORTRAN stub.
   */
  public static String getMethodStubName(SymbolID id,
                                         Method method,
                                         Context context)
  {
    if(hasBindC(context))
      return getBindCName(id, method, context);
    else
      return getSymbolName(id) + "_" + method.getLongMethodName()
        + getMethodSuffix(context);
  }

  /**
   * Get the function name that should be used for the FORTRAN stubs to the
   * sidl object methods.
   *
   * @param  id      the name of the symbol who has the method.
   * @param  method  information about the method to be named.
   * @param  non     the non-name mangler.
   * @param  fort    the fortran name mangler.
   * @param  context information about the method to be named.
   * @return the name of the function to be used in the FORTRAN stub.
   */
  public static String getMethodStubName(SymbolID    id,
                                         Method      method,
                                         NameMangler non,
                                         NameMangler fort,
                                         Context     context)
    throws CodeGenerationException
  {
    String nm;
    try {
      if ( (non != null) && (fort != null) ) {
        //
        // Duplicate the process for establishing whether an entry
        // is needed in AbbrevHeader.java.
        //
        String suffix=!hasBindC(context) ?
          getMethodSuffix(context) : CodeConstants.C_F03_BINDC_SUFFIX;
        nm = non.shortName(id.getFullName(),
                           method.getLongMethodName(),
                           suffix);
        if (nm.length() > AbbrevHeader.getMaxName(context)) {
          nm = fort.shortName(id.getFullName(),
                              method.getLongMethodName(),
                              suffix);
        }
      } else {
        nm = getMethodStubName(id, method, context);
      }
    } catch (java.io.UnsupportedEncodingException uee) {
      throw new CodeGenerationException(uee.getMessage());
    }
    return nm;
  }

  /**
   * Return the Fortran function name that should be used for the skeleton
   * method.  This is a Bind(C) function that is called by the IOR.
   *
   * @param  id     the name of the symbol who has the method
   * @param  method information about the method.
   * @return the name of the C skeleton function.
   */
  public static String getMethodFSkelName(SymbolID    id,
                                          Method      method,
                                          NameMangler non,
                                          NameMangler fort,
                                          Context     context)
    throws CodeGenerationException
  {
    String nm = null;
    try {
      String suffix = "_fskel" + CodeConstants.C_F03_BINDC_SUFFIX;
      nm = non.shortName(id.getFullName(),
                         method.getLongMethodName(),
                         suffix);
      if (nm.length() > AbbrevHeader.getMaxName(context)) {
        nm = fort.shortName(id.getFullName(),
                            method.getLongMethodName(),
                            suffix);
      }
    } catch (java.io.UnsupportedEncodingException uee) {
      throw new CodeGenerationException(uee.getMessage());
    }
    return nm;
  }


  /**
   * This returns the list of all methods that need to have stub methods
   * generated. This includes all locally defined methods and all locally
   * overloaded methods (i.e., if a method defined in a parent class is
   * overloaded by a locally defined method, it must be included).
   *
   * @return list of {@link gov.llnl.babel.symbols.Method} objects
   */
  public static List getMethodList(Extendable ext)
  {
    final List allMethods = ext.getMethods(true);
    final ArrayList result = new ArrayList(allMethods.size());
    final Iterator i = allMethods.iterator();
    while(i.hasNext()) {
      final Method m = (Method)i.next();
      if (null != ext.lookupMethodByShortName(m.getShortMethodName(),false)) {
        result.add(m);
      }
    }
    return result;
  }

  /**
   * Returns the _set_ of struct types referenced by the given argument
   * list. Nested structs are ignored.
   *
   * @return list of {@link gov.llnl.babel.symbols.Method} struct types
   */
  public static Set getStructTypes(List arg_list) {
    Set s = new HashSet();
    for(Iterator I=arg_list.iterator(); I.hasNext(); ) {
      Type t = ((Argument) I.next()).getType();
      if(t.isStruct()) s.add(t);
    }
    return s;
  }

  /**
   * Get the built-in function name without the leading underscore.
   * This method ignores compiler-specific issues in terms of case.
   * The issue it is intended to address is to avoid the leading
   * underscore problem with method names that exceed the typical
   * 31 character limit.
   *
   * @param index       the index of the built-in method that is
   *                    desired (e.g., IOR.CONTRACTS).
   * @param isStatic    TRUE if the static version is desired; FALSE otherwise.
   * @return the name of the function to be used in Fortran
   * @exception java.lang.ArrayIndexOutOfBoundsException
   *    this runtime exception is thrown if <code>index</code> is out of
   *    bounds.
   */
  public static String getAltBuiltinName(int index, boolean isStatic) {
    String nm = IOR.getBuiltinName(index, isStatic);
    return (nm.startsWith("_")) ? nm.substring(1, nm.length()) : nm;
  }

  /**
   * Get the function name that should be used for the FORTRAN stubs to the
   * sidl object methods.  This method does not take into account the
   * compiler specific issues (i.e. whether the symbol should be all upper
   * or lower case or whether it has underscores appended); it provides
   * the starting name with potentially mixed case.
   *
   * @param  id     the name of the symbol who has the method.
   * @param  method information about the method to be named.
   * @return the name of the function to be used in the FORTRAN stub.
   */
  public static String getAltStubName(SymbolID id,
                                      Method method,
                                      Context context) {
    return getSymbolName(id) + "_" + method.getLongMethodName()
      + getAltSuffix(context);
  }

  /**
   * Get the function name that should be used for the FORTRAN super method
   * in the skels (availible in this Impls). This method does not take into account the
   * compiler specific issues (i.e. whether the symbol should be all upper
   * or lower case or whether it has underscores appended); it provides
   * the starting name with potentially mixed case.
   *
   * @param  id     the name of the symbol who has the method.
   * @param  method information about the method to be named.
   * @return the name of the function to be used in the FORTRAN stub.
   */
  public static String getMethodSuperName(SymbolID id,
                                          Method method,
                                          NameMangler mang,
                                          Context context)
    throws CodeGenerationException
  {
    try {
      return mang.shortName(id.getFullName(),
                            "super_" + method.getLongMethodName(),
                            getMethodSuffix(context));
    }
    catch (java.io.UnsupportedEncodingException uee) {
      throw new CodeGenerationException(uee.getMessage());
    }
  }


  /**
   * Return the appropriate version of Fortran (i.e., 77 or 90).
   *
   * @return the version of Fortran being generated
   */
  public static int getFortranVersion(Context context) {
    int fversion;
    if(hasBindC(context))
      fversion = CodeConstants.C_F03_VERSION;
    else if (isFortran90(context)) {
      fversion = CodeConstants.C_F90_VERSION;
    } else {
      fversion = CodeConstants.C_F77_VERSION;
    }
    return fversion;
  }

  /**
   * Return the appropriate FORTRAN method name suffix based on the version.
   *
   * @return the FORTRAN method name suffix.
   */
  public static String getMethodSuffix(Context context) {
    if(isFortran03(context))
      return CodeConstants.C_F03_METHOD_SUFFIX;
    else if (isFortran90(context))
      return CodeConstants.C_F90_METHOD_SUFFIX;
    else
      return CodeConstants.C_F77_METHOD_SUFFIX;
  }

  /**
   * Return the appropriate FORTRAN alternative stub name.
   *
   * @return the FORTRAN method name suffix.
   */
  public static String getAltSuffix(Context context) {
    if(isFortran03(context))
      return CodeConstants.C_F03_ALT_SUFFIX;
    else
      return CodeConstants.C_F90_ALT_SUFFIX;
  }

  /**
   * Return the appropriate FORTRAN impl method name suffix based.
   *
   * @return the FORTRAN impl method name suffix.
   */
  public static String getImplMethodSuffix(Context context) {
    if(hasBindC(context))
      return CodeConstants.C_F03_IMPL_METHOD_SUFFIX;
    else if (isFortran90(context)) {
      return CodeConstants.C_F90_IMPL_METHOD_SUFFIX;
    } else {
      return CodeConstants.C_F77_IMPL_METHOD_SUFFIX;
    }
  }

  /**
   * Return the appropriate version of SIDLFortran##Symbol.
   *
   * @return the appropriate version of SIDLFortran##Symbol.
   */
  public static String getFortranSymbol(Context context) {
    return "SIDLFortran" + getFortranVersion(context) + "Symbol";
  }

  /**
   * Generate the name of the array destructor function.
   */
  public static String getArrayDestructor(SymbolID id, Context context) {
    return getSymbolName(id) + "__array_deleteRef" +
      getMethodSuffix(context);
  }

  /**
   * Generate the name of the array constructor function.
   */
  public static String getArrayConstructor(SymbolID id, Context context) {
    return getSymbolName(id) + "__array_createRow" +
      getMethodSuffix(context);
  }

  /**
   * Generate the name of the array set element function.
   */
  public static String getArraySet(SymbolID id, Context context) {
    return getSymbolName(id) + "__array_set" +
      getMethodSuffix(context);
  }

  /**
   * Generate the name of the array set element function.
   */
  public static String getArraySet(SymbolID id, int numArgs, Context context)
  {
    return getSymbolName(id) + "__array_set" +
      Integer.toString(numArgs) + getMethodSuffix(context);
  }

  /**
   * Generate the name of the array get element function.
   */
  public static String getArrayGet(SymbolID id, Context context) {
    return getSymbolName(id) + "__array_get" +
      getMethodSuffix(context);
  }

  /**
   * Generate the name of the array get element function.
   */
  public static String getArrayGet(SymbolID id, int numArgs,
                                   Context context) {
    return getSymbolName(id) + "__array_get" +
      Integer.toString(numArgs) + getMethodSuffix(context);
  }

  /**
   * Generate the name of the array dimension access function.
   */
  public static String getArrayDimen(SymbolID id, Context context) {
    return getSymbolName(id) + "__array_dimen" +
      getMethodSuffix(context);
  }

  /**
   * Generate the name of the array lower bound access function.
   */
  public static String getArrayLower(SymbolID id, Context context) {
    return getSymbolName(id) + "__array_lower" +
      getMethodSuffix(context);
  }

  /**
   * Generate the name of the array upper bound access function.
   */
  public static String getArrayUpper(SymbolID id, Context context) {
    return getSymbolName(id) + "__array_upper" + getMethodSuffix(context);
  }

  /**
   * Generate the name of the array length access function.
   */
  public static String getArrayLength(SymbolID id, Context context) {
    return getSymbolName(id) + "__array_length" + getMethodSuffix(context);
  }

  /**
   * Return the function name that should be used for the FORTRAN subroutine
   * that implements a particular method.  The returned string leaves
   * the cast of the components of the name unchanged.  If <code>id</code>
   * and the method name have mixed case, this method returns a mixed case
   * string.
   *
   * @param  id     the name of the symbol who has the method.
   * @param  method the information about the method.
   * @param  mang   the name mangler being used to address long names.
   * @return the name of the FORTRAN subroutine that implements this method.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    the name mangler is broken.
   */
  public static String getMethodImplName(SymbolID id,
                                         Method method,
                                         NameMangler mang,
                                         Context context)
    throws CodeGenerationException
  {
    try {

      //for Bind(C), we can use a more natural name
      if(Fortran.hasBindC(context)) {
        String name = method.getLongMethodName();
        //strip leading underscores from builtins
        while(name.startsWith("_")) name = name.substring(1);
        return mang.shortName(name,
                              getImplMethodSuffix(context));
      }
      else {
        return mang.shortName(id.getFullName(),
                              method.getLongMethodName(),
                              getImplMethodSuffix(context));
      }
    }
    catch (java.io.UnsupportedEncodingException uee) {
      throw new CodeGenerationException(uee.getMessage());
    }
  }

  /**
   * Returns the name of a Fortran method for the ISO BIND(C) extension.
   *
   * @param  id     the name of the symbol who has the method.
   * @param  method the information about the method.
   * @param  mang   the name mangler being used to address long names.
   * @return the name of the FORTRAN subroutine that implements this method.
   */
  public static String getBindCName(SymbolID id,
                                    Method method,
                                    Context context)
  {
    return getSymbolName(id) + '_' +
      method.getLongMethodName() +
      CodeConstants.C_F03_BINDC_SUFFIX;
  }

  /**
   * Returns the name of a Fortran method for the ISO BIND(C) extension.
   *
   * @param  id     the name of the symbol who has the method.
   * @param  method the information about the method.
   * @param  mang   the name mangler being used to address long names.
   * @return the name of the FORTRAN subroutine that implements this method.
   */
  public static String getAltBindCName(SymbolID id,
                                       Method method,
                                       Context context)
  {
    return getSymbolName(id) + "_" +
      method.getLongMethodName() + 
      getAltSuffix(context);
  }

  /**
   * Returns the name of a Fortran method skeleton for the ISO BIND(C) extension.
   *
   * @param  id     the name of the symbol who has the method.
   * @param  method the information about the method.
   * @param  mang   the name mangler being used to address long names.
   * @return the name of the FORTRAN subroutine that implements this method.
   */
  public static String getBindCSkelName(SymbolID id,
                                        Method method,
                                        Context context)
  {
    return getSymbolName(id) + '_' +
      method.getLongMethodName() + "_skel" +
      CodeConstants.C_F03_BINDC_SUFFIX;
  }

  /**
   * Returns the name of a Fortran type that is interoperable with Babel's C EPV
   */
  public static String getBindCEPVTypeName(SymbolID id, boolean doStatic) {
    return getSymbolName(id) + (doStatic ? "_sepv_t" : "_epv_t");
  }

  /**
   * Returns the name of a Fortran type that is interoperable with Babel's C EPV
   */
  public static String getBindCPreEPVTypeName(SymbolID id, boolean doStatic) {
    return getSymbolName(id) + (doStatic ? "_pre_sepv_t" : "_pre_epv_t");
  }

  /**
   * Returns the name of a Fortran type that is interoperable with Babel's C EPV
   */
  public static String getBindCPostEPVTypeName(SymbolID id, boolean doStatic) {
    return getSymbolName(id) + (doStatic ? "_post_sepv_t" : "_post_epv_t");
  }
  
  /**
   * Returns whether or not the given function requires a C stub for Fortran
   * 2003. Argument do_alternate is true if the interface using native
   * language pointers is to be generated for rarrays.
   */
  public static boolean needsCStub(Method m, boolean do_alternate, Context context) {
    String name = m.getLongMethodName();
    
    int ret_t = m.getReturnType().getDetailedType();
    //some of the builtins are currently handled badly!
    return !hasBindC(context) ||
      do_alternate && m.hasRarray() || 
      ret_t == Type.STRUCT ||
      ret_t == Type.FCOMPLEX ||
      ret_t == Type.DCOMPLEX ||
      name.equals("newLocal") ||
      name.equals("newRemote") ||
      name.equals("rConnect") ||
      name.equals("isLocal") || name.equals("_isLocal");
  }

  /**
   * Returns whether or not the given function requires a C skeleton for Fortran 2003
   */
  public static boolean needsCSkel(Method m, Context context) {
    String name = m.getLongMethodName();
    int ret_t = m.getReturnType().getDetailedType();
    //some of the builtins are currently handled badly!
    return !hasBindC(context) ||
      ret_t == Type.STRUCT ||
      ret_t == Type.FCOMPLEX ||
      ret_t == Type.DCOMPLEX ||
      name.equals("_load");
  }

  /**
   * Return the extended function name (i.e., one with the full name
   * prepended).
   *
   * @param  id       the name of the symbol who has the method.
   * @param  baseName the base method name.
   *
   * @return the name of the FORTRAN subroutine that implements this method.
   */
  public static String getExtendedMethodName(SymbolID id, String baseName)
  {
    return id.getFullName().replace('.','_') + "__" + baseName;
  }

  /**
   * Gets a possibly mangled version of the extended method name.  Mangling
   * is only applied when needed to ensure the max size is not exceeded.
   *
   * @param  id       the name of the symbol who has the method.
   * @param  baseName the base name to be extended.
   * @param  non      the non-name mangler.
   * @param  fort     the fortran name mangler.
   * @return the possibly mangled extended method name.
   */
  public static String getExtendedMethodName(SymbolID    id,
                                             String      baseName,
                                             NameMangler non,
                                             NameMangler fort,
                                             Context     context)
    throws CodeGenerationException
  {
    String nm;
    try {
      if ( (non != null) && (fort != null) ) {
        //
        // Assumes important to match the number of underbars used
        // in getExtendedMethodName(id, baseName).
        //
        String extBaseName = "_" + baseName;

        //
        // Assumed AbbrevHeader.MAXNAME is the same one used to instantiate
        // the Fortran name mangler.
        //
        nm = non.shortName(id.getFullName(), extBaseName, "");
        if (nm.length() > AbbrevHeader.getMaxName(context)) {
          nm = fort.shortName(id.getFullName(), baseName, "");
        }
      } else {
        nm = getExtendedMethodName(id, baseName);
      }
    } catch (java.io.UnsupportedEncodingException uee) {
      throw new CodeGenerationException(uee.getMessage());
    }
    return nm;
  }

  /**
   * Return the function name that should be used for the FORTRAN subroutine
   * that implements a particular SUPER method.  The returned string leaves
   * the cast of the components of the name unchanged.  If <code>id</code>
   * and the method name have mixed case, this method returns a mixed case
   * string.
   *
   * @param  id     the name of the symbol who has the method.
   * @param  method the information about the method.
   * @param  mang   the name mangler being used to address long names.
   * @return the name of the FORTRAN subroutine that implements this method.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    the name mangler is broken.
   */
  public static String getMethodSuperImplName(SymbolID id,
                                         Method method,
                                         NameMangler mang,
                                              Context context)
    throws CodeGenerationException
  {
    try {
      return mang.shortName(id.getFullName(),
                            "super_"+method.getLongMethodName(),
                            getImplMethodSuffix(context));
    }
    catch (java.io.UnsupportedEncodingException uee) {
      throw new CodeGenerationException(uee.getMessage());
    }
  }

  /**
   * Return the C function name that should be used for the skeleton
   * method.  This is a C function that is called by the IOR.
   *
   * @param  id     the name of the symbol who has the method
   * @param  method information about the method.
   * @return the name of the C skeleton function.
   */
  public static String getMethodSkelName(SymbolID id, Method method) {
    return "skel_" + method.getLongMethodName();
  }

  /**
   * Return the C type corresponding to the FORTRAN type corresponding to
   * a particular sidl type.  This is the type that a C subroutine would
   * need to pass to FORTRAN, or the type that a C subroutine could expect
   * to receive from a FORTRAN caller.
   *
   * @param type  the sidl type description.
   * @return the C type corresponding to the FORTRAN type corresponding
   *         to the sidl type.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    the type is unsupported.
   */
  public static String getFortranTypeInC(Type type, Context context)
    throws CodeGenerationException
  {
    final int t = type.getDetailedType();

    if(Fortran.hasBindC(context)) {
      switch(t) {
      case Type.STRUCT:
      case Type.ENUM:
      case Type.SYMBOL:
      case Type.INTERFACE:
      case Type.CLASS:
      case Type.ARRAY:
        return IOR.getReturnString(type, context, true, false);
      default:
        if (t >= 0 && t < s_cTypeMatchingFortranBindC.length)
          return s_cTypeMatchingFortranBindC[t];
      }
    }
    else {
      if (t >= 0 && t < s_cTypeMatchingFortran.length) {
        if (isFortran90(context)) {
          if (t == s_Fortran_Bool) return s_F90_Bool;
          if (t == Type.ARRAY) {
            if (type.isRarray()) 
              return getFortranTypeInC(type.getArrayType(), context);
            else return s_F90_Array;
          }
        } else { /* F77 */ 
          if(type.isStruct()) return s_F77_Struct;
        }
        return s_cTypeMatchingFortran[t];
      }
    }
    throw new CodeGenerationException("Unknown supported " + t);
  }

  /**
   * Return the sidl Fortran prefix for macros and types.
   *
   * @return the sidl Fortran prefix
   */
  public static String getFortranPrefix(Context context) {
    return "SIDL_F" + getFortranVersion(context);
  }

  public static boolean needsAbbrev(Context context)
  {
    return ! "f77".
      equals(context.getConfig().getTargetLanguage());
  }

  public static boolean isFortran(Context context)
  {
    return isFortran03(context) ||
      isFortran90(context) ||
      isFortran77(context) ||
      isFortran7731(context);
  }

  public static boolean isFortran03(Context context)
  {
    return "f03".equals(context.getConfig().getTargetLanguage());
  }

  public static boolean hasBindC(Context context)
  {
    return "f03".equals(context.getConfig().getTargetLanguage());
  }

  public static boolean isFortran90(Context context)
  {
    return "f90".equals(context.getConfig().getTargetLanguage());
  }

  public static boolean isFortran77(Context context)
  {
    return "f77".equals(context.getConfig().getTargetLanguage());
  }

  public static boolean isFortran7731(Context context)
  {
    return "f77_31".equals(context.getConfig().getTargetLanguage());
  }

  /**
   * Return TRUE if the type is one of the standard primitive types,
   * used for F03 Function return types; otherwise, return FALSE.
   */
  public static boolean isFortranFunction(Method m, Context context)
  {
    int type = m.getReturnType().getDetailedType();
    return Fortran.hasBindC(context) && type != Type.VOID;
  }

  public static String arrayIOR(Context context)
  {
    return (isFortran90(context) || hasBindC(context)) ? ".d_ior" : "";
  }

  public static Method getStructAlloc(Struct strct, Context context) {
    final String name = IOR.getSymbolName(strct);
    final String mname = name + "_Alloc" + getMethodSuffix(context);
    Method m = new Method(context);
    String comment[] = {
      "Allocates a new struct of type " + name + " on the heap."
    };
    m.setMethodName(mname);
    m.setDefinitionModifier(Method.STATIC);
    m.setReturnType(new Type(Type.VOID));
    m.setComment(new Comment(comment));
    m.addThrows(IOR.getRuntimeException(context));
    m.addArgument(new Argument(Argument.OUT,
                               new Type(Type.LONG),
                               "retval"));
    return m;
  }

  public static Method getStructDealloc(Struct strct, Context context) {
    final String name = IOR.getSymbolName(strct);
    final String mname = name + "_Dealloc" +  getMethodSuffix(context);
    Method m = new Method(context);
    String comment[] = {
      "Frees a dynamically allocated struct of type " + name + "."
    };
    m.setMethodName(mname);
    m.setDefinitionModifier(Method.STATIC);
    m.setReturnType(new Type(Type.VOID));
    m.setComment(new Comment(comment));
    m.addThrows(IOR.getRuntimeException(context));
    m.addArgument(new Argument(Argument.INOUT,
                               new Type(strct.getSymbolID(), context),
                               "s"));
    return m;
  }

  public static Method getStructInit(Struct strct, Context context) {
    final String name = IOR.getSymbolName(strct);
    final String mname = name + "_Init" + getMethodSuffix(context);
    Method m = new Method(context);
    String comment[] = {
      "Initializes a properly allocated struct of type " + name + "."
    };
    m.setMethodName(mname);
    m.setDefinitionModifier(Method.STATIC);
    m.setReturnType(new Type(Type.VOID));
    m.setComment(new Comment(comment));
    m.addArgument(new Argument(Argument.INOUT,
                               new Type(strct.getSymbolID(), context),
                               "s"));
    return m;
  }

  public static Method getStructDestroy(Struct strct, Context context) {
    final String name = IOR.getSymbolName(strct);
    final String mname = name + "_Destroy" + getMethodSuffix(context);
    Method m = new Method(context);
    String comment[] = {
      "Properly releases all struct members. ",
      "This function does not actually deallocate the struct."
    };
    m.setMethodName(mname);
    m.setDefinitionModifier(Method.STATIC);
    m.setReturnType(new Type(Type.VOID));
    m.setComment(new Comment(comment));
    m.addThrows(IOR.getRuntimeException(context));
    m.addArgument(new Argument(Argument.INOUT,
                               new Type(strct.getSymbolID(), context),
                               "s"));
    return m;
  }

  public static Method getStructRarrayAlloc(Struct strct, Context context) {
    final String name = IOR.getSymbolName(strct);
    final String mname = name + "_rarray_alloc" + getMethodSuffix(context);
    Method m = new Method(context);
    String comment[] = {
      "Allocates all Rarrays new struct of type " + name + " on the heap."
    };
    m.setMethodName(mname);
    m.setDefinitionModifier(Method.STATIC);
    m.setReturnType(new Type(Type.VOID));
    m.setComment(new Comment(comment));
    m.addThrows(IOR.getRuntimeException(context));
    m.addArgument(new Argument(Argument.INOUT,
                               new Type(strct.getSymbolID(), context),
                               "s"));
    return m;
  }

  public static Method getStructRarrayDealloc(Struct strct, Context context) {
    final String name = IOR.getSymbolName(strct);
    final String mname = name + "_rarray_dealloc" +  getMethodSuffix(context);
    Method m = new Method(context);
    String comment[] = {
      "Frees all dynamically allocated Rarrays in a struct of type " + name + "."
    };
    m.setMethodName(mname);
    m.setDefinitionModifier(Method.STATIC);
    m.setReturnType(new Type(Type.VOID));
    m.setComment(new Comment(comment));
    m.addThrows(IOR.getRuntimeException(context));
    m.addArgument(new Argument(Argument.INOUT,
                               new Type(strct.getSymbolID(), context),
                               "s"));
    return m;
  }

  public static Method getStructSetter(Struct strct,
                                       Struct.Item fld,
                                       Context context) {
    final String sname = IOR.getSymbolName(strct);
    final String fname = fld.getName();
    final String mname = sname + "_Set_" + fname + getMethodSuffix(context);
    final Type type = fld.getType();
    Method m = new Method(context);
    String comment[] = {
      "Automatically generated setter for field " + fname + "."
    };
    m.setMethodName(mname);
    m.setDefinitionModifier(Method.STATIC);
    m.setReturnType(new Type(Type.VOID));
    m.setComment(new Comment(comment));
    m.addArgument(new Argument(Argument.INOUT,
                               new Type(strct.getSymbolID(), context),
                               "s"));
    m.addArgument(new Argument(Argument.IN, type, "_in_" + fname));
    if(fld.getType().isStruct())
      m.addThrows(IOR.getRuntimeException(context));
    return m;
  }

  public static Method getStructGetter(Struct strct,
                                       Struct.Item fld,
                                       Context context) {
    final String sname = IOR.getSymbolName(strct);
    final String fname = fld.getName();
    final String mname = sname + "_Get_" + fname +  getMethodSuffix(context);
    final Type type = fld.getType();
    Method m = new Method(context);
    String comment[] = {
      "Automatically generated getter for field " + fname + "."
    };
    m.setMethodName(mname);
    m.setDefinitionModifier(Method.STATIC);
    m.setReturnType(new Type(Type.VOID));
    m.setComment(new Comment(comment));
    m.addArgument(new Argument(Argument.IN,
                               new Type(strct.getSymbolID(), context),
                               "s"));
    m.addArgument(new Argument(Argument.OUT, type, "_out_" + fname));
    return m;
  }

  public static Method createCast(Context context,
                                  SymbolID id)
  {
    Method m = new Method(context);
    m.setMethodName("_cast");
    m.setDefinitionModifier(Method.STATIC);
    String[] s = new String[1];
    s[0] = "Cast method for interface and type conversions.";
    m.setComment(new Comment(s));
    m.setReturnType(new Type(id, context));
    Argument a = new
      Argument(Argument.INOUT, new Type(Type.OPAQUE), "ref");
    m.addArgument(a);
    m.addThrows(IOR.getRuntimeException(context));
    return m;
  }

  public static  Method createCastTwo(Context context, SymbolID id)
    throws CodeGenerationException
  {
    Method m = IOR.getBuiltinMethod(IOR.CAST, id, context).cloneMethod();
    m.setMethodName("_cast2");
    return m;
  }

  public static Type convertRarrayToArray(Type array,
                                          Context context) {
    if (array.isRarray()) {
      return new Type(array.getArrayType(), array.getArrayDimension(),
                      array.getArrayOrder(), context);
    }
    return array;
  }
  
  /**
   * Return the FORTRAN type declaration corresponding to <code>type</code>,
   * a particular sidl type. This is potentially different from the return
   * type in F03 bindings.
   *
   * @param  type  a sidl type description.
   * @return the FORTRAN type used to some something of the given sidl
   * type.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    the type is unsupported.
   */
  public static String getArgumentString(Type type,
                                         Context context,
                                         boolean typeInStruct)
    throws CodeGenerationException
  {
    final int t = type.getDetailedType();
    if(isFortran03(context)) {
      if (t >= 0 && t < s_f03_types.length) {
        return s_f03_types[t];
      } else {
        switch(t) {
        case Type.INTERFACE:
        case Type.STRUCT:
        case Type.CLASS:
          return "type(" + getTypeName(type.getSymbolID()) + ")";
        case Type.ARRAY:
          Type arrayType = type.getArrayType();
          if (null != arrayType) {
            if (type.isRarray()) {
              return getArgumentString(arrayType, context, false);
            }
            else{
              if (arrayType.getDetailedType() <= Type.STRING) {
                return "type(" +
                  getArrayName(new SymbolID("sidl." + arrayType.getTypeString(),
                                            new Version()),
                               type.getArrayDimension()) + ")";
              }
              else {
                return "type(" + getArrayName(arrayType.getSymbolID(),
                                              type.getArrayDimension()) + ")";
              }
            }
          }
          else {
            return "type(sidl__array)";
          }
        default:
          throw new CodeGenerationException("Unknown supported " + t);
        }
      }
    }
    else {
      return getReturnString(type, context, typeInStruct);
    }
  }


  /**
   * Return the FORTRAN type declaration corresponding to <code>type</code>,
   * a particular sidl type.
   *
   * @param  type  a sidl type description.
   * @return the FORTRAN type used to some something of the given sidl
   * type.
   * @exception gov.llnl.babel.backend.CodeGenerationException
   *    the type is unsupported.
   */
  public static String getReturnString(Type type,
                                       Context context,
                                       boolean typeInStruct)
    throws CodeGenerationException
  {
    final int t = type.getDetailedType();
    if (isFortran90(context)) {
      if (t >= 0 && t < s_f90_types.length) {
        if (t==Type.STRING && typeInStruct) {
          /*Strings within derived types must be fixed length-hack for now SPM */
          // FIXME: work around this limitation!
          return "character(len=256)";
          //return "integer (kind=sidl_arrayptr)";
        } else {
          return s_f90_types[t];
        }
      } else {
        switch(t) {
        case Type.INTERFACE:
        case Type.STRUCT:
        case Type.CLASS:
          return "type(" + getTypeName(type.getSymbolID()) + ")";
        case Type.ARRAY:
          Type arrayType = type.getArrayType();
          if (null != arrayType) {
            if (type.isRarray()) {
              return getReturnString(arrayType, context,false);
            } else {
              if (arrayType.getDetailedType() <= Type.STRING) {
                return "type(" +
                  getArrayName(new SymbolID("sidl." + arrayType.getTypeString(),
                                            new Version()),
                               type.getArrayDimension()) + ")";
              }
              else {
                return "type(" + getArrayName(arrayType.getSymbolID(),
                                              type.getArrayDimension()) + ")";
              }
            }
          } else {
            return "type(sidl__array)";
          }
        default:
          throw new CodeGenerationException("Unknown supported " + t);
        }
      }
    } else if(isFortran03(context)) {
      //this is like getArgumentString except for strings
      if (t == Type.STRING)
        return "character (len=sidl_f03_str_minsize)";
      else
        return getArgumentString(type, context, typeInStruct);
    } else {
      if (t >= 0 && t < s_f77_types.length) {
        return s_f77_types[t];
      }
      else if (type.isArray()) {
        return type.isRarray() ? getReturnString(type.getArrayType(), context,false)
          : "integer*8";
      }
      throw new CodeGenerationException("Unknown supported " + t);
    }
  }

  /**
   * Returns the Bind(C) Type corresponding to the given SIDL type
   */
  public static String getBindCType(Type type, int mode) {
    return getBindCType(type, mode, false);
  }

  /**
   * Returns the Bind(C) Type corresponding to the given SIDL type
   */
  public static String getBindCType(Type type, int mode, boolean is_skeleton) {
    int t = type.getDetailedType();
    String res = "";
    if(t == Type.STRING) {
      if(mode == Argument.IN)
        if(is_skeleton)
          res = "type(c_ptr), value";
        else
          res = s_bindc_types[t];
      else
        res = "type(c_ptr)";
    }
    else if(t >= 0 && t < s_bindc_types.length) {
      res = s_bindc_types[t];
      if(mode == Argument.IN || type.isStruct())
        res += ", value";
    }
    return res;
  }

  /**
   * Returns the Bind(C) Type corresponding to the given SIDL type
   */
  public static String getBindCFieldType(Type type) {
    int t = type.getDetailedType();
    if(t == Type.STRING)
      return "type(c_ptr)";
    if(t >= 0 && t < s_bindc_types.length)
      return s_bindc_types[t];
    return "";
  }

  /**
   * Returns the Bind(C) Type corresponding to the given SIDL type
   */
  public static String getBindCReturnType(Type type) {
    int t = type.getDetailedType();
    if(t == Type.STRING) // strings have to be handled with care
      return "type(c_ptr)";
    else if(t >= 0 && t < s_bindc_types.length)
      return s_bindc_types[t];
    return "";
  }

  /**
   * Computes a shape array for the given rarray suitable for a call to
   * c_f_pointer. The output needs to be wrapped in "["brackets"]".
   */
  public static String getBindCRarrayShape(Type type) {
    StringBuffer result = new StringBuffer();
    for(Iterator I = type.getArrayIndexExprs().iterator(); I.hasNext();) {
      AssertionExpression ae = (AssertionExpression) I.next();
      result.append(ae.accept(new FortranExprString(""), null).toString());
      if(I.hasNext()) result.append(", ");
    }
    return result.toString();
  }

  /**
   * Contruct the name for a BindC Proxy variable
   * (adds a <code>ptr_</code> prefix and sanitizes
   * special characters
   */
  public static String getBindCProxyName(String name) {
    return "ptr_" + name.replaceAll("%","__");
  }


  /**
   * Emits declarations for rarrays indices
   */
  public static void declareIndices(Map indexMap,
                                    LanguageWriterForFortran d_lw,
                                    Context d_context)
    throws CodeGenerationException
  {
    for(Iterator I = indexMap.entrySet().iterator(); I.hasNext(); ) {
      Map.Entry ent = (Map.Entry)I.next();
      String indexVar = (String)ent.getKey();
      Collection choice = (Collection)ent.getValue();
      if (choice.size() > 0) {
        Method.RarrayInfo info = (Method.RarrayInfo) choice.iterator().next();
        Type t = info.index.getType();
        d_lw.println(getArgumentString(t, d_context, false) +
                     " :: " + info.index.getFormalName());
      }
    }
  }
  
  /**
   * Emits initializations for rarrays indices
   */
  public static void printIndexExprs(Map indexMap,
                                     Map expr_map,
                                     String builtin, 
                                     LanguageWriterForFortran d_lw,
                                     Context d_context)
      throws CodeGenerationException
  {
    
    for(Iterator I = indexMap.entrySet().iterator(); I.hasNext(); ) {
      Map.Entry ent = (Map.Entry)I.next();
      String indexVar = (String)ent.getKey();
      Collection choice = (Collection)ent.getValue();
      if (choice.size() > 0) {
        Method.RarrayInfo info = (Method.RarrayInfo)choice.iterator().next();
        String name = info.rarray.getFormalName();
        if(expr_map != null) name = (String)expr_map.get(name);
        d_lw.print(indexVar + " = ");
        d_lw.println(FortranExprString.
                     toFortranString(Inverter.invertExpr
                                     ((AssertionExpression)
                                      info.rarray.getType().
                                      getArrayIndexExprs().get(info.dim),
                                      builtin + "(" +
                                      name + ", " +
                                      (info.dim+1) + ')', d_context)));
      }
    }
  }
  
  /**
   * Emits code to convert the bindC variable <code>name</code> of the type
   * <code>type</code> to the eqvivalent Fortran proxy <code>cname</code>
   * @param generateDefaultAssignment   if true, generate a copy assignment,
   *                                    even if no conversion is necessary
   */
  public static void printBindC2Fortran(Type t,
                                        String cname,
                                        String name,
                                        int mode,
                                        boolean generateDefaultAssignment,
                                        boolean generateEnsure,
                                        LanguageWriterForFortran d_lw) {

    switch(t.getDetailedType()) {
    case Type.BOOLEAN:
      //This is to avoid logical to int conversion warnings
      d_lw.println(name + " = .false.");
      d_lw.println("if(" + cname + " .ne. 0) " + name + " = .true.");
      break;
    case Type.INTERFACE:
    case Type.CLASS:
      if(cname.equals(Fortran.s_self)) {
        d_lw.println("call " + getSymbolName(t.getSymbolID()) +
                   "_impl_cast(" + cname + ", " + name + ")");
      }
      else {
        d_lw.println(name + "%d_ior = " + cname);
        d_lw.println("call cache_epv(" + name + ")");
      }
      break;
    case Type.ARRAY:
      if(generateEnsure) {
        if(t.isRarray() || t.hasArrayOrderSpec()) {
          int order = t.hasArrayOrderSpec() ? t.getArrayOrder() : Type.COLUMN_MAJOR;
          String fname = getEnsureArrayFunction(t.getArrayType());
          d_lw.println(cname + "_tmp = " + cname);
          d_lw.println(cname + " = " + fname + "(" + cname + "_tmp, " +
                       t.getArrayDimension() + ", " +
                       s_ensure[order] + ")");
          // d_lw.println("call sidl__array_deleteRef(" + cname + "_tmp)");
        }
      }
      d_lw.println("call cast(" + cname + ", " + name + ")");
      break;
    case Type.OPAQUE:
      d_lw.println(name + "%value = " + cname);
      break;
    case Type.CHAR:
      d_lw.println(name + " = char(" + cname + ")");
      break;
    case Type.STRING:
      d_lw.println("call sidl_copy_c_str(" + name +
                 ", len(" + name + ", c_size_t), " + cname + ")");
      break;
    case Type.STRUCT:
      d_lw.println("call c_f_pointer(" + cname + ", " + name + ")");
      break;
    default:
      if(generateDefaultAssignment)
        d_lw.println(name + " = " + cname);
    }
  }

  /**
   * Emits code to convert the Fortran proxy <code>fname</code> to the
   * eqvivalent bindC variable <code>name</code> of type <code>type</code>.
   *
   * @param intentOut    if <code>true</code>, string variables are
   *                     initialized to <code>c_null_ptr</code> before
   *                     making a copy.
   * @param generateDefaultAssignment   if true, generate a copy assignment,
   *                                    even if no conversion is necessary
   */ 
  public static void printFortran2BindC(Type t,
                                        String fname,
                                        String name,
                                        int mode,
                                        boolean generateDefaultAssignment,
                                        boolean generateEnsure,
                                        LanguageWriterForFortran d_lw) {
    switch(t.getDetailedType()) {
    case Type.BOOLEAN:
       //This is to avoid logical to int conversion warnings
        d_lw.println(name + " = 0");
        d_lw.println("if(" + fname + ") " + name + " = 1");
       break;
    case Type.INTERFACE:
    case Type.CLASS:
      d_lw.println(name + " = " + fname + "%d_ior");
      break;
    case Type.ARRAY:
      if(t.isRarray()) {
        String first_elem = "";
        for(int i=1, n=t.getArrayDimension(); i <= n; ++i) {
          first_elem += "lbound(" + fname + ", " + i + ")";
          if(i < n) first_elem += ", ";
        }
        d_lw.println(getBindCProxyName(name) + " => " + fname + "(" + first_elem + ")");
        d_lw.println(name + " = c_loc(" + getBindCProxyName(name) + ")");
      }
      else {
        if(generateEnsure && t.hasArrayOrderSpec() && mode != Argument.IN) {
          String procname = getEnsureArrayFunction(t.getArrayType());
          d_lw.println(name + "_tmp = " + fname + "%d_array");
          d_lw.println(name + " = " + procname + "(" + name + "_tmp, " +
                       t.getArrayDimension() + ", " +
                       s_ensure[t.getArrayOrder()] + ")");
        }
        else {
          d_lw.println(name + " = " + fname + "%d_array");

        }
      }
      break;
    case Type.OPAQUE:
      d_lw.println(name + " = " + fname + "%value");
      break;
    case Type.CHAR:
      d_lw.println(name + " = ichar(" + fname + ", c_signed_char)");
      break;
    case Type.STRING:
      if(mode == Argument.OUT) d_lw.println(name + " = c_null_ptr");
      d_lw.println("call sidl_f03_copy_fortran_str(" + fname + ", " + name +
                 ", len(" + fname +", c_size_t))");
      break;
    case Type.STRUCT:
      d_lw.println(name + " = c_loc(" + fname + ")");
      break;
    default:
      if(generateDefaultAssignment)
        d_lw.println(name + " = " + fname);
    }
  }

  /**
   * Returns true if the given type requires a proxy variable for Bind(C) bindings
   */
  public static boolean hasBindCProxy(Type t, int mode) {
    switch(t.getDetailedType()) {
    case Type.BOOLEAN:
    case Type.INTERFACE:
    case Type.CLASS:
    case Type.ARRAY:
    case Type.OPAQUE:
    case Type.CHAR:
    case Type.STRUCT:
      return true;
    case Type.STRING:
      return (mode == Argument.INOUT || mode == Argument.OUT);
    }
    return false;
  }
  
  /**
   * Emits a Bind(C) proxy declaration
   */
  public static void printBindCProxyDecl(Type t,
                                         int mode,
                                         String name,
                                         LanguageWriterForFortran d_lw,
                                         Context d_context) {
    switch(t.getDetailedType()) {
    case Type.BOOLEAN:
      d_lw.println("integer(c_int) :: " + name);
      break;
    case Type.INTERFACE:
    case Type.CLASS:
    case Type.OPAQUE:
      d_lw.println("type(c_ptr) :: " + name);
      break;
    case Type.ARRAY:
      d_lw.println("type(c_ptr) :: " + name);
      if(t.isRarray()) {
        try {
          d_lw.println(getArgumentString(t, d_context, false) +
                       ", pointer :: " + getBindCProxyName(name) + " => null()");
        } catch(CodeGenerationException e) { } 
      }
      break;
    case Type.STRING:
      if(mode == Argument.INOUT || mode == Argument.OUT)
        d_lw.println("type(c_ptr) :: " + name);
      break;
    case Type.CHAR:
      d_lw.println("integer(c_signed_char) :: " + name);
      break;
    case Type.STRUCT:
      // d_lw.println("type(c_ptr) :: ");
      try {
        NameMangler non  = new NonMangler();
        d_lw.println("type(" + non.shortName(t.getSymbol().getFullName(), "_t") 
                     + "), target :: " + name);
      }
      catch (java.io.UnsupportedEncodingException e) { }
      break;
    }
  }
  
  /**
   * Emit code to initialize an empty t name
   * No code is generated for basic types
   */
  public static void printInitializeEmpty(Type t, String name,
                                          LanguageWriterForFortran d_lw) {
    switch(t.getDetailedType()) {
    case Type.BOOLEAN:
      d_lw.println(name + " = 0");
      break;
    case Type.INTERFACE:
    case Type.CLASS:
    case Type.ARRAY:
    case Type.OPAQUE:
    case Type.STRING:
      d_lw.println(name + " = c_null_ptr");
      break;
    case Type.CHAR:
      d_lw.println(name + " = 0");
      break;
    case Type.STRUCT:
      Struct strct = (Struct)t.getSymbol();
      for(Iterator i = strct.getItems().iterator(); i.hasNext();) {
        Struct.Item e = (Struct.Item) i.next();
        printInitializeEmpty(e.getType(), name + "%" + e.getName(), d_lw);
      }
      break;
    }
  }


  /**
   * Returns a String that translates among the usual Fortran representation
   * and Bind(C) equivalents. 
   */
  public static String getFortran2BindCExpr(String expr, Type t, int mode) {
    switch(t.getDetailedType()) {
    case Type.ARRAY:
    case Type.INTERFACE:
    case Type.CLASS:
    case Type.BOOLEAN:
    case Type.OPAQUE:
    case Type.CHAR:
      return "bindc_" + expr;
    case Type.STRING:
      if(mode == Argument.IN)
        return "trim(" + expr + ") // c_null_char";
      else
        return "bindc_" + expr;
    case Type.STRUCT:
      return "c_loc(bindc_" + expr + ")";
    }
    return expr;
  }

  
  /**
   * Generates include directives for all the Babel clases used in this
   * fortran stub or skel
   *
   * @param writer Language writer for C
   * @param ext Extendible (Class or Interface) to generate dependencies
   */
  public static Set generateStubIncludes( LanguageWriterForC writer,
                                            Extendable ext)
    throws CodeGenerationException
  {
    Set includes = new HashSet();
    includes.addAll(Utilities.sort(ext.getObjectDependencies()));

    if (!includes.isEmpty()){
      writer.writeComment("Includes for all method dependencies.",false);

      List entries = Utilities.sort(includes);

      for (Iterator i = entries.iterator(); i.hasNext(); ) {
        String header = Fortran.getHeaderFile( (SymbolID) i.next());

        writer.generateInclude( header, true );
      }
    }
    return includes;
  }

  public static Method createRemoteMethod(Extendable ext,
                                            Context context,
                                            boolean isF90) {
    SymbolID id = ext.getSymbolID();
    Method ret = new Method(context);
    Argument url = new Argument(Argument.IN, new Type(Type.STRING), "url");
    Argument self = new Argument(Argument.OUT, new Type(id, context), "self");
    if(isF90) {
      ret.setMethodName("new","Remote");
    } else {
      ret.setMethodName("_create","Remote");
    }
    ret.setDefinitionModifier(Method.STATIC);
    ret.setReturnType(new Type(Type.VOID));
    if (!context.getConfig().getSkipRMI()) {
      ret.addThrows(context.getSymbolTable().
                    lookupSymbol("sidl.rmi.NetworkException").getSymbolID());
    }
    ret.addArgument(self);
    ret.addArgument(url);
    return ret;
  }

  public static Method connectRemoteMethod(Extendable ext,
                                           Context context,
                                           boolean isF90) {
    SymbolID id = ext.getSymbolID();
    Method ret = new Method(context);
    Argument url = new Argument(Argument.IN, new Type(Type.STRING), "url");
    Argument self = new Argument(Argument.OUT, new Type(id, context), "self");
    if(isF90) {
      ret.setMethodName("rConnect");
    } else {
      ret.setMethodName("_connect");
    }
    ret.setDefinitionModifier(Method.STATIC);
    if (!context.getConfig().getSkipRMI()) {
      ret.addThrows(context.getSymbolTable().
                    lookupSymbol("sidl.rmi.NetworkException").getSymbolID());
    }
    ret.addArgument(self);
    ret.setReturnType(new Type(Type.VOID));
    ret.addArgument(url);
    return ret;
  }

}
