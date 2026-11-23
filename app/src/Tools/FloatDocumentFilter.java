import javax.swing.*;
import javax.swing.text.*;

class FloatDocumentFilter extends DocumentFilter {
  String defaultValue;
  
  public FloatDocumentFilter(String defaultValue) {
    this.defaultValue = defaultValue;
    super();
  }
  
  // Replacing text (marking text then typing/pasting)
  @Override
  public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
    Document doc = fb.getDocument();
    
    text = text.replaceAll("[.]", ",");
    text = text.replaceAll("[^0-9,]", "");

    super.replace(fb, offset, length, text, attrs);


    String string = doc.getText(0, doc.getLength());

    if (string == "")
      return;
    
    // Part of following substringing is from chatGPT which just keeps the entire text string to 1 comma with 2 trailing numbers.
    int comma = string.indexOf(',');
    if (comma != -1) {
      string = string.substring(0, comma + 1) + string.substring(comma + 1).replace(",", "");

      // Maximum two digits after comma
      if (string.length() > comma + 3) {
          string = string.substring(0, comma + 3);
      }
    }
    // Chatgpt until here

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
