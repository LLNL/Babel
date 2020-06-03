synch_IORHDRS = synch_IOR.h synch_RegOut_IOR.h synch_ResultType_IOR.h
synch_STUBHDRS = synch_RegOut_fAbbrev.h synch_RegOut_fStub.h                  \
  synch_ResultType_fAbbrev.h
synch_STUBMODULESRCS = synch_RegOut.F03
synch_STUBSRCS = synch_RegOut_fStub.c
synch_TYPEMODULESRCS = synch_RegOut_type.F03 synch_ResultType_type.F03

_deps_synch_RegOut =  synch_RegOut_type synch_ResultType_type
synch_RegOut$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_synch_RegOut))

