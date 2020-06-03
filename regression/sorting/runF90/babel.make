IORHDRS = sorting_CompInt_IOR.h sorting_Comparator_IOR.h                      \
  sorting_Container_IOR.h sorting_Counter_IOR.h sorting_Heapsort_IOR.h        \
  sorting_IOR.h sorting_IntegerContainer_IOR.h sorting_Integer_IOR.h          \
  sorting_Mergesort_IOR.h sorting_Quicksort_IOR.h sorting_SimpleCounter_IOR.h \
  sorting_SortTest_IOR.h sorting_SortingAlgorithm_IOR.h
STUBHDRS = sorting_CompInt_array_bindcdecls.h sorting_CompInt_fAbbrev.h       \
  sorting_CompInt_fStub.h sorting_CompInt_mod_bindcdecls.h                    \
  sorting_Comparator_array_bindcdecls.h sorting_Comparator_fAbbrev.h          \
  sorting_Comparator_fStub.h sorting_Comparator_mod_bindcdecls.h              \
  sorting_Container_array_bindcdecls.h sorting_Container_fAbbrev.h            \
  sorting_Container_fStub.h sorting_Container_mod_bindcdecls.h                \
  sorting_Counter_array_bindcdecls.h sorting_Counter_fAbbrev.h                \
  sorting_Counter_fStub.h sorting_Counter_mod_bindcdecls.h                    \
  sorting_Heapsort_array_bindcdecls.h sorting_Heapsort_fAbbrev.h              \
  sorting_Heapsort_fStub.h sorting_Heapsort_mod_bindcdecls.h                  \
  sorting_IntegerContainer_array_bindcdecls.h                                 \
  sorting_IntegerContainer_fAbbrev.h sorting_IntegerContainer_fStub.h         \
  sorting_IntegerContainer_mod_bindcdecls.h                                   \
  sorting_Integer_array_bindcdecls.h sorting_Integer_fAbbrev.h                \
  sorting_Integer_fStub.h sorting_Integer_mod_bindcdecls.h                    \
  sorting_Mergesort_array_bindcdecls.h sorting_Mergesort_fAbbrev.h            \
  sorting_Mergesort_fStub.h sorting_Mergesort_mod_bindcdecls.h                \
  sorting_Quicksort_array_bindcdecls.h sorting_Quicksort_fAbbrev.h            \
  sorting_Quicksort_fStub.h sorting_Quicksort_mod_bindcdecls.h                \
  sorting_SimpleCounter_array_bindcdecls.h sorting_SimpleCounter_fAbbrev.h    \
  sorting_SimpleCounter_fStub.h sorting_SimpleCounter_mod_bindcdecls.h        \
  sorting_SortTest_array_bindcdecls.h sorting_SortTest_fAbbrev.h              \
  sorting_SortTest_fStub.h sorting_SortTest_mod_bindcdecls.h                  \
  sorting_SortingAlgorithm_array_bindcdecls.h                                 \
  sorting_SortingAlgorithm_fAbbrev.h sorting_SortingAlgorithm_fStub.h         \
  sorting_SortingAlgorithm_mod_bindcdecls.h
STUBMODULESRCS = sorting_CompInt.F90 sorting_Comparator.F90                   \
  sorting_Container.F90 sorting_Counter.F90 sorting_Heapsort.F90              \
  sorting_Integer.F90 sorting_IntegerContainer.F90 sorting_Mergesort.F90      \
  sorting_Quicksort.F90 sorting_SimpleCounter.F90 sorting_SortTest.F90        \
  sorting_SortingAlgorithm.F90
STUBSRCS = sorting_CompInt_fStub.c sorting_Comparator_fStub.c                 \
  sorting_Container_fStub.c sorting_Counter_fStub.c sorting_Heapsort_fStub.c  \
  sorting_IntegerContainer_fStub.c sorting_Integer_fStub.c                    \
  sorting_Mergesort_fStub.c sorting_Quicksort_fStub.c                         \
  sorting_SimpleCounter_fStub.c sorting_SortTest_fStub.c                      \
  sorting_SortingAlgorithm_fStub.c
TYPEMODULESRCS = sorting_CompInt_type.F90 sorting_Comparator_type.F90         \
  sorting_Container_type.F90 sorting_Counter_type.F90                         \
  sorting_Heapsort_type.F90 sorting_IntegerContainer_type.F90                 \
  sorting_Integer_type.F90 sorting_Mergesort_type.F90                         \
  sorting_Quicksort_type.F90 sorting_SimpleCounter_type.F90                   \
  sorting_SortTest_type.F90 sorting_SortingAlgorithm_type.F90

_deps_sorting_CompInt =  sorting_Comparator_type sorting_CompInt_type
sorting_CompInt$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sorting_CompInt))

_deps_sorting_Comparator =  sorting_Comparator_type
sorting_Comparator$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sorting_Comparator))

_deps_sorting_Container =  sorting_Comparator_type sorting_Container_type
sorting_Container$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sorting_Container))

_deps_sorting_Counter =  sorting_Counter_type
sorting_Counter$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sorting_Counter))

_deps_sorting_Heapsort =  sorting_Counter_type sorting_SortingAlgorithm_type  \
  sorting_Comparator_type sorting_Container_type sorting_Heapsort_type
sorting_Heapsort$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sorting_Heapsort))

_deps_sorting_Integer =  sorting_Integer_type
sorting_Integer$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sorting_Integer))

_deps_sorting_IntegerContainer =  sorting_IntegerContainer_type               \
  sorting_Comparator_type sorting_Container_type
sorting_IntegerContainer$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sorting_IntegerContainer))

_deps_sorting_Mergesort =  sorting_Mergesort_type sorting_Counter_type        \
  sorting_SortingAlgorithm_type sorting_Comparator_type sorting_Container_type
sorting_Mergesort$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sorting_Mergesort))

_deps_sorting_Quicksort =  sorting_Counter_type sorting_SortingAlgorithm_type \
  sorting_Comparator_type sorting_Quicksort_type sorting_Container_type
sorting_Quicksort$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sorting_Quicksort))

_deps_sorting_SimpleCounter =  sorting_Counter_type sorting_SimpleCounter_type
sorting_SimpleCounter$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sorting_SimpleCounter))

_deps_sorting_SortTest =  sorting_SortTest_type sorting_SortingAlgorithm_type
sorting_SortTest$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sorting_SortTest))

_deps_sorting_SortingAlgorithm =  sorting_Counter_type                        \
  sorting_SortingAlgorithm_type sorting_Comparator_type sorting_Container_type
sorting_SortingAlgorithm$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_sorting_SortingAlgorithm))

