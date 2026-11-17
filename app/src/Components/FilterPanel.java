import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class FilterPanel extends JPanel {
    static final int FILTER_AMOUNT = 20;

    public FilterPanel() {
        super(new BorderLayout());
    
            //----- Filter part -----// 
        
                //initialisation//
                    //panels//

        JPanel presetPanel = new JPanel(new BorderLayout());
        add(presetPanel, BorderLayout.NORTH);
        
        WrapLayout gridWrap = new WrapLayout();
        gridWrap.setAlignment(FlowLayout.LEADING);
        JPanel filtersPanel = new JPanel(gridWrap);
        add(filtersPanel, BorderLayout.CENTER);
        
        JPanel filterButtonsPanel = new JPanel(new BorderLayout());
        add(filterButtonsPanel, BorderLayout.EAST);

                //initialisation//
                    //buttons//

        presetPanel.add(new JButton("preset"), BorderLayout.WEST);

        filterButtonsPanel.add(new JButton("save filter"), BorderLayout.NORTH);
        filterButtonsPanel.add(new JButton("reset filter"), BorderLayout.SOUTH);
        
                    //filters//

        for (int i = 0; i < FILTER_AMOUNT; i++) {
            JPanel attributePanel = new JPanel(new BorderLayout());
            
            JLabel nameLabel = new JLabel("Filter " + i, SwingConstants.RIGHT);
            nameLabel.setPreferredSize(new Dimension(120, 25));
            attributePanel.add(nameLabel, BorderLayout.WEST);
            
            JPanel placeHolderPanel = new JPanel();
            placeHolderPanel.add(new TextField("parameter " + i, 20));
            attributePanel.add(placeHolderPanel, BorderLayout.EAST);
            

            nameLabel.addMouseListener(new MouseListener()  {
                public void mouseEntered(MouseEvent e) {
                    nameLabel.setOpaque(true);
                    nameLabel.setBackground(Color.PINK);
                }
                public void mouseExited(MouseEvent e) {
                    nameLabel.setOpaque(false);
                    nameLabel.setBackground(Color.WHITE);
                }
                public void mousePressed(MouseEvent e) {
                    nameLabel.setBackground(Color.RED);
                }
                public void mouseReleased(MouseEvent e) {
                    nameLabel.setBackground(Color.WHITE);
                }
                public void mouseClicked(MouseEvent e) {
                    System.out.println(8);
                }
            });

            filtersPanel.add(attributePanel);
        }
    }
}
