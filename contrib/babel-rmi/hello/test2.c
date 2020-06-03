#include <stdio.h>
#include <string.h>
#include "hello_World.h"
#include "sidl_rmi_ProtocolFactory.h"
#include "sidl_String.h"
#include "sidl_Exception.h"
#include "sidlx_rmi_IPv4Socket.h"
#include "sidlx_rmi_ClientSocket.h"
#include "sidlx_rmi_Socket.h"
#include "sidlx_rmi_Simvocation.h"
#include "sidl_rmi_NetworkException.h"
#define BUFSIZE  1024
int main(int argc, char* argv[]) { 
  char * msg;
  sidl_BaseInterface _ex = NULL;
  //int port_number, address;
  sidl_BaseException s_b_e = NULL;
  if(!sidl_rmi_ProtocolFactory_addProtocol("simhandle","sidlx.rmi.SimHandle",&_ex )){
    printf("Error in addProtocol\n");
    exit(2);
  }
  hello_World h = hello_World__createRemote("simhandle://localhost:9999", &_ex);SIDL_CHECK(_ex);
  char * str = NULL;
  char * url = hello_World__getURL(h,&_ex);SIDL_CHECK(_ex);
  hello_World h2 = hello_World__connect(url, &_ex);SIDL_CHECK(_ex);
  hello_World h3 = hello_World__connect(url, &_ex);SIDL_CHECK(_ex);
  if ( argc > 1 ) { 
    hello_World_setName( h, argv[1],&_ex );
  } else { 
    hello_World_setName( h, NULL,&_ex );
  }
  msg = hello_World_getMsg( h ,&_ex); SIDL_CHECK(_ex);
  
  hello_World_deleteRef( h3 ,&_ex); SIDL_CHECK(_ex);
  hello_World_deleteRef( h2 ,&_ex);SIDL_CHECK(_ex);
  hello_World_deleteRef( h ,&_ex);SIDL_CHECK(_ex);
  printf("Msg: %s\n", msg );
  sidl_String_free( msg );
  return 0;
 EXIT: 
  {
    sidl_BaseInterface throwaway = NULL;
    s_b_e = sidl_BaseException__cast(_ex, &throwaway);
    str = sidl_BaseException_getNote(s_b_e,&throwaway);
    printf("ERROR! %s \n",str);
    return(1);
  }
}
