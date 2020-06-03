#! /usr/bin/env python
#
# The purpose of this python program is to scan through valgrind runs
# looking for memory leaks or bad exit statuses.
#
# It takes a list of executables to run through valgrind.

from os import popen
from sys import stdout
from re import compile
from time import sleep

suppressionFiles = [ ]
failedRuns = []
leakyRuns = []
suppressionMatch = compile(r"^--suppressions=")
definiteLeakedBytes = 0
definiteLeakedBlocks = 0
possibleLeakedBytes = 0
possibleLeakedBlocks = 0

def runValgrind(executable):
    global failedRuns, definiteLeakedBytes, definiteLeakedBlocks
    global possibleLeakedBytes, possibleLeakedBlocks, leakyRuns
    errors = compile(r"^==\d+==\s+ERROR SUMMARY:\s+(\d+)\s+errors from\s+(\d+)\s+contexts")
    definite = compile(r"^==\d+==\s+definitely lost:\s+(\d+)\s+bytes in\s+(\d+)\s+blocks.")
    possible = compile(r"^==\d+==\s+possibly lost:\s+(\d+)\s+bytes in\s+(\d+)\s+blocks.")
    if ((executable[0] != '/') and
        (executable[0] != '.')):
        executable = "./" + executable
    print "Running", executable
    run = popen("valgrind --leak-check=yes " +
                " ".join(suppressionFiles) + 
               " --show-reachable=yes ./" +
                executable + " 2>&1")
    line = run.readline()
    while (line):
        check = errors.match(line)
        if check and eval(check.group(1)):
            failedRuns = failedRuns + [ executable + " " + check.group(1) +
                                        " errors in " + check.group(2) +
                                        " contexts" ]
        check = definite.match(line)
        if check:
            leakedBytes = eval(check.group(1))
            leakedBlocks = eval(check.group(2))
            definiteLeakedBytes = definiteLeakedBytes + leakedBytes
            definiteLeakedBlocks = definiteLeakedBlocks + leakedBlocks
            if leakedBytes:
                leakyRuns = leakyRuns + [ executable + ' definitely leaked ' +
                                          str(leakedBytes) + " bytes in " +
                                          str(leakedBlocks) + " blocks." ]
        check = possible.match(line)
        if check:
            leakedBytes = eval(check.group(1))
            leakedBlocks = eval(check.group(2))
            possibleLeakedBytes = possibleLeakedBytes + leakedBytes
            possibleLeakedBlocks = possibleLeakedBlocks + leakedBlocks
            if leakedBytes:
                leakyRuns = leakyRuns + [ executable + ' possibly leaked ' +
                                          str(leakedBytes) + " bytes in " +
                                          str(leakedBlocks) + " blocks (possible)." ]
        stdout.write(line)
        line = run.readline()
    sleep(0.2)
    exitstatus = run.close();
    if exitstatus:
        print "Program", executable, "exited with status", exitstatus
        failedRuns = failedRuns + [ executable + " " + str(exitstatus)]

def summarizeRun():
    print "=============================="
    print "SUMMARY OF VALGRIND RUNS"
    print "=============================="
    
    print len(failedRuns), "failed or had errors"
    for i in failedRuns:
        print ' ', i
    print definiteLeakedBytes, "bytes leaked in", definiteLeakedBlocks, "blocks"
    print possibleLeakedBytes, "bytes leaked in", possibleLeakedBlocks, "blocks"
    for i in leakyRuns:
        print ' ', i


if __name__ == '__main__':
    from sys import argv
    for i in argv[1:]:
        if (suppressionMatch.match(i)):
            suppressionFiles = suppressionFiles + [ i ]
        else:
            runValgrind(i)
    summarizeRun()
