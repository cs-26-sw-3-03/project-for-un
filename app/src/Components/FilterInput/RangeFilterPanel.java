import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class RangeFilterPanel extends FilterInputPanel {
    private Dimension boxSize;
    private JTextField min;
    private JTextField max;


    public RangeFilterPanel(int preferedWidth, int preferedHeight, Type type) {
        this(preferedWidth, preferedHeight, "", "", type);
    }
    
    public RangeFilterPanel(int preferedWidth, int preferedHeight, String minStr, String maxStr, Type type) {
        super(preferedWidth, preferedHeight, type);
        boxSize = new Dimension((width - SYMBOL_WIDTH) / 2, height);
        min = addField(boxSize, type);
        min.setText(minStr);
        JLabel dash = new JLabel(" - ", JLabel.CENTER);
        dash.setPreferredSize(symbolSize);
        add(dash);
        max = addField(boxSize, type);
        max.setText(maxStr);
    }
    
    public RangeFilter ToFilter() {
        if(ReadTextBoxValue(min) == null || ReadTextBoxValue(max) == null)
            return new RangeFilter();
        return new RangeFilter(ReadTextBoxValue(min), ReadTextBoxValue(max));
    }
}
