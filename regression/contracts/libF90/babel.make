IMPLMODULESRCS = vect_Utils_Mod.F90 vect_vDivByZeroExcept_Mod.F90             \
  vect_vExcept_Mod.F90 vect_vNegValExcept_Mod.F90
IMPLSRCS = vect_Utils_Impl.F90 vect_vDivByZeroExcept_Impl.F90                 \
  vect_vExcept_Impl.F90 vect_vNegValExcept_Impl.F90
IORHDRS = vect_BadLevel_IOR.h vect_ExpectExcept_IOR.h vect_IOR.h              \
  vect_Utils_IOR.h vect_vDivByZeroExcept_IOR.h vect_vExcept_IOR.h             \
  vect_vNegValExcept_IOR.h
IORSRCS = vect_Utils_IOR.c vect_vDivByZeroExcept_IOR.c vect_vExcept_IOR.c     \
  vect_vNegValExcept_IOR.c
SKELSRCS = vect_Utils_fSkel.c vect_vDivByZeroExcept_fSkel.c                   \
  vect_vExcept_fSkel.c vect_vNegValExcept_fSkel.c
STUBHDRS = vect_BadLevel_array_bindcdecls.h vect_BadLevel_fAbbrev.h           \
  vect_BadLevel_mod_bindcdecls.h vect_ExpectExcept_array_bindcdecls.h         \
  vect_ExpectExcept_fAbbrev.h vect_ExpectExcept_mod_bindcdecls.h              \
  vect_Utils_array_bindcdecls.h vect_Utils_fAbbrev.h vect_Utils_fStub.h       \
  vect_Utils_mod_bindcdecls.h vect_vDivByZeroExcept_array_bindcdecls.h        \
  vect_vDivByZeroExcept_fAbbrev.h vect_vDivByZeroExcept_fStub.h               \
  vect_vDivByZeroExcept_mod_bindcdecls.h vect_vExcept_array_bindcdecls.h      \
  vect_vExcept_fAbbrev.h vect_vExcept_fStub.h vect_vExcept_mod_bindcdecls.h   \
  vect_vNegValExcept_array_bindcdecls.h vect_vNegValExcept_fAbbrev.h          \
  vect_vNegValExcept_fStub.h vect_vNegValExcept_mod_bindcdecls.h
STUBMODULESRCS = vect_BadLevel.F90 vect_ExpectExcept.F90 vect_Utils.F90       \
  vect_vDivByZeroExcept.F90 vect_vExcept.F90 vect_vNegValExcept.F90
STUBSRCS = vect_BadLevel_fStub.c vect_ExpectExcept_fStub.c vect_Utils_fStub.c \
  vect_vDivByZeroExcept_fStub.c vect_vExcept_fStub.c                          \
  vect_vNegValExcept_fStub.c
TYPEMODULESRCS = vect_BadLevel_type.F90 vect_ExpectExcept_type.F90            \
  vect_Utils_type.F90 vect_vDivByZeroExcept_type.F90 vect_vExcept_type.F90    \
  vect_vNegValExcept_type.F90

_deps_vect_BadLevel =  vect_BadLevel_type
vect_BadLevel$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_vect_BadLevel))

_deps_vect_ExpectExcept =  vect_ExpectExcept_type
vect_ExpectExcept$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_vect_ExpectExcept))

_deps_vect_Utils =  vect_Utils_type vect_vNegValExcept_type                   \
  vect_BadLevel_type vect_vDivByZeroExcept_type
vect_Utils$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_vect_Utils))

_deps_vect_Utils_Impl =  vect_Utils vect_vNegValExcept vect_BadLevel          \
  vect_vDivByZeroExcept
vect_Utils_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_vect_Utils_Impl))

_deps_vect_vDivByZeroExcept =  vect_vExcept_type vect_vDivByZeroExcept_type
vect_vDivByZeroExcept$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_vect_vDivByZeroExcept))

_deps_vect_vDivByZeroExcept_Impl =  vect_vDivByZeroExcept
vect_vDivByZeroExcept_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_vect_vDivByZeroExcept_Impl))

_deps_vect_vExcept =  vect_vExcept_type
vect_vExcept$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_vect_vExcept))

_deps_vect_vExcept_Impl =  vect_vExcept
vect_vExcept_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_vect_vExcept_Impl))

_deps_vect_vNegValExcept =  vect_vNegValExcept_type vect_vExcept_type
vect_vNegValExcept$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_vect_vNegValExcept))

_deps_vect_vNegValExcept_Impl =  vect_vNegValExcept
vect_vNegValExcept_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_vect_vNegValExcept_Impl))

