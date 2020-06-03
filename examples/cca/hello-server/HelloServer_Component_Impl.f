C       
C       File:          HelloServer_Component_Impl.f
C       Symbol:        HelloServer.Component-v0.6
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7399M trunk)
C       Description:   Server-side implementation for HelloServer.Component
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "HelloServer.Component" (version 0.6)
C       
C       The component implements a StringProducerPort (which generates "Hello World");
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine HelloServer_Component__ctor_fi(self, exception)
        implicit none
C        in HelloServer.Component self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(HelloServer.Component._ctor)
        exception = 0
C       DO-NOT-DELETE splicer.end(HelloServer.Component._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine HelloServer_Component__ctor2_fi(self, private_data, 
     &     exception)
        implicit none
C        in HelloServer.Component self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(HelloServer.Component._ctor2)
        exception = 0
C       DO-NOT-DELETE splicer.end(HelloServer.Component._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine HelloServer_Component__dtor_fi(self, exception)
        implicit none
C        in HelloServer.Component self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(HelloServer.Component._dtor)
        exception = 0
C       DO-NOT-DELETE splicer.end(HelloServer.Component._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine HelloServer_Component__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(HelloServer.Component._load)
        exception = 0
C       DO-NOT-DELETE splicer.end(HelloServer.Component._load)
        end


C       
C       Method:  get[]
C       

        subroutine HelloServer_Component_get_fi(self, retval, exception)
        implicit none
C        in HelloServer.Component self
        integer*8 :: self
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(HelloServer.Component.get)
        retval = 'Hello World!'
        exception = 0
C       DO-NOT-DELETE splicer.end(HelloServer.Component.get)
        end


C       
C       Method:  send[]
C       

        subroutine HelloServer_Component_send_fi(self, data, exception)
        implicit none
C        in HelloServer.Component self
        integer*8 :: self
C        out array<int,2,column-major> data
        integer*8 :: data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(HelloServer.Component.send)
        exception = 0
C       DO-NOT-DELETE splicer.end(HelloServer.Component.send)
        end


C       
C       Method:  setServices[]
C        Starts up a component presence in the calling framework.
C       @param services the component instance's handle on the framework world.
C       Contracts concerning Svc and setServices:
C       
C       The component interaction with the CCA framework
C       and Ports begins on the call to setServices by the framework.
C       
C       This function is called exactly once for each instance created
C       by the framework.
C       
C       The argument Svc will never be nil/null.
C       
C       Those uses ports which are automatically connected by the framework
C       (so-called service-ports) may be obtained via getPort during
C       setServices.
C       

        subroutine HelloServer_Component_setServices_fi(self, services, 
     &     exception)
        implicit none
C        in HelloServer.Component self
        integer*8 :: self
C        in gov.cca.Services services
        integer*8 :: services
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(HelloServer.Component.setServices)
        integer*8 port
        integer*8 properties
        integer*8 myexcept

        exception = 0
        call gov_cca_Port__cast_f(self, port, myexcept)
        call catch( myexcept )
        call gov_cca_Services_createTypeMap_f( services, 
     &     properties, 
     &     myexcept )
        call catch( myexcept )
        call gov_cca_Services_addProvidesPort_f(
     &     services, 
     &     port, 
     &     'HelloServer',
     &     'strop.StringProducerPort',
     &     properties, 
     &     myexcept)
        call catch( myexcept )
        call gov_cca_Port_deleteRef_f(port, myexcept)
        call catch( myexcept )
        call gov_cca_TypeMap_deleteRef_f(properties, myexcept)
        call catch( myexcept )
C       DO-NOT-DELETE splicer.end(HelloServer.Component.setServices)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
      subroutine catch( myexcept )
      implicit none
      integer*8 myexcept
      integer*8 asbe, ignored
      character*(512) msg
      character*(512) trace
      if ( myexcept .eq. 0 ) then
         return
      else
         call sidl_BaseException__cast_f( myexcept, asbe, ignored)
         call sidl_BaseException_getNote_f( asbe, msg, ignored )
         print *, 'Exception Caught', msg
         call sidl_BaseException_getTrace_f( asbe, trace, ignored )
         print *, trace
         call sidl_BaseException_deleteRef_f( asbe, ignored)
         call sidl_BaseInterface_deleteRef_f( myexcept, ignored)
         myexcept = 0
      endif
      end
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
