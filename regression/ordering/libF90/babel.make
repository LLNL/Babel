IMPLMODULESRCS = Ordering_IntOrderTest_Mod.F90
IMPLSRCS = Ordering_IntOrderTest_Impl.F90
IORHDRS = Ordering_IOR.h Ordering_IntOrderTest_IOR.h
IORSRCS = Ordering_IntOrderTest_IOR.c
SKELSRCS = Ordering_IntOrderTest_fSkel.c
STUBHDRS = Ordering_IntOrderTest_array_bindcdecls.h                           \
  Ordering_IntOrderTest_fAbbrev.h Ordering_IntOrderTest_fStub.h               \
  Ordering_IntOrderTest_mod_bindcdecls.h
STUBMODULESRCS = Ordering_IntOrderTest.F90
STUBSRCS = Ordering_IntOrderTest_fStub.c
TYPEMODULESRCS = Ordering_IntOrderTest_type.F90

_deps_Ordering_IntOrderTest =  Ordering_IntOrderTest_type
Ordering_IntOrderTest$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Ordering_IntOrderTest))

_deps_Ordering_IntOrderTest_Impl =  Ordering_IntOrderTest
Ordering_IntOrderTest_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Ordering_IntOrderTest_Impl))

