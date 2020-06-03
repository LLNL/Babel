#include <string>
#include <string.h>
using namespace std;
#include "objarg_Employee.hxx"
#include "objarg_EmployeeArray.hxx"
#include "objarg_Basic.hxx"
#include "sidl_BaseClass.hxx"
#include "synch.hxx"
#include <cstdio>

#ifdef WITH_RMI
#include "sidl_rmi_ProtocolFactory.hxx"
#endif

#define MYASSERT( AAA ) \
  tracker.startPart(++part_no); \
  tracker.writeComment(#AAA); \
  if ( AAA ) result = synch::ResultType_PASS; \
  else result = synch::ResultType_FAIL;  \
  tracker.endPart(part_no, result);

struct TmpData_t {
  string name;
  int    age;
  float  salary;
  char   status;
};

struct TmpData_t TmpData[] = {
  { "John Smith", 35, 75.7e3, 'c' },
  { "Jane Doe", 40, 85.5e3, 'm' },
  { "Ella Vader", 64, 144.2e3, 'r' },
  { "Marge Inovera", 32, 483.2e3, 's' },
  { "Hughy Louis Dewey", 45, 182.9e3, 'm' },
  { "Heywood Yubuzof", 12, 20.8e3, 'x' },
  { "Picov Andropov", 90, 120.6e3, 'r' }
};


//basic RMI support
static char *remoteURL = NULL;

static sidl_bool withRMI() {
  return remoteURL && *remoteURL;
}

static sidl::BaseClass
makeBaseClassObject( void ) {
#ifdef WITH_RMI  
  if(withRMI()) return sidl::BaseClass::_create(remoteURL);
#endif
  return sidl::BaseClass::_create();
}

static objarg::Employee
makeEmployeeObject( void ) {
#ifdef WITH_RMI  
  if(withRMI()) return objarg::Employee::_create(remoteURL);
#endif
  return objarg::Employee::_create();
}

static objarg::EmployeeArray
makeEmployeeArrayObject( void ) {
#ifdef WITH_RMI  
  if(withRMI()) return objarg::EmployeeArray::_create(remoteURL);
#endif
  return objarg::EmployeeArray::_create();
}

static objarg::Basic
makeBasicObject( void ) {
#ifdef WITH_RMI  
  if(withRMI()) return objarg::Basic::_create(remoteURL);
#endif
  return objarg::Basic::_create();
}


int main(int argc, char * argv[]) {
  synch::ResultType result = synch::ResultType_PASS; 
  synch::RegOut tracker = synch::RegOut::_create();

#ifdef WITH_RMI
  //Parse the command line  to see if we are running RMI tests
  {
    unsigned _arg;
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
  
  int part_no = 0;
  int i;
  const int numEmp = sizeof(TmpData)/sizeof(struct TmpData_t);
  tracker.setExpectations(82);
  objarg::EmployeeArray a = makeEmployeeArrayObject();
  for(i = 0; i < numEmp; ++i) {
    objarg::Employee e = makeEmployeeObject();
    MYASSERT(e.init(TmpData[i].name, TmpData[i].age, 
		    TmpData[i].salary, TmpData[i].status));
    MYASSERT(a.appendEmployee(e));
    MYASSERT(a.getLength() == (i+1));
    MYASSERT(e.isSame(a.at(i+1)));
    MYASSERT(e.getAge() == TmpData[i].age);
    MYASSERT(e.getSalary() == TmpData[i].salary);
    MYASSERT(e.getStatus() == TmpData[i].status);
  }
  for(i = 0; i < numEmp; ++i) {
    objarg::Employee e;
    int empInd = a.findByName(TmpData[i].name, e);
    MYASSERT(empInd == (i+1));
    if (empInd != 0) {
      MYASSERT(e.isSame(a.at(empInd)));
    }
  }
  objarg::Employee f = makeEmployeeObject();
  f.init("Hire High", 21, 0, 's');
  MYASSERT(a.promoteToMaxSalary(f));
  MYASSERT(f.getSalary() == (float)483.2e3);
  MYASSERT(a.appendEmployee(f));
  f = makeEmployeeObject();
  f.init("Amadeo Avogadro, conte di Quaregna", 225, 6.022045e23, 'd');
  MYASSERT(!a.promoteToMaxSalary(f));

  {
    ::objarg::Basic b = makeBasicObject();
    ::sidl::BaseClass o, inValue;
    MYASSERT(b.passIn(o, FALSE));
    o = makeBaseClassObject();
    MYASSERT(b.passIn(o, TRUE));
    MYASSERT(b.passIn(makeBaseClassObject(), TRUE));
    o = ::sidl::BaseClass();
    
    MYASSERT(b.passInOut(o, false, false, true));
    MYASSERT(o._is_nil());

    o = makeBaseClassObject();
    MYASSERT(b.passInOut(o, true, false, false));
    MYASSERT(o._is_nil());
    
    o = makeBaseClassObject();
    inValue = o;
    MYASSERT(b.passInOut(o, true, true, true));
    MYASSERT(inValue.isSame(o));

    o = makeBaseClassObject();
    inValue = o;
    MYASSERT(b.passInOut(o, true, true, false));
    MYASSERT(!inValue.isSame(o));
    
    o = ::sidl::BaseClass();
    tracker.writeComment("b.passOut(o, false);");
    b.passOut(o, false);
    MYASSERT(o._not_nil());
    tracker.writeComment("b.passOut(o, true);");
    b.passOut(o, true);
    MYASSERT(o._is_nil());
    
    MYASSERT(b.retObject(true)._is_nil());
    MYASSERT(b.retObject(false)._not_nil());
  }

  tracker.close();
  return 0;
}
