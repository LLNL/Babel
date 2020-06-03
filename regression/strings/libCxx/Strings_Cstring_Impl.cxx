// 
// File:          Strings_Cstring_Impl.cxx
// Symbol:        Strings.Cstring-v1.1
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7463M trunk)
// Description:   Server-side implementation for Strings.Cstring
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 
#include "Strings_Cstring_Impl.hxx"

// 
// Includes for all method dependencies.
// 
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_sidl_NotImplementedException_hxx
#include "sidl_NotImplementedException.hxx"
#endif
// DO-NOT-DELETE splicer.begin(Strings.Cstring._includes)
#include <string>
using namespace std;
// DO-NOT-DELETE splicer.end(Strings.Cstring._includes)

// special constructor, used for data wrapping(required).  Do not put code here unless you really know what you're doing!
Strings::Cstring_impl::Cstring_impl() : StubBase(reinterpret_cast< void*>(
  ::Strings::Cstring::_wrapObj(reinterpret_cast< void*>(this))),false) , 
  _wrapped(true){ 
  // DO-NOT-DELETE splicer.begin(Strings.Cstring._ctor2)
  // Insert-Code-Here {Strings.Cstring._ctor2} (ctor2)
  // DO-NOT-DELETE splicer.end(Strings.Cstring._ctor2)
}

// user defined constructor
void Strings::Cstring_impl::_ctor() {
  // DO-NOT-DELETE splicer.begin(Strings.Cstring._ctor)
  // add construction details here
  // DO-NOT-DELETE splicer.end(Strings.Cstring._ctor)
}

// user defined destructor
void Strings::Cstring_impl::_dtor() {
  // DO-NOT-DELETE splicer.begin(Strings.Cstring._dtor)
  // add destruction details here
  // DO-NOT-DELETE splicer.end(Strings.Cstring._dtor)
}

// static class initializer
void Strings::Cstring_impl::_load() {
  // DO-NOT-DELETE splicer.begin(Strings.Cstring._load)
  // guaranteed to be called at most once before any other method in this class
  // DO-NOT-DELETE splicer.end(Strings.Cstring._load)
}

// user defined static methods: (none)

// user defined non-static methods:
/**
 * If <code>nonNull</code> is <code>true</code>, this will
 * return "Three"; otherwise, it will return a NULL or empty string.
 */
::std::string
Strings::Cstring_impl::returnback_impl (
  /* in */bool nonNull ) 
{
  // DO-NOT-DELETE splicer.begin(Strings.Cstring.returnback)
  return nonNull ? string("Three") : string("");
  // DO-NOT-DELETE splicer.end(Strings.Cstring.returnback)
}

/**
 * This will return <code>true</code> iff <code>c</code> equals "Three".
 */
bool
Strings::Cstring_impl::passin_impl (
  /* in */const ::std::string& c ) 
{
  // DO-NOT-DELETE splicer.begin(Strings.Cstring.passin)
  return ( c == "Three" );
  // DO-NOT-DELETE splicer.end(Strings.Cstring.passin)
}

/**
 * If <code>nonNull</code> is <code>true</code>, this will return
 * "Three" in <code>c</code>; otherwise, it will return a null or
 * empty string. The return value is <code>false</code> iff 
 * the outgoing value of <code>c</code> is <code>null</code>.
 */
bool
Strings::Cstring_impl::passout_impl (
  /* in */bool nonNull,
  /* out */::std::string& c ) 
{
  // DO-NOT-DELETE splicer.begin(Strings.Cstring.passout)
  c = nonNull ? "Three" : "";
  return c == "Three";
  // DO-NOT-DELETE splicer.end(Strings.Cstring.passout)
}

/**
 * Method:  passinout[]
 */
bool
Strings::Cstring_impl::passinout_impl (
  /* inout */::std::string& c ) 
{
  // DO-NOT-DELETE splicer.begin(Strings.Cstring.passinout)
  if (!c.length()) return false;
  char h = c[0];
  if ( h >= 'a' && h <= 'z' ) { 
    c[0] = toupper(h);
  } else if ( h >= 'A' && h <= 'Z' ) { 
    c[0] = tolower(h);
  }
  c += 's';
  return true;
  // DO-NOT-DELETE splicer.end(Strings.Cstring.passinout)
}

/**
 * Method:  passeverywhere[]
 */
::std::string
Strings::Cstring_impl::passeverywhere_impl (
  /* in */const ::std::string& c1,
  /* out */::std::string& c2,
  /* inout */::std::string& c3 ) 
{
  // DO-NOT-DELETE splicer.begin(Strings.Cstring.passeverywhere)
  c2 = "";
  if (!c3.length()) return "";
  char h = c3[0];
  if ( h >= 'a' && h <= 'z' ) { 
    c3[0] = toupper(h);
  } else if ( h >= 'A' && h <= 'Z' ) { 
    c3[0] = tolower(h);
  }
  c3 = c3.substr(0,c3.length()-1);
  c2 = "Three";
  return  ( c1 == "Three" ) ? string("Three") : string();
  // DO-NOT-DELETE splicer.end(Strings.Cstring.passeverywhere)
}

/**
 *  return true iff s1 == s2 and c1 == c2 
 */
bool
Strings::Cstring_impl::mixedarguments_impl (
  /* in */const ::std::string& s1,
  /* in */char c1,
  /* in */const ::std::string& s2,
  /* in */char c2 ) 
{
  // DO-NOT-DELETE splicer.begin(Strings.Cstring.mixedarguments)
  return (c1 == c2) && (s1.compare(s2) == 0);
  // DO-NOT-DELETE splicer.end(Strings.Cstring.mixedarguments)
}


// DO-NOT-DELETE splicer.begin(Strings.Cstring._misc)
// Put miscellaneous code here
// DO-NOT-DELETE splicer.end(Strings.Cstring._misc)

