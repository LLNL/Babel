c
c     File:       helloclient.f
c     Copyright:  (c) 2001 Lawrence Livermore National Security, LLC
c     Revision:   @(#) $Revision: 6171 $
c     Date:       $Date: 2007-10-08 16:39:28 -0700 (Mon, 08 Oct 2007) $
c     Description:Simple Hello World F77 client
c
c
      program helloclient
      integer *8 h
      character *32 msg
      integer *8 throwaway

      call Hello_World__create_f (h, throwaway)
      call Hello_World_getMsg_f (h, msg, throwaway)
      call Hello_World_deleteRef_f (h,throwaway)

      print *,msg

      end
