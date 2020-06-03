C       
C       File:          knapsack_npKnapsack_Impl.f
C       Symbol:        knapsack.npKnapsack-v1.0
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 0)
C       Description:   Server-side implementation for knapsack.npKnapsack
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "knapsack.npKnapsack" (version 1.0)
C       
C       npKnapsack:  An implementation of knapsack that allows non-positive
C       weights.
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
C
CC
CC       Return a 1D SIDL array of the specified length, initialized
CC       to the given value.
CC
C        integer*8 function createSIDLArray(num, val)
C        implicit none
CC        in int num
C        integer*4 num
CC        in int val
C        integer*4 val
C
C        integer*4 i
C
C        call sidl_int__array_createRow_f(1, 0, num-1, createSIDLArray)
C        do i = 0, num-1
C           call sidl_int__array_set1_f(createSIDLArray, i, val)
C        enddo
C
C        return
C        end
C
CC
CC       Return the length of what is assumed to be a 1D SIDL integer 
CC       array.
CC
C        integer*4 function sidlLength(a)
C        implicit none
CC        in array<int> a
C        integer*8 a
C
C        integer*4 dimen, lower, upper
C
C        sidlLength = 0
C        if (a .ne. 0) then
C           call sidl_int__array_lower_f(a, 0, lower)
C           call sidl_int__array_upper_f(a, 0, upper)
C           sidlLength = (upper - lower) + 1
C        endif
C
C        return
C        end
C
CC
CC       Determine if all entries in the list are positive, returning .true.
CC       if they are or .false. if they are not.
CC
C        logical function onlyPos(w)
C        implicit none
CC        in array<int> w
C        integer*8 w
C
C        integer*4 num
C        integer*4 i, wi
C        integer*4 sidlLength
C
C        onlyPos = .false.
C        if (w .ne. 0) then
C           num = sidlLength(w)
C           if (num .gt. 0) then
C              onlyPos = .true.
C              i = 0
C              do while ( (i .lt. num) .and. onlyPos)
C                 call sidl_int__array_get1_f(w, i, wi)
C                 if (wi .le. 0) then
C                    onlyPos = .false.
C                 endif
C                 i = i + 1
C              enddo
C           endif
C        endif
C
C        return
C        end
C
CC
CC       Check to see if the two lists (SIDL arrays) match where order does 
CC       not matter.
CC
CC        logical function sameWeights(num, nW, sW)
C        logical function sameWeights(nW, sW)
C        implicit none
CC        in array<int> nw, sW
C        integer*8 nW, sW
C
C        integer*8 p, createSIDLArray
C        integer*4 i, j, nDim, nLen, nLower, sDim, sLen, sLower
C        integer*4 pj, sidlLength, wn, ws
C        logical found, onlyPos
C
C        found = .false.
C        sameWeights = .false.
C        if ( (nW .ne. 0) .and. (sW .ne. 0) ) then
C           call sidl_int__array_dimen_f(nW, nDim)
C           call sidl_int__array_dimen_f(sW, sDim)
C           if ( (nDim .eq. 1) .and. (sDim .eq. 1) ) then
C              nLen = sidlLength(nW)
C              sLen = sidlLength(sW)
C              if (nLen .eq. sLen) then
C                 p = createSIDLArray(nLen, 0)
C                 call sidl_int__array_lower_f(nW, 0, nLower)
C                 call sidl_int__array_lower_f(sW, 0, sLower)
C                 do i = 0, nLen - 1
C                    call sidl_int__array_get1_f(sW, sLower+i, ws)
C                    found = .false.
C                    j = 0
C                    do while ( (j .lt. nLen) .and. (.not. found) )
C                       call sidl_int__array_get1_f(nW, nLower+j, wn)
C                       call sidl_int__array_get1_f(p, j, pj)
C                       if ( (ws .eq. wn) .and. (pj .eq. 0) ) then
C                          call sidl_int__array_set1_f(p, j, 1)
C                          found = .true.
C                       endif
C                       j = j + 1
C                    enddo
C                 enddo
C                 sameWeights = onlyPos(p)
C              endif
C           endif
C        endif
C
C        return
C        end
CC
CC       Non-recursive implementation of the simplified knapsack 
CC       problem.
CC
CC       Based on the algorithm defined in "Data Structures and
CC       Algorithms" by Aho, Hopcroft, and Ullman (c) 1983.
CC
CC
C        logical function solve(w, t, i, n)
C        implicit none
CC        in array<int> w
C        integer*8 w
CC        in int t
C        integer*4 t, i, n
C
C        solve = .false.
CC
CC       ToDo/TLD:  Need non-recursive version
CC
CC       Recursive version:
CC
CC       solve = .false.
CC       if (t .eq. 0) then
CC          solve = .true.
CC       else if ( (t .lt. 0) .or. (i .ge. n) ) then
CC          solve = .false.
CC       else if (solve(w, t-w(i), i+1, n)) then
CC          solve = .true.
CC       else
CC          solve = solve(w, t, i+1, n)
CC       endif
CC
C
C        return
C        end

C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine knapsack_npKnapsack__ctor_fi(self, exception)
        implicit none
C        in knapsack.npKnapsack self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._ctor)
        integer*8 d_weights, createSIDLArray
        integer*4 max_weights
        character*36 l_dptr_missing
        character*40 l_max_weights
        character*39 l_pos_weights
C        data l_dptr_missing /'Private data is erroneously missing.'/
C        data l_max_weights /'Cannot exceed maximum number of weights.'/
C        data l_pos_weights /'Non-positive weights are NOT supported.'/
C        data max_weights /10/

C       TBD:  Does the following even work?
        common /consts/ max_weights, l_dptr_missing, l_max_weights,
     &     l_pos_weights

C
C       In order to maintain the ACTUAL length without too much 
C       of a headache, the convention will be to add an entry
C       to the weights array and use it to store the number of
C       entries in the array.
C
        d_weights = createSIDLArray(max_weights+1, 0)
        call knapsack_npKnapsack__set_data_f(self, d_weights)
        return
C       DO-NOT-DELETE splicer.end(knapsack.npKnapsack._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine knapsack_npKnapsack__ctor2_fi(self, private_data, 
     &     exception)
        implicit none
C        in knapsack.npKnapsack self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._ctor2)
        return
C       DO-NOT-DELETE splicer.end(knapsack.npKnapsack._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine knapsack_npKnapsack__dtor_fi(self, exception)
        implicit none
C        in knapsack.npKnapsack self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._dtor)
        integer*8 d_weights, throwaway_exception
        integer*4 max_weights
        character*36 l_dptr_missing
        character*40 l_max_weights
        character*39 l_pos_weights

C       TBD:  Does the following even work?
        common /consts/ max_weights, l_dptr_missing, l_max_weights,
     &    l_pos_weights

        call knapsack_npKnapsack__get_data_f(self, d_weights)
        if (d_weights .ne. 0) then
            call sidl_int__array_deleteRef_f(d_weights)
            d_weights = 0
            call knapsack_npKnapsack__set_data_f(self, d_weights)
        else
            call knapsack_kExcept__create_f
     &             (exception, throwaway_exception)
            if (exception .ne. 0) then
               call knapsack_kExcept_setNote_f(
     &             exception, l_dptr_missing,
     &             throwaway_exception)
              return
           endif
        endif

        return
C       DO-NOT-DELETE splicer.end(knapsack.npKnapsack._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine knapsack_npKnapsack__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(knapsack.npKnapsack._load)
        return
C       DO-NOT-DELETE splicer.end(knapsack.npKnapsack._load)
        end


C       
C       Method:  initialize[]
C       Initialize the knapsack with the specified weights, w.
C       

        subroutine knapsack_npKnapsack_initialize_fi(self, w, exception)
        implicit none
C        in knapsack.npKnapsack self
        integer*8 :: self
C        in array<int> w
        integer*8 :: w
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.initialize)
        integer*8 d_weights, throwaway_exception
        integer*4 i, j, num, wi
        integer*4 max_weights, sidlLength
        character*36 l_dptr_missing
        character*40 l_max_weights
        character*39 l_pos_weights

C       TBD:  Does the following even work?
        common /consts/ max_weights, l_dptr_missing, l_max_weights,
     &    l_pos_weights

        call knapsack_npKnapsack__get_data_f(self, d_weights)
        if (d_weights .ne. 0) then
           if (w .ne. 0) then
              num = sidlLength(w)
              if (num .le. max_weights) then
                 j = 0
                 do i = 0, num - 1
                    call sidl_int__array_get1_f(w, i, wi)
                    call sidl_int__array_set1_f(d_weights, j, wi)
                    j = j + 1
                 enddo
C
C                This is a hack.  The number of weights actually
C                provided are kept in the max_weights index of
C                the d_weights array since an additional entry
C                is allocated to ensure room.
C
                 call sidl_int__array_set1_f(d_weights,
     &                                       max_weights, j)
                 call knapsack_nwKnapsack__set_data_f(self,
     &                                                d_weights)
              else
                 call knapsack_kSizeExcept__create_f
     &                  (exception, throwaway_exception)
                 if (exception .ne. 0) then
                    call knapsack_kSizeExcept_setNote_f(
     &                  exception, l_max_weights,
     &                  throwaway_exception)
                    return
                 endif
              endif
           endif
        else
            call knapsack_kExcept__create_f
     &             (exception, throwaway_exception)
            if (exception .ne. 0) then
               call knapsack_kExcept_setNote_f(
     &             exception, l_dptr_missing,
     &             throwaway_exception)
              return
           endif
        endif

        return
C       DO-NOT-DELETE splicer.end(knapsack.npKnapsack.initialize)
        end


C       
C       Method:  onlyPosWeights[]
C       Return TRUE if all weights in the knapsack are positive;
C       otherwise, return FALSE.
C       

        subroutine knapsack_npKnapsack_onlyPosWeights_fi(self, retval, 
     &     exception)
        implicit none
C        in knapsack.npKnapsack self
        integer*8 :: self
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.onlyPosWeights)
        integer*8 d_weights, throwaway_exception
        integer*4 max_weights, num
        logical onlyPos
        character*36 l_dptr_missing
        character*40 l_max_weights
        character*39 l_pos_weights

C       TBD:  Does the following even work?
        common /consts/ max_weights, l_dptr_missing, l_max_weights,
     &    l_pos_weights

        retval = .false.
        call knapsack_npKnapsack__get_data_f(self, d_weights)
        if (d_weights .ne. 0) then
           call sidl_int__array_get1_f(d_weights, max_weights, num)
           retval = onlyPos(d_weights, num)
        else
            call knapsack_kExcept__create_f
     &             (exception, throwaway_exception)
            if (exception .ne. 0) then
               call knapsack_kExcept_setNote_f(
     &             exception, l_dptr_missing,
     &             throwaway_exception)
              return
           endif
        endif

        return
C       DO-NOT-DELETE splicer.end(knapsack.npKnapsack.onlyPosWeights)
        end


C       
C       Method:  hasWeights[]
C       Return TRUE if all of the specified weights, w, are in the knapsack
C       or there are no specified weights; otherwise, return FALSE.
C       

        subroutine knapsack_npKnapsack_hasWeights_fi(self, w, retval, 
     &     exception)
        implicit none
C        in knapsack.npKnapsack self
        integer*8 :: self
C        in array<int> w
        integer*8 :: w
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.hasWeights)
        integer*8 d_weights, throwaway_exception
        logical sameWeights
        integer*4 max_weights
        character*36 l_dptr_missing
        character*40 l_max_weights
        character*39 l_pos_weights

C       TBD:  Does the following even work?
        common /consts/ max_weights, l_dptr_missing, l_max_weights,
     &    l_pos_weights

        retval = .false.
        call knapsack_npKnapsack__get_data_f(self, d_weights)
        if (d_weights .ne. 0) then
           if (w .ne. 0) then
              retval = sameWeights(d_weights, w, .true.)
           endif
        else
            call knapsack_kExcept__create_f
     &             (exception, throwaway_exception)
            if (exception .ne. 0) then
               call knapsack_kExcept_setNote_f(
     &             exception, l_dptr_missing,
     &             throwaway_exception)
              return
           endif
        endif

        return
C       DO-NOT-DELETE splicer.end(knapsack.npKnapsack.hasWeights)
        end


C       
C       Method:  hasSolution[]
C       Return TRUE if there is a solution for the specified target
C       weight; otherwise, return FALSE.  Recall a solution is a
C       subset of weights that total exactly to the specified target
C       weight.
C       

        subroutine knapsack_npKnapsack_hasSolution_fi(self, t, retval, 
     &     exception)
        implicit none
C        in knapsack.npKnapsack self
        integer*8 :: self
C        in int t
        integer*4 :: t
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(knapsack.npKnapsack.hasSolution)
        integer*8 d_weights, throwaway_exception
        logical solve
        integer*4 max_weights, num
        character*36 l_dptr_missing
        character*40 l_max_weights
        character*39 l_pos_weights

C       TBD:  Does the following even work?
        common /consts/ max_weights, l_dptr_missing, l_max_weights,
     &     l_pos_weights

        retval = .false.
        call knapsack_npKnapsack__get_data_f(self, d_weights)
        if (d_weights .ne. 0) then
C
C          Remember:  The actual number of entries in the array are
C          maintained in the max_weights index of d_weights.
C
           call sidl_int__array_get1_f(d_weights, max_weights, num)
           retval = solve(d_weights, t, 0, num)
        else
            call knapsack_kExcept__create_f
     &             (exception, throwaway_exception)
            if (exception .ne. 0) then
               call knapsack_kExcept_setNote_f(
     &             exception, l_dptr_missing,
     &             throwaway_exception)
              return
           endif
        endif

        return
C       DO-NOT-DELETE splicer.end(knapsack.npKnapsack.hasSolution)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Nothing to do here
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
