package gov.llnl.babel.msg;

import java.util.LinkedList;
import java.util.Iterator;

/**
 * Contains a list of UserMsg, and displays various subsets of them
 * 
 * @see UserMsg
 */
public class MsgList {

  private LinkedList d_list;

  private int d_max_items = 50;
  /** 
   * a list of flags indicating the type of messages to print. 
   * corresponds to UserMsg.INTERNAL_ERROR, UserMsg.ERROR, UserMsg.WARNING, and UserMsg.REMARK 
   */
  private boolean print_on[] = { true, true, true, false };

  /**
   * a list of flags indicating what do die on.
   * corresponds to UserMsg.INTERNAL_ERROR, UserMsg.ERROR, UserMsg.WARNING, and UserMsg.REMARK 
   */
  private boolean die_on[] = { true, true, false, false };
  
  /**
   * a count of the different types of messages
   */
  private int count[] = {0,0,0,0};
 
  private int verbiosity = 2;
  
  /**
   * Create an empty message list
   */
  public MsgList() {
    d_list = new LinkedList();
  }

  /**
   * Add a new message to the List
   */
  public void addMsg( UserMsg msg ) { 
    d_list.add(msg);
    count[ msg.getType() ]++;
  }
  
  /**
   * Returns true iff a fatal message is included in the list.
   *
   */
  public boolean fatal_message() { 
    return ( count[UserMsg.INTERNAL_ERROR] > 0) || 
           ( (count[UserMsg.ERROR] > 0) && die_on[UserMsg.ERROR] ) ||
           ( (count[UserMsg.WARNING] > 0) && die_on[UserMsg.WARNING]) ||
           ( (count[UserMsg.REMARK] > 0) && die_on[UserMsg.REMARK]);
  }

  /**
   * Set a particular type of message to print or not.
   * By default, errors and warnings are printed, remarks are not.
   * Note that internal errors are always printed and cannot be suppressed.
   * 
   * @param type one of UserMsg.ERROR, UserMsg.WARNING, or UserMsg.REMARK
   * @param setOrNot true enables printing, false disables
   */
  public void setPrintCondition( int type, boolean setOrNot ) { 
    if ( type == UserMsg.INTERNAL_ERROR ) { return; }
    print_on[type] = setOrNot;
  }
  
  /**
   * Set a particular type of message to trigger fatal errors, or not.
   * By default errors are fatal, warnings and remarks are not.
   * Note that internal errors are always fatal and cannot be changed. 
   * 
   * @param type one of UserMsg.ERROR, UserMsg.WARNING, or UserMsg.REMARK
   * @param setOrNot true enables printing, false disables
   */
  public void setFatalCondition( int type, boolean setOrNot ) { 
    if ( type == UserMsg.INTERNAL_ERROR ) { return; }
    die_on[type] = setOrNot; 
  }
  
  /**
   * 
   */
  public void print( java.io.PrintStream out ) {
    // stablesort the list in place
    java.util.Collections.sort(d_list);
    
    // now print the top d_max_items items
    int nItems = 0;
    for( Iterator i = d_list.iterator(); i.hasNext() && nItems<d_max_items; ) { 
      UserMsg msg = (UserMsg) i.next();
      if ( print_on[msg.getType()] ) { 
        msg.setVerbiosity( verbiosity );
        out.println("- #" + (nItems+1) + "----------------------------------------");
        out.println(msg);
        nItems++;
      }
    }
    if ( nItems >= d_max_items ) { 
       out.println("--- Messages truncated after "+d_max_items+" items.-------------------");
    }
  }
}
