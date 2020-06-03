#! /usr/bin/env python

import sys
import httplib
import urllib
import os.path
import re

_version_strip = r'\$Id\:.*\$'
_version_replace = r'(cvs ID suppressed)'


autoconf_archive_mirrors = [
    ( "gnu.wwc.edu", "/software/ac-archive"),
    ( "gnu.sunsite.utk.edu", "/software/ac-archive"),
    ( "www.gnu.org", "/software/ac-archive")
    ]

autoconf_archive_local_dir = "config/autoconf-archive-macros";


autoconf_archive_files = [
    "C_Support/acx_restrict.m4",
    "Java_Support/ac_prog_javac.m4",
    "Java_Support/ac_prog_javadoc.m4",
    "Java_Support/ac_try_compile_java.m4",
    "Java_Support/ac_java_options.m4",
    "Java_Support/ac_prog_java.m4",
    "Java_Support/ac_prog_java_works.m4",
    "Java_Support/ac_check_class.m4",
    "Java_Support/ac_check_classpath.m4",
    "Java_Support/ac_prog_javac_works.m4",
    "C++_Support/ac_cxx_namespaces.m4",
    "C++_Support/ac_cxx_have_std.m4",
    "C++_Support/ac_cxx_have_stl.m4",
    "C++_Support/ac_cxx_have_numeric_limits.m4",
    "C++_Support/ac_cxx_complex_math_in_namespace_std.m4",
    "C++_Support/ac_cxx_have_complex.m4",
    "C++_Support/ac_cxx_have_complex_math1.m4",
    "C++_Support/ac_cxx_have_complex_math2.m4",
    "C++_Support/ac_cxx_have_ieee_math.m4",
    "m4source/acx_pthread.m4",
    ]

llnl_autoconf_files = [
    "config/llnl-ac-macros/llnl_which_prog.m4",
    "config/llnl-ac-macros/llnl_lib_chasm.m4",
    "config/llnl-ac-macros/llnl_check_autoconf.m4",
    "config/llnl-ac-macros/llnl_check_automake.m4",
    "config/llnl-ac-macros/llnl_check_int32_t.m4",
    "config/llnl-ac-macros/llnl_check_int64_t.m4",
    "config/llnl-ac-macros/llnl_check_long_long.m4",
    "config/llnl-ac-macros/llnl_prog_java.m4",
    "config/llnl-ac-macros/llnl_prog_jar.m4",
    "config/llnl-ac-macros/llnl_cxx_library_ldflags.m4",
    "config/llnl-ac-macros/llnl_cxx_old_header_suffix.m4",
    "config/llnl-ac-macros/llnl_f77_c_config.m4",
    "config/llnl-ac-macros/llnl_f77_name_mangling.m4",
    "config/llnl-ac-macros/llnl_f90_c_config.m4",
    "config/llnl-ac-macros/llnl_f90_name_mangling.m4",
    "config/llnl-ac-macros/llnl_find_32bit_signed_int.m4",
    "config/llnl-ac-macros/llnl_find_64bit_signed_int.m4",
    "config/llnl-ac-macros/llnl_fortran90.m4",
    "config/llnl-ac-macros/llnl_func_drand_fortyeight.m4",
    "config/llnl-ac-macros/llnl_lib_fmain.m4",
    "config/llnl-ac-macros/llnl_lib_f90main.m4",
    "config/llnl-ac-macros/llnl_prevent_cross_compilation.m4",
    "config/llnl-ac-macros/llnl_prevent_unholy_gnu_nongnu_mix.m4",
    "config/llnl-ac-macros/llnl_prog_java.m4",
    "config/llnl-ac-macros/llnl_prog_javah.m4",
    "config/llnl-ac-macros/llnl_check_classpath.m4",
    "config/llnl-ac-macros/llnl_prog_python.m4",
    "config/llnl-ac-macros/llnl_python_library.m4",
    "config/llnl-ac-macros/llnl_python_numeric.m4",
    "config/llnl-ac-macros/llnl_python_shared_library.m4",
    "config/llnl-ac-macros/llnl_python_aix.m4",
    "config/llnl-ac-macros/llnl_sort_flibs.m4",
    "config/llnl-ac-macros/llnl_sort_f90libs.m4",
    "config/llnl-ac-macros/llnl_f90_pointer_size.m4",
    "config/llnl-ac-macros/llnl_f90_volatile.m4",
    "config/llnl-ac-macros/llnl_confirm_babel_c_support.m4",
    "config/llnl-ac-macros/llnl_confirm_babel_f77_support.m4",
    "config/llnl-ac-macros/llnl_confirm_babel_f90_support.m4",
    "config/llnl-ac-macros/llnl_confirm_babel_f03_support.m4",
    "config/llnl-ac-macros/llnl_confirm_babel_cxx_support.m4",
    "config/llnl-ac-macros/llnl_confirm_babel_python_support.m4",
    "config/llnl-ac-macros/llnl_confirm_babel_java_support.m4",
    "config/llnl-ac-macros/llnl_prog_test_ef.m4",
    "config/llnl-ac-macros/llnl_f77_library_ldflags.m4",
    "config/llnl-ac-macros/llnl_f77_dummy_main.m4",
    "config/llnl-ac-macros/llnl_libxml.m4",
    "config/llnl-ac-macros/libtool.m4",
    "config/llnl-ac-macros/ltdl.m4"
    ]

def print_help():
    print sys.argv[0],""" [--download] [output-file [ input1 [ input2 [...]]]]
    Regenerates an acinclude.m4 file by concatenating files in the argument list
    with other m4 files in the autoconf archive.

    If output-file is not specified, \"acinclude.m4\" is used as the default.

    The optional --download flag forces an update from a GNU mirror.
    """

def find_mirror():
    """
    returns a URL root to a working autoconf-archive mirror
    or None if can't get a good connection to any of them.
    """
    for i in autoconf_archive_mirrors:
        conn = httplib.HTTPConnection( i[0] )
        sys.stdout.write("Searching for website/mirror " + i[0] + i[1] + "...")
        sys.stdout.flush()
        conn.request("GET", i[1]+"/index.html")
        r1 = conn.getresponse()
        if r1.status == 200:
            print "okay"
            return i
        else:
            print r2.status, r2.reason
        conn.close()
    return None

def slurp_urls():
    (ip,root) = find_mirror()
    for i in autoconf_archive_files:
        cachefile = open(os.path.join( autoconf_archive_local_dir, os.path.basename(i) ),'w')
        url = "http://" + ip + root + "/" + i
        sys.stdout.write(" ... downloading \"%s\"... " % i )
        sys.stdout.flush()
        conn = httplib.HTTPConnection( ip )
        conn.request( "GET", root + "/" + i )
        r = conn.getresponse()
        if r.status == 200:            
            cachefile.write("\n\ndnl *** Downloaded from " + url + "***\n" )
            cachefile.write( re.sub(_version_strip, _version_replace, r.read() ))
            cachefile.write("\n\n")            
            print "done"
        else:
            cachefile.write("\n\ndnl !!! Attempt for " + url + " FAILED !!!\n" )
            cachefile.write("dnl !!! %d" % r.status + " " + r.reason + "\n\n" )
            print r.status, r.reason
        conn.close()
        cachefile.close()

def local_autoconf( outfile ):
    for i in autoconf_archive_files:
        fname = os.path.join(autoconf_archive_local_dir, os.path.basename(i) )
        sys.stdout.write(" ...appending \"%s\"... " % fname )
        f=open(fname,'r')
        outfile.write('\n\ndnl *** file: ' + fname + '\n')
        outfile.write( re.sub(_version_strip, _version_replace, f.read() ))
        print "done"
        
def append_local_macros( outfile ):
    for i in llnl_autoconf_files:
        sys.stdout.write(" ... appending \"%s\"... " % i )
        s=open(i,'r')
        outfile.write('\n\ndnl *** file: ' + i + '\n')
        outfile.write( re.sub(_version_strip, _version_replace, s.read() ))
        s.close()        
        print "done"

def append_args( outfile, args ):
    for i in args:
        sys.stdout.write(" ... appending \"%s\"... " % i )
        s=open(i,'r')
        outfile.write('\n\ndnl *** file: ' + i + '\n')
        outfile.write( re.sub(_version_strip, _version_replace, s.read() ))
        s.close()
    print "done"

if __name__ == "__main__":
    if "--help" in sys.argv:
        print_help()
    else:
        if "--download" in sys.argv:
            sys.argv.remove("--download")
            slurp_urls()
        print sys.argv
        if len(sys.argv) > 1:
            f = open( sys.argv[1],'w' )
            sys.argv.pop()
        else:
            f = open( "acinclude.m4",'w')
        local_autoconf(f)
        append_local_macros(f)
        append_args(f, sys.argv[1:])
        f.close()
            

