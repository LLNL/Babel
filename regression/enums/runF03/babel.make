IORHDRS = enums_IOR.h enums_car_IOR.h enums_cartest_IOR.h enums_color_IOR.h   \
  enums_colorwheel_IOR.h enums_number_IOR.h enums_numbertest_IOR.h
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

_deps_enums_colorwheel =  enums_color_type enums_colorwheel_type
enums_colorwheel$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_enums_colorwheel))

_deps_enums_numbertest =  enums_numbertest_type enums_number_type
enums_numbertest$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_enums_numbertest))

