IORHDRS = Args_Basic_IOR.h Args_IOR.h
STUBHDRS = Args_Basic_array_bindcdecls.h Args_Basic_fAbbrev.h                 \
  Args_Basic_fStub.h Args_Basic_mod_bindcdecls.h
STUBMODULESRCS = Args_Basic.F90
STUBSRCS = Args_Basic_fStub.c
TYPEMODULESRCS = Args_Basic_type.F90

_deps_Args_Basic =  Args_Basic_type
Args_Basic$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Args_Basic))

