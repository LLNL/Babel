IMPLMODULESRCS = Overload_AClass_Mod.F90 Overload_AnException_Mod.F90         \
  Overload_BClass_Mod.F90 Overload_ParentTest_Mod.F90 Overload_Test_Mod.F90
IMPLSRCS = Overload_AClass_Impl.F90 Overload_AnException_Impl.F90             \
  Overload_BClass_Impl.F90 Overload_ParentTest_Impl.F90 Overload_Test_Impl.F90
IORHDRS = Overload_AClass_IOR.h Overload_AnException_IOR.h                    \
  Overload_BClass_IOR.h Overload_IOR.h Overload_ParentTest_IOR.h              \
  Overload_Test_IOR.h
IORSRCS = Overload_AClass_IOR.c Overload_AnException_IOR.c                    \
  Overload_BClass_IOR.c Overload_ParentTest_IOR.c Overload_Test_IOR.c
SKELSRCS = Overload_AClass_fSkel.c Overload_AnException_fSkel.c               \
  Overload_BClass_fSkel.c Overload_ParentTest_fSkel.c Overload_Test_fSkel.c
STUBHDRS = Overload_AClass_array_bindcdecls.h Overload_AClass_fAbbrev.h       \
  Overload_AClass_fStub.h Overload_AClass_mod_bindcdecls.h                    \
  Overload_AnException_array_bindcdecls.h Overload_AnException_fAbbrev.h      \
  Overload_AnException_fStub.h Overload_AnException_mod_bindcdecls.h          \
  Overload_BClass_array_bindcdecls.h Overload_BClass_fAbbrev.h                \
  Overload_BClass_fStub.h Overload_BClass_mod_bindcdecls.h                    \
  Overload_ParentTest_array_bindcdecls.h Overload_ParentTest_fAbbrev.h        \
  Overload_ParentTest_fStub.h Overload_ParentTest_mod_bindcdecls.h            \
  Overload_Test_array_bindcdecls.h Overload_Test_fAbbrev.h                    \
  Overload_Test_fStub.h Overload_Test_mod_bindcdecls.h
STUBMODULESRCS = Overload_AClass.F90 Overload_AnException.F90                 \
  Overload_BClass.F90 Overload_ParentTest.F90 Overload_Test.F90
STUBSRCS = Overload_AClass_fStub.c Overload_AnException_fStub.c               \
  Overload_BClass_fStub.c Overload_ParentTest_fStub.c Overload_Test_fStub.c
TYPEMODULESRCS = Overload_AClass_type.F90 Overload_AnException_type.F90       \
  Overload_BClass_type.F90 Overload_ParentTest_type.F90 Overload_Test_type.F90

_deps_Overload_AClass =  Overload_AClass_type
Overload_AClass$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Overload_AClass))

_deps_Overload_AClass_Impl =  Overload_AClass
Overload_AClass_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Overload_AClass_Impl))

_deps_Overload_AnException =  Overload_AnException_type
Overload_AnException$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Overload_AnException))

_deps_Overload_AnException_Impl =  Overload_AnException
Overload_AnException_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Overload_AnException_Impl))

_deps_Overload_BClass =  Overload_AClass_type Overload_BClass_type
Overload_BClass$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Overload_BClass))

_deps_Overload_BClass_Impl =  Overload_BClass
Overload_BClass_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Overload_BClass_Impl))

_deps_Overload_ParentTest =  Overload_ParentTest_type
Overload_ParentTest$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Overload_ParentTest))

_deps_Overload_ParentTest_Impl =  Overload_ParentTest
Overload_ParentTest_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Overload_ParentTest_Impl))

_deps_Overload_Test =  Overload_ParentTest_type Overload_AClass_type          \
  Overload_Test_type Overload_BClass_type Overload_AnException_type
Overload_Test$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Overload_Test))

_deps_Overload_Test_Impl =  Overload_Test Overload_AnException                \
  Overload_AClass Overload_BClass
Overload_Test_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Overload_Test_Impl))

