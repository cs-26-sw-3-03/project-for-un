import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import java.math.BigDecimal;

public abstract class FilterInputPanel extends JPanel {
    protected Type type;

    protected int width;
    protected int height;
    protected static final int SYMBOL_WIDTH = 20;
    protected Dimension symbolSize;
    protected Dimension inputSize;

    public FilterInputPanel(int preferedWidth, int preferedHeight, Type type) {

        this.type = type;
        this.width = preferedWidth > SYMBOL_WIDTH ? preferedWidth : SYMBOL_WIDTH;
        this.height = preferedHeight;
        this.symbolSize = new Dimension(SYMBOL_WIDTH, height);
        this.inputSize = new Dimension(width, height);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setPreferredSize(new Dimension(width, height));
    }

    protected JTextField addField(Dimension size, Type type) {
        JTextField field;
        
        switch(type) {
            case Type.STRING:
                field = new JTextField();
                break;
            case Type.INT:
                field = new IntTextField("");
                break;
            case Type.DECIMAL:
                field = new FloatTextField("");
                break;
            case null:
                throw new NullPointerException("Attribute type is null");
            default:
                throw new IllegalArgumentException("Attribute type doesnt match string/int/decimal");
        }
        
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
        add(field);
        return field;
    }
    
    public abstract Filter ToFilter();
    
    protected Object ReadTextBoxValue(JTextComponent box) {
        String str;
        switch(type) {
            case Type.STRING: 
              str = ((JTextComponent)box).getText();
              if (str.equals(""))
                return null;
              return str;

            case Type.INT:
              str = ((JTextComponent)box).getText();
              if (str.equals(""))
                return null;
              return Integer.parseInt(str);

            case Type.DECIMAL:
              str = ((JTextComponent)box).getText();
              if (str.equals(""))
                return null;
              return new BigDecimal(str);
          }
        return null;
    }
}
