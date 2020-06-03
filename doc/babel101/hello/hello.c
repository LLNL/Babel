#include <stdio.h>
#include "Hello_World.h"
 
int main(int argc, char** argv)
{
  Hello_World h = Hello_World__create();
  char* msg = Hello_World_getMsg(h);
  printf("%s\n", msg);
  Hello_World_deleteRef(h);
  free(msg);
}
