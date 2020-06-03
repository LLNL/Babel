synch_IORHDRS = synch_IOR.h synch_RegOut_IOR.h synch_ResultType_IOR.h
synch_STUBHDRS = synch_RegOut_array_bindcdecls.h synch_RegOut_fAbbrev.h       \
  synch_RegOut_fStub.h synch_RegOut_mod_bindcdecls.h                          \
  synch_ResultType_array_bindcdecls.h synch_ResultType_fAbbrev.h              \
  synch_ResultType_mod_bindcdecls.h
synch_STUBMODULESRCS = synch_RegOut.F90 synch_ResultType.F90
synch_STUBSRCS = synch_RegOut_fStub.c synch_ResultType_fStub.c
synch_TYPEMODULESRCS = synch_RegOut_type.F90 synch_ResultType_type.F90

_deps_synch_RegOut =  synch_ResultType_type synch_RegOut_type
synch_RegOut$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_synch_RegOut))

_deps_synch_ResultType =  synch_ResultType_type
synch_ResultType$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_synch_ResultType))

