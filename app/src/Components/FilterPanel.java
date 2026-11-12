import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class FilterPanel extends JPanel {
    static int FILTER_AMOUNT = 20;

    public FilterPanel() {
            //----- Filter part -----// 
        
                //initialisation//
                    //panels//

        JPanel dropDownPanel = new JPanel(new BorderLayout());

        JPanel presetPanel = new JPanel(new BorderLayout());

        JPanel filterPanel = new JPanel(new BorderLayout());

        JPanel filterButtonsPanel = new JPanel(new BorderLayout());
        
        JPanel filterGridPanel = new JPanel(new BorderLayout());

                //initialisation//
                    //meshing//

        add(dropDownPanel);

        dropDownPanel.add(presetPanel, BorderLayout.NORTH);

        dropDownPanel.add(filterPanel, BorderLayout.CENTER);

        filterPanel.add(filterGridPanel, BorderLayout.CENTER);

        filterPanel.add(filterButtonsPanel, BorderLayout.EAST);

        filterGridPanel.setLayout(new GridLayout(FILTER_AMOUNT/5,5,5,5));

                //initialisation//
                    //buttons//

        presetPanel.add(new JButton("preset"), BorderLayout.WEST);

        filterButtonsPanel.add(new JButton("save filter"),BorderLayout.NORTH);
        filterButtonsPanel.add(new JButton("reset filter"),BorderLayout.SOUTH);
        
                    //filters//

        JLabel[] filterLabels = new JLabel[FILTER_AMOUNT];
        TextField[] filterParams = new TextField[FILTER_AMOUNT];

        for (int i = 0; i < FILTER_AMOUNT; i++){
            int number = i;

            filterLabels[number] = new JLabel("filter_"+number);
            filterParams[number] = new TextField("parameter_"+number, 15);

            filterLabels[number].setPreferredSize(new Dimension(40,25));
            filterParams[number].setColumns(15);

            filterLabels[number].addMouseListener(new MouseListener()  {
                public void mouseEntered(MouseEvent e) {
                    filterLabels[number].setOpaque(true);
                    filterLabels[number].setBackground(Color.PINK);
                }
                public void mouseExited(MouseEvent e) {
                    filterLabels[number].setOpaque(false);
                    filterLabels[number].setBackground(Color.WHITE);
                }
                public void mousePressed(MouseEvent e) {
                    filterLabels[number].setBackground(Color.RED);
                }
                public void mouseReleased(MouseEvent e) {
                    filterLabels[number].setBackground(Color.WHITE);
                }
                public void mouseClicked(MouseEvent e) {
                    //placeholder
                    System.out.println(number);
                }
            });

            filterGridPanel.add(filterLabels[number], BorderLayout.CENTER);
            filterGridPanel.add(filterParams[number], BorderLayout.EAST);
        }
    }
}