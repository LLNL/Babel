IORHDRS = ArrayTest_ArrayOps_IOR.h ArrayTest_IOR.h
STUBHDRS = ArrayTest_ArrayOps_array_bindcdecls.h ArrayTest_ArrayOps_fAbbrev.h \
  ArrayTest_ArrayOps_fStub.h ArrayTest_ArrayOps_mod_bindcdecls.h
STUBMODULESRCS = ArrayTest_ArrayOps.F90
STUBSRCS = ArrayTest_ArrayOps_fStub.c
TYPEMODULESRCS = ArrayTest_ArrayOps_type.F90

_deps_ArrayTest_ArrayOps =  ArrayTest_ArrayOps_type
ArrayTest_ArrayOps$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_ArrayTest_ArrayOps))

