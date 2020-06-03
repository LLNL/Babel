IORHDRS = s_Color_IOR.h s_Combined_IOR.h s_Empty_IOR.h s_Hard_IOR.h s_IOR.h   \
  s_Rarrays_IOR.h s_Simple_IOR.h s_StructTest_IOR.h
STUBHDRS = s_Color_array_bindcdecls.h s_Color_fAbbrev.h                       \
  s_Color_mod_bindcdecls.h s_Combined_fAbbrev.h s_Empty_fAbbrev.h             \
  s_Hard_fAbbrev.h s_Rarrays_fAbbrev.h s_Simple_fAbbrev.h                     \
  s_StructTest_array_bindcdecls.h s_StructTest_fAbbrev.h s_StructTest_fStub.h \
  s_StructTest_mod_bindcdecls.h
STUBMODULESRCS = s_Color.F90 s_StructTest.F90
STUBSRCS = s_Color_fStub.c s_Combined_fStub.c s_Empty_fStub.c s_Hard_fStub.c  \
  s_Rarrays_fStub.c s_Simple_fStub.c s_StructTest_fStub.c
TYPEMODULESRCS = s_Color_type.F90 s_Combined.F90 s_Empty.F90 s_Hard.F90       \
  s_Rarrays.F90 s_Simple.F90 s_StructTest_type.F90

_deps_s_Color =  s_Color_type
s_Color$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_s_Color))

_deps_s_Combined =  s_Simple s_Hard
s_Combined$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_s_Combined))

_deps_s_StructTest =  s_StructTest_type s_Empty s_Simple s_Hard s_Combined    \
  s_Rarrays
s_StructTest$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_s_StructTest))

