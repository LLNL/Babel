#! /bin/bash

#
# See How Long This Stuff Takes...!
#

date

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
GANTLETDIR=${SCRIPTDIR}/..
# cd to the right directory, building it if neccessary
if test "X$CRONROOT" == "X" ; then
  export CRONROOT=${PACKAGING_BUILDDIR}/cronjobs/
fi
BUILDDIR=${PACKAGING_BUILDDIR}/cronjobs/${PROFILE_NAME}/${SESSION}
if test ! -d ${BUILDDIR}; then
    mkdir -p ${BUILDDIR} || exit 1
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
export XML_ARCHIVE=${BUILDDIR}/${PROFILE_NAME}.${SESSION}.${SNAPSHOT_NUMBER}.xml

$SH ${SCRIPTDIR}/${SESSION}.test >  $LOG 2>&1;
RESULT=$?

sleep 10

# generate email.
if test $RESULT -eq 0; then 
  echo ${XML_ARCHIVE}
  if test -f ${XML_ARCHIVE}; then
    echo "Everything worked as hoped"
    echo PYTHONPATH=${PYTHONPATH}:${GANTLETDIR} ${PYTHON} ${GANTLETDIR}/gantlet/email_display.py -e"$EMAILS" -m${MAIL_SERVER} -x${XML_ARCHIVE}
    PYTHONPATH=${PYTHONPATH}:${GANTLETDIR} ${PYTHON} ${GANTLETDIR}/gantlet/email_display.py -e"$EMAILS" -m${MAIL_SERVER} -x${XML_ARCHIVE}
  else
    echo "huh?  exit status == 0, but no XML report?"
    echo ${XML_ARCHIVE}
    tail -200 $LOG > mail.txt
    echo PYTHONPATH=${PYTHONPATH}:${GANTLETDIR} ${PYTHON} ${GANTLETDIR}/gantlet/email_display.py -e"$EMAILS" -m${MAIL_SERVER} -bmail.txt -k${PACKAGE} -p${PROFILE_NAME} -n${SESSION}
    PYTHONPATH=${PYTHONPATH}:${GANTLETDIR} ${PYTHON} ${GANTLETDIR}/gantlet/email_display.py -e"$EMAILS" -m${MAIL_SERVER} -bmail.txt -k${PACKAGE} -p${PROFILE_NAME} -n${SESSION}
  fi
else 
  if test -f ${XML_ARCHIVE}; then
    echo "Failure occurred after XML generation"
    echo PYTHONPATH=${PYTHONPATH}:${GANTLETDIR} ${PYTHON} ${GANTLETDIR}/gantlet/email_display.py -e"$EMAILS" -m${MAIL_SERVER} -x${XML_ARCHIVE}
    PYTHONPATH=${PYTHONPATH}:${GANTLETDIR} ${PYTHON} ${GANTLETDIR}/gantlet/email_display.py -e"$EMAILS" -m${MAIL_SERVER} -x${XML_ARCHIVE}
  else
    echo "Script exited with nonzero status, no Gantlet results to report"
    echo ${XML_ARCHIVE}
    tail -200 $LOG > mail.txt
    echo PYTHONPATH=${PYTHONPATH}:${GANTLETDIR} ${PYTHON} ${GANTLETDIR}/gantlet/email_display.py -e"$EMAILS" -m${MAIL_SERVER} -bmail.txt -k${PACKAGE} -p${PROFILE_NAME} -n${SESSION}
    PYTHONPATH=${PYTHONPATH}:${GANTLETDIR} ${PYTHON} ${GANTLETDIR}/gantlet/email_display.py -e"$EMAILS" -m${MAIL_SERVER} -bmail.txt -k${PACKAGE} -p${PROFILE_NAME} -n${SESSION}
  fi
fi

bzip2 --force --compress --best $LOG

# do our best to make this package readable by group babel
#$CHGRP -R ${TESTGID} ${BUILDDIR}
#$CHMOD -Rf ug+r ${BUILDDIR}
#$FIND ${BUILDDIR} -type d -exec $CHMOD -f ug+rwx {} \;
#$CHMOD -Rf o-rwx ${BUILDDIR}

#
# See How Long This Stuff Takes...!
#

date

