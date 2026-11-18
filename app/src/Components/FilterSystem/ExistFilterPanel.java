import javax.swing.JCheckBox;

public class ExistFilterPanel extends FilterInputPanel {
    public ExistFilterPanel() {
        //super(new BoxLayout(this, BoxLayout.X_AXIS));
        //add(new JLabel("Exist"));
        add(new JCheckBox());
    }
}