#! /bin/sh

TESTDIR="${HOME}/cronjobs"

echo "Test the CVS repository:"
echo "  You should run this after a day's worth of CVS"
echo "  commits, and certainly before going on an extended"
echo "  absence."
echo
if [ -x "${TESTDIR}" ] 
then 
echo 
echo " ** WARNING **  WARNING ** WARNING ** WARNING ** WARNING ** "
echo "  This script will do all the work in '${TESTDIR}'."
echo "  IF such a directory exists AND IF it contains files"
echo "  you value, quit now."
sleep 3;
fi 
echo 
echo
while :
do 
    echo "Ready to clobber everything in ${TESTDIR} ???"
    echo "                  Answer y or n, then press RETURN: "
    read ans
    case "$ans" in
    y|Y) echo "User Chooses: yes" 1>&2; break;;
    n|N) echo "User Chooses: no" 1>&2; exit; break;;
    *) echo "Unrecognized options: ${ans}  Please answer y or n." 1>&2 ;;
    esac
done

echo 
echo "Final Message:"
echo "  This script is about to begin.  If you wish to watch"
echo "  the progress of the tests, go to another window and "
echo "  type \`tail -f ${TESTDIR}/cronjob.log\`"
echo
echo "running cronjobs/babelbuild.sh....."
PWD=`pwd`
CVSROOT=/home/casc/repository export CVSROOT
GKK_HISTFILE=  export GKK_HISTFILE
cd ${TESTDIR}
time /usr/local/bin/tcsh ${PWD}/cronjobs/babelbuild.sh > cronjob.log 2>&1;
msg=`tail cronjob.log | grep '.tar.gz is ready for distribution'`
echo "THIS IS BABEL AUTOCHECK" 
echo 
if tail cronjob.log | grep '.tar.gz is ready for distribution'
then
    echo `date` 
    echo $msg 
    echo "(or at least has passed all its tests)" 
#    echo "" 
#    echo "results of 'make check'............" 
#    cat babel/tests/check.summary.log 
else
    echo `date` 
    echo "failed its 'make distcheck' command" 
#    if test -x babel/tests/check.summary.log
#    then 
#        echo "results of 'make check'............" 
#        cat babel/tests/check.summary.log 
#    else
#        echo "no results of 'make check' to report..." 
#    fi
#    echo "" 
#    echo "last 30 lines of distcheck log....." 
#    tail -30 cronjob.log 
fi
echo "... DONE!"
