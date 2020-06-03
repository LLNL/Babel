!
!     File:       HelloDriver.F90
!     Copyright:  (c) 2003 Lawrence Livermore National Security, LLC
!     Revision:   @(#) $Revision: 6171 $
!     Date:       $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
!     Description:Simple CCA Hello World F90 client
!
!
#include "decaf_Framework_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_SIDLException_fAbbrev.h"
#include "gov_cca_TypeMap_fAbbrev.h"
#include "gov_cca_Port_fAbbrev.h"
#include "gov_cca_ports_GoPort_fAbbrev.h"
#include "gov_cca_ComponentID_fAbbrev.h"
#include "gov_cca_ConnectionID_fAbbrev.h"

program HelloDriver
  use sidl
  use decaf_Framework
  use sidl_BaseInterface
  use sidl_SIDLException
  use gov_cca_TypeMap
  use gov_cca_Port
  use gov_cca_ports_GoPort
  use gov_cca_ComponentID
  use gov_cca_ConnectionID
  
  type(decaf_Framework_t) :: decaf
  type(gov_cca_ComponentID_t) :: server, client
  type(gov_cca_Port_t) :: port
  type(gov_cca_ports_GoPort_t) :: goPort
  integer(kind=sidl_int) :: retval
  type(sidl_SIDLException_t) :: sExcept
  type(sidl_BaseInterface_t) :: iExcept, throwaway_exception
  type(gov_cca_TypeMap_t) :: properties
  type(gov_cca_ConnectionID_t) :: connectionID

  call new(decaf, throwaway_exception)

  call createTypeMap( decaf, properties, iExcept )
  call cast(iExcept, sExcept, throwaway_exception)
  if (not_null(sExcept)) then
     write(*,*) 'createTypeMap threw an exception'
     call deleteRef(sExcept, throwaway_exception)
     stop
  end if

  call createInstance(decaf, 'HelloServerInstance', 'HelloServer.Component', &
       properties, server, iExcept)
  call cast(iExcept, sExcept, throwaway_exception)
  if (not_null(sExcept)) then
     write(*,*) 'createInstance(server) threw an exception'
     call deleteRef(sExcept, throwaway_exception)
     stop
  end if
  
  call createInstance(decaf, 'HelloClientInstance', 'HelloClient.Component', &
       properties,  client, iExcept)
  call cast(iExcept, sExcept, throwaway_exception)
  if (not_null(sExcept)) then
     write(*,*) 'createInstance(client) threw an exception'
     call deleteRef(sExcept, throwaway_exception)
     stop
  end if
  call deleteRef(properties, throwaway_exception)
  
  call connect(decaf, client, 'HelloServer', &
       server, 'HelloServer', connectionID, iExcept)
  call cast(iExcept, sExcept, throwaway_exception)
  if (not_null(sExcept)) then
     write(*,*) 'connect threw an exception'
     call deleteRef(sExcept, throwaway_exception)
     stop
  end if
  call deleteRef(connectionID, throwaway_exception)

  call lookupPort(decaf, client, 'GoPort', port, throwaway_exception)
  call cast(port, goPort, throwaway_exception)
  call go(goPort, retval, throwaway_exception)
  call deleteRef(goPort, throwaway_exception)
  call deleteRef(port, throwaway_exception)

  call destroyInstance(decaf, server, 0.0, iExcept)
  call destroyInstance(decaf, client, 0.0, iExcept)

! remove remaining references
  call deleteRef(server, throwaway_exception)
  call deleteRef(client, throwaway_exception)
  call deleteRef(decaf, throwaway_exception)
end program HelloDriver
