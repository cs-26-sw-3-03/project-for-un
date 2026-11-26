import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

public class FloatTextField extends JTextField{
  public FloatTextField(String defaultValue) {
    ((AbstractDocument)this.getDocument()).setDocumentFilter(new FloatDocumentFilter(defaultValue));
  }
  
  @Override
  public String getText() {
    String text = super.getText();
    return text.replace(",", ".");
  }
}
