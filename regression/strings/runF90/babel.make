IORHDRS = Strings_Cstring_IOR.h Strings_IOR.h
STUBHDRS = Strings_Cstring_array_bindcdecls.h Strings_Cstring_fAbbrev.h       \
  Strings_Cstring_fStub.h Strings_Cstring_mod_bindcdecls.h
STUBMODULESRCS = Strings_Cstring.F90
STUBSRCS = Strings_Cstring_fStub.c
TYPEMODULESRCS = Strings_Cstring_type.F90

_deps_Strings_Cstring =  Strings_Cstring_type
Strings_Cstring$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Strings_Cstring))

