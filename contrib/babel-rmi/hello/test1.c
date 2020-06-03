#include <stdio.h>
#include <string.h>
#include "hello_World.h"
#include "sidl_String.h"
#include "sidl_rmi_NetworkException.h"

#define BUFSIZE  1024
int main(int argc, char* argv[]) { 
  char * msg;
  sidl_BaseInterface _ex = NULL;
  hello_World h = hello_World__create(&_ex);
  if ( argc > 1 ) { 
    hello_World_setName( h, argv[1],&_ex );
  } else { 
    hello_World_setName( h, "World",&_ex );
  }
  msg = hello_World_getMsg( h,&_ex );
  hello_World_deleteRef( h,&_ex );
  printf("%s\n", msg );
  sidl_String_free( msg );
  return 0;
}
