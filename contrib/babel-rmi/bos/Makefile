PORTID=9999
IP=127.0.0.1

ALLLIBS=${CXXLIBS} ${CLIBS} -L${BABEL_LIBDIR} -lsidlx -lsidl -lpthread

include ../settings.make

all: bos_server


help:
	@echo "After make, run bos_server [port]"
	@echo "(The Hello World program looks for port 9999 on localhost)"
	@echo "Don't forget to set your SIDL_DLL_PATH to the include"
	@echo "libsidl.scl, libsidlx.scl, and libhello.so (if you're going"
	@echo "to run the Wello World test!)"

bos_server: bos_server.lo
	${BABEL_LIBTOOL} --mode=link ${CXX} -o bos_server bos_server.o ${ALLLIBS}

clean:
	rm *.o
	rm *.lo
	rm bos_server

