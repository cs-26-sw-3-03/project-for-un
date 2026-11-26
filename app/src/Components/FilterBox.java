import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;

public class FilterBox extends JPanel {
    private static FilterBox INSTANCE;
    
    private HashMap<Attribute, FilterInputPanel> inputs;
    private JPanel filtersPanel;
    
    public static FilterBox MakeInstance() {
        return INSTANCE = new FilterBox();
    }
    
    public static void DeleteInstance() {
        INSTANCE = null;
    }
    
    public static FilterBox GetInstance() {
        return INSTANCE;
    }
    
    public static FilterBox MakeOrGetInstance() {
        if(INSTANCE == null)
            INSTANCE = new FilterBox();
        return INSTANCE;
    }
    
    public QueryConfig ToQueryConfig() {
        QueryConfig config = new QueryConfig(0, 100);
        for(Attribute attribute : Attribute.values()) {
            config.SetFilter(attribute, inputs.get(attribute).ToFilter());
        }
        return config;
    }
    
    private FilterInputPanel MakeInputPanel(Filter filter, Type type) {
        FilterToFilterInputVisitor visitor = new FilterToFilterInputVisitor(100, 25, type);
        try {
            return filter.Accept(visitor);
        }
        catch (ImpossibleException e) {
            return null;
        }
    }
    
    public void ReplaceFilterInput(Attribute attribute, FilterInputPanel input) {
        FilterInputPanel oldInput = inputs.get(attribute);
        inputs.put(attribute, input);
        //The panel responsible for showing name and input panel and such of one attribute
        JPanel attributePanel = (JPanel) filtersPanel.getComponent(attribute.ordinal());
        attributePanel.remove(oldInput);
        attributePanel.add(input, BorderLayout.EAST);
    }
    
    
    private FilterBox() {
        super(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(1,1,1,1,java.awt.Color.GRAY));
        
        QueryConfig currentPreset = FilterManager.GetInstance().GetCurrentPreset();
        inputs = new HashMap<Attribute, FilterInputPanel>();
    
            //Save and delete button

        JPanel southPanel = new JPanel(new BorderLayout());
        JPanel filterButtonsPanel = new JPanel(new BorderLayout());

        JButton saveButton = new JButton("Gem");
        saveButton.addMouseListener(new ButtonListener(saveButton, "godkend", (e) -> {
            FilterManager.GetInstance().SetPreset(FilterBox.GetInstance().ToQueryConfig());
            FilterManager.GetInstance().SavePreset();
        }));
        
        JButton deleteButton = new JButton("Slet");
        deleteButton.addMouseListener(new ButtonListener(deleteButton, (e) -> {
            FilterManager.GetInstance().RemoveCurrentPreset();
            HomePage.GetInstance().ReloadFilterPanel();
        }));

        filterButtonsPanel.add(saveButton, BorderLayout.EAST);
        filterButtonsPanel.add(deleteButton, BorderLayout.WEST);
        southPanel.add(filterButtonsPanel, BorderLayout.EAST);
        add(southPanel, BorderLayout.SOUTH);
        
        
                    //filters//
        WrapLayout gridWrap = new WrapLayout();
        gridWrap.setAlignment(FlowLayout.LEADING);
        filtersPanel = new JPanel(gridWrap);

        for (Attribute attribute : Attribute.values()) {
            //The panel responsible for showing name and input panel and such of one attribute
            JPanel attributePanel = new JPanel(new BorderLayout());
            
            JLabel nameLabel = new JLabel(AttributeDescriber.DkNameOf(attribute), SwingConstants.RIGHT);
            nameLabel.setPreferredSize(new Dimension(97, 25));
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
                    //Stuff
                }
            });
            attributePanel.add(nameLabel, BorderLayout.WEST);
            
            
            FilterInputPanel inputPanel = MakeInputPanel(currentPreset.GetFilter(attribute), AttributeDescriber.TypeOf(attribute));
            attributePanel.add(inputPanel, BorderLayout.EAST);
            inputs.put(attribute, inputPanel);
            
            
            JButton cycleButton = new JButton("⇆");
            cycleButton.setPreferredSize(new Dimension(45, 25));
            cycleButton.addMouseListener(new ButtonListener(cycleButton, (e) -> {
                Filter newFilter = FilterManager.GetInstance().CycleFilter(attribute);
                FilterBox.GetInstance().ReplaceFilterInput(attribute, MakeInputPanel(newFilter, AttributeDescriber.TypeOf(attribute)));
                Main.window.revalidate();
                Main.window.repaint();
            }));
            attributePanel.add(cycleButton, BorderLayout.CENTER);
            
            filtersPanel.add(attributePanel);
        }
        add(filtersPanel, BorderLayout.CENTER);
    }
}
