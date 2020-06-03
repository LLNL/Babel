from Gnuplot import Gnuplot, GridData

class FieldView:

    def __init__( self, data, title, color_range=None ):
        self.G = Gnuplot(persist=True)
	self.G('set border 4095 lt 1.000')
	self.G('set view map')
	self.G('set samples 50,50')
	self.G('set isosamples 50,50')
	self.G('unset surface')
	self.G('set style data pm3d')
	self.G('set style function pm3d')
	self.G('set ticslevel 0')
        self.G('set terminal x11')
	self.G('set pm3d at b')
        if color_range:
            self.G('set cbrange [%d:%d]' % color_range)
        self.data = data
	self.gdata = GridData(self.data)
        self.change_title( title )
	self.change_data( data )
	return

    def change_title( self, title ):
        temp = 'set title "' + title + '"'
	self.G( temp )
	self.G.splot( self.gdata )
	return

    def change_data( self, data ):
	self.data = data
	self.gdata = GridData(self.data)
	self.G.splot( self.gdata )
	return
