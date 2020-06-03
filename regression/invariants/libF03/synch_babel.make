synch_FSKELSRCS = synch_RegOut_fSkelf.F03
synch_IMPLSRCS = synch_RegOut_Impl.F03
synch_IORHDRS = synch_IOR.h synch_RegOut_IOR.h synch_ResultType_IOR.h
synch_IORSRCS = synch_RegOut_IOR.c
synch_SKELSRCS = synch_RegOut_fSkel.c
synch_STUBHDRS = synch_RegOut_fAbbrev.h synch_RegOut_fStub.h                  \
  synch_ResultType_fAbbrev.h
synch_STUBMODULESRCS = synch_RegOut.F03
synch_STUBSRCS = synch_RegOut_fStub.c
synch_TYPEMODULESRCS = synch_RegOut_type.F03 synch_ResultType_type.F03

_deps_synch_RegOut =  synch_RegOut_type synch_ResultType_type
synch_RegOut$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_synch_RegOut))

_deps_synch_RegOut_Impl =  synch_RegOut
synch_RegOut_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_synch_RegOut_Impl))

_deps_synch_RegOut_fSkelf =  synch_ResultType_type synch_RegOut_type          \
  synch_RegOut_Impl
synch_RegOut_fSkelf$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_synch_RegOut_fSkelf))

