/* #include "sidlx_common.h" */
/* #include "sidl_BaseInterface.h" */
/* #include <stdio.h> */
#include <stdio.h>
#include <stddef.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>

int main() {

  int			listenfd, connfd,n;
  struct sockaddr_in	servaddr;
  char			buff[2048];
  /* time_t		ticks; */
  
  if((listenfd = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
    printf("listen fail\n\n");
    fflush(stdout);
  }
  
  bzero(&servaddr, sizeof(servaddr));
  servaddr.sin_family      = AF_INET;
  servaddr.sin_addr.s_addr = htonl(INADDR_ANY);
  servaddr.sin_port        = htons(13);	/* daytime server */
  
  if((n=s_bind(listenfd, &servaddr, sizeof(servaddr))) <0){
    printf("bind fail\n\n");
    fflush(stdout);
    exit(0);
  }


}
