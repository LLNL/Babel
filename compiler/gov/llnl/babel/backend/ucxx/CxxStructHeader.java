//
// File:        CxxStructHeader.java
// Package:     gov.llnl.babel.backend.ucxx
// Copyright:   (c) 2007 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 6271 $
// Date:        $Date: 2007-12-14 15:41:25 -0800 (Fri, 14 Dec 2007) $
// Description: 
// 

package gov.llnl.babel.backend.ucxx;

import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.ucxx.Cxx;
import gov.llnl.babel.backend.writers.LanguageWriterForCxx;
import gov.llnl.babel.symbols.Struct;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

public class CxxStructHeader {
  private LanguageWriterForCxx d_writer = null;
  private Struct d_struct = null;
  private Context d_context;

  /**
   * Create an object capable of generating the header file for a
   * BABEL struct.
   *
   * @param str   a struct that needs a header
   *              file for a Cxx stub.
   */
  public CxxStructHeader(Struct str,
                         Context context) {
    d_struct = str;
    d_context = context;
  }
  

  private void writeIncludes()
  {
    Set refs = d_struct.getSymbolReferences();
    Iterator i = refs.iterator();
    d_writer.generateInclude("sidl_cxx.hxx", true);
    while(i.hasNext()) {
      SymbolID id = (SymbolID)i.next();
      d_writer.generateInclude(Cxx.generateFilename(id,
                                                    Cxx.FILE_ROLE_STUB,
                                                    Cxx.FILE_TYPE_CXX_HEADER,
                                                    d_context),
                               true);
    }
    d_writer.printlnUnformatted("#include <string>");
    if (d_struct.hasType(Type.STRING)) {
      d_writer.generateInclude("sidl_String.h", true);
    }
    if (d_struct.hasType(Type.ARRAY)) {
      d_writer.generateInclude("sidlArray.h", true);
    }
    d_writer.generateInclude(IOR.getHeaderFile(d_struct.getSymbolID()), true);
    d_writer.println();
  }

  private void openNamespace()
  {
    StringTokenizer tok = 
      new StringTokenizer(d_struct.getSymbolID().getFullName(),".");
    String prev, next=null;
    while (tok.hasMoreTokens()) {
      prev = next;
      next = tok.nextToken();
      if (prev != null) {
        d_writer.println("namespace " + prev + " {");
        d_writer.tab();
      }
    }
  }

  private void closeNamespace()
  {
    int numClose = 
      (new StringTokenizer(d_struct.getSymbolID().getFullName(),"."))
      .countTokens() - 1;
    while(numClose-- > 0) {
      d_writer.backTab();
      d_writer.println("}");
    }
  }

  private void writeConstructor()
  {
    d_writer.println(d_struct.getSymbolID().getShortName() + "();");
    d_writer.println();
  }

  private void writeDestructor()
  {
    d_writer.println("~" + d_struct.getSymbolID().getShortName() + "();");
    d_writer.println();
  }

  private void writeDestroy()
  {
    d_writer.println("void _destroy();");
    d_writer.println();
  }

  private void writeCopyConstructor()
  {
    final String name = d_struct.getSymbolID().getShortName();
    d_writer.println(name + "(const " + Cxx.getSymbolName(d_struct) +
                     " &src);");
    d_writer.println();
  }

  private void writeAssignment()
  {
    final String name = d_struct.getSymbolID().getShortName();
    d_writer.println(name + "& operator =(const " + 
                     Cxx.getSymbolName(d_struct) + " &rhs);");
    d_writer.println();
  }

  private void writeSerialize()
  {
    d_writer.println("void serialize(::sidl::io::Serializer &pipe,");
    d_writer.tab();
    d_writer.println("const ::std::string &name, const bool copyArg) const;");
    d_writer.backTab();
    d_writer.println();
  }

  private void writeDeserialize()
  {
    d_writer.println("void deserialize(::sidl::io::Deserializer &pipe,");
    d_writer.tab();
    d_writer.println("const ::std::string &name, const bool copyArg);");
    d_writer.backTab();
    d_writer.println();
  }

  private void writeIORCopy()
    throws CodeGenerationException
  {
    final String iorStruct = IOR.getStructName(d_struct.getSymbolID());
    d_writer.beginBlockComment(false);
    d_writer.println("Assume that the dest is uninitialized on entry.");
    d_writer.println("Copy from this object into dest.");
    d_writer.endBlockComment(false);
    d_writer.println("void toIOR(" + iorStruct + " &dest) const;");
    d_writer.println();
  }

  private void writeIORAssignment()
    throws CodeGenerationException
  {
    final String iorStruct = IOR.getStructName(d_struct.getSymbolID());
    d_writer.beginBlockComment(false);
    d_writer.println("Assume that the object takes ownership of all");
    d_writer.println("data and references previous owned by the rhs.");
    d_writer.endBlockComment(false);
    d_writer.println(Cxx.getSymbolName(d_struct)
                     + "& operator =(const " + iorStruct + " &rhs);");
    d_writer.println();
  }

  static String typeString(final Type t,
                           Context context)
    throws CodeGenerationException
  {
    switch(t.getDetailedType()) {
    case Type.BOOLEAN:
      return "sidl_bool";
    case Type.STRING:
      return "char *";
    case Type.STRUCT:
      return Cxx.getSymbolName(t.getSymbolID());
    case Type.INTERFACE:
    case Type.CLASS:
      return Cxx.getSymbolName(t.getSymbolID()) + "::ior_t *";
    case Type.ARRAY:
        return Cxx.getCxxString(t, false, context) + "::ior_array_t *";
    default:
      return Cxx.getCxxString(t, false, context);
    }
  }

  private void writeBooleanHelper(Struct.Item item)
  {
    final String name = item.getName();
    d_writer.println("bool get_" + name + "() const { return " + name +
                     " == TRUE; }");
    d_writer.println();
    d_writer.println("void set_" + name + "(bool val) { " + name + " = (val ? TRUE : FALSE); }");
    d_writer.println();
  }

  private void writeStringHelper(Struct.Item item)
  {
    final String name = item.getName();
    d_writer.println("::std::string get_" + name + "() const { return (const char *)" + name +
                     "; }");
    d_writer.println();
    d_writer.println("void set_" + name + "(const ::std::string &val) {");
    d_writer.tab();
    d_writer.println("sidl_String_free(" + name + ");");
    d_writer.println(name + " = sidl_String_strdup(val.c_str());");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  private void writeExtendableHelper(Struct.Item item)
    throws CodeGenerationException
  {
    final Type t = item.getType();
    final String name = item.getName();
    final String cxxType = Cxx.getReturnString(t, d_context);
    d_writer.println(cxxType + " get_" + name + "() const { return " + cxxType + 
                     "(" + name + "); }");
    d_writer.println();
    d_writer.println("void set_" + name + "(const " + cxxType + " &val) {");
    d_writer.tab();
    d_writer.println("struct sidl_BaseInterface__object *throwaway;");
    d_writer.println("if (" + name + ") (" + name + "->d_epv->f_deleteRef)("
                     + name + 
                     ((Type.INTERFACE == t.getDetailedType()) 
                      ? "->d_object, &throwaway);" 
                      : ", &throwaway);"));
    d_writer.println(name + " = val._get_ior();");
    d_writer.println("if (" + name + ") (" + name + "->d_epv->f_addRef)("
                     + name + 
                     ((Type.INTERFACE == t.getDetailedType()) 
                      ? "->d_object, &throwaway);" 
                      : ", &throwaway);"));
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  private void writeRarrayHelper(Struct.Item item)
    throws CodeGenerationException
  {
    final Type t = item.getType();
    final String name = item.getName();
    final String cxxType = Cxx.getReturnString(t, d_context);
    //NOP for now--SPM
  }

  private void writeArrayHelper(Struct.Item item)
    throws CodeGenerationException
  {
    final Type t = item.getType();
    final String name = item.getName();
    final String cxxType = Cxx.getReturnString(t, d_context);
    d_writer.println(cxxType + " get_" + name + "() const {");
    d_writer.tab();
    d_writer.println(cxxType + "_result(" + name + ");");
    d_writer.println("_result.addRef();");
    d_writer.println("return _result;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    d_writer.println("void set_" + name + "(const " + cxxType + " &val) {");
    d_writer.tab();
    d_writer.println("sidl__array_deleteRef((struct sidl__array*)" +
                     name + ");");
    d_writer.println(name + " = (" + typeString(t, d_context) +
                     ")val._get_baseior();");
    d_writer.println("sidl__array_addRef((struct sidl__array*)" +
                     name + ");");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  private void writeEnumHelper(Struct.Item item)
    throws CodeGenerationException
  {
    final Type t = item.getType();
    final String name = item.getName();
    final String cxxType = Cxx.getReturnString(t, d_context);
    d_writer.println(cxxType + " get_" + name + "() const { return (" +
                     typeString(t, d_context) +
                     ")" + name + "; }");
    d_writer.println();
    d_writer.println("void set_" + name + "(const " + cxxType + " &val) {");
    d_writer.tab();
    d_writer.println(name + " = (" + IOR.getReturnString(t, d_context) +
                     ")val;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  private void writeStructHelper(Struct.Item item)
    throws CodeGenerationException
  {
    final Type t = item.getType();
    final String name = item.getName();
    final String cxxType = Cxx.getReturnString(t, d_context);
    d_writer.println(cxxType + "& get_" + name + "() {");
    d_writer.tab();
    d_writer.println("return *reinterpret_cast< " +
                     cxxType + " * >(&" + name  + ");");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    d_writer.println("const " + cxxType + "& get_" + name + "() const {");
    d_writer.tab();
    d_writer.println("return *reinterpret_cast< const " +
                     cxxType + " * >(&" + name  + ");");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
    d_writer.println("void set_" + name + "(const " + cxxType + " &val) {");
    d_writer.tab();
    d_writer.println("*reinterpret_cast< " +
                     cxxType + " * >(&" + name + ") = val;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  private void writeForwardDecls() {
    d_writer.println("namespace sidl {");
    d_writer.tab();
    d_writer.println("namespace io {");
    d_writer.tab();
    d_writer.println("class Serializer;");
    d_writer.println("class Deserializer;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.backTab();
    d_writer.println("}");
  }

  private void writeHelper(Struct.Item item)
    throws CodeGenerationException
  {
    switch(item.getType().getDetailedType()) {
    case Type.BOOLEAN:
      writeBooleanHelper(item);
      break;
    case Type.STRING:
      writeStringHelper(item);
      break;
    case Type.CLASS:
    case Type.INTERFACE:
      writeExtendableHelper(item);
      break;
    case Type.STRUCT:
      writeStructHelper(item);
      break;
    case Type.ARRAY:
      if (item.getType().isRarray()){
        writeRarrayHelper(item);
      } else {
        writeArrayHelper(item);
      }
      break;
    case Type.ENUM:
      writeEnumHelper(item);
      break;
    }
  }

  private void writeHelperFunctions()
    throws CodeGenerationException
  {
    if (!(d_struct.getItems().isEmpty())) {
      if (d_struct.hasTypeEmbedded(Type.STRING) ||
          d_struct.hasTypeEmbedded(Type.INTERFACE) ||
          d_struct.hasTypeEmbedded(Type.CLASS) ||
          d_struct.hasTypeEmbedded(Type.ARRAY) ||
          d_struct.hasTypeEmbedded(Type.OPAQUE)) {
        writeConstructor();
        writeDestructor();
        writeCopyConstructor();
        writeAssignment();
      }
      writeDestroy();
      Iterator i = d_struct.getItems().iterator();
      while(i.hasNext()) {
        writeHelper((Struct.Item)i.next());
      }
    }
    writeIORAssignment();
    writeIORCopy();
    writeSerialize();
    writeDeserialize();
  }

  public synchronized void generateCode()
    throws CodeGenerationException
  {
    String filename = Cxx.generateFilename(d_struct.getSymbolID(),
                                           Cxx.FILE_ROLE_STUB,
                                           Cxx.FILE_TYPE_CXX_HEADER, 
                                           d_context );
    try {
      d_writer = Cxx.createHeader(d_struct, Cxx.FILE_ROLE_STUB, "STUBHDRS",
                                  d_context);
      d_writer.println();
      d_writer.openHeaderGuard(filename);

      writeIncludes();
      writeForwardDecls();
      openNamespace();
      d_writer.writeComment(d_struct, true);
      d_writer.println("struct " + d_struct.getSymbolID().getShortName() 
                       + " : " + IOR.getSymbolName(d_struct)
                       + "__data {");
      d_writer.tab();
      writeHelperFunctions();
      d_writer.backTab();
      d_writer.println("};");
      closeNamespace();
      d_writer.closeHeaderGuard();
    }
    finally {
      if (d_writer != null) {
        d_writer.close();
        d_writer = null;
      }
    }
  }
}
