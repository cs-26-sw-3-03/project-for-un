import java.awt.Dimension;

import javax.swing.JLabel;

public class RangeFilterPanel extends FilterInputPanel {
    private static final Dimension BOX_SIZE = new Dimension((INPUT_WIDTH - DASH_WIDTH) / 2, INPUT_HEIGHT);
    private static final Dimension DASH_SIZE = new Dimension(DASH_WIDTH, INPUT_HEIGHT);

    public RangeFilterPanel() {
        addTextField(BOX_SIZE);

        JLabel dash = new JLabel(" - ", JLabel.CENTER);
        dash.setPreferredSize(DASH_SIZE);
        add(dash);
        addTextField(BOX_SIZE);
    }
}
