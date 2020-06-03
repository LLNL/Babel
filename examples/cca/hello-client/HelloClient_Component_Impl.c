/*
 * File:          HelloClient_Component_Impl.c
 * Symbol:        HelloClient.Component-v0.5
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7399M trunk)
 * Description:   Server-side implementation for HelloClient.Component
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "HelloClient.Component" (version 0.5)
 * 
 * The component uses the hello port and provides a go port.
 */

#include "HelloClient_Component_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(HelloClient.Component._includes) */
#include <stdio.h>
#include "strop_StringProducerPort.h"
#include "sidl_String.h"
#include "sidl_string_IOR.h"
#include "sidl_Exception.h"
/* DO-NOT-DELETE splicer.end(HelloClient.Component._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_HelloClient_Component__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_HelloClient_Component__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(HelloClient.Component._load) */
  /* Insert the implementation of the static class initializer method here... */
    /* DO-NOT-DELETE splicer.end(HelloClient.Component._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_HelloClient_Component__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_HelloClient_Component__ctor(
  /* in */ HelloClient_Component self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(HelloClient.Component._ctor) */
  struct HelloClient_Component__data* data =
    (struct HelloClient_Component__data*) malloc(
      sizeof(struct HelloClient_Component__data));

  data->services = NULL;

  HelloClient_Component__set_data(self, data);
    /* DO-NOT-DELETE splicer.end(HelloClient.Component._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_HelloClient_Component__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_HelloClient_Component__ctor2(
  /* in */ HelloClient_Component self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(HelloClient.Component._ctor2) */
  /* Insert-Code-Here {HelloClient.Component._ctor2} (special constructor method) */
    /* DO-NOT-DELETE splicer.end(HelloClient.Component._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_HelloClient_Component__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_HelloClient_Component__dtor(
  /* in */ HelloClient_Component self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(HelloClient.Component._dtor) */
  struct HelloClient_Component__data* data =
    HelloClient_Component__get_data(self);

  if (data->services != NULL) {
    gov_cca_Services_deleteRef(data->services, _ex);
  }

  free((void*) data);
  HelloClient_Component__set_data(self, NULL);
    /* DO-NOT-DELETE splicer.end(HelloClient.Component._dtor) */
  }
}

/*
 * The following method starts the component.
 */

#undef __FUNC__
#define __FUNC__ "impl_HelloClient_Component_go"

#ifdef __cplusplus
extern "C"
#endif
int32_t
impl_HelloClient_Component_go(
  /* in */ HelloClient_Component self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(HelloClient.Component.go) */
  struct HelloClient_Component__data* data =
    HelloClient_Component__get_data(self);

  gov_cca_Port port = gov_cca_Services_getPort(data->services, "HelloServer", _ex);SIDL_CHECK(*_ex);
  {
    strop_StringProducerPort hello = strop_StringProducerPort__cast(port, _ex); SIDL_CHECK(*_ex);
    {
      char* saying = strop_StringProducerPort_get(hello,_ex);SIDL_CHECK(*_ex);
      printf("%s\n", saying);
      sidl_String_free(saying);

      gov_cca_Services_releasePort(data->services, "HelloServer", _ex);SIDL_CHECK(*_ex);
      strop_StringProducerPort_deleteRef(hello, _ex);SIDL_CHECK(*_ex);
      gov_cca_Port_deleteRef(port, _ex);SIDL_CHECK(*_ex);
    }
  }

  return 0;
 EXIT:
  return -1;
    /* DO-NOT-DELETE splicer.end(HelloClient.Component.go) */
  }
}

/*
 * Method <code>setServices</code> is called by the framework.
 */

#undef __FUNC__
#define __FUNC__ "impl_HelloClient_Component_setServices"

#ifdef __cplusplus
extern "C"
#endif
void
impl_HelloClient_Component_setServices(
  /* in */ HelloClient_Component self,
  /* in */ gov_cca_Services services,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(HelloClient.Component.setServices) */
  struct HelloClient_Component__data* data = HelloClient_Component__get_data(self);
  sidl_BaseInterface ex;
  gov_cca_Port gp;

  gov_cca_Services_registerUsesPort(services, "HelloServer", "HelloServer.HelloPort", 0, &ex );

  gp = gov_cca_Port__cast(self, _ex);SIDL_CHECK(*_ex);
  gov_cca_Services_addProvidesPort( services, gp, "GoPort", "gov.cca.ports.GoPort", 0, &ex );
  gov_cca_Port_deleteRef(gp, _ex);SIDL_CHECK(*_ex);

  data->services = services;
  gov_cca_Services_addRef(services, _ex);SIDL_CHECK(*_ex);
 EXIT:;
    /* DO-NOT-DELETE splicer.end(HelloClient.Component.setServices) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

