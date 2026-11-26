import javax.swing.JTextField;

public class ContainsFilterPanel extends FilterInputPanel {
    private JTextField field;

    public ContainsFilterPanel(int preferedWidth, int preferedHeight) {
        this(preferedWidth, preferedHeight, "");
    }

    public ContainsFilterPanel(int preferedWidth, int preferedHeight, String content) {
        super(preferedWidth, preferedHeight, Type.STRING);
        field = addField(inputSize, Type.STRING);
        field.setText(content);
    }
    
    public ContainsFilter ToFilter() {
        if(ReadTextBoxValue(field) == null) {
            return new ContainsFilter();
        }
        return new ContainsFilter((String) ReadTextBoxValue(field));
    }
}
