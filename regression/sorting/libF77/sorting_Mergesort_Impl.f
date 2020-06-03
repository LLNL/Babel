C       
C       File:          sorting_Mergesort_Impl.f
C       Symbol:        sorting.Mergesort-v0.1
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7463M trunk)
C       Description:   Server-side implementation for sorting.Mergesort
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "sorting.Mergesort" (version 0.1)
C       
C       Merge sort
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
C This file contains an interative (non-recursive) version of mergesort.

      subroutine mergeLists(elems, comp, cmp, swp, start, mid, last)
      implicit none
      integer*8 elems, comp, cmp, swp, tae
      integer*4 start, mid, last, j
      integer*4 compres, counter
      integer*4 m, s
      m = mid
      s = start
      do while ((s .lt. m) .and. (m .lt. last))
         call sorting_Counter_inc_f(cmp, counter, tae)
         call sorting_Container_compare_f(elems, s, m, comp,
     $        compres, tae)
         if (compres .gt. 0) then
            do j = m, s + 1, -1
               call sorting_Counter_inc_f(swp, counter, tae)
               call sorting_Container_swap_f(elems, j, j - 1, tae)
            enddo
            m = m + 1
         endif
         s = s + 1
      enddo
      end
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine sorting_Mergesort__ctor_fi(self, exception)
        implicit none
C        in sorting.Mergesort self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(sorting.Mergesort._ctor)
C       DO-NOT-DELETE splicer.end(sorting.Mergesort._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine sorting_Mergesort__ctor2_fi(self, private_data, 
     &     exception)
        implicit none
C        in sorting.Mergesort self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(sorting.Mergesort._ctor2)
C       Insert-Code-Here {sorting.Mergesort._ctor2} (_ctor2 method)
C       DO-NOT-DELETE splicer.end(sorting.Mergesort._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine sorting_Mergesort__dtor_fi(self, exception)
        implicit none
C        in sorting.Mergesort self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(sorting.Mergesort._dtor)
C       DO-NOT-DELETE splicer.end(sorting.Mergesort._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine sorting_Mergesort__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(sorting.Mergesort._load)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(sorting.Mergesort._load)
        end


C       
C       Method:  sort[]
C       Sort elements using Merge Sort.
C       

        subroutine sorting_Mergesort_sort_fi(self, elems, comp, 
     &     exception)
        implicit none
C        in sorting.Mergesort self
        integer*8 :: self
C        in sorting.Container elems
        integer*8 :: elems
C        in sorting.Comparator comp
        integer*8 :: comp
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(sorting.Mergesort.sort)
        integer*8 cmp, swp, tae
        integer*4 stride, i, mid, last, len
        mid = 1
        stride = 2
        call sorting_Container_getLength_f(elems, len, tae)
        call sorting_Mergesort_getCompareCounter_f(self, cmp, tae)
        call sorting_Mergesort_getSwapCounter_f(self, swp, tae)
        do while (mid .lt. len)
           do i = 0, len - mid, stride
              last = i + stride
              if (last .gt. len) then
                 last = len
              endif
              call mergeLists(elems, comp, cmp, swp,
     $             i, i + mid, last)
           enddo
           mid = stride
           stride = stride + stride
        enddo
        call sorting_Counter_deleteRef_f(cmp, tae)
        call sorting_Counter_deleteRef_f(swp, tae)
C       DO-NOT-DELETE splicer.end(sorting.Mergesort.sort)
        end


C       
C       Method:  getName[]
C       Return merge sorting.
C       

        subroutine sorting_Mergesort_getName_fi(self, retval, exception)
        implicit none
C        in sorting.Mergesort self
        integer*8 :: self
C        out string retval
        character*(*) :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(sorting.Mergesort.getName)
        retval = 'Merge sort (non-recursive)'
C       DO-NOT-DELETE splicer.end(sorting.Mergesort.getName)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
