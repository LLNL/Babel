IMPLMODULESRCS = wrapper_User_Mod.F90
IMPLSRCS = wrapper_User_Impl.F90
IORHDRS = wrapper_User_IOR.h
IORSRCS = wrapper_User_IOR.c
SKELSRCS = wrapper_User_fSkel.c
STUBHDRS = wrapper_User_array_bindcdecls.h wrapper_User_fAbbrev.h             \
  wrapper_User_fStub.h wrapper_User_mod_bindcdecls.h
STUBMODULESRCS = wrapper_User.F90
STUBSRCS = wrapper_User_fStub.c
TYPEMODULESRCS = wrapper_User_type.F90

_deps_wrapper_User =  wrapper_User_type
wrapper_User$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_wrapper_User))

_deps_wrapper_User_Impl =  wrapper_User
wrapper_User_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_wrapper_User_Impl))

