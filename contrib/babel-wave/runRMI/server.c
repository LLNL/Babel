/* server.c
 * 
 * A simple example server for babel-wave
 */

#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include <sys/resource.h>
#include <unistd.h>
#include "sidlx_rmi_SimpleOrb.h"
#include "sidl_BaseException.h"
#include "sidl_Exception.h"
#include "sidl_rmi_ProtocolFactory.h"
#include "sidl_rmi_ServerRegistry.h"
#include "sidl_rmi_ServerInfo.h"
#include "cxx_ScalarField.h"
#include <pthread.h>

int main(int argc, char * argv[]) { 

  int port_number;
  sidlx_rmi_SimpleOrb echo=NULL;
  sidl_BaseInterface ex=NULL;
  sidl_BaseException s_b_e = NULL;
  char url[1024];
  int thread_id;
  struct rlimit limit;
  sidl_rmi_ServerInfo si = NULL;
  if ( argc < 2 ) { 
    printf("usage: %s <portnumber>\n", argv[0] );
    exit(0);
  }
  port_number = atoi(argv[1]);

  /* NOTE: 
   * changing this limit does not matter for this example
   * but can be important for massive fan-in situations in 
   * a cluster.
   */
  if ( getrlimit( RLIMIT_NOFILE, &limit ) == 0 ) { 
    printf("Changing RLIMIT_NOFILE from %d to %d\n",limit.rlim_cur, limit.rlim_max);
    limit.rlim_cur = limit.rlim_max;
    setrlimit( RLIMIT_NOFILE, &limit );
  }                                                                            

  /* create the orb & set the port */
  sidl_rmi_ProtocolFactory_addProtocol("simhandle","sidlx.rmi.SimHandle",&ex); SIDL_CHECK(ex);
  echo = sidlx_rmi_SimpleOrb__create(&ex);SIDL_CHECK(ex);
  sidlx_rmi_SimpleOrb_requestPortInRange( echo, port_number, port_number+100, &ex); SIDL_CHECK(ex);

  /* register the orb in the server registry... note the upcast and 
   * deleting the extra (unneeded) reference
   */
  si = sidl_rmi_ServerInfo__cast(echo,&ex);SIDL_CHECK(ex);
  sidl_rmi_ServerRegistry_registerServer(si, &ex);SIDL_CHECK(ex);
  sidl_rmi_ServerInfo_deleteRef(si,&ex);SIDL_CHECK(ex);

  /* run the orb (internally spawns a listening thread) */
  thread_id = sidlx_rmi_SimpleOrb_run( echo, &ex );SIDL_CHECK(ex);

  /* if spawn succeded, we register an instance a publish its address */
  if ( thread_id ) { 
    cxx_ScalarField c = cxx_ScalarField__create(&ex);  SIDL_CHECK(ex);
    char * str = cxx_ScalarField__getURL(c,&ex);
    printf("url: %s\n", str); SIDL_CHECK(ex);
    free( str );

    printf("Press <RETURN> to shutdown --->");
    fflush(stdout);
    int  cc = getc(stdin);

    /* now just wait for the thread to die (or press CTRL^C) */
    sidlx_rmi_SimpleOrb_shutdown(echo,&ex); SIDL_CHECK(ex);
    pthread_join(thread_id, NULL);
  }
 
  return 0;
 EXIT:
  {
    char* str; 
    sidl_BaseInterface throwaway = NULL;
    s_b_e = sidl_BaseException__cast(ex, &throwaway);
    str = sidl_BaseException_getNote(s_b_e, &throwaway);
    printf("ERROR! %s \n",str);
    
    return(1);
  }
}

