client_IORHDRS = synch_IOR.h synch_RegOut_IOR.h synch_ResultType_IOR.h        \
  wrapper_User_IOR.h
client_STUBHDRS = synch_RegOut_array_bindcdecls.h synch_RegOut_fAbbrev.h      \
  synch_RegOut_fStub.h synch_RegOut_mod_bindcdecls.h                          \
  synch_ResultType_array_bindcdecls.h synch_ResultType_fAbbrev.h              \
  synch_ResultType_mod_bindcdecls.h wrapper_User_array_bindcdecls.h           \
  wrapper_User_fAbbrev.h wrapper_User_fStub.h wrapper_User_mod_bindcdecls.h
client_STUBMODULESRCS = synch_RegOut.F90 synch_ResultType.F90 wrapper_User.F90
client_STUBSRCS = synch_RegOut_fStub.c synch_ResultType_fStub.c               \
  wrapper_User_fStub.c
client_TYPEMODULESRCS = synch_RegOut_type.F90 synch_ResultType_type.F90       \
  wrapper_User_type.F90

_deps_synch_RegOut =  synch_ResultType_type synch_RegOut_type
synch_RegOut$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_synch_RegOut))

_deps_synch_ResultType =  synch_ResultType_type
synch_ResultType$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_synch_ResultType))

_deps_wrapper_User =  wrapper_User_type
wrapper_User$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_wrapper_User))

