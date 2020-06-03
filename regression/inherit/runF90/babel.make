IORHDRS = Inherit_A2_IOR.h Inherit_A_IOR.h Inherit_B_IOR.h Inherit_C_IOR.h    \
  Inherit_D_IOR.h Inherit_E2_IOR.h Inherit_E_IOR.h Inherit_F2_IOR.h           \
  Inherit_F_IOR.h Inherit_G2_IOR.h Inherit_G_IOR.h Inherit_H_IOR.h            \
  Inherit_IOR.h Inherit_I_IOR.h Inherit_J_IOR.h Inherit_K_IOR.h               \
  Inherit_L_IOR.h
STUBHDRS = Inherit_A2_array_bindcdecls.h Inherit_A2_fAbbrev.h                 \
  Inherit_A2_fStub.h Inherit_A2_mod_bindcdecls.h Inherit_A_array_bindcdecls.h \
  Inherit_A_fAbbrev.h Inherit_A_fStub.h Inherit_A_mod_bindcdecls.h            \
  Inherit_B_array_bindcdecls.h Inherit_B_fAbbrev.h Inherit_B_fStub.h          \
  Inherit_B_mod_bindcdecls.h Inherit_C_array_bindcdecls.h Inherit_C_fAbbrev.h \
  Inherit_C_fStub.h Inherit_C_mod_bindcdecls.h Inherit_D_array_bindcdecls.h   \
  Inherit_D_fAbbrev.h Inherit_D_fStub.h Inherit_D_mod_bindcdecls.h            \
  Inherit_E2_array_bindcdecls.h Inherit_E2_fAbbrev.h Inherit_E2_fStub.h       \
  Inherit_E2_mod_bindcdecls.h Inherit_E_array_bindcdecls.h                    \
  Inherit_E_fAbbrev.h Inherit_E_fStub.h Inherit_E_mod_bindcdecls.h            \
  Inherit_F2_array_bindcdecls.h Inherit_F2_fAbbrev.h Inherit_F2_fStub.h       \
  Inherit_F2_mod_bindcdecls.h Inherit_F_array_bindcdecls.h                    \
  Inherit_F_fAbbrev.h Inherit_F_fStub.h Inherit_F_mod_bindcdecls.h            \
  Inherit_G2_array_bindcdecls.h Inherit_G2_fAbbrev.h Inherit_G2_fStub.h       \
  Inherit_G2_mod_bindcdecls.h Inherit_G_array_bindcdecls.h                    \
  Inherit_G_fAbbrev.h Inherit_G_fStub.h Inherit_G_mod_bindcdecls.h            \
  Inherit_H_array_bindcdecls.h Inherit_H_fAbbrev.h Inherit_H_fStub.h          \
  Inherit_H_mod_bindcdecls.h Inherit_I_array_bindcdecls.h Inherit_I_fAbbrev.h \
  Inherit_I_fStub.h Inherit_I_mod_bindcdecls.h Inherit_J_array_bindcdecls.h   \
  Inherit_J_fAbbrev.h Inherit_J_fStub.h Inherit_J_mod_bindcdecls.h            \
  Inherit_K_array_bindcdecls.h Inherit_K_fAbbrev.h Inherit_K_fStub.h          \
  Inherit_K_mod_bindcdecls.h Inherit_L_array_bindcdecls.h Inherit_L_fAbbrev.h \
  Inherit_L_fStub.h Inherit_L_mod_bindcdecls.h
STUBMODULESRCS = Inherit_A.F90 Inherit_A2.F90 Inherit_B.F90 Inherit_C.F90     \
  Inherit_D.F90 Inherit_E.F90 Inherit_E2.F90 Inherit_F.F90 Inherit_F2.F90     \
  Inherit_G.F90 Inherit_G2.F90 Inherit_H.F90 Inherit_I.F90 Inherit_J.F90      \
  Inherit_K.F90 Inherit_L.F90
STUBSRCS = Inherit_A2_fStub.c Inherit_A_fStub.c Inherit_B_fStub.c             \
  Inherit_C_fStub.c Inherit_D_fStub.c Inherit_E2_fStub.c Inherit_E_fStub.c    \
  Inherit_F2_fStub.c Inherit_F_fStub.c Inherit_G2_fStub.c Inherit_G_fStub.c   \
  Inherit_H_fStub.c Inherit_I_fStub.c Inherit_J_fStub.c Inherit_K_fStub.c     \
  Inherit_L_fStub.c
TYPEMODULESRCS = Inherit_A2_type.F90 Inherit_A_type.F90 Inherit_B_type.F90    \
  Inherit_C_type.F90 Inherit_D_type.F90 Inherit_E2_type.F90                   \
  Inherit_E_type.F90 Inherit_F2_type.F90 Inherit_F_type.F90                   \
  Inherit_G2_type.F90 Inherit_G_type.F90 Inherit_H_type.F90                   \
  Inherit_I_type.F90 Inherit_J_type.F90 Inherit_K_type.F90 Inherit_L_type.F90

_deps_Inherit_A =  Inherit_A_type
Inherit_A$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Inherit_A))

_deps_Inherit_A2 =  Inherit_A2_type
Inherit_A2$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Inherit_A2))

_deps_Inherit_B =  Inherit_B_type
Inherit_B$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Inherit_B))

_deps_Inherit_C =  Inherit_C_type
Inherit_C$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Inherit_C))

_deps_Inherit_D =  Inherit_D_type Inherit_A_type
Inherit_D$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Inherit_D))

_deps_Inherit_E =  Inherit_E_type Inherit_C_type
Inherit_E$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Inherit_E))

_deps_Inherit_E2 =  Inherit_C_type Inherit_E2_type
Inherit_E2$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Inherit_E2))

_deps_Inherit_F =  Inherit_F_type Inherit_C_type Inherit_A_type Inherit_B_type
Inherit_F$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Inherit_F))

_deps_Inherit_F2 =  Inherit_C_type Inherit_F2_type Inherit_A_type             \
  Inherit_B_type
Inherit_F2$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Inherit_F2))

_deps_Inherit_G =  Inherit_D_type Inherit_G_type Inherit_A_type
Inherit_G$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Inherit_G))

_deps_Inherit_G2 =  Inherit_D_type Inherit_G2_type Inherit_A_type
Inherit_G2$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Inherit_G2))

_deps_Inherit_H =  Inherit_H_type Inherit_A_type
Inherit_H$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Inherit_H))

_deps_Inherit_I =  Inherit_I_type Inherit_H_type Inherit_A_type
Inherit_I$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Inherit_I))

_deps_Inherit_J =  Inherit_C_type Inherit_J_type Inherit_E2_type              \
  Inherit_A_type Inherit_B_type
Inherit_J$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Inherit_J))

_deps_Inherit_K =  Inherit_H_type Inherit_A2_type Inherit_K_type              \
  Inherit_A_type
Inherit_K$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Inherit_K))

_deps_Inherit_L =  Inherit_A2_type Inherit_L_type Inherit_A_type
Inherit_L$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_Inherit_L))

