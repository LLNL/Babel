package gov.llnl.babel.ast;

public class DocComment {

  protected String d_text;

  public DocComment(String rawText) {
    d_text = rawText;
  }

  /**
   * @return text stripped of adornments
   */
  public String toString() {
    return d_text;
  }

}
