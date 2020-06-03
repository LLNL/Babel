IMPLSRCS = HelloServer_Component_Impl.f
IORHDRS = HelloServer_Component_IOR.h HelloServer_IOR.h                       \
  gov_cca_CCAExceptionType_IOR.h gov_cca_CCAException_IOR.h                   \
  gov_cca_ComponentID_IOR.h gov_cca_ComponentRelease_IOR.h                    \
  gov_cca_Component_IOR.h gov_cca_Port_IOR.h gov_cca_Services_IOR.h           \
  gov_cca_TypeMap_IOR.h gov_cca_TypeMismatchException_IOR.h                   \
  gov_cca_Type_IOR.h strop_StringProducerPort_IOR.h
IORSRCS = HelloServer_Component_IOR.c
SKELSRCS = HelloServer_Component_fSkel.c
STUBDOCS = HelloServer_Component.fif gov_cca_CCAException.fif                 \
  gov_cca_Component.fif gov_cca_ComponentID.fif gov_cca_ComponentRelease.fif  \
  gov_cca_Port.fif gov_cca_Services.fif gov_cca_TypeMap.fif                   \
  gov_cca_TypeMismatchException.fif strop_StringProducerPort.fif
STUBFORTRANINC = gov_cca_CCAExceptionType.inc gov_cca_Type.inc
STUBHDRS = HelloServer_Component_fStub.h gov_cca_CCAException_fStub.h         \
  gov_cca_ComponentID_fStub.h gov_cca_ComponentRelease_fStub.h                \
  gov_cca_Component_fStub.h gov_cca_Port_fStub.h gov_cca_Services_fStub.h     \
  gov_cca_TypeMap_fStub.h gov_cca_TypeMismatchException_fStub.h               \
  strop_StringProducerPort_fStub.h
STUBSRCS = HelloServer_Component_fStub.c gov_cca_CCAExceptionType_fStub.c     \
  gov_cca_CCAException_fStub.c gov_cca_ComponentID_fStub.c                    \
  gov_cca_ComponentRelease_fStub.c gov_cca_Component_fStub.c                  \
  gov_cca_Port_fStub.c gov_cca_Services_fStub.c gov_cca_TypeMap_fStub.c       \
  gov_cca_TypeMismatchException_fStub.c gov_cca_Type_fStub.c                  \
  strop_StringProducerPort_fStub.c

