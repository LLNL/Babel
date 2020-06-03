from subprocess import *

def write_data( data, file ):
    f=open( file + ".dat",'w')
    f.write('# A GNUplot grid in matrix format\n')
    s = data.shape
    for row in range(s[0]):
        for col in range(s[1]):
            print >> f , data[row][col], " ",
       	print >> f
    f.close()
    return

def plot_data( title , file ):
    pipe = Popen('gnuplot',shell=True,stdin=PIPE).stdin
    pipe.write('set terminal gif\n')
    pipe.write('set output \'%s.gif\'\n' %(file,))
    pipe.write('set border 4095 lt 1.000\n')
    pipe.write('set view map\n')
    pipe.write('set samples 50,50\n')
    pipe.write('set isosamples 50,50\n')
    pipe.write('unset surface\n')
    pipe.write('set style data pm3d\n')
    pipe.write('set style function pm3d\n')
    pipe.write('set ticslevel 0\n')
    pipe.write('set pm3d at b\n')
    pipe.write('set title \"%s\"\n' %(title,))
    pipe.write('splot \'%s.dat\' matrix\n' %(file,))
    pipe.close()

def plot2( data, title, file ):
    write_data( data, file )
    plot_data( title, file )
    return
