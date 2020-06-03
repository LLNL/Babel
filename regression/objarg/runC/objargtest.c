#include <stdio.h>
#include <stdlib.h>
#include <stddef.h>
#include <time.h>
#include <string.h>
#include "objarg_Employee.h"
#include "objarg_EmployeeArray.h"
#include "sidl_BaseInterface.h"
#include "synch.h"
#include "sidl_Exception.h"
#include "sidl_BaseClass.h"
#include "objarg_Basic.h"


#define MYASSERT( AAA ) \
  synch_RegOut_startPart(tracker, ++part_no, &exception); SIDL_REPORT(exception); \
  synch_RegOut_writeComment(tracker, #AAA, &exception); SIDL_REPORT(exception);\
  { \
     int _result = AAA; SIDL_REPORT(exception); \
     if ( _result ) result = synch_ResultType_PASS; \
     else result = synch_ResultType_FAIL;  \
  } \
  synch_RegOut_endPart(tracker, part_no, result, &exception); SIDL_REPORT(exception);

struct TmpData_t {
  char  *name;
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

/* basic RMI support */
static char *remoteURL = NULL;

static sidl_bool withRMI( void ) {
  return remoteURL && *remoteURL;
}

static sidl_BaseClass
makeBaseClassObject( void ) {
  sidl_BaseClass obj = NULL;
  sidl_BaseInterface exception = NULL;

#ifdef WITH_RMI  
  if(withRMI()) {
    obj = sidl_BaseClass__createRemote(remoteURL, &exception); SIDL_REPORT(exception);
    return obj;
  }
#endif
  
  obj = sidl_BaseClass__create(&exception); SIDL_REPORT(exception);
  return obj;
  EXIT:
  exit(1);
}

static objarg_Employee
makeEmployeeObject( void ) {
  objarg_Employee obj = NULL;
  sidl_BaseInterface exception = NULL;

#ifdef WITH_RMI  
  if(withRMI()) {
    obj = objarg_Employee__createRemote(remoteURL, &exception); SIDL_REPORT(exception);
    return obj;
  }
#endif
  
  obj = objarg_Employee__create(&exception); SIDL_REPORT(exception);
  return obj;
  EXIT:
  exit(1);
}

static objarg_EmployeeArray
makeEmployeeArrayObject( void ) {
  objarg_EmployeeArray obj = NULL;
  sidl_BaseInterface exception = NULL;

#ifdef WITH_RMI  
  if(withRMI()) {
    obj = objarg_EmployeeArray__createRemote(remoteURL, &exception); SIDL_REPORT(exception);
    return obj;
  }
#endif
  
  obj = objarg_EmployeeArray__create(&exception); SIDL_REPORT(exception);
  return obj;
  EXIT:
  exit(1);
}

static objarg_Basic
makeBasicObject( void ) {
  objarg_Basic obj = NULL;
  sidl_BaseInterface exception = NULL;

#ifdef WITH_RMI  
  if(withRMI()) {
    obj = objarg_Basic__createRemote(remoteURL, &exception); SIDL_REPORT(exception);
    return obj;
  }
#endif
  
  obj = objarg_Basic__create(&exception); SIDL_REPORT(exception);
  return obj;
  EXIT:
  exit(1);
}

int main(int argc, char **argv) {
  int result = synch_ResultType_PASS; 
  sidl_BaseInterface exception = NULL;
  int part_no = 0;
  int i, NewRidersFound=0;
  const int numEmp = sizeof(TmpData)/sizeof(struct TmpData_t);
  synch_RegOut tracker;
  objarg_EmployeeArray a;
  objarg_Employee f;

  tracker = synch_RegOut__create(&exception); SIDL_REPORT(exception);
    
  /* Parse the command line  to see if we are running RMI tests */
#ifdef WITH_RMI  
  {
    unsigned _arg;
    for(_arg = 0; _arg < argc; ++_arg) {
      if(strncmp(argv[_arg], "--url=", 6) == 0) {
        remoteURL = argv[_arg] + 6;
        synch_RegOut_replaceMagicVars(tracker, &remoteURL, argv[0], &exception); SIDL_REPORT(exception);        
      }
    }
    if(withRMI()) {
      fprintf(stdout, "using remote URL %s\n", remoteURL);
    }
  }

  /* Setup RMI if necessary */
  if(withRMI()) {
    sidl_BaseInterface _ex = NULL;
    fprintf(stdout, "registering RMI protocol simhandle\n");
    if(!sidl_rmi_ProtocolFactory_addProtocol("simhandle","sidlx.rmi.SimHandle",&_ex )){
      printf("sidl.rmi.ProtocolFactor.addProtocol() failed\n");
      exit(2);
    }
  }
#endif
  
  a = makeEmployeeArrayObject();
  srand(time(NULL));
  synch_RegOut_setExpectations(tracker, 91, &exception); SIDL_REPORT(exception);
  for(i = 0; i < numEmp; ++i) {
    objarg_Employee tmp;
    objarg_Employee e = makeEmployeeObject();
    MYASSERT(objarg_Employee_init(e,
                                  TmpData[i].name, TmpData[i].age, 
                                  TmpData[i].salary, TmpData[i].status,
                                  &exception));
    MYASSERT(objarg_EmployeeArray_appendEmployee(a, e, &exception));
    MYASSERT(objarg_EmployeeArray_getLength(a, &exception) == (i+1));
    tmp = objarg_EmployeeArray_at(a, i+1, &exception); SIDL_REPORT(exception);
    MYASSERT(objarg_Employee_isSame(e, (sidl_BaseInterface)tmp, &exception));
    MYASSERT(objarg_Employee_getAge(e, &exception) == TmpData[i].age);
    MYASSERT(objarg_Employee_getSalary(e, &exception) == TmpData[i].salary);
    MYASSERT(objarg_Employee_getStatus(e, &exception) == TmpData[i].status);
    if (tmp) {
      objarg_Employee_deleteRef(tmp, &exception); SIDL_REPORT(exception);
    }
    objarg_Employee_deleteRef(e, &exception); SIDL_REPORT(exception);
  }
  for(i = 0; i < numEmp; ++i) {
    /* initialize pointer to arbitrary value to make sure input is ignored */
    objarg_Employee e = (objarg_Employee)(ptrdiff_t)rand(); 
    objarg_Employee fromArray;
    int empInd = objarg_EmployeeArray_findByName(a, TmpData[i].name, &e, &exception);
    SIDL_REPORT(exception);
    MYASSERT(empInd == (i+1));
    if (empInd != 0) {
      fromArray = objarg_EmployeeArray_at(a, empInd, &exception);
      SIDL_REPORT(exception);
      MYASSERT(fromArray != NULL);
      if (fromArray) {
        MYASSERT(objarg_Employee_isSame(e, (sidl_BaseInterface)fromArray,
                                        &exception));
        objarg_Employee_deleteRef(fromArray, &exception); SIDL_REPORT(exception);
      }
    }
    if (e) {
      objarg_Employee_deleteRef(e, &exception);
      SIDL_REPORT(exception);
    }
  }
  f = makeEmployeeObject();
  objarg_Employee_init(f, "Hire High", 21, 0, 's', &exception);
  SIDL_REPORT(exception);
  MYASSERT(objarg_EmployeeArray_promoteToMaxSalary(a, &f, &exception));
  MYASSERT(f != NULL);
  if (f) {
    MYASSERT(objarg_Employee_getSalary(f, &exception) == (float)483.2e3);
    MYASSERT(objarg_EmployeeArray_appendEmployee(a, f, &exception));
    objarg_Employee_deleteRef(f, &exception); SIDL_REPORT(exception);
  }
  f = makeEmployeeObject();
  objarg_Employee_init(f, "Amadeo Avogadro, conte di Quaregna",
                       225, 6.022045e23, 'd', &exception);
  SIDL_REPORT(exception);
  MYASSERT(!objarg_EmployeeArray_promoteToMaxSalary(a, &f, &exception));
  if (f) {
    objarg_Employee_deleteRef(f, &exception);
    SIDL_REPORT(exception);
  }
  /*  Adding a NULL employee should fail  */
  MYASSERT(!objarg_EmployeeArray_appendEmployee(a, NULL, &exception));
  /* Search for a non-existent employee  */
  NewRidersFound = objarg_EmployeeArray_findByName(a, "New Riders", &f, &exception);
  SIDL_REPORT(exception);
  MYASSERT(!(NewRidersFound || f));
  f = NULL;
  MYASSERT(!objarg_EmployeeArray_promoteToMaxSalary(a, &f, &exception));
  objarg_EmployeeArray_deleteRef(a, &exception); SIDL_REPORT(exception);

  {
    sidl_BaseClass o = NULL, inValue;
    objarg_Basic b = makeBasicObject();
    MYASSERT(objarg_Basic_passIn(b, o, FALSE, &exception));
    o = makeBaseClassObject();
    MYASSERT(objarg_Basic_passIn(b, o, TRUE, &exception));
    if (o) {sidl_BaseClass_deleteRef(o, &exception); SIDL_REPORT(exception);}
    o = NULL;

    MYASSERT(objarg_Basic_passInOut(b, &o, FALSE, FALSE, TRUE, &exception));
    MYASSERT(o == NULL);

    o = makeBaseClassObject();
    MYASSERT(objarg_Basic_passInOut(b, &o, TRUE, FALSE, FALSE, &exception));
    MYASSERT(o == NULL);

    o = makeBaseClassObject();
    inValue = o;
    sidl_BaseClass_addRef(inValue, &exception);  SIDL_REPORT(exception);
    MYASSERT(objarg_Basic_passInOut(b, &o, TRUE, TRUE, TRUE, &exception));
    /* 
    implementations are allowed to return a different object
    MYASSERT(inValue == o);
    */
    if (inValue) {sidl_BaseClass_deleteRef(inValue, &exception); SIDL_REPORT(exception);}
    if (o){ sidl_BaseClass_deleteRef(o, &exception); SIDL_REPORT(exception);}

    o = makeBaseClassObject();
    inValue = o;
    sidl_BaseClass_addRef(inValue, &exception);  SIDL_REPORT(exception);
    MYASSERT(objarg_Basic_passInOut(b, &o, TRUE, TRUE, FALSE, &exception));
    MYASSERT(inValue != o);
    if (inValue){ sidl_BaseClass_deleteRef(inValue, &exception); SIDL_REPORT(exception);}
    if (o){ sidl_BaseClass_deleteRef(o, &exception); SIDL_REPORT(exception);}
    
    synch_RegOut_writeComment(tracker, "objarg.Basic.passOut(b, &o, FALSE)", &exception); SIDL_REPORT(exception);
    objarg_Basic_passOut(b, &o, FALSE, &exception); SIDL_REPORT(exception);
    MYASSERT(o != NULL);
    if (o){ sidl_BaseClass_deleteRef(o, &exception); SIDL_REPORT(exception);}
    synch_RegOut_writeComment(tracker, "objarg.Basic.passOut(b, &o, TRUE)", &exception); SIDL_REPORT(exception);
    objarg_Basic_passOut(b, &o, TRUE, &exception); SIDL_REPORT(exception);
    MYASSERT(o == NULL);

    synch_RegOut_writeComment(tracker, "objarg.Basic.retObject(b, TRUE)", &exception); SIDL_REPORT(exception);
    o = objarg_Basic_retObject(b, TRUE, &exception);  SIDL_REPORT(exception);
    MYASSERT(o == NULL);
    if (o) {sidl_BaseClass_deleteRef(o, &exception);  SIDL_REPORT(exception);}
    synch_RegOut_writeComment(tracker, "objarg.Basic.retObject(b, FALSE)", &exception); SIDL_REPORT(exception);
    o = objarg_Basic_retObject(b, FALSE, &exception);  SIDL_REPORT(exception);
    MYASSERT(o != NULL);
    if (o) {sidl_BaseClass_deleteRef(o, &exception);  SIDL_REPORT(exception);}

    objarg_Basic_deleteRef(b, &exception);  SIDL_REPORT(exception);
  }

  synch_RegOut_close(tracker, &exception); SIDL_REPORT(exception);
  synch_RegOut_deleteRef(tracker, &exception);SIDL_REPORT(exception);
  return 0;
 EXIT:
  {
    sidl_BaseInterface throwaway_exception = NULL;
    if (tracker) {
      sidl_BaseInterface exception2 = NULL;
      synch_RegOut_forceFailure(tracker, &exception2);
      if (exception2) {
        puts("TEST_RESULT FAIL\n");
        sidl_BaseInterface_deleteRef(exception2, &throwaway_exception);
      }
      synch_RegOut_deleteRef(tracker, &throwaway_exception);
    }
    sidl_BaseInterface_deleteRef(exception, &throwaway_exception);
    return -1;
  }
}
