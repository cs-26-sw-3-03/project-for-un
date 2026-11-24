import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.colorchooser.AbstractColorChooserPanel;

/**
 * A JColorChooser panel with three sliders: Hue (0-360), Saturation (0-100), Brightness (0-100).
 * Built by hand (instead of reusing the built-in HSB tab) so it works the same on every
 * Java version and Locale.
 */
public class HsbChooserPanel extends AbstractColorChooserPanel {
  private final JSlider hue = new JSlider(0, 360, 0);
  private final JSlider sat = new JSlider(0, 100, 0);
  private final JSlider bri = new JSlider(0, 100, 0);
  private final JLabel hueValue = new JLabel();
  private final JLabel satValue = new JLabel();
  private final JLabel briValue = new JLabel();

  // True while the sliders themselves are changing the model, so updateChooser() doesn't
  // overwrite them (RGB -> HSB round-trips lose the hue when saturation or brightness is 0).
  private boolean fromSlider = false;

  @Override
  protected void buildChooser() {
    setLayout(new GridBagLayout());
    addRow(0, "Hue (H)", hue, hueValue);
    addRow(1, "Saturation (S)", sat, satValue);
    addRow(2, "Brightness (B)", bri, briValue);

    javax.swing.event.ChangeListener l = e -> onSlide();
    hue.addChangeListener(l);
    sat.addChangeListener(l);
    bri.addChangeListener(l);
  }

  private void addRow(int row, String name, JSlider slider, JLabel value) {
    GridBagConstraints c = new GridBagConstraints();
    c.gridy = row;
    c.insets = new Insets(4, 6, 4, 6);

    c.gridx = 0;
    c.anchor = GridBagConstraints.WEST;
    add(new JLabel(name), c);

    c.gridx = 1;
    c.weightx = 1;
    c.fill = GridBagConstraints.HORIZONTAL;
    add(slider, c);

    c.gridx = 2;
    c.weightx = 0;
    c.fill = GridBagConstraints.NONE;
    value.setPreferredSize(new Dimension(36, value.getPreferredSize().height));
    add(value, c);
  }

  private void onSlide() {
    updateLabels();
    Color c = Color.getHSBColor(hue.getValue() / 360f, sat.getValue() / 100f, bri.getValue() / 100f);
    fromSlider = true;
    try {
      getColorSelectionModel().setSelectedColor(c);
    } finally {
      fromSlider = false;
    }
  }

  private void updateLabels() {
    hueValue.setText(hue.getValue() + "\u00B0");
    satValue.setText(sat.getValue() + "%");
    briValue.setText(bri.getValue() + "%");
  }

  @Override
  public void updateChooser() {
    if (fromSlider) return;
    Color c = getColorFromModel();
    float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
    hue.setValue(Math.round(hsb[0] * 360));
    sat.setValue(Math.round(hsb[1] * 100));
    bri.setValue(Math.round(hsb[2] * 100));
    updateLabels();
  }

  @Override
  public String getDisplayName() {
    return "HSB";
  }

  @Override
  public Icon getSmallDisplayIcon() {
    return null;
  }

  @Override
  public Icon getLargeDisplayIcon() {
    return null;
  }
}
