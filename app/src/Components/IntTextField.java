import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

public class IntTextField extends JTextField{
  public IntTextField(String defaultValue) {
    ((AbstractDocument)this.getDocument()).setDocumentFilter(new IntDocumentFilter(defaultValue));
  }
}
