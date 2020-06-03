IMPLMODULESRCS = f90_ScalarField_Mod.F90 f90_WavePropagator_Mod.F90
IMPLSRCS = f90_ScalarField_Impl.F90 f90_WavePropagator_Impl.F90
IORHDRS = f90_IOR.h f90_ScalarField_IOR.h f90_WavePropagator_IOR.h            \
  wave2d_IOR.h wave2d_ScalarField_IOR.h wave2d_Shape_IOR.h                    \
  wave2d_WavePropagator_IOR.h
IORSRCS = f90_ScalarField_IOR.c f90_WavePropagator_IOR.c
SKELSRCS = f90_ScalarField_fSkel.c f90_WavePropagator_fSkel.c
STUBHDRS = f90_ScalarField_fAbbrev.h f90_ScalarField_fStub.h                  \
  f90_WavePropagator_fAbbrev.h f90_WavePropagator_fStub.h                     \
  wave2d_ScalarField_fAbbrev.h wave2d_ScalarField_fStub.h                     \
  wave2d_Shape_fAbbrev.h wave2d_Shape_fStub.h wave2d_WavePropagator_fAbbrev.h \
  wave2d_WavePropagator_fStub.h
STUBMODULESRCS = f90_ScalarField.F90 f90_WavePropagator.F90                   \
  wave2d_ScalarField.F90 wave2d_Shape.F90 wave2d_WavePropagator.F90
STUBSRCS = f90_ScalarField_fStub.c f90_WavePropagator_fStub.c                 \
  wave2d_ScalarField_fStub.c wave2d_Shape_fStub.c                             \
  wave2d_WavePropagator_fStub.c
TYPEMODULESRCS = f90_ScalarField_type.F90 f90_WavePropagator_type.F90         \
  wave2d_ScalarField_type.F90 wave2d_Shape_type.F90                           \
  wave2d_WavePropagator_type.F90
