/*
 * File:          Basic_Impl.java
 * Symbol:        Args.Basic-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for Args.Basic
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

package Args;

import sidl.BaseClass;
import sidl.BaseInterface;
import sidl.ClassInfo;
import sidl.RuntimeException;

// DO-NOT-DELETE splicer.begin(Args.Basic._imports)
// Insert-Code-Here {Args.Basic._imports} (additional imports)
// DO-NOT-DELETE splicer.end(Args.Basic._imports)

/**
 * Symbol "Args.Basic" (version 1.0)
 */
public class Basic_Impl extends Basic
{

  // DO-NOT-DELETE splicer.begin(Args.Basic._data)
  // Insert-Code-Here {Args.Basic._data} (private data)
  // DO-NOT-DELETE splicer.end(Args.Basic._data)


  static { 
  // DO-NOT-DELETE splicer.begin(Args.Basic._load)
  // Insert-Code-Here {Args.Basic._load} (class initialization)
  // DO-NOT-DELETE splicer.end(Args.Basic._load)

  }

  /**
   * User defined constructor
   */
  public Basic_Impl(long IORpointer){
    super(IORpointer);
    // DO-NOT-DELETE splicer.begin(Args.Basic.Basic)
    // Insert-Code-Here {Args.Basic.Basic} (constructor)
    // DO-NOT-DELETE splicer.end(Args.Basic.Basic)

  }

  /**
   * Back door constructor
   */
  public Basic_Impl(){
    d_ior = _wrap(this);
    // DO-NOT-DELETE splicer.begin(Args.Basic._wrap)
    // Insert-Code-Here {Args.Basic._wrap} (_wrap)
    // DO-NOT-DELETE splicer.end(Args.Basic._wrap)

  }

  /**
   * User defined destructing method
   */
  public void dtor() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Args.Basic._dtor)
    // Insert-Code-Here {Args.Basic._dtor} (destructor)
    // DO-NOT-DELETE splicer.end(Args.Basic._dtor)

  }

  /**
   * finalize method (Only use this if you're sure you need it!)
   */
  public void finalize() throws Throwable{
    // DO-NOT-DELETE splicer.begin(Args.Basic.finalize)
    // Insert-Code-Here {Args.Basic.finalize} (finalize)
    // DO-NOT-DELETE splicer.end(Args.Basic.finalize)

  }

  // user defined static methods: (none)

  // user defined non-static methods:
  /**
   * Method:  returnbackbool[]
   */
  public boolean returnbackbool_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.returnbackbool)
    // insert implementation here
    return true;
    // DO-NOT-DELETE splicer.end(Args.Basic.returnbackbool)

  }

  /**
   * Method:  passinbool[]
   */
  public boolean passinbool_Impl (
    /*in*/ boolean b ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passinbool)
    // insert implementation here
    return b;
    // DO-NOT-DELETE splicer.end(Args.Basic.passinbool)

  }

  /**
   * Method:  passoutbool[]
   */
  public boolean passoutbool_Impl (
    /*out*/ sidl.Boolean.Holder b ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passoutbool)
    // insert implementation here
      b.set(true);
      return b.get();
    // DO-NOT-DELETE splicer.end(Args.Basic.passoutbool)

  }

  /**
   * Method:  passinoutbool[]
   */
  public boolean passinoutbool_Impl (
    /*inout*/ sidl.Boolean.Holder b ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passinoutbool)
    // insert implementation here
      b.set(false);
      return !b.get();
    // DO-NOT-DELETE splicer.end(Args.Basic.passinoutbool)

  }

  /**
   * Method:  passeverywherebool[]
   */
  public boolean passeverywherebool_Impl (
    /*in*/ boolean b1,
    /*out*/ sidl.Boolean.Holder b2,
    /*inout*/ sidl.Boolean.Holder b3 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherebool)
    // insert implementation here
      b2.set(true);
      b3.set(true);
    return b1;
    // DO-NOT-DELETE splicer.end(Args.Basic.passeverywherebool)

  }

  /**
   * Method:  returnbackchar[]
   */
  public char returnbackchar_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.returnbackchar)
    // insert implementation here
    return '3';
    // DO-NOT-DELETE splicer.end(Args.Basic.returnbackchar)

  }

  /**
   * Method:  passinchar[]
   */
  public boolean passinchar_Impl (
    /*in*/ char c ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passinchar)
    // insert implementation here
      if(c == '3')
	  return true;
      else
	  return false;
    // DO-NOT-DELETE splicer.end(Args.Basic.passinchar)

  }

  /**
   * Method:  passoutchar[]
   */
  public boolean passoutchar_Impl (
    /*out*/ sidl.Character.Holder c ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passoutchar)
    // insert implementation here
      if(c != null){
	  c.set('3');
	  return true;
      }
      else{
	  c.set('\0');
	  return false;
      }
    // DO-NOT-DELETE splicer.end(Args.Basic.passoutchar)

  }

  /**
   * Method:  passinoutchar[]
   */
  public boolean passinoutchar_Impl (
    /*inout*/ sidl.Character.Holder c ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passinoutchar)
    // insert implementation here
      if(c == null)
	  return false;

      // Uppercase
      if(c.get() < 97) {
	  c.set((char)(c.get() + 32));
	  return true;
      }
      else { // Lowercase
	  c.set((char)(c.get() - 32));
	  return true;
      }
    // DO-NOT-DELETE splicer.end(Args.Basic.passinoutchar)

  }

  /**
   * Method:  passeverywherechar[]
   */
  public char passeverywherechar_Impl (
    /*in*/ char c1,
    /*out*/ sidl.Character.Holder c2,
    /*inout*/ sidl.Character.Holder c3 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherechar)
    // insert implementation here
      c2.set('\0');
      if(c3 == null)
	  return '\0';
      c3.set('A');
      c2.set('3');
      if(c1 == '3')
	  return '3';
      else
	  return '\0';
    // DO-NOT-DELETE splicer.end(Args.Basic.passeverywherechar)

  }

  /**
   * Method:  returnbackint[]
   */
  public int returnbackint_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.returnbackint)
    // insert implementation here
    return 3;
    // DO-NOT-DELETE splicer.end(Args.Basic.returnbackint)

  }

  /**
   * Method:  passinint[]
   */
  public boolean passinint_Impl (
    /*in*/ int i ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passinint)
    // insert implementation here
      if (i == 3)
	  return true;
      else 
	  return false;
    // DO-NOT-DELETE splicer.end(Args.Basic.passinint)

  }

  /**
   * Method:  passoutint[]
   */
  public boolean passoutint_Impl (
    /*out*/ sidl.Integer.Holder i ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passoutint)
    // insert implementation here
      if (i != null) {
	  i.set(3);
	  return true;
      }
      else {
	  return false;
      }
    // DO-NOT-DELETE splicer.end(Args.Basic.passoutint)

  }

  /**
   * Method:  passinoutint[]
   */
  public boolean passinoutint_Impl (
    /*inout*/ sidl.Integer.Holder i ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passinoutint)
    // insert implementation here
      if (i != null){
	  i.set(-i.get());
	  return true;
      }
      else {
	  return false;
      }
    // DO-NOT-DELETE splicer.end(Args.Basic.passinoutint)

  }

  /**
   * Method:  passeverywhereint[]
   */
  public int passeverywhereint_Impl (
    /*in*/ int i1,
    /*out*/ sidl.Integer.Holder i2,
    /*inout*/ sidl.Integer.Holder i3 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passeverywhereint)
    // insert implementation here
      i2.set(0);
      if(i3 == null)
	  return 0;
      i3.set(-i3.get());
      i2.set(3);
      if(i1 == 3)
	  return 3;
      else
	  return 0;
    // DO-NOT-DELETE splicer.end(Args.Basic.passeverywhereint)

  }

  /**
   * Method:  returnbacklong[]
   */
  public long returnbacklong_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.returnbacklong)
    // insert implementation here
    return 3;
    // DO-NOT-DELETE splicer.end(Args.Basic.returnbacklong)

  }

  /**
   * Method:  passinlong[]
   */
  public boolean passinlong_Impl (
    /*in*/ long l ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passinlong)
    // insert implementation here
      if (l == 3)
	  return true;
      else 
	  return false;
    // DO-NOT-DELETE splicer.end(Args.Basic.passinlong)

  }

  /**
   * Method:  passoutlong[]
   */
  public boolean passoutlong_Impl (
    /*out*/ sidl.Long.Holder l ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passoutlong)
    // insert implementation here
       if (l != null) {
	  l.set(3);
	  return true;
      }
      else {
	  return false;
      }

    // DO-NOT-DELETE splicer.end(Args.Basic.passoutlong)

  }

  /**
   * Method:  passinoutlong[]
   */
  public boolean passinoutlong_Impl (
    /*inout*/ sidl.Long.Holder l ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passinoutlong)
    // insert implementation here
       if (l != null){
	  l.set(-l.get());
	  return true;
      }
      else {
	  return false;
      }
    // DO-NOT-DELETE splicer.end(Args.Basic.passinoutlong)

  }

  /**
   * Method:  passeverywherelong[]
   */
  public long passeverywherelong_Impl (
    /*in*/ long l1,
    /*out*/ sidl.Long.Holder l2,
    /*inout*/ sidl.Long.Holder l3 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherelong)
    // insert implementation here
       l2.set(0);
      if(l3 == null)
	  return 0;
      l3.set(-l3.get());
      l2.set(3);
      if(l1 == 3)
	  return 3;
      else
	  return 0;
 
    // DO-NOT-DELETE splicer.end(Args.Basic.passeverywherelong)

  }

  /**
   * Method:  returnbackfloat[]
   */
  public float returnbackfloat_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.returnbackfloat)
    // insert implementation here
    return 3.1f;

    // DO-NOT-DELETE splicer.end(Args.Basic.returnbackfloat)

  }

  /**
   * Method:  passinfloat[]
   */
  public boolean passinfloat_Impl (
    /*in*/ float f ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passinfloat)
    // insert implementation here
      if (f == 3.1f)
	  return true;
      else 
	  return false;

    // DO-NOT-DELETE splicer.end(Args.Basic.passinfloat)

  }

  /**
   * Method:  passoutfloat[]
   */
  public boolean passoutfloat_Impl (
    /*out*/ sidl.Float.Holder f ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passoutfloat)
    // insert implementation here
      if (f != null) {
	  f.set(3.1f);
	  return true;
      }
      else {
	  return false;
      }

    // DO-NOT-DELETE splicer.end(Args.Basic.passoutfloat)

  }

  /**
   * Method:  passinoutfloat[]
   */
  public boolean passinoutfloat_Impl (
    /*inout*/ sidl.Float.Holder f ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passinoutfloat)
    // insert implementation here
      if (f != null){
	  f.set(-f.get());
	  return true;
      }
      else {
	  return false;
      }

    // DO-NOT-DELETE splicer.end(Args.Basic.passinoutfloat)

  }

  /**
   * Method:  passeverywherefloat[]
   */
  public float passeverywherefloat_Impl (
    /*in*/ float f1,
    /*out*/ sidl.Float.Holder f2,
    /*inout*/ sidl.Float.Holder f3 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherefloat)
    // insert implementation here
      f2.set(0);
      if(f3 == null)
	  return 0;
      f3.set(-f3.get());
      f2.set(3.1f);
      if(f1 == 3.1f)
	  return 3.1f;
      else
	  return 0;
    // DO-NOT-DELETE splicer.end(Args.Basic.passeverywherefloat)

  }

  /**
   * Method:  returnbackdouble[]
   */
  public double returnbackdouble_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.returnbackdouble)
    // insert implementation here
    return 3.14;
    // DO-NOT-DELETE splicer.end(Args.Basic.returnbackdouble)

  }

  /**
   * Method:  passindouble[]
   */
  public boolean passindouble_Impl (
    /*in*/ double d ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passindouble)
    // insert implementation here
      if (d == 3.14)
	  return true;
      else 
	  return false;
    // DO-NOT-DELETE splicer.end(Args.Basic.passindouble)

  }

  /**
   * Method:  passoutdouble[]
   */
  public boolean passoutdouble_Impl (
    /*out*/ sidl.Double.Holder d ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passoutdouble)
    // insert implementation here
       if (d != null) {
	  d.set(3.14);
	  return true;
      }
      else {
	  return false;
      }
    // DO-NOT-DELETE splicer.end(Args.Basic.passoutdouble)

  }

  /**
   * Method:  passinoutdouble[]
   */
  public boolean passinoutdouble_Impl (
    /*inout*/ sidl.Double.Holder d ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passinoutdouble)
    // insert implementation here
      if (d != null){
	  d.set(-d.get());
	  return true;
      }
      else {
	  return false;
      }
    // DO-NOT-DELETE splicer.end(Args.Basic.passinoutdouble)

  }

  /**
   * Method:  passeverywheredouble[]
   */
  public double passeverywheredouble_Impl (
    /*in*/ double d1,
    /*out*/ sidl.Double.Holder d2,
    /*inout*/ sidl.Double.Holder d3 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passeverywheredouble)
    // insert implementation here
      d2.set(0);
      if(d3 == null)
	  return 0;
      d3.set(-d3.get());
      d2.set(3.14);
      if(d1 == 3.14)
	  return 3.14;
      else
	  return 0;
    // DO-NOT-DELETE splicer.end(Args.Basic.passeverywheredouble)

  }

  /**
   * Method:  returnbackfcomplex[]
   */
  public sidl.FloatComplex returnbackfcomplex_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.returnbackfcomplex)
    // insert implementation here
    return new sidl.FloatComplex(3.1f,3.1f);
    // DO-NOT-DELETE splicer.end(Args.Basic.returnbackfcomplex)

  }

  /**
   * Method:  passinfcomplex[]
   */
  public boolean passinfcomplex_Impl (
    /*in*/ sidl.FloatComplex c ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passinfcomplex)
    // insert implementation here
      float real = sidl.FloatComplex.real(c);
      float imag = sidl.FloatComplex.imag(c);
      if(real == 3.1f && imag == 3.1f) {
	  return true;
      }
      else 
	  return false;
    // DO-NOT-DELETE splicer.end(Args.Basic.passinfcomplex)

  }

  /**
   * Method:  passoutfcomplex[]
   */
  public boolean passoutfcomplex_Impl (
    /*out*/ sidl.FloatComplex.Holder c ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passoutfcomplex)
    // insert implementation here
      if(c != null){
        c.set(new sidl.FloatComplex(3.1f,3.1f));
        c.get().set(3.1f,3.1f);
        return true;
      
      }
      else
	  return false;
    // DO-NOT-DELETE splicer.end(Args.Basic.passoutfcomplex)

  }

  /**
   * Method:  passinoutfcomplex[]
   */
  public boolean passinoutfcomplex_Impl (
    /*inout*/ sidl.FloatComplex.Holder c ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passinoutfcomplex)
    // insert implementation here
      if(c != null){
	  c.get().set(c.get().real(),-c.get().imag());
	  return true;
      }
      else
	  return false;
    // DO-NOT-DELETE splicer.end(Args.Basic.passinoutfcomplex)

  }

  /**
   * Method:  passeverywherefcomplex[]
   */
  public sidl.FloatComplex passeverywherefcomplex_Impl (
    /*in*/ sidl.FloatComplex c1,
    /*out*/ sidl.FloatComplex.Holder c2,
    /*inout*/ sidl.FloatComplex.Holder c3 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passeverywherefcomplex)
    // insert implementation here
    
    c2.set(new sidl.FloatComplex(0.0f, 0.0f));
    if(c3 == null)
      return null;
    c3.get().set(c3.get().real(),-c3.get().imag());
    c2.get().set(3.1f,3.1f); 
    
    if(c1.real() == 3.1f && c1.imag() == 3.1f)
      return new sidl.FloatComplex(3.1f,3.1f);
    else 
      return null;
    // DO-NOT-DELETE splicer.end(Args.Basic.passeverywherefcomplex)

  }

  /**
   * Method:  returnbackdcomplex[]
   */
  public sidl.DoubleComplex returnbackdcomplex_Impl () 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.returnbackdcomplex)
    // insert implementation here
      return new sidl.DoubleComplex(3.14,3.14);
    // DO-NOT-DELETE splicer.end(Args.Basic.returnbackdcomplex)

  }

  /**
   * Method:  passindcomplex[]
   */
  public boolean passindcomplex_Impl (
    /*in*/ sidl.DoubleComplex c ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passindcomplex)
    // insert implementation here
      double real = sidl.DoubleComplex.real(c);
      double imag = sidl.DoubleComplex.imag(c);
      if(real == 3.14 && imag == 3.14) {
	  return true;
      }
      else 
	  return false;
    // DO-NOT-DELETE splicer.end(Args.Basic.passindcomplex)

  }

  /**
   * Method:  passoutdcomplex[]
   */
  public boolean passoutdcomplex_Impl (
    /*out*/ sidl.DoubleComplex.Holder c ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passoutdcomplex)
    // insert implementation here
    
      if(c != null){
        c.set(new sidl.DoubleComplex(3.14,3.14));
        return true;
      }
      else
        return false;
    // DO-NOT-DELETE splicer.end(Args.Basic.passoutdcomplex)

  }

  /**
   * Method:  passinoutdcomplex[]
   */
  public boolean passinoutdcomplex_Impl (
    /*inout*/ sidl.DoubleComplex.Holder c ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passinoutdcomplex)
    // insert implementation here
      if(c != null){
	  c.get().set(c.get().real(),-c.get().imag());
	  return true;
      }
      else
	  return false;
    // DO-NOT-DELETE splicer.end(Args.Basic.passinoutdcomplex)

  }

  /**
   * Method:  passeverywheredcomplex[]
   */
  public sidl.DoubleComplex passeverywheredcomplex_Impl (
    /*in*/ sidl.DoubleComplex c1,
    /*out*/ sidl.DoubleComplex.Holder c2,
    /*inout*/ sidl.DoubleComplex.Holder c3 ) 
    throws sidl.RuntimeException.Wrapper
  {
    // DO-NOT-DELETE splicer.begin(Args.Basic.passeverywheredcomplex)
    // insert implementation here
    
       c2.set(new sidl.DoubleComplex(0.0, 0.0));
       if(c3 == null)
         return null;
       c3.get().set(c3.get().real(),-c3.get().imag());
       c2.get().set(3.14,3.14); 
       
       if(c1.real() == 3.14 && c1.imag() == 3.14)
         return new sidl.DoubleComplex(3.14,3.14);
       else 
         return null;
    // DO-NOT-DELETE splicer.end(Args.Basic.passeverywheredcomplex)

  }


  // DO-NOT-DELETE splicer.begin(Args.Basic._misc)
  // Insert-Code-Here {Args.Basic._misc} (miscellaneous)
  // DO-NOT-DELETE splicer.end(Args.Basic._misc)

} // end class Basic

/**
 * ================= BEGIN UNREFERENCED METHOD(S) ================
 * The following code segment(s) belong to unreferenced method(s).
 * This can result from a method rename/removal in the sidl file.
 * Move or remove the code in order to compile cleanly.
 */
// DO-NOT-DELETE splicer.begin(Args.Basic.finalizefcomplex)
    // Insert-Code-Here {Args.Cfcomplex.finalize} (finalize)
// DO-NOT-DELETE splicer.end(Args.Basic.finalizefcomplex)
// DO-NOT-DELETE splicer.begin(Args.Basic.finalizebool)
    // Insert-Code-Here {Args.Cbool.finalize} (finalize)
// DO-NOT-DELETE splicer.end(Args.Basic.finalizebool)
// DO-NOT-DELETE splicer.begin(Args.Basic.finalizedcomplex)
    // Insert-Code-Here {Args.Cdcomplex.finalize} (finalize)
// DO-NOT-DELETE splicer.end(Args.Basic.finalizedcomplex)
// DO-NOT-DELETE splicer.begin(Args.Basic.finalizeint)
    // Insert-Code-Here {Args.Cint.finalize} (finalize)
// DO-NOT-DELETE splicer.end(Args.Basic.finalizeint)
// DO-NOT-DELETE splicer.begin(Args.Basic.Ccharchar)
    // add construction details here
// DO-NOT-DELETE splicer.end(Args.Basic.Ccharchar)
// DO-NOT-DELETE splicer.begin(Args.Basic.Cfcomplexfcomplex)
    // add construction details here
// DO-NOT-DELETE splicer.end(Args.Basic.Cfcomplexfcomplex)
// DO-NOT-DELETE splicer.begin(Args.Basic.Cfloatfloat)
    // add construction details here
// DO-NOT-DELETE splicer.end(Args.Basic.Cfloatfloat)
// DO-NOT-DELETE splicer.begin(Args.Basic.Clonglong)
    // add construction details here
// DO-NOT-DELETE splicer.end(Args.Basic.Clonglong)
// DO-NOT-DELETE splicer.begin(Args.Basic.Cboolbool)
    // add construction details here
// DO-NOT-DELETE splicer.end(Args.Basic.Cboolbool)
// DO-NOT-DELETE splicer.begin(Args.Basic.Cdcomplexdcomplex)
    // add construction details here
// DO-NOT-DELETE splicer.end(Args.Basic.Cdcomplexdcomplex)
// DO-NOT-DELETE splicer.begin(Args.Basic.finalizechar)
    // Insert-Code-Here {Args.Cchar.finalize} (finalize)
// DO-NOT-DELETE splicer.end(Args.Basic.finalizechar)
// DO-NOT-DELETE splicer.begin(Args.Basic.finalizedouble)
    // Insert-Code-Here {Args.Cdouble.finalize} (finalize)
// DO-NOT-DELETE splicer.end(Args.Basic.finalizedouble)
// DO-NOT-DELETE splicer.begin(Args.Basic.finalizefloat)
    // Insert-Code-Here {Args.Cfloat.finalize} (finalize)
// DO-NOT-DELETE splicer.end(Args.Basic.finalizefloat)
// DO-NOT-DELETE splicer.begin(Args.Basic.Cintint)
    // add construction details here
// DO-NOT-DELETE splicer.end(Args.Basic.Cintint)
// DO-NOT-DELETE splicer.begin(Args.Basic.finalizelong)
    // Insert-Code-Here {Args.Clong.finalize} (finalize)
// DO-NOT-DELETE splicer.end(Args.Basic.finalizelong)
// DO-NOT-DELETE splicer.begin(Args.Basic.Cdoubledouble)
    // add construction details here
// DO-NOT-DELETE splicer.end(Args.Basic.Cdoubledouble)
// ================== END UNREFERENCED METHOD(S) =================
