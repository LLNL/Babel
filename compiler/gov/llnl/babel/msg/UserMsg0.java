package gov.llnl.babel.msg;

/**
 * Encapsulation of a single Error, Warning, or Remark based on two conflicting 
 * tokens.
 */
public class UserMsg0 extends UserMsg {

  public UserMsg0(int type, String msg) {
    super(type, msg);
  }
  
  public String toString() { 
    return d_msg;
  } 
}
