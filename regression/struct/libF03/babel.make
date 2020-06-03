FSKELSRCS = s_StructTest_fSkelf.F03
IMPLSRCS = s_StructTest_Impl.F03
IORHDRS = s_Color_IOR.h s_Combined_IOR.h s_Empty_IOR.h s_Hard_IOR.h s_IOR.h   \
  s_Rarrays_IOR.h s_Simple_IOR.h s_StructTest_IOR.h
IORSRCS = s_StructTest_IOR.c
SKELSRCS = s_StructTest_fSkel.c
STUBHDRS = s_Color_array_bindcdecls.h s_Color_fAbbrev.h s_Combined_fAbbrev.h  \
  s_Empty_fAbbrev.h s_Hard_fAbbrev.h s_Rarrays_fAbbrev.h s_Simple_fAbbrev.h   \
  s_StructTest_array_bindcdecls.h s_StructTest_fAbbrev.h s_StructTest_fStub.h \
  s_StructTest_mod_bindcdecls.h
STUBMODULESRCS = s_StructTest.F03
STUBSRCS = s_Combined_fStub.c s_Empty_fStub.c s_Hard_fStub.c                  \
  s_Rarrays_fStub.c s_Simple_fStub.c s_StructTest_fStub.c
TYPEMODULESRCS = s_Color_type.F03 s_Combined.F03 s_Empty.F03 s_Hard.F03       \
  s_Rarrays.F03 s_Simple.F03 s_StructTest_type.F03

_deps_s_Combined =  s_Simple s_Hard
s_Combined$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_s_Combined))

_deps_s_StructTest =  s_StructTest_type s_Empty s_Simple s_Hard s_Combined    \
  s_Rarrays
s_StructTest$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_s_StructTest))

_deps_s_StructTest_Impl =  s_Combined s_Empty s_Hard s_Rarrays s_Simple       \
  s_StructTest
s_StructTest_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_s_StructTest_Impl))

_deps_s_StructTest_fSkelf =  s_StructTest_Impl s_Combined s_Empty s_Hard      \
  s_Rarrays s_Simple s_StructTest_type
s_StructTest_fSkelf$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_s_StructTest_fSkelf))

