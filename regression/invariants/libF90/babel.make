IMPLMODULESRCS = knapsack_gKnapsack_Mod.F90 knapsack_kBadWeightExcept_Mod.F90 \
  knapsack_kExcept_Mod.F90 knapsack_kSizeExcept_Mod.F90                       \
  knapsack_npKnapsack_Mod.F90 knapsack_nwKnapsack_Mod.F90
IMPLSRCS = knapsack_gKnapsack_Impl.F90 knapsack_kBadWeightExcept_Impl.F90     \
  knapsack_kExcept_Impl.F90 knapsack_kSizeExcept_Impl.F90                     \
  knapsack_npKnapsack_Impl.F90 knapsack_nwKnapsack_Impl.F90
IORHDRS = knapsack_ExpectExcept_IOR.h knapsack_IOR.h knapsack_gKnapsack_IOR.h \
  knapsack_iKnapsack_IOR.h knapsack_kBadWeightExcept_IOR.h                    \
  knapsack_kExcept_IOR.h knapsack_kSizeExcept_IOR.h knapsack_npKnapsack_IOR.h \
  knapsack_nwKnapsack_IOR.h
IORSRCS = knapsack_gKnapsack_IOR.c knapsack_kBadWeightExcept_IOR.c            \
  knapsack_kExcept_IOR.c knapsack_kSizeExcept_IOR.c knapsack_npKnapsack_IOR.c \
  knapsack_nwKnapsack_IOR.c
SKELSRCS = knapsack_gKnapsack_fSkel.c knapsack_kBadWeightExcept_fSkel.c       \
  knapsack_kExcept_fSkel.c knapsack_kSizeExcept_fSkel.c                       \
  knapsack_npKnapsack_fSkel.c knapsack_nwKnapsack_fSkel.c
STUBHDRS = knapsack_ExpectExcept_array_bindcdecls.h                           \
  knapsack_ExpectExcept_fAbbrev.h knapsack_ExpectExcept_mod_bindcdecls.h      \
  knapsack_gKnapsack_array_bindcdecls.h knapsack_gKnapsack_fAbbrev.h          \
  knapsack_gKnapsack_fStub.h knapsack_gKnapsack_mod_bindcdecls.h              \
  knapsack_iKnapsack_array_bindcdecls.h knapsack_iKnapsack_fAbbrev.h          \
  knapsack_iKnapsack_fStub.h knapsack_iKnapsack_mod_bindcdecls.h              \
  knapsack_kBadWeightExcept_array_bindcdecls.h                                \
  knapsack_kBadWeightExcept_fAbbrev.h knapsack_kBadWeightExcept_fStub.h       \
  knapsack_kBadWeightExcept_mod_bindcdecls.h                                  \
  knapsack_kExcept_array_bindcdecls.h knapsack_kExcept_fAbbrev.h              \
  knapsack_kExcept_fStub.h knapsack_kExcept_mod_bindcdecls.h                  \
  knapsack_kSizeExcept_array_bindcdecls.h knapsack_kSizeExcept_fAbbrev.h      \
  knapsack_kSizeExcept_fStub.h knapsack_kSizeExcept_mod_bindcdecls.h          \
  knapsack_npKnapsack_array_bindcdecls.h knapsack_npKnapsack_fAbbrev.h        \
  knapsack_npKnapsack_fStub.h knapsack_npKnapsack_mod_bindcdecls.h            \
  knapsack_nwKnapsack_array_bindcdecls.h knapsack_nwKnapsack_fAbbrev.h        \
  knapsack_nwKnapsack_fStub.h knapsack_nwKnapsack_mod_bindcdecls.h
STUBMODULESRCS = knapsack_ExpectExcept.F90 knapsack_gKnapsack.F90             \
  knapsack_iKnapsack.F90 knapsack_kBadWeightExcept.F90 knapsack_kExcept.F90   \
  knapsack_kSizeExcept.F90 knapsack_npKnapsack.F90 knapsack_nwKnapsack.F90
STUBSRCS = knapsack_ExpectExcept_fStub.c knapsack_gKnapsack_fStub.c           \
  knapsack_iKnapsack_fStub.c knapsack_kBadWeightExcept_fStub.c                \
  knapsack_kExcept_fStub.c knapsack_kSizeExcept_fStub.c                       \
  knapsack_npKnapsack_fStub.c knapsack_nwKnapsack_fStub.c
TYPEMODULESRCS = knapsack_ExpectExcept_type.F90 knapsack_gKnapsack_type.F90   \
  knapsack_iKnapsack_type.F90 knapsack_kBadWeightExcept_type.F90              \
  knapsack_kExcept_type.F90 knapsack_kSizeExcept_type.F90                     \
  knapsack_npKnapsack_type.F90 knapsack_nwKnapsack_type.F90

_deps_knapsack_gKnapsack_Impl =  knapsack_gKnapsack knapsack_kExcept          \
  knapsack_kBadWeightExcept knapsack_kSizeExcept
knapsack_gKnapsack_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_knapsack_gKnapsack_Impl))

_deps_knapsack_kBadWeightExcept_Impl =  knapsack_kBadWeightExcept
knapsack_kBadWeightExcept_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_knapsack_kBadWeightExcept_Impl))

_deps_knapsack_kExcept_Impl =  knapsack_kExcept
knapsack_kExcept_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_knapsack_kExcept_Impl))

_deps_knapsack_kSizeExcept_Impl =  knapsack_kSizeExcept
knapsack_kSizeExcept_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_knapsack_kSizeExcept_Impl))

_deps_knapsack_npKnapsack_Impl =  knapsack_npKnapsack knapsack_kExcept        \
  knapsack_kBadWeightExcept knapsack_kSizeExcept
knapsack_npKnapsack_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_knapsack_npKnapsack_Impl))

_deps_knapsack_nwKnapsack_Impl =  knapsack_nwKnapsack knapsack_kExcept        \
  knapsack_kBadWeightExcept knapsack_kSizeExcept
knapsack_nwKnapsack_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_knapsack_nwKnapsack_Impl))

