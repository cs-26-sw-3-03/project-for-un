import javax.swing.*;
import javax.swing.text.*;

class IntDocumentFilter extends DocumentFilter {
  String defaultValue;
  
  public IntDocumentFilter(String defaultValue) {
    this.defaultValue = defaultValue;
    super();
  }
  
  // Replacing text (marking text then typing/pasting)
  @Override
  public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
    Document doc = fb.getDocument();

    text = text.replaceAll("[^0-9]", "");

    super.replace(fb, offset, length, text, attrs);

    String string = doc.getText(0, doc.getLength());

    if (string.equals(""))
      return;

    while(string.length() > 1 && string.charAt(0) == '0') {
      string = string.substring(1, string.length());
    }

    super.replace(fb, 0, doc.getLength(), string, attrs);
  }
  
  // Removing text
  @Override
  public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
    Document doc = fb.getDocument();

    if (doc.getText(0, doc.getLength()-length).equals("")) {
      super.replace(fb, 0, doc.getLength(), defaultValue, null);
      return;
    }
    
    super.remove(fb, offset, length);
  }
}
