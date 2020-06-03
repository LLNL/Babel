#!/bin/bash
# This is until I come around to write a new Makefile for this directory
# cf. "Recursive make considered harmful"
JOBS='-j8 -l8'
make check $JOBS -C output
make check $JOBS -C inherit    & inherit=$!
make check $JOBS -C args       & args=$!
make check $JOBS -C arrays     & arrays=$! 
make check $JOBS -C bos        & bos=$!
make check $JOBS -C construct  & construct=$!
make check $JOBS -C contracts  & contracts=$!
make check $JOBS -C enums      & enums=$!
make check $JOBS -C exceptions & exceptions=$!
make check $JOBS -C hooks      & hooks=$!
make check $JOBS -C objarg     & objarg=$!
make check $JOBS -C ordering   & ordering=$!
make check $JOBS -C overload   & overload=$!
make check $JOBS -C sorting    & sorting=$!
make check $JOBS -C strings    & strings=$!
make check $JOBS -C struct     & struct=$!
FAILED=
wait $args       || FAILED=yes 
wait $arrays     || FAILED=yes 
wait $bos        || FAILED=yes 
wait $construct  || FAILED=yes 
wait $contracts  || FAILED=yes 
wait $enums      || FAILED=yes 
wait $exceptions || FAILED=yes 
wait $hooks      || FAILED=yes 
wait $inherit    || FAILED=yes 
wait $objarg     || FAILED=yes 
wait $ordering   || FAILED=yes 
wait $overload   || FAILED=yes 
wait $sorting    || FAILED=yes 
wait $strings    || FAILED=yes 
wait $struct     || FAILED=yes 
if [ -n "$FAILED" ]; then
    wait
    echo FAILED
    exit 1
fi
make check $JOBS -C wrapper