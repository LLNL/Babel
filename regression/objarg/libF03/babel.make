FSKELSRCS = objarg_Basic_fSkelf.F03 objarg_EmployeeArray_fSkelf.F03           \
  objarg_Employee_fSkelf.F03
IMPLSRCS = objarg_Basic_Impl.F03 objarg_EmployeeArray_Impl.F03                \
  objarg_Employee_Impl.F03
IORHDRS = objarg_Basic_IOR.h objarg_EmployeeArray_IOR.h objarg_Employee_IOR.h \
  objarg_IOR.h
IORSRCS = objarg_Basic_IOR.c objarg_EmployeeArray_IOR.c objarg_Employee_IOR.c
SKELSRCS = objarg_Basic_fSkel.c objarg_EmployeeArray_fSkel.c                  \
  objarg_Employee_fSkel.c
STUBHDRS = objarg_Basic_array_bindcdecls.h objarg_Basic_fAbbrev.h             \
  objarg_Basic_fStub.h objarg_Basic_mod_bindcdecls.h                          \
  objarg_EmployeeArray_array_bindcdecls.h objarg_EmployeeArray_fAbbrev.h      \
  objarg_EmployeeArray_fStub.h objarg_EmployeeArray_mod_bindcdecls.h          \
  objarg_Employee_array_bindcdecls.h objarg_Employee_fAbbrev.h                \
  objarg_Employee_fStub.h objarg_Employee_mod_bindcdecls.h
STUBMODULESRCS = objarg_Basic.F03 objarg_Employee.F03 objarg_EmployeeArray.F03
STUBSRCS = objarg_Basic_fStub.c objarg_EmployeeArray_fStub.c                  \
  objarg_Employee_fStub.c
TYPEMODULESRCS = objarg_Basic_type.F03 objarg_EmployeeArray_type.F03          \
  objarg_Employee_type.F03

_deps_objarg_Basic =  objarg_Basic_type
objarg_Basic$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_objarg_Basic))

_deps_objarg_Basic_Impl =  objarg_Basic
objarg_Basic_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_objarg_Basic_Impl))

_deps_objarg_Basic_fSkelf =  objarg_Basic_Impl objarg_Basic_type
objarg_Basic_fSkelf$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_objarg_Basic_fSkelf))

_deps_objarg_Employee =  objarg_Employee_type
objarg_Employee$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_objarg_Employee))

_deps_objarg_EmployeeArray =  objarg_EmployeeArray_type objarg_Employee_type
objarg_EmployeeArray$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_objarg_EmployeeArray))

_deps_objarg_EmployeeArray_Impl =  objarg_Employee objarg_EmployeeArray
objarg_EmployeeArray_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_objarg_EmployeeArray_Impl))

_deps_objarg_EmployeeArray_fSkelf =  objarg_EmployeeArray_Impl                \
  objarg_Employee_type objarg_EmployeeArray_type
objarg_EmployeeArray_fSkelf$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_objarg_EmployeeArray_fSkelf))

_deps_objarg_Employee_Impl =  objarg_Employee
objarg_Employee_Impl$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_objarg_Employee_Impl))

_deps_objarg_Employee_fSkelf =  objarg_Employee_Impl objarg_Employee_type
objarg_Employee_fSkelf$(MOD_SUFFIX) : $(addsuffix $(MOD_SUFFIX), $(_deps_objarg_Employee_fSkelf))

