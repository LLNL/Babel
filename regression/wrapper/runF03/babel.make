FSKELSRCS = wrapper_Data_fSkelf.F03
IMPLSRCS = wrapper_Data_Impl.F03
IORHDRS = wrapper_Data_IOR.h
IORSRCS = wrapper_Data_IOR.c
SKELSRCS = wrapper_Data_fSkel.c
STUBHDRS = wrapper_Data_array_bindcdecls.h wrapper_Data_fAbbrev.h             \
  wrapper_Data_fStub.h wrapper_Data_mod_bindcdecls.h
STUBMODULESRCS = wrapper_Data.F03
STUBSRCS = wrapper_Data_fStub.c
TYPEMODULESRCS = wrapper_Data_type.F03

_deps_wrapper_Data =  wrapper_Data_type
wrapper_Data$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_wrapper_Data))

_deps_wrapper_Data_Impl =  wrapper_Data
wrapper_Data_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_wrapper_Data_Impl))

_deps_wrapper_Data_fSkelf =  wrapper_Data_Impl wrapper_Data_type
wrapper_Data_fSkelf$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_wrapper_Data_fSkelf))

