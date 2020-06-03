set path = (/usr/global/tools/sdm/java/Linux/ppc64/IBMJava2-ppc-142/bin ${HOME}/current/ubgl/autotools/install/bin $path)
setenv CC mpxlc
setenv CXX mpxlC
setenv F77 mpxlf
setenv FC  mpxlf90
setenv CPP gcc

setenv CFLAGS '-O3 -qmaxmem=-1 -qhot=novector -qarch=440 -qtune=440 -DNDEBUG'
setenv CXXFLAGS '-O3 -qmaxmem=-1 -qhot=novector -qarch=440 -qtune=440 -DNDEBUG'
setenv FFLAGS '-O3 -qmaxmem=-1 -qhot=novector -qarch=440 -qtune=440'
setenv FCLAGS '-O3 -qmaxmem=-1 -qhot=novector -qarch=440 -qtune=440'
# using CHASM 1.4RC1
# ./configure --with-F90=mpxlf90 --with-F90-vendor=IBMXL --disable-shared \
#     --disable-pdt --prefix=/g/g10/epperly/current/chasm_ubgl/install
setenv CHASMPREFIX ${HOME}/current/chasm_ubgl/install
