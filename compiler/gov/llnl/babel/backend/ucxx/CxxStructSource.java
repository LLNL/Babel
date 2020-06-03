//
// File:        CxxStructSource.java
// Package:     gov.llnl.babel.backend.ucxx
// Copyright:   (c) 2007 Lawrence Livermore National Security, LLC
// Release:     $Name$
// Revision:    @(#) $Revision: 7421 $
// Date:        $Date: 2011-12-15 17:06:06 -0800 (Thu, 15 Dec 2011) $
// Description: 
// 

package gov.llnl.babel.backend.ucxx;
import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.fortran.ImplSource;
import gov.llnl.babel.backend.c.C;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.rmi.RMI;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.writers.LanguageWriterForCxx;
import gov.llnl.babel.symbols.AssertionExpression;
import gov.llnl.babel.symbols.Struct;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.Type;
import gov.llnl.babel.symbols.Symbol;
import java.util.Collection;
import java.util.Iterator;

public class CxxStructSource {
  private LanguageWriterForCxx d_writer = null;
  private Struct d_struct = null;
  private Context d_context;

  public CxxStructSource (Struct str,
                          Context context) {
    d_struct = str;
    d_context = context;
  }

  private void initialize(Collection structItems,
                          String prefix)
    throws CodeGenerationException
  {
    Iterator i = structItems.iterator();
    while(i.hasNext()) {
      final Struct.Item item = (Struct.Item)i.next();
      final Type t = item.getType();
      switch(t.getDetailedType()) {
      case Type.STRING:
      case Type.INTERFACE:
      case Type.CLASS:
      case Type.OPAQUE:
        d_writer.println(prefix + item.getName() + " = 0;");
        break;
      case Type.ARRAY:
        if (!t.isRarray()){
          d_writer.println(prefix + item.getName() + " = 0;");
        } else {
          Iterator indexVar = t.getArrayIndexExprs().iterator();
          AssertionExpression ae = (AssertionExpression)indexVar.next();
          String checkInt = ae.toString();
          if (!ImplSource.isInt(checkInt)){
            d_writer.println(prefix + item.getName() + " = 0;");
          }
        }
        break;
      case Type.STRUCT:
        Struct embedded = (Struct)Utilities.lookupSymbol(d_context, 
                                                         t.getSymbolID());
        initialize(embedded.getItems(), prefix + item.getName() + ".");
        break;
      }
    }
  }

  private void copyItems(Collection items,
                         String source,
                         String dest)
    throws CodeGenerationException
  {
    Iterator i = items.iterator();
    while(i.hasNext()) {
      final Struct.Item item = (Struct.Item)i.next();
      final Type t = item.getType();
      final String iname = item.getName();
      switch(t.getDetailedType()) {
      case Type.STRING:
        d_writer.println(dest + iname + " = sidl_String_strdup(" + source +
                         iname + ");");
                         
        break;
      case Type.INTERFACE:
      case Type.CLASS:
        d_writer.println(dest + iname + " = " + source + iname + ";");
        d_writer.println("if (" + dest + iname + ") {");
        d_writer.tab();
        d_writer.println("(" + dest + iname + "->d_epv->f_addRef)(" 
                         + dest + iname + 
                         ((Type.INTERFACE == t.getDetailedType() ) 
                          ? "->d_object, &throwaway);"
                          : ", &throwaway);"));
        d_writer.backTab();
        d_writer.println("}");
        break;
      case Type.ARRAY:
        if (!t.isRarray()){
          d_writer.println(dest + iname + " = (" + 
                          CxxStructHeader.typeString(t, d_context) +
                          ")sidl__array_smartCopy((struct sidl__array *)(" +
                          source + iname + "));");
        } else {
          Iterator indexVar = t.getArrayIndexExprs().iterator();
          AssertionExpression ae = (AssertionExpression)indexVar.next();
          String checkInt = ae.toString();
          if (!ImplSource.isInt(checkInt)){
            d_writer.println(dest + iname + " = " + source + iname + ";");
          }
        }
        break;
      case Type.STRUCT:
        Struct s = (Struct)Utilities.lookupSymbol(d_context,
                                                  t.getSymbolID());
        copyItems(s.getItems(), source + iname + ".",
                  dest + iname + ".");
        break;
      default:
        d_writer.println(dest + iname + " = " + source + iname + ";");
        break;
      }
    }
    
  }

  private void writeConstructor()
    throws CodeGenerationException
  {
    final SymbolID id = d_struct.getSymbolID();
    d_writer.println(Cxx.getSymbolNameWithoutLeadingColons(id, null) + "::" +
                     d_struct.getSymbolID().getShortName() + "() {");
    d_writer.tab();
    initialize(d_struct.getItems(), "");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }


  void writeDestroyStmts(Collection structItems)
    throws CodeGenerationException
  {
    Iterator i = structItems.iterator();
    
    while(i.hasNext()) {
      final Struct.Item item = (Struct.Item)i.next();
      final Type t = item.getType();
      final String iname = item.getName();
      switch(t.getDetailedType()) {
      case Type.STRING:
        d_writer.println("sidl_String_free(" + iname + ");");
        d_writer.println(iname + " = 0;");
        break;
      case Type.INTERFACE:
      case Type.CLASS:
        d_writer.println("if (" + iname + ") {");
        d_writer.tab();
        d_writer.println("(" + iname + "->d_epv->f_deleteRef)(" + iname + 
                         ((Type.INTERFACE == t.getDetailedType() ) 
                          ? "->d_object, &throwaway);"
                          : ", &throwaway);"));
        d_writer.println(iname + " = 0;");
        d_writer.backTab();
        d_writer.println("}");
        break;
      case Type.ARRAY:
        if (!t.isRarray()){
          d_writer.println("sidl__array_deleteRef((struct sidl__array *)" +
                          iname + ");");
          d_writer.println(iname + " = 0;");
        } else {
          Iterator indexVar = t.getArrayIndexExprs().iterator();
          AssertionExpression ae = (AssertionExpression)indexVar.next();
          String checkInt = ae.toString();
          if (!ImplSource.isInt(checkInt)){
            d_writer.println("// "+iname+" skipped since user will manage");
          } else {
            d_writer.println("// "+iname+" skipped since it is static");
          }
        }
        break;
      case Type.STRUCT:
        d_writer.println("((" +
                         Cxx.getReturnString(t, d_context) + "*)&" + iname + ")->_destroy();");
        break;
      }
    }
  }

  private void writeDestructor()
  {
    final SymbolID id = d_struct.getSymbolID();
    d_writer.println(Cxx.getSymbolNameWithoutLeadingColons(id, null) +
                     "::~" + d_struct.getSymbolID().getShortName() + "() {");
    d_writer.tab();
    d_writer.println("_destroy();");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  private void writeDestroy()
    throws CodeGenerationException
  {
    final SymbolID id = d_struct.getSymbolID();
    d_writer.println("void");
    d_writer.println(Cxx.getSymbolNameWithoutLeadingColons(id, null) + 
                     "::_destroy() {");
    d_writer.tab();
    if (d_struct.hasType(Type.INTERFACE) || 
        d_struct.hasType(Type.CLASS)) {
      d_writer.println("struct sidl_BaseInterface__object *throwaway;");
    }
    writeDestroyStmts(d_struct.getItems());
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  private void writeIORCopy()
    throws CodeGenerationException
  {
    final String iorStruct = IOR.getStructName(d_struct.getSymbolID());
    Iterator i = d_struct.getItems().iterator();
    d_writer.beginBlockComment(false);
    d_writer.println("Assume that the dest is uninitialized on entry.");
    d_writer.println("Copy from this object into dest.");
    d_writer.endBlockComment(false);
    d_writer.println("void");
    d_writer.println(Cxx.getSymbolNameWithoutLeadingColons(d_struct, null) +
                     "::toIOR(" + iorStruct + " &dest) const {");
    d_writer.tab();
    if (d_struct.hasType(Type.INTERFACE) || 
        d_struct.hasType(Type.CLASS)) {
      d_writer.println("struct sidl_BaseInterface__object *throwaway;");
    }
    i = d_struct.getItems().iterator();
    while(i.hasNext()) {
      final Struct.Item item = (Struct.Item)i.next();
      final Type t = item.getType();
      final String iname = item.getName();
      switch(t.getDetailedType()) {
      case Type.STRING:
        d_writer.println("dest." + iname + " = sidl_String_strdup(" + iname +
                         ");");
        break;
      case Type.INTERFACE:
      case Type.CLASS:
        d_writer.println("dest." + iname + " = " + iname + ";");
        d_writer.println("if (dest." + iname + ") {");
        d_writer.tab();
        d_writer.println("(dest." + iname + "->d_epv->f_addRef)(dest." + 
                         iname + 
                         ((Type.INTERFACE == t.getDetailedType() ) 
                          ? "->d_object, &throwaway);"
                          : ", &throwaway);"));
        d_writer.backTab();
        d_writer.println("}");
        break;
      case Type.ARRAY:
        if (!t.isRarray()){
          d_writer.println("dest." + iname + 
                          " = (" + IOR.getReturnString(t, d_context) + 
                          ")sidl__array_smartCopy((struct sidl__array *)" + 
                          iname + ");");
        } else {
          Iterator indexVar = t.getArrayIndexExprs().iterator();
          AssertionExpression ae = (AssertionExpression)indexVar.next();
          String checkInt = ae.toString();
          if (!ImplSource.isInt(checkInt)){
            d_writer.println("dest." + iname + " = " + iname + ";");
          }
        }
        break;
      case Type.STRUCT:
        d_writer.println("reinterpret_cast< const " + Cxx.getReturnString(t, d_context) + " * > (&" +
                         iname + ")->toIOR(dest." + iname + ");");
        break;
      default:
        d_writer.println("dest." + iname + " = " + iname + ";");
        break;
      }
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  private void writeIORAssignment()
    throws CodeGenerationException
  {
    final String iorStruct = IOR.getStructName(d_struct.getSymbolID());
    Iterator i = d_struct.getItems().iterator();
    d_writer.beginBlockComment(false);
    d_writer.println("Assume that the object takes ownership of all");
    d_writer.println("data and references previous owned by the rhs.");
    d_writer.endBlockComment(false);
    d_writer.println(Cxx.getSymbolName(d_struct) + 
                     "&");
    d_writer.println(Cxx.getSymbolNameWithoutLeadingColons(d_struct, null) +
                     "::operator =(const " + iorStruct + " &rhs)");
    d_writer.println("{");
    d_writer.tab();
    if (d_struct.hasType(Type.INTERFACE) || 
        d_struct.hasType(Type.CLASS)) {
      d_writer.println("struct sidl_BaseInterface__object *throwaway;");
    }
    i = d_struct.getItems().iterator();
    while(i.hasNext()) {
      final Struct.Item item = (Struct.Item)i.next();
      final Type t = item.getType();
      final String iname = item.getName();
      switch(t.getDetailedType()) {
      case Type.STRING:
        d_writer.println("sidl_String_free(" + iname + ");");
        d_writer.println(iname + " = rhs." + iname + ";");
                         
        break;
      case Type.INTERFACE:
      case Type.CLASS:
        d_writer.println("if (" + iname + ") {");
        d_writer.tab();
        d_writer.println("(" + iname + "->d_epv->f_deleteRef)(" + iname + 
                         ((Type.INTERFACE == t.getDetailedType() ) 
                          ? "->d_object, &throwaway);"
                          : ", &throwaway);"));
        d_writer.backTab();
        d_writer.println("}");
        d_writer.println(iname + " = rhs." + iname + "; // DO NOT ADD REF");
        break;
      case Type.ARRAY:
          if (!t.isRarray()){
            d_writer.println("sidl__array_deleteRef((struct sidl__array *)("
                            + iname + "));");
            d_writer.println(iname + " = rhs." + iname + ";");
          } else {
            Iterator indexVar = t.getArrayIndexExprs().iterator();
            AssertionExpression ae = (AssertionExpression)indexVar.next();
            String checkInt = ae.toString();
            if (!ImplSource.isInt(checkInt)){
              d_writer.println("sidl__array_deleteRef((struct sidl__array *)("
                              + iname + "));");
              d_writer.println(iname + " = rhs." + iname + ";");
            }
          }
        break;
      case Type.STRUCT:
        d_writer.println("*reinterpret_cast< " + Cxx.getReturnString(t, d_context) + " * > (&" +
                         iname + ") = rhs." +
                         iname + ";");
        break;
      default:
        d_writer.println(iname + " = rhs." + iname + ";");
        break;
      }
    }
    d_writer.println("return *this;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  private void writeCopyConstructor()
    throws CodeGenerationException
  {
    final SymbolID id = d_struct.getSymbolID();
    final String name = id.getShortName();
    d_writer.println(Cxx.getSymbolNameWithoutLeadingColons(id, null) + "::" +
                     name + "(const " + Cxx.getSymbolName(d_struct) + 
                     " &src)");
    d_writer.println("{");
    d_writer.tab();
    if (d_struct.hasTypeEmbedded(Type.INTERFACE) || 
        d_struct.hasTypeEmbedded(Type.CLASS)) {
      d_writer.println("struct sidl_BaseInterface__object *throwaway;");
    }
    copyItems(d_struct.getItems(), "src.", "");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  private void writeAssignment()
    throws CodeGenerationException
  {
    final SymbolID id = d_struct.getSymbolID();
    Iterator i = d_struct.getItems().iterator();
    d_writer.println(Cxx.getSymbolName(id) + "& " +
                     Cxx.getSymbolNameWithoutLeadingColons(id, null) + 
                     "::operator =(const " + Cxx.getSymbolName(id) + 
                     " &rhs) {");
    d_writer.tab();
    if (d_struct.hasType(Type.INTERFACE) || 
        d_struct.hasType(Type.CLASS)) {
      d_writer.println("struct sidl_BaseInterface__object *throwaway;");
    }
    d_writer.println("if (this != &rhs) {");
    d_writer.tab();
    i = d_struct.getItems().iterator();
    while(i.hasNext()) {
      final Struct.Item item = (Struct.Item)i.next();
      final Type t = item.getType();
      final String iname = item.getName();
      switch(t.getDetailedType()) {
      case Type.STRING:
        d_writer.println("sidl_String_free(" + iname + ");");
        d_writer.println(iname + " = sidl_String_strdup(rhs." +
                         iname + ");");
                         
        break;
      case Type.INTERFACE:
      case Type.CLASS:
        d_writer.println("if (" + iname + ") {");
        d_writer.tab();
        d_writer.println("(" + iname + "->d_epv->f_deleteRef)(" + iname + 
                         ((Type.INTERFACE == t.getDetailedType() ) 
                          ? "->d_object, &throwaway);"
                          : ", &throwaway);"));
        d_writer.backTab();
        d_writer.println("}");
        d_writer.println(iname + " = rhs." + iname + ";");
        d_writer.println("if (" + iname + ") {");
        d_writer.tab();
        d_writer.println("(" + iname + "->d_epv->f_addRef)(" + iname + 
                         ((Type.INTERFACE == t.getDetailedType() ) 
                          ? "->d_object, &throwaway);"
                          : ", &throwaway);"));
        d_writer.backTab();
        d_writer.println("}");
        break;
      case Type.ARRAY:
        if (!t.isRarray()){
          d_writer.println("sidl__array_deleteRef((struct sidl__array *)("
                         + iname + "));");
          d_writer.println(iname + " = (" + 
                          CxxStructHeader.typeString(t, d_context) +
                          ")sidl__array_smartCopy((struct sidl__array *)(rhs."
                          + iname + "));");
        } else {
          Iterator indexVar = t.getArrayIndexExprs().iterator();
          AssertionExpression ae = (AssertionExpression)indexVar.next();
          String checkInt = ae.toString();
          if (!ImplSource.isInt(checkInt)){
            d_writer.println("sidl__array_deleteRef((struct sidl__array *)("
                          + iname + "));");
            d_writer.println(iname + " = " + "rhs." + iname + ";");
          }
        }
        break;
      case Type.STRUCT:
        d_writer.println("*reinterpret_cast< " +
                         Cxx.getReturnString(t, d_context) + " * >(&" +
                         iname + ") = *reinterpret_cast< const " +
                         Cxx.getReturnString(t, d_context) + " * >(&(rhs." + iname + "));");
        break;
      default:
        d_writer.println(iname + " = rhs." + iname + ";");
        break;
      }
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println("return *this;");
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  private void writeIncludes()
  {
    final SymbolID id = d_struct.getSymbolID();
    d_writer.generateInclude(Cxx.generateFilename(id,
                                                  Cxx.FILE_ROLE_STUB,
                                                  Cxx.FILE_TYPE_CXX_HEADER,
                                                  d_context),
                             false);
    if (!d_context.getConfig().getSkipRMI()) {
      d_writer.generateInclude("sidl_io_Serializer.hxx", true);
      d_writer.generateInclude("sidl_io_Deserializer.hxx", true);
    }
    d_writer.generateInclude("sidl_CastException.hxx", true);
    d_writer.generateInclude(Cxx.generateFilename(BabelConfiguration.getNotImplemented(),
                                                  Cxx.FILE_ROLE_STUB, 
                                                  Cxx.FILE_TYPE_CXX_HEADER), true);
    
    d_writer.printlnUnformatted("#include <string>");
    d_writer.println();
  }

  private void writeSerialize(boolean serialize)
    throws CodeGenerationException
  {
    final SymbolID id = d_struct.getSymbolID();
    d_writer.println("void" );
    d_writer.println(Cxx.getSymbolNameWithoutLeadingColons(id, null) +
                     "::" + (serialize ? "" : "de") +
                     "serialize(");
    d_writer.tab();
    d_writer.println("::sidl::io::"
                     + (serialize ? "S" : "Des") +
                     "erializer &pipe, ");
    d_writer.println("const ::std::string &name,");
    d_writer.println("const bool copyArg)" +
                     (serialize ? " const" : ""));
    d_writer.backTab();
    d_writer.println("{");
    d_writer.tab();
    if (!d_struct.getItems().isEmpty()) {
      d_writer.println("const ::std::string prefix = name + \".\";");
      d_writer.println("::std::string combined;");
    }
    Iterator i = d_struct.getItems().iterator();
    while(i.hasNext()) {
      final Struct.Item item = (Struct.Item)i.next();
      final Type t = item.getType();
      d_writer.println("combined = prefix + \"" +
                       item.getName() + "\";");
      switch(t.getDetailedType()) {
      case Type.BOOLEAN:
        d_writer.println("bool _proxy" + item.getName() + 
                         (serialize ? (" = " + item.getName()) : "") + ";");
        d_writer.println("pipe." + (serialize ? "" : "un") +
                         "packBool(combined, _proxy" + item.getName() + ");");
        if (!serialize) {
          d_writer.println(item.getName() + " = _proxy" + item.getName() + 
                           ";");
        }
        break;
      case Type.ENUM:
        d_writer.println("int64_t _proxy" + item.getName() + 
                         (serialize ? (" = " + item.getName()) : "")+ ";");
        d_writer.println("pipe." + (serialize ? "" : "un") +
                         "packLong(combined, _proxy" + item.getName() + ");");
        if (!serialize) {
          d_writer.println(item.getName() + " = _proxy" + item.getName() + 
                           ";");
        }
        break;
      case Type.DCOMPLEX:
      case Type.FCOMPLEX:
        d_writer.println(Cxx.getReturnString(t, d_context) + " _proxy" +
                         item.getName() + 
                         (serialize 
                          ? ("(" +
                             item.getName() + ".real, " +
                             item.getName() + ".imaginary);")
                          : ";"));
        d_writer.println("pipe." + (serialize ? "" : "un") +
                         "pack" +
                         Utilities.
                         capitalize(Type.getTypeName(t.getDetailedType())) +
                         "(combined, _proxy" + item.getName() + ");");
        if (!serialize) {
          d_writer.println(item.getName() + ".real = _proxy" +
                           item.getName() + ".real();");
          d_writer.println(item.getName() + ".imaginary = _proxy" +
                           item.getName() + ".imag();");
        }
        break;
      case Type.CHAR:
      case Type.DOUBLE:
      case Type.FLOAT:
      case Type.INT:
      case Type.LONG:
      case Type.OPAQUE:
        d_writer.println("pipe." + (serialize ? "" : "un") +
                         "pack" +
                         Utilities.
                         capitalize(Type.getTypeName(t.getDetailedType())) +
                         "(combined, " + item.getName() + ");");
        break;
      case Type.STRING:
        if (serialize) {
          d_writer.println("pipe.packString(combined, " + item.getName() +
                           " == NULL ? \"\" : " + item.getName() + ");");
        }
        else {
          d_writer.println("::std::string _proxy" + item.getName() + ";");
          d_writer.println("pipe.unpackString(combined, _proxy" +
                           item.getName() + ");");
          d_writer.println("set_" + item.getName() + "(_proxy" +
                           item.getName() + ");");
        }
        break;
      case Type.ARRAY:
        if(!RMI.isSerializable(t, d_context)) {
          d_writer.println("{");
          d_writer.tab();
          if(!serialize) d_writer.println("set_" + item.getName() + "(NULL);");
          d_writer.pushLineBreak(false);
          d_writer.println("sidl::NotImplementedException _nyi = ::sidl::NotImplementedException::_create();");
          d_writer.println("_nyi.setNote(\"Cannot serialize struct " + d_struct.getSymbolID().getShortName()
                           + " due to nonserializable field " + item.getName() + ".\");");
          d_writer.println("throw _nyi;");
          d_writer.backTab();
          d_writer.println("}");
          d_writer.popLineBreak();
        }
        else {
          String var_prefix = "";
          if(t.getArrayType() != null) {
            if(t.getArrayType().getDetailedType() == Symbol.ENUM) {
              var_prefix = "(" + Cxx.getReturnString(new Type(Type.LONG), d_context) + ")";
            }
          }

          d_writer.println("{");
          d_writer.tab();

          String array_arg = var_prefix + item.getName();
          if(t.isRarray()) {
            array_arg = C.wrapRawArrayFromStruct(d_writer,
                                                 item.getName(),
                                                 "this",
                                                 t,
                                                 d_context);
          }
          
          if(serialize) {
            d_writer.println("pipe.pack" + RMI.getMethodExtension(t) + "(combined, " +
                             array_arg + ", " +
                             BabelConfiguration.getArrayOrderName(t.getArrayOrder()) +
                             "," + t.getArrayDimension() + ", 0" + ");"); 
          }
          else {
            d_writer.println(Cxx.getCxxString(t, false, d_context) + " _proxy" + item.getName() + ";");
            d_writer.println("pipe.unpack" + RMI.getMethodExtension(t) + "(combined, " +
                             "_proxy" + item.getName()  + ", " +
                             BabelConfiguration.getArrayOrderName(t.getArrayOrder()) +
                             ", " + t.getArrayDimension() + ", " + 
                             (t.isRarray() ? "TRUE" : "FALSE") + ");");
            if(t.isRarray()) {
              d_writer.writeComment("TODO: allocate memory (for variable sized arrays) and do a memcpy here", true);
              //d_writer.println("this->" + item.getName() + " = " + array_arg + "->d_firstElement;");
            }
            else
              d_writer.println("set_" + item.getName() + "(_proxy" + item.getName() + ");");
          }
          
          d_writer.backTab();
          d_writer.println("}");
        }
        break;
      case Type.INTERFACE:
      case Type.CLASS:
        d_writer.println("if (copyArg) {");
        d_writer.tab();
        d_writer.println("::sidl::io::Serializable _serial;");
        if (serialize) {
          d_writer.println("if (" + item.getName() + ") {");
          d_writer.tab();
          d_writer.println("struct sidl_BaseInterface__object *throwaway;");
          d_writer.println("::sidl::io::Serializable::ior_t *sior =");
          d_writer.tab();
          d_writer.println("(::sidl::io::Serializable::ior_t *)");
          d_writer.println("((*" + item.getName() + "->d_epv->f__cast)(" +
                           item.getName() + 
                           ((t.getDetailedType() == Type.INTERFACE) ?
                            "->d_object" : "") + 
                           ", \"" + t.getTypeString() + 
                           "\", &throwaway));");
          d_writer.backTab();
          d_writer.println("if (0 == sior) {");
          d_writer.tab();
          d_writer.println("::sidl::CastException ce = ::sidl::CastException::_create();");
          try {
            d_writer.pushLineBreak(false);
            d_writer.println("ce.setNote(\"struct item " +
                             item.getName() +
                             " cannot be cast to a sidl.io.Serializable.\");");
          }
          finally {
            d_writer.popLineBreak();
          }
          d_writer.println("throw ce;");
          d_writer.backTab();
          d_writer.println("}");
          d_writer.println("_serial._set_ior(sior);");
          d_writer.backTab();
          d_writer.println("}");
        }
        d_writer.println("pipe." + (serialize ? "" : "un") +
                         "packSerializable(combined, _serial);");
        if (!serialize) {
          d_writer.println("{");
          d_writer.tab();
          d_writer.println(Cxx.getReturnString(t, d_context) + 
                           " _proxy =");
          d_writer.tab();
          d_writer.println("::sidl::babel_cast< " +
                           Cxx.getReturnString(t, d_context) 
                           + " >(_serial);");
          d_writer.backTab();
          d_writer.println("set_" + item.getName() + "(_proxy);");
          d_writer.backTab();
          d_writer.println("}");
        }
        d_writer.backTab();
        d_writer.println("}");
        d_writer.println("else {");
        d_writer.tab();
        if (serialize) {
          d_writer.println("struct sidl_BaseInterface__object *throwaway;");
          d_writer.println("char *url = " +
                           item.getName() + " ? NULL :");
          d_writer.tab();
          d_writer.println("((*" + item.getName() + "->d_epv->f__getURL)(" +
                           item.getName() + 
                           ((t.getDetailedType() == Type.INTERFACE) ?
                            "->d_object" : "") + 
                           ", &throwaway));");
          d_writer.backTab();
          d_writer.println("pipe.packString(combined, url ? url : \"\");");
          d_writer.println("if (url) free(url);");
        }
        else {
          d_writer.println("::std::string url;");
          d_writer.println("pipe.unpackString(combined, url);");
          d_writer.printlnUnformatted("#ifdef WITH_RMI");
          
          d_writer.println("set_" + item.getName() + "((url.length() > 0) ? " +
                           Cxx.getReturnString(t, d_context) + 
                           "::_connect(url) : " +
                           Cxx.getReturnString(t, d_context) + "());");
          d_writer.printlnUnformatted("#else");
          d_writer.println("set_" + item.getName() + "(" +
                           Cxx.getReturnString(t, d_context) + "());");
          d_writer.printlnUnformatted("#endif /* WITH_RMI */");
        }
        d_writer.backTab();
        d_writer.println("}");
        break;
      case Type.STRUCT:
        d_writer.println("reinterpret_cast< " +
                         (serialize ? "const " : "") +
                         Cxx.getReturnString(t, d_context) + " * >(&" +
                         item.getName() + ")->" + 
                         (serialize ? "" : "de") +
                         "serialize(pipe, combined, copyArg);");
        break;
      default:
        throw new CodeGenerationException("Unexpected type");
      }
    }
    d_writer.backTab();
    d_writer.println("}");
    d_writer.println();
  }

  public synchronized void generateCode()
    throws CodeGenerationException
  {
    try {
      d_writer = Cxx.createSource(d_struct, Cxx.FILE_ROLE_STUB, "STUBSRCS",
                                  d_context );
      d_writer.println();

      writeIncludes();
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
      }
      writeIORAssignment();
      writeIORCopy();
      if (!d_context.getConfig().getSkipRMI()) {
        writeSerialize(true);
        writeSerialize(false);
      }
    }
    finally {
      if (d_writer != null) {
        d_writer.close();
        d_writer = null;
      }
    }
  }
}
