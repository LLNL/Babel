.c.o:
	gcc -I$(HOME)/babel/include -Ilib -c $<

include babel.make
OBJS = hello.o ${STUBSRCS:.c=.o}

hello: ${OBJS}
	gcc ${OBJS} -o $@ \
	  -Rlib -Llib -lhello \
	  -R$(HOME)/babel/lib -L$(HOME)/babel/lib -lsidl

clean:
	${RM} *.o hello
