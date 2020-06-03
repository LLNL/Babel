//
// File:        LineCountingFilterWriter.java
// Package:     gov.llnl.babel.backend.writers
// Revision:    @(#) $Id: LineCountingFilterWriter.java 7188 2011-09-27 18:38:42Z adrian $
// Description: a pretty writer class to aid in formatting backend output
//
// Copyright (c) 2000-2001, Lawrence Livermore National Security, LLC
// Produced at the Lawrence Livermore National Laboratory.
// Written by the Components Team <components@llnl.gov>
// UCRL-CODE-2002-054
// All rights reserved.
// 
// This file is part of Babel. For more information, see
// http://www.llnl.gov/CASC/components/. Please read the COPYRIGHT file
// for Our Notice and the LICENSE file for the GNU Lesser General Public
// License.
// 
// This program is free software; you can redistribute it and/or modify it
// under the terms of the GNU Lesser General Public License (as published by
// the Free Software Foundation) version 2.1 dated February 1999.
// 
// This program is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the IMPLIED WARRANTY OF
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the terms and
// conditions of the GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License
// along with this program; if not, write to the Free Software Foundation,
// Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package gov.llnl.babel.backend.writers;

import java.io.FilterWriter;
import java.io.IOException;

/**
 * The <code>LineCountingFilterWriter</code>
 * keeps track of the current line going through the character
 * stream.  If the write throws an exception, the line count
 * remains unchanged.
 */
public class LineCountingFilterWriter extends FilterWriter {
    
    protected int d_line_count; 

    public LineCountingFilterWriter( java.io.Writer out ) { 
        super(out);
        d_line_count=1;
    }
 
    public int getLineCount() { 
        return d_line_count;
    }

    public void setLineCount( int i ) { 
        d_line_count = i;
    }

    public void write(int c) throws IOException { 
        out.write(c);
        if ((char) c == '\n') { 
            ++d_line_count;
        }
    }

    public void write( char[] cbuf, int off, int len) throws IOException { 
        out.write( cbuf, off, len );
        d_line_count += countLines( cbuf, off, len );
    }

    public void write( String str, int off, int len ) throws IOException { 
        out.write( str, off, len );
        d_line_count += countLines( str.toCharArray(), off, len );
    }
    
    protected int countLines(char[] x, int off, int len) { 
        int count = 0;
        int max = off+len;
        for ( int i=off; i<max; i++ ) { 
            if (x[i]=='\n') { 
                count++;
            }
        }
        return count;
    }
        
}
