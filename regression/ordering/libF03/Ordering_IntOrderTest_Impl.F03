! 
! File:          Ordering_IntOrderTest_Impl.F03
! Symbol:        Ordering.IntOrderTest-v0.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side implementation for Ordering.IntOrderTest
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 


! 
! Symbol "Ordering.IntOrderTest" (version 0.1)
! 
! This class provides methods to verify that the array ordering
! capabilities work for arrays of int.
! 


#include "Ordering_IntOrderTest_fAbbrev.h"
#include "sidl_RuntimeException_fAbbrev.h"
#include "sidl_ClassInfo_fAbbrev.h"
#include "sidl_BaseClass_fAbbrev.h"
#include "sidl_BaseInterface_fAbbrev.h"
#include "sidl_BaseException_fAbbrev.h"
#include "sidl_int_fAbbrev.h"
! DO-NOT-DELETE splicer.begin(module.include)
! 
! Add additional include statements here
! 

! DO-NOT-DELETE splicer.end(module.include)

module Ordering_IntOrderTest_Impl
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Ordering_IntOrderTest
  use sidl_int_array
  ! DO-NOT-DELETE splicer.begin(module.use)
  ! 
  ! Add use statements here
  ! 

  ! DO-NOT-DELETE splicer.end(module.use)


  type, extends(Ordering_IntOrderTest_t) :: Ordering_IntOrderTest_impl_t
    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.private_data)
    ! insert code here (private data members)
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.private_data)
  end type Ordering_IntOrderTest_impl_t

contains

  subroutine wrap(obj, self, exception)
    type(Ordering_IntOrderTest_impl_t), target :: obj
    type(Ordering_IntOrderTest_t) :: self
    type(sidl_BaseInterface_t) :: exception
    external Ordering_IntOrderTest_wrap
    call Ordering_IntOrderTest_wrap(obj, self, exception)
  end subroutine wrap

  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)

  integer(kind=selected_int_kind(9)) function iFunc(ind, dim)
    use sidl
    implicit none
    integer(kind=sidl_int) :: dim
    integer(kind=sidl_int), dimension(dim), intent(in) :: ind
    integer(kind=sidl_int) :: res, i
    res = 0_sidl_int
    do i=1_sidl_int, dim
       res = res + i * ind(i)
    enddo
    ifunc = res
  end function iFunc

  logical function isIMatrix_1(A)
    use sidl
    use sidl_int_array
    type(sidl_int_1d), intent(in) :: A
    integer(kind=sidl_int), dimension(1) :: ind, low, up
    integer(kind=sidl_int) :: i
    isIMatrix_1 = .true.
    do i = 1_sidl_int, 1_sidl_int
       low(i) = lower(A, i-1_sidl_int)
       up(i) = upper(A, i-1_sidl_int)
    enddo
    do i = low(1), up(1)
       ind(1) = i
       if (iFunc(ind, 1_sidl_int) .ne. A%d_data(i)) then
          isIMatrix_1 = .false.
       endif
    enddo
  end function isIMatrix_1

  logical function isIMatrix_2(A)
    use sidl
    use sidl_int_array
    type(sidl_int_2d), intent(in) :: A
    integer(kind=sidl_int), dimension(2) :: ind, low, up
    integer(kind=sidl_int) :: i, j
    isIMatrix_2 = .true.
    do i = 1_sidl_int, 2_sidl_int
       low(i) = lower(A, i-1_sidl_int)
       up(i) = upper(A, i-1_sidl_int)
    enddo
    do i = low(1), up(1)
       ind(1) = i
       do j = low(2), up(2)
          ind(2) = j
          if (iFunc(ind, 2_sidl_int) .ne. A%d_data(i,j)) then
             isIMatrix_2 = .false.
          endif
       enddo
    enddo
  end function isIMatrix_2

  logical function isIMatrix_4(A)
    use sidl
    use sidl_int_array
    type(sidl_int_4d), intent(in) :: A
    integer(kind=sidl_int), dimension(4) :: ind, low, up
    integer(kind=sidl_int) :: i, j, k, l
    isIMatrix_4 = .true.
    do i = 1_sidl_int, 4_sidl_int
       low(i) = lower(A, i-1_sidl_int)
       up(i) = upper(A, i-1_sidl_int)
    enddo
    do i = low(1), up(1)
       ind(1) = i
       do j = low(2), up(2)
          ind(2) = j
          do  k = low(3), up(3)
             ind(3) = k
             do l = low(4), up(4)
                ind(4) = l
                if (iFunc(ind, 4_sidl_int) .ne. A%d_data(i,j,k,l)) then
                   isIMatrix_4 = .false.
                endif
             enddo
          enddo
       enddo
    enddo
  end function isIMatrix_4

  subroutine fillIMatrix_2(A)
    use sidl
    use sidl_int_array
    type(sidl_int_2d), intent(inout) :: A
    integer(kind=sidl_int), dimension(2) :: ind, low, up
    integer(kind=sidl_int) :: i, j
    do i = 1_sidl_int, 2_sidl_int
       low(i) = lower(A, i-1_sidl_int)
       up(i) = upper(A, i-1_sidl_int)
    enddo
    do i = low(1), up(1)
       ind(1) = i
       do j = low(2), up(2)
          ind(2) = j
          if (mod(i+j,2_sidl_int) .eq. 1_sidl_int) then
             A%d_data(i,j) = iFunc(ind, 2_sidl_int)
          else
             call set(A, i, j, iFunc(ind, 2_sidl_int))
          endif
       enddo
    enddo
  end subroutine fillIMatrix_2

  subroutine fillIMatrix_4(A)
    use sidl
    use sidl_int_array
    type(sidl_int_4d), intent(inout) :: A
    integer(kind=sidl_int), dimension(4) :: ind, low, up
    integer(kind=sidl_int) :: i, j, k, l
    do i = 1_sidl_int, 4_sidl_int
       low(i) = lower(A, i-1_sidl_int)
       up(i) = upper(A, i-1_sidl_int)
    enddo
    do i = low(1), up(1)
       ind(1) = i
       do j = low(2), up(2)
          ind(2) = j
          do  k = low(3), up(3)
             ind(3) = k
             do l = low(4), up(4)
                ind(4) = l
                if (mod(i+j+k+l, 2_sidl_int) .eq. 1_sidl_int) then
                   A%d_data(i,j,k,l) = iFunc(ind, 4_sidl_int)
                else
                   call set(A, ind, iFunc(ind, 4_sidl_int))
                endif
             enddo
          enddo
       enddo
    enddo
  end subroutine fillIMatrix_4

  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



  ! 
  ! Method:  _ctor[]
  ! Class constructor called when the class is created.
  ! 

  subroutine ctor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._ctor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._ctor.use)
    implicit none
    !  in Ordering.IntOrderTest self
    type(Ordering_IntOrderTest_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._ctor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._ctor)
  end subroutine ctor_impl


  ! 
  ! Method:  _dtor[]
  ! Class destructor called when the class is deleted.
  ! 

  subroutine dtor_impl(self, exception)
    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._dtor.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._dtor.use)
    implicit none
    !  in Ordering.IntOrderTest self
    type(Ordering_IntOrderTest_impl_t) :: self
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._dtor)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._dtor)
  end subroutine dtor_impl


  ! 
  ! Method:  _load[]
  ! Static class initializer called exactly once before any user-defined method is dispatched
  ! 

  subroutine load_impl(exception)
    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._load.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._load.use)
    implicit none
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._load)
    ! Insert the implementation here...
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._load)
  end subroutine load_impl


  ! 
  ! Method:  makeColumnIMatrix[]
  ! Create a column-major matrix satisfying condition I.
  ! 

  function makeColumnIMatrix_impl(size, useCreateCol, exception) result(       &
    retval)
    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.makeColumnIMatrix.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.makeColumnIMatrix.use)
    implicit none
    !  in int size
    integer (kind=sidl_int), intent(in) :: size
    !  in bool useCreateCol
    logical, intent(in) :: useCreateCol
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    type(sidl_int_2d) :: retval

    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.makeColumnIMatrix)
    if (useCreateCol) then
       call create2dCol(size, size, retval)
    else
       call create2dRow(size, size, retval)
    endif
    call fillIMatrix_2(retval)
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.makeColumnIMatrix)
  end function makeColumnIMatrix_impl


  ! 
  ! Method:  makeRowIMatrix[]
  ! Create a row-major matrix satisfying condition I.
  ! 

  function makeRowIMatrix_impl(size, useCreateRow, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.makeRowIMatrix.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.makeRowIMatrix.use)
    implicit none
    !  in int size
    integer (kind=sidl_int), intent(in) :: size
    !  in bool useCreateRow
    logical, intent(in) :: useCreateRow
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    type(sidl_int_2d) :: retval

    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.makeRowIMatrix)
    if (useCreateRow) then
       call create2dRow(size, size, retval)
    else
       call create2dCol(size, size, retval)
    endif
    call fillIMatrix_2(retval)
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.makeRowIMatrix)
  end function makeRowIMatrix_impl


  ! 
  ! Method:  makeIMatrix[]
  ! Create a 4-D matrix satisfying condition I.  Each dimension has
  ! size elements numbers 0 through size-1.
  ! 

  function makeIMatrix_impl(size, useCreateColumn, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.makeIMatrix.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.makeIMatrix.use)
    implicit none
    !  in int size
    integer (kind=sidl_int), intent(in) :: size
    !  in bool useCreateColumn
    logical, intent(in) :: useCreateColumn
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    type(sidl_int_4d) :: retval

    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.makeIMatrix)
    integer(kind=sidl_int) low(4), up(4), i
    do i = 1_sidl_int, 4_sidl_int
       low(i) = 0_sidl_int
       up(i) = size - 1_sidl_int
    enddo
    if (useCreateColumn) then
       call createCol(low, up, retval)
    else
       call createRow(low, up, retval)
    endif
    call fillIMatrix_4(retval)
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.makeIMatrix)
  end function makeIMatrix_impl


  ! 
  ! Method:  createColumnIMatrix[]
  ! Create a column-major matrix satisfying condition I.
  ! 

  subroutine createColumnIMatrix_impl(size, useCreateCol, res, exception)
    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.createColumnIMatrix.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.createColumnIMatrix.use)
    implicit none
    !  in int size
    integer (kind=sidl_int), intent(in) :: size
    !  in bool useCreateCol
    logical, intent(in) :: useCreateCol
    !  out array<int,2,column-major> res
    type(sidl_int_2d), intent(out) :: res
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.createColumnIMatrix)
    if (useCreateCol) then
       res = makeColumnIMatrix(size, .true., exception)
    else
       res = makeRowIMatrix(size, .true., exception)
    endif
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.createColumnIMatrix)
  end subroutine createColumnIMatrix_impl


  ! 
  ! Method:  createRowIMatrix[]
  ! Create a row-major matrix satisfying condition I.
  ! 

  subroutine createRowIMatrix_impl(size, useCreateRow, res, exception)
    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.createRowIMatrix.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.createRowIMatrix.use)
    implicit none
    !  in int size
    integer (kind=sidl_int), intent(in) :: size
    !  in bool useCreateRow
    logical, intent(in) :: useCreateRow
    !  out array<int,2,row-major> res
    type(sidl_int_2d), intent(out) :: res
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.createRowIMatrix)
    if (useCreateRow) then
       res = makeRowIMatrix(size, .true., exception)
    else
       res = makeColumnIMatrix(size, .true., exception)
    endif
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.createRowIMatrix)
  end subroutine createRowIMatrix_impl


  ! 
  ! Method:  ensureColumn[]
  ! Make sure an array is column-major.  No changes to the dimension or
  ! values in a are made.
  ! 

  subroutine ensureColumn_impl(a, exception)
    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.ensureColumn.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.ensureColumn.use)
    implicit none
    !  inout array<int,2,column-major> a
    type(sidl_int_2d), intent(inout) :: a
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.ensureColumn)
    !     no action required
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.ensureColumn)
  end subroutine ensureColumn_impl


  ! 
  ! Method:  ensureRow[]
  ! Make sure an array is row-major.  No changes to the dimension or
  ! values in a are made.
  ! 

  subroutine ensureRow_impl(a, exception)
    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.ensureRow.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.ensureRow.use)
    implicit none
    !  inout array<int,2,row-major> a
    type(sidl_int_2d), intent(inout) :: a
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception

    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.ensureRow)
    !     no action required
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.ensureRow)
  end subroutine ensureRow_impl


  ! 
  ! Method:  isIMatrixOne[]
  ! Return <code>true</code> iff the implementation sees
  ! an incoming array satisfying condition I.
  ! 

  function isIMatrixOne_impl(A, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isIMatrixOne.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isIMatrixOne.use)
    implicit none
    !  in array<int> A
    type(sidl_int_1d), intent(in) :: A
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isIMatrixOne)
    retval = isIMatrix_1(A)
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isIMatrixOne)
  end function isIMatrixOne_impl


  ! 
  ! Method:  isColumnIMatrixOne[]
  ! Return <code>true</code> iff the implementation sees
  ! an incoming column-major array satisfying condition I.
  ! 

  function isColumnIMatrixOne_impl(A, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isColumnIMatrixOne.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isColumnIMatrixOne.use)
    implicit none
    !  in array<int,column-major> A
    type(sidl_int_1d), intent(in) :: A
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isColumnIMatrixOne)
    logical acol
    acol = isColumnOrder(A)
    retval = acol .and. isIMatrix_1(A)
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isColumnIMatrixOne)
  end function isColumnIMatrixOne_impl


  ! 
  ! Method:  isRowIMatrixOne[]
  ! Return <code>true</code> iff the implementation sees
  ! an incoming row-major array satisfying condition I.
  ! 

  function isRowIMatrixOne_impl(A, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isRowIMatrixOne.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isRowIMatrixOne.use)
    implicit none
    !  in array<int,row-major> A
    type(sidl_int_1d), intent(in) :: A
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isRowIMatrixOne)
    logical arow
    arow = isRowOrder(A)
    retval = arow .and. isIMatrix_1(A)
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isRowIMatrixOne)
  end function isRowIMatrixOne_impl


  ! 
  ! Method:  isIMatrixTwo[]
  ! Return <code>true</code> iff the implementation sees
  ! an incoming array satisfying condition I.
  ! 

  function isIMatrixTwo_impl(A, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isIMatrixTwo.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isIMatrixTwo.use)
    implicit none
    !  in array<int,2> A
    type(sidl_int_2d), intent(in) :: A
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isIMatrixTwo)
    retval = isIMatrix_2(A)
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isIMatrixTwo)
  end function isIMatrixTwo_impl


  ! 
  ! Method:  isColumnIMatrixTwo[]
  ! Return <code>true</code> iff the implementation sees
  ! an incoming column-major array satisfying condition I.
  ! 

  function isColumnIMatrixTwo_impl(A, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isColumnIMatrixTwo.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isColumnIMatrixTwo.use)
    implicit none
    !  in array<int,2,column-major> A
    type(sidl_int_2d), intent(in) :: A
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isColumnIMatrixTwo)
    logical acol
    acol = isColumnOrder(A)
    retval = acol .and. isIMatrix_2(A)
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isColumnIMatrixTwo)
  end function isColumnIMatrixTwo_impl


  ! 
  ! Method:  isRowIMatrixTwo[]
  ! Return <code>true</code> iff the implementation sees
  ! an incoming row-major array satisfying condition I.
  ! 

  function isRowIMatrixTwo_impl(A, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isRowIMatrixTwo.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isRowIMatrixTwo.use)
    implicit none
    !  in array<int,2,row-major> A
    type(sidl_int_2d), intent(in) :: A
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isRowIMatrixTwo)
    logical arow
    arow = isRowOrder(A)
    retval = arow .and. isIMatrix_2(A)
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isRowIMatrixTwo)
  end function isRowIMatrixTwo_impl


  ! 
  ! Method:  isIMatrixFour[]
  ! Return <code>true</code> iff the implementation sees
  ! an incoming array satisfying condition I.
  ! 

  function isIMatrixFour_impl(A, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isIMatrixFour.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isIMatrixFour.use)
    implicit none
    !  in array<int,4> A
    type(sidl_int_4d), intent(in) :: A
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isIMatrixFour)
    retval = isIMatrix_4(A)
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isIMatrixFour)
  end function isIMatrixFour_impl


  ! 
  ! Method:  isColumnIMatrixFour[]
  ! Return <code>true</code> iff the implementation sees
  ! an incoming column-major array satisfying condition I.
  ! 

  function isColumnIMatrixFour_impl(A, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isColumnIMatrixFour.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isColumnIMatrixFour.use)
    implicit none
    !  in array<int,4,column-major> A
    type(sidl_int_4d), intent(in) :: A
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isColumnIMatrixFour)
    logical acol
    acol = isColumnOrder(A)
    retval = acol .and. isIMatrix_4(A)
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isColumnIMatrixFour)
  end function isColumnIMatrixFour_impl


  ! 
  ! Method:  isRowIMatrixFour[]
  ! Return <code>true</code> iff the implementation sees
  ! an incoming row-major array satisfying condition I.
  ! 

  function isRowIMatrixFour_impl(A, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isRowIMatrixFour.use)
    ! Insert use statements here...
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isRowIMatrixFour.use)
    implicit none
    !  in array<int,4,row-major> A
    type(sidl_int_4d), intent(in) :: A
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isRowIMatrixFour)
    logical arow
    arow = isRowOrder(A)
    retval = arow .and. isIMatrix_4(A)
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isRowIMatrixFour)
  end function isRowIMatrixFour_impl


  ! 
  ! Method:  isSliceWorking[]
  ! Return <code>true</code> iff the implementation of slice
  ! and smart copy is correct.
  ! 

  function isSliceWorking_impl(useCreateCol, exception) result(retval)
    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isSliceWorking.use)
    use sidl_int_array
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isSliceWorking.use)
    implicit none
    !  in bool useCreateCol
    logical, intent(in) :: useCreateCol
    !  out sidl.BaseInterface exception
    type(sidl_BaseInterface_t), intent(out) :: exception
    !  function result
    logical :: retval

    ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isSliceWorking)
    integer(kind=sidl_int) :: maxDim, halfDim, ind(2), &
         astride(2), numElem(2)
    integer(kind=sidl_int) :: newIndex(2), dim, Aelem, &
         Belem
    type(sidl_int_2d) :: A, B
    retval = .true.
    maxDim = 16_sidl_int
    halfDim = maxDim / 2_sidl_int
    astride(1) = 2_sidl_int
    astride(2) = 2_sidl_int
    numElem(1) = halfDim
    numElem(2) = halfDim
    ind(1) = 0_sidl_int
    ind(2) = 0_sidl_int
    newIndex(1) = 0_sidl_int
    newIndex(2) = 0_sidl_int
    call set_null(A)
    call set_null(B)
    if (useCreateCol) then
       A = makeColumnIMatrix(maxDim, .false., exception)
    else
       A = makeRowIMatrix(maxDim, .false., exception)
    endif
    call slice(A, numElem, ind, astride, newIndex, B)
    if (is_null(B)) then 
       retval = .false.
       goto 100
    endif
    dim = dimen(B)
    if (dim .ne. 2_sidl_int) then
       retval = .false.
       goto 100
    endif

    do while (newIndex(2) .lt. halfDim)
       do while (newIndex(1) .lt. halfDim)
          call get(A, ind, Aelem)
          call get(B, newIndex, Belem)
          if ((Aelem .ne. Belem) .or.  Belem .ne. iFunc(ind, 2_sidl_int)) then
             retval = .false.
             goto 100
          endif
          newIndex(1) = newIndex(1) + 1_sidl_int
          ind(1) = ind(1) + 2_sidl_int
       enddo
       newIndex(2) = newIndex(2) + 1_sidl_int
       ind(2) = ind(2) + 2_sidl_int
    enddo

    ! CLEANUP AND RETURN
100 if (not_null(A)) then
       call deleteRef(A)
    endif
    if (not_null(B)) then
       call deleteRef(B)
    endif
    ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isSliceWorking)
  end function isSliceWorking_impl


  ! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
  ! Insert extra code here...
  ! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)

end module Ordering_IntOrderTest_Impl
