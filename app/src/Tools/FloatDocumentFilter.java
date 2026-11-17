import javax.swing.*;
import javax.swing.text.*;

class FloatDocumentFilter extends DocumentFilter {
  //Inserting text (typing, pasting)
  @Override
  public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
    Document doc = fb.getDocument();
    StringBuilder sb = new StringBuilder();
    sb.append(doc.getText(0, doc.getLength()));
    sb.insert(offset, string);

    // Following replace is from chatGPT and replaces all characters with nothing but 0-9 and commas.
    // Furthermore it formats to only 1 comma with 2 trailing numbers: 
    string = string.replaceAll("[^0-9,]", "");

    super.insertString(fb, offset, string, attr);
  }
  
  // Replacing text (marking text then typing/pasting)
  @Override
  public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
    Document doc = fb.getDocument();
    StringBuilder sb = new StringBuilder();
    sb.append(doc.getText(0, doc.getLength()));
    sb.replace(offset, offset + length, text);


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
