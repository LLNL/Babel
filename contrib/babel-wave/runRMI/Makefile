OPT=-g

all:  server client

server: server.lo
	babel-cc $(OPT) -o server server.lo -L../runC -lstubs -lsidlx

server.lo: server.c
	babel-cc $(OPT) -c -o server.lo -I../runC server.c

client:  client.lo
	babel-cxx $(OPT) -o client client.lo -L../runCxx -lstubs -lsidlx

client.lo:   client.cxx
	babel-cxx $(OPT) -c -o client.lo -I../runCxx client.cxx

clean:
	rm -f *.o *.lo  *~

new: clean
	rm -f client server

