import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

public class FloatTextField extends JTextField{
  public FloatTextField() {
    ((AbstractDocument)this.getDocument()).setDocumentFilter(new FloatDocumentFilter());
  }
}
