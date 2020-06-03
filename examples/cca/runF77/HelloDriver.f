c
c     File:       HelloDriver.f
c     Copyright:  (c) 2001 Lawrence Livermore National Security, LLC
c     Revision:   @(#) $Revision: 6171 $
c     Date:       $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
c     Description:Simple CCA Hello World F77 client
c
c
      program HelloDriver
      integer *8 decaf
      integer *8 server
      integer *8 client 
      integer *8 port
      integer *8 go
      integer *4 retval
      integer *8 ex
      integer *8 properties
      integer *8 connectionID

      call decaf_Framework__create_f(decaf, ex)

      call decaf_Framework_createTypemap_f( decaf, properties, ex )

      call decaf_Framework_createInstance_f(
     &  decaf,
     &  'HelloServerInstance',
     &  'HelloServer.Component',
     &  properties,
     &  server,
     &  ex)
      
      call decaf_Framework_createInstance_f(
     &  decaf,
     &  'HelloClientInstance',
     &  'HelloClient.Component',
     &  properties,
     &  client,
     &  ex)

      call gov_cca_TypeMap_deleteRef_f(properties, ex)

      call decaf_Framework_connect_f(
     &  decaf,
     &  client,
     &  'HelloServer',
     &  server,
     &  'HelloServer',
     &  connectionID,
     &  ex )
      call gov_cca_ConnectionID_deleteRef_f(connectionID, ex)

      call decaf_Framework_lookupPort_f(decaf, client, 'GoPort',
     $     port, ex)
      call gov_cca_ports_GoPort__cast_f(port, go, ex)
      call gov_cca_ports_GoPort_go_f(go, retval, ex)
      call gov_cca_ports_GoPort_deleteRef_f(go, ex)
      call gov_cca_Port_deleteRef_f(port, ex)

      call decaf_Framework_destroyInstance_f(decaf, server, 0.0, ex)
      call decaf_Framework_destroyInstance_f(decaf, client, 0.0, ex)

      call gov_cca_ComponentID_deleteRef_f(server, ex)
      call gov_cca_ComponentID_deleteRef_f(client, ex)
      call decaf_Framework_deleteRef_f(decaf, ex)

      end
