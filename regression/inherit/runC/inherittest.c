
#include <stddef.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "Inherit.h"
#include "synch.h"
#include "sidl_Exception.h"
#include "sidl_String.h"


/**
 * Fill the stack with random junk.
 */
int clearstack(int magicNumber) {
  int chunk[2048], i;
  for(i = 0; i < 2048; i++){
    chunk[i] = rand() + magicNumber;
  }
  for(i = 0; i < 16; i++){
    magicNumber += chunk[rand() & 2047];
  }
  return magicNumber;
}

void freeResult(char **result)
{
  if (result && *result) {
    free((void *)(*result));
    *result = NULL;
  }
}

void declare_part(synch_RegOut tracker,  int * part_no ) {
  sidl_BaseInterface exception = NULL;
  synch_RegOut_startPart(tracker, ++(*part_no), &exception);
  if (exception) {
    sidl_BaseInterface throwaway_exception;
    sidl_BaseInterface exception2 = NULL;
    synch_RegOut_forceFailure(tracker, &exception2);
    if (exception2) {
      puts("TEST_RESULT FAIL\n");
      sidl_BaseInterface_deleteRef(exception2, &throwaway_exception);
    }
    sidl_BaseInterface_deleteRef(exception, &throwaway_exception);
  }
}

void end_part( synch_RegOut tracker, int part_no,
               enum synch_ResultType__enum result) 
{
  sidl_BaseInterface exception = NULL;
  synch_RegOut_endPart(tracker, part_no, result, &exception);
  if (exception) {
    sidl_BaseInterface throwaway_exception;
    sidl_BaseInterface exception2 = NULL;
    synch_RegOut_forceFailure(tracker, &exception2);
    if (exception2) {
      puts("TEST_RESULT FAIL\n");
      sidl_BaseInterface_deleteRef(exception2, &throwaway_exception);
    }
    sidl_BaseInterface_deleteRef(exception, &throwaway_exception);
  }
}

#define MYASSERT( AAA ) \
  declare_part(tracker,  &part_no ); \
  magicNumber = clearstack(magicNumber); \
  synch_RegOut_writeComment(tracker, #AAA, &exception); SIDL_REPORT(exception);\
  { \
    int _result = ((AAA)!=0); SIDL_REPORT(exception); \
    if ( (_result) ) result = synch_ResultType_PASS; \
    else result = synch_ResultType_FAIL;  \
    end_part( tracker, part_no, result); \
  }

#define INHERITTEST( METHOD, ARG, RESULT ) \
  { \
    char *method_result; \
    synch_RegOut_writeComment(tracker, "method " #METHOD  \
                              "(" #ARG ")  should return " \
                              RESULT, &exception); SIDL_REPORT(exception); \
    method_result = METHOD(ARG, &exception); SIDL_REPORT(exception); \
    MYASSERT(method_result && !strcmp(method_result, RESULT)); \
    freeResult(&method_result); \
  }

#define INTTEST( METHOD, ARG, RESULT ) \
  { \
    char *method_result; \
    synch_RegOut_writeComment(tracker, "method " #METHOD  \
                              "(" #ARG ")  should return " \
                              RESULT, &exception); SIDL_REPORT(exception); \
    method_result = METHOD(ARG, 0, &exception); SIDL_REPORT(exception);	\
    MYASSERT(method_result && !strcmp(method_result, RESULT)); \
    freeResult(&method_result); \
  }

/* basic RMI support */
static char *remoteURL = NULL;

static sidl_bool withRMI( void ) {
  return remoteURL && *remoteURL;
}

static Inherit_C
makeCObject( void ) {
  Inherit_C obj = NULL;
  sidl_BaseInterface exception = NULL;

#ifdef WITH_RMI  
  if(withRMI()) {
    obj = Inherit_C__createRemote(remoteURL, &exception); SIDL_REPORT(exception);
    return obj;
  }
#endif
  
  obj = Inherit_C__create(&exception); SIDL_REPORT(exception);
  return obj;
  EXIT:
  exit(1);
}

static Inherit_D
makeDObject( void ) {
  Inherit_D obj = NULL;
  sidl_BaseInterface exception = NULL;

#ifdef WITH_RMI  
  if(withRMI()) {
    obj = Inherit_D__createRemote(remoteURL, &exception); SIDL_REPORT(exception);
    return obj;
  }
#endif
  
  obj = Inherit_D__create(&exception); SIDL_REPORT(exception);
  return obj;
  EXIT:
  exit(1);
}

static Inherit_E
makeEObject( void ) {
  Inherit_E obj = NULL;
  sidl_BaseInterface exception = NULL;

#ifdef WITH_RMI  
  if(withRMI()) {
    obj = Inherit_E__createRemote(remoteURL, &exception); SIDL_REPORT(exception);
    return obj;
  }
#endif
  
  obj = Inherit_E__create(&exception); SIDL_REPORT(exception);
  return obj;
  EXIT:
  exit(1);
}

static Inherit_E2
makeE2Object( void ) {
  Inherit_E2 obj = NULL;
  sidl_BaseInterface exception = NULL;

#ifdef WITH_RMI  
  if(withRMI()) {
    obj = Inherit_E2__createRemote(remoteURL, &exception); SIDL_REPORT(exception);
    return obj;
  }
#endif
  
  obj = Inherit_E2__create(&exception); SIDL_REPORT(exception);
  return obj;
  EXIT:
  exit(1);
}

static Inherit_F
makeFObject( void ) {
  Inherit_F obj = NULL;
  sidl_BaseInterface exception = NULL;

#ifdef WITH_RMI  
  if(withRMI()) {
    obj = Inherit_F__createRemote(remoteURL, &exception); SIDL_REPORT(exception);
    return obj;
  }
#endif
  
  obj = Inherit_F__create(&exception); SIDL_REPORT(exception);
  return obj;
  EXIT:
  exit(1);
}

static Inherit_F2
makeF2Object( void ) {
  Inherit_F2 obj = NULL;
  sidl_BaseInterface exception = NULL;

#ifdef WITH_RMI  
  if(withRMI()) {
    obj = Inherit_F2__createRemote(remoteURL, &exception); SIDL_REPORT(exception);
    return obj;
  }
#endif
  
  obj = Inherit_F2__create(&exception); SIDL_REPORT(exception);
  return obj;
  EXIT:
  exit(1);
}

static Inherit_G
makeGObject( void ) {
  Inherit_G obj = NULL;
  sidl_BaseInterface exception = NULL;

#ifdef WITH_RMI  
  if(withRMI()) {
    obj = Inherit_G__createRemote(remoteURL, &exception); SIDL_REPORT(exception);
    return obj;
  }
#endif
  
  obj = Inherit_G__create(&exception); SIDL_REPORT(exception);
  return obj;
  EXIT:
  exit(1);
}

static Inherit_G2
makeG2Object( void ) {
  Inherit_G2 obj = NULL;
  sidl_BaseInterface exception = NULL;

#ifdef WITH_RMI  
  if(withRMI()) {
    obj = Inherit_G2__createRemote(remoteURL, &exception); SIDL_REPORT(exception);
    return obj;
  }
#endif
  
  obj = Inherit_G2__create(&exception); SIDL_REPORT(exception);
  return obj;
  EXIT:
  exit(1);
}

static Inherit_I
makeIObject( void ) {
  Inherit_I obj = NULL;
  sidl_BaseInterface exception = NULL;

#ifdef WITH_RMI  
  if(withRMI()) {
    obj = Inherit_I__createRemote(remoteURL, &exception); SIDL_REPORT(exception);
    return obj;
  }
#endif
  
  obj = Inherit_I__create(&exception); SIDL_REPORT(exception);
  return obj;
  EXIT:
  exit(1);
}

static Inherit_J
makeJObject( void ) {
  Inherit_J obj = NULL;
  sidl_BaseInterface exception = NULL;

#ifdef WITH_RMI  
  if(withRMI()) {
    obj = Inherit_J__createRemote(remoteURL, &exception); SIDL_REPORT(exception);
    return obj;
  }
#endif
  
  obj = Inherit_J__create(&exception); SIDL_REPORT(exception);
  return obj;
  EXIT:
  exit(1);
}

static Inherit_K
makeKObject( void ) {
  Inherit_K obj = NULL;
  sidl_BaseInterface exception = NULL;

#ifdef WITH_RMI  
  if(withRMI()) {
    obj = Inherit_K__createRemote(remoteURL, &exception); SIDL_REPORT(exception);
    return obj;
  }
#endif
  
  obj = Inherit_K__create(&exception); SIDL_REPORT(exception);
  return obj;
  EXIT:
  exit(1);
}

static Inherit_L
makeLObject( void ) {
  Inherit_L obj = NULL;
  sidl_BaseInterface exception = NULL;

#ifdef WITH_RMI  
  if(withRMI()) {
    obj = Inherit_L__createRemote(remoteURL, &exception); SIDL_REPORT(exception);
    return obj;
  }
#endif
  
  obj = Inherit_L__create(&exception); SIDL_REPORT(exception);
  return obj;
  EXIT:
  exit(1);
}

int main( int argc, char * argv[] ) { 
  enum synch_ResultType__enum result = synch_ResultType_PASS; 
  int magicNumber = 1;
  int part_no = 0;
  sidl_BaseInterface exception = NULL;
  synch_RegOut tracker = synch_RegOut__create(&exception); 
  SIDL_REPORT(exception);

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
  
  { 
    Inherit_C  c;
    char *tmp = NULL;
    c = makeCObject();
    printf("\n\nClass C:\n");
    INHERITTEST(Inherit_C_c, c, "C.c");
    tmp = Inherit_C_c( c, &exception ); SIDL_REPORT(exception);
    Inherit_C_deleteRef( c , &exception); SIDL_REPORT(exception);
    free(tmp);
  }

  { 
    Inherit_A a;
    Inherit_D d;
    d = makeDObject();
    printf("\n\nClass D: inheritance of interface A\n");
    INHERITTEST(Inherit_D_a, d, "D.a");
    INHERITTEST(Inherit_D_d, d, "D.d");

    printf("\n\nClass D: via interface A\n");
    MYASSERT(a = Inherit_A__cast(d, &exception));
    if (a) {
      INHERITTEST(Inherit_A_a, a, "D.a");
      Inherit_A_deleteRef( a, &exception ); SIDL_REPORT(exception);
    }
    Inherit_D_deleteRef( d, &exception ); SIDL_REPORT(exception);
  }

  { 
    Inherit_E e; 
    Inherit_C c;
    e = makeEObject();
    printf("\n\nClass E: inheritance of class C\n");
    INHERITTEST(Inherit_E_c, e, "C.c");
    INHERITTEST(Inherit_E_e, e, "E.e");

    printf("\n\nClass E: via class C (C.c not overridden)\n");
    MYASSERT(c = Inherit_C__cast(e, &exception));
    if (c) {
      INHERITTEST(Inherit_C_c, c, "C.c");
      Inherit_C_deleteRef( c, &exception ); SIDL_REPORT(exception);
    }
    Inherit_E_deleteRef( e, &exception ); SIDL_REPORT(exception);
  }

  { 
    char *str_result;
    Inherit_E2 e2; 
    Inherit_C c;
    e2 = makeE2Object();
    printf("\n\nClass E2: inheritance of class C\n");
    INHERITTEST(Inherit_E2_c, e2, "E2.c");
    INHERITTEST(Inherit_E2_e, e2, "E2.e");

    printf("\n\nClass E2: via class C (C.c overridden)\n");
    MYASSERT(c = Inherit_C__cast(e2, &exception));
    if (c) {
      INHERITTEST(Inherit_C_c, c, "E2.c");
      Inherit_C_deleteRef( c, &exception); SIDL_REPORT(exception);
    }
    Inherit_E2_deleteRef( e2, &exception ); SIDL_REPORT(exception);

    str_result = Inherit_E2_m(&exception); SIDL_REPORT(exception);
    MYASSERT(str_result && !strcmp(str_result, "E2.m"));
    sidl_String_free(str_result);
  }

  { 
    Inherit_F f;
    Inherit_A a;
    Inherit_B b;
    Inherit_C c;

    f = makeFObject();
    printf("\n\nClass F: Multiple inheritance (no overriding)\n");
    INHERITTEST(Inherit_F_a, f, "F.a");
    INHERITTEST(Inherit_F_b, f, "F.b");
    INHERITTEST(Inherit_F_c, f, "C.c");
    INHERITTEST(Inherit_F_f, f, "F.f");

    
    printf("\n\nClass F: via interface A\n");
    MYASSERT(a = Inherit_A__cast(f, &exception));
    if (a) {
      INHERITTEST(Inherit_A_a, a, "F.a");
      Inherit_A_deleteRef( a, &exception ); SIDL_REPORT(exception);
    }

    printf("\n\nClass F: via interface B\n");
    MYASSERT(b = Inherit_B__cast(f, &exception));
    if (b) {
      INHERITTEST(Inherit_B_b, b, "F.b");
      Inherit_B_deleteRef( b, &exception ); SIDL_REPORT(exception);
    }

    printf("\n\nClass F: via class C (no overloading of C.c)\n");
    MYASSERT(c = Inherit_C__cast(f, &exception));
    if (c) {
      INHERITTEST(Inherit_C_c, c, "C.c");
      Inherit_C_deleteRef( c, &exception ); SIDL_REPORT(exception);
    } 
    Inherit_F_deleteRef( f , &exception); SIDL_REPORT(exception);
  }

  { 
    Inherit_F2 f2;
    Inherit_A a;
    Inherit_B b;
    Inherit_C c;

    f2 = makeF2Object();
    printf("\n\nClass F2: Multiple inheritance (overrides C.c)\n");
    INHERITTEST(Inherit_F2_a, f2, "F2.a");
    INHERITTEST(Inherit_F2_b, f2, "F2.b");
    INHERITTEST(Inherit_F2_c, f2, "F2.c");
    INHERITTEST(Inherit_F2_f, f2, "F2.f");
    
    printf("\n\nClass F2: via interface A\n");
    MYASSERT(a = Inherit_A__cast(f2, &exception));
    if (a) {
      INHERITTEST(Inherit_A_a, a, "F2.a");
      Inherit_A_deleteRef( a, &exception ); SIDL_REPORT(exception);
    }

    printf("\n\nClass F2: via interface B\n");
    MYASSERT(b = Inherit_B__cast(f2, &exception));
    if (b) {
      INHERITTEST(Inherit_B_b, b, "F2.b");
      Inherit_B_deleteRef( b, &exception ); SIDL_REPORT(exception);
    }

    printf("\n\nClass F2: via class C (overloads C.c)\n");
    MYASSERT(c = Inherit_C__cast(f2, &exception));
    if (c) {
      INHERITTEST(Inherit_C_c, c, "F2.c");
      Inherit_C_deleteRef( c, &exception ); SIDL_REPORT(exception);
    } 
    Inherit_F2_deleteRef(f2, &exception); SIDL_REPORT(exception);
  }

  { 
    Inherit_G g;
    Inherit_A a;
    Inherit_D d;
    g = makeGObject();

    printf("\n\nClass G: indirect multiple inheritance ( no overloads)\n");
    INHERITTEST(Inherit_G_a, g, "D.a");
    INHERITTEST(Inherit_G_d, g, "D.d");
    INHERITTEST(Inherit_G_g, g, "G.g");
    
    printf("\n\nClass G: via interface A\n");
    MYASSERT(a = Inherit_A__cast(g, &exception));
    if (a) {
      INHERITTEST(Inherit_A_a, a, "D.a");
      Inherit_A_deleteRef(a , &exception); SIDL_REPORT(exception);
    }

    printf("\n\nClass G: via class D\n");
    MYASSERT(d = Inherit_D__cast(g, &exception));
    if (d) {
      INHERITTEST(Inherit_D_a, d, "D.a");
      INHERITTEST(Inherit_D_d, d, "D.d");
      Inherit_D_deleteRef(d , &exception); SIDL_REPORT(exception);
    }

    Inherit_G_deleteRef(g, &exception); SIDL_REPORT(exception);
  }
  
  { 
    Inherit_G2 g2;
    Inherit_A a;
    Inherit_D d;

    g2 = makeG2Object();

    printf("\n\nClass G2: indirect multiple inheritance (overloads)\n");
    INHERITTEST(Inherit_G2_a, g2, "G2.a");
    INHERITTEST(Inherit_G2_g, g2, "G2.g");

    printf("\n\nClass G2: via interface A\n");
    MYASSERT(a = Inherit_A__cast(g2, &exception));
    if (a) {
      INHERITTEST(Inherit_A_a, a, "G2.a");
      Inherit_A_deleteRef(a , &exception);
    }

    printf("\n\nClass G2: via class D\n");
    MYASSERT(d = Inherit_D__cast(g2, &exception));
    if (d) {
      INHERITTEST(Inherit_D_a, d, "G2.a");
      INHERITTEST(Inherit_D_d, d, "G2.d");
      Inherit_D_deleteRef(d , &exception);
    }

    Inherit_G2_deleteRef(g2 , &exception);
  }

  {
    Inherit_I i;
    i = makeIObject();
    printf("\n\nClass I: implement abstract class H which implements A\n");
    INHERITTEST(Inherit_I_a, i, "I.a");
    INHERITTEST(Inherit_I_h, i, "I.h");
    Inherit_I_deleteRef(i, &exception); SIDL_REPORT(exception);
  }

  {
    char *str_result;
    Inherit_J j;
    j = makeJObject();
    printf("\n\nClass J: implements A and B, extends E. Calls super of E and C\n");
    INHERITTEST(Inherit_J_a, j, "J.a");
    INHERITTEST(Inherit_J_b, j, "J.b");
    INHERITTEST(Inherit_J_j, j, "J.j");
    INHERITTEST(Inherit_J_c, j, "J.E2.c");

    INHERITTEST(Inherit_J_e, j, "J.E2.e");
    Inherit_J_deleteRef(j, &exception); SIDL_REPORT(exception);

    str_result = Inherit_J_m(&exception); SIDL_REPORT(exception);
    MYASSERT(str_result && !strcmp(str_result, "E2.m"));
    sidl_String_free(str_result);
  }

  {
    Inherit_K k;
    Inherit_A a;
    Inherit_A2 a2;
    Inherit_H h;

    k = makeKObject();
    printf("\n\nClass K: implements A2, extends H.\n");
    INHERITTEST(Inherit_K_a, k, "K.a");
    INTTEST(Inherit_K_a2, k, "K.a2");

    INHERITTEST(Inherit_K_h, k, "K.h");
    INHERITTEST(Inherit_K_k, k, "K.k");
    MYASSERT(a = Inherit_A__cast(k, &exception));
    if (a) {
      INHERITTEST(Inherit_A_a, a, "K.a");
      Inherit_A_deleteRef(a , &exception); SIDL_REPORT(exception);
    }

    MYASSERT(a2 = Inherit_A2__cast(k, &exception));
    if (a2) {
      INTTEST(Inherit_A2_a, a2, "K.a2");
      Inherit_A2_deleteRef(a2 , &exception); SIDL_REPORT(exception);
    }

    MYASSERT(h = Inherit_H__cast(k, &exception));
    if (h) {
      INHERITTEST(Inherit_H_a, h, "K.a");
      INHERITTEST(Inherit_H_h, h, "K.h");
      Inherit_H_deleteRef(h , &exception); SIDL_REPORT(exception);
    }

    Inherit_K_deleteRef(k, &exception); SIDL_REPORT(exception);
  }

  {
    Inherit_L l;
    Inherit_A a;
    Inherit_A2 a2;

    l = makeLObject();
    printf("\n\nClass L: implements A, A2.\n");
    INHERITTEST(Inherit_L_aa, l, "L.a");
    INTTEST(Inherit_L_a2, l, "L.a2");

    INHERITTEST(Inherit_L_l, l, "L.l");
    MYASSERT(a = Inherit_A__cast(l, &exception));
    if (a) {
      INHERITTEST(Inherit_A_a, a, "L.a");
      Inherit_A_deleteRef(a , &exception); SIDL_REPORT(exception);
    }

    MYASSERT(a2 = Inherit_A2__cast(l, &exception));
    if (a2) {
      INTTEST(Inherit_A2_a, a2, "L.a2");
      Inherit_A2_deleteRef(a2 , &exception); SIDL_REPORT(exception);
    }

    Inherit_L_deleteRef(l, &exception); SIDL_REPORT(exception);
  }

  printf("\n");
  synch_RegOut_close(tracker, &exception); SIDL_REPORT(exception);
  synch_RegOut_deleteRef(tracker, &exception); SIDL_REPORT(exception);
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

