C       
C       File:          knapsack_nwKnapsack_Impl.f
C       Symbol:        knapsack.nwKnapsack-v1.0
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 0)
C       Description:   Server-side implementation for knapsack.nwKnapsack
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "knapsack.nwKnapsack" (version 1.0)
C       
C       nwKnapsack:  An implementation of knapsack that drops about half 
C       of the input weights.
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
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
CC       Determine if all num entries in the list are positive, 
CC       returning .true.  if they are or .false. if they are not.
CC
C        logical function onlyPos(w, num)
C        implicit none
CC        in array<int> w
C        integer*8 w
CC        in num
C        integer*4 num
C
C        integer*4 i, wi, wLen
C        integer*4 sidlLength
C
C        onlyPos = .false.
C        if (w .ne. 0) then
C           wLen = sidlLength(w)
C           if ( (wLen .gt. 0) .and. (wLen .le. num) ) then
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
CC       not matter.  The length is assumed to be in the last element of the
CC       native weights array, nW, if hasLength is true.
CC
CC        logical function sameWeights(num, nW, sW)
C        logical function sameWeights(nW, sW, hasLength)
C        implicit none
CC        in array<int> nw, sW
C        integer*8 nW, sW
CC        in logical hasLength
C        logical hasLength
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
C              if (hasLength .eqv. .true.) then
C                 nLen = nLen - 1
C              endif
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
C                 sameWeights = onlyPos(p, nLen)
C              endif
C           endif
C        endif
C
C        return
C        end
C
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

        subroutine knapsack_nwKnapsack__ctor_fi(self, exception)
        implicit none
C        in knapsack.nwKnapsack self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack._ctor)
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
     &    l_pos_weights

C
C       In order to maintain the ACTUAL length without too much 
C       of a headache, the convention will be to add an entry
C       to the weights array and use it to store the number of
C       entries in the array.
C
        d_weights = createSIDLArray(max_weights+1, 0)
        call knapsack_nwKnapsack__set_data_f(self, d_weights)

        return
C       DO-NOT-DELETE splicer.end(knapsack.nwKnapsack._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine knapsack_nwKnapsack__ctor2_fi(self, private_data, 
     &     exception)
        implicit none
C        in knapsack.nwKnapsack self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack._ctor2)
        return
C       DO-NOT-DELETE splicer.end(knapsack.nwKnapsack._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine knapsack_nwKnapsack__dtor_fi(self, exception)
        implicit none
C        in knapsack.nwKnapsack self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack._dtor)
        integer*8 d_weights, throwaway_exception
        integer*4 max_weights
        character*36 l_dptr_missing
        character*40 l_max_weights
        character*39 l_pos_weights

C       TBD:  Does the following even work?
        common /consts/ max_weights, l_dptr_missing, l_max_weights,
     &    l_pos_weights


        call knapsack_nwKnapsack__get_data_f(self, d_weights)
        if (d_weights .ne. 0) then
            call sidl_int__array_deleteRef_f(d_weights)
            d_weights = 0
            call knapsack_nwKnapsack__set_data_f(self, d_weights)
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
C       DO-NOT-DELETE splicer.end(knapsack.nwKnapsack._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine knapsack_nwKnapsack__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack._load)
        return
C       DO-NOT-DELETE splicer.end(knapsack.nwKnapsack._load)
        end


C       
C       Method:  initialize[]
C       Initialize the knapsack with the specified weights, w.
C       

        subroutine knapsack_nwKnapsack_initialize_fi(self, w, exception)
        implicit none
C        in knapsack.nwKnapsack self
        integer*8 :: self
C        in array<int> w
        integer*8 :: w
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack.initialize)
        integer*8 d_weights, throwaway_exception
        integer*4 i, j, num, wi
        integer*4 max_weights, sidlLength
        character*36 l_dptr_missing
        character*40 l_max_weights
        character*39 l_pos_weights
        logical onlyPos

C       TBD:  Does the following even work?
        common /consts/ max_weights, l_dptr_missing, l_max_weights,
     &    l_pos_weights

        call knapsack_nwKnapsack__get_data_f(self, d_weights)
        if (d_weights .ne. 0) then
           if (w .ne. 0) then
              num = sidlLength(w)
              if (num .le. max_weights) then
                 if (onlyPos(w, num)) then
                    j = 0
                    do i = 0, num - 1
                       if (mod(i, 2) .eq. 1) then
                          call sidl_int__array_get1_f(w, i, wi)
                          call sidl_int__array_set1_f(d_weights, 
     &                                                j, wi)
                          j = j + 1
                       endif
                    enddo
C
C                   This is a hack.  The number of weights actually
C                   provided are kept in the max_weights index of
C                   the d_weights array since an additional entry
C                   is allocated to ensure room.
C
                    call sidl_int__array_set1_f(d_weights, 
     &                                          max_weights, j)
                    call knapsack_nwKnapsack__set_data_f(self, 
     &                                                   d_weights)
                 else
                    call knapsack_kBadWeightExcept__create_f
     &                     (exception, throwaway_exception)
                    if (exception .ne. 0) then
                       call knapsack_kBadWeightExcept_setNote_f(
     &                     exception, l_pos_weights,
     &                     throwaway_exception)
                       return
                    endif
                 endif
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
C       DO-NOT-DELETE splicer.end(knapsack.nwKnapsack.initialize)
        end


C       
C       Method:  onlyPosWeights[]
C       Return TRUE if all weights in the knapsack are positive;
C       otherwise, return FALSE.
C       

        subroutine knapsack_nwKnapsack_onlyPosWeights_fi(self, retval, 
     &     exception)
        implicit none
C        in knapsack.nwKnapsack self
        integer*8 :: self
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack.onlyPosWeights)
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
        call knapsack_nwKnapsack__get_data_f(self, d_weights)
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
C       DO-NOT-DELETE splicer.end(knapsack.nwKnapsack.onlyPosWeights)
        end


C       
C       Method:  hasWeights[]
C       Return TRUE if all of the specified weights, w, are in the knapsack
C       or there are no specified weights; otherwise, return FALSE.
C       

        subroutine knapsack_nwKnapsack_hasWeights_fi(self, w, retval, 
     &     exception)
        implicit none
C        in knapsack.nwKnapsack self
        integer*8 :: self
C        in array<int> w
        integer*8 :: w
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack.hasWeights)
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
        call knapsack_nwKnapsack__get_data_f(self, d_weights)
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
C       DO-NOT-DELETE splicer.end(knapsack.nwKnapsack.hasWeights)
        end


C       
C       Method:  hasSolution[]
C       Return TRUE if there is a solution for the specified target
C       weight; otherwise, return FALSE.  Recall a solution is a
C       subset of weights that total exactly to the specified target
C       weight.
C       

        subroutine knapsack_nwKnapsack_hasSolution_fi(self, t, retval, 
     &     exception)
        implicit none
C        in knapsack.nwKnapsack self
        integer*8 :: self
C        in int t
        integer*4 :: t
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(knapsack.nwKnapsack.hasSolution)
        integer*8 d_weights, throwaway_exception
        logical solve
        integer*4 max_weights, num
        character*36 l_dptr_missing
        character*40 l_max_weights
        character*39 l_pos_weights

C       TBD:  Does the following even work?
        common /consts/ max_weights, l_dptr_missing, l_max_weights,
     &    l_pos_weights

        retval = .false.
        call knapsack_nwKnapsack__get_data_f(self, d_weights)
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
C       DO-NOT-DELETE splicer.end(knapsack.nwKnapsack.hasSolution)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Nothing to do here
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
