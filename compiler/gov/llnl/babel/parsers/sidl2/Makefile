

# WARNING!!!! Any file not listed here will be deleted with "make new"
PRESERVE = README Makefile SIDLParser.jjt Main.java SIDLDumpVisitor.java \
	SimpleNode.patch SIDLTouchUpVisitor.java ParseTree2ASTVisitor.java \
	Expr2ASTVisitor.java

TESTS = empty_file empty_pkg empty_class empty_method \
	primative_types arrays expressions $(REGRESSION)

#these are copied directly from the babel/regression tree
REGRESSION = args enums strings ArrayTest objarg output inherit ordering sort Overload exceptions vector

all : SIDLParser.java

Main.class: Main.java SIDLParser.java
	javac *.java

SIDLParser.java : SIDLParser.jj
	javacc SIDLParser.jj
	mv SimpleNode.java ParseTreeNode.java
	perl -p -i -e 's/SimpleNode/ParseTreeNode/g;' *.java


SIDLParser.jj : SIDLParser.jjt
	jjtree SIDLParser.jjt;
	@if grep gkk SimpleNode.java > /dev/null ; then \
	  echo "SimpleNode.java already patched..."; \
        else \
          echo 'patch -u -i SimpleNode.patch SimpleNode.java' ; \
          patch -u -i SimpleNode.patch SimpleNode.java ; \
	fi;

run:
	java Main

check: Main.class
	@for i in $(TESTS); do                                       \
	  if java Main < tests/$$i.sidl > tests/$$i.out 2>&1 ; then \
	    echo "$$i PASS"; 					    \
	  else							    \
	    echo "$$i FAIL"; 					    \
	  fi;							    \
	done;

clean: 
	$(RM) *.class *~

new: clean
	@list= ;				\
	for i in *.java *.jj ; do 		\
	  d=true; 				\
	  for j in $(PRESERVE) ; do 		\
	    if test "$$i" = "$$j"; then 	\
	      d=false; 				\
            fi; 				\
          done; 				\
          if test "$$d" = "true"; then   	\
	    list="$$list    $$i";		\
	  fi;					\
	done;					\
	echo $(RM) $$list;			\
	$(RM) $$list
	$(RM) test/*.out	
