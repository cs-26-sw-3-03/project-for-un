import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class FilterPanel extends JPanel {
    private static FilterPanel INSTANCE;

    public void OpenFilterBox() {
        if(FilterBox.GetInstance() != null)
            remove(FilterBox.GetInstance());
        add(FilterBox.MakeOrGetInstance(), BorderLayout.CENTER);
        Main.window.revalidate();
        Main.window.repaint();
    }
    
    public void ReloadFilterBox() {
        ReloadFilterBoxInvisible();
        Main.window.revalidate();
        Main.window.repaint();
    }
    
    private void ReloadFilterBoxInvisible() {
        if(FilterBox.GetInstance() != null)
            remove(FilterBox.GetInstance());
        add(FilterBox.MakeInstance(), BorderLayout.CENTER);
    }
    
    public void CloseFilterBox() {
        if(FilterBox.GetInstance() == null)
            return;
        remove(FilterBox.GetInstance());
        FilterBox.DeleteInstance();
        Main.window.revalidate();
        Main.window.repaint();
    }
    
    public void ToggleFilterBox() {
        if(FilterBox.GetInstance() == null)
            ReloadFilterBox();
        else {
            CloseFilterBox();
        }
    }
    
    
    public static FilterPanel MakeInstance() {
        return INSTANCE = new FilterPanel();
    }
    
    public static void DeleteInstance() {
        INSTANCE = null;
    }
    
    public static FilterPanel GetInstance() {
        return INSTANCE;
    }
    
    public static FilterPanel MakeOrGetInstance() {
        if(INSTANCE == null)
            INSTANCE = new FilterPanel();
        return INSTANCE;
    }
    
    private FilterPanel() {
        super(new BorderLayout());
        
        FilterManager filterManager = FilterManager.GetInstance();
        int presetIndex = filterManager.GetCurrentIndex();
    
        ReloadFilterBoxInvisible();
    
                //Tab button things at the top//

        WrapLayout tabWrap = new WrapLayout();
        tabWrap.setAlignment(FlowLayout.LEADING);
        JPanel tabPanel = new JPanel(tabWrap);
        tabPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,java.awt.Color.GRAY));

        for(int i = 0; i < filterManager.PresetCount(); i++) {
            JButton tabButton = new JButton(String.valueOf(i));
            if(i == presetIndex)
                tabButton.setBackground(Color.RED);
            
            final int fi = i;
            tabButton.addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent e) {
                    filterManager.SwitchPreset(fi);
                    //Repaint the filter panel
                    HomePage.GetInstance().ReloadFilterPanel();
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
                filterManager.AddPreset();
                //Repaint the filter panel
                HomePage.GetInstance().ReloadFilterPanel();
            }
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
        });
        tabPanel.add(addButton);
        add(tabPanel, BorderLayout.NORTH);
        
                //Filter toggle
        
        JPanel southPanel = new JPanel(new BorderLayout());
        JButton filterBoxToggle = new JButton(FilterBox.GetInstance() == null ? "v" : "^");
        filterBoxToggle.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                ToggleFilterBox();
                filterBoxToggle.setText(FilterBox.GetInstance() == null ? "v" : "^");
            }
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        });
        southPanel.add(filterBoxToggle, BorderLayout.WEST);
        add(southPanel, BorderLayout.SOUTH);
    }
}
