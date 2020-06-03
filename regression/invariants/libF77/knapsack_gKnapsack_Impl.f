C       
C       File:          knapsack_gKnapsack_Impl.f
C       Symbol:        knapsack.gKnapsack-v1.0
C       Symbol Type:   class
C       Babel Version: 2.0.0 (Revision: 0)
C       Description:   Server-side implementation for knapsack.gKnapsack
C       
C       WARNING: Automatically generated; only changes within splicers preserved
C       
C       


C       
C       Symbol "knapsack.gKnapsack" (version 1.0)
C       
C       gKnapsack:  A good implementation of the knapsack interface.
C       


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_start)
C
C       Return a 1D SIDL array of the specified length, initialized
C       to the given value.
C
        integer*8 function createSIDLArray(num, val)
        implicit none
C        in int num
        integer*4 num
C        in int val
        integer*4 val

        integer*4 i

        call sidl_int__array_createRow_f(1, 0, num-1, createSIDLArray)
        do i = 0, num-1
           call sidl_int__array_set1_f(createSIDLArray, i, val)
        enddo

        return
        end

C
C       Return the length of what is assumed to be a 1D SIDL integer 
C       array.
C
        integer*4 function sidlLength(a)
        implicit none
C        in array<int> a
        integer*8 a

        integer*4 dimen, lower, upper

        sidlLength = 0
        if (a .ne. 0) then
           call sidl_int__array_lower_f(a, 0, lower)
           call sidl_int__array_upper_f(a, 0, upper)
           sidlLength = (upper - lower) + 1
        endif

        return
        end

C
C       Determine if all num entries in the list are positive, 
C       returning .true.  if they are or .false. if they are not.
C
        logical function onlyPos(w, num)
        implicit none
C        in array<int> w
        integer*8 w
C        in num
        integer*4 num

        integer*4 i, wi, wLen
        integer*4 sidlLength

        onlyPos = .false.
        if (w .ne. 0) then
           wLen = sidlLength(w)
           if ( (wLen .gt. 0) .and. (wLen .le. num) ) then
              onlyPos = .true.
              i = 0
              do while ( (i .lt. num) .and. onlyPos)
                 call sidl_int__array_get1_f(w, i, wi) 
                 if (wi .le. 0) then
                    onlyPos = .false.
                 endif
                 i = i + 1
              enddo
           endif
        endif

        return
        end

C
C       Check to see if the two lists (SIDL arrays) match where order does 
C       not matter.  The length is assumed to be in the last element of the
C       native weights array, nW, if hasLength is true.
C
C        logical function sameWeights(num, nW, sW)
C        logical function sameWeights(nW, sW)
        logical function sameWeights(nW, sW, hasLength)
        implicit none
C        in array<int> nw, sW
        integer*8 nW, sW
C        in logical hasLength
        logical hasLength

        integer*8 p, createSIDLArray
        integer*4 i, j, nDim, nLen, nLower, sDim, sLen, sLower
        integer*4 pj, sidlLength, wn, ws
        logical found, onlyPos

        found = .false.
        sameWeights = .false.
        if ( (nW .ne. 0) .and. (sW .ne. 0) ) then
           call sidl_int__array_dimen_f(nW, nDim)
           call sidl_int__array_dimen_f(sW, sDim)
           if ( (nDim .eq. 1) .and. (sDim .eq. 1) ) then
              nLen = sidlLength(nW)
              if (hasLength .eqv. .true.) then
                 nLen = nLen - 1
              endif
              sLen = sidlLength(sW)
              if (nLen .eq. sLen) then
                 p = createSIDLArray(nLen, 0)
                 call sidl_int__array_lower_f(nW, 0, nLower)
                 call sidl_int__array_lower_f(sW, 0, sLower)
                 do i = 0, nLen - 1
                    call sidl_int__array_get1_f(sW, sLower+i, ws)
                    found = .false.
                    j = 0
                    do while ( (j .lt. nLen) .and. (.not. found) )
                       call sidl_int__array_get1_f(nW, nLower+j, wn)
                       call sidl_int__array_get1_f(p, j, pj)
                       if ( (ws .eq. wn) .and. (pj .eq. 0) ) then
                          call sidl_int__array_set1_f(p, j, 1)
                          found = .true.
                       endif
                       j = j + 1
                    enddo
                 enddo
                 sameWeights = onlyPos(p)
              endif
           endif
        endif

        return
        end

C
C       Non-recursive implementation of the simplified knapsack 
C       problem.
C
C       Based on the algorithm defined in "Data Structures and
C       Algorithms" by Aho, Hopcroft, and Ullman (c) 1983.
C
C
        logical function solve(w, t, i, n)
        implicit none
C        in array<int> w
        integer*8 w
C        in int t
        integer*4 t, i, n

        solve = .false.
C
C       ToDo/TLD:  Need non-recursive version
C
C       Recursive version:
C
C       solve = .false.
C       if (t .eq. 0) then
C          solve = .true.
C       else if ( (t .lt. 0) .or. (i .ge. n) ) then
C          solve = .false.
C       else if (solve(w, t-w(i), i+1, n)) then
C          solve = .true.
C       else
C          solve = solve(w, t, i+1, n)
C       endif
C

        return
        end

C       DO-NOT-DELETE splicer.end(_miscellaneous_code_start)



C       
C       Method:  _ctor[]
C       Class constructor called when the class is created.
C       

        subroutine knapsack_gKnapsack__ctor_fi(self, exception)
        implicit none
C        in knapsack.gKnapsack self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._ctor)
        integer*8 d_weights, createSIDLArray
        integer*4 max_weights
        character*36 l_dptr_missing
        character*40 l_max_weights
        character*39 l_pos_weights
        data l_dptr_missing /'Private data is erroneously missing.'/
        data l_max_weights /'Cannot exceed maximum number of weights.'/
        data l_pos_weights /'Non-positive weights are NOT supported.'/
        data max_weights /10/

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
        call knapsack_gKnapsack__set_data_f(self, d_weights)

        return
C       DO-NOT-DELETE splicer.end(knapsack.gKnapsack._ctor)
        end


C       
C       Method:  _ctor2[]
C       Special Class constructor called when the user wants to wrap his own private data.
C       

        subroutine knapsack_gKnapsack__ctor2_fi(self, private_data, 
     &     exception)
        implicit none
C        in knapsack.gKnapsack self
        integer*8 :: self
C        in opaque private_data
        integer*8 :: private_data
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._ctor2)
        return
C       DO-NOT-DELETE splicer.end(knapsack.gKnapsack._ctor2)
        end


C       
C       Method:  _dtor[]
C       Class destructor called when the class is deleted.
C       

        subroutine knapsack_gKnapsack__dtor_fi(self, exception)
        implicit none
C        in knapsack.gKnapsack self
        integer*8 :: self
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._dtor)
        integer*8 d_weights, throwaway_exception
        integer*4 max_weights
        character*36 l_dptr_missing
        character*40 l_max_weights
        character*39 l_pos_weights

C       TBD:  Does the following even work?
        common /consts/ max_weights, l_dptr_missing, l_max_weights,
     &    l_pos_weights


        call knapsack_gKnapsack__get_data_f(self, d_weights)
        if (d_weights .ne. 0) then
            call sidl_int__array_deleteRef_f(d_weights)
            d_weights = 0
            call knapsack_gKnapsack__set_data_f(self, d_weights)
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
C       DO-NOT-DELETE splicer.end(knapsack.gKnapsack._dtor)
        end


C       
C       Method:  _load[]
C       Static class initializer called exactly once before any user-defined method is dispatched
C       

        subroutine knapsack_gKnapsack__load_fi(exception)
        implicit none
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(knapsack.gKnapsack._load)
        return
C       DO-NOT-DELETE splicer.end(knapsack.gKnapsack._load)
        end


C       
C       Method:  initialize[]
C       Initialize the knapsack with the specified weights, w.
C       

        subroutine knapsack_gKnapsack_initialize_fi(self, w, exception)
        implicit none
C        in knapsack.gKnapsack self
        integer*8 :: self
C        in array<int> w
        integer*8 :: w
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.initialize)
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

        call knapsack_gKnapsack__get_data_f(self, d_weights)
        if (d_weights .ne. 0) then
           if (w .ne. 0) then
              num = sidlLength(w)
              if (num .le. max_weights) then
                 if (onlyPos(w)) then
                    j = 0
                    do i = 0, num - 1
                       call sidl_int__array_get1_f(w, i, wi)
                       call sidl_int__array_set1_f(d_weights, j, wi)
                       j = j + 1
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
C       DO-NOT-DELETE splicer.end(knapsack.gKnapsack.initialize)
        end


C       
C       Method:  onlyPosWeights[]
C       Return TRUE if all weights in the knapsack are positive;
C       otherwise, return FALSE.
C       

        subroutine knapsack_gKnapsack_onlyPosWeights_fi(self, retval, 
     &     exception)
        implicit none
C        in knapsack.gKnapsack self
        integer*8 :: self
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.onlyPosWeights)
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
        call knapsack_gKnapsack__get_data_f(self, d_weights)
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
C       DO-NOT-DELETE splicer.end(knapsack.gKnapsack.onlyPosWeights)
        end


C       
C       Method:  hasWeights[]
C       Return TRUE if all of the specified weights, w, are in the knapsack
C       or there are no specified weights; otherwise, return FALSE.
C       

        subroutine knapsack_gKnapsack_hasWeights_fi(self, w, retval, 
     &     exception)
        implicit none
C        in knapsack.gKnapsack self
        integer*8 :: self
C        in array<int> w
        integer*8 :: w
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.hasWeights)
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
        call knapsack_gKnapsack__get_data_f(self, d_weights)
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
C       DO-NOT-DELETE splicer.end(knapsack.gKnapsack.hasWeights)
        end


C       
C       Method:  hasSolution[]
C       Return TRUE if there is a solution for the specified target
C       weight; otherwise, return FALSE.  Recall a solution is a
C       subset of weights that total exactly to the specified target
C       weight.
C       

        subroutine knapsack_gKnapsack_hasSolution_fi(self, t, retval, 
     &     exception)
        implicit none
C        in knapsack.gKnapsack self
        integer*8 :: self
C        in int t
        integer*4 :: t
C        out bool retval
        logical :: retval
C        out sidl.BaseInterface exception
        integer*8 :: exception

C       DO-NOT-DELETE splicer.begin(knapsack.gKnapsack.hasSolution)
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
        call knapsack_gKnapsack__get_data_f(self, d_weights)
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
C       DO-NOT-DELETE splicer.end(knapsack.gKnapsack.hasSolution)
        end


C       DO-NOT-DELETE splicer.begin(_miscellaneous_code_end)
C       Nothing to do here
C       DO-NOT-DELETE splicer.end(_miscellaneous_code_end)
