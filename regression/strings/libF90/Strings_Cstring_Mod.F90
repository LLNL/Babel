! 
! File:          Strings_Cstring_Mod.F90
! Symbol:        Strings.Cstring-v1.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for Strings.Cstring
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "Strings_Cstring_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module Strings_Cstring_impl

  ! DO-NOT-DELETE splicer.begin(Strings.Cstring.use)
! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Strings.Cstring.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type Strings_Cstring_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(Strings.Cstring.private_data)
  integer :: place_holder
    ! DO-NOT-DELETE splicer.end(Strings.Cstring.private_data)
  end type Strings_Cstring_priv

  type Strings_Cstring_wrap
    sequence
    type(Strings_Cstring_priv), pointer :: d_private_data
  end type Strings_Cstring_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use Strings_Cstring
    use sidl_BaseInterface
    implicit none
    ! out Strings_Cstring retval
    type(Strings_Cstring_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in Strings_Cstring_wrap private_data
    type(Strings_Cstring_wrap), intent(in) :: private_data
    external Strings_Cstring_wrapObj_m
    call Strings_Cstring_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module Strings_Cstring_impl
