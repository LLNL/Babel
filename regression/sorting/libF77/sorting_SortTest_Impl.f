C       
C       File:          sorting_SortTest_Impl.f
C       Symbol:        sorting.SortTest-v0.1
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7463M trunk)
C       Description:   Server-side implementation for sorting.SortTest
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "sorting.SortTest" (version 0.1)
C       
C       Run a bunch of sorts through a stress test.
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
      subroutine intToString(ivalue, str, strlen)
      integer*4 ivalue, copy, ten, strlen
      character*(*) str
      character*(80) buffer
      logical isneg
      integer i, j, k
      copy = ivalue
      ten = 10
      i = 1
      j = 1
      str = ''
      if (copy .lt. 0) then
         str(j:j) = '-'
         j = j + 1
         copy = -copy
      end if
 100  buffer(i:i) = char(ichar('0') + mod(copy,ten))
        i = i + 1
        copy = copy / 10
      if (copy .ne. 0) go to 100
c the string is done but reversed
      do k = 1, i-1
         str(j:j) = buffer(i - k:i - k)
         j = j + 1
      enddo
      strlen = j - 1
      end

      subroutine sortAndReport(alg, cont, comp)
      implicit none
      integer*8 alg, cont, comp
      integer*8 swpCnt, cmpCnt, tracker, tae
      integer*4 numswap, numcmp
      character*(80) cmpbuf, swapbuf
      integer*4 cmplen, swaplen
      call sorting_SortingAlgorithm_reset_f(alg, tae)
      call sorting_SortingAlgorithm_sort_f(alg, cont, comp, tae)
      call sorting_SortingAlgorithm_getSwapCounter_f(alg, swpCnt, tae)
      call sorting_Counter_getCount_f(swpCnt, numswap, tae)
      call sorting_Counter_deleteRef_f(swpCnt, tae)
      call sorting_SortingAlgorithm_getCompareCounter_f(alg, cmpCnt,
     & tae)
      call sorting_Counter_getCount_f(cmpCnt, numcmp, tae)
      call sorting_Counter_deleteRef_f(cmpCnt, tae)
      call intToString(numcmp, cmpbuf, cmplen)
      call intToString(numswap, swapbuf, swaplen)
      call synch_RegOut_getInstance_f(tracker, tae)
      call synch_RegOut_writeComment_f(tracker,
     $     'compares (' // cmpbuf(1:cmplen) // ') swaps (' //
     $     swapbuf(1:swaplen)  // ')', tae)
      call synch_RegOut_deleteRef_f(tracker, tae)
      end

      logical function notSorted(cont, comp)
      implicit none
      integer*8 cont, comp, tae
      integer*4 length, i, result
      call sorting_Container_getLength_f(cont, length, tae)
      do i = 1, length - 1
         call sorting_Container_compare_f(cont, i-1, i, comp, result,
     $        tae)
         if (result .gt. 0) then
            notSorted = .true.
            goto 100
         endif
      enddo
      notSorted = .false.
 100  return
      end

      subroutine testAlgorithm(alg, retval)
      implicit none
      integer*8 alg
      integer*8 data, cont, intcomp, comp, tae
      integer*8 tracker
      character*30 name
      logical notSorted, retval
      integer*4 j, testsizes(10), datalen
      character*80 databuf
      data testsizes / 0, 1, 2, 3, 4, 7, 10, 51, 64, -1 /
      
      call synch_RegOut_getInstance_f(tracker, tae)
      call sorting_SortingAlgorithm_getName_f(alg, name, tae)
      call synch_RegOut_writeComment_f(tracker,
     $     '****ALGORITHM IS ' // name // '****', tae)
      call sorting_IntegerContainer__create_f(data, tae)
      call sorting_Container__cast_f(data, cont, tae)
      call sorting_CompInt__create_f(intcomp, tae)
      call sorting_Comparator__cast_f(intcomp, comp, tae)
      j = 1
      do while (testsizes(j) .ge. 0)
         call sorting_CompInt_setSortIncreasing_f(intcomp, .true., tae)
         call intToString(testsizes(j), databuf, datalen)
         call synch_RegOut_writeComment_f(tracker,
     $        'DATA SIZE ' // databuf(1:datalen), tae)
         call sorting_IntegerContainer_setLength_f(data, testsizes(j),
     $        tae)
         call sortAndReport(alg, cont, comp)
         if (notSorted(cont, comp)) then
            call synch_RegOut_writeComment_f(tracker,
     $           'sort failed!!', tae)
            retval = .false.
         endif
         call synch_RegOut_writeComment_f(tracker,
     $        'pre-sorted list', tae)
         call sortAndReport(alg, cont, comp)
         if (notSorted(cont, comp)) then
            call synch_RegOut_writeComment_f(tracker,
     $           'sort failed!!', tae)
            retval = .false.
         endif
         call synch_RegOut_writeComment_f(tracker,
     $        'reverse sorted list', tae)
         call sorting_CompInt_setSortIncreasing_f(intcomp, .false.,
     $        tae)
         call sortAndReport(alg, cont, comp)
         if (notSorted(cont, comp)) then
            call synch_RegOut_writeComment_f(tracker,
     $           'sort failed!!', tae)
            retval = .false.
         endif
         j = j + 1
      enddo

      call sorting_Container_deleteRef_f(cont, tae)
      call sorting_IntegerContainer_deleteRef_f(data, tae)
      call sorting_SortingAlgorithm_deleteRef_f(alg, tae)
      call sorting_Comparator_deleteRef_f(comp, tae)
      call sorting_CompInt_deleteRef_f(intcomp, tae)
      call synch_RegOut_deleteRef_f(tracker, tae)
      end
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine sorting_SortTest__ctor_fi(self, exception)
        implicit none
C        in sorting.SortTest self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(sorting.SortTest._ctor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(sorting.SortTest._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine sorting_SortTest__ctor2_fi(self, private_data, 
     &     exception)
        implicit none
C        in sorting.SortTest self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(sorting.SortTest._ctor2)
C       Insert-Code-Here {sorting.SortTest._ctor2} (_ctor2 method)
C       DO-NOT-DELETE splicer.end(sorting.SortTest._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine sorting_SortTest__dtor_fi(self, exception)
        implicit none
C        in sorting.SortTest self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(sorting.SortTest._dtor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(sorting.SortTest._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine sorting_SortTest__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(sorting.SortTest._load)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(sorting.SortTest._load)
        end


C       
C       Method:  stressTest[]
C       Perform the array stress test.
C       
C       Return true if all the algorithms work okay.
C       

        subroutine sorting_SortTest_stressTest_fi(algs, retval, 
     &     exception)
        implicit none
C        in array<sorting.SortingAlgorithm> algs
        integer*8 :: algs
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(sorting.SortTest.stressTest)
        integer*4 lower, upper, i
        integer*8 alg
        if (algs .ne. 0) then
           call sorting_SortingAlgorithm__array_lower_f(algs, 0, lower)
           call sorting_SortingAlgorithm__array_upper_f(algs, 0, upper)
           retval = .true.
           do i = lower, upper
              call sorting_SortingAlgorithm__array_get1_f(algs, i, alg)
              if (alg .ne. 0) then
                 call testAlgorithm(alg, retval)
              else
                 retval = .false.
              endif
           enddo
        endif
C       DO-NOT-DELETE splicer.end(sorting.SortTest.stressTest)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
