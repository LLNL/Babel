// 
// File:          decaf_Framework_Impl.hxx
// Symbol:        decaf.Framework-v0.8.2
// Symbol Type:   class
// Babel Version: 2.0.0 (Revision: 7399M trunk)
// Description:   Server-side implementation for decaf.Framework
// 
// WARNING: Automatically generated; only changes within splicers preserved
// 
// 

#ifndef included_decaf_Framework_Impl_hxx
#define included_decaf_Framework_Impl_hxx

#ifndef included_sidl_cxx_hxx
#include "sidl_cxx.hxx"
#endif
#ifndef included_decaf_Framework_IOR_h
#include "decaf_Framework_IOR.h"
#endif
#ifndef included_decaf_Framework_hxx
#include "decaf_Framework.hxx"
#endif
#ifndef included_gov_cca_AbstractFramework_hxx
#include "gov_cca_AbstractFramework.hxx"
#endif
#ifndef included_gov_cca_CCAException_hxx
#include "gov_cca_CCAException.hxx"
#endif
#ifndef included_gov_cca_ComponentID_hxx
#include "gov_cca_ComponentID.hxx"
#endif
#ifndef included_gov_cca_ComponentRelease_hxx
#include "gov_cca_ComponentRelease.hxx"
#endif
#ifndef included_gov_cca_ConnectionID_hxx
#include "gov_cca_ConnectionID.hxx"
#endif
#ifndef included_gov_cca_Port_hxx
#include "gov_cca_Port.hxx"
#endif
#ifndef included_gov_cca_Services_hxx
#include "gov_cca_Services.hxx"
#endif
#ifndef included_gov_cca_TypeMap_hxx
#include "gov_cca_TypeMap.hxx"
#endif
#ifndef included_gov_cca_ports_BuilderService_hxx
#include "gov_cca_ports_BuilderService.hxx"
#endif
#ifndef included_gov_cca_ports_ServiceProvider_hxx
#include "gov_cca_ports_ServiceProvider.hxx"
#endif
#ifndef included_sidl_BaseClass_hxx
#include "sidl_BaseClass.hxx"
#endif
#ifndef included_sidl_BaseInterface_hxx
#include "sidl_BaseInterface.hxx"
#endif
#ifndef included_sidl_ClassInfo_hxx
#include "sidl_ClassInfo.hxx"
#endif
#ifndef included_sidl_RuntimeException_hxx
#include "sidl_RuntimeException.hxx"
#endif


// DO-NOT-DELETE splicer.begin(decaf.Framework._hincludes)
#include <map>
#include <set>
#include <iostream>
//#include <utility> // for pair<,> and make_pair
#include "gov_cca_Component.hxx"
#include "decaf_Services.hxx"
#include "decaf_ConnectionID.hxx"

namespace decaf {
  class Framework_impl; // forward declaration

  std::ostream& operator<<( std::ostream& os, Framework_impl& fwk );
}
// DO-NOT-DELETE splicer.end(decaf.Framework._hincludes)

namespace decaf { 

  /**
   * Symbol "decaf.Framework" (version 0.8.2)
   */
  class Framework_impl : public virtual ::decaf::Framework 
  // DO-NOT-DELETE splicer.begin(decaf.Framework._inherits)
  // Put additional inheritance here...
  // DO-NOT-DELETE splicer.end(decaf.Framework._inherits)

  {

  // All data marked protected will be accessable by 
  // descendant Impl classes
  protected:

    bool _wrapped;

    // DO-NOT-DELETE splicer.begin(decaf.Framework._implementation)
  public:
    struct cid_compare { 
      bool operator () ( const gov::cca::ConnectionID& A, const gov::cca::ConnectionID& B ) const { 
	gov::cca::ConnectionID& a = const_cast<gov::cca::ConnectionID&>( A );
	gov::cca::ConnectionID& b = const_cast<gov::cca::ConnectionID&>( B );
	if (a.isSame(b) ) { return 0;}
	std::string s1 = a.getUser().getInstanceName();
	std::string s2 = b.getUser().getInstanceName();
	if (s1<s2) { return -1; }
	if (s1>s2) { return 1; }
	s1 = a.getUserPortName();
	s2 = b.getUserPortName();
	if (s1<s2) { return -1; }
	if (s1>s2) { return 1; }
	s1 = a.getProvider().getInstanceName();
	s2 = b.getProvider().getInstanceName();
	if (s1<s2) { return -1; }
	if (s1>s2) { return 1; }
	s1 = a.getProviderPortName();
	s2 = b.getProviderPortName();
	if (s1<s2) { return -1; }
	if (s1>s2) { return 1; }
	return 0;
      }
    };
    typedef std::map< std::string, gov::cca::ConnectionID > cidmap_t;
    typedef std::set< gov::cca::ConnectionID, cid_compare >  cidset_t;
    typedef std::map< std::string, cidset_t > cidmapset_t;
    // Service Provider map
    struct ProviderEntry {
      gov::cca::ComponentID            d_id;
      gov::cca::ports::ServiceProvider d_provider;
    };
    typedef std::map< std::string, ProviderEntry > spmap_t;
    // Service single map
    typedef std::map< std::string, gov::cca::Port > ssmap_t;

    typedef struct { 
      gov::cca::Component component;
      gov::cca::ComponentRelease release;
      gov::cca::Services svcs;
      cidmap_t usesConnection;
      cidmapset_t providesConnection;      
    } instance_t; 
    
    typedef std::map< std::string, instance_t > instancemap_t;
    instancemap_t d_instance;
    spmap_t d_serviceProviders;
    ssmap_t d_servicePorts;

    typedef std::map< std::string, std::string > aliasMap_t;
    aliasMap_t d_aliases;
    // the aliases that the framework uses to masquerade as a component
    // give the unique name, it returns the "type" (having nothing to do with
    // a component, per se) 

  protected:
    std::string p_getUniqueName( const std::string& requestedName ) ;

    int p_removeInstance( const std::string& instanceName );

    // debugging aid
    friend std::ostream& operator<<( std::ostream& os, Framework_impl& fwk );

    // DO-NOT-DELETE splicer.end(decaf.Framework._implementation)

  public:
    // default constructor, used for data wrapping(required)
    Framework_impl();
    // sidl constructor (required)
    // Note: alternate Skel constructor doesn't call addref()
    // (fixes bug #275)
      Framework_impl( struct decaf_Framework__object * ior ) : StubBase(ior,
        true), 
      ::gov::cca::AbstractFramework((ior==NULL) ? NULL : &((
        *ior).d_gov_cca_abstractframework)),
      ::gov::cca::Port((ior==NULL) ? NULL : &((*ior).d_gov_cca_port)),
    ::gov::cca::ports::BuilderService((ior==NULL) ? NULL : &((
      *ior).d_gov_cca_ports_builderservice)) , _wrapped(false) {
      ior->d_data = this;
      _ctor();
    }


    // user defined construction
    void _ctor();

    // virtual destructor (required)
    virtual ~Framework_impl() { _dtor(); }

    // user defined destruction
    void _dtor();

    // true if this object was created by a user newing the impl
    inline bool _isWrapped() {return _wrapped;}

    // static class initializer
    static void _load();

  public:


    /**
     * Return the named port from the specified component id.
     */
    ::gov::cca::Port
    lookupPort_impl (
      /* in */::gov::cca::ComponentID& componentID,
      /* in */const ::std::string& portName
    )
    ;


    /**
     * Check if this type is a framework supported service.
     */
    bool
    isProvidedService_impl (
      /* in */const ::std::string& portType
    )
    ;


    /**
     * Framework creates providing component, registers provides port and connects to
     * using port for special cases (e.g. BuilderService)
     * @param type the string name of the port type 
     * (currently accepts only "gov.cca.ports.BuilderServices" and 
     * gov.cca.ports.ConnectionEventServices)
     * @param componentID the ID of the user component
     * @param portName the userPortName on the user component
     */
    void
    provideRequestedServices_impl (
      /* in */::gov::cca::ComponentID& componentID,
      /* in */const ::std::string& portName,
      /* in */const ::std::string& type
    )
    ;

    /**
     * user defined non-static method.
     */
    void
    addServiceProvider_impl (
      /* in */const ::std::string& portType,
      /* in */::gov::cca::ComponentID& componentID,
      /* in */::gov::cca::ports::ServiceProvider& provider
    )
    ;

    /**
     * user defined non-static method.
     */
    void
    addServicePort_impl (
      /* in */const ::std::string& portType,
      /* in */::gov::cca::Port& port
    )
    ;

    /**
     * user defined non-static method.
     */
    void
    removeFromRegistry_impl (
      /* in */const ::std::string& portType
    )
    ;

    /**
     * user defined non-static method.
     */
    void
    setInstanceRelease_impl (
      /* in */::gov::cca::ComponentID& cid,
      /* in */::gov::cca::ComponentRelease& callback
    )
    ;


    /**
     *  
     * Create an empty TypeMap. Presumably this would be used in 
     * an ensuing call to <code>getServices()</code>. The "normal" method of
     * creating typemaps is found in the <code>Services</code> interface. It
     * is duplicated here to break the "chicken and egg" problem.
     */
    ::gov::cca::TypeMap
    createTypeMap_impl() // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;

    /**
     *  
     * Retrieve a Services handle to the underlying framework. 
     * This interface effectively causes the calling program to 
     * appear as the image of a component inside the framework.
     * This method may be called any number of times
     * with different arguments, creating a new component image 
     * each time. 
     * The only proper method to destroy a Services obtained 
     * from this interface is to pass it to releaseServices.
     * 
     * @param selfInstanceName the Component instance name,
     * as it will appear in the framework.
     * 
     * @param selfClassName the Component type of the 
     * calling program, as it will appear in the framework. 
     * 
     * @param selfProperties (which can be null) the properties 
     * of the component image to appear. 
     * 
     * @throws CCAException in the event that selfInstanceName 
     * is already in use by another component.
     * 
     * @return  A Services object that pertains to the
     * image of the this component. This is identical
     * to the object passed into Component.setServices() 
     * when a component is created.
     */
    ::gov::cca::Services
    getServices_impl (
      /* in */const ::std::string& selfInstanceName,
      /* in */const ::std::string& selfClassName,
      /* in */::gov::cca::TypeMap& selfProperties
    )
    // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;


    /**
     *  
     * Inform framework that the <code>Services</code> handle is no longer needed by the 
     * caller and that the reference to its component image is to be
     * deleted from the context of the underlying framework. This invalidates
     * any <code>ComponentID</code>'s or <code>ConnectionID</code>'s associated 
     * with the given <code>Services</code>' component image. 
     * 
     * @param services The result of getServices earlier obtained.
     * 
     * @throws CCAException if the <code>Services</code>
     * handle has already been released or is otherwise rendered invalid 
     * or was not obtained from <code>getServices()</code>.
     */
    void
    releaseServices_impl (
      /* in */::gov::cca::Services& services
    )
    // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;


    /**
     *  
     * Tell the framework it is no longer needed and to clean up after itself. 
     * @throws CCAException if the framework has already been shutdown.
     */
    void
    shutdownFramework_impl() // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;

    /**
     *  
     * Creates a new framework instance based on the same underlying 
     * framework implementation. This does not copy the existing 
     * framework, nor are any of the user-instantiated components in
     * the original framework available in the newly created 
     * <code>AbstractFramework</code>. 
     * 
     * @throws CCAException when one of the following conditions occur:
     * 
     * (1)the AbstractFramework previously had shutdownFramework() called on it, or 
     * (2)the underlying framework implementation does not permit creation 
     * of another instance.	 
     */
    ::gov::cca::AbstractFramework
    createEmptyFramework_impl() // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;

    /**
     * Creates an instance of a CCA component of the type defined by the 
     * string className.  The string classname uniquely defines the
     * "type" of the component, e.g.
     * doe.cca.Library.GaussianElmination. 
     * It has an instance name given by the string instanceName.
     * The instanceName may be empty (zero length) in which case
     * the instanceName will be assigned to the component automatically.
     * @throws CCAException If the Component className is unknown, or if the
     * instanceName has already been used, a CCAException is thrown.
     * @return A ComponentID corresponding to the created component. Destroying
     * the returned ID does not destroy the component; 
     * see destroyInstance instead.
     */
    ::gov::cca::ComponentID
    createInstance_impl (
      /* in */const ::std::string& instanceName,
      /* in */const ::std::string& className,
      /* in */::gov::cca::TypeMap& properties
    )
    // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;


    /**
     *  
     * Get component list.
     * @return a ComponentID for each component currently created.
     */
    ::sidl::array< ::gov::cca::ComponentID>
    getComponentIDs_impl() // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;

    /**
     *  
     * Get property map for component.
     * @return the public properties associated with the component referred to by
     * ComponentID. 
     * @throws a CCAException if the ComponentID is invalid.
     */
    ::gov::cca::TypeMap
    getComponentProperties_impl (
      /* in */::gov::cca::ComponentID& cid
    )
    // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;


    /**
     * Causes the framework implementation to associate the given properties 
     * with the component designated by cid. 
     * @throws CCAException if cid is invalid or if there is an attempted
     * change to a property locked by the framework implementation.
     */
    void
    setComponentProperties_impl (
      /* in */::gov::cca::ComponentID& cid,
      /* in */::gov::cca::TypeMap& map
    )
    // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;


    /**
     *  Get component id from stringified reference.
     * @return a ComponentID from the string produced by 
     * ComponentID.getSerialization(). 
     * @throws CCAException if the string does not represent the appropriate 
     * serialization of a ComponentID for the underlying framework.
     */
    ::gov::cca::ComponentID
    getDeserialization_impl (
      /* in */const ::std::string& s
    )
    // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;


    /**
     *  Get id from name by which it was created.
     * @return a ComponentID from the instance name of the component
     * produced by ComponentID.getInstanceName().
     * @throws CCAException if there is no component matching the 
     * given componentInstanceName.
     */
    ::gov::cca::ComponentID
    getComponentID_impl (
      /* in */const ::std::string& componentInstanceName
    )
    // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;


    /**
     *  
     * Eliminate the Component instance, from the scope of the framework.
     * @param toDie the component to be removed.
     * @param timeout the allowable wait; 0 means up to the framework.
     * @throws CCAException if toDie refers to an invalid component, or
     * if the operation takes longer than timeout seconds.
     */
    void
    destroyInstance_impl (
      /* in */::gov::cca::ComponentID& toDie,
      /* in */float timeout
    )
    // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;


    /**
     *  
     * Get the names of Port instances provided by the identified component.
     * @param cid the component.
     * @throws CCAException if cid refers to an invalid component.
     */
    ::sidl::array< ::std::string>
    getProvidedPortNames_impl (
      /* in */::gov::cca::ComponentID& cid
    )
    // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;


    /**
     *  
     * Get the names of Port instances used by the identified component.
     * @param cid the component.
     * @throws CCAException if cid refers to an invalid component. 
     */
    ::sidl::array< ::std::string>
    getUsedPortNames_impl (
      /* in */::gov::cca::ComponentID& cid
    )
    // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;


    /**
     *  
     * Fetch map of Port properties exposed by the framework.
     * @return the public properties pertaining to the Port instance 
     * portname on the component referred to by cid. 
     * @throws CCAException when any one of the following conditions occur:<ul>
     * <li>portname is not a registered Port on the component indicated by cid,
     * <li>cid refers to an invalid component. </ul>
     */
    ::gov::cca::TypeMap
    getPortProperties_impl (
      /* in */::gov::cca::ComponentID& cid,
      /* in */const ::std::string& portName
    )
    // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;


    /**
     *  
     * Associates the properties given in map with the Port indicated by 
     * portname. The component must have a Port known by portname.
     * @throws CCAException if either cid or portname are
     * invalid, or if this a changed property is locked by 
     * the underlying framework or component.
     */
    void
    setPortProperties_impl (
      /* in */::gov::cca::ComponentID& cid,
      /* in */const ::std::string& portName,
      /* in */::gov::cca::TypeMap& map
    )
    // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;


    /**
     * Creates a connection between ports on component user and 
     * component provider. Destroying the ConnectionID does not
     * cause a disconnection; for that, see disconnect().
     * @throws CCAException when any one of the following conditions occur:<ul>
     * <li>If either user or provider refer to an invalid component,
     * <li>If either usingPortName or providingPortName refer to a 
     * nonexistent Port on their respective component,
     * <li>If other-- In reality there are a lot of things that can go wrong 
     * with this operation, especially if the underlying connections 
     * involve networking.</ul>
     */
    ::gov::cca::ConnectionID
    connect_impl (
      /* in */::gov::cca::ComponentID& user,
      /* in */const ::std::string& usingPortName,
      /* in */::gov::cca::ComponentID& provider,
      /* in */const ::std::string& providingPortName
    )
    // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;


    /**
     *  Returns a list of connections as an array of 
     * handles. This will return all connections involving components 
     * in the given componentList of ComponentIDs. This
     * means that ConnectionID's will be returned even if only one 
     * of the participants in the connection appears in componentList.
     * 
     * @throws CCAException if any component in componentList is invalid.
     */
    ::sidl::array< ::gov::cca::ConnectionID>
    getConnectionIDs_impl (
      /* in array<gov.cca.ComponentID> */::sidl::array< 
        ::gov::cca::ComponentID>& componentList
    )
    // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;


    /**
     * Fetch property map of a connection.
     * @returns the properties for the given connection.
     * @throws CCAException if connID is invalid.
     */
    ::gov::cca::TypeMap
    getConnectionProperties_impl (
      /* in */::gov::cca::ConnectionID& connID
    )
    // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;


    /**
     *  Associates the properties with the connection.
     * @param map the source of the properties.
     * @param connID connection to receive property values.
     * @throws CCAException if connID is invalid, or if this changes 
     * a property locked by the underlying framework.
     */
    void
    setConnectionProperties_impl (
      /* in */::gov::cca::ConnectionID& connID,
      /* in */::gov::cca::TypeMap& map
    )
    // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;


    /**
     *  Disconnect the connection indicated by connID before the indicated
     * timeout in secs. Upon successful completion, connID and the connection
     * it represents become invalid. 
     * @param timeout the time in seconds to wait for a connection to close; 0
     * means to use the framework implementation default.
     * @param connID the connection to be broken.
     * @throws CCAException when any one of the following conditions occur: <ul>
     * <li>id refers to an invalid ConnectionID,
     * <li>timeout is exceeded, after which, if id was valid before 
     * disconnect() was invoked, it remains valid
     * </ul>
     */
    void
    disconnect_impl (
      /* in */::gov::cca::ConnectionID& connID,
      /* in */float timeout
    )
    // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;


    /**
     *  Remove all connections between components id1 and id2 within 
     * the period of timeout secs. If id2 is null, then all connections 
     * to id1 are removed (within the period of timeout secs).
     * @throws CCAException when any one of the following conditions occur:<ul>
     * <li>id1 or id2 refer to an invalid ComponentID (other than id2 == null),
     * <li>The timeout period is exceeded before the disconnections can be made. 
     * </ul>
     */
    void
    disconnectAll_impl (
      /* in */::gov::cca::ComponentID& id1,
      /* in */::gov::cca::ComponentID& id2,
      /* in */float timeout
    )
    // throws:
    //    ::gov::cca::CCAException
    //    ::sidl::RuntimeException
    ;

  };  // end class Framework_impl

} // end namespace decaf

// DO-NOT-DELETE splicer.begin(decaf.Framework._hmisc)
// DO-NOT-DELETE splicer.end(decaf.Framework._hmisc)

#endif
