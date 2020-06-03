ARRAYMODULESRCS = sidl_array_array.F90 sidl_bool_array.F90                    \
  sidl_char_array.F90 sidl_dcomplex_array.F90 sidl_double_array.F90           \
  sidl_fcomplex_array.F90 sidl_float_array.F90 sidl_int_array.F90             \
  sidl_long_array.F90 sidl_opaque_array.F90 sidl_string_array.F90
BASICMODULESRC = sidl.F90
STUBHDRS = sidl_BaseClass_array_bindcdecls.h sidl_BaseClass_fAbbrev.h         \
  sidl_BaseClass_fStub.h sidl_BaseClass_mod_bindcdecls.h                      \
  sidl_BaseException_array_bindcdecls.h sidl_BaseException_fAbbrev.h          \
  sidl_BaseException_fStub.h sidl_BaseException_mod_bindcdecls.h              \
  sidl_BaseInterface_array_bindcdecls.h sidl_BaseInterface_fAbbrev.h          \
  sidl_BaseInterface_fStub.h sidl_BaseInterface_mod_bindcdecls.h              \
  sidl_CastException_array_bindcdecls.h sidl_CastException_fAbbrev.h          \
  sidl_CastException_fStub.h sidl_CastException_mod_bindcdecls.h              \
  sidl_ClassInfoI_array_bindcdecls.h sidl_ClassInfoI_fAbbrev.h                \
  sidl_ClassInfoI_fStub.h sidl_ClassInfoI_mod_bindcdecls.h                    \
  sidl_ClassInfo_array_bindcdecls.h sidl_ClassInfo_fAbbrev.h                  \
  sidl_ClassInfo_fStub.h sidl_ClassInfo_mod_bindcdecls.h                      \
  sidl_ClauseType_array_bindcdecls.h sidl_ClauseType_fAbbrev.h                \
  sidl_ClauseType_mod_bindcdecls.h sidl_ContractClass_array_bindcdecls.h      \
  sidl_ContractClass_fAbbrev.h sidl_ContractClass_mod_bindcdecls.h            \
  sidl_DFinder_array_bindcdecls.h sidl_DFinder_fAbbrev.h sidl_DFinder_fStub.h \
  sidl_DFinder_mod_bindcdecls.h sidl_DLL_array_bindcdecls.h                   \
  sidl_DLL_fAbbrev.h sidl_DLL_fStub.h sidl_DLL_mod_bindcdecls.h               \
  sidl_EnfPolicy_array_bindcdecls.h sidl_EnfPolicy_fAbbrev.h                  \
  sidl_EnfPolicy_fStub.h sidl_EnfPolicy_mod_bindcdecls.h                      \
  sidl_EnfTraceLevel_array_bindcdecls.h sidl_EnfTraceLevel_fAbbrev.h          \
  sidl_EnfTraceLevel_mod_bindcdecls.h sidl_EnforceFreq_array_bindcdecls.h     \
  sidl_EnforceFreq_fAbbrev.h sidl_EnforceFreq_mod_bindcdecls.h                \
  sidl_Finder_array_bindcdecls.h sidl_Finder_fAbbrev.h sidl_Finder_fStub.h    \
  sidl_Finder_mod_bindcdecls.h sidl_InvViolation_array_bindcdecls.h           \
  sidl_InvViolation_fAbbrev.h sidl_InvViolation_fStub.h                       \
  sidl_InvViolation_mod_bindcdecls.h                                          \
  sidl_LangSpecificException_array_bindcdecls.h                               \
  sidl_LangSpecificException_fAbbrev.h sidl_LangSpecificException_fStub.h     \
  sidl_LangSpecificException_mod_bindcdecls.h sidl_Loader_array_bindcdecls.h  \
  sidl_Loader_fAbbrev.h sidl_Loader_fStub.h sidl_Loader_mod_bindcdecls.h      \
  sidl_MemAllocException_array_bindcdecls.h sidl_MemAllocException_fAbbrev.h  \
  sidl_MemAllocException_fStub.h sidl_MemAllocException_mod_bindcdecls.h      \
  sidl_NotImplementedException_array_bindcdecls.h                             \
  sidl_NotImplementedException_fAbbrev.h sidl_NotImplementedException_fStub.h \
  sidl_NotImplementedException_mod_bindcdecls.h                               \
  sidl_PostViolation_array_bindcdecls.h sidl_PostViolation_fAbbrev.h          \
  sidl_PostViolation_fStub.h sidl_PostViolation_mod_bindcdecls.h              \
  sidl_PreViolation_array_bindcdecls.h sidl_PreViolation_fAbbrev.h            \
  sidl_PreViolation_fStub.h sidl_PreViolation_mod_bindcdecls.h                \
  sidl_Resolve_array_bindcdecls.h sidl_Resolve_fAbbrev.h                      \
  sidl_Resolve_mod_bindcdecls.h sidl_RuntimeException_array_bindcdecls.h      \
  sidl_RuntimeException_fAbbrev.h sidl_RuntimeException_fStub.h               \
  sidl_RuntimeException_mod_bindcdecls.h                                      \
  sidl_SIDLException_array_bindcdecls.h sidl_SIDLException_fAbbrev.h          \
  sidl_SIDLException_fStub.h sidl_SIDLException_mod_bindcdecls.h              \
  sidl_Scope_array_bindcdecls.h sidl_Scope_fAbbrev.h                          \
  sidl_Scope_mod_bindcdecls.h sidl_array_array_bindcdecls.h                   \
  sidl_array_fAbbrev.h sidl_bool_array_bindcdecls.h sidl_bool_fAbbrev.h       \
  sidl_char_array_bindcdecls.h sidl_char_fAbbrev.h                            \
  sidl_dcomplex_array_bindcdecls.h sidl_dcomplex_fAbbrev.h                    \
  sidl_double_array_bindcdecls.h sidl_double_fAbbrev.h                        \
  sidl_fcomplex_array_bindcdecls.h sidl_fcomplex_fAbbrev.h                    \
  sidl_float_array_bindcdecls.h sidl_float_fAbbrev.h                          \
  sidl_int_array_bindcdecls.h sidl_int_fAbbrev.h                              \
  sidl_io_Deserializer_array_bindcdecls.h sidl_io_Deserializer_fAbbrev.h      \
  sidl_io_Deserializer_fStub.h sidl_io_Deserializer_mod_bindcdecls.h          \
  sidl_io_IOException_array_bindcdecls.h sidl_io_IOException_fAbbrev.h        \
  sidl_io_IOException_fStub.h sidl_io_IOException_mod_bindcdecls.h            \
  sidl_io_Serializable_array_bindcdecls.h sidl_io_Serializable_fAbbrev.h      \
  sidl_io_Serializable_fStub.h sidl_io_Serializable_mod_bindcdecls.h          \
  sidl_io_Serializer_array_bindcdecls.h sidl_io_Serializer_fAbbrev.h          \
  sidl_io_Serializer_fStub.h sidl_io_Serializer_mod_bindcdecls.h              \
  sidl_long_array_bindcdecls.h sidl_long_fAbbrev.h                            \
  sidl_opaque_array_bindcdecls.h sidl_opaque_fAbbrev.h                        \
  sidl_rmi_BindException_array_bindcdecls.h sidl_rmi_BindException_fAbbrev.h  \
  sidl_rmi_BindException_fStub.h sidl_rmi_BindException_mod_bindcdecls.h      \
  sidl_rmi_Call_array_bindcdecls.h sidl_rmi_Call_fAbbrev.h                    \
  sidl_rmi_Call_fStub.h sidl_rmi_Call_mod_bindcdecls.h                        \
  sidl_rmi_ConnectException_array_bindcdecls.h                                \
  sidl_rmi_ConnectException_fAbbrev.h sidl_rmi_ConnectException_fStub.h       \
  sidl_rmi_ConnectException_mod_bindcdecls.h                                  \
  sidl_rmi_ConnectRegistry_array_bindcdecls.h                                 \
  sidl_rmi_ConnectRegistry_fAbbrev.h sidl_rmi_ConnectRegistry_fStub.h         \
  sidl_rmi_ConnectRegistry_mod_bindcdecls.h                                   \
  sidl_rmi_InstanceHandle_array_bindcdecls.h                                  \
  sidl_rmi_InstanceHandle_fAbbrev.h sidl_rmi_InstanceHandle_fStub.h           \
  sidl_rmi_InstanceHandle_mod_bindcdecls.h                                    \
  sidl_rmi_InstanceRegistry_array_bindcdecls.h                                \
  sidl_rmi_InstanceRegistry_fAbbrev.h sidl_rmi_InstanceRegistry_fStub.h       \
  sidl_rmi_InstanceRegistry_mod_bindcdecls.h                                  \
  sidl_rmi_Invocation_array_bindcdecls.h sidl_rmi_Invocation_fAbbrev.h        \
  sidl_rmi_Invocation_fStub.h sidl_rmi_Invocation_mod_bindcdecls.h            \
  sidl_rmi_MalformedURLException_array_bindcdecls.h                           \
  sidl_rmi_MalformedURLException_fAbbrev.h                                    \
  sidl_rmi_MalformedURLException_fStub.h                                      \
  sidl_rmi_MalformedURLException_mod_bindcdecls.h                             \
  sidl_rmi_NetworkException_array_bindcdecls.h                                \
  sidl_rmi_NetworkException_fAbbrev.h sidl_rmi_NetworkException_fStub.h       \
  sidl_rmi_NetworkException_mod_bindcdecls.h                                  \
  sidl_rmi_NoRouteToHostException_array_bindcdecls.h                          \
  sidl_rmi_NoRouteToHostException_fAbbrev.h                                   \
  sidl_rmi_NoRouteToHostException_fStub.h                                     \
  sidl_rmi_NoRouteToHostException_mod_bindcdecls.h                            \
  sidl_rmi_NoServerException_array_bindcdecls.h                               \
  sidl_rmi_NoServerException_fAbbrev.h sidl_rmi_NoServerException_fStub.h     \
  sidl_rmi_NoServerException_mod_bindcdecls.h                                 \
  sidl_rmi_ObjectDoesNotExistException_array_bindcdecls.h                     \
  sidl_rmi_ObjectDoesNotExistException_fAbbrev.h                              \
  sidl_rmi_ObjectDoesNotExistException_fStub.h                                \
  sidl_rmi_ObjectDoesNotExistException_mod_bindcdecls.h                       \
  sidl_rmi_ProtocolException_array_bindcdecls.h                               \
  sidl_rmi_ProtocolException_fAbbrev.h sidl_rmi_ProtocolException_fStub.h     \
  sidl_rmi_ProtocolException_mod_bindcdecls.h                                 \
  sidl_rmi_ProtocolFactory_array_bindcdecls.h                                 \
  sidl_rmi_ProtocolFactory_fAbbrev.h sidl_rmi_ProtocolFactory_fStub.h         \
  sidl_rmi_ProtocolFactory_mod_bindcdecls.h                                   \
  sidl_rmi_Response_array_bindcdecls.h sidl_rmi_Response_fAbbrev.h            \
  sidl_rmi_Response_fStub.h sidl_rmi_Response_mod_bindcdecls.h                \
  sidl_rmi_Return_array_bindcdecls.h sidl_rmi_Return_fAbbrev.h                \
  sidl_rmi_Return_fStub.h sidl_rmi_Return_mod_bindcdecls.h                    \
  sidl_rmi_ServerInfo_array_bindcdecls.h sidl_rmi_ServerInfo_fAbbrev.h        \
  sidl_rmi_ServerInfo_fStub.h sidl_rmi_ServerInfo_mod_bindcdecls.h            \
  sidl_rmi_ServerRegistry_array_bindcdecls.h                                  \
  sidl_rmi_ServerRegistry_fAbbrev.h sidl_rmi_ServerRegistry_fStub.h           \
  sidl_rmi_ServerRegistry_mod_bindcdecls.h                                    \
  sidl_rmi_TicketBook_array_bindcdecls.h sidl_rmi_TicketBook_fAbbrev.h        \
  sidl_rmi_TicketBook_fStub.h sidl_rmi_TicketBook_mod_bindcdecls.h            \
  sidl_rmi_Ticket_array_bindcdecls.h sidl_rmi_Ticket_fAbbrev.h                \
  sidl_rmi_Ticket_fStub.h sidl_rmi_Ticket_mod_bindcdecls.h                    \
  sidl_rmi_TimeOutException_array_bindcdecls.h                                \
  sidl_rmi_TimeOutException_fAbbrev.h sidl_rmi_TimeOutException_fStub.h       \
  sidl_rmi_TimeOutException_mod_bindcdecls.h                                  \
  sidl_rmi_UnexpectedCloseException_array_bindcdecls.h                        \
  sidl_rmi_UnexpectedCloseException_fAbbrev.h                                 \
  sidl_rmi_UnexpectedCloseException_fStub.h                                   \
  sidl_rmi_UnexpectedCloseException_mod_bindcdecls.h                          \
  sidl_rmi_UnknownHostException_array_bindcdecls.h                            \
  sidl_rmi_UnknownHostException_fAbbrev.h                                     \
  sidl_rmi_UnknownHostException_fStub.h                                       \
  sidl_rmi_UnknownHostException_mod_bindcdecls.h                              \
  sidl_string_array_bindcdecls.h sidl_string_fAbbrev.h
STUBMODULESRCS = sidl_BaseClass.F90 sidl_BaseException.F90                    \
  sidl_BaseInterface.F90 sidl_CastException.F90 sidl_ClassInfo.F90            \
  sidl_ClassInfoI.F90 sidl_ClauseType.F90 sidl_ContractClass.F90              \
  sidl_DFinder.F90 sidl_DLL.F90 sidl_EnfPolicy.F90 sidl_EnfTraceLevel.F90     \
  sidl_EnforceFreq.F90 sidl_Finder.F90 sidl_InvViolation.F90                  \
  sidl_LangSpecificException.F90 sidl_Loader.F90 sidl_MemAllocException.F90   \
  sidl_NotImplementedException.F90 sidl_PostViolation.F90                     \
  sidl_PreViolation.F90 sidl_Resolve.F90 sidl_RuntimeException.F90            \
  sidl_SIDLException.F90 sidl_Scope.F90 sidl_io_Deserializer.F90              \
  sidl_io_IOException.F90 sidl_io_Serializable.F90 sidl_io_Serializer.F90     \
  sidl_rmi_BindException.F90 sidl_rmi_Call.F90 sidl_rmi_ConnectException.F90  \
  sidl_rmi_ConnectRegistry.F90 sidl_rmi_InstanceHandle.F90                    \
  sidl_rmi_InstanceRegistry.F90 sidl_rmi_Invocation.F90                       \
  sidl_rmi_MalformedURLException.F90 sidl_rmi_NetworkException.F90            \
  sidl_rmi_NoRouteToHostException.F90 sidl_rmi_NoServerException.F90          \
  sidl_rmi_ObjectDoesNotExistException.F90 sidl_rmi_ProtocolException.F90     \
  sidl_rmi_ProtocolFactory.F90 sidl_rmi_Response.F90 sidl_rmi_Return.F90      \
  sidl_rmi_ServerInfo.F90 sidl_rmi_ServerRegistry.F90 sidl_rmi_Ticket.F90     \
  sidl_rmi_TicketBook.F90 sidl_rmi_TimeOutException.F90                       \
  sidl_rmi_UnexpectedCloseException.F90 sidl_rmi_UnknownHostException.F90
STUBSRCS = sidl_BaseClass_fStub.c sidl_BaseException_fStub.c                  \
  sidl_BaseInterface_fStub.c sidl_CastException_fStub.c                       \
  sidl_ClassInfoI_fStub.c sidl_ClassInfo_fStub.c sidl_ClauseType_fStub.c      \
  sidl_ContractClass_fStub.c sidl_DFinder_fStub.c sidl_DLL_fStub.c            \
  sidl_EnfPolicy_fStub.c sidl_EnfTraceLevel_fStub.c sidl_EnforceFreq_fStub.c  \
  sidl_Finder_fStub.c sidl_InvViolation_fStub.c                               \
  sidl_LangSpecificException_fStub.c sidl_Loader_fStub.c                      \
  sidl_MemAllocException_fStub.c sidl_NotImplementedException_fStub.c         \
  sidl_PostViolation_fStub.c sidl_PreViolation_fStub.c sidl_Resolve_fStub.c   \
  sidl_RuntimeException_fStub.c sidl_SIDLException_fStub.c sidl_Scope_fStub.c \
  sidl_array_fStub.c sidl_bool_fStub.c sidl_char_fStub.c                      \
  sidl_dcomplex_fStub.c sidl_double_fStub.c sidl_fcomplex_fStub.c             \
  sidl_float_fStub.c sidl_int_fStub.c sidl_io_Deserializer_fStub.c            \
  sidl_io_IOException_fStub.c sidl_io_Serializable_fStub.c                    \
  sidl_io_Serializer_fStub.c sidl_long_fStub.c sidl_opaque_fStub.c            \
  sidl_rmi_BindException_fStub.c sidl_rmi_Call_fStub.c                        \
  sidl_rmi_ConnectException_fStub.c sidl_rmi_ConnectRegistry_fStub.c          \
  sidl_rmi_InstanceHandle_fStub.c sidl_rmi_InstanceRegistry_fStub.c           \
  sidl_rmi_Invocation_fStub.c sidl_rmi_MalformedURLException_fStub.c          \
  sidl_rmi_NetworkException_fStub.c sidl_rmi_NoRouteToHostException_fStub.c   \
  sidl_rmi_NoServerException_fStub.c                                          \
  sidl_rmi_ObjectDoesNotExistException_fStub.c                                \
  sidl_rmi_ProtocolException_fStub.c sidl_rmi_ProtocolFactory_fStub.c         \
  sidl_rmi_Response_fStub.c sidl_rmi_Return_fStub.c                           \
  sidl_rmi_ServerInfo_fStub.c sidl_rmi_ServerRegistry_fStub.c                 \
  sidl_rmi_TicketBook_fStub.c sidl_rmi_Ticket_fStub.c                         \
  sidl_rmi_TimeOutException_fStub.c sidl_rmi_UnexpectedCloseException_fStub.c \
  sidl_rmi_UnknownHostException_fStub.c sidl_string_fStub.c
TYPEMODULESRCS = sidl_BaseClass_type.F90 sidl_BaseException_type.F90          \
  sidl_BaseInterface_type.F90 sidl_CastException_type.F90                     \
  sidl_ClassInfoI_type.F90 sidl_ClassInfo_type.F90 sidl_ClauseType_type.F90   \
  sidl_ContractClass_type.F90 sidl_DFinder_type.F90 sidl_DLL_type.F90         \
  sidl_EnfPolicy_type.F90 sidl_EnfTraceLevel_type.F90                         \
  sidl_EnforceFreq_type.F90 sidl_Finder_type.F90 sidl_InvViolation_type.F90   \
  sidl_LangSpecificException_type.F90 sidl_Loader_type.F90                    \
  sidl_MemAllocException_type.F90 sidl_NotImplementedException_type.F90       \
  sidl_PostViolation_type.F90 sidl_PreViolation_type.F90                      \
  sidl_Resolve_type.F90 sidl_RuntimeException_type.F90                        \
  sidl_SIDLException_type.F90 sidl_Scope_type.F90 sidl_array_type.F90         \
  sidl_io_Deserializer_type.F90 sidl_io_IOException_type.F90                  \
  sidl_io_Serializable_type.F90 sidl_io_Serializer_type.F90                   \
  sidl_rmi_BindException_type.F90 sidl_rmi_Call_type.F90                      \
  sidl_rmi_ConnectException_type.F90 sidl_rmi_ConnectRegistry_type.F90        \
  sidl_rmi_InstanceHandle_type.F90 sidl_rmi_InstanceRegistry_type.F90         \
  sidl_rmi_Invocation_type.F90 sidl_rmi_MalformedURLException_type.F90        \
  sidl_rmi_NetworkException_type.F90 sidl_rmi_NoRouteToHostException_type.F90 \
  sidl_rmi_NoServerException_type.F90                                         \
  sidl_rmi_ObjectDoesNotExistException_type.F90                               \
  sidl_rmi_ProtocolException_type.F90 sidl_rmi_ProtocolFactory_type.F90       \
  sidl_rmi_Response_type.F90 sidl_rmi_Return_type.F90                         \
  sidl_rmi_ServerInfo_type.F90 sidl_rmi_ServerRegistry_type.F90               \
  sidl_rmi_TicketBook_type.F90 sidl_rmi_Ticket_type.F90                       \
  sidl_rmi_TimeOutException_type.F90                                          \
  sidl_rmi_UnexpectedCloseException_type.F90                                  \
  sidl_rmi_UnknownHostException_type.F90

_deps_sidl_BaseClass =  sidl sidl_RuntimeException_type sidl_ClassInfo_type   \
  sidl_BaseClass_type sidl_BaseInterface_type sidl_BaseException_type         \
  sidl_rmi_Call_type sidl_rmi_Return_type sidl_rmi_Ticket_type sidl_array_type
sidl_BaseClass$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_BaseClass))

_deps_sidl_BaseClass_type =  sidl
sidl_BaseClass_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_BaseClass_type))

_deps_sidl_BaseException =  sidl sidl_io_Deserializer_type                    \
  sidl_io_Serializer_type sidl_RuntimeException_type sidl_ClassInfo_type      \
  sidl_BaseInterface_type sidl_io_Serializable_type sidl_BaseException_type   \
  sidl_rmi_Call_type sidl_rmi_Return_type sidl_rmi_Ticket_type sidl_array_type
sidl_BaseException$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_BaseException))

_deps_sidl_BaseException_type =  sidl
sidl_BaseException_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_BaseException_type))

_deps_sidl_BaseInterface =  sidl sidl_RuntimeException_type                   \
  sidl_ClassInfo_type sidl_BaseInterface_type sidl_BaseException_type         \
  sidl_rmi_Call_type sidl_rmi_Return_type sidl_rmi_Ticket_type sidl_array_type
sidl_BaseInterface$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_BaseInterface))

_deps_sidl_BaseInterface_type =  sidl
sidl_BaseInterface_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_BaseInterface_type))

_deps_sidl_CastException =  sidl sidl_io_Deserializer_type                    \
  sidl_io_Serializer_type sidl_RuntimeException_type sidl_ClassInfo_type      \
  sidl_BaseClass_type sidl_CastException_type sidl_BaseInterface_type         \
  sidl_io_Serializable_type sidl_SIDLException_type sidl_BaseException_type   \
  sidl_rmi_Call_type sidl_rmi_Return_type sidl_rmi_Ticket_type sidl_array_type
sidl_CastException$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_CastException))

_deps_sidl_CastException_type =  sidl
sidl_CastException_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_CastException_type))

_deps_sidl_ClassInfo =  sidl sidl_RuntimeException_type sidl_ClassInfo_type   \
  sidl_BaseInterface_type sidl_BaseException_type sidl_rmi_Call_type          \
  sidl_rmi_Return_type sidl_rmi_Ticket_type sidl_array_type
sidl_ClassInfo$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_ClassInfo))

_deps_sidl_ClassInfoI =  sidl sidl_RuntimeException_type sidl_ClassInfo_type  \
  sidl_BaseClass_type sidl_BaseInterface_type sidl_BaseException_type         \
  sidl_ClassInfoI_type sidl_rmi_Call_type sidl_rmi_Return_type                \
  sidl_rmi_Ticket_type sidl_array_type
sidl_ClassInfoI$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_ClassInfoI))

_deps_sidl_ClassInfoI_type =  sidl
sidl_ClassInfoI_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_ClassInfoI_type))

_deps_sidl_ClassInfo_type =  sidl
sidl_ClassInfo_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_ClassInfo_type))

_deps_sidl_ClauseType =  sidl sidl_ClauseType_type sidl_array_type
sidl_ClauseType$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_ClauseType))

_deps_sidl_ClauseType_type =  sidl
sidl_ClauseType_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_ClauseType_type))

_deps_sidl_ContractClass =  sidl sidl_ContractClass_type sidl_array_type
sidl_ContractClass$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_ContractClass))

_deps_sidl_ContractClass_type =  sidl
sidl_ContractClass_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_ContractClass_type))

_deps_sidl_DFinder =  sidl sidl_Finder_type sidl_RuntimeException_type        \
  sidl_ClassInfo_type sidl_BaseClass_type sidl_DLL_type sidl_DFinder_type     \
  sidl_BaseInterface_type sidl_Resolve_type sidl_Scope_type                   \
  sidl_BaseException_type sidl_rmi_Call_type sidl_rmi_Return_type             \
  sidl_rmi_Ticket_type sidl_array_type
sidl_DFinder$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_DFinder))

_deps_sidl_DFinder_type =  sidl
sidl_DFinder_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_DFinder_type))

_deps_sidl_DLL =  sidl sidl_RuntimeException_type sidl_ClassInfo_type         \
  sidl_BaseClass_type sidl_DLL_type sidl_BaseInterface_type                   \
  sidl_BaseException_type sidl_rmi_Call_type sidl_rmi_Return_type             \
  sidl_rmi_Ticket_type sidl_array_type
sidl_DLL$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_DLL))

_deps_sidl_DLL_type =  sidl
sidl_DLL_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_DLL_type))

_deps_sidl_EnfPolicy =  sidl sidl_EnfTraceLevel_type sidl_EnfPolicy_type      \
  sidl_RuntimeException_type sidl_ClassInfo_type sidl_BaseClass_type          \
  sidl_BaseInterface_type sidl_EnforceFreq_type sidl_ContractClass_type       \
  sidl_BaseException_type sidl_rmi_Call_type sidl_rmi_Return_type             \
  sidl_rmi_Ticket_type sidl_array_type
sidl_EnfPolicy$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_EnfPolicy))

_deps_sidl_EnfPolicy_type =  sidl
sidl_EnfPolicy_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_EnfPolicy_type))

_deps_sidl_EnfTraceLevel =  sidl sidl_EnfTraceLevel_type sidl_array_type
sidl_EnfTraceLevel$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_EnfTraceLevel))

_deps_sidl_EnfTraceLevel_type =  sidl
sidl_EnfTraceLevel_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_EnfTraceLevel_type))

_deps_sidl_EnforceFreq =  sidl sidl_EnforceFreq_type sidl_array_type
sidl_EnforceFreq$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_EnforceFreq))

_deps_sidl_EnforceFreq_type =  sidl
sidl_EnforceFreq_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_EnforceFreq_type))

_deps_sidl_Finder =  sidl sidl_Finder_type sidl_RuntimeException_type         \
  sidl_ClassInfo_type sidl_DLL_type sidl_BaseInterface_type sidl_Resolve_type \
  sidl_Scope_type sidl_BaseException_type sidl_rmi_Call_type                  \
  sidl_rmi_Return_type sidl_rmi_Ticket_type sidl_array_type
sidl_Finder$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_Finder))

_deps_sidl_Finder_type =  sidl
sidl_Finder_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_Finder_type))

_deps_sidl_InvViolation =  sidl sidl_io_Deserializer_type                     \
  sidl_io_Serializer_type sidl_InvViolation_type sidl_RuntimeException_type   \
  sidl_ClassInfo_type sidl_BaseClass_type sidl_BaseInterface_type             \
  sidl_io_Serializable_type sidl_SIDLException_type sidl_BaseException_type   \
  sidl_rmi_Call_type sidl_rmi_Return_type sidl_rmi_Ticket_type sidl_array_type
sidl_InvViolation$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_InvViolation))

_deps_sidl_InvViolation_type =  sidl
sidl_InvViolation_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_InvViolation_type))

_deps_sidl_LangSpecificException =  sidl sidl_LangSpecificException_type      \
  sidl_io_Deserializer_type sidl_io_Serializer_type                           \
  sidl_RuntimeException_type sidl_ClassInfo_type sidl_BaseClass_type          \
  sidl_BaseInterface_type sidl_io_Serializable_type sidl_SIDLException_type   \
  sidl_BaseException_type sidl_rmi_Call_type sidl_rmi_Return_type             \
  sidl_rmi_Ticket_type sidl_array_type
sidl_LangSpecificException$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_LangSpecificException))

_deps_sidl_LangSpecificException_type =  sidl
sidl_LangSpecificException_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_LangSpecificException_type))

_deps_sidl_Loader =  sidl sidl_Loader_type sidl_Finder_type                   \
  sidl_RuntimeException_type sidl_ClassInfo_type sidl_BaseClass_type          \
  sidl_DLL_type sidl_BaseInterface_type sidl_Resolve_type sidl_Scope_type     \
  sidl_BaseException_type sidl_rmi_Call_type sidl_rmi_Return_type             \
  sidl_rmi_Ticket_type sidl_array_type
sidl_Loader$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_Loader))

_deps_sidl_Loader_type =  sidl
sidl_Loader_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_Loader_type))

_deps_sidl_MemAllocException =  sidl sidl_io_Deserializer_type                \
  sidl_io_Serializer_type sidl_RuntimeException_type sidl_ClassInfo_type      \
  sidl_BaseClass_type sidl_BaseInterface_type sidl_io_Serializable_type       \
  sidl_MemAllocException_type sidl_SIDLException_type sidl_BaseException_type \
  sidl_rmi_Call_type sidl_rmi_Return_type sidl_rmi_Ticket_type sidl_array_type
sidl_MemAllocException$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_MemAllocException))

_deps_sidl_MemAllocException_type =  sidl
sidl_MemAllocException_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_MemAllocException_type))

_deps_sidl_NotImplementedException =  sidl sidl_io_Deserializer_type          \
  sidl_io_Serializer_type sidl_RuntimeException_type sidl_ClassInfo_type      \
  sidl_BaseClass_type sidl_BaseInterface_type sidl_io_Serializable_type       \
  sidl_SIDLException_type sidl_NotImplementedException_type                   \
  sidl_BaseException_type sidl_rmi_Call_type sidl_rmi_Return_type             \
  sidl_rmi_Ticket_type sidl_array_type
sidl_NotImplementedException$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_NotImplementedException))

_deps_sidl_NotImplementedException_type =  sidl
sidl_NotImplementedException_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_NotImplementedException_type))

_deps_sidl_PostViolation =  sidl sidl_io_Deserializer_type                    \
  sidl_PostViolation_type sidl_io_Serializer_type sidl_RuntimeException_type  \
  sidl_ClassInfo_type sidl_BaseClass_type sidl_BaseInterface_type             \
  sidl_io_Serializable_type sidl_SIDLException_type sidl_BaseException_type   \
  sidl_rmi_Call_type sidl_rmi_Return_type sidl_rmi_Ticket_type sidl_array_type
sidl_PostViolation$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_PostViolation))

_deps_sidl_PostViolation_type =  sidl
sidl_PostViolation_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_PostViolation_type))

_deps_sidl_PreViolation =  sidl sidl_io_Deserializer_type                     \
  sidl_io_Serializer_type sidl_RuntimeException_type sidl_ClassInfo_type      \
  sidl_BaseClass_type sidl_BaseInterface_type sidl_PreViolation_type          \
  sidl_io_Serializable_type sidl_SIDLException_type sidl_BaseException_type   \
  sidl_rmi_Call_type sidl_rmi_Return_type sidl_rmi_Ticket_type sidl_array_type
sidl_PreViolation$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_PreViolation))

_deps_sidl_PreViolation_type =  sidl
sidl_PreViolation_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_PreViolation_type))

_deps_sidl_Resolve =  sidl sidl_Resolve_type sidl_array_type
sidl_Resolve$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_Resolve))

_deps_sidl_Resolve_type =  sidl
sidl_Resolve_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_Resolve_type))

_deps_sidl_RuntimeException =  sidl sidl_io_Deserializer_type                 \
  sidl_io_Serializer_type sidl_RuntimeException_type sidl_ClassInfo_type      \
  sidl_BaseInterface_type sidl_io_Serializable_type sidl_BaseException_type   \
  sidl_rmi_Call_type sidl_rmi_Return_type sidl_rmi_Ticket_type sidl_array_type
sidl_RuntimeException$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_RuntimeException))

_deps_sidl_RuntimeException_type =  sidl
sidl_RuntimeException_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_RuntimeException_type))

_deps_sidl_SIDLException =  sidl sidl_io_Deserializer_type                    \
  sidl_io_Serializer_type sidl_RuntimeException_type sidl_ClassInfo_type      \
  sidl_BaseClass_type sidl_BaseInterface_type sidl_io_Serializable_type       \
  sidl_SIDLException_type sidl_BaseException_type sidl_rmi_Call_type          \
  sidl_rmi_Return_type sidl_rmi_Ticket_type sidl_array_type
sidl_SIDLException$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_SIDLException))

_deps_sidl_SIDLException_type =  sidl
sidl_SIDLException_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_SIDLException_type))

_deps_sidl_Scope =  sidl sidl_Scope_type sidl_array_type
sidl_Scope$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_Scope))

_deps_sidl_Scope_type =  sidl
sidl_Scope_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_Scope_type))

_deps_sidl_io_Deserializer =  sidl sidl_io_Deserializer_type                  \
  sidl_RuntimeException_type sidl_ClassInfo_type sidl_BaseInterface_type      \
  sidl_io_Serializable_type sidl_BaseException_type sidl_rmi_Call_type        \
  sidl_rmi_Return_type sidl_rmi_Ticket_type sidl_double_array sidl_int_array  \
  sidl_char_array sidl_opaque_array sidl_dcomplex_array sidl_float_array      \
  sidl_array_array sidl_bool_array sidl_string_array sidl_long_array          \
  sidl_fcomplex_array sidl_array_type
sidl_io_Deserializer$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_io_Deserializer))

_deps_sidl_io_Deserializer_type =  sidl
sidl_io_Deserializer_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_io_Deserializer_type))

_deps_sidl_io_IOException =  sidl sidl_io_IOException_type                    \
  sidl_io_Deserializer_type sidl_io_Serializer_type                           \
  sidl_RuntimeException_type sidl_ClassInfo_type sidl_BaseClass_type          \
  sidl_BaseInterface_type sidl_io_Serializable_type sidl_SIDLException_type   \
  sidl_BaseException_type sidl_rmi_Call_type sidl_rmi_Return_type             \
  sidl_rmi_Ticket_type sidl_array_type
sidl_io_IOException$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_io_IOException))

_deps_sidl_io_IOException_type =  sidl
sidl_io_IOException_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_io_IOException_type))

_deps_sidl_io_Serializable =  sidl sidl_io_Deserializer_type                  \
  sidl_io_Serializer_type sidl_RuntimeException_type sidl_ClassInfo_type      \
  sidl_BaseInterface_type sidl_io_Serializable_type sidl_BaseException_type   \
  sidl_rmi_Call_type sidl_rmi_Return_type sidl_rmi_Ticket_type sidl_array_type
sidl_io_Serializable$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_io_Serializable))

_deps_sidl_io_Serializable_type =  sidl
sidl_io_Serializable_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_io_Serializable_type))

_deps_sidl_io_Serializer =  sidl sidl_io_Serializer_type                      \
  sidl_RuntimeException_type sidl_ClassInfo_type sidl_BaseInterface_type      \
  sidl_io_Serializable_type sidl_BaseException_type sidl_rmi_Call_type        \
  sidl_rmi_Return_type sidl_rmi_Ticket_type sidl_double_array sidl_int_array  \
  sidl_char_array sidl_opaque_array sidl_dcomplex_array sidl_float_array      \
  sidl_array_array sidl_bool_array sidl_string_array sidl_long_array          \
  sidl_fcomplex_array sidl_array_type
sidl_io_Serializer$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_io_Serializer))

_deps_sidl_io_Serializer_type =  sidl
sidl_io_Serializer_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_io_Serializer_type))

_deps_sidl_rmi_BindException =  sidl sidl_io_IOException_type                 \
  sidl_io_Deserializer_type sidl_io_Serializer_type                           \
  sidl_RuntimeException_type sidl_ClassInfo_type sidl_BaseClass_type          \
  sidl_BaseInterface_type sidl_io_Serializable_type sidl_SIDLException_type   \
  sidl_rmi_BindException_type sidl_BaseException_type                         \
  sidl_rmi_NetworkException_type sidl_rmi_Call_type sidl_rmi_Return_type      \
  sidl_rmi_Ticket_type sidl_array_type
sidl_rmi_BindException$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_BindException))

_deps_sidl_rmi_BindException_type =  sidl
sidl_rmi_BindException_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_BindException_type))

_deps_sidl_rmi_Call =  sidl sidl_io_Deserializer_type sidl_rmi_Call_type      \
  sidl_RuntimeException_type sidl_ClassInfo_type sidl_BaseInterface_type      \
  sidl_io_Serializable_type sidl_BaseException_type sidl_rmi_Return_type      \
  sidl_rmi_Ticket_type sidl_double_array sidl_int_array sidl_char_array       \
  sidl_opaque_array sidl_dcomplex_array sidl_float_array sidl_array_array     \
  sidl_bool_array sidl_string_array sidl_long_array sidl_fcomplex_array       \
  sidl_array_type
sidl_rmi_Call$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_Call))

_deps_sidl_rmi_Call_type =  sidl
sidl_rmi_Call_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_Call_type))

_deps_sidl_rmi_ConnectException =  sidl sidl_io_IOException_type              \
  sidl_rmi_ConnectException_type sidl_io_Deserializer_type                    \
  sidl_io_Serializer_type sidl_RuntimeException_type sidl_ClassInfo_type      \
  sidl_BaseClass_type sidl_BaseInterface_type sidl_io_Serializable_type       \
  sidl_SIDLException_type sidl_BaseException_type                             \
  sidl_rmi_NetworkException_type sidl_rmi_Call_type sidl_rmi_Return_type      \
  sidl_rmi_Ticket_type sidl_array_type
sidl_rmi_ConnectException$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_ConnectException))

_deps_sidl_rmi_ConnectException_type =  sidl
sidl_rmi_ConnectException_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_ConnectException_type))

_deps_sidl_rmi_ConnectRegistry =  sidl sidl_RuntimeException_type             \
  sidl_ClassInfo_type sidl_BaseClass_type sidl_BaseInterface_type             \
  sidl_rmi_ConnectRegistry_type sidl_BaseException_type sidl_rmi_Call_type    \
  sidl_rmi_Return_type sidl_rmi_Ticket_type sidl_array_type
sidl_rmi_ConnectRegistry$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_ConnectRegistry))

_deps_sidl_rmi_ConnectRegistry_type =  sidl
sidl_rmi_ConnectRegistry_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_ConnectRegistry_type))

_deps_sidl_rmi_InstanceHandle =  sidl sidl_RuntimeException_type              \
  sidl_ClassInfo_type sidl_rmi_InstanceHandle_type sidl_BaseInterface_type    \
  sidl_rmi_Invocation_type sidl_io_Serializable_type sidl_BaseException_type  \
  sidl_rmi_Call_type sidl_rmi_Return_type sidl_rmi_Ticket_type sidl_array_type
sidl_rmi_InstanceHandle$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_InstanceHandle))

_deps_sidl_rmi_InstanceHandle_type =  sidl
sidl_rmi_InstanceHandle_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_InstanceHandle_type))

_deps_sidl_rmi_InstanceRegistry =  sidl sidl_RuntimeException_type            \
  sidl_ClassInfo_type sidl_BaseClass_type sidl_BaseInterface_type             \
  sidl_rmi_InstanceRegistry_type sidl_BaseException_type sidl_rmi_Call_type   \
  sidl_rmi_Return_type sidl_rmi_Ticket_type sidl_array_type
sidl_rmi_InstanceRegistry$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_InstanceRegistry))

_deps_sidl_rmi_InstanceRegistry_type =  sidl
sidl_rmi_InstanceRegistry_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_InstanceRegistry_type))

_deps_sidl_rmi_Invocation =  sidl sidl_io_Serializer_type                     \
  sidl_rmi_Ticket_type sidl_RuntimeException_type sidl_rmi_Response_type      \
  sidl_ClassInfo_type sidl_BaseInterface_type sidl_rmi_Invocation_type        \
  sidl_io_Serializable_type sidl_BaseException_type sidl_rmi_Call_type        \
  sidl_rmi_Return_type sidl_double_array sidl_int_array sidl_char_array       \
  sidl_opaque_array sidl_dcomplex_array sidl_float_array sidl_array_array     \
  sidl_bool_array sidl_string_array sidl_long_array sidl_fcomplex_array       \
  sidl_array_type
sidl_rmi_Invocation$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_Invocation))

_deps_sidl_rmi_Invocation_type =  sidl
sidl_rmi_Invocation_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_Invocation_type))

_deps_sidl_rmi_MalformedURLException =  sidl sidl_io_IOException_type         \
  sidl_io_Deserializer_type sidl_io_Serializer_type                           \
  sidl_rmi_MalformedURLException_type sidl_RuntimeException_type              \
  sidl_ClassInfo_type sidl_BaseClass_type sidl_BaseInterface_type             \
  sidl_io_Serializable_type sidl_SIDLException_type sidl_BaseException_type   \
  sidl_rmi_NetworkException_type sidl_rmi_Call_type sidl_rmi_Return_type      \
  sidl_rmi_Ticket_type sidl_array_type
sidl_rmi_MalformedURLException$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_MalformedURLException))

_deps_sidl_rmi_MalformedURLException_type =  sidl
sidl_rmi_MalformedURLException_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_MalformedURLException_type))

_deps_sidl_rmi_NetworkException =  sidl sidl_io_IOException_type              \
  sidl_io_Deserializer_type sidl_io_Serializer_type                           \
  sidl_RuntimeException_type sidl_ClassInfo_type sidl_BaseClass_type          \
  sidl_BaseInterface_type sidl_io_Serializable_type sidl_SIDLException_type   \
  sidl_BaseException_type sidl_rmi_NetworkException_type sidl_rmi_Call_type   \
  sidl_rmi_Return_type sidl_rmi_Ticket_type sidl_array_type
sidl_rmi_NetworkException$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_NetworkException))

_deps_sidl_rmi_NetworkException_type =  sidl
sidl_rmi_NetworkException_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_NetworkException_type))

_deps_sidl_rmi_NoRouteToHostException =  sidl sidl_io_IOException_type        \
  sidl_io_Deserializer_type sidl_io_Serializer_type                           \
  sidl_rmi_NoRouteToHostException_type sidl_RuntimeException_type             \
  sidl_ClassInfo_type sidl_BaseClass_type sidl_BaseInterface_type             \
  sidl_io_Serializable_type sidl_SIDLException_type sidl_BaseException_type   \
  sidl_rmi_NetworkException_type sidl_rmi_Call_type sidl_rmi_Return_type      \
  sidl_rmi_Ticket_type sidl_array_type
sidl_rmi_NoRouteToHostException$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_NoRouteToHostException))

_deps_sidl_rmi_NoRouteToHostException_type =  sidl
sidl_rmi_NoRouteToHostException_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_NoRouteToHostException_type))

_deps_sidl_rmi_NoServerException =  sidl sidl_io_IOException_type             \
  sidl_io_Deserializer_type sidl_io_Serializer_type                           \
  sidl_rmi_NoServerException_type sidl_RuntimeException_type                  \
  sidl_ClassInfo_type sidl_BaseClass_type sidl_BaseInterface_type             \
  sidl_io_Serializable_type sidl_SIDLException_type sidl_BaseException_type   \
  sidl_rmi_NetworkException_type sidl_rmi_Call_type sidl_rmi_Return_type      \
  sidl_rmi_Ticket_type sidl_array_type
sidl_rmi_NoServerException$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_NoServerException))

_deps_sidl_rmi_NoServerException_type =  sidl
sidl_rmi_NoServerException_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_NoServerException_type))

_deps_sidl_rmi_ObjectDoesNotExistException =  sidl sidl_io_IOException_type   \
  sidl_rmi_ObjectDoesNotExistException_type sidl_io_Deserializer_type         \
  sidl_io_Serializer_type sidl_RuntimeException_type sidl_ClassInfo_type      \
  sidl_BaseClass_type sidl_BaseInterface_type sidl_io_Serializable_type       \
  sidl_SIDLException_type sidl_BaseException_type                             \
  sidl_rmi_NetworkException_type sidl_rmi_Call_type sidl_rmi_Return_type      \
  sidl_rmi_Ticket_type sidl_array_type
sidl_rmi_ObjectDoesNotExistException$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_ObjectDoesNotExistException))

_deps_sidl_rmi_ObjectDoesNotExistException_type =  sidl
sidl_rmi_ObjectDoesNotExistException_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_ObjectDoesNotExistException_type))

_deps_sidl_rmi_ProtocolException =  sidl sidl_io_IOException_type             \
  sidl_io_Deserializer_type sidl_io_Serializer_type                           \
  sidl_RuntimeException_type sidl_ClassInfo_type sidl_BaseClass_type          \
  sidl_rmi_ProtocolException_type sidl_BaseInterface_type                     \
  sidl_io_Serializable_type sidl_SIDLException_type sidl_BaseException_type   \
  sidl_rmi_NetworkException_type sidl_rmi_Call_type sidl_rmi_Return_type      \
  sidl_rmi_Ticket_type sidl_array_type
sidl_rmi_ProtocolException$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_ProtocolException))

_deps_sidl_rmi_ProtocolException_type =  sidl
sidl_rmi_ProtocolException_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_ProtocolException_type))

_deps_sidl_rmi_ProtocolFactory =  sidl sidl_RuntimeException_type             \
  sidl_ClassInfo_type sidl_BaseClass_type sidl_rmi_InstanceHandle_type        \
  sidl_rmi_ProtocolFactory_type sidl_BaseInterface_type                       \
  sidl_io_Serializable_type sidl_BaseException_type sidl_rmi_Call_type        \
  sidl_rmi_Return_type sidl_rmi_Ticket_type sidl_array_type
sidl_rmi_ProtocolFactory$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_ProtocolFactory))

_deps_sidl_rmi_ProtocolFactory_type =  sidl
sidl_rmi_ProtocolFactory_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_ProtocolFactory_type))

_deps_sidl_rmi_Response =  sidl sidl_io_Deserializer_type                     \
  sidl_RuntimeException_type sidl_rmi_Response_type sidl_ClassInfo_type       \
  sidl_BaseInterface_type sidl_io_Serializable_type sidl_BaseException_type   \
  sidl_rmi_Call_type sidl_rmi_Return_type sidl_rmi_Ticket_type                \
  sidl_double_array sidl_int_array sidl_char_array sidl_opaque_array          \
  sidl_dcomplex_array sidl_float_array sidl_array_array sidl_bool_array       \
  sidl_string_array sidl_long_array sidl_fcomplex_array sidl_array_type
sidl_rmi_Response$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_Response))

_deps_sidl_rmi_Response_type =  sidl
sidl_rmi_Response_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_Response_type))

_deps_sidl_rmi_Return =  sidl sidl_io_Serializer_type                         \
  sidl_RuntimeException_type sidl_ClassInfo_type sidl_BaseInterface_type      \
  sidl_io_Serializable_type sidl_BaseException_type sidl_rmi_Return_type      \
  sidl_rmi_Call_type sidl_rmi_Ticket_type sidl_double_array sidl_int_array    \
  sidl_char_array sidl_opaque_array sidl_dcomplex_array sidl_float_array      \
  sidl_array_array sidl_bool_array sidl_string_array sidl_long_array          \
  sidl_fcomplex_array sidl_array_type
sidl_rmi_Return$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_Return))

_deps_sidl_rmi_Return_type =  sidl
sidl_rmi_Return_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_Return_type))

_deps_sidl_rmi_ServerInfo =  sidl sidl_RuntimeException_type                  \
  sidl_ClassInfo_type sidl_rmi_ServerInfo_type sidl_BaseInterface_type        \
  sidl_io_Serializable_type sidl_BaseException_type sidl_rmi_Call_type        \
  sidl_rmi_Return_type sidl_rmi_Ticket_type sidl_array_type
sidl_rmi_ServerInfo$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_ServerInfo))

_deps_sidl_rmi_ServerInfo_type =  sidl
sidl_rmi_ServerInfo_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_ServerInfo_type))

_deps_sidl_rmi_ServerRegistry =  sidl sidl_rmi_ServerRegistry_type            \
  sidl_RuntimeException_type sidl_ClassInfo_type sidl_BaseClass_type          \
  sidl_rmi_ServerInfo_type sidl_BaseInterface_type sidl_io_Serializable_type  \
  sidl_BaseException_type sidl_rmi_Call_type sidl_rmi_Return_type             \
  sidl_rmi_Ticket_type sidl_array_type
sidl_rmi_ServerRegistry$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_ServerRegistry))

_deps_sidl_rmi_ServerRegistry_type =  sidl
sidl_rmi_ServerRegistry_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_ServerRegistry_type))

_deps_sidl_rmi_Ticket =  sidl sidl_rmi_Ticket_type sidl_RuntimeException_type \
  sidl_rmi_Response_type sidl_ClassInfo_type sidl_rmi_TicketBook_type         \
  sidl_BaseInterface_type sidl_BaseException_type sidl_rmi_Call_type          \
  sidl_rmi_Return_type sidl_array_type
sidl_rmi_Ticket$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_Ticket))

_deps_sidl_rmi_TicketBook =  sidl sidl_rmi_Ticket_type                        \
  sidl_RuntimeException_type sidl_rmi_Response_type sidl_ClassInfo_type       \
  sidl_rmi_TicketBook_type sidl_BaseInterface_type sidl_BaseException_type    \
  sidl_rmi_Call_type sidl_rmi_Return_type sidl_array_type
sidl_rmi_TicketBook$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_TicketBook))

_deps_sidl_rmi_TicketBook_type =  sidl
sidl_rmi_TicketBook_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_TicketBook_type))

_deps_sidl_rmi_Ticket_type =  sidl
sidl_rmi_Ticket_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_Ticket_type))

_deps_sidl_rmi_TimeOutException =  sidl sidl_io_IOException_type              \
  sidl_io_Deserializer_type sidl_io_Serializer_type                           \
  sidl_RuntimeException_type sidl_rmi_TimeOutException_type                   \
  sidl_ClassInfo_type sidl_BaseClass_type sidl_BaseInterface_type             \
  sidl_io_Serializable_type sidl_SIDLException_type sidl_BaseException_type   \
  sidl_rmi_NetworkException_type sidl_rmi_Call_type sidl_rmi_Return_type      \
  sidl_rmi_Ticket_type sidl_array_type
sidl_rmi_TimeOutException$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_TimeOutException))

_deps_sidl_rmi_TimeOutException_type =  sidl
sidl_rmi_TimeOutException_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_TimeOutException_type))

_deps_sidl_rmi_UnexpectedCloseException =  sidl sidl_io_IOException_type      \
  sidl_io_Deserializer_type sidl_io_Serializer_type                           \
  sidl_RuntimeException_type sidl_ClassInfo_type sidl_BaseClass_type          \
  sidl_BaseInterface_type sidl_io_Serializable_type sidl_SIDLException_type   \
  sidl_rmi_UnexpectedCloseException_type sidl_BaseException_type              \
  sidl_rmi_NetworkException_type sidl_rmi_Call_type sidl_rmi_Return_type      \
  sidl_rmi_Ticket_type sidl_array_type
sidl_rmi_UnexpectedCloseException$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_UnexpectedCloseException))

_deps_sidl_rmi_UnexpectedCloseException_type =  sidl
sidl_rmi_UnexpectedCloseException_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_UnexpectedCloseException_type))

_deps_sidl_rmi_UnknownHostException =  sidl sidl_io_IOException_type          \
  sidl_io_Deserializer_type sidl_io_Serializer_type                           \
  sidl_RuntimeException_type sidl_rmi_UnknownHostException_type               \
  sidl_ClassInfo_type sidl_BaseClass_type sidl_BaseInterface_type             \
  sidl_io_Serializable_type sidl_SIDLException_type sidl_BaseException_type   \
  sidl_rmi_NetworkException_type sidl_rmi_Call_type sidl_rmi_Return_type      \
  sidl_rmi_Ticket_type sidl_array_type
sidl_rmi_UnknownHostException$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_UnknownHostException))

_deps_sidl_rmi_UnknownHostException_type =  sidl
sidl_rmi_UnknownHostException_type$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sidl_rmi_UnknownHostException_type))

