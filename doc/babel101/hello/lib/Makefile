.cc.o:
	g++ -fPIC -I$(HOME)/babel/include -c $<
.c.o:
	gcc -fPIC -I$(HOME)/babel/include -c $<

include babel.make
OBJS = ${IMPLSRCS:.cc=.o} ${IORSRCS:.c=.o} \
       ${SKELSRCS:.cc=.o} ${STUBSRCS:.cc=.o}

libhello.so: ${OBJS}
	g++ -shared -o $@ ${OBJS}

clean:
	${RM} *.o libhello.so

