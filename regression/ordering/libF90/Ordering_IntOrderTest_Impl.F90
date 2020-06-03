! 
! File:          Ordering_IntOrderTest_Impl.F90
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
  integer(kind=sidl_int) :: i, iFunc
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
  integer(kind=sidl_int) :: i, j, iFunc
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
  integer(kind=sidl_int) :: i, j, k, l, iFunc
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
  integer(kind=sidl_int) :: i, j, iFunc
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
  integer(kind=sidl_int) :: i, j, k, l, iFunc
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

recursive subroutine Ordering_IntOrderTest__ctor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Ordering_IntOrderTest
  use Ordering_IntOrderTest_impl
  ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._ctor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._ctor.use)
  implicit none
  type(Ordering_IntOrderTest_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._ctor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._ctor)
end subroutine Ordering_IntOrderTest__ctor_mi


! 
! Method:  _ctor2[]
! Special Class constructor called when the user wants to wrap his own private data.
! 

recursive subroutine Ordering_IntOrderTest__ctor2_mi(self, private_data,       &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Ordering_IntOrderTest
  use Ordering_IntOrderTest_impl
  ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._ctor2.use)
  ! Insert-Code-Here {Ordering.IntOrderTest._ctor2.use} (use statements)
  ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._ctor2.use)
  implicit none
  type(Ordering_IntOrderTest_t) :: self
  ! in
  type(Ordering_IntOrderTest_wrap) :: private_data
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._ctor2)
! Insert-Code-Here {Ordering.IntOrderTest._ctor2} (_ctor2 method)
! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._ctor2)
end subroutine Ordering_IntOrderTest__ctor2_mi


! 
! Method:  _dtor[]
! Class destructor called when the class is deleted.
! 

recursive subroutine Ordering_IntOrderTest__dtor_mi(self, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Ordering_IntOrderTest
  use Ordering_IntOrderTest_impl
  ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._dtor.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._dtor.use)
  implicit none
  type(Ordering_IntOrderTest_t) :: self
  ! in
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._dtor)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._dtor)
end subroutine Ordering_IntOrderTest__dtor_mi


! 
! Method:  _load[]
! Static class initializer called exactly once before any user-defined method is dispatched
! 

recursive subroutine Ordering_IntOrderTest__load_mi(exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Ordering_IntOrderTest
  use Ordering_IntOrderTest_impl
  ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._load.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._load.use)
  implicit none
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._load)
! Insert the implementation here...
! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._load)
end subroutine Ordering_IntOrderTest__load_mi


! 
! Method:  makeColumnIMatrix[]
! Create a column-major matrix satisfying condition I.
! 

recursive subroutine makeColumnIMatrixz5ybfavnz3i_mi(size, useCreateCol,       &
  retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Ordering_IntOrderTest
  use sidl_int_array
  use Ordering_IntOrderTest_impl
  ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.makeColumnIMatrix.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.makeColumnIMatrix.use)
  implicit none
  integer (kind=sidl_int) :: size
  ! in
  logical :: useCreateCol
  ! in
  type(sidl_int_2d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.makeColumnIMatrix)
  if (useCreateCol) then
     call create2dCol(size, size, retval)
  else
     call create2dRow(size, size, retval)
  endif
  call fillIMatrix_2(retval)
! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.makeColumnIMatrix)
end subroutine makeColumnIMatrixz5ybfavnz3i_mi


! 
! Method:  makeRowIMatrix[]
! Create a row-major matrix satisfying condition I.
! 

recursive subroutine Int_makeRowIMatrix8h06e37u6x_mi(size, useCreateRow,       &
  retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Ordering_IntOrderTest
  use sidl_int_array
  use Ordering_IntOrderTest_impl
  ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.makeRowIMatrix.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.makeRowIMatrix.use)
  implicit none
  integer (kind=sidl_int) :: size
  ! in
  logical :: useCreateRow
  ! in
  type(sidl_int_2d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.makeRowIMatrix)
  if (useCreateRow) then
     call create2dRow(size, size, retval)
  else
     call create2dCol(size, size, retval)
  endif
  call fillIMatrix_2(retval)
! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.makeRowIMatrix)
end subroutine Int_makeRowIMatrix8h06e37u6x_mi


! 
! Method:  makeIMatrix[]
! Create a 4-D matrix satisfying condition I.  Each dimension has
! size elements numbers 0 through size-1.
! 

recursive subroutine IntOrd_makeIMatrixs68ixk4nld_mi(size, useCreateColumn,    &
  retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Ordering_IntOrderTest
  use sidl_int_array
  use Ordering_IntOrderTest_impl
  ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.makeIMatrix.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.makeIMatrix.use)
  implicit none
  integer (kind=sidl_int) :: size
  ! in
  logical :: useCreateColumn
  ! in
  type(sidl_int_4d) :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

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
end subroutine IntOrd_makeIMatrixs68ixk4nld_mi


! 
! Method:  createColumnIMatrix[]
! Create a column-major matrix satisfying condition I.
! 

recursive subroutine createColumnIMatrij98n1gtwcu_mi(size, useCreateCol, res,  &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Ordering_IntOrderTest
  use sidl_int_array
  use Ordering_IntOrderTest_impl
  ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.createColumnIMatrix.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.createColumnIMatrix.use)
  implicit none
  integer (kind=sidl_int) :: size
  ! in
  logical :: useCreateCol
  ! in
  type(sidl_int_2d) :: res
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.createColumnIMatrix)
  if (useCreateCol) then
     call makeColumnIMatrix(size, .true., res, exception)
  else
     call makeRowIMatrix(size, .true., res, exception)
  endif
! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.createColumnIMatrix)
end subroutine createColumnIMatrij98n1gtwcu_mi


! 
! Method:  createRowIMatrix[]
! Create a row-major matrix satisfying condition I.
! 

recursive subroutine I_createRowIMatrixq_lpgbci6l_mi(size, useCreateRow, res,  &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Ordering_IntOrderTest
  use sidl_int_array
  use Ordering_IntOrderTest_impl
  ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.createRowIMatrix.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.createRowIMatrix.use)
  implicit none
  integer (kind=sidl_int) :: size
  ! in
  logical :: useCreateRow
  ! in
  type(sidl_int_2d) :: res
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.createRowIMatrix)
  if (useCreateRow) then
     call makeRowIMatrix(size, .true., res, exception)
  else
     call makeColumnIMatrix(size, .true., res, exception)
  endif
! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.createRowIMatrix)
end subroutine I_createRowIMatrixq_lpgbci6l_mi


! 
! Method:  ensureColumn[]
! Make sure an array is column-major.  No changes to the dimension or
! values in a are made.
! 

recursive subroutine IntOr_ensureColumno2tek0_o3o_mi(a, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Ordering_IntOrderTest
  use sidl_int_array
  use Ordering_IntOrderTest_impl
  ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.ensureColumn.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.ensureColumn.use)
  implicit none
  type(sidl_int_2d) :: a
  ! inout
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.ensureColumn)
!     no action required
! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.ensureColumn)
end subroutine IntOr_ensureColumno2tek0_o3o_mi


! 
! Method:  ensureRow[]
! Make sure an array is row-major.  No changes to the dimension or
! values in a are made.
! 

recursive subroutine IntOrder_ensureRow2xbnpv1nvt_mi(a, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Ordering_IntOrderTest
  use sidl_int_array
  use Ordering_IntOrderTest_impl
  ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.ensureRow.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.ensureRow.use)
  implicit none
  type(sidl_int_2d) :: a
  ! inout
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.ensureRow)
!     no action required
! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.ensureRow)
end subroutine IntOrder_ensureRow2xbnpv1nvt_mi


! 
! Method:  isIMatrixOne[]
! Return <code>true</code> iff the implementation sees
! an incoming array satisfying condition I.
! 

recursive subroutine IntOr_isIMatrixOne3gwlgypfcv_mi(A, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Ordering_IntOrderTest
  use sidl_int_array
  use Ordering_IntOrderTest_impl
  ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isIMatrixOne.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isIMatrixOne.use)
  implicit none
  type(sidl_int_1d) :: A
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isIMatrixOne)
  logical isIMatrix_1
  retval = isIMatrix_1(A)
! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isIMatrixOne)
end subroutine IntOr_isIMatrixOne3gwlgypfcv_mi


! 
! Method:  isColumnIMatrixOne[]
! Return <code>true</code> iff the implementation sees
! an incoming column-major array satisfying condition I.
! 

recursive subroutine isColumnIMatrixOned9rdelopb9_mi(A, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Ordering_IntOrderTest
  use sidl_int_array
  use Ordering_IntOrderTest_impl
  ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isColumnIMatrixOne.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isColumnIMatrixOne.use)
  implicit none
  type(sidl_int_1d) :: A
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isColumnIMatrixOne)
  logical acol, isIMatrix_1
  acol = isColumnOrder(A)
  retval = acol .and. isIMatrix_1(A)
! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isColumnIMatrixOne)
end subroutine isColumnIMatrixOned9rdelopb9_mi


! 
! Method:  isRowIMatrixOne[]
! Return <code>true</code> iff the implementation sees
! an incoming row-major array satisfying condition I.
! 

recursive subroutine In_isRowIMatrixOne607iplqtr5_mi(A, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Ordering_IntOrderTest
  use sidl_int_array
  use Ordering_IntOrderTest_impl
  ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isRowIMatrixOne.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isRowIMatrixOne.use)
  implicit none
  type(sidl_int_1d) :: A
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isRowIMatrixOne)
  logical arow, isIMatrix_1
  arow = isRowOrder(A)
  retval = arow .and. isIMatrix_1(A)
! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isRowIMatrixOne)
end subroutine In_isRowIMatrixOne607iplqtr5_mi


! 
! Method:  isIMatrixTwo[]
! Return <code>true</code> iff the implementation sees
! an incoming array satisfying condition I.
! 

recursive subroutine IntOr_isIMatrixTwogo7_eewh9w_mi(A, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Ordering_IntOrderTest
  use sidl_int_array
  use Ordering_IntOrderTest_impl
  ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isIMatrixTwo.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isIMatrixTwo.use)
  implicit none
  type(sidl_int_2d) :: A
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isIMatrixTwo)
  logical isIMatrix_2
  retval = isIMatrix_2(A)
! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isIMatrixTwo)
end subroutine IntOr_isIMatrixTwogo7_eewh9w_mi


! 
! Method:  isColumnIMatrixTwo[]
! Return <code>true</code> iff the implementation sees
! an incoming column-major array satisfying condition I.
! 

recursive subroutine isColumnIMatrixTwo9neer9_rft_mi(A, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Ordering_IntOrderTest
  use sidl_int_array
  use Ordering_IntOrderTest_impl
  ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isColumnIMatrixTwo.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isColumnIMatrixTwo.use)
  implicit none
  type(sidl_int_2d) :: A
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isColumnIMatrixTwo)
  logical acol, isIMatrix_2
  acol = isColumnOrder(A)
  retval = acol .and. isIMatrix_2(A)
! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isColumnIMatrixTwo)
end subroutine isColumnIMatrixTwo9neer9_rft_mi


! 
! Method:  isRowIMatrixTwo[]
! Return <code>true</code> iff the implementation sees
! an incoming row-major array satisfying condition I.
! 

recursive subroutine In_isRowIMatrixTwoqo0guawv83_mi(A, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Ordering_IntOrderTest
  use sidl_int_array
  use Ordering_IntOrderTest_impl
  ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isRowIMatrixTwo.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isRowIMatrixTwo.use)
  implicit none
  type(sidl_int_2d) :: A
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isRowIMatrixTwo)
  logical arow, isIMatrix_2
  arow = isRowOrder(A)
  retval = arow .and. isIMatrix_2(A)
! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isRowIMatrixTwo)
end subroutine In_isRowIMatrixTwoqo0guawv83_mi


! 
! Method:  isIMatrixFour[]
! Return <code>true</code> iff the implementation sees
! an incoming array satisfying condition I.
! 

recursive subroutine IntO_isIMatrixFourgf7n_6hyd2_mi(A, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Ordering_IntOrderTest
  use sidl_int_array
  use Ordering_IntOrderTest_impl
  ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isIMatrixFour.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isIMatrixFour.use)
  implicit none
  type(sidl_int_4d) :: A
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isIMatrixFour)
  logical isIMatrix_4
  retval = isIMatrix_4(A)
! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isIMatrixFour)
end subroutine IntO_isIMatrixFourgf7n_6hyd2_mi


! 
! Method:  isColumnIMatrixFour[]
! Return <code>true</code> iff the implementation sees
! an incoming column-major array satisfying condition I.
! 

recursive subroutine isColumnIMatrixFouunjqbwfb23_mi(A, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Ordering_IntOrderTest
  use sidl_int_array
  use Ordering_IntOrderTest_impl
  ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isColumnIMatrixFour.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isColumnIMatrixFour.use)
  implicit none
  type(sidl_int_4d) :: A
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isColumnIMatrixFour)
  logical acol, isIMatrix_4
  acol = isColumnOrder(A)
  retval = acol .and. isIMatrix_4(A)
! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isColumnIMatrixFour)
end subroutine isColumnIMatrixFouunjqbwfb23_mi


! 
! Method:  isRowIMatrixFour[]
! Return <code>true</code> iff the implementation sees
! an incoming row-major array satisfying condition I.
! 

recursive subroutine I_isRowIMatrixFourfai8pehvh9_mi(A, retval, exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Ordering_IntOrderTest
  use sidl_int_array
  use Ordering_IntOrderTest_impl
  ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isRowIMatrixFour.use)
  ! Insert use statements here...
  ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isRowIMatrixFour.use)
  implicit none
  type(sidl_int_4d) :: A
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isRowIMatrixFour)
  logical arow, isIMatrix_4
  arow = isRowOrder(A)
  retval = arow .and. isIMatrix_4(A)
! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isRowIMatrixFour)
end subroutine I_isRowIMatrixFourfai8pehvh9_mi


! 
! Method:  isSliceWorking[]
! Return <code>true</code> iff the implementation of slice
! and smart copy is correct.
! 

recursive subroutine Int_isSliceWorkingf42w8d0h4b_mi(useCreateCol, retval,     &
  exception)
  use sidl
  use sidl_BaseInterface
  use sidl_RuntimeException
  use Ordering_IntOrderTest
  use Ordering_IntOrderTest_impl
  ! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isSliceWorking.use)
  use sidl_int_array
  ! DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isSliceWorking.use)
  implicit none
  logical :: useCreateCol
  ! in
  logical :: retval
  ! out
  type(sidl_BaseInterface_t) :: exception
  ! out

! DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isSliceWorking)
  integer(kind=sidl_int) :: maxDim, halfDim, ind(2), &
       astride(2), numElem(2)
  integer(kind=sidl_int) :: newIndex(2), dim, Aelem, &
       Belem, iFunc
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
     call makeColumnIMatrix(maxDim, .false., A, exception)
  else
     call makeRowIMatrix(maxDim, .false., A, exception)
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
end subroutine Int_isSliceWorkingf42w8d0h4b_mi


! DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
! Insert extra code here...
! DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
