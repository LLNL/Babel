client_IORHDRS = wrapper_Data_IOR.h
client_STUBHDRS = wrapper_Data_array_bindcdecls.h wrapper_Data_fAbbrev.h      \
  wrapper_Data_fStub.h wrapper_Data_mod_bindcdecls.h
client_STUBMODULESRCS = wrapper_Data.F03
client_STUBSRCS = wrapper_Data_fStub.c
client_TYPEMODULESRCS = wrapper_Data_type.F03

_deps_wrapper_Data =  wrapper_Data_type
wrapper_Data$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_wrapper_Data))

