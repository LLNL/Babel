IORHDRS = hooks_Basics_IOR.h hooks_IOR.h
STUBHDRS = hooks_Basics_array_bindcdecls.h hooks_Basics_fAbbrev.h             \
  hooks_Basics_fStub.h hooks_Basics_mod_bindcdecls.h
STUBMODULESRCS = hooks_Basics.F03
STUBSRCS = hooks_Basics_fStub.c
TYPEMODULESRCS = hooks_Basics_type.F03

_deps_hooks_Basics =  hooks_Basics_type
hooks_Basics$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_hooks_Basics))

