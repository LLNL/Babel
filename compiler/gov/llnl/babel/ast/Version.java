package gov.llnl.babel.ast;

/**
 * Encapsualtes a version string (dot separated numbers).
 *
 */
public class Version implements Comparable {

  int d_version[];
  
  /**
   * Constructor
   * 
   * @param s
   *           dot separated list of nonnegative integers
   */
  public Version(String s) {
    if (s == null || s.length() < 3) {
      d_version = new int[0];
      return;
    }
    // first count the number of dots
    char data[] = s.toCharArray();
    int count = 1; // yes init to one
    for (int i = 0; i < data.length; i++) {
      if (data[i] == '.') {
        count++;
      }
    }
    // create the array
    d_version = new int[count];
    // convert individual dot-separated numbers from text to ints
    int start = 0;
    for (int i = 0; i < count; ++i) {
      int end = s.indexOf('.', start);
      if (end == -1) {
        end = s.length();
      }
      d_version[i] = Integer.parseInt(s.substring(start, end));
      start = end + 1;
    }
  }
  
  /**
   * Not strictly lexigraphic since 4.0.0.0 == 4.0, but 4.0.0.1 > 4.0
   */
  public int compareTo(Object o) {
    if (!(o instanceof Version)) {
      return -1;
    }
    Version v = (Version) o;
    
    // construct temporary arrays if one of the main ones is too short
    int maxlen = (d_version.length >= v.d_version.length) ? d_version.length
      : v.d_version.length;
    int[] first;
    if (maxlen == d_version.length) {
      first = d_version;
    } else {
      first = new int[maxlen];
      int i = 0;
      for (; i < d_version.length; i++) {
        first[i] = d_version[i];
      }
      for (; i < maxlen; i++) {
        first[i] = 0;
      }
    }
    int[] second;
    if (maxlen == v.d_version.length) {
			second = v.d_version;
    } else {
      second = new int[maxlen];
      int i = 0;
      for (; i < v.d_version.length; i++) {
        second[i] = v.d_version[i];
      }
      for (; i < maxlen; i++) {
        second[i] = 0;
      }
    }
    
    // now do lexographic compare on the temporary arrays.
    for (int i = 0; i < maxlen; i++) {
      int diff = first[i] - second[i];
      if (diff != 0) {
        return diff;
      }
    }
    
    // else they are identical
    return 0;
  }
  
  public boolean equals(Object o) {
    return (compareTo(o) == 0);
  }

  public int hashCode() {
    int result = 0, g;
    for(int i = 0; i < d_version.length; ++i) {
      result = (result << 4) + d_version[i]*13;
      g = (result & 0xf0000000);
      if (g != 0) {
        result = result ^ (g >> 24);
        result = result ^ g;
      }
    }
    return result;
  }
  
  public String toString() {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < d_version.length; ++i) {
      sb.append(Integer.toString(d_version[i]));
      sb.append(".");
    }
    int len = sb.length();
    if (sb.lastIndexOf(".") == (len - 1)) {
      sb.deleteCharAt(sb.length() - 1);
    }
    return sb.toString();
  }
}
