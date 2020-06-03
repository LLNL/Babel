DIRS = libCxx libF90 runCxx runPython 

all: settings.make all-recursive all-local

settings.make: mini-configure
	@echo "To configure and build this demo:"
	@echo " 1.  Install Babel with Python, F90, C++ and C support"
	@echo " 2.  Locate installed babel-config script"
	@echo " 3.  Run mini-configure with absolute path to babel-config as argument"
	@echo "     (This will generate the needed settings.make file)"
	@echo " 4.  Retype make"
	@echo " 5.  For text-based python, \"cd runPython; make MYSCRIPT=myscript.py py\""
	@echo " 6.  For python GUI, \"cd runGUI; gmake\""
	@exit -1

all-recursive:
	for subdir in $(DIRS); do \
		echo "Making all in $$subdir"; \
		( cd $$subdir && $(MAKE) all) || exit 1; \
	done

all-local:

clean: clean-recursive clean-local

clean-recursive:
	for subdir in $(DIRS); do \
		echo "Making clean in $$subdir"; \
		( cd $$subdir && $(MAKE) clean ) || exit 1; \
	done

clean-local:
	$(RM) *~ 

new: new-recursive new-local

new-recursive:
	for subdir in $(DIRS); do \
		echo "Making new in $$subdir"; \
		( cd $$subdir && $(MAKE) new ) || exit 1; \
	done

new-local: clean-local
	$(RM) settings.make wave2d.scl 
