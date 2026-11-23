import javax.swing.JCheckBox;

public class ExistsFilterPanel extends FilterInputPanel {
    private JCheckBox box;

    public ExistsFilterPanel(int preferedWidth, int preferedHeight) {
        this(preferedWidth, preferedHeight, false);
    }
    
    public ExistsFilterPanel(int preferedWidth, int preferedHeight, boolean state) {
        super(preferedWidth, preferedHeight, Type.BOOL);
        box = new JCheckBox("", state);
        add(box);
    }
    
    public ExistsFilter ToFilter() {
        return new ExistsFilter(box.isSelected());
    }
}
