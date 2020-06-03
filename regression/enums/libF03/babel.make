FSKELSRCS = enums_cartest_fSkelf.F03 enums_colorwheel_fSkelf.F03              \
  enums_numbertest_fSkelf.F03
IMPLSRCS = enums_cartest_Impl.F03 enums_colorwheel_Impl.F03                   \
  enums_numbertest_Impl.F03
IORHDRS = enums_IOR.h enums_car_IOR.h enums_cartest_IOR.h enums_color_IOR.h   \
  enums_colorwheel_IOR.h enums_number_IOR.h enums_numbertest_IOR.h
IORSRCS = enums_cartest_IOR.c enums_colorwheel_IOR.c enums_numbertest_IOR.c
SKELSRCS = enums_cartest_fSkel.c enums_colorwheel_fSkel.c                     \
  enums_numbertest_fSkel.c
STUBHDRS = enums_car_array_bindcdecls.h enums_car_fAbbrev.h                   \
  enums_cartest_array_bindcdecls.h enums_cartest_fAbbrev.h                    \
  enums_cartest_fStub.h enums_cartest_mod_bindcdecls.h                        \
  enums_color_array_bindcdecls.h enums_color_fAbbrev.h                        \
  enums_colorwheel_array_bindcdecls.h enums_colorwheel_fAbbrev.h              \
  enums_colorwheel_fStub.h enums_colorwheel_mod_bindcdecls.h                  \
  enums_number_array_bindcdecls.h enums_number_fAbbrev.h                      \
  enums_numbertest_array_bindcdecls.h enums_numbertest_fAbbrev.h              \
  enums_numbertest_fStub.h enums_numbertest_mod_bindcdecls.h
STUBMODULESRCS = enums_cartest.F03 enums_colorwheel.F03 enums_numbertest.F03
STUBSRCS = enums_cartest_fStub.c enums_colorwheel_fStub.c                     \
  enums_numbertest_fStub.c
TYPEMODULESRCS = enums_car_type.F03 enums_cartest_type.F03                    \
  enums_color_type.F03 enums_colorwheel_type.F03 enums_number_type.F03        \
  enums_numbertest_type.F03

_deps_enums_cartest =  enums_car_type enums_cartest_type
enums_cartest$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_enums_cartest))

_deps_enums_cartest_Impl =  enums_cartest
enums_cartest_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_enums_cartest_Impl))

_deps_enums_cartest_fSkelf =  enums_cartest_Impl enums_car_type               \
  enums_cartest_type
enums_cartest_fSkelf$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_enums_cartest_fSkelf))

_deps_enums_colorwheel =  enums_color_type enums_colorwheel_type
enums_colorwheel$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_enums_colorwheel))

_deps_enums_colorwheel_Impl =  enums_colorwheel
enums_colorwheel_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_enums_colorwheel_Impl))

_deps_enums_colorwheel_fSkelf =  enums_colorwheel_Impl enums_color_type       \
  enums_colorwheel_type
enums_colorwheel_fSkelf$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_enums_colorwheel_fSkelf))

_deps_enums_numbertest =  enums_numbertest_type enums_number_type
enums_numbertest$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_enums_numbertest))

_deps_enums_numbertest_Impl =  enums_numbertest
enums_numbertest_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_enums_numbertest_Impl))

_deps_enums_numbertest_fSkelf =  enums_numbertest_Impl enums_number_type      \
  enums_numbertest_type
enums_numbertest_fSkelf$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_enums_numbertest_fSkelf))

