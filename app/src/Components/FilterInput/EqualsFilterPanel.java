import javax.swing.JLabel;
import javax.swing.JTextField;

public class EqualsFilterPanel extends FilterInputPanel {
    private JTextField field;

    public EqualsFilterPanel(int preferedWidth, int preferedHeight, Type type) {
        this(preferedWidth, preferedHeight, "", type);
    }
    
    public EqualsFilterPanel(int preferedWidth, int preferedHeight, String content, Type type) {
        super(preferedWidth, preferedHeight, type);
        JLabel eq = new JLabel(" = ", JLabel.CENTER);
        eq.setPreferredSize(symbolSize);
        add(eq);
        field = addField(inputSize, type);
        field.setText(content);
    }
    
    public EqualsFilter ToFilter() {
        if(ReadTextBoxValue(field) == null)
            return new EqualsFilter();
        return new EqualsFilter(ReadTextBoxValue(field));
    }
}
