FSKELSRCS = knapsack_gKnapsack_fSkelf.F03 knapsack_npKnapsack_fSkelf.F03 \
  knapsack_nwKnapsack_fSkelf.F03 knapsack_kBadWeightExcept_fSkelf.F03 \
  knapsack_kExcept_fSkelf.F03 knapsack_kSizeExcept_fSkelf.F03
IMPLSRCS = knapsack_gKnapsack_Impl.F03 knapsack_npKnapsack_Impl.F03 \
  knapsack_nwKnapsack_Impl.F03 knapsack_kBadWeightExcept_Impl.F03 \
  knapsack_kExcept_Impl.F03 knapsack_kSizeExcept_Impl.F03
IORHDRS = knapsack_ExpectExcept_IOR.h knapsack_IOR.h \
  knapsack_gKnapsack_IORknapsack_npKnapsack_IOR.h \
  knapsack_nwKnapsack_IOR.h .h knapsack_kBadWeightExcept_IOR.h \
  knapsack_kExcept_IOR.h knapsack_kSizeExcept_IOR.h
IORSRCS = knapsack_gKnapsack_IOR.c knapsack_npKnapsack_IOR.c \
  knapsack_nwKnapsack_IOR.c knapsack_kBadWeightExcept_IOR.c \
  knapsack_kExcept_IOR.c knapsack_kSizeExcept_IOR.c
SKELSRCS = knapsack_gKnapsack_fSkel.c knapsack_npKnapsack_fSkel.c \
  knapsack_nwKnapsack_fSkel.c knapsack_kBadWeightExcept_fSkel.c \
  knapsack_kExcept_fSkel.c knapsack_kSizeExcept_fSkel.c
STUBHDRS = knapsack_ExpectExcept_fAbbrev.h \
  knapsack_gKnapsack_fAbbrev.h knapsack_gKnapsack_fStub.h  \
  knapsack_npKnapsack_fAbbrev.h knapsack_npKnapsack_fStub.h \
  knapsack_nwKnapsack_fAbbrev.h knapsack_nwKnapsack_fStub.h \
  knapsack_kBadWeightExcept_fAbbrev.h knapsack_kBadWeightExcept_fStub.h \
  knapsack_kExcept_fAbbrev.h knapsack_kExcept_fStub.h \
  knapsack_kSizeExcept_fAbbrev.h knapsack_kSizeExcept_fStub.h
STUBMODULESRCS = knapsack_gKnapsack.F03 knapsack_npKnapsack.F03 \
  knapsack_nwKnapsack.F03 knapsack_kBadWeightExcept.F03 knapsack_kExcept.F03 \
  knapsack_kSizeExcept.F03
STUBSRCS = knapsack_gKnapsack_fStub.c knapsack_npKnapsack_fStub.c \
  knapsack_nwKnapsack_fStub.c knapsack_kBadWeightExcept_fStub.c \
  knapsack_kExcept_fStub.c knapsack_kSizeExcept_fStub.c
TYPEMODULESRCS = knapsack_ExpectExcept_type.F03            \
  knapsack_gKnapsack_type.F03 knapsack_npKnapsack_type.F03 \
  knapsack_nwKnapsack_type.F03 knapsack_kBadWeightExcept_type.F03 \
  knapsack_kExcept_type.F03 knapsack_kSizeExcept_type.F03

_deps_knapsack_gKnapsack =  knapsack_kSizeExcept_type \
  knapsack_kBadWeightExcept_type knapsack_gKnapsack_type 
knapsack_gKnapsack$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_knapsack_gKnapsack))

_deps_knapsack_gKnapsack_Impl =  knapsack_gKnapsack \
  knapsack_kBadWeightExcept knapsack_kSizeExcept
knapsack_gKnapsack_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_knapsack_gKnapsack_Impl))

_deps_knapsack_gKnapsack_fSkelf =  knapsack_gKnapsack_Impl \
  knapsack_gKnapsack_type \
  knapsack_kBadWeightExcept_type knapsack_kSizeExcept_type
knapsack_gKnapsack_fSkelf$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_knapsack_gKnapsack_fSkelf))

_deps_knapsack_npKnapsack =  knapsack_kSizeExcept_type \
  knapsack_kBadWeightExcept_type        \
  knapsack_npKnapsack_type  knapsack_gKnapsack_type
knapsack_npKnapsack$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_knapsack_npKnapsack))

_deps_knapsack_npKnapsack_Impl =  knapsack_gKnapsack knapsack_npKnapsack\
  knapsack_kBadWeightExcept knapsack_kSizeExcept
knapsack_npKnapsack_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_knapsack_npKnapsack_Impl))

_deps_knapsack_npKnapsack_fSkelf =  knapsack_npKnapsack_Impl \
  knapsack_npKnapsack_type knapsack_gKnapsack_Impl knapsack_npKnapsack_type \
  knapsack_kBadWeightExcept_type knapsack_kSizeExcept_type
knapsack_npKnapsack_fSkelf$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_knapsack_npKnapsack_fSkelf))

_deps_knapsack_nwKnapsack_Impl =  knapsack_gKnapsack \
  knapsack_nwKnapsack knapsack_kBadWeightExcept knapsack_kSizeExcept
knapsack_nwKnapsack_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_knapsack_nwKnapsack_Impl))

_deps_knapsack_nwKnapsack_fSkelf =  knapsack_nwKnapsack_Impl \
  knapsack_nwKnapsack_type knapsack_gKnapsack_Impl knapsack_gKnapsack_type \
  knapsack_kBadWeightExcept_type knapsack_kSizeExcept_type
knapsack_nwKnapsack_fSkelf$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_knapsack_nwKnapsack_fSkelf))

_deps_knapsack_kBadWeightExcept =  knapsack_kBadWeightExcept_type \
  knapsack_kExcept_type
knapsack_kBadWeightExcept$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_knapsack_kBadWeightExcept))

_deps_knapsack_kBadWeightExcept_Impl =  knapsack_kBadWeightExcept
knapsack_kBadWeightExcept_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_knapsack_kBadWeightExcept_Impl))

_deps_knapsack_kBadWeightExcept_fSkelf =  knapsack_kBadWeightExcept_Impl \
  knapsack_kBadWeightExcept_type
knapsack_kBadWeightExcept_fSkelf$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_knapsack_kBadWeightExcept_fSkelf))

_deps_knapsack_kExcept =  knapsack_kExcept_type
knapsack_kExcept$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_knapsack_kExcept))

_deps_knapsack_kExcept_Impl =  knapsack_kExcept
knapsack_kExcept_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_knapsack_kExcept_Impl))

_deps_knapsack_kExcept_fSkelf =  knapsack_kExcept_Impl knapsack_kExcept_type
knapsack_kExcept_fSkelf$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_knapsack_kExcept_fSkelf))

_deps_knapsack_kSizeExcept =  knapsack_kSizeExcept_type knapsack_kExcept_type
knapsack_kSizeExcept$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_knapsack_kSizeExcept))

_deps_knapsack_kSizeExcept_Impl =  knapsack_kSizeExcept
knapsack_kSizeExcept_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_knapsack_kSizeExcept_Impl))

_deps_knapsack_kSizeExcept_fSkelf =  knapsack_kSizeExcept_Impl \
  knapsack_kSizeExcept_type
knapsack_kSizeExcept_fSkelf$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_knapsack_kSizeExcept_fSkelf))

