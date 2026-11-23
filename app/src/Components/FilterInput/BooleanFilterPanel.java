import javax.swing.JCheckBox;

public class BooleanFilterPanel extends FilterInputPanel {
    private JCheckBox box;

    public BooleanFilterPanel(int preferedWidth, int preferedHeight) {
        this(preferedWidth, preferedHeight, false);
    }

    public BooleanFilterPanel(int preferedWidth, int preferedHeight, boolean state) {
        super(preferedWidth, preferedHeight, Type.BOOL);
        box = new JCheckBox("", state);
        add(box);
    }
    
    public EqualsFilter ToFilter() {
        return new EqualsFilter(box.isSelected());
    }
}
