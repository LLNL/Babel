C       
C       File:          sorting_Heapsort_Impl.f
C       Symbol:        sorting.Heapsort-v0.1
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7463M trunk)
C       Description:   Server-side implementation for sorting.Heapsort
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "sorting.Heapsort" (version 0.1)
C       
C       Heap sort
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
      subroutine remakeHeap(elem, comp, cmp, swp, last, first)
      implicit none
      integer*8 elem, comp, cmp, swp, tae
      integer*4 last, first, half, child, result, counter
      integer*4 f
      f = first
      half = (last/2) - 1
      do while (f .le. half) 
         child = f + f + 1
         if ((child + 1) .lt. last) then
            call sorting_Counter_inc_f(cmp, counter, tae)
            call sorting_Container_compare_f(elem, child, child+1,
     $           comp, result, tae)
            if (result .lt. 0) then
               child = child + 1
            endif
         endif
         call sorting_Counter_inc_f(cmp, counter, tae)
         call sorting_Container_compare_f(elem, f, child, comp,
     $        result, tae)
         if (result .ge. 0) goto 100
         call sorting_Counter_inc_f(swp, counter, tae)
         call sorting_Container_swap_f(elem, f, child, tae)
         f = child
      enddo
 100  return
      end
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine sorting_Heapsort__ctor_fi(self, exception)
        implicit none
C        in sorting.Heapsort self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(sorting.Heapsort._ctor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(sorting.Heapsort._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine sorting_Heapsort__ctor2_fi(self, private_data, 
     &     exception)
        implicit none
C        in sorting.Heapsort self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(sorting.Heapsort._ctor2)
C       Insert-Code-Here {sorting.Heapsort._ctor2} (_ctor2 method)
C       DO-NOT-DELETE splicer.end(sorting.Heapsort._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine sorting_Heapsort__dtor_fi(self, exception)
        implicit none
C        in sorting.Heapsort self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(sorting.Heapsort._dtor)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(sorting.Heapsort._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine sorting_Heapsort__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(sorting.Heapsort._load)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(sorting.Heapsort._load)
        end


C       
C       Method:  sort[]
C       Sort elements using Heap Sort.
C       

        subroutine sorting_Heapsort_sort_fi(self, elems, comp, 
     &     exception)
        implicit none
C        in sorting.Heapsort self
        integer*8 :: self
C        in sorting.Container elems
        integer*8 :: elems
C        in sorting.Comparator comp
        integer*8 :: comp
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(sorting.Heapsort.sort)
        integer*4 i, num, counter
        integer*8 cmp, swp, tae
        call sorting_Container_getLength_f(elems, num, tae)
        call sorting_Heapsort_getCompareCounter_f(self, cmp, tae)
        call sorting_Heapsort_getSwapCounter_f(self, swp, tae)
C     make the heap
        do i = ((num / 2) - 1), 0, -1
           call remakeHeap(elems, comp, cmp, swp, num, i)
        enddo
C     put top of heap at back and remake the heap
        i = num - 1
        do while (i .gt. 0)
           call sorting_Counter_inc_f(swp, counter, tae)
           call sorting_Container_swap_f(elems, 0, i, tae)
           call remakeHeap(elems, comp, cmp, swp, i, 0)
           i = i - 1
        enddo
        call sorting_Counter_deleteRef_f(cmp, tae)
        call sorting_Counter_deleteRef_f(swp, tae)
C       DO-NOT-DELETE splicer.end(sorting.Heapsort.sort)
        end


C       
C       Method:  getName[]
C       Return heap sorting.
C       

        subroutine sorting_Heapsort_getName_fi(self, retval, exception)
        implicit none
C        in sorting.Heapsort self
        integer*8 :: self
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(sorting.Heapsort.getName)
        retval = 'Heap sort'
C       DO-NOT-DELETE splicer.end(sorting.Heapsort.getName)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
