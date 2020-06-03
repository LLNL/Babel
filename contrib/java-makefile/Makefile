# This is an example Makefile for compiling server side Java bindings.
# Some values need to be filled in, but, with minimal modification, this
# makefile should build Babel server side bindings for you.  However, Do not 
# forget to also define your Babel environment variables before running
# Babelized Java code.
#
# THE VARIABLES JAVA_HOME AND BABEL_BUILD_DIR MUST BE FILLED IN!
#
# This file was originally written by Susan Knoop, and then 
# updated by Niji Shi.  Comments and Java binding written by Jim Leek. 


JAVA_HOME = #where ever java is installed, on my machine: /usr/java/j2sdk1.4.2_01/
JNI_INCLUDES = -I$(JAVA_HOME)/include/ -I$(JAVA_HOME)/include/linux
JAVA_LIBS = -L$(JAVA_HOME)/jre/lib/i386
BABEL_BUILD_DIR = #Babel build directory, on my machine: /home/leek2/babel/vpath
SIDL_LIBS = $(BABEL_BUILD_DIR)/runtime/java
LOADER_OPTS = -Wl,-rpath -Wl,$(JAVA_LIBS) -Wl,-rpath -Wl,$(JAVA_LIBS)/client

include babel.make

SRCS = $(IORSRCS) $(SKELSRCS) $(STUBSRCS)

OBJS = $(IORSRCS:.c=.lo) $(SKELSRCS:.c=.lo) $(STUBSRCS:.c=.lo)

all : libImpl.la .java.class


libImpl.la : $(OBJS)
	babel-libtool --mode=link gcc -O $(SKELSRCS) -o libImpl.la -no-undefined -rpath `pwd` -release `babel-config --version` -no-undefined $(OBJS) $(JNI_INCLUDES) -I$(BABEL_HOME)/include $(JAVA_LIBS) $(LOADER_OPTS) -L$(BABEL_HOME)/lib -lsidl -lpthread -ljava


$(SRCS): babel-stamp


babel-stamp : ../rmi.sidl
	babel --server=Java ../rmi.sidl
	rm -f libImpl.scl
	echo "<?xml version=\"1.0\" ?>" > libImpl.scl
	echo "<library uri=\""`pwd`"/libImpl.la\" scope=\"local\" resolution=\"lazy\" >" >> libImpl.scl
	grep  __set_epv $(IORSRCS) /dev/null | awk 'BEGIN {FS=":"} { print $$1}' | sort -u | sed -e 's/_IOR.c//g' -e 's/_/./g' | awk ' { printf "  <class name=\"%s\" desc=\"ior/impl\" />\n", $$1 }' >> libImpl.scl
	echo "</library>" >> libImpl.scl
	echo "Bogus file since make doesn't understand generating multiple files " > babel-stamp

.SUFFIXES: .c .lo .java .class 

.c.lo:
	babel-libtool --mode=compile gcc -I$(BABEL_HOME)/include $(JNI_INCLUDES) -g -c -o $*.o -fPIC -DPIC $< 


.java.class:
	javac -classpath $(SIDL_LIBS)/sidl-0.9.7.jar sidl/*.java && javac -classpath ./:$(SIDL_LIBS)/sidl-0.9.7.jar rmi/*.java

clean:
	$(RM) *.lo  *.class *.la *.o rmi/*.class sidl/*.class

realclean: clean
	$(RM)  babel-stamp *.scl
