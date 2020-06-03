
#ifndef included_gov_cca_CCAExceptionType_hxx
#include "gov_cca_CCAExceptionType.hxx"
#endif

#ifndef included_gov_cca_CCAException_hxx
#include "gov_cca_CCAException.hxx"
#endif

#ifndef included_decaf_CCAException_hxx
#include "decaf_CCAException.hxx"
#endif


// e.g.  THROW_CCA_EXCEPTION( gov::cca::Nonstandard, "Unable to get properties", "decaf::Framework_impl::getComponentProperties()")
#define THROW_CCA_EXCEPTION( exception_type, exception_msg ) {decaf::CCAException dex = decaf::CCAException::_create();\
                                            dex.setCCAExceptionType( exception_type ); \
                                            gov::cca::CCAException ex = dex; \
                                            ex.setNote( exception_msg ); \
                                            throw ex; }

#define CCA_ASSERT( logical_predicate ) {if ( !(logical_predicate) ) { THROW_CCA_EXCEPTION( gov::cca::CCAExceptionType_Nonstandard, #logical_predicate ) } }
