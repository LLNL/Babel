#!/usr/bin/env python
#
# File:        genmakefile.py
# Revision:    $Revision: 7188 $
# Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
# Description: Python to generate Makefile.am fragment used runtime/Makefile.am
# Copyright (c) 2005, Lawrence Livermore National Security, LLC.
# Produced at the Lawrence Livermore National Laboratory.
# Written by the Components Team <components@llnl.gov>
# UCRL-CODE-2002-054
# All Rights Reserved.
# 
# This file is part of Babel. For more information, see
# http://www.llnl.gov/CASC/components/. Please read the COPYRIGHT file
# for Our Notice and the LICENSE file for the GNU Lesser General Public
# License.
# 
# This program is free software; you can redistribute it and/or modify it
# under the terms of the GNU Lesser General Public License (as published by
# the Free Software Foundation) version 2.1 dated February 1999.
# 
# This program is distributed in the hope that it will be useful, but
# WITHOUT ANY WARRANTY; without even the IMPLIED WARRANTY OF
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the terms and
# conditions of the GNU Lesser General Public License for more details.
# 
# You should have received a copy of the GNU Lesser General Public License
# along with this program; if not, write to the Free Software Foundation,
# Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

from os.path import walk, join
from re import compile

def compressLines(lines):
    i = 0
    while i < len(lines):
        if lines[i][-2:] == "\\\n":
            lines[i] = lines[i][0:-2] + lines[i+1]
            del lines[i+1]
        else:
            lines[i] = lines[i].strip()
            if len(lines[i]) == 0:
                del lines[i]
            else:
                i = i+1
        

def parseBabelMake(filelist, dirname):
    i = open(join(dirname, "babel.make"))
    lines = i.readlines()
    i.close()
    if (len(dirname) >= 2 and dirname[:2] == "./"):
        dirname = dirname[2:]
    compressLines(lines)
    assignment = compile(r'\s*([A-Za-z][A-Za-z_]*)\s*=\s*(.*)')
    for l in lines:
        match = assignment.match(l)
        if match:
            genfiles = match.group(2).split()
            for gf in genfiles:
                filename = join(dirname, gf)
                if (len(filename) >= 2 and filename[:2] == "./"):
                    filename = filename[2:]
                filelist.append(filename)

def visit(filelist, dirname, names):
    if "babel.make" in names:
        parseBabelMake(filelist, dirname)

genfilelist = [ 'setup.py' ]
walk('.', visit, genfilelist)
print "# USE runtime/python/genmakefile.py to GENERATE THIS LIST"
print "# ./configure ; make ; cd runtime/python ; python genmakefile.py"
print "RUNTIME_ONLY_FILES=\\"
genfilelist.sort()
for file in genfilelist[0:-1]:
        print "\t\t", join('python', file), "\\"
print "\t\t", join('python', genfilelist[-1])

