! 
! File:          conway_BoundsException_Impl.F90
! Symbol:        conway.BoundsException-v2.0
! Symbol Type:   class
! Babel Version: 0.9.1
! sidl Created:  20040510 21:37:47 PDT
! Generated:     20040510 21:37:52 PDT
! Description:   Server-side implementation for conway.BoundsException
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! babel-version = 0.9.1
! source-line   = 22
! source-url    = file:/home/kumfert/babel-life/libF90/../life.sidl
! 


! 
! Symbol "conway.BoundsException" (version 2.0)
! 
! This can be thrown when someone tries to access
! a grid beyond its normal bounds.
! 


#include "sidl_BaseException_fAbbrev.h"
#include "conway_BoundsException_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_SIDLException_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)




! 
! Class constructor called when the class is created.
! 

recursive subroutine conway_BoundsException__ctor_mi(self)
  use conway_BoundsException
  use conway_BoundsException_impl
  ! DO-NOT-DELETE splicer.begin(conway.BoundsException._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(conway.BoundsException._ctor.use)
  implicit none
  type(conway_BoundsException_t) :: self ! in

! DO-NOT-DELETE splicer.begin(conway.BoundsException._ctor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(conway.BoundsException._ctor)
end subroutine conway_BoundsException__ctor_mi


! 
! Class destructor called when the class is deleted.
! 

recursive subroutine conway_BoundsException__dtor_mi(self)
  use conway_BoundsException
  use conway_BoundsException_impl
  ! DO-NOT-DELETE splicer.begin(conway.BoundsException._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(conway.BoundsException._dtor.use)
  implicit none
  type(conway_BoundsException_t) :: self ! in

! DO-NOT-DELETE splicer.begin(conway.BoundsException._dtor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(conway.BoundsException._dtor)
end subroutine conway_BoundsException__dtor_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
