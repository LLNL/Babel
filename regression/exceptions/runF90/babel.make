IORHDRS = Exceptions_FibException_IOR.h Exceptions_Fib_IOR.h Exceptions_IOR.h \
  Exceptions_NegativeValueException_IOR.h Exceptions_TooBigException_IOR.h    \
  Exceptions_TooDeepException_IOR.h
STUBHDRS = Exceptions_FibException_array_bindcdecls.h                         \
  Exceptions_FibException_fAbbrev.h Exceptions_FibException_fStub.h           \
  Exceptions_FibException_mod_bindcdecls.h Exceptions_Fib_array_bindcdecls.h  \
  Exceptions_Fib_fAbbrev.h Exceptions_Fib_fStub.h                             \
  Exceptions_Fib_mod_bindcdecls.h                                             \
  Exceptions_NegativeValueException_array_bindcdecls.h                        \
  Exceptions_NegativeValueException_fAbbrev.h                                 \
  Exceptions_NegativeValueException_fStub.h                                   \
  Exceptions_NegativeValueException_mod_bindcdecls.h                          \
  Exceptions_TooBigException_array_bindcdecls.h                               \
  Exceptions_TooBigException_fAbbrev.h Exceptions_TooBigException_fStub.h     \
  Exceptions_TooBigException_mod_bindcdecls.h                                 \
  Exceptions_TooDeepException_array_bindcdecls.h                              \
  Exceptions_TooDeepException_fAbbrev.h Exceptions_TooDeepException_fStub.h   \
  Exceptions_TooDeepException_mod_bindcdecls.h
STUBMODULESRCS = Exceptions_Fib.F90 Exceptions_FibException.F90               \
  Exceptions_NegativeValueException.F90 Exceptions_TooBigException.F90        \
  Exceptions_TooDeepException.F90
STUBSRCS = Exceptions_FibException_fStub.c Exceptions_Fib_fStub.c             \
  Exceptions_NegativeValueException_fStub.c                                   \
  Exceptions_TooBigException_fStub.c Exceptions_TooDeepException_fStub.c
TYPEMODULESRCS = Exceptions_FibException_type.F90 Exceptions_Fib_type.F90     \
  Exceptions_NegativeValueException_type.F90                                  \
  Exceptions_TooBigException_type.F90 Exceptions_TooDeepException_type.F90

_deps_Exceptions_Fib =  Exceptions_Fib_type Exceptions_FibException_type      \
  Exceptions_NegativeValueException_type
Exceptions_Fib$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Exceptions_Fib))

_deps_Exceptions_FibException =  Exceptions_FibException_type
Exceptions_FibException$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Exceptions_FibException))

_deps_Exceptions_NegativeValueException =                                     \
  Exceptions_NegativeValueException_type
Exceptions_NegativeValueException$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Exceptions_NegativeValueException))

_deps_Exceptions_TooBigException =  Exceptions_FibException_type              \
  Exceptions_TooBigException_type
Exceptions_TooBigException$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Exceptions_TooBigException))

_deps_Exceptions_TooDeepException =  Exceptions_FibException_type             \
  Exceptions_TooDeepException_type
Exceptions_TooDeepException$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Exceptions_TooDeepException))

