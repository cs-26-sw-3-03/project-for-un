import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class FilterPanel extends JPanel {
    static final int FILTER_AMOUNT = 20;
    
    public FilterPanel() {
        super(new BorderLayout());
        int presetIndex = FilterManager.GetInstance().GetCurrentIndex();
    
            //----- Filter part -----// 
        
                //initialisation//
                    //panels//

        WrapLayout tabWrap = new WrapLayout();
        tabWrap.setAlignment(FlowLayout.LEADING);
        JPanel tabPanel = new JPanel(tabWrap);
        add(tabPanel, BorderLayout.NORTH);
        
        WrapLayout gridWrap = new WrapLayout();
        gridWrap.setAlignment(FlowLayout.LEADING);
        JPanel filtersPanel = new JPanel(gridWrap);
        add(filtersPanel, BorderLayout.CENTER);
        
        JPanel filterButtonsPanel = new JPanel(new BorderLayout());
        add(filterButtonsPanel, BorderLayout.EAST);

                //initialisation//
                    //buttons//
        for(int i = 0; i < FilterManager.GetInstance().PresetCount(); i++) {
            JButton tabButton = new JButton(String.valueOf(i));
            if(i == presetIndex)
                tabButton.setBackground(Color.RED);
            
            
            final int fi = i;
            tabButton.addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent e) {
                    FilterManager.GetInstance().SwitchPreset(fi);
                    ((HomePage)getParent()).AddFilterPanel();
                }
                public void mouseEntered(MouseEvent e) {}
                public void mouseExited(MouseEvent e) {}
                public void mousePressed(MouseEvent e) {}
                public void mouseReleased(MouseEvent e) {}
            });
            
            tabPanel.add(tabButton);
            
            
        }
        JButton addButton = new JButton("+");
        addButton.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                FilterManager.GetInstance().AddPreset();
                ((HomePage)getParent()).AddFilterPanel();
            }
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
        });
        tabPanel.add(addButton);

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
