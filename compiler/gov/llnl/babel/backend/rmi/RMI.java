//
// File:        RMI.java
// Package:     gov.llnl.babel.backend.rmi
// Copyright:   (c) 2004 The Lawrence Livermore National Security, LLC
// Revision:     @(#) $Id: RMI.java 7188 2011-09-27 18:38:42Z adrian $
// Description: 
// Copyright (c) 2000-2001, Lawrence Livermore National Security, LLC
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

package gov.llnl.babel.backend.rmi;

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.c.C;
import gov.llnl.babel.backend.jdk.JavaStructSource;
import gov.llnl.babel.backend.writers.LanguageWriterForC;
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.Extendable;
import gov.llnl.babel.symbols.Struct;
import gov.llnl.babel.symbols.Symbol;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolTable;
import gov.llnl.babel.symbols.Type;

import java.util.Iterator;

public class RMI { 

  //Sharthands for commonly used names
  
  static final String BIName = BabelConfiguration.getBaseInterface();
  static final String SerialName = BabelConfiguration.getSerializableType();

  /** generate the method call to pack an argument
   * @param lw              The language writer to write to
   * @param packer_typename The packer type (often sidl_rmi_Serializer)
   * @param packer_varname  The name of the packer object
   * @param arg             The Argument itself
   * @param reuseable       True if (when the argument is an array) 
   *                         babel should attempt to copy the new data into
   *                         the old array.
   *
   */
  public static void packArg( LanguageWriterForC lw , 
                              Context context,
                              String packer_typename, 
                              String packer_varname, 
                              Argument arg,
                              boolean reuseable)
  { 
    packType( lw, context, packer_typename, packer_varname, arg.getType(), 
              arg.getFormalName(), arg.getFormalName(), arg.getMode(), 
              arg.isCopy(), reuseable, false);
    
  }

  /** generate the method call to pack an argument
   * @param lw              The language writer to write to
   * @param packer_typename The packer type (often sidl_rmi_Serializer)
   * @param packer_varname  The name of the packer object
   * @param varname         The name the argument is called in the
   *                        serializer
   * @param var             The actual argument name (return = _retval)
   * @param mode            IN, OUT, or INOUT
   * @param isCopy          Serialize the argument if it is an object.
   * @param reuseable       True is (when the argument is an array) 
   *                         babel should attempt to copy the new data into
   *                         the old array.
   * @param isReturn        If it's a return value (not an argument) 
   */
  public static void packType( LanguageWriterForC lw ,
                               Context context,
                               String packer_typename, 
                               String packer_varname,  Type type, 
                               String varname, String var, int mode, boolean isCopy, 
                               boolean reuseable, boolean isReturn) {
    
    switch(type.getDetailedType()) {
    case Type.CLASS:
    case Type.INTERFACE:
      packSymbol(lw, context,
                 packer_typename, packer_varname, type, varname, var, mode,
                 isCopy, isReturn);
      break;
      
    case Type.ARRAY:
      packArray(lw, context,
                packer_typename, packer_varname, type, varname, var,
                mode, reuseable, isReturn);
      break;
    case Type.STRUCT:
      packStruct(lw, context,
                 packer_typename, packer_varname, type, varname, var,
                 isCopy, isReturn);
      break;
    case Type.ENUM:
      type = new Type(Type.LONG);
      //fall though
    default:
      String ext = RMI.getMethodExtension( type );
      String var_prefix = "";
      if(mode != Argument.IN) {
        var_prefix ="*";
      }
      lw.println(packer_typename + "_pack" + ext + "( " + packer_varname + ", \"" 
                 + varname + "\", " + var_prefix + var +", _ex);SIDL_CHECK(*_ex);");
    }
    return;
  }

  private static boolean hasStructArguments(Method m,
                                            Context context) {
    SymbolTable table = context.getSymbolTable();
    Iterator args = m.getSymbolReferences().iterator();
    while (args.hasNext()) {
      Symbol sym = table.lookupSymbol((SymbolID)args.next());
      if ((sym != null) && (sym instanceof Struct)) return true;
    }
    return false;
  }

  /** pack class or interface arguments
   * @param lw              The language writer to write to
   * @param packer_typename The packer type (often sidl_rmi_Serializer)
   * @param packer_varname  The name of the packer object
   * @param type            The Type 
   * @param varname         The name the argument is called in the
   *                        serializer
   * @param var             The actual argument name (return = _retval)
   * @param mode            IN, OUT, or INOUT
   * @param isCopy          Serialize the argument if it is an object.
   */
  private static void packSymbol(LanguageWriterForC lw, 
                                 Context context,
                                 String packer_typename, 
                                 String packer_varname,  Type type, 
                                 String varname, String var, int mode, 
                                 boolean isCopy, boolean isReturn) {
    String var_prefix = "";
    String url_string = null;
    if(mode != Argument.IN) {
      var_prefix ="*";
    }
    url_string = "sidl_BaseInterface__getURL" + "((sidl_BaseInterface)" 
      + var_prefix + var + ", _ex)";

    if(isCopy) {
      if(RMI.isSerializable(type, context)) {
      /**-------------------------------------------------------------------
       * Semi-hack.  In the backed there's no real SYMBOL type, so I'm using
       * it as a flag here to mean Serializable. Plus, this is a recursive
       * call to reuse unpacktype, if we just passed "type" it would
       * infinite loop.
       *--------------------------------------------------------------------*/

        lw.println("if("+var+") {");
        lw.tab();
        lw.println(var+getSerializableExt()+" = ("+IOR.getObjectName(SerialName)+"*)((("+
                   IOR.getObjectName(BIName)+"*)"+ var_prefix+var+
                   ")->d_epv->f__cast)((("+ IOR.getObjectName(BIName)+"*)"+
                   var_prefix+var + ")->d_object, \""+SerialName+
                   "\",_ex); SIDL_CHECK(*_ex);");

        //TODO: Is there some way to use the C binding to autogenerate this call?
        // DeleteRef the extra reference from the cast
        
        lw.println("((("+IOR.getObjectName(BIName)+"*)("+
                   var+getSerializableExt()+"))->d_epv->f_deleteRef)((("+
                   IOR.getObjectName(BIName)+"*)"+
                   var+getSerializableExt() + ")->d_object, _ex); SIDL_CHECK(*_ex);");
        lw.backTab();
        lw.println("}");
        packType(lw, context,
                 packer_typename, packer_varname, new Type(Type.SYMBOL), 
                 varname, var+getSerializableExt(), Argument.IN, isCopy, 
                 false, isReturn);
      }
    } else {
      lw.println("if("+var_prefix+var+"){");
      lw.tab();
      lw.println("char* _url = "+url_string+";SIDL_CHECK(*_ex);");
      lw.println(packer_typename + "_packString( " + packer_varname + ", \"" 
                 + varname + "\", _url, _ex);SIDL_CHECK(*_ex);");
      lw.println("free((void*)_url);");
      lw.backTab();
      lw.println("} else {");
      lw.tab();
      lw.println(packer_typename + "_packString( " + packer_varname + ", \"" 
                 + varname + "\", NULL, _ex);SIDL_CHECK(*_ex);");
      lw.backTab();
      lw.println("}");
    }
    return;
  }
  
  /** pack struct
   * @param lw              The language writer to write to
   * @param packer_typename The packer type (often sidl_rmi_Serializer)
   * @param packer_varname  The name of the packer object
   * @param type            The Type 
   * @param varname         The name the argument is called in the
   *                        serializer
   * @param var             The actual argument name (return = _retval)
   * @param isCopy          Serialize the argument if it is an object.
   * @param isReturn        If it's a return value (not an argument) 
   */
  private static void packStruct(LanguageWriterForC lw, 
                                 Context context,
                                 String packer_typename, 
                                 String packer_varname,  Type type, 
                                 String varname, String var,
                                 boolean isCopy, boolean isReturn) 
  {
    final SymbolID id = type.getSymbolID();
    final String name = IOR.getSymbolName(id);
    final String varmod = (isReturn ? "&" : "");
    lw.println("RMI_" + name +
               "_serialize(" + varmod + var + ", " +
               packer_varname + ", \"" + varname + "\"," +
               (isCopy ? "1"  : "0")
               + ", _ex); SIDL_CHECK(*_ex);");
  }

  /** pack an array argument
   * @param lw              The language writer to write to
   * @param packer_typename The packer type (often sidl_rmi_Serializer)
   * @param packer_varname  The name of the packer object
   * @param type            The Type 
   * @param varname         The name the argument is called in the
   *                        serializer
   * @param var             The actual argument name (return = _retval)
   * @param mode            IN, OUT, or INOUT
   * @param reuseable       True is (when the argument is an array) 
   *                         babel should attempt to copy the new data into
   *                         the old array.
   * @param isReturn       If it's a return value (not an argument) 
   */
  private static void packArray(LanguageWriterForC lw, 
                                Context context,
                                String packer_typename, 
                                String packer_varname,  Type type, 
                                String varname, String var, int mode, 
                                boolean reuseable, boolean isReturn) {
    String var_prefix = "";
    String ext = RMI.getMethodExtension( type );
    if(mode != Argument.IN && !isReturn) {
      var_prefix ="*";
    }
    //Need to cast ENUM arrays to the right type. The first clause
    //is just to avoid a null reference with generic arrays.
    if(type.getArrayType() != null && 
       type.getArrayType().getDetailedType() == Symbol.ENUM) {
      var_prefix = "(" + IOR.getArrayName(Type.LONG)+")"+ var_prefix;
    } else if(type.getArrayType() != null && 
              (type.getArrayType().getDetailedType() == Symbol.CLASS ||
               type.getArrayType().getDetailedType() == Symbol.INTERFACE)) {
      SymbolTable table = context.getSymbolTable();
      Extendable serializable = (Extendable)table.lookupSymbol(SerialName);
      var_prefix = "(" + IOR.getArrayName(serializable.getSymbolID()) + "*)"+ var_prefix;
    }

    if(isReturn || mode != Argument.INOUT) {
      reuseable = false;
    }

    //If this type is not serializable (probably an object array that
    //doesn't inherit from Serializable) skip it.
    if(RMI.isSerializable(type, context)) {
      lw.println(packer_typename + "_pack" + ext + "( " + packer_varname + ", \"" 
                 + varname + "\"," + var_prefix + var +  
                 RMI.getEnsureIn(type,varname, reuseable)+", _ex);SIDL_CHECK(*_ex);");
    } else {
      //If isSerializable fails here we know, from context, that the array
      //is an object array.
      lw.println("SIDL_THROW(*_ex, sidl_NotImplementedException, \"An array of type \"");
      lw.println("\""+type.getArrayType().getSymbolID().getFullName() + "\"");
      lw.println("\" cannot currently be passed through RMI.  That functionality\"");
      lw.println("\"is not implemented.\");");
      //      lw.println("SIDL_THROW(*_ex, sidl_NotImplementedException, \"An array of type "+
      //           type.getArrayType().getSymbolID().getFullName() +" cannot currently "+
      //           " be passed through RMI.  That functionality is not implemented.\");");
    }
    
  }



  /** generate the method call to unpack an argument
   * @param lw              The language writer to write to
   * @param ext             The Extendable this is being generated in
   * @param packer_typename The packer type (often sidl_rmi_Serializer)
   * @param packer_varname  The name of the packer object
   * @param inIOR           True if this is being generated in thie IOR
   */
  public static void unpackArg( LanguageWriterForC lw, 
                                Context context,
                                Extendable ext, String packer_typename, 
                                String packer_varname, Argument arg, boolean inIOR ) 
    throws CodeGenerationException
  { 
    unpackType( lw, context,
                ext, packer_typename, packer_varname, arg.getType(), 
                arg.getFormalName(), arg.getFormalName(), arg.getMode(), 
                arg.isCopy(), false, inIOR );
  }

  /** generate the method call to unpack an argument or return value
   * @param lw              The language writer to write to
   * @param ext             The Extendable this is being generated in
   * @param packer_typename The packer type (often sidl_rmi_Serializer)
   * @param packer_varname  The name of the packer object
   * @param type            The Type 
   * @param varname         The name the argument is called in the
   *                        serializer
   * @param var             The actual argument name (return = _retval)
   * @param mode            IN, OUT, or INOUT
   * @param isCopy          Serialize the argument if it is an object.
   * @param isReturn        If it's a return value (not an argument) 
   * @param inIOR           True if this is being generated in thie IOR
   */
  public static void unpackType(LanguageWriterForC lw,  
                                Context context,
                                Extendable ext, String packer_typename, 
                                String packer_varname, Type type, 
                                String varname, String var, int mode, 
                                boolean isCopy, boolean isReturn, boolean inIOR)
    throws CodeGenerationException {
 
    switch(type.getDetailedType()) {
    case Type.CLASS:
    case Type.INTERFACE:
      unpackSymbol(lw, context,
                   ext, packer_typename, packer_varname, type, varname, var, mode, 
                   isCopy, isReturn, inIOR);
      break;
    case Type.ARRAY:
      unpackArray(lw, context,
                  packer_typename, packer_varname, type, varname, var, mode, isReturn);
      break;        
    case Type.ENUM:
      unpackEnum(lw, context,
                 packer_typename, packer_varname, type, varname, var, mode, 
                 isReturn, inIOR); 
      break;
    case Type.STRUCT:
      unpackStruct(lw, context,
                   packer_typename, packer_varname, type, varname, var,
                   isCopy, isReturn, inIOR);
      break;
    default:
      String m_ext = RMI.getMethodExtension( type );
      String var_prefix = "";
      if(mode == Argument.IN || isReturn) {
        var_prefix = "&";
      }
      lw.println(packer_typename + "_unpack" + m_ext + "( " + packer_varname 
                 + ", \"" + varname + "\", " + var_prefix + var +", _ex);SIDL_CHECK(*_ex);");
      break;
    }
    return; 
      
  }

  /** unpack struct
   * @param lw              The language writer to write to
   * @param packer_typename The packer type (often sidl_rmi_Serializer)
   * @param packer_varname  The name of the packer object
   * @param type            The Type 
   * @param varname         The name the argument is called in the
   *                        serializer
   * @param var             The actual argument name (return = _retval)
   * @param isCopy          Serialize the argument if it is an object.
   * @param isReturn        If it's a return value (not an argument) 
   * @param inIOR           True if this is being generated in thie IOR
   */
  private static void unpackStruct(LanguageWriterForC lw, 
                                   Context context,
                                   String packer_typename, 
                                   String packer_varname,  Type type, 
                                   String varname, String var,
                                   boolean isCopy, boolean isReturn, boolean inIOR) 
    throws CodeGenerationException 
  {
    final SymbolID id = type.getSymbolID();
    final String name = IOR.getSymbolName(id);
    final String varmod = (isReturn ? "&" : "");
    lw.println("RMI_" + name +
               "_deserialize(" + varmod + var + ", " +
               packer_varname + ", \"" + varname + "\"," +
               (isCopy ? "1" : "0" ) +
               " , _ex); SIDL_CHECK(*_ex);");
  }

  /** unpack class or interface arguments
   * @param lw              The language writer to write to
   * @param ext             The Extendable this is being generated in
   * @param packer_typename The packer type (often sidl_rmi_Serializer)
   * @param packer_varname  The name of the packer object
   * @param type            The Type 
   * @param varname         The name the argument is called in the
   *                        serializer
   * @param var             The actual argument name (return = _retval)
   * @param mode            IN, OUT, or INOUT
   * @param isCopy          Serialize the argument if it is an object.
   * @param isReturn        If it's a return value (not an argument) 
   * @param inIOR           True if this is being generated in thie IOR
   */
  private static void unpackSymbol(LanguageWriterForC lw, 
                                   Context context,
                                   Extendable ext, String packer_typename, 
                                   String packer_varname,  Type type, 
                                   String varname, String var, int mode, 
                                   boolean isCopy, boolean isReturn, boolean inIOR) 
    throws CodeGenerationException 
  {

    String raddref = "FALSE";
    String mod = "";
    if(mode != Argument.IN) {
      mod = "*";
    }
    if(mode == Argument.IN && !isReturn) {  //We should only raddref on in args
      raddref = "TRUE";
    }

    if(isCopy) {
      if(RMI.isSerializable(type, context)) {
      /**-------------------------------------------------------------------
       * Semi-hack.  In the backed there's no real SYMBOL type, so I'm using
       * it as a flag here to mean Serializable.  Plus, this is a recursive
       * call to reuse unpacktype, if we just passed "type" it would
       * infinite loop.
       *--------------------------------------------------------------------*/
        unpackType(lw, context, 
                   ext, packer_typename, packer_varname, new Type(Type.SYMBOL),
                   varname, var+getSerializableExt(), Argument.IN, isCopy, 
                   isReturn, inIOR);
        
        //If the variable comes back NULL, just leave it that way, don't cast.
        lw.println("if("+var+getSerializableExt()+") {");
        lw.tab();
        if(inIOR) {
          // Cast 
          //          lw.printlnUnformatted("#ifdef WITH_RMI");
          //          lw.println();

          //lw.println(mod+var +" = "+IOR.getSkelFCastName(ext.getSymbolID(), type.getSymbolID())+ 
          //                "((sidl_BaseInterface)"+var+getSerializableExt()+", _ex);SIDL_CHECK(*_ex);");

        //lw.printlnUnformatted("#else");
          
          lw.println(mod+var + "= ("+IOR.getObjectName(type.getSymbolID())+"*) ("+var+getSerializableExt()+"->d_epv->f__cast)(((sidl_BaseInterface)"+var+getSerializableExt()+")->d_object, \""+type.getSymbolID().getFullName()+"\", _ex);"); 

          //lw.printlnUnformatted("#endif /* WITH_RMI */"); 

          // DeleteRef the extra reference from the cast
          lw.println("((("+IOR.getObjectName(BIName)+"*)("+
                     var+getSerializableExt()+"))->d_epv->f_deleteRef)((("+
                     IOR.getObjectName(BIName)+"*)"+
                     var+getSerializableExt() + ")->d_object, _ex); SIDL_CHECK(*_ex);");
        } else {
          // Cast 
          lw.println(mod+var +" = ("+IOR.getObjectName(type.getSymbolID())+"*) ((("+IOR.getObjectName(BIName)+"*)("+
                     var+getSerializableExt()+"))->d_epv->f__cast)((("+
                     IOR.getObjectName(BIName)+"*)"+
                     var+getSerializableExt()+ ")->d_object, \""+type.getSymbolID().getFullName()+
                     "\",_ex); SIDL_CHECK(*_ex);");

          //          lw.println(mod+var +" = "+IOR.getSymbolName(type.getSymbolID())+ "__cast" +
          //          "((sidl_BaseInterface)"+var+getSerializableExt()+", _ex);");
          // DeleteRef the extra reference from the cast
          //TODO: Is there some way to use the C binding to autogenerate this call?
          lw.println("((("+IOR.getObjectName(BIName)+"*)("+
                     var+getSerializableExt()+"))->d_epv->f_deleteRef)((("+
                     IOR.getObjectName(BIName)+"*)"+
                     var+getSerializableExt() + ")->d_object, _ex); SIDL_CHECK(*_ex);");
        }
        lw.backTab();
        lw.println("}");
      }
      return;
      }
    
    RMI.unpackType(lw, context,
                   ext, packer_typename, packer_varname, new Type(Type.STRING),
                   varname,var+"_str", Argument.IN, inIOR, isCopy, false);
    
    
    if(inIOR) {
      lw.printlnUnformatted("#ifdef WITH_RMI");
      lw.println();

      lw.println(mod+var +" = "+IOR.getSkelFConnectName(ext.getSymbolID(), type.getSymbolID())+ 
                 "("+var+"_str, "+raddref+", _ex);SIDL_CHECK(*_ex);");

      lw.printlnUnformatted("#else");

      lw.println(var +"_bc = sidl_rmi_InstanceRegistry_getInstanceByString("+var+"_str, _ex);SIDL_CHECK(*_ex);");
      lw.println("if("+var+"_bc != NULL) {");
      lw.tab();
      lw.println(mod+var + "= ("+IOR.getObjectName(ext)+"*) (*"+var+"_bc->d_epv->f__cast)("+var+"_bc, \""+ext.getSymbolID().getFullName()+"\", _ex);"); 
      lw.println("if("+mod+var+" != NULL) {");
      lw.tab();      
      lw.println("((("+IOR.getObjectName(BIName)+"*)("+
                 mod+var+"))->d_epv->f_deleteRef)((("+
                 IOR.getObjectName(BIName)+"*)"+
                 mod+var+")->d_object, _ex); SIDL_CHECK(*_ex);");
      if(raddref.compareTo("FALSE") == 0) {
        lw.writeCommentLine("No addrefs in this case, delete implicit addrefs from InstanceRegistry and cast");
        lw.println("((("+IOR.getObjectName(BIName)+"*)("+
                   mod+var+"))->d_epv->f_deleteRef)((("+
                   IOR.getObjectName(BIName)+"*)"+
                   mod+var+ ")->d_object, _ex); SIDL_CHECK(*_ex);");
      }
      lw.backTab();
      lw.println("} else {");  
      lw.tab();      
      lw.println("(*"+var+"_bc->d_epv->f_deleteRef)("+var+"_bc, _ex);"); 
      lw.backTab();
      lw.println("}");  
      lw.backTab();
      lw.println("}");  


      lw.printlnUnformatted("#endif /* WITH_RMI */"); 
    } else {
      lw.println(mod+var +" = "+ IOR.getRemoteConnectName(type.getSymbolID())+ 
                 "("+var+"_str, "+raddref+", _ex);SIDL_CHECK(*_ex);");
    }
    
  }

  private static void unpackEnum(LanguageWriterForC lw, 
                                 Context context,
                                 String packer_typename, 
                                 String packer_varname,  Type type, 
                                 String varname, String var, int mode, 
                                 boolean isReturn, boolean inIOR) 
    throws CodeGenerationException{

    String mod = "";
    if(mode != Argument.IN) {
      mod = "*";
    }
    
    RMI.unpackType(lw, context, 
                   null, packer_typename, packer_varname, new Type(Type.LONG), 
                   varname,var+"_tmp", Argument.IN, inIOR, false, false);
    lw.println(mod+var +" = ("+IOR.getReturnString(type, context,
                                                   true,true)+
               ")"+var+"_tmp;");
    
  }


  /** unpack array arguments
   * @param lw              The language writer to write to
   * @param packer_typename The packer type (often sidl_rmi_Serializer)
   * @param packer_varname  The name of the packer object
   * @param type            The Type 
   * @param varname         The name the argument is called in the
   *                        serializer
   * @param var             The actual argument name (return = _retval)
   * @param mode            IN, OUT, or INOUT
   * @param isReturn        If it's a return value (not an argument) 
   */
  private static void unpackArray(LanguageWriterForC lw, 
                                  Context context,
                                  String packer_typename, 
                                  String packer_varname,  Type type, 
                                  String varname, String var, int mode, 
                                  boolean isReturn) {
    
    String ext = RMI.getMethodExtension( type );
    String var_prefix = "";
    if(mode == Argument.IN || isReturn) {
      var_prefix = "&";
    }
    //Need to cast ENUM arrays to the right type. The first clause
    //is just to avoid a null reference with generic arrays.
    if(type.getArrayType() != null && 
       type.getArrayType().getDetailedType() == Symbol.ENUM) {
      var_prefix = "(" + IOR.getArrayName(Type.LONG)+"*)"+ var_prefix;
    } else if(type.getArrayType() != null && 
              (type.getArrayType().getDetailedType() == Symbol.CLASS ||
               type.getArrayType().getDetailedType() == Symbol.INTERFACE)) {
      SymbolTable table = context.getSymbolTable();
      Extendable serializable = (Extendable)table.lookupSymbol(SerialName);
      var_prefix = "(" + IOR.getArrayName(serializable.getSymbolID()) + "**)"+ var_prefix;
    }

    //If this type is not serializable (probably an object array that
    //doesn't inherit from Serializable) skip it.
    if(RMI.isSerializable(type, context)) {
      lw.println(packer_typename + "_unpack" + ext + "( " + packer_varname 
                 + ", \"" + varname + "\", " + 
                 var_prefix + var + RMI.getEnsureOut(type)+ ", _ex);SIDL_CHECK(*_ex);");
    } else {
      //If isSerializable fails here we know, from context, that the array
      //is an object array.
      lw.println("SIDL_THROW(*_ex, sidl_NotImplementedException, \"An array of type \"");
      lw.println("\""+type.getArrayType().getSymbolID().getFullName() + "\"");
      lw.println("\" cannot currently be passed through RMI.  That functionality\"");
      lw.println("\"is not implemented.\");");
    }
  }

  /** Emits some code to the given language writer so serialize a particular
   * field of a SIDL struct. The struct is expected to be called "arg". The
   * serializer object itself has to be called "serializer". The
   * implementation is shared by the Java and the Python backend. 
   */
  public static void serializeField(LanguageWriterForC d_writer, 
                                    Context context,
                                    String packer_typename,
                                    String packer_name,
                                    Struct.Item item) {
    final String SerialName = BabelConfiguration.getSerializableType();
    final String BIName = BabelConfiguration.getBaseInterface();
    
    String field_name = item.getName();
    Type type = item.getType();
      
    d_writer.println("strcpy(p_field, \"." + field_name + "\");");

    if(type.isStruct()) {
      d_writer.println(JavaStructSource.getIORSerializerName(type.getSymbolID()) +
                       "(&arg->" + field_name + ", " + packer_name + 
                       ", name_buf, copyArg, exception); SIDL_CHECK(*exception);");
    }
    else if(type.isArray()) {
      d_writer.writeCommentLine("TODO: not yet implemented: " + field_name);
    }
    else if(type.getDetailedType() == Type.INTERFACE ||
            type.getDetailedType() == Type.CLASS) {

      d_writer.println("if(copyArg) {");
      d_writer.tab();
      d_writer.println(IOR.getSymbolName(SerialName) + " _ser = NULL;");
        
      //clone the current field
      d_writer.println("if(arg->" + field_name + ") {");
      d_writer.tab();
      d_writer.println("_ser = (" + IOR.getObjectName(SerialName)+"*)((("+
                       IOR.getObjectName(BIName)+"*) arg->"+ field_name +
                       ")->d_epv->f__cast)((("+ IOR.getObjectName(BIName)+"*)"+
                       "arg->" + field_name + ")->d_object, \"" + SerialName +
                       "\", exception); SIDL_CHECK(*exception);");
      //get rid of the extra reference from the cast
      d_writer.println("((("+IOR.getObjectName(BIName)+"*)(_ser))->d_epv->f_deleteRef)((("+
                       IOR.getObjectName(BIName)+"*) _ser)->d_object, exception); SIDL_CHECK(*exception);");
      d_writer.println(packer_typename + "_packSerializable(" + packer_name + ", name_buf, _ser, exception); SIDL_CHECK(*exception);");
        
      d_writer.backTab();
      d_writer.println("}");
        
      d_writer.println(packer_typename + "_packSerializable(" + packer_name + ", name_buf, _ser, exception); SIDL_CHECK(*exception);");
        
      d_writer.backTab();
      d_writer.println("} else {");
      d_writer.tab();

      //serialize the remote URL
      d_writer.println("if(arg->" + field_name + ") {");
      d_writer.tab();
      d_writer.println("char* _url = sidl_BaseInterface__getURL((sidl_BaseInterface) arg->" +
                       field_name + ", exception); SIDL_CHECK(*exception);");
      d_writer.println(packer_typename + "_packString(" + packer_name + ", name_buf, _url, exception); SIDL_CHECK(*exception);");
      d_writer.println("free(_url);");
      d_writer.backTab();
      d_writer.println("} else {");
      d_writer.tab();
      d_writer.println(packer_typename + "_packString(" + packer_name + ", name_buf, NULL, exception); SIDL_CHECK(*exception);");
      d_writer.backTab();
      d_writer.println("}");
        
      d_writer.backTab();
      d_writer.println("}");
        
    }
    else {
      Type pack_type = (type.getDetailedType() == Type.ENUM ? new Type(Type.LONG) : type);
      d_writer.println(packer_typename + "_pack" +
                       RMI.getMethodExtension(pack_type) + 
                       "(" + packer_name + ", name_buf, arg->" + field_name +", exception); SIDL_CHECK(*exception);");
    }
  }
  

  /** Emits some code to the given language writer so deserialize a particular
   * field of a SIDL struct. The struct is expected to be called "arg". The
   * serializer object itself has to be called "serializer". The
   * implementation is shared by the Java and the Python backend. 
   */
  public static void deserializeField(LanguageWriterForC d_writer, 
                                      Context context,
                                      String packer_typename,
                                      String packer_name,
                                      Struct.Item item) {

    final String SerialName = BabelConfiguration.getSerializableType();
    final String BIName = BabelConfiguration.getBaseInterface();
    String field_name = item.getName();
    Type type = item.getType();
      
    d_writer.println("strcpy(p_field, \"." + field_name + "\");");

    if(type.isStruct()) {
      d_writer.println(JavaStructSource.getIORDeserializerName(type.getSymbolID()) +
                       "(&arg->" + field_name + ", " + packer_name + 
                       ", name_buf, copyArg, exception); SIDL_CHECK(*exception);");
    }
    else if(type.isArray()) {
      d_writer.writeCommentLine("TODO: not yet implemented: " + field_name);
    }
    else if(type.getDetailedType() == Type.INTERFACE ||
            type.getDetailedType() == Type.CLASS) {

      d_writer.println("arg->" + field_name + " = NULL;");
      d_writer.println("if(copyArg) {");
      d_writer.tab();

      //deserialize the whole object
      d_writer.println(IOR.getSymbolName(SerialName) + " _ser = NULL;");
      d_writer.println(packer_typename + "_unpackSerializable" +
                       "(" + packer_name + ", name_buf, &_ser, exception); SIDL_CHECK(*exception);");
      d_writer.println("if(_ser) {");
      d_writer.tab();
      d_writer.println("arg->" + field_name + "= (" + IOR.getObjectName(type.getSymbolID()) + "*) (" +
                       "_ser->d_epv->f__cast)(((sidl_BaseInterface) _ser)->d_object, \""+
                       type.getSymbolID().getFullName()+"\", exception);");
      d_writer.println("((("+IOR.getObjectName(BIName)+"*)(_ser))->d_epv->f_deleteRef)((("+
                       IOR.getObjectName(BIName)+"*) _ser)->d_object, exception); SIDL_CHECK(*exception);");
      d_writer.backTab();
      d_writer.println("}");
        
      d_writer.backTab();
      d_writer.println("} else {");
      d_writer.tab();

      //deserialize the remote URL and connect to the specified object
      d_writer.println("char *_url = NULL;");
      d_writer.println(packer_typename + "_unpackString" +
                       "(" + packer_name + ", name_buf, &_url, exception); SIDL_CHECK(*exception);");
      d_writer.println("if(_url && *_url) {");
      d_writer.tab();
      d_writer.println("arg->" + field_name + " = " +
                       C.getFullMethodName(type.getSymbolID(), "_connectI") +
                       "(_url, FALSE, exception);SIDL_CHECK(*exception);");
      d_writer.backTab();
      d_writer.println("}");
        
      d_writer.backTab();
      d_writer.println("}");
        
    }
    else {
      Type pack_type = (type.getDetailedType() == Type.ENUM ? new Type(Type.LONG) : type);
      d_writer.println(packer_typename + "_unpack" +
                       RMI.getMethodExtension(pack_type) + 
                       "(" + packer_name + ", name_buf, &arg->" + field_name +", exception); SIDL_CHECK(*exception);");
    }

  }
  
  /** generate the proper method extension for packing or unpacking
   * @param t
   */
  public static String getMethodExtension( Type t ) { 
    if ( t.isPrimitive() ) { /* fixed size & string */
      String buff = t.getTypeString();  
      return buff.substring(0,1).toUpperCase() + buff.substring(1); 
    } else if ( t.isArray() ) { 
      Type arrayType = t.getArrayType();
      if(arrayType == null) {
        return "GenericArray";
      }
      return getMethodExtension(t.getArrayType()) + "Array";

    } else if(t.getDetailedType() == Symbol.CLASS ||
              t.getDetailedType() == Symbol.INTERFACE) {
      return "Serializable";
    } else if(t.getDetailedType() == Symbol.ENUM) {
      Type tmp = new Type(Type.LONG);  //Enums are just longs
      return getMethodExtension(tmp);
      /**-------------------------------------------------------------------
       * Semi-hack.  In the backed there's no real SYMBOL type, so I'm using
       * it as a flag here to mean Serializable.
       *--------------------------------------------------------------------*/
    } else if(t.getDetailedType() == Type.SYMBOL) {
      return "Serializable";
    } else { 
      return "ERROR";
    }
  }

  /**
   * Unpacking array arguments requires a couple of extra args, generate
   * those.
   * @param t
   */
  private static String getEnsureOut(Type t) {
    String ret = "";
    String israrray = null;
    if(t.getArrayType() != null) {
      if(t.isArray()) {
        if(t.isRarray()) {
          israrray = "TRUE";
        } else {
          israrray = "FALSE";
        }
        if(t.hasArrayOrderSpec()) {
          ret = ","+BabelConfiguration.getArrayOrderName(t.getArrayOrder())+","+t.getArrayDimension()+","+israrray;
        } else {
          ret = ",0,0,"+israrray;
        }
      }
    }
    return ret;
  }

  /**
   * Packing array arguments requires a couple of extra args, generate
   * those.
   * @param t
   */
  private static String getEnsureIn(Type t, String varname, boolean check_reuse) {
    String ret = "";
    if(t.getArrayType() != null) {
      if(t.hasArrayOrderSpec()) {
        ret = ","+BabelConfiguration.getArrayOrderName(t.getArrayOrder())+","+t.getArrayDimension();
      } else {
        ret = ",0,0";
      }
    }
    if(check_reuse) {
      String reuseFlag = "(*"+varname+"==" +varname+RMI.getDataExt()+")";
      ret = ret + "," + reuseFlag;
    } else {
      ret = ret + ",0";
    }

    return ret;
  }

  public static void declareStackArgs(LanguageWriterForC lw , 
                                      Argument arg,
                                      Context context)
    throws CodeGenerationException 
  {
    switch(arg.getType().getDetailedType()) {
    case Type.CLASS:
    case Type.INTERFACE:
      declareStackSymbol(lw, arg.getType(), arg.getFormalName(), 
                         arg.getMode(), arg.isCopy(), false, context);
      break;
    case Type.STRUCT:
      lw.println(IOR.getStructName(arg.getType().getSymbolID()) + 
                 " " + arg.getFormalName() + RMI.getDataExt() + " = { 0 };");
      lw.println(IOR.getStructName(arg.getType().getSymbolID()) + 
                 "* const " + arg.getFormalName() + " = &" +
                 arg.getFormalName() + RMI.getDataExt() + ";");
      break;
    case Type.ENUM:
      if(arg.getMode() != Argument.OUT) {
        lw.println(IOR.getReturnString(new Type(Type.LONG), context) + " "
                   + arg.getFormalName() + "_tmp= 0;");
      }
      if(arg.getMode() == Argument.IN) {
        lw.println(IOR.getArgumentWithFormal(arg, context, 
                                             true, false, true) + 
                   Utilities.getTypeInitialization(arg.getType(), context)
                   + ";");
      } else {
        lw.println(IOR.getArgumentWithFormal(arg, context, 
                                             true, false, true) + 
                   RMI.getDataExt() + 
                   Utilities.getTypeInitialization(arg.getType(), context) 
                   + ";");
        lw.println(IOR.getArgumentWithFormal(arg, context, true, false, false) 
                   + " = &"+ arg.getFormalName()+RMI.getDataExt()+";");
      }
      break;
    case Type.STRING:
      if(arg.getMode() == Argument.IN) {
        //Special case for string: normally IN strings are const, not in RMI
        lw.println(IOR.getReturnString(arg.getType(), context) + " " 
                   + arg.getFormalName() + "= "+C.NULL+";");
      } else {
        lw.println(IOR.getArgumentWithFormal(arg, context, 
                                             true, false, true) + 
                   RMI.getDataExt()+ 
                   Utilities.getTypeInitialization(arg.getType(), context) +
                   ";");
        lw.println(IOR.getArgumentWithFormal(arg, context, true, false, false) 
                   + " = &"+ arg.getFormalName()+RMI.getDataExt()+";");
      }
      break;
    case Type.ARRAY:
      if(arg.getMode() == Argument.IN) {
        lw.println(IOR.getArgumentWithFormal(arg, context, 
                                             true, false, true) +
                   " = " + C.NULL + ";");
      } else {
        lw.println(IOR.getArgumentWithFormal(arg, context, true, false, true)
                   + RMI.getDataExt()+ " = " + C.NULL + ";");
        lw.println(IOR.getArgumentWithFormal(arg, context, true, false, false) 
                   + " = &"+ arg.getFormalName()+RMI.getDataExt()+";");
      }
      break;
    default:
      if(arg.getMode() == Argument.IN) {
        lw.println(IOR.getArgumentWithFormal(arg, context, true, false, true) +
                   Utilities.getTypeInitialization(arg.getType(), context) +
                   ";");
      } else {
        lw.println(IOR.getArgumentWithFormal(arg, context, true, false, true)
                   + RMI.getDataExt()+
                   Utilities.getTypeInitialization(arg.getType(), context) 
                   +";");
        lw.println(IOR.getArgumentWithFormal(arg, context, true, false, false) 
                   + " = &"+ arg.getFormalName()+RMI.getDataExt()+";");
      }
      
    }
  }
    
  

  public static void declareStackReturn(LanguageWriterForC lw , 
                                        Type type, 
                                        boolean isCopy, 
                                        Context context) 
    throws CodeGenerationException {
    if(type.getDetailedType() == Type.CLASS ||
       type.getDetailedType() == Type.INTERFACE) {
      declareStackSymbol(lw, type, RMI.getReturnArgName(),
                         Argument.IN, isCopy, true, context);
    } else if (type.getDetailedType() == Type.STRUCT) {
      lw.println(IOR.getStructName(type.getSymbolID()) +
                 " " + RMI.getReturnArgName() + ";");
    } else {
      lw.println( IOR.getReturnString(type, context, true, false) + 
                  " " + RMI.getReturnArgName()+
                  Utilities.getTypeInitialization(type, context)+";");
    }
  }
  
  public static void declareStackSymbol(LanguageWriterForC lw , 
                                        Type type, 
                                        String var, 
                                        int mode, 
                                        boolean isCopy, 
                                        boolean isReturn,
                                        Context context) 
    throws CodeGenerationException {

    if(isCopy) {
      lw.println("sidl_io_Serializable " +var+ RMI.getSerializableExt()+" = "+C.NULL+ ";");
    } else {
      if(mode != Argument.OUT) {
        if(!isReturn) {
          //We need a string to unserialize the url into
          lw.println(IOR.getReturnString(new Type(Type.STRING), context) 
                     +" "+var + RMI.getStringExt() +" = "+C.NULL+";");

          lw.printlnUnformatted("#ifndef WITH_RMI");
          lw.println(IOR.getSymbolName(BabelConfiguration.getBaseClass()) + " " 
                     + var + "_bc = "+C.NULL+";");
          lw.printlnUnformatted("#endif /* WITH_RMI */"); 
          
        }
      }
    }
    if(mode == Argument.IN) {
      lw.println(C.getSymbolObjectPtr(type.getSymbolID()) + " " 
                 + var + " = "+C.NULL+";");
    } else {
      lw.println(IOR.getReturnString(type, context) + " " 
                 + var + RMI.getDataExt() +" = "+C.NULL+";");
      lw.println(IOR.getReturnString(type, context) + "* " 
                 + var + " = &"+var+RMI.getDataExt()+";");
    }
  }



  /**
   * Returns the varialbe extension used to identify a temporary variable of
   * type sidl.io.Serializable.  (Used in object serialization)
   */
  public static String getSerializableExt() {
    return "_ser";
  }
  /**
   * Returns the varialbe extension used to identify a temporary variable of
   * type String to be used for object connection.
   */
  public static String getStringExt() {
    return "_str";
  }
  /**
   * Returns the varialbe extension used to identify a temporary variable of
   * for holding the pointer that inout arguments point to in serialization.
   *
   * ie: if we have an inout argument of type int, it is passed as an
   * int32_t*.  However, we must unserialize the actual integer somewhere. So:
   * int32_t x_data = unserialize_int();
   * int32_t* x = &x_data;
   * _retval = foo(x); 
   */
  public static String getDataExt() {
    return "_data";
  }

  /**
   * Returns internal name of the return value.
   */
  public static String getReturnArgName() {
    return "_retval";
  }

  /**
   * Classes that do no inherit from serializable and arrays of the same
   * are not serializable.  (There's gotta be a better way to do this...)
   */
  public static boolean isSerializable(Type type, Context context) {
    SymbolTable table = context.getSymbolTable();
    Extendable serializable = (Extendable)table.lookupSymbol(SerialName);
    
    if(type.getDetailedType() == Type.CLASS ||
       type.getDetailedType() == Type.INTERFACE) {
      Extendable this_ext = (Extendable)table.lookupSymbol(type.getSymbolID().getFullName()); 
      if(!this_ext.hasAncestor(serializable) && !this_ext.equals(serializable)) {
        return false;
      }
    }

    if((type.getArrayType() != null) &&
       (type.getArrayType().getDetailedType() == Type.CLASS ||
        type.getArrayType().getDetailedType() == Type.INTERFACE)) {
      Extendable this_ext = (Extendable)table.lookupSymbol(type.getArrayType().getSymbolID().getFullName()); 
      if(!this_ext.hasAncestor(serializable) && !this_ext.equals(serializable)) {
        return false;
      }
    }
    return true;
  }

  public static String LangSpecificInit() {
    return "LANG_SPECIFIC_INIT()";
  }
}
