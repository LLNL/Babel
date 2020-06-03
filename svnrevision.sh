#!/bin/sh

outfile=""
topdir="."
srcfile=""
awkfile=`echo $0 | sed -e 's/svnrevision.sh/svnrevision.awk/'`
git_awkfile=`echo $0 | sed -e 's/svnrevision.sh/svnrevision.git-svn.awk/'`

while test $# -gt 0; do
  case "$1" in
  -*=*) optarg=`echo "$1" | sed -e 's/[-_a-zA-Z0-9]*=//'`;;
  *) optarg= ;;
  esac
  case "$1" in
  --srcfile=*)
    srcfile="$optarg"
    ;;
  --outfile=*)
    outfile="$optarg"
    ;;
  --topdir=*)
    topdir="$optarg"
    ;;
   *)
     echo 1>&2 "Usage: $0 --srcfile=<filename> --outfile=<filename> --topdir=<dirname>"
     ;;
   esac
   shift
done

if test -z "$srcfile" -o -z "$outfile" -o -z "$topdir"; then
  echo 1>&2 "Usage: $0 --srcfile=<filename> --outfile=<filename> --topdir=<dirname>"
  exit 1
fi

if test -d $topdir/.svn ; then
  mkdir -p `dirname "$outfile"` || exit 1
  (cd $topdir ; svn status -v $1 ) | awk -f $awkfile > "$outfile.tmp"
  svn info $topdir | grep "^URL:" | sed -e 's,.*/,,g' >> "$outfile.tmp"
  if cmp -s "$outfile.tmp" "$outfile" 2>&1 >/dev/null; then
    rm -f "$outfile.tmp"
    exit 1
  else
    mv -f "$outfile.tmp" "$outfile" 2>&1 >/dev/null
    exit $?
  fi
elif test -d $topdir/.git ; then
  #for those wanting to track babel with git-svn
  mkdir -p `dirname "$outfile"` || exit 1
  (cd $topdir ; git svn info ) | awk -f $git_awkfile > "$outfile.tmp"
  if cmp -s "$outfile.tmp" "$outfile" 2>&1 >/dev/null; then
    rm -f "$outfile.tmp"
    exit 1
  else
    mv -f "$outfile.tmp" "$outfile" 2>&1 >/dev/null
    exit $?
  fi
else
  if ! cmp -s "$srcfile" "$outfile" 2>&1 >/dev/null; then
    # files are not equal
    mkdir -p `dirname "$outfile"` || exit 1
    cp "$srcfile" "$outfile" 2>&1 >/dev/null
    exit $?
  fi
fi
exit 1
