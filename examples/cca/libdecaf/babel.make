IMPLHDRS = decaf_CCAException_Impl.hxx                                        \
  decaf_ComponentClassDescription_Impl.hxx decaf_ComponentID_Impl.hxx         \
  decaf_ConnectionID_Impl.hxx decaf_Framework_Impl.hxx                        \
  decaf_Services_Impl.hxx decaf_TypeMap_Impl.hxx                              \
  decaf_TypeMismatchException_Impl.hxx                                        \
  decaf_ports_ComponentRepository_Impl.hxx                                    \
  decaf_ports_ConnectionEvent_Impl.hxx decaf_ports_ParameterPort_Impl.hxx
IMPLSRCS = decaf_CCAException_Impl.cxx                                        \
  decaf_ComponentClassDescription_Impl.cxx decaf_ComponentID_Impl.cxx         \
  decaf_ConnectionID_Impl.cxx decaf_Framework_Impl.cxx                        \
  decaf_Services_Impl.cxx decaf_TypeMap_Impl.cxx                              \
  decaf_TypeMismatchException_Impl.cxx                                        \
  decaf_ports_ComponentRepository_Impl.cxx                                    \
  decaf_ports_ConnectionEvent_Impl.cxx decaf_ports_ParameterPort_Impl.cxx
IORHDRS = decaf_CCAException_IOR.h decaf_ComponentClassDescription_IOR.h      \
  decaf_ComponentID_IOR.h decaf_ConnectionID_IOR.h decaf_Framework_IOR.h      \
  decaf_IOR.h decaf_Services_IOR.h decaf_TypeMap_IOR.h                        \
  decaf_TypeMismatchException_IOR.h decaf_Type_IOR.h                          \
  decaf_ports_ComponentRepository_IOR.h decaf_ports_ConnectionEvent_IOR.h     \
  decaf_ports_IOR.h decaf_ports_ParameterPort_IOR.h                           \
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
  gov_cca_ports_ServiceProvider_IOR.h gov_cca_ports_ServiceRegistry_IOR.h
IORSRCS = decaf_CCAException_IOR.c decaf_ComponentClassDescription_IOR.c      \
  decaf_ComponentID_IOR.c decaf_ConnectionID_IOR.c decaf_Framework_IOR.c      \
  decaf_Services_IOR.c decaf_TypeMap_IOR.c decaf_TypeMismatchException_IOR.c  \
  decaf_ports_ComponentRepository_IOR.c decaf_ports_ConnectionEvent_IOR.c     \
  decaf_ports_ParameterPort_IOR.c
SKELSRCS = decaf_CCAException_Skel.cxx                                        \
  decaf_ComponentClassDescription_Skel.cxx decaf_ComponentID_Skel.cxx         \
  decaf_ConnectionID_Skel.cxx decaf_Framework_Skel.cxx                        \
  decaf_Services_Skel.cxx decaf_TypeMap_Skel.cxx                              \
  decaf_TypeMismatchException_Skel.cxx                                        \
  decaf_ports_ComponentRepository_Skel.cxx                                    \
  decaf_ports_ConnectionEvent_Skel.cxx decaf_ports_ParameterPort_Skel.cxx
STUBHDRS = decaf.hxx decaf_CCAException.hxx                                   \
  decaf_ComponentClassDescription.hxx decaf_ComponentID.hxx                   \
  decaf_ConnectionID.hxx decaf_Framework.hxx decaf_Services.hxx               \
  decaf_Type.hxx decaf_TypeMap.hxx decaf_TypeMismatchException.hxx            \
  decaf_ports.hxx decaf_ports_ComponentRepository.hxx                         \
  decaf_ports_ConnectionEvent.hxx decaf_ports_ParameterPort.hxx gov_cca.hxx   \
  gov_cca_AbstractFramework.hxx gov_cca_CCAException.hxx                      \
  gov_cca_CCAExceptionType.hxx gov_cca_Component.hxx                          \
  gov_cca_ComponentClassDescription.hxx gov_cca_ComponentID.hxx               \
  gov_cca_ComponentRelease.hxx gov_cca_ConnectionID.hxx gov_cca_Port.hxx      \
  gov_cca_Services.hxx gov_cca_Type.hxx gov_cca_TypeMap.hxx                   \
  gov_cca_TypeMismatchException.hxx gov_cca_ports.hxx                         \
  gov_cca_ports_BasicParameterPort.hxx gov_cca_ports_BuilderService.hxx       \
  gov_cca_ports_ComponentRepository.hxx gov_cca_ports_ConnectionEvent.hxx     \
  gov_cca_ports_ConnectionEventListener.hxx                                   \
  gov_cca_ports_ConnectionEventService.hxx gov_cca_ports_EventType.hxx        \
  gov_cca_ports_GoPort.hxx gov_cca_ports_ParameterGetListener.hxx             \
  gov_cca_ports_ParameterPort.hxx gov_cca_ports_ParameterPortFactory.hxx      \
  gov_cca_ports_ParameterSetListener.hxx gov_cca_ports_ServiceProvider.hxx    \
  gov_cca_ports_ServiceRegistry.hxx
STUBSRCS = decaf_CCAException.cxx decaf_ComponentClassDescription.cxx         \
  decaf_ComponentID.cxx decaf_ConnectionID.cxx decaf_Framework.cxx            \
  decaf_Services.cxx decaf_TypeMap.cxx decaf_TypeMismatchException.cxx        \
  decaf_ports_ComponentRepository.cxx decaf_ports_ConnectionEvent.cxx         \
  decaf_ports_ParameterPort.cxx gov_cca_AbstractFramework.cxx                 \
  gov_cca_CCAException.cxx gov_cca_Component.cxx                              \
  gov_cca_ComponentClassDescription.cxx gov_cca_ComponentID.cxx               \
  gov_cca_ComponentRelease.cxx gov_cca_ConnectionID.cxx gov_cca_Port.cxx      \
  gov_cca_Services.cxx gov_cca_TypeMap.cxx gov_cca_TypeMismatchException.cxx  \
  gov_cca_ports_BasicParameterPort.cxx gov_cca_ports_BuilderService.cxx       \
  gov_cca_ports_ComponentRepository.cxx gov_cca_ports_ConnectionEvent.cxx     \
  gov_cca_ports_ConnectionEventListener.cxx                                   \
  gov_cca_ports_ConnectionEventService.cxx gov_cca_ports_GoPort.cxx           \
  gov_cca_ports_ParameterGetListener.cxx gov_cca_ports_ParameterPort.cxx      \
  gov_cca_ports_ParameterPortFactory.cxx                                      \
  gov_cca_ports_ParameterSetListener.cxx gov_cca_ports_ServiceProvider.cxx    \
  gov_cca_ports_ServiceRegistry.cxx
