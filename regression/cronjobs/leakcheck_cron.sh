#! /bin/bash

# Set the soft limit on the stack size to the hard limit
ulimit -H -s unlimited
ulimit -S -s unlimited

if test $# != 3; then
    echo "Usage:"
    echo " $0 <profile> <session> <email1>"
    echo ""
    echo "Where"
    echo "  '<profile>.profile' is a bunch of variables that can be sourced by bash"
    echo "  '<session>.test' is a shell script that runs some commands"
    echo "  <email-n> is a list of email addresses to send the results to"
    exit -1
fi   

# load the appropriate settings
SCRIPTDIR=`pwd`
export PROFILE=$1
export SESSION=$2
shift
shift
EMAILS=$@

# now find the profile fil and source it to get the settings right.
if test ! -f ${SCRIPTDIR}/${PROFILE}; then 
    if test -f ${SCRIPTDIR}/${PROFILE}.profile; then
	SCRIPTFILE="${PROFILE}.profile"
    else
	echo "cannot find settings file \"${SCRIPTDIR}/${PROFILE}\" or"
        echo "\"${SCRIPTDIR}/${PROFILE}.profile\""
	exit -1
    fi
else
    SCRIPTFILE=${PROFILE}
    PROFILE=`echo ${PROFILE} | sed -e 's/\.profile$//'`
fi
source ${SCRIPTDIR}/${SCRIPTFILE}  #all that work for this one crummy line
# A trick to set the title bar of an xterm
if test "X$DISPLAY" != "X"; then
  echo -e -n "\033]0;Building $PROFILE_NAME $SESSION\a"
fi
echo

if test "X$PYTHON" == "X"; then
  PYTHON=python
fi
# cd to the right directory, building it if neccessary
if test "X$CRONROOT" == "X" ; then
  export CRONROOT=${PACKAGING_BUILDDIR}/cronjobs/
fi
BUILDDIR=${PACKAGING_BUILDDIR}/cronjobs/${PROFILE_NAME}/${SESSION}
if test ! -d ${BUILDDIR}; then
    mkdir -p ${BUILDDIR}
    $CHGRP -f ${TESTGID} ${BUILDDIR}
    $CHMOD -f g+rwxs ${BUILDDIR}
fi
if test ! -d ${BUILDDIR}/tmp; then
    mkdir -p ${BUILDDIR}/tmp
    $CHGRP -f ${TESTGID} ${BUILDDIR}/tmp
    $CHMOD -f g+rwxs ${BUILDDIR}/tmp
fi
if test -d ${BUILDDIR}/tmp; then
  export TMPDIR=${BUILDDIR}/tmp
fi
$CD ${BUILDDIR}
$CHGRP -Rf ${TESTGID} ${BUILDDIR}

# set the name for the log file.
LOG=${PROFILE_NAME}.${SESSION}.cron.log
export LEAKCHECK_ARCHIVE=${BUILDDIR}/${PROFILE_NAME}.${SESSION}.${SNAPSHOT_NUMBER}.txt

$SH ${SCRIPTDIR}/${SESSION}.test >  $LOG 2>&1;
RESULT=$?

sleep 10

# generate email.
if test $RESULT -eq 0; then 
  echo ${LEAKCHECK_ARCHIVE}
  if test -f ${LEAKCHECK_ARCHIVE}; then
    echo "Everything worked as hoped"
    ${PYTHON} ${SCRIPTDIR}/sendmail.py --subject="Babel leak check report" --from="babel-dev@llnl.gov" --smtp=${MAIL_SERVER} --file=${LEAKCHECK_ARCHIVE} $EMAILS
  else
    echo "huh?  exit status == 0, but no leakcheck report?"
    echo ${LEAKCHECK_ARCHIVE}
    tail -200 $LOG > mail.txt
    ${PYTHON} ${SCRIPTDIR}/sendmail.py --subject="Babel leak check error report" --from="babel-dev@llnl.gov" --smtp=${MAIL_SERVER} --file=mail.txt $EMAILS
  fi
else 
  if test -f ${LEAKCHECK_ARCHIVE}; then
    echo "Failure occurred after leakcheck run"
    ${PYTHON} ${SCRIPTDIR}/sendmail.py --subject="Babel leak check report (status=$RESULT)" --from="babel-dev@llnl.gov" --smtp=${MAIL_SERVER} --file=${LEAKCHECK_ARCHIVE} $EMAILS
  else
    echo "Script exited with nonzero status, no leakcheck results to report"
    echo ${LEAKCHECK_ARCHIVE}
    tail -200 $LOG > mail.txt
    ${PYTHON} ${SCRIPTDIR}/sendmail.py --subject="Babel leak check error report (status=$RESULT)" --from="babel-dev@llnl.gov" --smtp=${MAIL_SERVER} --file=mail.txt $EMAILS
  fi
fi

bzip2 --force --compress --best $LOG
