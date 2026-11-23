import javax.swing.JLabel;
import javax.swing.JTextField;

public class LeqFilterPanel extends FilterInputPanel {
    private JTextField field;

    public LeqFilterPanel(int preferedWidth, int preferedHeight, Type type) {
        this(preferedWidth, preferedHeight, "", type);
    }

    public LeqFilterPanel(int preferedWidth, int preferedHeight, String content, Type type) {
        super(preferedWidth, preferedHeight, type);
        JLabel leq = new JLabel(" ≤ ", JLabel.CENTER);
        leq.setPreferredSize(symbolSize);
        add(leq);
        field = addField(inputSize, type);
        field.setText(content);
    }
    
    public LeqFilter ToFilter() {
        if(ReadTextBoxValue(field) == null)
            return new LeqFilter();
        return new LeqFilter(ReadTextBoxValue(field));
    }
}
