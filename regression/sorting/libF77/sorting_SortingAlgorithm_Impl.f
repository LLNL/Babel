C       
C       File:          sorting_SortingAlgorithm_Impl.f
C       Symbol:        sorting.SortingAlgorithm-v0.1
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 7463M trunk)
C       Description:   Server-side implementation for sorting.SortingAlgorithm
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "sorting.SortingAlgorithm" (version 0.1)
C       
C       An abstract sorting algorithm.
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine sorting_SortingAlgorithm__ctor_fi(self, exception)
        implicit none
C        in sorting.SortingAlgorithm self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._ctor)
        integer*8 data, count, scount, tae
        call sorting_Counter__array_create1d_f(2, data)
        call sorting_SimpleCounter__create_f(scount, tae)
        call sorting_Counter__cast_f(scount, count, tae)
        call sorting_Counter__array_set1_f(data, 0, count)
        call sorting_Counter_deleteRef_f(count, tae)
        call sorting_SimpleCounter_deleteRef_f(scount, tae)
        call sorting_SimpleCounter__create_f(scount, tae)
        call sorting_Counter__cast_f(scount, count, tae)
        call sorting_Counter__array_set1_f(data, 1, count)
        call sorting_Counter_deleteRef_f(count, tae)
        call sorting_SimpleCounter_deleteRef_f(scount, tae)
        call sorting_SortingAlgorithm__set_data_f(self, data)
C       DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine sorting_SortingAlgorithm__ctor2_fi(self, 
     &     private_data, exception)
        implicit none
C        in sorting.SortingAlgorithm self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._ctor2)
C       Insert-Code-Here {sorting.SortingAlgorithm._ctor2} (_ctor2 method)
C       DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine sorting_SortingAlgorithm__dtor_fi(self, exception)
        implicit none
C        in sorting.SortingAlgorithm self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._dtor)
        integer*8 data
        call sorting_SortingAlgorithm__get_data_f(self, data)
        call sorting_Counter__array_deleteRef_f(data)
        data = 0
        call sorting_SortingAlgorithm__set_data_f(self, data)
C       DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine sorting_SortingAlgorithm__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm._load)
C       Insert the implementation here...
C       DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm._load)
        end


C       
C       Method:  getCompareCounter[]
C       Return the comparison counter.
C       

        subroutine sorting_SortingAlgorithm_getCompareCounter_fi(self, 
     &     retval, exception)
        implicit none
C        in sorting.SortingAlgorithm self
        integer*8 :: self
C        out sorting.Counter retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm.getCompareCounter)
        integer*8 data, counter
        call sorting_SortingAlgorithm__get_data_f(self, data)
        retval = 0
        if (data .ne. 0) then
           call sorting_Counter__array_get1_f(data, 0, retval)
        endif
C       DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm.getCompareCounter)
        end


C       
C       Method:  getSwapCounter[]
C       Return the swap counter.
C       

        subroutine sorting_SortingAlgorithm_getSwapCounter_fi(self, 
     &     retval, exception)
        implicit none
C        in sorting.SortingAlgorithm self
        integer*8 :: self
C        out sorting.Counter retval
        integer*8 :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm.getSwapCounter)
        integer*8 data, counter
        call sorting_SortingAlgorithm__get_data_f(self, data)
        retval = 0
        if (data .ne. 0) then
           call sorting_Counter__array_get1_f(data, 1, retval)
        endif
C       DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm.getSwapCounter)
        end


C       
C       Method:  reset[]
C       Reset the comparison and swap counter.
C       

        subroutine sorting_SortingAlgorithm_reset_fi(self, exception)
        implicit none
C        in sorting.SortingAlgorithm self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm.reset)
        integer*8 data, counter, tae
        integer*4 i
        call sorting_SortingAlgorithm__get_data_f(self, data)
        if (data .ne. 0) then
           do i = 0, 1
              call sorting_Counter__array_get1_f(data, i, counter)
              call sorting_Counter_reset_f(counter, tae)
              call sorting_Counter_deleteRef_f(counter, tae)
           enddo
        endif
C       DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm.reset)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Insert extra code here...
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
