#! /usr/bin/env python

from os import popen
from re import compile
import sys


def runTest(i, verbose):
    array = 0
    object = 0
    special = 0
    broken = 0
    fileregex = compile(r"^(.*/)?([^/]+)$")
    filecheck = fileregex.match(i)
    specialerrors = compile(r"^((babel: (array )?data type invariant failure .*)|(babel: Exception occurred.*))$")
    leakline = compile(r"^((babel: no (objects|arrays) leaked)|(babel: leaked (object|array) .* reference count -?\d+ \(type .*\)))$")
    if filecheck:
        directory = filecheck.group(1)
        file = filecheck.group(2)
        if verbose:
            print i
        if directory and (len(directory) > 0):
            run = popen("cd " + directory + " ; ./" + file + " 2>&1")
        else:
            run = popen("./" + file + " 2>&1")
        linecount = 0
        line = run.readline()
        while (line):
            match = leakline.match(line)
            if (match):
                if verbose:
                    print match.group(1)
                linecount = linecount + 1
                if match.group(5) == "object":
                    object = object + 1
                else:
                    if match.group(5) == "array":
                        array = array + 1
            match = specialerrors.match(line)
            if (match):
                if verbose:
                    print match.group(1)
                special = special + 1
            line = run.readline()
        if linecount == 0:
            if verbose:
                print "BROKEN TEST: No report"
            broken = broken + 1
        exitstatus = run.close()
        if exitstatus:
            if verbose:
                print "Program", i, "exited with status", exitstatus
            broken = broken + 1
    else:
        if verbose:
            print "Skipping ", i
    return (array, object, special, broken)

if __name__ == '__main__':
    totalarrayleaks = 0
    totalobjectleaks = 0
    totalspecialerrors = 0
    totalbroken = 0
    from getopt import getopt
    from time import asctime
    session = ""
    opts, pargs = getopt(sys.argv[1:], '', ['summary-only', 'session=', 'summary-file='])
    verbose = 1
    for i in opts:
        if i[0] == "--summary-only":
            verbose = None
        elif i[0] == "--session":
            session = i[1]
        elif i[0] == "--summary-file":
            sys.stdout = open(i[1],"w")
    for i in pargs:
        (array, object, special, broken) = runTest(i, verbose)
        totalarrayleaks += array
        totalobjectleaks += object
        totalspecialerrors += special
        totalbroken += broken
    print "Test summary:", asctime()
    if session:
        print "     Session:", session
    print "================================================================"
    print " Total arrays leaked: ", totalarrayleaks
    print "Total objects leaked: ", totalobjectleaks
    print "Total special errors: ", totalspecialerrors
    print "  Total broken tests: ", totalbroken
