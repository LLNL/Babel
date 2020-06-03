IMPLMODULESRCS = wrapper_Data_Mod.F90
IMPLSRCS = wrapper_Data_Impl.F90
IORHDRS = wrapper_Data_IOR.h
IORSRCS = wrapper_Data_IOR.c
SKELSRCS = wrapper_Data_fSkel.c
STUBHDRS = wrapper_Data_array_bindcdecls.h wrapper_Data_fAbbrev.h             \
  wrapper_Data_fStub.h wrapper_Data_mod_bindcdecls.h
STUBMODULESRCS = wrapper_Data.F90
STUBSRCS = wrapper_Data_fStub.c
TYPEMODULESRCS = wrapper_Data_type.F90

_deps_wrapper_Data =  wrapper_Data_type
wrapper_Data$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_wrapper_Data))

_deps_wrapper_Data_Impl =  wrapper_Data
wrapper_Data_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_wrapper_Data_Impl))

