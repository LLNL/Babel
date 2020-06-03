/*
 * File:          sidl_NotImplementedException_Impl.c
 * Symbol:        sidl.NotImplementedException-v0.9.17
 * Symbol Type:   class
 * Babel Version: 2.0.0 (Revision: 7463 trunk)
 * Release:       $Name:  $
 * Revision:      @(#) $Id: $
 * Description:   Server-side implementation for sidl.NotImplementedException
 * 
 * Copyright (c) 2000-2002, Lawrence Livermore National Security, LLC.
 * Produced at the Lawrence Livermore National Laboratory.
 * Written by the Components Team <components@llnl.gov>
 * All rights reserved.
 * 
 * This file is part of Babel. For more information, see
 * http://www.llnl.gov/CASC/components/. Please read the COPYRIGHT file
 * for Our Notice and the LICENSE file for the GNU Lesser General Public
 * License.
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License (as published by
 * the Free Software Foundation) version 2.1 dated February 1999.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the IMPLIED WARRANTY OF
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the terms and
 * conditions of the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * WARNING: Automatically generated; only changes within splicers preserved
 * 
 */

/*
 * DEVELOPERS ARE EXPECTED TO PROVIDE IMPLEMENTATIONS
 * FOR THE FOLLOWING METHODS BETWEEN SPLICER PAIRS.
 */

/*
 * Symbol "sidl.NotImplementedException" (version 0.9.17)
 * 
 *  
 * This Exception is thrown when a method is called that an 
 * implmentation has not been written for yet.  The throw code is
 * placed into the _Impl files automatically when they are generated.
 */

#include "sidl_NotImplementedException_Impl.h"
#include "sidl_NotImplementedException.h"
#include "sidl_Exception.h"
#ifndef included_sidl_MemAllocException_h
#include "sidl_MemAllocException.h"
#endif

/* DO-NOT-DELETE splicer.begin(sidl.NotImplementedException._includes) */
/* Insert-Code-Here {sidl.NotImplementedException._includes} (includes and 
  arbitrary code) */
/* DO-NOT-DELETE splicer.end(sidl.NotImplementedException._includes) */

#define SIDL_IOR_MAJOR_VERSION 2
#define SIDL_IOR_MINOR_VERSION 0
/*
 * Static class initializer called exactly once before any user-defined method is dispatched
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_NotImplementedException__load"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_NotImplementedException__load(
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.NotImplementedException._load) */
    /* DO-NOT-DELETE splicer.end(sidl.NotImplementedException._load) */
  }
}
/*
 * Class constructor called when the class is created.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_NotImplementedException__ctor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_NotImplementedException__ctor(
  /* in */ sidl_NotImplementedException self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.NotImplementedException._ctor) */
    /* Insert-Code-Here {sidl.NotImplementedException._ctor} (constructor 
      method) */
    /*
     * // boilerplate constructor
     * struct sidl_NotImplementedException__data *dptr = (struct sidl_NotImplementedException__data*)malloc(sizeof(struct sidl_NotImplementedException__data));
     * if (dptr) {
     *   memset(dptr, 0, sizeof(struct sidl_NotImplementedException__data));
     *   // initialize elements of dptr here
     * sidl_NotImplementedException__set_data(self, dptr);
     * } else {
     *   sidl_MemAllocException ex = sidl_MemAllocException_getSingletonException(_ex);
     *   SIDL_CHECK(*_ex);
     *   sidl_MemAllocException_setNote(ex, "Out of memory.", _ex); SIDL_CHECK(*_ex);
     *   sidl_MemAllocException_add(ex, __FILE__, __LINE__, "sidl.NotImplementedException._ctor", _ex);
     *   SIDL_CHECK(*_ex);
     *   *_ex = (sidl_BaseInterface)ex;
     * }
     * EXIT:;
     */

    /* DO-NOT-DELETE splicer.end(sidl.NotImplementedException._ctor) */
  }
}

/*
 * Special Class constructor called when the user wants to wrap his own private data.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_NotImplementedException__ctor2"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_NotImplementedException__ctor2(
  /* in */ sidl_NotImplementedException self,
  /* in */ void* private_data,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.NotImplementedException._ctor2) */
    /* DO-NOT-DELETE splicer.end(sidl.NotImplementedException._ctor2) */
  }
}
/*
 * Class destructor called when the class is deleted.
 */

#undef __FUNC__
#define __FUNC__ "impl_sidl_NotImplementedException__dtor"

#ifdef __cplusplus
extern "C"
#endif
void
impl_sidl_NotImplementedException__dtor(
  /* in */ sidl_NotImplementedException self,
  /* out */ sidl_BaseInterface *_ex)
{
  *_ex = 0;
  {
    /* DO-NOT-DELETE splicer.begin(sidl.NotImplementedException._dtor) */
    /* Insert-Code-Here {sidl.NotImplementedException._dtor} (destructor 
      method) */
    /*
     * // boilerplate destructor
     * struct sidl_NotImplementedException__data *dptr = sidl_NotImplementedException__get_data(self);
     * if (dptr) {
     *   // free contained in dtor before next line
     *   free(dptr);
     *   sidl_NotImplementedException__set_data(self, NULL);
     * }
     */

    /* DO-NOT-DELETE splicer.end(sidl.NotImplementedException._dtor) */
  }
}
/* Babel internal methods, Users should not edit below this line. */

/* DO-NOT-DELETE splicer.begin(_misc) */
/* Insert-Code-Here {_misc} (miscellaneous code) */
/* DO-NOT-DELETE splicer.end(_misc) */

