#include <stdio.h>
#include <stdlib.h>
#include "sidlx_rmi_SimpleOrb.h"
#include "sidl_BaseException.h"
#include "sidl_Exception.h"
#include "sidl_rmi_ProtocolFactory.h"
#include "sidl_rmi_ServerRegistry.h"
#include "sidl_rmi_ServerInfo.h"
#include <pthread.h>

int main(int argc, char * argv[]) { 

  int port_number;
  sidlx_rmi_SimpleOrb echo=NULL;
  
  sidl_BaseInterface ex=NULL;
  sidl_BaseException s_b_e = NULL;
  char * str;
  char *url;
  int tid;
  sidl_rmi_ServerInfo si = NULL;
  if ( argc < 2 ) { 
    printf("usage: %s <portnumber>\n", argv[0] );
    exit(0);
  }
  port_number = atoi(argv[1]);
  if(!sidl_rmi_ProtocolFactory_addProtocol("simhandle","sidlx.rmi.SimHandle",&ex )){
    printf("Error in addProtocol\n");
    exit(2);
  }

  echo = sidlx_rmi_SimpleOrb__create(&ex);SIDL_CHECK(ex);
  
  sidlx_rmi_SimpleOrb_requestPort( echo, port_number, &ex);SIDL_CHECK(ex);
  tid = sidlx_rmi_SimpleOrb_run( echo, &ex );SIDL_CHECK(ex);
  si = sidl_rmi_ServerInfo__cast(echo,&ex);SIDL_CHECK(ex);
  sidl_rmi_ServerRegistry_registerServer(si, &ex);SIDL_CHECK(ex);
  sidl_rmi_ServerInfo_deleteRef(si,&ex);SIDL_CHECK(ex);
  url = sidl_rmi_ServerRegistry_getServerURL("1000", &ex);
  printf("url: %s\n", url);SIDL_CHECK(ex);
  free(url);
#ifdef HAVE_PTHREAD
  if(tid) {
    pthread_join(tid, NULL);
  }
#endif 
  sidlx_rmi_SimpleOrb_deleteRef(echo,&ex);SIDL_CHECK(ex);
  
  return 0;
 EXIT:
  {
    sidl_BaseInterface throwaway = NULL;
    s_b_e = sidl_BaseException__cast(ex, &throwaway);
    str = sidl_BaseException_getNote(s_b_e, &throwaway);
    printf("ERROR! %s \n",str);
    
    return(1);
  }
}

