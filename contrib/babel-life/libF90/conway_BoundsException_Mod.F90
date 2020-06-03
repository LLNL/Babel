! 
! File:          conway_BoundsException_Mod.F90
! Symbol:        conway.BoundsException-v2.0
! Symbol Type:   class
! Babel Version: 0.9.1
! sidl Created:  20040510 21:37:47 PDT
! Generated:     20040510 21:37:52 PDT
! Description:   Server-side private data module for conway.BoundsException
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! babel-version = 0.9.1
! source-line   = 22
! source-url    = file:/home/kumfert/babel-life/libF90/../life.sidl
! 

#include"conway_BoundsException_fAbbrev.h"
module conway_BoundsException_impl

! DO-NOT-DELETE splicer.begin(conway.BoundsException.use)
! Insert use statements here...
! DO-NOT-DELETE splicer.end(conway.BoundsException.use)

type conway_BoundsException_private
  sequence
  ! DO-NOT-DELETE splicer.begin(conway.BoundsException.private_data)
   integer :: place_holder ! replace with your private data
  ! DO-NOT-DELETE splicer.end(conway.BoundsException.private_data)
end type conway_BoundsException_private

type conway_BoundsException_wrap
  sequence
  type(conway_BoundsException_private), pointer :: d_private_data
end type conway_BoundsException_wrap

end module conway_BoundsException_impl
