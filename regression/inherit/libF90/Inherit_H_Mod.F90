! 
! File:          Inherit_H_Mod.F90
! Symbol:        Inherit.H-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for Inherit.H
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "Inherit_H_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module Inherit_H_impl

  ! DO-NOT-DELETE splicer.begin(Inherit.H.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Inherit.H.use)

  type Inherit_H_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(Inherit.H.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(Inherit.H.private_data)
  end type Inherit_H_priv

  type Inherit_H_wrap
    sequence
    type(Inherit_H_priv), pointer :: d_private_data
  end type Inherit_H_wrap

end module Inherit_H_impl
