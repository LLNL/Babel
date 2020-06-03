//
// File:        ChangeWriter.java
// Package:     gov.llnl.babel.writers
// Copyright:   (c) 2003 Lawrence Livermore National Security, LLC
// Revision:    @(#) $Revision: 7188 $
// Date:        $Date: 2011-09-27 11:38:42 -0700 (Tue, 27 Sep 2011) $
// Description: FileWriter that generates a temporary
// 
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

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

/**
 * This class will write a file leaving its last time modified unchanged if
 * the content is unchanged. When overwriting a file, this {@link
 * java.io.Writer} will initially write a temporary file. When {@link
 * #close} is called, it compares the contents of the original file with the
 * temporary file. If the contents have changed, it will overwrite the
 * original file with the contents of the temporary file; otherwise, the
 * original file is left unchanged. Thus, the last time modified of the
 * original file is only changed when the contents change. The temporary
 * file is deleted when {@link #close} is called.
 *
 *
 * @author Tom Epperly <tepperly@llnl.gov>
 * @version $Id: ChangeWriter.java 7188 2011-09-27 18:38:42Z adrian $
 */
public class ChangeWriter extends Writer {

  /**
   * This is where the original file is located.
   */
  private File    d_finalDestination;

  /**
   * This is the temporary file. The contents are initial written here.
   */
  private File    d_temporaryFile;

  /**
   * This {@link java.io.Writer} writes to {@link d_temporaryFile}. If
   * <code>null</code>, it indicates that the {@link ChangeWriter} is
   * closed.
   */
  private Writer  d_output;
  
  /**
   * Create a writer to write a new file or overwrite an old file.
   * You may prefer to use {@link #createWriter} instead of this
   * constructor; it uses the simpler {@link java.io.FileWriter} if 
   * destination does not already exist.
   *
   * @param destination   this is the location of the new file
   * @param directory     this is the directory where the temporary
   *                      file will be created. If <code>null</code>,
   *                      a system dependent temporary will be
   *                      used (e.g., /tmp on Unix).
   * @exception java.io.IOException
   *    something went wrong creating the {@link java.io.Writer}.
   */
  public ChangeWriter(File destination, File directory) 
    throws IOException
  {
    d_finalDestination = destination;
    d_temporaryFile = File.createTempFile("babel", null, directory);
    d_temporaryFile.deleteOnExit();
    d_output = new BufferedWriter(new FileWriter(d_temporaryFile));
  }

  /**
   * Create a {@link java.io.Writer} for a particular filename and directory
   * combination.
   *
   * @param filename   the filename without any leading directory information.
   * @param directory  the directory name. This string will be prepended
   *                   to filename to get the whole path to the file.
   *                   If it's not an empty string, it should end with
   *                   the directory separator character,
   *                   {@link java.io.File#separatorChar}.
   * @return           a writer that will write to (directory + filename).
   *                   This is typically a {@link ChangeWriter} or
   *                   a {@link java.io.FileWriter}. The final type
   *                   shouldn't really matter to the caller.
   * @exception java.io.IOException
   *    something went wrong creating the {@link java.io.Writer}.
   */
  public static Writer createWriter(String filename, String directory)
    throws IOException
  {
    File dest = new File(directory + filename);
    // can modify checks for existence and abililty to modify
    if (!dest.canWrite()) return new BufferedWriter(new FileWriter(dest));
    if (directory.length() > 1) {
      return new ChangeWriter(dest, 
                              new File(directory.substring
                                       (0, directory.length() - 1)));
    }
    else {
      return new ChangeWriter(dest, new File("."));
    }
  }

  /**
   * Write a string.
   * @param     str the string to be output.
   * @exception java.io.IOException
   *    There was a problem writing to the output device.
   *    Writing to a closed ChangeWriter also produces an 
   *    {@link java.io.IOException}.
   */
  public void write(String str) throws IOException
  {
    synchronized(lock) {
      if (null == d_output) {
        throw new IOException("Cannot write to closed Writer.");
      }
      d_output.write(str);
    }
  }

  /**
   * Write a character array.
   * @param     cbuf the character array to be output.
   * @exception java.io.IOException
   *    There was a problem writing to the output device.
   *    Writing to a closed ChangeWriter also produces an 
   *    {@link java.io.IOException}.
   */
  public void write(char[] cbuf) throws IOException
  {
    synchronized(lock) {
      if (null == d_output) {
        throw new IOException("Cannot write to closed Writer.");
      }
      d_output.write(cbuf);
    }
  }

  /**
   * Write a substring.
   * @param     str the whole string
   * @param     off the offset of the first character to be written.
   * @param     len the number of characters to write.
   * @exception java.io.IOException
   *    There was a problem writing to the output device.
   *    Writing to a closed ChangeWriter also produces an 
   *    {@link java.io.IOException}.
   */
  public void write(String str, int off, int len) throws IOException
  {
    synchronized(lock) {
      if (null == d_output) {
        throw new IOException("Cannot write to closed Writer.");
      }
      d_output.write(str, off, len);
    }
  }

  /**
   * Write a character.
   * @param     c the character to be output.
   * @exception java.io.IOException
   *    There was a problem writing to the output device.
   *    Writing to a closed ChangeWriter also produces an 
   *    {@link java.io.IOException}.
   */
  public void write(int c) throws IOException
  {
    synchronized(lock) {
      if (null == d_output) {
        throw new IOException("Cannot write to closed Writer.");
      }
      d_output.write(c);
    }
  }

  /**
   * Write part of a character array.
   * @param     cbuf the character array to be output.
   * @param     off  the index of the first character to be written.
   * @param     len  the number of characters to write.
   * @exception java.io.IOException
   *    There was a problem writing to the output device.
   *    Writing to a closed ChangeWriter also produces an 
   *    {@link java.io.IOException}.
   */
  public void write(char[] cbuf, int off, int len)
    throws IOException
  {
    synchronized (lock) {
      if (null == d_output) {
        throw new IOException("Cannot write to closed Writer.");
      }
      d_output.write(cbuf, off, len);
    }
  }

  /**
   * This will flush all the buffered output into the temporary file.
   * Because all output goes to the temporary file first, flushing will
   * not force an update to the final file. The decision on whether
   * to update the final file or not is made when {@link #close} is
   * called.
   *
   * @exception java.io.IOException
   *    There was a problem flushing the output device.
   *    Flushing a closed ChangeWriter also produces an 
   *    {@link java.io.IOException}.
   */
  public void flush()
    throws IOException
  {
    synchronized (lock) {
      if (null == d_output) {
        throw new IOException("Cannot flush closed Writer.");
      }
      d_output.flush();
    }
  }

  /**
   * Return true if the contents of the temporary file and the final file
   * differ. This will always return true if the final file does not
   * exist.
   *
   * @exception java.io.IOException
   *    There was a problem reading one of the files.
   */
  private boolean filesDiffer() 
    throws IOException
  {
    long currentLength = d_finalDestination.length();
    if ((currentLength > 0) &&
        (d_temporaryFile.length() == currentLength)) {
      InputStream finalIn = null;
      InputStream temp = null;
      int finalChar, tempChar;
      try {
        finalIn = new FileInputStream(d_finalDestination);
        finalIn = new BufferedInputStream(finalIn);
        temp = new FileInputStream(d_temporaryFile);
        temp = new BufferedInputStream(temp);
        do {
          finalChar = finalIn.read();
          tempChar = temp.read();
        } while ((finalChar == tempChar) && (finalChar >= 0));
        return finalChar != tempChar;
      }
      finally {
        try {
          if (finalIn != null) finalIn.close();
        }
        finally {
          if (temp != null) temp.close();
        }
      }
    }
    return true;
  }

  /**
   * Copy the temporary file to the final file.
   */
  private void copyTempToFinal()
    throws IOException
  {
    FileInputStream temp = null;
    FileOutputStream finalOut = null;
    int numBytes;
    byte [] buffer = new byte[1024];
    try {
      temp = new FileInputStream(d_temporaryFile);
      finalOut = new FileOutputStream(d_finalDestination);
      while ((numBytes = temp.read(buffer)) >= 0) {
        finalOut.write(buffer, 0, numBytes);
      }
    }
    finally {
      try {
        if (temp != null) temp.close();
      }
      finally {
        if (finalOut != null) finalOut.close();
      }
    }
  }

  /**
   * Complete writing to the original file if needed. This compares
   * the contents of the temporary file and the original file. If they
   * differ or if the original file does not exist, this copies the
   * contents from the temporary file to the original file. This will
   * remove the temporary file.
   *
   * @exception java.io.IOException
   *    This method does a lot compared to many close methods. It must
   *    compare the temporary file, and then try to write the original
   *    file. There are many possible reasons for an exception.
   */
  public void close()
    throws IOException
  {
    synchronized(lock) {
      if (null != d_output) {
        try {
          d_output.close();
          if (filesDiffer()) {
            copyTempToFinal();
          }
        }
        finally {
          d_output = null;
          d_finalDestination = null;
          d_temporaryFile.delete();
          d_temporaryFile = null;
        }
      }
    }
  }

  /**
   * Make sure to close the writer if the programmer should forget to do so.
   */
  protected void finalize() 
    throws Throwable 
  {
    try {
      close();
    }
    finally {
      super.finalize();
    }
  }
}
