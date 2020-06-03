#!/usr/local/bin/bash
DIR=/nfs/tmp2/$USER/babel
mkdir -p $DIR
cd $DIR
PREFIX=$HOME/babel/install
PATH=$PREFIX/bin:$PATH
LD_LIBRARY_PATH=$PREFIX/lib:$LD_LIBRARY_PATH
JOBS=`cat /proc/cpuinfo |sed 's/ //g' |awk 'BEGIN {FS=":"; n=0} {if ($1 ~ "processor") n=$2} END {print n+1}'`
echo "Compiling with $JOBS jobs."

case "$HOSTNAME" in
    ubgl*|udawn*)
	echo please update svn from a Linux machine
	#rsync -avz $DIR/babel /tmp/$USER/babel/
	;;
    *)
	svn co svn+ssh://svn.cca-forum.org/svn/babel/trunk babel  
	;;
esac
mkdir -p install

case "$1" in
    gcc)
	JAVAPREFIX=/usr/lib/jvm/java-1.5.0-ibm.x86_64
	PYTHON=/usr/apps/python2.6/bin/python
	PYTHONPATH=/usr/apps/python2.6/lib
	VERSION=4.4.4
	CPP='gcc -E'
	CC=gcc-$VERSION
	CXX=g++-$VERSION
	FC=gfortran-$VERSION
	F77=gfortran-$VERSION
	FLAGS='-O3 -g'
	LD=ld
	LDFLAGS=
	EXTRA=
	;;
    intel)
	JAVAPREFIX=/usr/lib/jvm/java-1.5.0-ibm-1.5.0.12.2.x86_64
	JAVAPREFIX=/usr/lib/jvm/java-1.6.0-sun.x86_64
	PYTHON=/usr/apps/python2.6/bin/python
	PYTHONPATH=/usr/apps/python2.6/lib
	VERSION=12.1.233
	CPP='gcc -E'
	CC=icc-$VERSION
	CXX=icpc-$VERSION
	FC=ifort-$VERSION
	F77=ifort-$VERSION
	FLAGS='-O3 -g'
	LD=ld
	LDFLAGS=
	EXTRA=
	;;
    shmintel)
	JAVAPREFIX=/usr/lib/jvm/java-1.5.0-ibm-1.5.0.12.2.x86_64
	JAVAPREFIX=/usr/lib/jvm/java-1.6.0-sun.x86_64
	PYTHON=/usr/apps/python2.6/bin/python
	PYTHONPATH=/usr/apps/python2.6/lib
	VERSION=12.0.191
	CPP='gcc -E'
	CC=icc-$VERSION
	CXX=icpc-$VERSION
	FC=ifort-$VERSION
	F77=ifort-$VERSION
	FLAGS='-O3 -g'
	LD=ld
	LDFLAGS=
	EXTRA=
	;;
    pgi)
	JAVAPREFIX=/usr/lib/jvm/java-1.6.0-sun.x86_64
	PYTHON=/usr/apps/python2.6/bin/python
	PYTHONPATH=/usr/apps/python2.6/lib
	#VERSION=10.9
	VERSION=11.5
	#VERSION=9.0.2
	CPP='gcc -E'
	CC=pgcc-$VERSION
	CXX=pgCC-$VERSION
	FC=pgf95-$VERSION
	F77=pgf95-$VERSION
	FLAGS='-O3 -g -Melf -Mdwarf3 -traceback'
	LD=ld
	MAKE_LDFLAGS="-Wc,-nomp -Wl,-lgcc_eh"
	EXTRA="--with-F90-vendor=PGI"
	;;
    pathscale)
	JAVAPREFIX=/usr/lib/jvm/java-1.6.0-sun.x86_64
	PYTHON=/usr/apps/python2.6/bin/python
	PYTHONPATH=/usr/apps/python2.6/lib
	VERSION=3.2.99
	CPP='gcc -E'
	CC=pathcc-$VERSION
	CXX=pathCC-$VERSION
	FC=pathf90-$VERSION
	F77=pathf90-$VERSION
	FLAGS='-O3 -g -fPIC'
	EXTRA="--with-F90-vendor=PathScale"
	;;
    bgl) # uBGL
	JAVAPREFIX=/usr/global/tools/sdm/java/Linux/ppc64/ibm-java-ppc-60/
	#PYTHON=/usr/apps/python2.6/bin/python
	#PYTHONPATH=/usr/apps/python2.6/lib
	VERSION=
	CPP='/usr/bin/gcc -E'
	LD=ld
	CC=blrts_xlc
	CXX=blrts_xlc++
	FC=blrts_xlf2003 #/opt/ibmcmp/xlf/bg/11.1/bin/f2003
	F77=blrts_xlf
	FLAGS='-O2 -g'
        # touch test.f && mpixlf_r -v -o test test.f
	EXTRA="--target=powerpc64-ibm-bgl --host=powerpc64-ibm-bgl --with-F90-vendor=IBMXL --disable-shared --disable-pdt" 
	;;
    bgp) # dawn
	JAVAPREFIX=/usr/global/tools/sdm/java/Linux/ppc64/ibm-java-ppc-60/
	#PYTHON=/bgsys/drivers/ppcfloor/gnu-linux/bin/python
	#PYTHONPATH=/bgsys/drivers/ppcfloor/gnu-linux/lib
	PYTHON=/usr/bin/python
	PYTHONPATH=/usr/lib
	VERSION=
	CPP='/usr/bin/gcc -E'
	LD=ld
	# the babel wrappers make sure that linker options are prefixed with -Wl,
        # libtool removes those prefixes because it assumes that eg. FCLD is ld
        # but mpixlc acts only as a fronted and would get confused by something like -rpath
	function gen_wrapper() {
	    cat <<EOF
#!/bin/bash
            for arg in "\$@"; do
	      case "\$arg" in 
		  -L*) arg="-Wl,\$arg" ;;
		  -R*) arg="-Wl,\$arg" ;;
                  -r*) arg="-Wl,\$arg" ;;
                  -l*) arg="-Wl,\$arg" ;;
	      esac
	      args="\$args \`printf %q \"\$arg\"|sed s/.,/,/g\`"
	    done
	    eval "$1 \$args"
EOF
	}

	for compiler in mpixlc_r mpixlcxx_r mpixlf2003_r mpixlf77_r; do 
	    gen_wrapper $compiler >$PREFIX/bin/babel_$compiler
	    chmod u+x $PREFIX/bin/babel_$compiler
	done
	CC=$PREFIX/bin/babel_mpixlc_r # _r is the thread-safe variant
	CXX=$PREFIX/bin/babel_mpixlcxx_r
	FC=$PREFIX/bin/babel_mpixlf2003_r
	F77=$PREFIX/bin/babel_mpixlf77_r
	FLAGS='-O0 -g'
        # touch test.f && mpixlf_r -v -o test test.f
	FCLIBS='-L/bgsys/drivers/V1R4M2_200_2010-100508P/ppc/comm/default/lib,-L/bgsys/drivers/V1R4M2_200_2010-100508P/ppc/comm/default/lib,-L/bgsys/drivers/V1R4M2_200_2010-100508P/ppc/comm/sys/lib,-L/bgsys/drivers/V1R4M2_200_2010-100508P/ppc/runtime/SPI,-L/opt/ibmcmp/xlsmp/bg/1.7/bglib,-L/opt/ibmcmp/xlmass/bg/4.4/bglib,-L/opt/ibmcmp/xlf/bg/11.1/bglib,-L/bgsys/drivers/V1R4M2_200_2010-100508P/ppc/gnu-linux/lib/gcc/powerpc-bgp-linux/4.1.2,-L/bgsys/drivers/V1R4M2_200_2010-100508P/ppc/gnu-linux/lib/gcc/powerpc-bgp-linux/4.1.2/../../../../powerpc-bgp-linux/lib,-lmpich.cnk,-lopa,-ldcmf.cnk,-ldcmfcoll.cnk,-lpthread,-lSPI.cna,-lrt,-dynamic-linker,/lib/ld.so.1,-lxlf90_r,-lxlopt,-lxlomp_ser,-lxl,-lxlfmath,-ldl,-lpthread,-lm,-lc,-lgcc,-lgcc_eh'
        FLIBS="$FCLIBS"
	EXTRA="--target=powerpc64-ibm-bgp --host=powerpc64-ibm-bgp --with-F90-vendor=IBMXL --disable-shared --disable-python"
	;;
    *)
	echo "unknown configuration in arg1"
	exit
esac

# for python build_ext
alias cc=$CC

function compile() {
    if [ -e $PREFIX/bin/$1 ]; then
	echo "skipping $1"
        return
    fi		
    echo building $1
    wget -nc http://ftp.gnu.org/pub/gnu/$1/$1-$2.tar.gz
    tar -xzf $1-$2.tar.gz || exit 1
    mkdir $1.build
    cd $1.build
    SRCDIR=../$1-$2
    shift 2
    ( $SRCDIR/configure --prefix=$PREFIX $* && make -j8 && make install ) || exit 1
    cd ..
}

compile autoconf 2.68
compile automake 1.11.1
compile libtool 2.4

export PATH=$JAVAPREFIX/bin:$PATH

cd babel
if [ -x ./configure -a -x runtime/configure ]; then
    echo "Skipping autogen"
else
    ./autotool_rebuild.sh || exit 1
fi
cd ..
rm -rf babel.build.$1
mkdir babel.build.$1
cd babel.build.$1
set -x
../babel/configure \
    -C \
    --prefix=$PREFIX --enable-leave-preprocessed \
    $EXTRA \
    JAVAPREFIX="$JAVAPREFIX" JNI_INCLUDES="$JAVAPREFIX/include" \
    PYTHON="$PYTHON" PYTHONPATH="$PYTHONPATH" \
    CPP="$CPP" CC="$CC" CXX="$CXX" FC="$FC" F77="$F77" LD="$LD" \
    CFLAGS="$FLAGS" \
    CXXFLAGS="$FLAGS" \
    FFLAGS="$FLAGS" \
    FCFLAGS="$FLAGS" \
    LDFLAGS="$LDFLAGS" \
    && nice -n19 make -j$JOBS \
    && nice make check -j$JOBS \
    && nice make -C regression check-rmi
