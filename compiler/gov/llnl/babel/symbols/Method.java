// File:        Method.java
// Package:     gov.llnl.babel.symbols
// Revision:    @(#) $Id: Method.java 7409 2011-12-09 01:11:10Z tdahlgren $
// Description: sidl method (modifiers, return type, name, args, and throws)
//
// Copyright (c) 2000-2007, Lawrence Livermore National Security, LLC
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

package gov.llnl.babel.symbols;
import gov.llnl.babel.BabelConfiguration;
import gov.llnl.babel.Context;
import gov.llnl.babel.symbols.ASTNode; 
import gov.llnl.babel.symbols.Argument;
import gov.llnl.babel.symbols.Assertion;
import gov.llnl.babel.symbols.AssertionException;

import gov.llnl.babel.backend.CodeGenerationException;
import gov.llnl.babel.backend.IOR;
import gov.llnl.babel.backend.Utilities;
import gov.llnl.babel.backend.c.C;
import gov.llnl.babel.backend.fortran.Fortran;
import gov.llnl.babel.symbols.Comment;
import gov.llnl.babel.symbols.SymbolID;
import gov.llnl.babel.symbols.SymbolUtilities;
import gov.llnl.babel.symbols.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The <code>Method</code> class defines a SIDL method, including modifiers,
 * return type, name, arguments, and exceptions.  Methods may have one of
 * three definition modifiers: final, static, or abstract.  They may also
 * have one of two communication modifiers: local or oneway.  The return type
 * is either a type or null (which indicates void).  The method name is a
 * simple identifier string.  The optional arguments are an ordered collecton
 * or arguments.  The method may throw any number of exceptions.
 */
public class Method extends ASTNode 
  implements Cloneable, Attributes, IMetadata {
  public static final int NORMAL   = 0; // communication/definition modifier
  public static final int ABSTRACT = 1; // definition modifier
  public static final int FINAL    = 2; // definition modifier
  public static final int STATIC   = 3; // definition modifier
  public static final int LOCAL    = 1; // communication modifier
  public static final int ONEWAY   = 2; // communication modifier
  // communication modifier (set by parser)
  public static final int NONBLOCKING = 3; 
  // communication modifier (set by spawning a NONBLOCKING instance)
  public static final int NONBLOCKING_SEND = 4; 
  // communication modifier (set by spawning a NONBLOCKING instance)
  public static final int NONBLOCKING_RECV = 5; 

  public static final String[] s_def_mod = {
    "", "abstract", "final", "static",
  };

  public static final String[] s_comm_mod = {
    "", "local", "oneway", "nonblocking", "nonblocking_send", "nonblocking_recv",
  };

  private Map         d_attributes = new HashMap();
  private Metadata    d_metadata   = new Metadata(new Date());
  private List        d_arguments;
  private boolean     d_is_builtin;
  private Comment     d_comment;
  private String      d_short_name;      // for langs that support overloading
  private String      d_extension;       // short + extension = long name
  private String      d_long_name;       // keep for convenience
  // if NONBLOCKING_SEND or NONBLOCKING_RECV, this has the original name
  private String      d_blocking_name;   
  private Set         d_references;
  //subset of references, no exceptions
  private Set         d_references_no_exceptions;  
  private Set         d_basicarrays;     // basic array types references
  private Type        d_return_type;
  private Set         d_throws;
  private Set         d_explicit_throws; // subset of d_throws
  private List        d_pre_clause;      // Local precondition clause only
  private List        d_post_clause;     // Local postcondition clause only
  //All the rarray indices required in the arg list	
  private Set         d_rarray_indices;  

  /*
   * A Hashmap of rarray index names to a collection of RarrayInfo objects.  
   * And RarrayInfo objects represents what rarray uses the index, and where.
   */
  private Map         d_rarray_index_map; 
  private SplicerList d_splicers = null;	// Optional splicer blocks
  private Context     d_context;

  /**
   * Create an empty <code>Method</code> object that will be built by
   * calls to other member functions.
   */
  public Method(Context context) {
    d_context                  = context;
    d_arguments                = new ArrayList();
    d_is_builtin               = false;  /* NOT built-in by default */
    d_comment                  = null;
    d_short_name               = null;
    d_extension                = null;
    d_long_name                = null;
    d_references               = new HashSet();
    d_references_no_exceptions = new HashSet();
    d_basicarrays              = new HashSet();
    d_return_type              = null;
    d_throws                   = new TreeSet();
    d_explicit_throws          = new TreeSet();
    d_pre_clause               = new ArrayList();
    d_post_clause              = new ArrayList();
    d_rarray_indices           = null;	
    d_splicers                 = null;
  }

  /**
   * Return a shallow copy of this method object.  This method should
   * never fail since this method is cloneable.  Send in the clones.
   */
  public Method cloneMethod() {
    Method m = null;
    try {
      m = (Method) super.clone();
      m.d_attributes = new HashMap();
      m.d_attributes.putAll(d_attributes);
    } catch (CloneNotSupportedException ex) {
      throw new java.lang.InternalError(ex.getMessage());
    }
    return m;
  }

  private void copyAttributes(Attributes src, Attributes dest)
  {
    Iterator i = src.getAttributes().iterator();
    while(i.hasNext()) {
      String key = (String)i.next();
      dest.setAttribute(key, src.getAttribute(key));
    }
  }

  /**
   * Create the pre hook variant iff hooks are turned on.  Always filters
   * out the return value and the out args
   * 
   * @param withinout If false, all in and inout args are converted to in
   * args only.  If true, inouts remain inout.  out args are always removed.
   * @return new Method or null;
   * @see #spawnNonblockingRecv()
   */
  public Method spawnPreHook(boolean withinout) throws CodeGenerationException {
    Method m = new Method(d_context);
    m.d_is_builtin = true;

    // now largely copy everything... but there are important differences.
    m.d_comment = d_comment;
    m.d_attributes.putAll(d_attributes);
    
    m.d_blocking_name = d_long_name;
    m.d_short_name = d_short_name + IOR.GENERIC_PRE_SUFFIX;
    m.d_extension = d_extension;
    m.d_long_name = d_long_name + IOR.GENERIC_PRE_SUFFIX;
    // FIXME: May need to downselect later.
    m.d_references = new HashSet( d_references);
    m.d_references_no_exceptions = new HashSet( d_references_no_exceptions);
    // FIXME: May need to downselect later.
    m.d_basicarrays = new HashSet(d_basicarrays);

    m.d_throws = new TreeSet( d_throws);
    m.d_explicit_throws = new TreeSet(d_explicit_throws);
    
    /*
     * If copying is simply used to produce the pre-method hooks signatures, 
     * there does not appear to be any (good) reason to include contracts.
     * However, must at least initialize it or null pointer exceptions 
     * can be raised (if don't make sure it's not null).
     */
    m.d_pre_clause  = new ArrayList();
    m.d_post_clause = new ArrayList();
          
    m.d_rarray_indices   = ((d_rarray_indices == null ) ? null 
                                      : new HashSet(d_rarray_indices));
    m.d_rarray_index_map = ((d_rarray_index_map == null ) ? null 
                                      : new HashMap(d_rarray_index_map));

    m.d_arguments = new ArrayList();
    m.d_arguments.addAll(d_arguments);

    /* 
     * now the key part... create a new arglist for the pre hook
     * that optionally converts all arguments to IN mode.  (To
     * avoid the developer changing the arguments inside the hook)
     */
    m.d_arguments = new ArrayList();
    for( Iterator i = d_arguments.iterator(); i.hasNext(); ) { 
      Argument arg = (Argument) i.next();
      if ( arg.getMode() != Argument.OUT ) { 
        Argument newarg = null;
        if(withinout) {
          newarg = new Argument(arg.getMode(), arg.getType(), 
                                arg.getFormalName() );
        } else {
          newarg = new Argument(Argument.IN, arg.getType(), 
                                arg.getFormalName() );
        }
        copyAttributes(arg, newarg);
        m.d_arguments.add(newarg);
      }
    }
    m.d_splicers = null;  /* Do not need clone of splicer block contents */
    m.d_return_type = new Type(Type.VOID);
    return m;
  }

  public Method spawnPreHook() throws CodeGenerationException {
    return spawnPreHook(false);
  }

  /**
   * Create the post hook variant iff hooks are turned on.  The return
   * argument is always converted to an in argument
   * 
   * 
   * @param useCReturn if true, uses the return name given in C.java.  If
   * false, uses the Fortran variant name
   * @param without If false, all out and inout args are converted to in
   * args only.  If true, inouts and outs remain such.
   * @return new Method or null;
   * @see #spawnNonblockingRecv()
   */
  public Method spawnPostHook(boolean useCReturn, boolean without) 
    throws CodeGenerationException
  {
    Method m = new Method(d_context);
    m.d_is_builtin = true;

    // now largely copy everything... but there are important differences.
    m.d_comment = d_comment;
    m.d_attributes.putAll(d_attributes);
    
    m.d_blocking_name = d_long_name;
    m.d_short_name = d_short_name + IOR.GENERIC_POST_SUFFIX;
    m.d_extension = d_extension;
    m.d_long_name = d_long_name + IOR.GENERIC_POST_SUFFIX;
    // FIXME: May need to downselect later.
    m.d_references = new HashSet( d_references);
    m.d_references_no_exceptions = new HashSet( d_references_no_exceptions);
    // FIXME: May need to downselect later.
    m.d_basicarrays = new HashSet(d_basicarrays);
    
    m.d_throws = new TreeSet( d_throws);
    m.d_explicit_throws = new TreeSet( d_explicit_throws);

    /*
     * If copying is simply used to produce the post-method hook signatures, 
     * there does not appear to be any (good) reason to copy contracts.
     * However, must at least initialize it or null pointer exceptions 
     * can be raised (if don't make sure it's not null).
     */
    m.d_pre_clause  = new ArrayList();
    m.d_post_clause = new ArrayList();
          
    m.d_rarray_indices   = ((d_rarray_indices == null ) ? null 
                                      : new HashSet(d_rarray_indices));
    m.d_rarray_index_map = ((d_rarray_index_map == null ) ? null 
                                      : new HashMap(d_rarray_index_map));

    m.d_arguments = new ArrayList();
    m.d_arguments.addAll(d_arguments);

    /* 
     * now the key part... create a new arglist for the post hook
     * that optionally converts all arguments to IN mode. (To
     * avoid the developer changing the arguments inside the hook)
     */
    m.d_arguments = new ArrayList();
    for( Iterator i = d_arguments.iterator(); i.hasNext(); ) { 
      Argument arg    = (Argument) i.next();
      Argument newarg = null;
      if(without) {
        newarg = new Argument(arg.getMode(), arg.getType(), 
                              arg.getFormalName() );
      } else {
        newarg = new Argument(Argument.IN, arg.getType(), arg.getFormalName() );
      }
      copyAttributes(arg, newarg);
      m.d_arguments.add(newarg);
    }
    Type t = d_return_type;
    if ( (t != null) && (t.getType() != Type.VOID) ) {
      Argument a;
      if (useCReturn) {
        a = new Argument(Argument.IN, t, C.FUNCTION_RESULT);
      } else {
        a = new Argument(Argument.IN, t, Fortran.s_return);
      }
      m.addArgument(a);
    }

    m.d_splicers = null;  /* Do not need clone of splicer block contents */
    m.d_return_type = new Type(Type.VOID);
    return m;
  }
  
  public Method spawnPostHook() throws CodeGenerationException {
    return spawnPostHook(true, false);
  }

  /**
   * Create the non-blocking send variant iff method is nonblocking
   * 
   * @return new Method or null;
   * @see #spawnNonblockingRecv()
   */
  public Method spawnNonblockingSend() throws CodeGenerationException {
    if ( getCommunicationModifier() != NONBLOCKING ) { 
      return null;
    }
    Method m       = new Method(d_context);
    m.d_is_builtin = true;

    // now largely copy everything... but there are important differences.
    m.d_comment                  = d_comment;
    m.d_attributes.putAll(d_attributes);
    
    // change NONBLOCKING to NONBLOCKING_SEND
    m.setCommunicationModifier(NONBLOCKING_SEND);
    m.d_blocking_name            = d_long_name;
    m.d_short_name               = d_short_name + "_send";
    m.d_extension                = d_extension;
    m.d_long_name                = d_long_name + "_send";
    // FIXME: May need to downselect later.
    m.d_references               = new HashSet (d_references);
    m.d_references_no_exceptions = new HashSet(d_references_no_exceptions);
    // FIXME: May need to downselect later.
    m.d_basicarrays = new HashSet( d_basicarrays);
    
    // send will always return a local Ticket
    Symbol tmpsym   = Utilities.lookupSymbol(d_context, "sidl.rmi.Ticket");
    m.d_return_type = new Type( tmpsym.getSymbolID(), d_context );  
    
    // send can return exceptions if the send fails
    // (not any exceptions from the recipient)
    TreeSet default_throws = new TreeSet();
    default_throws.add(Utilities.lookupSymbol(d_context, 
                       BabelConfiguration.getRuntimeException()).getSymbolID());
    m.d_throws             = default_throws;
    m.d_explicit_throws    = new TreeSet();

    /*
     * TBD:  Are contracts (to be) generated for the clone?  If so, then
     * also need to copy method-specific contract clauses.
     * Must at least initialize it or null pointer exceptions can be raised 
     * (if don't make sure it's not null).
     */
    m.d_pre_clause       = new ArrayList();
    m.d_post_clause      = new ArrayList();
	  
    m.d_rarray_indices   = ((d_rarray_indices == null ) ? null 
                                      : new HashSet(d_rarray_indices));
    m.d_rarray_index_map = ((d_rarray_index_map == null ) ? null 
                                      : new HashMap(d_rarray_index_map));
    /* 
     * now the key part... create a new arglist for the send
     * this new arglist contains only the in and inout args
     * but they all appear simply as "in"
     */
    m.d_arguments = new ArrayList();
    for( Iterator i = d_arguments.iterator(); i.hasNext(); ) { 
      Argument arg = (Argument) i.next();
      if ( arg.getMode() != Argument.OUT ) { 
        Argument newarg = new Argument(Argument.IN, arg.getType(), 
                                       arg.getFormalName() );
        copyAttributes(arg, newarg);
        m.d_arguments.add(newarg);
      }
    }
    m.d_splicers = null;  /* Do not need clone of splicer block contents */
    return m;
  }
  
  /**
   * Create the non-blocking recv variant iff method is nonblocking
   * 
   * @return new Method or null;
   * @see #spawnNonblockingSend()
   */
  public Method spawnNonblockingRecv() throws CodeGenerationException {
    if ( getCommunicationModifier() != NONBLOCKING ) { 
      return null;
    }
    Method m       = new Method(d_context);
    m.d_is_builtin = true;

    m.d_attributes.putAll(d_attributes);

    // now largely copy everything... but there are important differences.
    m.d_comment                  = d_comment;
    // change NONBLOCKING to NONBLOCKING_RECV
    m.setCommunicationModifier(NONBLOCKING_RECV); 
    m.d_blocking_name            = d_long_name;
    m.d_short_name               = d_short_name + "_recv";
    m.d_extension                = d_extension;
    m.d_long_name                = d_long_name + "_recv";
    m.d_references_no_exceptions = new HashSet( d_references_no_exceptions);

    // FIXME: May need to downselect later
    m.d_basicarrays     = new HashSet(d_basicarrays); 
    m.setReturnCopy(isReturnCopy());
    m.d_return_type     = d_return_type;
    m.d_throws          = new TreeSet(d_throws);
    m.d_explicit_throws = new TreeSet(d_explicit_throws);

    /*
     * TBD:  Are contracts (to be) generated for the clone?  If so, then
     * also need to copy method-specific contract clauses.
     * Must at least initialize it or null pointer exceptions  can be 
     * raised (if don't make sure it's not null).
     */
    m.d_pre_clause       = new ArrayList();
    m.d_post_clause      = new ArrayList();

    /* 
     * now the key part... create a new arglist for the send
     * this new arglist contains only the inout and out args
     * but they all appear simply as "out", the extends of any
     * inout rarray and the ticket from the corresponding send call are both
     * "in."
     */
    m.d_arguments = new ArrayList();
    Symbol ticketSymbol = Utilities.lookupSymbol(d_context, "sidl.rmi.Ticket");
    Type ticketType = new Type( ticketSymbol.getSymbolID(), d_context );
    Argument ticketArg = new Argument(Argument.IN, ticketType, "ticket");
    m.d_arguments.add(ticketArg);
	
    HashSet rarrayIndicies = new HashSet();
    if(hasRarray()) {
      for( Iterator i = d_arguments.iterator(); i.hasNext(); ) { 
        Argument arg = (Argument) i.next();
        if (arg.getType().isRarray()  && 
            arg.getMode() != Argument.IN ) {
          rarrayIndicies.addAll(arg.getType().getArrayIndices());
        }
      }
    }
    m.d_rarray_indices   = ((rarrayIndicies.size() == 0 ) ? null 
                            : rarrayIndicies);

    for( Iterator i = d_arguments.iterator(); i.hasNext(); ) { 
      Argument arg = (Argument) i.next();
      //Include out args and rarray index args
      if ( arg.getMode() != Argument.IN ||
           rarrayIndicies.contains(arg.getFormalName())) {
        m.d_arguments.add(arg);
      }          
    }

    m.d_splicers = null;  /* Do not need clone of splicer block contents */
    if (m.d_rarray_indices != null) {
      m.createRarrayMap();
    }
    return m;
  }

  
  private void _flattenStruct(List list,
                              Map rhs_map,
                              int mode,
                              Attributes attrs,
                              String prefix,
                              String lang_prefix,
                              String field_accessor,
                              Struct s) {
    for(Iterator I=s.getItems().iterator(); I.hasNext(); ) {
      Struct.Item item = (Struct.Item) I.next();
      if(item.getType().isStruct()) {
        _flattenStruct(list,
                       rhs_map,
                       mode,
                       attrs,
                       prefix + "_" + item.getName(),
                       lang_prefix + field_accessor + item.getName(),
                       field_accessor,
                       (Struct)d_context.getSymbolTable().
                       lookupSymbol(item.getType().getSymbolID())); 
      }
      else {
        String name = prefix + "_" + item.getName();
        Argument new_arg = new Argument(mode, item.getType(), name);

        new_arg.setAttribute("F90_flattened_struct_arg");
        copyAttributes(attrs, new_arg);
        list.add(new_arg);
        if(rhs_map != null) {
          rhs_map.put(name, lang_prefix + field_accessor + item.getName());
        }
      }
    }
  }
  
  /**
   * "Flattens" struct arguments in order to pass them in and out of F90
   * modules. The method must have at least one Argument of type
   * struct. Otherwise, we return null. The function also produces a mapping
   * of Arguments to C right-hand-side expressions that can be used to
   * reference fields. If fexprs is true, these rhs expressions will be F90
   * syntax. 
   * 
   * @param  extendedArgs The list of arguments
   * @param  rhs_map      An optional map of argument names to C/F90 expressions
   * @param  fexprs       True, if we are interested in Fortran
   *                      syntax. False, will produce C/C++ expressions.
   * @return new Method or null;
   */
  public Method spawnF90Wrapper(List extendedArgs,
                                Map rhs_map,
                                boolean fexprs)
    throws CodeGenerationException
  {
    if(!hasStruct())
      return null;

    Method m = new Method(d_context);
    m.d_is_builtin = true;

    // now largely copy everything... 
    m.d_comment = d_comment;
    m.d_attributes.putAll(d_attributes);
    
    m.d_blocking_name = d_long_name;
    m.d_short_name = d_short_name + IOR.GENERIC_FLATTENED_SUFFIX;
    m.d_extension = d_extension;
    m.d_long_name = d_long_name + IOR.GENERIC_FLATTENED_SUFFIX;
    m.d_references = new HashSet(d_references);
    m.d_references_no_exceptions = new HashSet(d_references_no_exceptions);
    m.d_basicarrays = new HashSet(d_basicarrays);
    m.d_throws = new TreeSet(d_throws);
    m.d_explicit_throws = new TreeSet(d_explicit_throws);
    m.d_pre_clause  = new ArrayList();
    m.d_post_clause = new ArrayList();
    m.d_rarray_indices   = ((d_rarray_indices == null ) ? null 
                            : new HashSet(d_rarray_indices));
    m.d_rarray_index_map = ((d_rarray_index_map == null ) ? null 
                            : new HashMap(d_rarray_index_map));
    m.d_splicers = null;
    m.d_return_type = d_return_type;
    
    //copy over all arguments, thereby flattening structs recursively
    m.d_arguments = new ArrayList();
    for(Iterator I=extendedArgs.iterator(); I.hasNext(); ) { 
      Argument arg = (Argument) I.next();
      int mode = arg.getMode();
      boolean is_retval = arg.getFormalName().equals(Fortran.s_return);
      boolean is_struct = arg.getType().isStruct();
      String val_expr = arg.getFormalName();
      if ((mode != Argument.IN || is_struct) && !is_retval && !fexprs) {
        val_expr = "*" + val_expr;
        if(is_struct) val_expr = "(" + val_expr + ")";
      }
      if(is_struct) {
        _flattenStruct(m.d_arguments,
                       rhs_map,
                       mode,
                       (Attributes)arg,
                       arg.getFormalName(),
                       val_expr,
                       fexprs ? "%" : ".",
                       (Struct)d_context.getSymbolTable().
                       lookupSymbol(arg.getType().getSymbolID()));
      }
      else {
        Argument new_arg = new Argument(mode,
                                        arg.getType(),
                                        arg.getFormalName());
        copyAttributes(arg, new_arg);
        m.d_arguments.add(new_arg);
        if(rhs_map != null) rhs_map.put(new_arg.getFormalName(), val_expr);
      }
    }
    return m;
  }

  /**
   * Add another argument to the end of the list of method arguments.
   *
   * @param  arg  The argument to be appended to the object's argument list
   */
  public void addArgument(Argument arg) {
    checkFrozen();
    d_arguments.add(arg);
    addTypeReferences(arg.getType());
  }

  /**
   * Return the array of arguments in an <code>List</code>, where each
   * element is an <code>Argument</code>.
   */
  public List getArgumentList() {
    return getArgumentListWithOutIndices();
  }

  /**
   * Return the array of arguments in an <code>List</code> container.
   * This method returns only arguments that are not used as indices to 
   * an Rarray
   */
  public List getArgumentListWithOutIndices() {
    if(d_rarray_indices != null) {
      List ret = new ArrayList(Math.max(d_arguments.size() -
                                        d_rarray_indices.size(),0));
      for(Iterator a = d_arguments.iterator(); a.hasNext();) {
        Argument arg = (Argument) a.next();
        if (!d_rarray_indices.contains(arg.getFormalName())) {
          ret.add(arg);
        }
      }      
      return ret;
    } else {
      return d_arguments;
    }
  }

  /**
   * Return the array of arguments in an <code>List</code> container.
   * This returns all the arguments in the argument list, including those
   * that are indices to an Rarray.
   */
  public List getArgumentListWithIndices() {
    return protectList(d_arguments);
  }


  /**
   * add a required rarray index to the set of indices
   */	
  public void addRarrayIndex(String s) {
    checkFrozen();
    if(d_rarray_indices == null)
      d_rarray_indices = new HashSet();
    d_rarray_indices.add(s);
  }

  /**
   * add a set of required rarray indices to the set of indices
   */	
  public void addRarrayIndex(Collection new_indices) {
    checkFrozen();
    if(d_rarray_indices == null)
      d_rarray_indices = new HashSet();
    d_rarray_indices.addAll(new_indices);
  }
  
  /**
   * Return the set of rarray indices required in the arg list.  
   * (Returns null if there are no indices required)
   */
  public Set getRarrayIndices() {
    return protectSet(d_rarray_indices);
  } 

  /**
   * Returns true if this method has an rarray in it's signature
   */
  public boolean hasRarray() {
    return d_rarray_indices != null;
  }

  /**
   * Returns the number of rarray arguments in the method's argument list.
   */
  public int numRarray() {
    int result = 0;
    Iterator i = d_arguments.iterator();
    while (i.hasNext()) {
      if (((Argument)i.next()).getType().isRarray()) {
        ++result;
      }
    }
    return result;
  }

  /**
   * Returns whether an argument or the return value is of type struct. 
   */
  public boolean hasStruct() {
    if (d_return_type.isStruct())
      return true;
    for(Iterator J = d_arguments.iterator(); J.hasNext();) {
      final Argument a = (Argument)J.next();
      if(a.getType().isStruct())
        return true;
    }
    return false;
  }
  
  /**
   * Return the <code>Type</code> of the argument with the specified formal 
   * name, if any; otherwise, return null.
   *
   * @param  name  The formal argument name whose type is to be returned.
   */
  public Type getArgumentType(String name) {
    Type t = null;
    for (Iterator i=d_arguments.iterator(); (i.hasNext() && (t == null)); ) {
      Argument arg = (Argument) i.next();
      if (arg.getFormalName().equals(name)) {
        t = arg.getType();
      }
    }
    return t;
  }

  /**
   * Return the mode of the argument with the specified formal name, if any; 
   * otherwise, return -1.
   *
   * @param  name  The formal argument name whose type is to be returned.
   */
  public int getArgumentMode(String name) {
    int m = -1;
    for (Iterator i=d_arguments.iterator(); (i.hasNext() && (m == -1)); ) {
      Argument arg = (Argument) i.next();
      if (arg.getFormalName().equals(name)) {
        m = arg.getMode();
      }
    }
    return m;
  }

  /**
   * Set the built-in attribute.
   *
   * @param  is_builtin  TRUE if the method is/should be built-in; otherwise,
   *                     false.
   */
  public void setBuiltIn(boolean is_builtin) {
    checkFrozen();
    d_is_builtin = is_builtin;
  }

  /**
   * Return the built-in attribute for the method.  
   */
  public boolean isBuiltIn() {
    return d_is_builtin;
  }

  /**
   * Set the comment for the method.  
   *
   * @param  comment  The comment associated with the method.  May be null.
   */
  public void setComment(Comment comment) {
    checkFrozen();
    d_comment = comment;
  }

  /**
   * Return the comment for the method.  This may be null if there is no
   * comment.
   */
  public Comment getComment() {
    return d_comment;
  }

  /**
   * Set the communication modifier for the method.  
   *
   * @param  modifier  The method's communication modifier.  Valid values
   *                   are NORMAL, LOCAL, and ONEWAY.
   */
  public void setCommunicationModifier(int modifier) {
    checkFrozen();
    for(int i = 0 ; i < s_comm_mod.length; ++i) {
      if (i == modifier) {
        if (s_comm_mod[i].length() > 0) {
          setAttribute(s_comm_mod[i]);
        }
      }
      else {
        if (hasAttribute(s_comm_mod[i])) {
          removeAttribute(s_comm_mod[i]);
        }
      }
    }
  }

  /**
   * Return the communication modifier for this method.  
   */
  public int getCommunicationModifier() {
    int i;
    for(i = s_comm_mod.length - 1; i > 0; --i) {
      if (hasAttribute(s_comm_mod[i])) return i;
    }
    return i;                   // defaults to 0 normal
  }

  /**
   * Return the communication modifier string for this method.  
   */
  public String getCommunicationModifierString() {
    return s_comm_mod[getCommunicationModifier()];
  }

  /**
   * Set the definition modifier for the method.
   *
   * @param  modifier  The method's definnition modifier.  Valid values are
   *                   NORMAL, ABSTRACT, FINAL, and STATIC.
   */
  public void setDefinitionModifier(int modifier) {
    checkFrozen();
    for(int i = 0; i < s_def_mod.length; ++i) {
      if (i == modifier) {
        if (s_def_mod[i].length() > 0) {
          setAttribute(s_def_mod[i]);
        }
      }
      else {
        if (hasAttribute(s_def_mod[i])) { 
          removeAttribute(s_def_mod[i]);
        }
      }
    }
  }

  /**
   * Return the definition modifier for the method.  
   */
  public int getDefinitionModifier() {
    int i;
    for(i = s_def_mod.length - 1; i > 0; --i){
      if (hasAttribute(s_def_mod[i])) return i;
    }
    return i;
  }

  /**
   * Return the explicit definition modifier string for the method based
   * on the type of extendable in which it belongs.  
   *
   * @param  is_interface  If TRUE, then the extendable is an interface so
   *                       do NOT include ABSTRACT since it is implicit.
   * @return the string associated with the explicit definition modifier
   */
  public String getDefinitionModifier(boolean is_interface) {
    String mod = null;
    if (  (!is_interface)
          || (getDefinitionModifier() == NORMAL) ) {
      mod = s_def_mod[getDefinitionModifier()];
    }
    return mod;
  }

  /**
   * Return TRUE if the method is abstract; otherwise, return FALSE.
   */
  public boolean isAbstract() {
    return getDefinitionModifier() == ABSTRACT;
  }

  /**
   * Return TRUE if the method is final; otherwise, return FALSE.
   */
  public boolean isFinal() {
    return getDefinitionModifier() == FINAL;
  }

  /**
   * Return TRUE if the method is static; otherwise, return FALSE.
   */
  public boolean isStatic() {
    return getDefinitionModifier() == STATIC;
  }

  /**
   * Return TRUE if and only if at least one argument of this method is an 
   * array with an ordering specification.  For example, calling this on 
   * methods without array arguments will return FALSE while calling it on 
   * something like <code>void doIt(in array&lt;int, 2, row-major&gt; x);</code>
   * will return TRUE.
   */
  public boolean hasArrayOrderSpec() {
    if (d_return_type.hasArrayOrderSpec()) return true;
    for(Iterator i = d_arguments.iterator(); i.hasNext(); ){
      if (((Argument)i.next()).hasArrayOrderSpec()) return true;
    }
    return false;
  }

  /**
   * Set the names of the method (a standard SIDL identifier).
   *
   * @param  shortName  The short name of the method
   * @param  extension  The method name extension used in combination
   *                    with the short name to uniquely identify this 
   *                    method
   */
  public void setMethodName(String shortName, String extension) {
    checkFrozen();
    d_short_name = shortName;
    d_extension  = extension;
    d_long_name  = shortName + extension;
  }

  /**
   * Set the names of the method (a standard SIDL identifier).  Use of
   * this method will result in the long and short name being identical.
   *
   * @param  shortName  The short name of the method
   */
  public void setMethodName(String shortName) {
    checkFrozen();
    setMethodName(shortName, "");
  }


  /**
   * This method is used for language bindings that support overloading.
   * For most methods it will return the short name, but if the method 
   * requires the long name for some reason determinable inside the method
   * class, the long name is returned.
   * For example, nonblocking send and receive always use the long method name.
   */
  public String getCorrectMethodName() {
    if(getCommunicationModifier() == NONBLOCKING_SEND ||
       getCommunicationModifier() == NONBLOCKING_RECV) {
      return getLongMethodName();
    } else {
      return getShortMethodName();
    }
  }

  /**
   * Return the short method name (a standard SIDL identifier).
   */
  public String getShortMethodName() {
    return d_short_name;
  }

  /**
   * Return the method name extension (a standard SIDL identifier).
   */
  public String getNameExtension() {
    return d_extension;
  }

  /**
   * Return the long method name (a standard SIDL identifier).
   */
  public String getLongMethodName() {
    return d_long_name;
  }

  /**
   * Return the nonblocking method name (if NONBLOCKING), else return long name
   */
  public String getBlockingMethodName() { 
    final int modifier = getCommunicationModifier();
    if (modifier == NONBLOCKING_SEND || 
        modifier == NONBLOCKING_RECV ) { 	
      return d_blocking_name;
    } else { 
      return d_long_name;
    }
  }
  
  /**
   * Set the copy mode for the return type.  
   *
   * @param  copy  If TRUE then the return interface/class is to be copied 
   *               to the caller; otherwise, it is not.
   */
  public void setReturnCopy(boolean copy) {
    checkFrozen();
    if (copy) {
      d_attributes.put("copy", null);
    }
    else {
      d_attributes.remove("copy");
    }
  }

  /**
   * Return the copy mode for the return type.  
   */
  public boolean isReturnCopy() {
    return d_attributes.containsKey("copy");
  }

  /**
   * Set the return type for the method.  
   *
   * @param  type  The return type to be used.  Note that a void return
   *               type must be represented by a <code>Type.VOID</code>
   *               NOT a null Type reference.
   */
  public void setReturnType(Type type) {
    checkFrozen();
    d_return_type = type;
    addTypeReferences(type);
  }

  /**
   * Return the return type for the method or throw NullPointerException
   */
  public Type getReturnType() {
    d_return_type.getType(); // generates an exception if null
    return d_return_type;
  }

  /**
   * Add a symbol identifier to the list of explicit supported exceptions
   * for this method.  No error checking is performed to ensure that the
   * specified symbol is valid in the throws clause; such checking must be
   * performed by the parser. An explicit exception is one that is listed
   * in the SIDL file rather than the implicit runtime exception.
   *
   * @param  id  The symbol identifier to be added 
   */
  public void addThrows(SymbolID id) {
    checkFrozen();
    d_explicit_throws.add(id);
    addImplicitThrows(id);
  }

  /**
   * Add a symbol identifier to the list of exceptions thrown, but
   * do not treat it as an explicit throw.
   */
  public void addImplicitThrows(SymbolID id ) {
    checkFrozen();
    d_throws.add(id);
    d_references.add(id);
  }

  public boolean isImplicitException(SymbolID id) {
    return d_throws.contains(id) && !d_explicit_throws.contains(id);
  }

  public boolean hasExplicitExceptions() {
    return !d_explicit_throws.isEmpty();
  }

  /**
   * Return the <code>Set</code> of exceptions that may be thrown by this 
   * method.  Each element of the set is a <code>SymbolID</code>.
   */
  public Set getThrows() {
    return protectSet(d_throws);
  }

  /**
   * Return the <code>Set</code> of explicit exceptions that may be thrown
   * by this method. Each element of the set is a <code>SymbolID</code>.
   */
  public Set getExplicitThrows() {
    return protectSet(d_explicit_throws);
  }

  /**
   * Return the <code>Set</code> of implicit exceptions that may be thrown
   * by this method. Each element of the set is a <code>SymbolID</code>.
   */
  public Set getImplicitThrows() {
    TreeSet result = new TreeSet(d_throws);
    result.removeAll(d_explicit_throws);
    return result;
  }

  /**
   * Return the <code>Set</code> of symbols referred to by this method.  
   * Each element of the set is a <code>SymbolID</code>.
   */
  public Set getSymbolReferences() {
    return protectSet(d_references);
  }

  /**
   * Return the <code>Set</code> of symbols referred to by this method,
   * except for the exceptions it throws.
   * Each element of the set is a <code>SymbolID</code>.
   */
  public Set getSymbolReferencesWithoutExceptions() {
    return protectSet(d_references_no_exceptions);
  }


  /**
   * Return the <code>Set</code> of basic array references including
   * arrays of fundamental types such as double, int, etc.  Each element
   * of the set is a <code>SymbolID</code>.
   */
  public Set getBasicArrays() {
    return protectSet(d_basicarrays);
  }


  /**
   * Return the string corresponding to the return type for this method.  Note
   * the string may represent an abbreviated return type (i.e., a type
   * stripped of its package information).
   *
   * @param  parent_pkg  The string containing the parent package.  When not
   *                     null, it is used to strip the package from the return
   *                     string if it is in the specified package.
   * @return             the string containing the possibly abbreviated return
   *                     type.
   */
  public String getReturnType(String parent_pkg) {
    StringBuffer result = new StringBuffer();
    String rtype = d_return_type.getTypeString();
    if (parent_pkg != null) {
      result.append(SymbolUtilities.getSymbolName(rtype, parent_pkg));
    } else {
      result.append(rtype);
    }
    return result.toString();
  }

  private String customAttrStr()
  {
    StringBuffer result = new StringBuffer();
    if (getAttributes().size() > 0) {
      HashSet attrs = new HashSet(getAttributes());
      attrs.remove("copy");
      attrs.remove("static");
      attrs.remove("final");
      attrs.remove("abstract");
      attrs.remove("oneway");
      attrs.remove("local");
      attrs.remove("nonblocking");
      if (attrs.size() > 0) {
        Object[] sorted = attrs.toArray();
        Arrays.sort(sorted);
        result.append("%attrib{");
        for(int i = 0; i < sorted.length; ++i) {
          final String key = sorted[i].toString();
          final String value = getAttribute(key);
          result.append(' ').append(key);
          if (value != null) {
            result.append(" = \"").append(value).append('"');
          }
          if (i+1 < sorted.length) {
            result.append(',');
          }
        }
        result.append(" } ");
      }
    }
    return result.toString();
  }

  /**
   * Return the concatenation of the explicit definition modifier, copy,
   * return type, name and extension.
   *
   * @param  is_interface  TRUE if an interface and want the implicit 
   *                       definition modifier excluded from the result; 
   *                       otherwise, FALSE.
   * @param  parent_pkg    The string containing the parent package.  When not
   *                       null, it is used to strip the package from the return
   *                       string if it is in the specified package.
   * @return               the string containing the preface
   */
  public String getSignaturePreface(boolean is_interface, String parent_pkg) {
    StringBuffer preface = new StringBuffer();

    String dmod = getDefinitionModifier(is_interface);
    if (dmod != null) {
      preface.append(dmod + " ");
    }
    
    String cmod = getCommunicationModifierString();
    if (cmod != null) {
      preface.append(cmod + " ");
    }

    if (isReturnCopy()) {
      preface.append("copy ");
    }
    preface.append(customAttrStr());
    preface.append(getReturnType(parent_pkg));
    preface.append(" ");
    preface.append(d_short_name);
    if ((d_extension != null) && (d_extension != "")) {
      preface.append(" [");
      preface.append(d_extension);
      preface.append("] ");
    }
    return preface.toString();
  }


  /**
   * Return the signature of the method, including the definition modifier
   * based on the extendable type.  Also, optionally abbreviate type if in 
   * specified package.
   *
   * @param  is_interface  TRUE if interface and want the implicit definition
   *                       modifier excluded from the result; otherwise, FALSE
   * @param  parent_pkg    The string containing the parent package.  When not
   *                       null, it is used to strip the package from the return
   *                       string if it is in the specified package.
   * @return               The string containing the full signature.
   */
  public String getSignature(boolean is_interface, String parent_pkg) {
    StringBuffer signature = new StringBuffer();

    signature.append(getSignaturePreface(is_interface, parent_pkg));
    signature.append("(");
    for (Iterator a = d_arguments.iterator(); a.hasNext(); ) {
      signature.append(((Argument) a.next()).getArgumentString());
      if (a.hasNext()) {
        signature.append(", ");
      }
    }
    signature.append(") " + getCommunicationModifierString());

    if (!d_throws.isEmpty()) {
      signature.append(" throws ");
      for (Iterator t = d_throws.iterator(); t.hasNext(); ) {
        SymbolID sid = (SymbolID) t.next();
        String fname = sid.getFullName();
        if (parent_pkg != null) {
          signature.append(SymbolUtilities.getSymbolName(fname, parent_pkg));
        } else {
          signature.append(fname);
        }
        if (t.hasNext()) {
          signature.append(", ");
        }
      }
    }

    return signature.toString();
  }

  /**
   * Return the signature of the method.  The signature does not include
   * the attributes abstract, final, or static.  It also does not abbreviate
   * package names.
   */
  public String getSignature() {
    return getSignature(false, null);
  }

  /**
   * Return TRUE if the signature of the specified method matches that of
   * this method; otherwise, return FALSE.  For signatures to match, the 
   * methods must have the same return types and mode, the same names, the 
   * same arguments, and the same throws clauses.  They must also have the 
   * same communication modifiers.  The signature does not include modifiers 
   * static, abstract, or final.
   *
   * @param  m  The method whose signature is being compared with.
   */
  public boolean sameSignature(Method m) {
    return sameSignature(m, true);
  }

  /**
   * Return TRUE if the signature of the specified method matches that of
   * this method; otherwise, return FALSE.  For signatures to match, the 
   * methods must have the same return types and mode, the same names, the 
   * same arguments, and the same throws clauses.  They must also have the 
   * same communication modifiers.  The signature does not include modifiers 
   * static, abstract, or final.
   *
   * @param  m  The method whose signature is being compared with.
   * @param  compExt True if the comparision should compare method
   * extensions.  (False for from clauses)
   */
  public boolean sameSignature(Method m, boolean compExt) {
    /*
     * Check modifiers, method names, and return types
     */
    if (getCommunicationModifier() != m.getCommunicationModifier()) {
      return false;
    }
    if (!d_short_name.equals(m.d_short_name)) {
      return false;
    }
    if(compExt) {
      if (!d_extension.equals(m.d_extension)) {
        return false;
      }
    }
    if (isReturnCopy() != m.isReturnCopy()) {
      return false;
    }
    if (d_return_type == null) {
      if (m.d_return_type != null) {
        return false;
      }
    } else if (!d_return_type.equals(m.d_return_type)) {
      return false;
    }

    /*
     * Check that the two throws lists are the same
     */
    if (d_throws.size() != m.d_throws.size()) {
      return false;
    }
    for (Iterator i = d_throws.iterator(); i.hasNext(); ) {
      if (!m.d_throws.contains(i.next())) {
        return false;
      }
    }

    return sameArguments(m);
  }

  /**
   * Return TRUE if the base signature of the specified method matches
   * that of this method; otherwise, return FALSE.  For them to match, 
   * the methods must have the same short and the same arguments.
   *
   * @param  m  The method whose base signature is being compared.
   */
  public boolean sameBaseSignature(Method m) {
    /*
     * Check the names
     */
    if (!d_short_name.equals(m.d_short_name)) {
      return false;
    }

    return sameArguments(m);
  }

  public boolean overloadCollision(Method m) {
    return d_short_name.equals(m.d_short_name) && similarArguments(m);
  }

  /**
   * Return TRUE if the arguments of the specified method match those of
   * this method; otherwise, return FALSE.  Note that ordering counts!  
   * That is, the arguments are the same if the same types appear in the 
   * same order.
   *
   * @param  m  The method whose argument list is being compared.
   */
  private boolean sameArguments(Method m) {
    /*
     * Check that the arguments are the same
     */
    int size = d_arguments.size();
    if (size != m.d_arguments.size()) {
      return false;
    } else {
      for (int a = 0; a < size; a++) {
        if (!d_arguments.get(a).equals(m.d_arguments.get(a))) {
          return false;
        }
      }
    }

    return true;
  }

  /** Used to detect overload collisions */
  private boolean similarArguments(Method m) {
    final int size = d_arguments.size();
    if (size != m.d_arguments.size()) return false;
    for (int i = 0; i < size; ++i) {
      Argument arg1 = (Argument)d_arguments.get(i);
      Argument arg2 = (Argument)m.d_arguments.get(i);
      if (!arg1.similar(arg2)) return false;
    }
    return true;
  }

  /**
   * Return an the argument whose formal name matches the passed in string.
   * Return null if the string does not match anything.
   */
  public Argument getArgumentByName(String s) {
    for(Iterator a = d_arguments.iterator(); a.hasNext();) {
      Argument arg = (Argument) a.next();
      if(arg.getFormalName().compareTo(s) == 0)
        return arg;
    }
      
    return null;
  }

  /**
   * Add the symbol identifier of the specified type to the symbol reference
   * set ONLY if the specified type is an enumerated type, an interface, a 
   * class, or an array of these objects.
   *
   * @param  type  The symbol/type to be added
   */
  private void addTypeReferences(Type type) {
    if (type != null) {
      if (type.getType() == Type.SYMBOL) {
        d_references.add(type.getSymbolID());
        d_references_no_exceptions.add(type.getSymbolID());
      } else if (type.getType() == Type.ARRAY) {
        type = type.getArrayType();
        if (null != type) {
          addTypeReferences(type);
          if ((type.getType() >= Type.BOOLEAN) &&
              (type.getType() <= Type.STRING)) {
            Version v = new Version();
            v.appendVersionNumber(0);
            d_basicarrays.add(new SymbolID("sidl." + type.getTypeString(), v));
          }
        }
      }
    }
  }

  /**
   * Add the specified assertion to the proper contract clause for this method.
   *
   * @param   assertion  The assertion to be added.
   * @throws  gov.llnl.babel.symbols.AssertionException
   *                     The exception thrown if the assertion is not valid.
   */
  public void addClauseAssertion(Assertion assertion) throws AssertionException 
  {
    checkFrozen();
    if ( assertion.isPrecondition() ) {
      d_pre_clause.add(assertion);
      try {
        /*
         * ToDo:  This should be added as an implicit exception but
         * more work is needed to get C++ to properly handle additional
         * implicit exceptions beyond RuntimeException.
         */
        if (d_pre_clause.size() <= 0) {
          addThrows(Utilities.lookupSymbol(d_context, 
             BabelConfiguration.getPreconditionViolation()).getSymbolID());
        }
      } catch (CodeGenerationException preCGEx) {
        System.err.println("Babel: Warning: Unable to add sidl.PreViolation "
          + "as implicit exception for " + getLongMethodName() + ".");
      }
    } else if ( assertion.isPostcondition() ) {
      d_post_clause.add(assertion);
      try {
        /*
         * ToDo:  Would prefer to add this as an implicit exception but
         * more work is needed to get C++ to properly handle additional
         * implicit exceptions beyond RuntimeException.
         */
        if (d_post_clause.size() <= 0) {
          addThrows(Utilities.lookupSymbol(d_context, 
             BabelConfiguration.getPostconditionViolation()).getSymbolID());
        }
      } catch (CodeGenerationException postCGEx) {
        System.err.println("Babel: Warning: Unable to add sidl.PostViolation "
          + "as implicit exception for " + getLongMethodName() + ".");
      }
    } else {
      throw new AssertionException("Method: " + d_long_name, "Cannot add a(n) "
                  + "\"" + assertion.getTypeName() + "\" assertion to a "
                  + "contract clause for a method.");
    }
  }

  /**
   * Return an <code>List</code> of <code>Assertion</code>s that make up
   * the precondition clause.
   */
  public List getPreClause() {
    return protectList(d_pre_clause);
  }

  /**
   * Returns TRUE if the method has assertions within its precondition clause; 
   * otherwise, returns FALSE.
   */
  public boolean hasPreClause() {
    return d_pre_clause.size() > 0;
  }

  /**
   * Return an <code>List</code> of <code>Assertion</code>s that make up
   * the postcondition clause.
   */
  public List getPostClause() {
    return protectList(d_post_clause);
  }

  /**
   * Returns TRUE if the method has assertions within its postcondition clause;
   * otherwise, returns FALSE.
   */
  public boolean hasPostClause() {
    return d_post_clause.size() > 0;
  }

  /**
   * Returns TRUE if the clause (provided as a list of assertions) contains
   * at least one method call; returns FALSE otherwise.
   *
   * @param list  Contract clause (as a list of assertions).
   */
  private static boolean contractHasMethodCall(List list) {
    boolean has = false;
    for (Iterator iter = list.iterator(); iter.hasNext() && !has; ) {
      Assertion item = (Assertion)iter.next();
      if (item.hasMethodCall()) {
        has = true;
      }
    }

    return has;
  }

  /**
   * Returns TRUE if the clause (provided as a list of assertions) contains
   * at least one method call; returns FALSE otherwise.
   *
   * @param name  Name of the method of interest.
   * @param list  Contract clause (as a list of assertions).
   */
  private static boolean contractHasMethodCall(String name, List list) {
    boolean has = false;
    for (Iterator iter = list.iterator(); iter.hasNext() && !has; ) {
      Assertion item = (Assertion)iter.next();
      if (item.hasMethodCall(name)) {
        has = true;
      }
    }

    return has;
  }

  /**
   * Returns TRUE if the precondition clause includes at least one method 
   * call; returns FALSE otherwise.
   */
  public boolean preHasMethodCall() {
    return contractHasMethodCall(d_pre_clause);
  }

  /**
   * Returns TRUE if the precondition clause includes at least one call to
   * the specified method; returns FALSE otherwise.
   */
  public boolean preHasMethodCall(String name) {
    return contractHasMethodCall(name, d_pre_clause);
  }

  /**
   * Returns TRUE if the postcondition clause includes at least one method 
   * call; returns FALSE otherwise.
   */
  public boolean postHasMethodCall() {
    return contractHasMethodCall(d_post_clause);
  }

  /**
   * Returns TRUE if the postcondition clause includes at least one call to
   * the specified method; returns FALSE otherwise.
   */
  public boolean postHasMethodCall(String name) {
    return contractHasMethodCall(name, d_post_clause);
  }

  /**
   * Returns TRUE if the clause (provided as a list of assertions) includes
   * at least one result or output argument; returns FALSE otherwise.
   *
   * @param list  Contract clause (as a list of assertions).
   */
  private static boolean clauseHasResultOrOutArg(List list, boolean outOnly) {
    boolean has = false;
    for (Iterator iter = list.iterator(); iter.hasNext() && !has; ) {
      Assertion item = (Assertion)iter.next();
      if (item.hasResultOrOutArg(outOnly)) {
        has = true;
      }
    }
    return has;
  }

  /**
   * Returns TRUE if the precondition clause includes at least one result 
   * or output argument; returns FALSE otherwise. 
   *
   * Note:  Preconditions should _not_ have such arguments (unless the out
   * is inout).
   */
  public boolean preHasResultOrOutArg() {
    return clauseHasResultOrOutArg(d_pre_clause, true);
  }

  /**
   * Returns TRUE if the postcondition clause includes at least one result 
   * or output argument; returns FALSE otherwise.
   */
  public boolean postHasResultOrOutArg() {
    return clauseHasResultOrOutArg(d_post_clause, false);
  }

  /**
   * Returns TRUE if the postcondition clause includes a result argument; 
   * returns FALSE otherwise.
   */
  public boolean postHasResult() {
    boolean has = false;
    for (Iterator iter = d_post_clause.iterator(); iter.hasNext() && !has; ) {
      Assertion post = (Assertion)iter.next();
      if (post.hasResult()) {
        has = true;
      }
    }

    return has;
  }

  /**
   * Return TRUE if PURE clause appears in the assertions associated
   * with this method; otherwise, return FALSE.
   */
  public boolean hasPureAssertion() {
    boolean found = false;
    for (Iterator iter = d_post_clause.iterator(); iter.hasNext() && !found; ) {
      Assertion post = (Assertion)iter.next();
      if (post.hasPureClause()) {
        found = true;
      }
    }

    return found;
  }

  /**
   * Returns the default complexity of the contract clause (provided as a
   * list of assertions); returns 0 (for constant-time) if none.
   *
   * @param list  Contract clause (as a list of assertions).
   */
  private static int getDefaultClauseComplexity(List list) {
    int def  = 0;
    int comp = 0;

    for (Iterator iter = list.iterator(); iter.hasNext(); ) {
      Assertion item = (Assertion)iter.next();
      comp           = item.getDefaultComplexity();
      if (def < comp) {
        def = comp;
      }
    }

    return def;
  }

  /**
   * Returns the default complexity of the precondition clause; returns 0 
   * (for constant-time) if none.
   */
  public int getPreDefaultComplexity() {
    return getDefaultClauseComplexity(d_pre_clause);
  }

  /**
   * Returns the default complexity of the postcondition clause; returns 0 
   * (for constant-time) if none.
   */
  public int getPostDefaultComplexity() {
    return getDefaultClauseComplexity(d_post_clause);
  }

  /**
   * Return TRUE if the contract clause (provided as a list of assertions) has 
   * the specified built-in method call; FALSE otherwise.
   */
  private static boolean clauseHasBuiltinMethod(List list, int type) {
    boolean hasIt = false;
    for (Iterator iter = list.iterator(); iter.hasNext() && !hasIt; ) {
      Assertion item = (Assertion)iter.next();
      if (item.hasBuiltinMethod(type)) {
        hasIt = true;
      }
    }
    return hasIt;
  }

  /**
   * Returns TRUE if the a built-in assertion method call of the specified type
   * is included in either contract clause; returns FALSE otherwise.
   */
  public boolean contractHasBuiltinMethod(int type) {
    boolean hasIt = clauseHasBuiltinMethod(d_pre_clause, type);
    if (!hasIt) {
      hasIt = clauseHasBuiltinMethod(d_post_clause, type);
    }
    return hasIt;
  }

  /**
   * Returns TRUE if the contract clause (specified as a list of assertions)
   * has user-defined method call(s); returns FALSE otherwise.
   */
  private boolean clauseHasUserDefinedMethod(List list, boolean any) {
    boolean hasIt = false;
    for (Iterator iter = list.iterator(); iter.hasNext() && !hasIt; ) {
      Assertion item = (Assertion) iter.next();
      if (item.hasUserDefinedMethod(any)) {
        hasIt = true;
      }
    }
    return hasIt;
  }

  /**
   * Returns TRUE if the contract calls any user-defined methods (when any is 
   * TRUE) or has a user-defined method with a throws clause (if any is FALSE) 
   * in either of its contract clauses; otherwise, returns FALSE.
   */
  public boolean contractHasUserDefinedMethod(boolean any) {
    boolean hasIt = clauseHasUserDefinedMethod(d_pre_clause, any);
    if (!hasIt) {
      hasIt = clauseHasUserDefinedMethod(d_post_clause, any);
    }
    return hasIt;
  }

  /**
   * Return the number of array iteration macros --- contained in the contract
   * --- that return the specified type.  Valid types are given in 
   * MethodCall.java as MACRO_RETURNS_* values.
   */
  public int getMaxArrayIterMacros(char type) {
    int num;
    Assertion as;
    int max = 0;

    Iterator i = d_pre_clause.iterator();
    while (i.hasNext()) {
      as  = (Assertion)i.next();
      num = as.getNumArrayIterMacrosByType(type);
      if (num > max) {
        max = num;
      }
    }

    i = d_post_clause.iterator();
    while (i.hasNext()) {
      as  = (Assertion)i.next();
      num = as.getNumArrayIterMacrosByType(type);
      if (num > max) {
        max = num;
      }
    }

    return max;
  }

  /**
   * This returns an array list of RarrayInfo objects that contains the info
   * for every rarray index used in this method.  
   */
  public Map getRarrayInfo() {
    if(d_rarray_index_map == null)
      createRarrayMap();
    return protectMap(d_rarray_index_map);
  }

  private void createRarrayMap() {
    d_rarray_index_map = new HashMap();
    Iterator i = d_arguments.iterator();
    while (i.hasNext()) {
      Argument a = (Argument)i.next();
      Type argType = a.getType();
      if (argType.isRarray()) {
        Iterator j = argType.getArrayIndexExprs().iterator();
        int position = 0;
        while (j.hasNext()) {
          AssertionExpression ae = (AssertionExpression)j.next();
          Set indices = (Set)ae.accept(new RarrayIndices(), new HashSet());
          Iterator k = indices.iterator();
          while (k.hasNext()) {
            String indexName = k.next().toString();
            Set indexSet = (Set) d_rarray_index_map.get(indexName);
            if (indexSet == null) {
              indexSet = new HashSet();
              d_rarray_index_map.put(indexName, indexSet);
            }
          
            Argument index_arg = null;
            for(Iterator a1 = d_arguments.iterator(); a1.hasNext();) {
              Argument arg = (Argument) a1.next();
              if(indexName.compareTo(arg.getFormalName()) == 0) {
                index_arg = arg;
                break;
              }
            }
            indexSet.add(new RarrayInfo(a, index_arg, position));
          }
          ++position;
        }
      }
    }
  }

  public int hashCode() {
    return d_long_name.hashCode() +
      d_short_name.hashCode() +
      getCommunicationModifier() +
      d_throws.hashCode() +
      d_arguments.hashCode() +
      (isReturnCopy() ? 1 : 0);
  }

  public boolean equals(Object o) {
    if (o instanceof Method) { 
      Method m = (Method)o;
      return (getCommunicationModifier() == m.getCommunicationModifier()) &&
        (isReturnCopy() == m.isReturnCopy()) &&
        d_arguments.equals(m.d_arguments) &&
        d_return_type.equals(m.d_return_type) &&
        d_long_name.equals(m.d_long_name) &&
        d_short_name.equals(m.d_short_name) &&
        d_throws.equals(m.d_throws) &&
        d_explicit_throws.equals(m.d_explicit_throws);
    } else { 
      return false;
    }
  }

  
  public static class RarrayInfo {
    
    public final Argument rarray;
    public final Argument index;
    public final int dim;

    public RarrayInfo(Argument r, Argument i, int d) {
      rarray = r;
      index = i;
      dim = d;
    }
    
    public int hashCode() {
      return rarray.getFormalName().hashCode() 
           + index.getFormalName().hashCode() + dim;
    }

    public boolean equals(Object o) {
      if ( o instanceof RarrayInfo ) { 
        RarrayInfo r = (RarrayInfo)o;
        return  ( r.rarray == rarray &&
                  r.index == index &&
                  r.dim == dim);
      } else { 
        return false;
      }
    }
  }

  public boolean hasAttribute(String key)
  {
    return d_attributes.containsKey(key);
  }

  public String getAttribute(String key)
  {
    if (hasAttribute(key)) {
      return (String)d_attributes.get(key);
    }
    throw new UnknownAttributeException("Method does not have attribute " 
                                       + key);
  }

  public void setAttribute(String key)
  {
    checkFrozen();
    setAttribute(key, null);
  }

  public void setAttribute(String key, String value)
  {
    checkFrozen();
    d_attributes.put(key, value);
  }
  
  public void removeAttribute(String key) throws UnknownAttributeException
  {
    checkFrozen();
    if (hasAttribute(key)) {
      d_attributes.remove(key);
    }
    else {
      throw new UnknownAttributeException("Method does not have attribute " 
                                        + key);
    }
  }
  
  public Set getAttributes()
  {
    return protectSet(d_attributes.keySet());
  }

  public Metadata getMetadata() {
    return d_metadata;
  }

  public void addMetadata(String keyword, String value)
  {
    checkFrozen();
    d_metadata.addMetadata(keyword, value);
  }

   /**
    * Add the specified contents to the splicer block identified by the
    * location and name.
    */
  public void addSplicerContents(String location, String name, String impl) {
    checkFrozen();
    if (d_splicers == null) {
      d_splicers = new SplicerList();
    }
    d_splicers.addSplicerContents(location, name, impl);
  }

  /**
   * Return splicer blocks for the specified location.
   */
  public ArrayList getSplicerBlocks(String location) {
    return (d_splicers != null) ? d_splicers.getSplicerBlocks(location) : null;
  }

  /**
   * Return splicer contents for the specified location and splicer name.
   */
  public ArrayList getSplicerContents(String location, String name) {
    return (d_splicers != null) ? d_splicers.getSplicerContents(location, name) 
                                : null;
  }

  public void freeze()
  {
    if (!d_frozen) {
      super.freeze();
      if (d_comment != null) d_comment.freeze();
      if (d_return_type != null) d_return_type.freeze();
      Iterator i = d_references.iterator();
      while (i.hasNext()) {
        SymbolID id = (SymbolID)i.next();
        id.freeze();
      }
      i = d_arguments.iterator();
      while (i.hasNext()) {
        Argument arg = (Argument)i.next();
        arg.freeze();
      }
      i = d_throws.iterator();
      while (i.hasNext()) {
        SymbolID id = (SymbolID)i.next();
        id.freeze();
      }
      d_attributes               = protectMap(d_attributes);
      d_arguments                = protectList(d_arguments);
      d_references               = protectSet(d_references);
      d_references_no_exceptions = protectSet(d_references_no_exceptions);
      d_basicarrays              = protectSet(d_basicarrays);
      d_throws                   = protectSet(d_throws);
      d_explicit_throws          = protectSet(d_explicit_throws);
      d_pre_clause               = protectList(d_pre_clause);
      d_post_clause              = protectList(d_post_clause);
      d_rarray_indices           = protectSet(d_rarray_indices);
      d_rarray_index_map         = protectMap(d_rarray_index_map);
    }
  }
}
