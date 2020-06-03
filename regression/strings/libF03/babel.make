FSKELSRCS = Strings_Cstring_fSkelf.F03
IMPLSRCS = Strings_Cstring_Impl.F03
IORHDRS = Strings_Cstring_IOR.h Strings_IOR.h
IORSRCS = Strings_Cstring_IOR.c
SKELSRCS = Strings_Cstring_fSkel.c
STUBHDRS = Strings_Cstring_array_bindcdecls.h Strings_Cstring_fAbbrev.h       \
  Strings_Cstring_fStub.h Strings_Cstring_mod_bindcdecls.h
STUBMODULESRCS = Strings_Cstring.F03
STUBSRCS = Strings_Cstring_fStub.c
TYPEMODULESRCS = Strings_Cstring_type.F03

_deps_Strings_Cstring =  Strings_Cstring_type
Strings_Cstring$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Strings_Cstring))

_deps_Strings_Cstring_Impl =  Strings_Cstring
Strings_Cstring_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Strings_Cstring_Impl))

_deps_Strings_Cstring_fSkelf =  Strings_Cstring_Impl Strings_Cstring_type
Strings_Cstring_fSkelf$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Strings_Cstring_fSkelf))

