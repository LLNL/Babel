/*
 * File:        structtest.cxx
 * Copyright:   (c) 2007 Lawrence Livermore National Security, LLC
 * Release:     $Name$
 * Revision:    @(#) $Revision: 6998 $
 * Date:        $Date$
 * Description: Struct test driver
 *
 */
#include "s_StructTest.hxx"
#include <sstream>
#include <iostream>
#include <cstring>
#include <string.h>
#include <cstdio>

#ifdef WITH_RMI
#include "sidl_rmi_ProtocolFactory.hxx"
#endif

using namespace std;
#include "synch.hxx"

#define MYASSERT( AAA ) \
  tracker.startPart(++part_no);\
  tracker.writeComment(#AAA); \
  if ( AAA ) result = synch::ResultType_PASS; \
  else result = synch::ResultType_FAIL;  \
  tracker.endPart(part_no, result);

static void
initSimple(::s::Simple &s)
{
  s.d_bool = true;
  s.d_char = '3';
  s.d_dcomplex.real = s.d_dcomplex.imaginary = 3.14;
  s.d_fcomplex.real = s.d_fcomplex.imaginary = 3.1F;
  s.d_double = 3.14;
  s.d_float = 3.1F;
  s.d_int = 3;
  s.d_long = 3;
  s.d_opaque = 0;
  s.set_d_enum(::s::Color_blue);
}

static void 
initHard(::s::Hard &h) {
  ::sidl::array< ::std::string> str = ::sidl::array< ::std::string> ::create1d(1);
  str.set(0,"Three");
  h.set_d_string(str);
  h.set_d_object(::sidl::BaseClass::_create());
  h.set_d_interface(h.get_d_object());
  ::sidl::array<double> da = ::sidl::array<double> ::create1d(3);
  da.set(0,1.0);
  da.set(1,2.0);
  da.set(2,3.0);
  h.set_d_array(da);
  ::sidl::array< ::sidl::BaseClass > oa = 
      ::sidl::array< ::sidl::BaseClass > :: create1d(3);
  oa.set(0, ::sidl::BaseClass::_create());
  oa.set(1, ::sidl::BaseClass::_create());
  oa.set(2, ::sidl::BaseClass::_create());
  h.set_d_objectArray(oa);
}


static void
initRarrays(::s::Rarrays &r) {
  const int N=3;
  int i;

  r.d_rarrayRaw = new double[N];

  r.d_int=N;
  for (i=0; i<N;i++){
    r.d_rarrayRaw[i]=(double)(i+1);
    r.d_rarrayFix[i]=(double)((i+1)*5);
  }
}

static void
initCombined(::s::Combined &c)
{
  initSimple(c.get_d_simple());
  initHard(c.get_d_hard());
}

static bool
checkSimple(const ::s::Simple &s)
{
  const double eps = 1.E-6;

  return ((s.d_bool &&
           (s.d_char == '3') &&
           (fabs(s.d_dcomplex.real - 3.14) < eps) &&
           (fabs(s.d_dcomplex.imaginary - 3.14) < eps) &&
           (fabs(s.d_double - 3.14) < eps) &&
           (fabs(s.d_fcomplex.real - 3.1F) < eps) &&
           (fabs(s.d_fcomplex.imaginary - 3.1F) < eps) &&
           (fabs(s.d_float - 3.1F) < eps) &&
           (s.d_int == 3) &&
           (s.d_long == 3) &&
           (s.d_opaque == NULL) &&
           (s.get_d_enum() == ::s::Color_blue)));
}

static bool
checkSimpleInv(const ::s::Simple &s)
{
  const double eps = 1.E-6;

  return ((!s.d_bool &&
           (s.d_char == '3') &&
           (fabs(s.d_dcomplex.real - 3.14) < eps) &&
           (fabs(s.d_dcomplex.imaginary + 3.14) < eps) &&
           (fabs(s.d_double + 3.14) < eps) &&
           (fabs(s.d_fcomplex.real - 3.1F) < eps) &&
           (fabs(s.d_fcomplex.imaginary + 3.1F) < eps) &&
           (fabs(s.d_float + 3.1F) < eps) &&
           (s.d_int == -3) &&
           (s.d_long == -3) &&
           (s.d_opaque == NULL) &&
           (s.get_d_enum() == ::s::Color_red)));
}

static bool
checkHard(const ::s::Hard &h) {
  //bool result = (h.get_d_string() == "Three");
  bool result = h.get_d_string()._not_nil();
  if (result) {
    ::sidl::array<string> str = h.get_d_string();
    result = result && (str.dimen() == 1);
    result = result && (str.length(0) == 1);
    result = result && ("Three" == str[0]);
  }
  result = result && h.get_d_object()._not_nil();
  result = result && h.get_d_interface()._not_nil();
  if (result) {
    result = result && h.get_d_object().
      isSame(h.get_d_interface());
  }
  result = result && h.get_d_array()._not_nil();
  if (result) {
    ::sidl::array<double> da = h.get_d_array();
    result = result && (da.dimen() == 1);
    result = result && (da.length(0) == 3);
    result = result && (da[0] == 1.0);
    result = result && (da[1] == 2.0);
    result = result && (da[2] == 3.0);
  }
  result = result && h.get_d_objectArray()._not_nil();
  if (result) {
    ::sidl::array< ::sidl::BaseClass > oa = h.get_d_objectArray();
    result = result && (oa.dimen() == 1);
    result = result && (oa.length(0) == 3);
    result = result && oa[0]._not_nil() && oa[0].isType("sidl.BaseClass");
    result = result && oa[1]._not_nil() && oa[1].isType("sidl.BaseClass");
    result = result && oa[2]._not_nil() && oa[2].isType("sidl.BaseClass");
  }
  return result;
}

static bool
checkHardInv(const ::s::Hard &h) {
  bool result = h.get_d_string()._not_nil();
  if (result) {
    ::sidl::array<string> str = h.get_d_string();
    result = result && (str.dimen() == 1);
    result = result && (str.length(0) == 1);
    result = result && ("three" == str[0]);
  }
  result = result && h.get_d_object()._not_nil();
  result = result && h.get_d_interface()._not_nil();
  if (result) {
    result = result && !(h.get_d_object().
			 isSame(h.get_d_interface()));
  }
  result = result && h.get_d_array()._not_nil();
  if (result) {
    const double eps = 1.E-5;
    ::sidl::array<double> da = h.get_d_array();
    result = result && (da.dimen() == 1);
    result = result && (da.length(0) == 3);
    result = result && (fabs(da[0] - 3.0) < eps);
    result = result && (fabs(da[1] - 2.0) < eps);
    result = result && (fabs(da[2] - 1.0) < eps);
  }
  result = result && h.get_d_objectArray()._not_nil();
  if (result) {
    ::sidl::array< ::sidl::BaseClass > oa = h.get_d_objectArray();
    result = result && (oa.dimen() == 1);
    result = result && (oa.length(0) == 3);
    result = result && oa[0]._not_nil() && oa[0].isType("sidl.BaseClass");
    result = result && oa[1]._is_nil();
    result = result && oa[2]._not_nil() && oa[2].isType("sidl.BaseClass");
  }
  return result;
}

static bool
checkRarrays(const ::s::Rarrays &r) {
  const double eps = 1.E-5;
  bool result = (r.d_rarrayRaw != NULL);
  if (result) {
    result = result && (fabs(r.d_rarrayRaw[0] - 1.0) < eps);
    result = result && (fabs(r.d_rarrayRaw[1] - 2.0) < eps);
    result = result && (fabs(r.d_rarrayRaw[2] - 3.0) < eps);
    result = result && (fabs(r.d_rarrayFix[0] - 5.0) < eps);
    result = result && (fabs(r.d_rarrayFix[1] - 10.0) < eps);
    result = result && (fabs(r.d_rarrayFix[2] - 15.0) < eps);
  }
  return result;
}

static bool
checkRarraysInv(const ::s::Rarrays &r) {
  const double eps = 1.E-5;
  bool result = (r.d_rarrayRaw != NULL);
  if (result) {
    result = result && (fabs(r.d_rarrayRaw[0] - 3.0) < eps);
    result = result && (fabs(r.d_rarrayRaw[1] - 2.0) < eps);
    result = result && (fabs(r.d_rarrayRaw[2] - 1.0) < eps);
    result = result && (fabs(r.d_rarrayFix[0] - 15.0) < eps);
    result = result && (fabs(r.d_rarrayFix[1] - 10.0) < eps);
    result = result && (fabs(r.d_rarrayFix[2] - 5.0) < eps);
  }
  return result;
}

void
deleteRarrays(struct ::s::Rarrays &r) {

  delete [] r.d_rarrayRaw;

}

static bool
checkCombined(const ::s::Combined &c)
{
  return checkSimple(c.get_d_simple()) 
    && checkHard(c.get_d_hard());
}

static bool
checkCombinedInv(const ::s::Combined &c)
{
  return checkSimpleInv(c.get_d_simple()) && checkHardInv(c.get_d_hard());
}

static char *remoteURL = NULL;

static sidl_bool withRMI() {
  return remoteURL && *remoteURL;
}

static ::s::StructTest
makeObject( void ) {
  
#ifdef WITH_RMI  
  if(withRMI()) return ::s::StructTest::_create(remoteURL);
#endif
  
  return ::s::StructTest::_create();
}

int
main(int argc, char **argv)
{
  synch::ResultType result = synch::ResultType_PASS;
  synch::RegOut tracker = synch::RegOut::_create();
  int part_no = 0;
#ifdef WITH_RMI
  //Parse the command line  to see if we are running RMI tests
  {
    int _arg;
    for(_arg = 0; _arg < argc; ++_arg) {
      if(strncmp(argv[_arg], "--url=", 6) == 0) {
        std::string _tmp(argv[_arg] + 6);
        tracker.replaceMagicVars(_tmp, argv[0]);
        remoteURL = strdup(_tmp.c_str());
      }
    }
    if(withRMI()) {
      fprintf(stdout, "using remote URL %s\n", remoteURL);
    }
  }

  //Setup RMI if necessary
  if(withRMI()) {
    fprintf(stdout, "registering RMI protocol simhandle\n");
    if(!sidl::rmi::ProtocolFactory::addProtocol("simhandle","sidlx.rmi.SimHandle")) {
      printf("sidl.rmi.ProtocolFactor.addProtocol() failed\n");
      exit(2);
    }
  }
#endif
  
  tracker.setExpectations(withRMI() ? 14 : 36);
  ::s::StructTest test = makeObject();
  MYASSERT(test._not_nil());
  
  {
    ::s::Empty e1, e2, e3, e4;
    ostringstream buf;
    buf << "sizeof(::s::Empty) == " << sizeof(::s::Empty);
    tracker.writeComment(buf.str());
    e1 = test.returnEmpty();
    MYASSERT(test.passinEmpty(e1));
    MYASSERT(test.passoutEmpty(e1));
    MYASSERT(test.passoutEmpty(e2));
    MYASSERT(test.passinoutEmpty(e2));
    MYASSERT(test.passoutEmpty(e3));
    e4 = test.passeverywhereEmpty(e1, e2, e3);
  }

  {
    ::s::Simple s1, s2, s3, s4;
    ostringstream buf;
    buf << "sizeof(::s::Simple) == " << sizeof(::s::Simple);
    tracker.writeComment(buf.str());
    s1 = test.returnSimple();
    MYASSERT(checkSimple(s1));
    MYASSERT(test.passinSimple(s1));
    MYASSERT(test.passoutSimple(s1));
    MYASSERT(test.passoutSimple(s2));
    MYASSERT(test.passinoutSimple(s2));
    MYASSERT(checkSimpleInv(s2));
    MYASSERT(test.passoutSimple(s3));
    s4 = test.passeverywhereSimple(s1, s2, s3);
    MYASSERT(checkSimple(s4) && checkSimple(s2) && checkSimpleInv(s3));
  }

  //some elements in s.Hard can't be passed as they are not
  //serializable, so skip these tests for RMI

  if (!withRMI()) {
    ::s::Hard h1, h2, h3, h4;
    ostringstream buf;
    buf << "sizeof(::s::Hard) == " << sizeof(::s::Hard);
    tracker.writeComment(buf.str());
    h1 = test.returnHard();
    MYASSERT(checkHard(h1));
    MYASSERT(test.passinHard(h1));
    MYASSERT(test.passoutHard(h1));
    MYASSERT(test.passoutHard(h2));
    MYASSERT(test.passinoutHard(h2));
    MYASSERT(checkHardInv(h2));
    MYASSERT(test.passoutHard(h3));
    h4 = test.passeverywhereHard(h1, h2, h3);
    MYASSERT(checkHard(h4) && checkHard(h2) && checkHardInv(h3));
  }

  if(!withRMI()) {
    ::s::Rarrays r1, r2;
    ostringstream buf;
    buf << "sizeof(::s::Rarrays) == " << sizeof(::s::Rarrays)
	<< " sizeof(struct s_Rarrays__data) == " 
	<< sizeof(struct s_Rarrays__data);
    tracker.writeComment(buf.str());
    initRarrays(r1); 
    MYASSERT(test.passinRarrays(r1)); 
    MYASSERT(test.passinoutRarrays(r1));
    MYASSERT(checkRarraysInv(r1));
    deleteRarrays(r1);
    initRarrays(r1); 
    initRarrays(r2); 
    MYASSERT(test.passeverywhereRarrays(r1, r2));
    MYASSERT(checkRarrays(r1) && checkRarraysInv(r2));
    deleteRarrays(r1);
    deleteRarrays(r2);
  }

  if(!withRMI()) {
    ::s::Combined c0, c1, c2, c3, c4;
    ostringstream buf;
    buf << "sizeof(::s::Combined) == " << sizeof(::s::Combined)
	<< " sizeof(struct s_Combined__data) == " 
	<< sizeof(struct s_Combined__data);
    tracker.writeComment(buf.str());
    initCombined(c0);
    MYASSERT(checkCombined(c0));
    c1 = test.returnCombined();
    MYASSERT(checkCombined(c1));
    MYASSERT(test.passinCombined(c1));
    MYASSERT(test.passoutCombined(c1));
    MYASSERT(test.passoutCombined(c2));
    MYASSERT(test.passinoutCombined(c2));
    MYASSERT(checkCombinedInv(c2));
    MYASSERT(test.passoutCombined(c3));
    c4 = test.passeverywhereCombined(c1, c2, c3);
    MYASSERT(checkCombined(c4) && checkCombined(c2) && checkCombinedInv(c3));
  }
  tracker.close();
  return 0;
}
