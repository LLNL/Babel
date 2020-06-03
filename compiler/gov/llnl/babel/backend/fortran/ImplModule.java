//
// File:        ImplModule.java
// Package:     gov.llnl.babel.backend.fortran
// Copyright:   (c) 2003 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 7421 $
// Date:        $Date: 2011-12-15 17:06:06 -0800 (Thu, 15 Dec 2011) $
// Description: Generate a module containing some derived types
//
// Copyright (c) 2003, Lawrence Livermore National Security, LLC
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

import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.backend.CodeConstants;
import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.CodeSplicer;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.mangler.FortranMangler;
import gov.llnl.babel.backend.mangler.NameMangler;
import gov.llnl.babel.backend.mangler.NonMangler;
import gov.llnl.babel.backend.writers.LanguageWriterForFortran;
import gov.llnl.babel.symbols.Class;
import gov.llnl.babel.symbols.Method;
import gov.llnl.babel.symbols.SymbolID;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;
/**
 * This class provides the ability to write a FORTRAN 90 module file
 * containing the developer's derived type and a wrapper type too.
 */
public class ImplModule
{
  private LanguageWriterForFortran d_lw;

  private NameMangler d_mang;

  private CodeSplicer d_splicer;
  
  private Class d_cls;

  private SymbolID d_id;

  private Context d_context;

  public ImplModule(LanguageWriterForFortran writer,
                    CodeSplicer              splicer,
                    Class                    cls,
                    Context                  context)
    throws NoSuchAlgorithmException
  {
    d_mang = new NonMangler();
    d_splicer = splicer;
    d_lw = writer;
    d_cls = cls;
    d_id = cls.getSymbolID();
    d_context = context;
  }

  public void generateCode()
    throws CodeGenerationException
  {
    SymbolID bi_id = d_context.getSymbolTable().
      lookupSymbol(BabelConfiguration.getBaseInterface()).getSymbolID();

    String name = d_cls.getFullName();
    NameMangler mang = null;
    d_lw.writeBanner(d_cls, Fortran.getImplModuleFile(d_id, d_context), true,
                     CodeConstants.C_FORTRAN_IMPL_MODULE_PREFIX + 
                     d_id.getFullName());
    d_lw.generateInclude( Fortran.getStubNameFile(bi_id) );
    d_lw.generateInclude( Fortran.getStubNameFile(d_id));
    d_lw.println();
    d_splicer.splice("_hincludes", d_lw, "include files");
    d_lw.println();
    try {
      d_lw.println("module " + 
                   d_mang.shortName(name, "_impl"));
      d_lw.tab();
      d_lw.println();
      
      d_splicer.splice(name + ".use", d_lw, "use statements");
      d_lw.println();

      if (!d_cls.isAbstract() && !IOR.isSIDLSymbol(d_cls.getSymbolID()) ) {
        //Write interface for _wrapObj
        d_lw.println("private :: wrapObj_s");
        d_lw.println();
        d_lw.println("interface wrapObj");
        d_lw.println("module procedure wrapObj_s");
        d_lw.println("end interface");
        d_lw.println();      
      }

      if(d_cls.hasOverwrittenMethods()) {
        try {
          if (Fortran.needsAbbrev(d_context)) {
            mang =  new FortranMangler(AbbrevHeader.getMaxName(d_context),
                                       AbbrevHeader.getMaxUnmangled(d_context));
          }
          else {
            mang  =  new NonMangler();
          }
        } catch (NoSuchAlgorithmException ex) {
          throw new CodeGenerationException("Caught NoSuchAlgorithm exception from new FortranMangler");
        }

        //Write the interface for the super functions (if they exist)
        Collection methods = d_cls.getOverwrittenClassMethods();
        Iterator i = methods.iterator();
        while (i.hasNext()) {
          Method m = (Method)i.next();
          String mangName = Fortran.getMethodSuperImplName(d_cls.getSymbolID(),m,mang, d_context);
          String shortName = "super_" + m.getShortMethodName();
          d_lw.println("private :: " + mangName);
          d_lw.println();
          d_lw.println("interface " + shortName);
          d_lw.println("module procedure " + mangName);
          d_lw.println("end interface");
          d_lw.println();
         }
      }

      d_lw.println("type " + d_mang.shortName(name, "_priv"));
      d_lw.tab();
      d_lw.println("sequence");
      d_splicer.splice(name + ".private_data", d_lw, 
                       "private data members",
                       "integer :: place_holder ! replace with your private data");
      d_lw.backTab();
      d_lw.println("end type " + d_mang.shortName(name, "_priv"));
      d_lw.println();
      d_lw.println("type " + d_mang.shortName(name, "_wrap"));
      d_lw.tab();
      d_lw.println("sequence");
      d_lw.println("type(" + d_mang.shortName(name, "_priv") +
                   "), pointer :: d_private_data");
      d_lw.backTab();
      d_lw.println("end type " + d_mang.shortName(name, "_wrap"));
      d_lw.println();

      if (!d_cls.isAbstract() || d_cls.hasOverwrittenMethods()) {
        d_lw.println("contains");
        d_lw.println();
      }
      if (!d_cls.isAbstract() && !IOR.isSIDLSymbol(d_cls.getSymbolID())) {
        generateWrapObj();
      }

      //Here's where we generate the super functions!
      if(d_cls.hasOverwrittenMethods()) {

        ModuleSource.generateSupers(d_cls, d_lw, null, d_context, mang);

      }

      d_lw.backTab();
      d_lw.println("end module " + 
                   d_mang.shortName(name, "_impl"));
    }
    catch (UnsupportedEncodingException uee) {
      throw new CodeGenerationException("UnsupportedEncodingException: " +
                                        uee.getMessage());
    }
  }

  public static void generateCode(Class cls,
                                  LanguageWriterForFortran writer,
                                  CodeSplicer splicer,
                                  Context context)
    throws CodeGenerationException, NoSuchAlgorithmException
  {
    ImplModule mod = new ImplModule(writer, splicer,cls,context);
    mod.generateCode();
  }

  private void generateWrapObj() throws CodeGenerationException{
    try {
      SymbolID bi_id = d_context.getSymbolTable().
        lookupSymbol(BabelConfiguration.getBaseInterface()).getSymbolID();
      String clsName = d_id.getFullName();
      d_lw.println("recursive subroutine wrapObj_s(private_data, retval, exception)");
      d_lw.tab();
      d_lw.generateUse(Fortran.getSymbolNameForFile(d_id), new TreeMap());
      d_lw.generateUse(Fortran.getSymbolNameForFile(bi_id), new TreeMap());
      
      d_lw.println("implicit none");
      d_lw.writeCommentLine("out "+Fortran.getSymbolNameForFile(d_id)+" retval");
      d_lw.println("type("+Fortran.getTypeName(d_id)+") , intent(out) :: retval");
      d_lw.writeCommentLine("out "+Fortran.getSymbolNameForFile(bi_id)+" exception");
      d_lw.println("type("+Fortran.getTypeName(bi_id)+") , intent(out) :: exception");
      d_lw.writeCommentLine("in "+d_mang.shortName(clsName, "_wrap")+" private_data");
      d_lw.println("type("+d_mang.shortName(clsName, "_wrap")+
                 "), intent(in) :: private_data");
      
      d_lw.println("external "+d_mang.shortName(clsName, "wrapObj","_m"));
      d_lw.println("call "+d_mang.shortName(clsName, "wrapObj","_m")+
                   "(private_data, retval, exception)");
      d_lw.backTab();
      d_lw.println(" end subroutine wrapObj_s");
    } catch (UnsupportedEncodingException uee) {
      throw new CodeGenerationException("UnsupportedEncodingException: " +
                                        uee.getMessage());
    }
  } 
}
