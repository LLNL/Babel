! 
! File:          sorting_SortingAlgorithm_Mod.F90
! Symbol:        sorting.SortingAlgorithm-v0.1
! Symbol Type:   class
! Babel Version: 2.0.0 (Revision: 7463M trunk)
! Description:   Server-side private data module for sorting.SortingAlgorithm
! 
! WARNING: Automatically generated; only changes within splicers preserved
! 
! 

#include "sidl_BaseInterface_fAbbrev.h"
#include "sorting_SortingAlgorithm_fAbbrev.h"

! DO-NOT-DELETE splicer.begin(_hincludes)
! Insert-Code-Here {_hincludes} (include files)
! DO-NOT-DELETE splicer.end(_hincludes)

module sorting_SortingAlgorithm_impl

  ! DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm.use)
  use sorting_Counter_type
  ! DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm.use)

  type sorting_SortingAlgorithm_priv
    sequence
    ! DO-NOT-DELETE splicer.begin(sorting.SortingAlgorithm.private_data)
   type(sorting_Counter_t) :: d_compare
   type(sorting_Counter_t) :: d_swap
    ! DO-NOT-DELETE splicer.end(sorting.SortingAlgorithm.private_data)
  end type sorting_SortingAlgorithm_priv

  type sorting_SortingAlgorithm_wrap
    sequence
    type(sorting_SortingAlgorithm_priv), pointer :: d_private_data
  end type sorting_SortingAlgorithm_wrap

end module sorting_SortingAlgorithm_impl
