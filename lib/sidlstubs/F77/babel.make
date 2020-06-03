STUBDOCS = sidl_BaseClass.fif sidl_BaseException.fif sidl_BaseInterface.fif   \
  sidl_CastException.fif sidl_ClassInfo.fif sidl_ClassInfoI.fif               \
  sidl_DFinder.fif sidl_DLL.fif sidl_EnfPolicy.fif sidl_Finder.fif            \
  sidl_InvViolation.fif sidl_LangSpecificException.fif sidl_Loader.fif        \
  sidl_MemAllocException.fif sidl_NotImplementedException.fif                 \
  sidl_PostViolation.fif sidl_PreViolation.fif sidl_RuntimeException.fif      \
  sidl_SIDLException.fif sidl_io_Deserializer.fif sidl_io_IOException.fif     \
  sidl_io_Serializable.fif sidl_io_Serializer.fif sidl_rmi_BindException.fif  \
  sidl_rmi_Call.fif sidl_rmi_ConnectException.fif                             \
  sidl_rmi_ConnectRegistry.fif sidl_rmi_InstanceHandle.fif                    \
  sidl_rmi_InstanceRegistry.fif sidl_rmi_Invocation.fif                       \
  sidl_rmi_MalformedURLException.fif sidl_rmi_NetworkException.fif            \
  sidl_rmi_NoRouteToHostException.fif sidl_rmi_NoServerException.fif          \
  sidl_rmi_ObjectDoesNotExistException.fif sidl_rmi_ProtocolException.fif     \
  sidl_rmi_ProtocolFactory.fif sidl_rmi_Response.fif sidl_rmi_Return.fif      \
  sidl_rmi_ServerInfo.fif sidl_rmi_ServerRegistry.fif sidl_rmi_Ticket.fif     \
  sidl_rmi_TicketBook.fif sidl_rmi_TimeOutException.fif                       \
  sidl_rmi_UnexpectedCloseException.fif sidl_rmi_UnknownHostException.fif
STUBFORTRANINC = sidl.inc sidl_ClauseType.inc sidl_ContractClass.inc          \
  sidl_EnfTraceLevel.inc sidl_EnforceFreq.inc sidl_Resolve.inc sidl_Scope.inc
STUBHDRS = sidl_BaseClass_fStub.h sidl_BaseException_fStub.h                  \
  sidl_BaseInterface_fStub.h sidl_CastException_fStub.h                       \
  sidl_ClassInfoI_fStub.h sidl_ClassInfo_fStub.h sidl_DFinder_fStub.h         \
  sidl_DLL_fStub.h sidl_EnfPolicy_fStub.h sidl_Finder_fStub.h                 \
  sidl_InvViolation_fStub.h sidl_LangSpecificException_fStub.h                \
  sidl_Loader_fStub.h sidl_MemAllocException_fStub.h                          \
  sidl_NotImplementedException_fStub.h sidl_PostViolation_fStub.h             \
  sidl_PreViolation_fStub.h sidl_RuntimeException_fStub.h                     \
  sidl_SIDLException_fStub.h sidl_io_Deserializer_fStub.h                     \
  sidl_io_IOException_fStub.h sidl_io_Serializable_fStub.h                    \
  sidl_io_Serializer_fStub.h sidl_rmi_BindException_fStub.h                   \
  sidl_rmi_Call_fStub.h sidl_rmi_ConnectException_fStub.h                     \
  sidl_rmi_ConnectRegistry_fStub.h sidl_rmi_InstanceHandle_fStub.h            \
  sidl_rmi_InstanceRegistry_fStub.h sidl_rmi_Invocation_fStub.h               \
  sidl_rmi_MalformedURLException_fStub.h sidl_rmi_NetworkException_fStub.h    \
  sidl_rmi_NoRouteToHostException_fStub.h sidl_rmi_NoServerException_fStub.h  \
  sidl_rmi_ObjectDoesNotExistException_fStub.h                                \
  sidl_rmi_ProtocolException_fStub.h sidl_rmi_ProtocolFactory_fStub.h         \
  sidl_rmi_Response_fStub.h sidl_rmi_Return_fStub.h                           \
  sidl_rmi_ServerInfo_fStub.h sidl_rmi_ServerRegistry_fStub.h                 \
  sidl_rmi_TicketBook_fStub.h sidl_rmi_Ticket_fStub.h                         \
  sidl_rmi_TimeOutException_fStub.h sidl_rmi_UnexpectedCloseException_fStub.h \
  sidl_rmi_UnknownHostException_fStub.h
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
