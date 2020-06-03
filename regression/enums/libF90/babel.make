IMPLMODULESRCS = enums_cartest_Mod.F90 enums_colorwheel_Mod.F90               \
  enums_numbertest_Mod.F90
IMPLSRCS = enums_cartest_Impl.F90 enums_colorwheel_Impl.F90                   \
  enums_numbertest_Impl.F90
IORHDRS = enums_IOR.h enums_car_IOR.h enums_cartest_IOR.h enums_color_IOR.h   \
  enums_colorwheel_IOR.h enums_number_IOR.h enums_numbertest_IOR.h
IORSRCS = enums_cartest_IOR.c enums_colorwheel_IOR.c enums_numbertest_IOR.c
SKELSRCS = enums_cartest_fSkel.c enums_colorwheel_fSkel.c                     \
  enums_numbertest_fSkel.c
STUBHDRS = enums_car_array_bindcdecls.h enums_car_fAbbrev.h                   \
  enums_car_mod_bindcdecls.h enums_cartest_array_bindcdecls.h                 \
  enums_cartest_fAbbrev.h enums_cartest_fStub.h                               \
  enums_cartest_mod_bindcdecls.h enums_color_array_bindcdecls.h               \
  enums_color_fAbbrev.h enums_color_mod_bindcdecls.h                          \
  enums_colorwheel_array_bindcdecls.h enums_colorwheel_fAbbrev.h              \
  enums_colorwheel_fStub.h enums_colorwheel_mod_bindcdecls.h                  \
  enums_number_array_bindcdecls.h enums_number_fAbbrev.h                      \
  enums_number_mod_bindcdecls.h enums_numbertest_array_bindcdecls.h           \
  enums_numbertest_fAbbrev.h enums_numbertest_fStub.h                         \
  enums_numbertest_mod_bindcdecls.h
STUBMODULESRCS = enums_car.F90 enums_cartest.F90 enums_color.F90              \
  enums_colorwheel.F90 enums_number.F90 enums_numbertest.F90
STUBSRCS = enums_car_fStub.c enums_cartest_fStub.c enums_color_fStub.c        \
  enums_colorwheel_fStub.c enums_number_fStub.c enums_numbertest_fStub.c
TYPEMODULESRCS = enums_car_type.F90 enums_cartest_type.F90                    \
  enums_color_type.F90 enums_colorwheel_type.F90 enums_number_type.F90        \
  enums_numbertest_type.F90

_deps_enums_car =  enums_car_type
enums_car$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_enums_car))

_deps_enums_cartest =  enums_car_type enums_cartest_type
enums_cartest$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_enums_cartest))

_deps_enums_cartest_Impl =  enums_cartest enums_car
enums_cartest_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_enums_cartest_Impl))

_deps_enums_color =  enums_color_type
enums_color$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_enums_color))

_deps_enums_colorwheel =  enums_color_type enums_colorwheel_type
enums_colorwheel$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_enums_colorwheel))

_deps_enums_colorwheel_Impl =  enums_colorwheel enums_color
enums_colorwheel_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_enums_colorwheel_Impl))

_deps_enums_number =  enums_number_type
enums_number$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_enums_number))

_deps_enums_numbertest =  enums_numbertest_type enums_number_type
enums_numbertest$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_enums_numbertest))

_deps_enums_numbertest_Impl =  enums_numbertest enums_number
enums_numbertest_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_enums_numbertest_Impl))

