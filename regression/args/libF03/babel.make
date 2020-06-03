FSKELSRCS = Args_Basic_fSkelf.F03
IMPLSRCS = Args_Basic_Impl.F03
IORHDRS = Args_Basic_IOR.h Args_IOR.h
IORSRCS = Args_Basic_IOR.c
SKELSRCS = Args_Basic_fSkel.c
STUBHDRS = Args_Basic_array_bindcdecls.h Args_Basic_fAbbrev.h                 \
  Args_Basic_fStub.h Args_Basic_mod_bindcdecls.h
STUBMODULESRCS = Args_Basic.F03
STUBSRCS = Args_Basic_fStub.c
TYPEMODULESRCS = Args_Basic_type.F03

_deps_Args_Basic =  Args_Basic_type
Args_Basic$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Args_Basic))

_deps_Args_Basic_Impl =  Args_Basic
Args_Basic_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Args_Basic_Impl))

_deps_Args_Basic_fSkelf =  Args_Basic_Impl Args_Basic_type
Args_Basic_fSkelf$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Args_Basic_fSkelf))

