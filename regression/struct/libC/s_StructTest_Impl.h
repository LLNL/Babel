/*
 * File:          s_StructTest_Impl.h
 * Symbol:        s.StructTest-v1.0
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463M trunk)
 * Description:   Server-side implementation for s.StructTest
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

#ifndef included_s_StructTest_Impl_h
#define included_s_StructTest_Impl_h

#ifndef included_sidl_header_h
#include "sidl_header.h"
#endif
#ifndef included_s_StructTest_h
#include "s_StructTest.h"
#endif
#ifndef included_sidl_BaseClass_h
#include "sidl_BaseClass.h"
#endif
#ifndef included_sidl_BaseInterface_h
#include "sidl_BaseInterface.h"
#endif
#ifndef included_sidl_ClassInfo_h
#include "sidl_ClassInfo.h"
#endif
#ifndef included_sidl_RuntimeException_h
#include "sidl_RuntimeException.h"
#endif
/* DO-NOT-DELETE splicer.begin(s.StructTest._hincludes) */
/* DO-NOT-DELETE splicer.end(s.StructTest._hincludes) */

/*
 * Private data for class s.StructTest
 */

struct s_StructTest__data {
  /* DO-NOT-DELETE splicer.begin(s.StructTest._data) */
  int ignore; /* dummy to force non-empty struct; remove if you add data */
  /* DO-NOT-DELETE splicer.end(s.StructTest._data) */
};

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Access functions for class private data and built-in methods
 */

extern struct s_StructTest__data*
s_StructTest__get_data(
  s_StructTest);

extern void
s_StructTest__set_data(
  s_StructTest,
  struct s_StructTest__data*);

extern
void
impl_s_StructTest__load(
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_s_StructTest__ctor(
  /* in */ s_StructTest self,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_s_StructTest__ctor2(
  /* in */ s_StructTest self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex);

extern
void
impl_s_StructTest__dtor(
  /* in */ s_StructTest self,
  /* out */ sidl_BaseInterface *_ex);

/*
 * User-defined object methods
 */

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_s_StructTest_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern void
skel_s_StructTest_serialize_s_Simple(const struct s_Simple__data *strct, struct 
  sidl_rmi_Return__object *pipe, const char *name, sidl_bool copyArg, struct 
  sidl_BaseInterface__object * *exc);
extern void
skel_s_StructTest_serialize_s_Hard(const struct s_Hard__data *strct, struct 
  sidl_rmi_Return__object *pipe, const char *name, sidl_bool copyArg, struct 
  sidl_BaseInterface__object * *exc);
extern void
skel_s_StructTest_serialize_s_Combined(const struct s_Combined__data *strct, 
  struct sidl_rmi_Return__object *pipe, const char *name, sidl_bool copyArg, 
  struct sidl_BaseInterface__object * *exc);
extern void
skel_s_StructTest_serialize_s_Rarrays(const struct s_Rarrays__data *strct, 
  struct sidl_rmi_Return__object *pipe, const char *name, sidl_bool copyArg, 
  struct sidl_BaseInterface__object * *exc);
extern void
skel_s_StructTest_serialize_s_Empty(const struct s_Empty__data *strct, struct 
  sidl_rmi_Return__object *pipe, const char *name, sidl_bool copyArg, struct 
  sidl_BaseInterface__object * *exc);
extern void
skel_s_StructTest_deserialize_s_Simple(struct s_Simple__data *strct, struct 
  sidl_rmi_Call__object *pipe, const char *name, sidl_bool copyArg, struct 
  sidl_BaseInterface__object * *exc);
extern void
skel_s_StructTest_deserialize_s_Hard(struct s_Hard__data *strct, struct 
  sidl_rmi_Call__object *pipe, const char *name, sidl_bool copyArg, struct 
  sidl_BaseInterface__object * *exc);
extern void
skel_s_StructTest_deserialize_s_Combined(struct s_Combined__data *strct, struct 
  sidl_rmi_Call__object *pipe, const char *name, sidl_bool copyArg, struct 
  sidl_BaseInterface__object * *exc);
extern void
skel_s_StructTest_deserialize_s_Rarrays(struct s_Rarrays__data *strct, struct 
  sidl_rmi_Call__object *pipe, const char *name, sidl_bool copyArg, struct 
  sidl_BaseInterface__object * *exc);
extern void
skel_s_StructTest_deserialize_s_Empty(struct s_Empty__data *strct, struct 
  sidl_rmi_Call__object *pipe, const char *name, sidl_bool copyArg, struct 
  sidl_BaseInterface__object * *exc);
extern
struct s_Empty__data
impl_s_StructTest_returnEmpty(
  /* in */ s_StructTest self,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_s_StructTest_passinEmpty(
  /* in */ s_StructTest self,
  /* in */ const struct s_Empty__data* s1,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_s_StructTest_passoutEmpty(
  /* in */ s_StructTest self,
  /* out */ struct s_Empty__data* s1,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_s_StructTest_passinoutEmpty(
  /* in */ s_StructTest self,
  /* inout */ struct s_Empty__data* s1,
  /* out */ sidl_BaseInterface *_ex);

extern
struct s_Empty__data
impl_s_StructTest_passeverywhereEmpty(
  /* in */ s_StructTest self,
  /* in */ const struct s_Empty__data* s1,
  /* out */ struct s_Empty__data* s2,
  /* inout */ struct s_Empty__data* s3,
  /* out */ sidl_BaseInterface *_ex);

extern
struct s_Simple__data
impl_s_StructTest_returnSimple(
  /* in */ s_StructTest self,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_s_StructTest_passinSimple(
  /* in */ s_StructTest self,
  /* in */ const struct s_Simple__data* s1,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_s_StructTest_passoutSimple(
  /* in */ s_StructTest self,
  /* out */ struct s_Simple__data* s1,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_s_StructTest_passinoutSimple(
  /* in */ s_StructTest self,
  /* inout */ struct s_Simple__data* s1,
  /* out */ sidl_BaseInterface *_ex);

extern
struct s_Simple__data
impl_s_StructTest_passeverywhereSimple(
  /* in */ s_StructTest self,
  /* in */ const struct s_Simple__data* s1,
  /* out */ struct s_Simple__data* s2,
  /* inout */ struct s_Simple__data* s3,
  /* out */ sidl_BaseInterface *_ex);

extern
struct s_Hard__data
impl_s_StructTest_returnHard(
  /* in */ s_StructTest self,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_s_StructTest_passinHard(
  /* in */ s_StructTest self,
  /* in */ const struct s_Hard__data* s1,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_s_StructTest_passoutHard(
  /* in */ s_StructTest self,
  /* out */ struct s_Hard__data* s1,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_s_StructTest_passinoutHard(
  /* in */ s_StructTest self,
  /* inout */ struct s_Hard__data* s1,
  /* out */ sidl_BaseInterface *_ex);

extern
struct s_Hard__data
impl_s_StructTest_passeverywhereHard(
  /* in */ s_StructTest self,
  /* in */ const struct s_Hard__data* s1,
  /* out */ struct s_Hard__data* s2,
  /* inout */ struct s_Hard__data* s3,
  /* out */ sidl_BaseInterface *_ex);

extern
struct s_Combined__data
impl_s_StructTest_returnCombined(
  /* in */ s_StructTest self,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_s_StructTest_passinCombined(
  /* in */ s_StructTest self,
  /* in */ const struct s_Combined__data* s1,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_s_StructTest_passoutCombined(
  /* in */ s_StructTest self,
  /* out */ struct s_Combined__data* s1,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_s_StructTest_passinoutCombined(
  /* in */ s_StructTest self,
  /* inout */ struct s_Combined__data* s1,
  /* out */ sidl_BaseInterface *_ex);

extern
struct s_Combined__data
impl_s_StructTest_passeverywhereCombined(
  /* in */ s_StructTest self,
  /* in */ const struct s_Combined__data* s1,
  /* out */ struct s_Combined__data* s2,
  /* inout */ struct s_Combined__data* s3,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_s_StructTest_passinRarrays(
  /* in */ s_StructTest self,
  /* in */ const struct s_Rarrays__data* s1,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_s_StructTest_passinoutRarrays(
  /* in */ s_StructTest self,
  /* inout */ struct s_Rarrays__data* s1,
  /* out */ sidl_BaseInterface *_ex);

extern
sidl_bool
impl_s_StructTest_passeverywhereRarrays(
  /* in */ s_StructTest self,
  /* in */ const struct s_Rarrays__data* s1,
  /* inout */ struct s_Rarrays__data* s2,
  /* out */ sidl_BaseInterface *_ex);

#ifdef WITH_RMI
extern struct sidl_BaseInterface__object* 
  impl_s_StructTest_fconnect_sidl_BaseInterface(const char* url, sidl_bool ar, 
  sidl_BaseInterface *_ex);
#endif /*WITH_RMI*/
extern void
skel_s_StructTest_serialize_s_Simple(const struct s_Simple__data *strct, struct 
  sidl_rmi_Return__object *pipe, const char *name, sidl_bool copyArg, struct 
  sidl_BaseInterface__object * *exc);
extern void
skel_s_StructTest_serialize_s_Hard(const struct s_Hard__data *strct, struct 
  sidl_rmi_Return__object *pipe, const char *name, sidl_bool copyArg, struct 
  sidl_BaseInterface__object * *exc);
extern void
skel_s_StructTest_serialize_s_Combined(const struct s_Combined__data *strct, 
  struct sidl_rmi_Return__object *pipe, const char *name, sidl_bool copyArg, 
  struct sidl_BaseInterface__object * *exc);
extern void
skel_s_StructTest_serialize_s_Rarrays(const struct s_Rarrays__data *strct, 
  struct sidl_rmi_Return__object *pipe, const char *name, sidl_bool copyArg, 
  struct sidl_BaseInterface__object * *exc);
extern void
skel_s_StructTest_serialize_s_Empty(const struct s_Empty__data *strct, struct 
  sidl_rmi_Return__object *pipe, const char *name, sidl_bool copyArg, struct 
  sidl_BaseInterface__object * *exc);
extern void
skel_s_StructTest_deserialize_s_Simple(struct s_Simple__data *strct, struct 
  sidl_rmi_Call__object *pipe, const char *name, sidl_bool copyArg, struct 
  sidl_BaseInterface__object * *exc);
extern void
skel_s_StructTest_deserialize_s_Hard(struct s_Hard__data *strct, struct 
  sidl_rmi_Call__object *pipe, const char *name, sidl_bool copyArg, struct 
  sidl_BaseInterface__object * *exc);
extern void
skel_s_StructTest_deserialize_s_Combined(struct s_Combined__data *strct, struct 
  sidl_rmi_Call__object *pipe, const char *name, sidl_bool copyArg, struct 
  sidl_BaseInterface__object * *exc);
extern void
skel_s_StructTest_deserialize_s_Rarrays(struct s_Rarrays__data *strct, struct 
  sidl_rmi_Call__object *pipe, const char *name, sidl_bool copyArg, struct 
  sidl_BaseInterface__object * *exc);
extern void
skel_s_StructTest_deserialize_s_Empty(struct s_Empty__data *strct, struct 
  sidl_rmi_Call__object *pipe, const char *name, sidl_bool copyArg, struct 
  sidl_BaseInterface__object * *exc);

/* DO-NOT-DELETE splicer.begin(_hmisc) */
/* DO-NOT-DELETE splicer.end(_hmisc) */

#ifdef __cplusplus
}
#endif
#endif
