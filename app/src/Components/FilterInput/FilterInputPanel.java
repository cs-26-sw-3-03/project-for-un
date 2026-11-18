import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class FilterInputPanel extends JPanel {
    public static final int INPUT_WIDTH = 100;
    public static final int INPUT_HEIGHT = 25;
    public static final int DASH_WIDTH = 20;
    public static final Dimension INPUT_SIZE = new Dimension(INPUT_WIDTH, INPUT_HEIGHT);

    public FilterInputPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setPreferredSize(new Dimension(INPUT_WIDTH, INPUT_HEIGHT));
        // setBorder(BorderFactory.createMatteBorder(1,1,1,1,java.awt.Color.GRAY));
    }

    public JTextField addTextField(Dimension size) {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, INPUT_HEIGHT));
        /*
        field.setPreferredSize(size);
        field.setMaximumSize(size);
        */
        add(field);
        return field;
    }
}