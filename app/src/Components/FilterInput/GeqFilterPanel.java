import javax.swing.JLabel;
import javax.swing.JTextField;

public class GeqFilterPanel extends FilterInputPanel {
    private JTextField field;
    
    public GeqFilterPanel(int preferedWidth, int preferedHeight, Type type) {
        this(preferedWidth, preferedHeight, "", type);
    }
    
    public GeqFilterPanel(int preferedWidth, int preferedHeight, String content, Type type) {
        super(preferedWidth, preferedHeight, type);
        JLabel geq = new JLabel(" ≥ ", JLabel.CENTER);
        geq.setPreferredSize(symbolSize);
        add(geq);
        field = addField(inputSize, type);
        field.setText(content);
    }
    
    public GeqFilter ToFilter() {
        if(ReadTextBoxValue(field) == null)
            return new GeqFilter();
        return new GeqFilter(ReadTextBoxValue(field));
    }
}
