#! /bin/sh
##
## File:	gen-java-arrays.sh
## Package:	sidl runtime
## Copyright:	(c) 2000-2001 Lawrence Livermore National Security, LLC
## Revision:	\$Revision: 6171 $
## Modified:	\$Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
## Description:	shell script to build JNI support for sidl Java arrays
##

#
# Write the native code for array support.  The arguments to this routine are
# as follows:
#
# CLASSNAME: name of run-time support class sidl.XXX (Float, Character, etc.)
# IORNAME:   name of the IOR array object (bool, float, char, etc.)
# JAVAELEM:  Java type corresponding to an array element (float, char, etc.)
# IORELEM:   IOR type corresponding to an array element (float, char, etc.)
# JAVASIG:   Java signature corresponding to the element type (F, C, etc.)
#
# Note: This script generates code for all basic array types.  This does
# NOT include Base Interface! You may know that all object arrays use the 
# base interface native functions.  They all maintain an internal BaseInterface
# array that actually containts the array data, and all calls are actually 
# delegated to that array.
# The code that generates this BaseInterface JNI stuff is based on this
# script, in fact it was generated there, then ported over to the java
# generation routines.  So any changes here should be reflected in:
# babel/compiler/gov/llnl/babel/backend/jdk/ClientJNI.java
#
# NOTE: If Max Array dimensionaly changes from 7, this strict will need editing. 


write_source() {
  cat << EOF \
  | sed -e "s%CLASSNAME%$1%g" \
        -e "s%IORNAME%$2%g"   \
        -e "s%JAVAELEM%$3%g"  \
        -e "s%IORELEM%$4%g"  \
        -e "s%JAVASIG%$5%g"   \
  | m4 | indent -nbad -bap -nbc -bbo -br -brs -cdw -ncdb -nce -cp1 -cs -di2 \
   -ndj -nfc1 -nfca -hnl -i2 -ip5 -lp -npcs -nprs -psl -saf -sai \
   -saw -nsc -nsob -nut -npro
changequote(@,@)dnl
@
/* 
 * Local utility function to extract the array pointer from the Java object.
 * Extract the d_array long data member and convert it to a pointer.
 */
static struct sidl_IORNAME__array* sidl_CLASSNAME__getptr(
  JNIEnv* env,
  jobject obj)
{
  void* ptr = NULL;
  static jfieldID s_array_field = NULL;

  if (s_array_field == NULL) {
    jclass cls = (*env)->GetObjectClass(env, obj);
    s_array_field = (*env)->GetFieldID(env, cls, "d_array", "J");
    (*env)->DeleteLocalRef(env, cls);
  }

  ptr = JLONG_TO_POINTER((*env)->GetLongField(env, obj, s_array_field));
  return (struct sidl_IORNAME__array*) ptr;
}

/*
 * Local utility function to set the array pointer on the Java object.
 * Convert the pointer to a long value and set the d_array data member.
 */
static void sidl_CLASSNAME__setptr(
  JNIEnv* env,
  jobject obj,
  struct sidl_IORNAME__array* array)
{
  static jfieldID s_array_field = NULL;

  if (s_array_field == NULL) {
    jclass cls = (*env)->GetObjectClass(env, obj);
    s_array_field = (*env)->GetFieldID(env, cls, "d_array", "J");
    (*env)->DeleteLocalRef(env, cls);
  }

  (*env)->SetLongField(env, obj, s_array_field, POINTER_TO_JLONG(array));
}

/*
 * Native routine to fetch the specified value from the array.  The
 * specified array index/indices must be lie between the array lower
 * upper bounds (inclusive).  Invalid indices will have unpredictable
 * (but almost certainly bad) results.
 */
static JAVAELEM sidl_CLASSNAME__get(
  JNIEnv* env,
  jobject obj,
  jint i,
  jint j,
  jint k,
  jint l,
  jint m,
  jint n,
  jint o)
{
  struct sidl_IORNAME__array* array = sidl_CLASSNAME__getptr(env, obj);
  int32_t a[SIDL_MAX_ARRAY_DIMENSION];
@ifelse(CLASSNAME,DoubleComplex,@dnl
  struct sidl_dcomplex value;
@,CLASSNAME,FloatComplex,@dnl
  struct sidl_fcomplex value;
@,CLASSNAME,String,@dnl
  char* value;
  jstring jstr;
@,)dnl
@
  a[0] = i; a[1] = j; a[2] = k; a[3] = l; a[4] = m; a[5] = n; a[6] = o;
@ifelse(CLASSNAME,DoubleComplex,@dnl
  value = sidl_dcomplex__array_get(array, a);
  return sidl_Java_I2J_dcomplex(env, &value);
@,CLASSNAME,FloatComplex,@dnl
  value = sidl_fcomplex__array_get(array, a);
  return sidl_Java_I2J_fcomplex(env, &value);
@,CLASSNAME,Opaque,@dnl
  return POINTER_TO_JLONG(sidl_opaque__array_get(array, a));
@,CLASSNAME,String,@dnl
  value = sidl_string__array_get(array, a);
  jstr = sidl_Java_I2J_string(env, value);
  sidl_String_free(value);
  return jstr;
@,CLASSNAME,Boolean,@dnl
#if (JNI_TRUE == TRUE) && (JNI_FALSE == FALSE)
  return (JAVAELEM) sidl_IORNAME__array_get(array, a);
#else
  return sidl_IORNAME__array_get(array, a) ? JNI_TRUE : JNI_FALSE;
#endif
@,@dnl
  return (JAVAELEM) sidl_IORNAME__array_get(array, a);
@)dnl
@}

/**
 * Native routine to set the specified value in the array.  The
 * specified array index/indices must be lie between the array lower
 * upper bounds (inclusive).  Invalid indices will have unpredictable
 * (but almost certainly bad) results.
 */
static void sidl_CLASSNAME__set(
  JNIEnv* env,
  jobject obj,
  jint i,
  jint j,
  jint k,
  jint l,
  jint m, 
  jint n, 
  jint o,
  JAVAELEM value)
{
  struct sidl_IORNAME__array* array = sidl_CLASSNAME__getptr(env, obj);
  int32_t a[SIDL_MAX_ARRAY_DIMENSION];
@ifelse(CLASSNAME,DoubleComplex,@dnl
  struct sidl_dcomplex elem;
@,CLASSNAME,FloatComplex,@dnl
  struct sidl_fcomplex elem;
@,CLASSNAME,String,@dnl
  char* elem;
@,)dnl
@
  a[0] = i; a[1] = j; a[2] = k; a[3] = l; a[4] = m; a[5] = n; a[6] = o;
@ifelse(CLASSNAME,DoubleComplex,@dnl
  elem = sidl_Java_J2I_dcomplex(env, value);
  sidl_dcomplex__array_set(array, a, elem);
@,CLASSNAME,FloatComplex,@dnl
  elem = sidl_Java_J2I_fcomplex(env, value);
  sidl_fcomplex__array_set(array, a, elem);
@,CLASSNAME,Opaque,@dnl
  sidl_opaque__array_set(array, a, JLONG_TO_POINTER(value));
@,CLASSNAME,String,@dnl
  elem = sidl_Java_J2I_string(env, value);
  sidl_string__array_set(array, a, elem);
  sidl_String_free(elem);
@,CLASSNAME,Boolean,@dnl
#if (JNI_TRUE == TRUE) && (JNI_FALSE == FALSE)
  sidl_IORNAME__array_set(array, a, (IORELEM) value);
#else
  sidl_IORNAME__array_set(array, a, ((value) ? TRUE : FALSE));
#endif
@,@dnl
  sidl_IORNAME__array_set(array, a, (IORELEM) value);
@)dnl
@}

/*
 * Native routine to reallocate data in the array.  The specified array
 * dimension and indices must match and be within valid ranges (e.g., the
 * upper bounds must be greater than or equal to lowe rbounds.  Invalid
 * indices will have unpredictable (but almost certainly bad) results.
 * This routine will deallocate the existing array data if it is not null.
 * if argument "isRow" is true, the array will be in RowOrder, other wise
 * it will be in ColumnOrder   
 */
static void sidl_CLASSNAME__reallocate(
  JNIEnv* env,
  jobject obj,
  jint dim,
  jarray lower,
  jarray upper,
  jboolean isRow)
{
  jint* l = NULL;
  jint* u = NULL;
  struct sidl_IORNAME__array* array = NULL;

  sidl__destroy(env, obj);

  l = (*env)->GetIntArrayElements(env, lower, NULL);
  u = (*env)->GetIntArrayElements(env, upper, NULL);
  if(isRow)  
    array = sidl_IORNAME__array_createRow((int32_t) dim, (int32_t*) l,
                                          (int32_t*) u);
  else
    array = sidl_IORNAME__array_createCol((int32_t) dim, (int32_t*) l,
                                          (int32_t*) u);
  (*env)->ReleaseIntArrayElements(env, lower, l, JNI_ABORT);
  (*env)->ReleaseIntArrayElements(env, upper, u, JNI_ABORT);

  sidl_CLASSNAME__setptr(env, obj, array);
}


/*
 * Native function copies borrowed arrays, or increments the reference count
 * of non-borrowed arrays.  Good if you want to keep a copy of a passed
 * in array.  Returns an array, new if borrowed.
 */
static void sidl_CLASSNAME__copy(
  JNIEnv* env,
  jobject obj,
  jobject dest)
{
  struct sidl_IORNAME__array* csrc = sidl_CLASSNAME__getptr(env, obj);
  struct sidl_IORNAME__array* cdest = sidl_CLASSNAME__getptr(env, dest);
      
  if(csrc && cdest) {
    sidl_IORNAME__array_copy(csrc,cdest);
  } 

}

/*
 * Native function slices arrays in various ways
 */
static jobject sidl_CLASSNAME__slice(
  JNIEnv* env,
  jobject obj, 
  jint dimen, 
  jintArray numElem, 
  jintArray srcStart, 
  jintArray srcStride,
  jintArray newStart)
{
  struct sidl_IORNAME__array* array = sidl_CLASSNAME__getptr(env, obj);
  struct sidl_IORNAME__array* ret_ptr = NULL;
  jobject ret_array = NULL;

  /* I'm cheating a bit here.  Slice expects any unused arguments to be
     null.  But I don't want to use malloc, so I'm allocating the space
     on the stack, but only pointing to it if that argument is actually
     used. */
  jint anumElem[SIDL_MAX_ARRAY_DIMENSION];
  jint asrcStart[SIDL_MAX_ARRAY_DIMENSION];
  jint asrcStride[SIDL_MAX_ARRAY_DIMENSION];
  jint anewStart[SIDL_MAX_ARRAY_DIMENSION];
  jint* cnumElem = NULL;
  jint* csrcStart = NULL;
  jint* csrcStride = NULL;
  jint* cnewStart = NULL;
  int i = 0; 

  if(numElem == NULL) {
    return NULL;  /*If numElem is NULL, we need to return Null, that's bad.*/
  } else {
    if((*env)->GetArrayLength(env, numElem) > SIDL_MAX_ARRAY_DIMENSION)
      return NULL;  
    cnumElem = anumElem;
    for(i = 0; i < SIDL_MAX_ARRAY_DIMENSION; ++i) {  /*Make sure the array is clean*/ 
      cnumElem[i] = 0;
    }
    (*env)->GetIntArrayRegion(env, numElem, 0,
			      (*env)->GetArrayLength(env, numElem), cnumElem); 
    
  }

  if(srcStart != NULL) {
    if((*env)->GetArrayLength(env, srcStart) > SIDL_MAX_ARRAY_DIMENSION)
      return NULL;
    csrcStart = asrcStart;
    for(i = 0; i < SIDL_MAX_ARRAY_DIMENSION; ++i) {  /*Make sure the array is clean*/ 
      csrcStart[i] = 0;
    }
    (*env)->GetIntArrayRegion(env, srcStart, 0, 
			    (*env)->GetArrayLength(env, srcStart), csrcStart);   
  } 

  if(srcStride != NULL) {
    if((*env)->GetArrayLength(env, srcStride) > SIDL_MAX_ARRAY_DIMENSION)
      return NULL; 
    csrcStride = asrcStride;
    for(i = 0; i < SIDL_MAX_ARRAY_DIMENSION; ++i) {  /*Make sure the array is clean*/ 
      csrcStride[i] = 0;
    }
    (*env)->GetIntArrayRegion(env, srcStride, 0, 
			      (*env)->GetArrayLength(env, srcStride), csrcStride);
  } 

  if(newStart != NULL) {
    if((*env)->GetArrayLength(env, newStart) > SIDL_MAX_ARRAY_DIMENSION)
      return NULL;
    cnewStart = anewStart; 
    for(i = 0; i < SIDL_MAX_ARRAY_DIMENSION; ++i) {  /*Make sure the array is clean*/ 
      cnewStart[i] = 0;
    }
    (*env)->GetIntArrayRegion(env, newStart, 0, 
			      (*env)->GetArrayLength(env, newStart), cnewStart);    
  } 

  if (array != NULL) {
    /* jint should be equivalent to int32_t; Java int is 32 bits */
    ret_ptr = sidl_IORNAME__array_slice(array, dimen, cnumElem, csrcStart, csrcStride, cnewStart);
  }

  if(ret_ptr != NULL) {  
    ret_array = sidl_Java_I2J_new_array(env,ret_ptr, "sidl.CLASSNAME\$Array");
  }

   return ret_array;
}


/*
 * Register JNI array methods with the Java JVM.
 */
void sidl_CLASSNAME__register(JNIEnv* env)
{
  JNINativeMethod methods[5];
  jclass cls;

  methods[0].name      = "_get";
  methods[0].signature = "(IIIIIII)JAVASIG";
  methods[0].fnPtr     = sidl_CLASSNAME__get;
  methods[1].name      = "_set";
  methods[1].signature = "(IIIIIIIJAVASIG)V";
  methods[1].fnPtr     = sidl_CLASSNAME__set;
  methods[2].name      = "_copy";
  methods[2].signature = "(Lsidl/CLASSNAME\$Array;)V";
  methods[2].fnPtr     = sidl_CLASSNAME__copy;  
  methods[3].name      = "_slice";
  methods[3].signature = "(I[I[I[I[I)Lsidl/CLASSNAME\$Array;";
  methods[3].fnPtr     = sidl_CLASSNAME__slice;    
  methods[4].name      = "_reallocate";
  methods[4].signature = "(I[I[IZ)V";
  methods[4].fnPtr     = sidl_CLASSNAME__reallocate;


  cls = (*env)->FindClass(env, "sidl/CLASSNAME\$Array");
  if (cls) {
    (*env)->RegisterNatives(env, cls, methods, 5);
    (*env)->DeleteLocalRef(env, cls);
  }
}
@dnl
EOF
}

#
# Generate the native code support for all built-in array types.
#

write_source Boolean       bool     jboolean sidl_bool Z
write_source Character     char     jchar    char      C
write_source DoubleComplex dcomplex jobject  XXX       Lsidl/DoubleComplex\;
write_source Double        double   jdouble  double    D
write_source FloatComplex  fcomplex jobject  XXX       Lsidl/FloatComplex\;
write_source Float         float    jfloat   float     F
write_source Integer       int      jint     int32_t   I
write_source Long          long     jlong    int64_t   J
write_source Opaque        opaque   jlong    XXX       J
write_source String        string   jstring  XXX       Ljava/lang/String\;
#write_source BaseInterface BaseInterface jobject ""    Lsidl/BaseInterface\;

exit

