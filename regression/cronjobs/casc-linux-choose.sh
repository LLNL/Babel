#!/bin/sh

if lockfile -r 5 casc-linux.lock; then
  trap "/bin/rm -f casc-linux.lock; exit" 0 1 2 15
  if [ -f casc-linux.current ]; then
    current=`cat casc-linux.current`
  else
    current=0
  fi
  expr $current + 1 > casc-linux.current
  rm -f casc-linux.lock
  trap 0 1 2 15
else
  current=`head -c 8 /dev/urandom | perl -ne 'print unpack("I",$_);'`
fi
lines=`wc -l casc-linux.profiles | awk ' {print $1}'`
linenum=`expr \( $current % $lines \) + 1`
profile=`head -n $linenum casc-linux.profiles | tail -n 1`
echo $profile
