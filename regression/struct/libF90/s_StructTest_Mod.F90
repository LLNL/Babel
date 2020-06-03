! 
! File:          s_StructTest_Mod.F90
! Symbol:        s.StructTest-v1.0
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for s.StructTest
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "s_StructTest_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! insert code here (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module s_StructTest_impl

  ! DO-NOT-DELETE splicer.begin(s.StructTest.use)
  ! insert code here (use statements)
  ! DO-NOT-DELETE splicer.end(s.StructTest.use)

  private :: wrapObj_s

  interface wrapObj
  module procedure wrapObj_s
  end interface

  type s_StructTest_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(s.StructTest.private_data)
    ! insert code here (private data members)
    integer :: place_holder ! replace with your private data
    ! DO-NOT-DELETE splicer.end(s.StructTest.private_data)
  end type s_StructTest_priv

  type s_StructTest_wrap
    sequence
    type(s_StructTest_priv), pointer :: d_private_data
  end type s_StructTest_wrap

  contains

  recursive subroutine wrapObj_s(private_data, retval, exception)
    use s_StructTest
    use sidl_BaseInterface
    implicit none
    ! out s_StructTest retval
    type(s_StructTest_t) , intent(out) :: retval
    ! out sidl_BaseInterface exception
    type(sidl_BaseInterface_t) , intent(out) :: exception
    ! in s_StructTest_wrap private_data
    type(s_StructTest_wrap), intent(in) :: private_data
    external s_StructTest_wrapObj_m
    call s_StructTest_wrapObj_m(private_data, retval, exception)
   end subroutine wrapObj_s
end module s_StructTest_impl
