C       
C       File:          Ordering_IntOrderTest_Impl.f
C       Symbol:        Ordering.IntOrderTest-v0.1
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7463M trunk)
C       Description:   Server-side implementation for Ordering.IntOrderTest
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "Ordering.IntOrderTest" (version 0.1)
C       
C       This class provides methods to verify that the array ordering
C       capabilities work for arrays of int.
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
      integer*4 function iFunc(ind, dim)
      implicit none
      integer*4 dim, ind(dim), res, i
      res = 0
      do i=1, dim
         res = res + i * ind(i)
      enddo
      ifunc = res
      end

      logical function incIndex(ind, A, dimen)
      implicit none
      integer*4 dimen
      integer*4 ind(dimen), i, upper, lower
      integer*8 A
      incIndex = .false.
      i = 1
      do while (i .le. dimen .and. .not. incIndex) 
         call sidl_int__array_upper_f(A, i - 1, upper)
         ind(i) = ind(i) + 1
         if (ind(i) .gt. upper) then
            call sidl_int__array_lower_f(A, i - 1, lower)
            ind(i) = lower
            i = i + 1
         else
            incIndex = .true.
         endif
      enddo
      end

      logical function isIMatrix(A)
      implicit none
      integer*8 A
      integer*4 dimen, i, cindex(16), upper
      integer*4 iFunc, elem
      logical incIndex
      call sidl_int__array_dimen_f(A, dimen)
      isIMatrix = .false.
      if ((dimen .le. 16) .and. (dimen .gt. 0)) then
         do i = 1, dimen
            call sidl_int__array_lower_f(A, i - 1, cindex(i))
            call sidl_int__array_upper_f(A, i - 1, upper)
            if (cindex(i) .gt. upper) then
               isIMatrix = .true.
            endif
         enddo
         if (.not. isIMatrix) then
            cindex(1) = cindex(1) - 1
            isIMatrix = .true.
            do while (incIndex(cindex, A, dimen) .and. isIMatrix)
               call sidl_int__array_get_f(A, cindex, elem)
               if (iFunc(cindex, dimen) .ne. elem) then
                  isIMatrix = .false.
               endif
            enddo
         endif
      endif
      end
      
      subroutine fillIMatrix(A)
      implicit none
      integer*8 A
      integer*4 dimen, i, cindex(16), upper, iFunc
      logical incIndex
      if (A .ne. 0) then
         call sidl_int__array_dimen_f(A, dimen)
         if ((dimen .le. 16) .and. (dimen .gt. 0)) then
            do i = 1, dimen
               call sidl_int__array_lower_f(A, i - 1, cindex(i))
               call sidl_int__array_upper_f(A, i - 1, upper)
               if (cindex(i) .gt. upper) then
                  return
               endif
            enddo
            cindex(1) = cindex(1) - 1
            do while (incIndex(cindex, A, dimen))
               call sidl_int__array_set_f(A, cindex,
     $              iFunc(cindex, dimen))
            enddo
         endif
      endif
      end
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine Ordering_IntOrderTest__ctor_fi(self, exception)
        implicit none
C        in Ordering.IntOrderTest self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._ctor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine Ordering_IntOrderTest__ctor2_fi(self, private_data, 
     &     exception)
        implicit none
C        in Ordering.IntOrderTest self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._ctor2)
C       Insert-Code-Here {Ordering.IntOrderTest._ctor2} (_ctor2 method)
C       DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine Ordering_IntOrderTest__dtor_fi(self, exception)
        implicit none
C        in Ordering.IntOrderTest self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._dtor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine Ordering_IntOrderTest__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest._load)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(Ordering.IntOrderTest._load)
        end


C       
C       Method:  makeColumnIMatrix[]
C       Create a column-major matrix satisfying condition I.
C       

        subroutine Ordering_IntOrderTest_makeColumnIMatrix_fi(size, 
     &     useCreateCol, retval, exception)
        implicit none
C        in int size
        integer*4 :: size
C        in bool useCreateCol
        logical :: useCreateCol
C        out array<int,2,column-major> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.makeColumnIMatrix)
        if (useCreateCol) then
           call sidl_int__array_create2dCol_f(size, size, retval)
        else
           call sidl_int__array_create2dRow_f(size, size, retval)
        endif
        call fillIMatrix(retval)
C       DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.makeColumnIMatrix)
        end


C       
C       Method:  makeRowIMatrix[]
C       Create a row-major matrix satisfying condition I.
C       

        subroutine Ordering_IntOrderTest_makeRowIMatrix_fi(size, 
     &     useCreateRow, retval, exception)
        implicit none
C        in int size
        integer*4 :: size
C        in bool useCreateRow
        logical :: useCreateRow
C        out array<int,2,row-major> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.makeRowIMatrix)
        if (useCreateRow) then
           call sidl_int__array_create2dRow_f(size, size, retval)
        else
           call sidl_int__array_create2dCol_f(size, size, retval)
        endif
        call fillIMatrix(retval)
C       DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.makeRowIMatrix)
        end


C       
C       Method:  makeIMatrix[]
C       Create a 4-D matrix satisfying condition I.  Each dimension has
C       size elements numbers 0 through size-1.
C       

        subroutine Ordering_IntOrderTest_makeIMatrix_fi(size, 
     &     useCreateColumn, retval, exception)
        implicit none
C        in int size
        integer*4 :: size
C        in bool useCreateColumn
        logical :: useCreateColumn
C        out array<int,4> retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.makeIMatrix)
        integer*4 lower(4), upper(4), i
        do i = 1, 4
           lower(i) = 0
           upper(i) = size - 1
        enddo
        if (useCreateColumn) then
           call sidl_int__array_createCol_f(4, lower, upper, retval)
        else
           call sidl_int__array_createRow_f(4, lower, upper, retval)
        endif
        call fillIMatrix(retval)
C       DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.makeIMatrix)
        end


C       
C       Method:  createColumnIMatrix[]
C       Create a column-major matrix satisfying condition I.
C       

        subroutine Ordering_IntOrderTest_createColumnIMatrix_fi(size, 
     &     useCreateCol, res, exception)
        implicit none
C        in int size
        integer*4 :: size
C        in bool useCreateCol
        logical :: useCreateCol
C        out array<int,2,column-major> res
        integer*8 :: res
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.createColumnIMatrix)
        if (useCreateCol) then
           call Ordering_IntOrderTest_makeColumnIMatrix_f(size,
     $          .true., res, exception)
        else
           call Ordering_IntOrderTest_makeRowIMatrix_f(size,
     $          .true., res, exception)
        endif
C       DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.createColumnIMatrix)
        end


C       
C       Method:  createRowIMatrix[]
C       Create a row-major matrix satisfying condition I.
C       

        subroutine Ordering_IntOrderTest_createRowIMatrix_fi(size, 
     &     useCreateRow, res, exception)
        implicit none
C        in int size
        integer*4 :: size
C        in bool useCreateRow
        logical :: useCreateRow
C        out array<int,2,row-major> res
        integer*8 :: res
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.createRowIMatrix)
        if (useCreateRow) then
           call Ordering_IntOrderTest_makeRowIMatrix_f(size,
     $          .true., res, exception)
        else
           call Ordering_IntOrderTest_makeColumnIMatrix_f(size,
     $          .true., res, exception)
        endif
C       DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.createRowIMatrix)
        end


C       
C       Method:  ensureColumn[]
C       Make sure an array is column-major.  No changes to the dimension or
C       values in a are made.
C       

        subroutine Ordering_IntOrderTest_ensureColumn_fi(a, exception)
        implicit none
C        inout array<int,2,column-major> a
        integer*8 :: a
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.ensureColumn)
C     no action required
C       DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.ensureColumn)
        end


C       
C       Method:  ensureRow[]
C       Make sure an array is row-major.  No changes to the dimension or
C       values in a are made.
C       

        subroutine Ordering_IntOrderTest_ensureRow_fi(a, exception)
        implicit none
C        inout array<int,2,row-major> a
        integer*8 :: a
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.ensureRow)
C     no action required
C       DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.ensureRow)
        end


C       
C       Method:  isIMatrixOne[]
C       Return <code>true</code> iff the implementation sees
C       an incoming array satisfying condition I.
C       

        subroutine Ordering_IntOrderTest_isIMatrixOne_fi(A, retval, 
     &     exception)
        implicit none
C        in array<int> A
        integer*8 :: A
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isIMatrixOne)
        logical isIMatrix
        retval = isIMatrix(A)
C       DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isIMatrixOne)
        end


C       
C       Method:  isColumnIMatrixOne[]
C       Return <code>true</code> iff the implementation sees
C       an incoming column-major array satisfying condition I.
C       

        subroutine Ordering_IntOrderTest_isColumnIMatrixOne_fi(A, 
     &     retval, exception)
        implicit none
C        in array<int,column-major> A
        integer*8 :: A
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isColumnIMatrixOne)
        logical acol, isIMatrix
        call sidl_int__array_isColumnOrder_f(A, acol)
        retval = acol .and. isIMatrix(A)
C       DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isColumnIMatrixOne)
        end


C       
C       Method:  isRowIMatrixOne[]
C       Return <code>true</code> iff the implementation sees
C       an incoming row-major array satisfying condition I.
C       

        subroutine Ordering_IntOrderTest_isRowIMatrixOne_fi(A, retval, 
     &     exception)
        implicit none
C        in array<int,row-major> A
        integer*8 :: A
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isRowIMatrixOne)
        logical arow, isIMatrix
        call sidl_int__array_isRowOrder_f(A, arow)
        retval = arow .and. isIMatrix(A)
C       DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isRowIMatrixOne)
        end


C       
C       Method:  isIMatrixTwo[]
C       Return <code>true</code> iff the implementation sees
C       an incoming array satisfying condition I.
C       

        subroutine Ordering_IntOrderTest_isIMatrixTwo_fi(A, retval, 
     &     exception)
        implicit none
C        in array<int,2> A
        integer*8 :: A
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isIMatrixTwo)
        logical isIMatrix
        retval = isIMatrix(A)
C       DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isIMatrixTwo)
        end


C       
C       Method:  isColumnIMatrixTwo[]
C       Return <code>true</code> iff the implementation sees
C       an incoming column-major array satisfying condition I.
C       

        subroutine Ordering_IntOrderTest_isColumnIMatrixTwo_fi(A, 
     &     retval, exception)
        implicit none
C        in array<int,2,column-major> A
        integer*8 :: A
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isColumnIMatrixTwo)
        logical acol, isIMatrix
        call sidl_int__array_isColumnOrder_f(A, acol)
        retval = acol .and. isIMatrix(A)
C       DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isColumnIMatrixTwo)
        end


C       
C       Method:  isRowIMatrixTwo[]
C       Return <code>true</code> iff the implementation sees
C       an incoming row-major array satisfying condition I.
C       

        subroutine Ordering_IntOrderTest_isRowIMatrixTwo_fi(A, retval, 
     &     exception)
        implicit none
C        in array<int,2,row-major> A
        integer*8 :: A
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isRowIMatrixTwo)
        logical arow, isIMatrix
        call sidl_int__array_isRowOrder_f(A, arow)
        retval = arow .and. isIMatrix(A)
C       DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isRowIMatrixTwo)
        end


C       
C       Method:  isIMatrixFour[]
C       Return <code>true</code> iff the implementation sees
C       an incoming array satisfying condition I.
C       

        subroutine Ordering_IntOrderTest_isIMatrixFour_fi(A, retval, 
     &     exception)
        implicit none
C        in array<int,4> A
        integer*8 :: A
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isIMatrixFour)
        logical isIMatrix
        retval = isIMatrix(A)
C       DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isIMatrixFour)
        end


C       
C       Method:  isColumnIMatrixFour[]
C       Return <code>true</code> iff the implementation sees
C       an incoming column-major array satisfying condition I.
C       

        subroutine Ordering_IntOrderTest_isColumnIMatrixFour_fi(A, 
     &     retval, exception)
        implicit none
C        in array<int,4,column-major> A
        integer*8 :: A
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isColumnIMatrixFour)
        logical acol, isIMatrix
        call sidl_int__array_isColumnOrder_f(A, acol)
        retval = acol .and. isIMatrix(A)
C       DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isColumnIMatrixFour)
        end


C       
C       Method:  isRowIMatrixFour[]
C       Return <code>true</code> iff the implementation sees
C       an incoming row-major array satisfying condition I.
C       

        subroutine Ordering_IntOrderTest_isRowIMatrixFour_fi(A, retval, 
     &     exception)
        implicit none
C        in array<int,4,row-major> A
        integer*8 :: A
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isRowIMatrixFour)
        logical arow, isIMatrix
        call sidl_int__array_isRowOrder_f(A, arow)
        retval = arow .and. isIMatrix(A)
C       DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isRowIMatrixFour)
        end


C       
C       Method:  isSliceWorking[]
C       Return <code>true</code> iff the implementation of slice
C       and smart copy is correct.
C       

        subroutine Ordering_IntOrderTest_isSliceWorking_fi(useCreateCol,
     &     retval, exception)
        implicit none
C        in bool useCreateCol
        logical :: useCreateCol
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(Ordering.IntOrderTest.isSliceWorking)
        integer*4 maxDim, halfDim, ind(2), stride(2), numElem(2)
        integer*4 newIndex(2), dimen, Aelem, Belem, iFunc
        integer*8 A, B
        retval = .true.
        maxDim = 16
        halfDim = maxDim / 2
        stride(1) = 2
        stride(2) = 2
        numElem(1) = halfDim
        numElem(2) = halfDim
        ind(1) = 0
        ind(2) = 0
        newIndex(1) = 0
        newIndex(2) = 0
        A = 0
        B = 0
        if (useCreateCol) then
           call Ordering_IntOrderTest_makeColumnIMatrix_f(maxDim,
     $          .false., A, exception)
        else
           call Ordering_IntOrderTest_makeRowIMatrix_f(maxDim,
     $          .false., A, exception)
        endif
        call sidl_int__array_slice_f(A, 2, numElem, ind, stride,
     $       newIndex, B)
        if (B .eq. 0) then 
           retval = .false.
           goto 100
        endif
        call sidl_int__array_dimen_f(B, dimen)
        if (dimen .ne. 2) then
           retval = .false.
           goto 100
        endif

        do while (newIndex(2) .lt. halfDim)
           do while (newIndex(1) .lt. halfDim)
              call sidl_int__array_get_f(A, ind, Aelem)
              call sidl_int__array_get_f(B, newIndex, Belem)
              if ((Aelem .ne. Belem) .or.
     $             Belem .ne. iFunc(ind, 2)) then
                 retval = .false.
                 goto 100
              endif
              newIndex(1) = newIndex(1) + 1
              ind(1) = ind(1) + 2
           enddo
           newIndex(2) = newIndex(2) + 1
           ind(2) = ind(2) + 2
        enddo
        
C     CLEANUP AND RETURN
 100    if (A .ne. 0) then
           call sidl_int__array_deleteRef_f(A)
        endif
        if (B .ne. 0) then
           call sidl_int__array_deleteRef_f(B)
        endif
C       DO-NOT-DELETE splicer.end(Ordering.IntOrderTest.isSliceWorking)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
