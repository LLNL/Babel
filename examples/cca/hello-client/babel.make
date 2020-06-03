IMPLHDRS = HelloClient_Component_Impl.h
IMPLSRCS = HelloClient_Component_Impl.c
IORHDRS = HelloClient_Component_IOR.h HelloClient_IOR.h                       \
  gov_cca_AbstractFramework_IOR.h gov_cca_CCAExceptionType_IOR.h              \
  gov_cca_CCAException_IOR.h gov_cca_ComponentClassDescription_IOR.h          \
  gov_cca_ComponentID_IOR.h gov_cca_ComponentRelease_IOR.h                    \
  gov_cca_Component_IOR.h gov_cca_ConnectionID_IOR.h gov_cca_IOR.h            \
  gov_cca_Port_IOR.h gov_cca_Services_IOR.h gov_cca_TypeMap_IOR.h             \
  gov_cca_TypeMismatchException_IOR.h gov_cca_Type_IOR.h                      \
  gov_cca_ports_BasicParameterPort_IOR.h gov_cca_ports_BuilderService_IOR.h   \
  gov_cca_ports_ComponentRepository_IOR.h                                     \
  gov_cca_ports_ConnectionEventListener_IOR.h                                 \
  gov_cca_ports_ConnectionEventService_IOR.h                                  \
  gov_cca_ports_ConnectionEvent_IOR.h gov_cca_ports_EventType_IOR.h           \
  gov_cca_ports_GoPort_IOR.h gov_cca_ports_IOR.h                              \
  gov_cca_ports_ParameterGetListener_IOR.h                                    \
  gov_cca_ports_ParameterPortFactory_IOR.h gov_cca_ports_ParameterPort_IOR.h  \
  gov_cca_ports_ParameterSetListener_IOR.h                                    \
  gov_cca_ports_ServiceProvider_IOR.h gov_cca_ports_ServiceRegistry_IOR.h     \
  strop_StringProducerPort_IOR.h
IORSRCS = HelloClient_Component_IOR.c
SKELSRCS = HelloClient_Component_Skel.c
STUBHDRS = HelloClient.h HelloClient_Component.h gov_cca.h                    \
  gov_cca_AbstractFramework.h gov_cca_CCAException.h                          \
  gov_cca_CCAExceptionType.h gov_cca_Component.h                              \
  gov_cca_ComponentClassDescription.h gov_cca_ComponentID.h                   \
  gov_cca_ComponentRelease.h gov_cca_ConnectionID.h gov_cca_Port.h            \
  gov_cca_Services.h gov_cca_Type.h gov_cca_TypeMap.h                         \
  gov_cca_TypeMismatchException.h gov_cca_ports.h                             \
  gov_cca_ports_BasicParameterPort.h gov_cca_ports_BuilderService.h           \
  gov_cca_ports_ComponentRepository.h gov_cca_ports_ConnectionEvent.h         \
  gov_cca_ports_ConnectionEventListener.h                                     \
  gov_cca_ports_ConnectionEventService.h gov_cca_ports_EventType.h            \
  gov_cca_ports_GoPort.h gov_cca_ports_ParameterGetListener.h                 \
  gov_cca_ports_ParameterPort.h gov_cca_ports_ParameterPortFactory.h          \
  gov_cca_ports_ParameterSetListener.h gov_cca_ports_ServiceProvider.h        \
  gov_cca_ports_ServiceRegistry.h strop_StringProducerPort.h
STUBSRCS = HelloClient_Component_Stub.c gov_cca_AbstractFramework_Stub.c      \
  gov_cca_CCAExceptionType_Stub.c gov_cca_CCAException_Stub.c                 \
  gov_cca_ComponentClassDescription_Stub.c gov_cca_ComponentID_Stub.c         \
  gov_cca_ComponentRelease_Stub.c gov_cca_Component_Stub.c                    \
  gov_cca_ConnectionID_Stub.c gov_cca_Port_Stub.c gov_cca_Services_Stub.c     \
  gov_cca_TypeMap_Stub.c gov_cca_TypeMismatchException_Stub.c                 \
  gov_cca_Type_Stub.c gov_cca_ports_BasicParameterPort_Stub.c                 \
  gov_cca_ports_BuilderService_Stub.c                                         \
  gov_cca_ports_ComponentRepository_Stub.c                                    \
  gov_cca_ports_ConnectionEventListener_Stub.c                                \
  gov_cca_ports_ConnectionEventService_Stub.c                                 \
  gov_cca_ports_ConnectionEvent_Stub.c gov_cca_ports_EventType_Stub.c         \
  gov_cca_ports_GoPort_Stub.c gov_cca_ports_ParameterGetListener_Stub.c       \
  gov_cca_ports_ParameterPortFactory_Stub.c                                   \
  gov_cca_ports_ParameterPort_Stub.c                                          \
  gov_cca_ports_ParameterSetListener_Stub.c                                   \
  gov_cca_ports_ServiceProvider_Stub.c gov_cca_ports_ServiceRegistry_Stub.c   \
  strop_StringProducerPort_Stub.c
