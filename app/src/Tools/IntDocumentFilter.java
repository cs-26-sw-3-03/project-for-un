import javax.swing.*;
import javax.swing.text.*;

class IntDocumentFilter extends DocumentFilter {
  
  // Replacing text (marking text then typing/pasting)
  @Override
  public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
    Document doc = fb.getDocument();
    StringBuilder sb = new StringBuilder();
    sb.append(doc.getText(0, doc.getLength()));
    sb.replace(offset, offset + length, text);

    text = text.replaceAll("[^0-9]", "");

    super.replace(fb, offset, length, text, attrs);

    String string = doc.getText(0, doc.getLength());

    if (string.equals(""))
      return;

    while(string.charAt(0) == '0' && string.length() > 1) {
      string = string.substring(1, string.length());
    }

    super.replace(fb, 0, doc.getLength(), string, attrs);
  }
  
  // Removing text
  @Override
  public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
    Document doc = fb.getDocument();
    StringBuilder sb = new StringBuilder();
    sb.append(doc.getText(0, doc.getLength()));
    sb.delete(offset, offset + length);

    if (doc.getText(0, doc.getLength()).equals("0") || (doc.getText(0, doc.getLength()-length)).equals("")) {
      super.replace(fb, 0, 1, "0", null);
      return;
    }
    
    super.remove(fb, offset, length);
  }
}
