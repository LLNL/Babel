IMPLMODULESRCS = Args_Basic_Mod.F90
IMPLSRCS = Args_Basic_Impl.F90
IORHDRS = Args_Basic_IOR.h Args_IOR.h
IORSRCS = Args_Basic_IOR.c
SKELSRCS = Args_Basic_fSkel.c
STUBHDRS = Args_Basic_array_bindcdecls.h Args_Basic_fAbbrev.h                 \
  Args_Basic_fStub.h Args_Basic_mod_bindcdecls.h
STUBMODULESRCS = Args_Basic.F90
STUBSRCS = Args_Basic_fStub.c
TYPEMODULESRCS = Args_Basic_type.F90

_deps_Args_Basic =  Args_Basic_type
Args_Basic$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Args_Basic))

_deps_Args_Basic_Impl =  Args_Basic
Args_Basic_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Args_Basic_Impl))

