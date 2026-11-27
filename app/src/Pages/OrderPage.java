import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public abstract class OrderPage extends JPanel {

    private final Map<String, Object[][]> orderLines = new HashMap<>(Map.of(
        "1", new Object[][] {{"Hviskestykke", 2}, {"håndklæde", 3}},
        "2", new Object[][] {{"Brødposer", 3}, {"Klassisk forklæde", 1}}
    ));

    private JTable newOrderTable;
    private DefaultTableModel newOrderModel;
    private DefaultTableModel oldModel;
    private JTable oldOrderTable;
    private JButton sendReturnButton = new JButton("Send returnering");
    private String draftNumber;

    private JPanel header;
    private JPanel topPanel;


    protected OrderPage(String[] newOrderColumns, String[] oldOrderColumns) {
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new BorderLayout());
        header = new JPanel(new WrapLayout(FlowLayout.LEFT));


        JLabel orderNumber = new JLabel("99999");
        orderNumber.setFont(orderNumber.getFont().deriveFont(Font.PLAIN));
        header.add(HeaderTextFieldPanel("Ordrenummer: ", orderNumber, new Dimension(50, 20)));

        JLabel orderDate = new JLabel("YYYY-MM-DD");
        orderDate.setFont(orderDate.getFont().deriveFont(Font.PLAIN));
        header.add(HeaderTextFieldPanel("Ordredato: ", orderDate, new Dimension(100,20)));

        JLabel deliveryDate = new JLabel("YYYY-MM-DD");
        deliveryDate.setFont(deliveryDate.getFont().deriveFont(Font.PLAIN));
        header.add(HeaderTextFieldPanel("Leveringsdato: ", deliveryDate, new Dimension(100, 20)));

        /*
        Hard coded indtil videre
        Bestemmelse for antal rækker, skal bestemmes af antal transact_num
        */
        int rowCount = 10;
        newOrderModel = new DefaultTableModel(newOrderColumns, rowCount);
        newOrderTable = new JTable(newOrderModel);

        JPanel removeColumn = new JPanel();
        removeColumn.setLayout(new BoxLayout(removeColumn, BoxLayout.Y_AXIS));
        for (int i = 0; i < rowCount; i++) {
            JButton removeButton = new JButton("X");
            removeButton.setForeground(Color.RED);
            removeButton.setMargin(new Insets(0, 0, 0, 0));
            Dimension size = new Dimension(30, newOrderTable.getRowHeight());
            removeButton.setPreferredSize(size);
            removeButton.setMaximumSize(size);
            removeColumn.add(removeButton);
        }

        JButton addButton = new JButton("+");
        addButton.setForeground(Color.GREEN);
        addButton.setFont(addButton.getFont().deriveFont(Font.BOLD, 18f));
        addButton.setMargin(new Insets(0, 0, 0, 0));
        Dimension addSize = new Dimension(30, newOrderTable.getRowHeight());
        addButton.setPreferredSize(addSize);
        addButton.setMaximumSize(addSize);
        removeColumn.add(addButton);

        JScrollPane lineScroll = new JScrollPane(newOrderTable);
        lineScroll.setRowHeaderView(removeColumn);
    
        
        topPanel = new JPanel(new BorderLayout());
        topPanel.add(lineScroll, BorderLayout.CENTER);

        JPanel topButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topButtons.add(new JButton("Annuller"));
        topButtons.add(new JButton("Lav ordre"));
        topButtons.add(sendReturnButton);
        sendReturnButton.setVisible(false);
        sendReturnButton.addActionListener(e -> SendReturn());
        

        top.add(header, BorderLayout.NORTH);
        top.add(topPanel, BorderLayout.CENTER);
        top.add(topButtons, BorderLayout.SOUTH);

        /*
        Hard coded indtil videre
        Bestemmelse for antal rækker, skal bestemmes af antal lavet ordre
        
        JTable oldOrderTable = new JTable(new Object[rows][oldOrderColumns.length], oldOrderColumns);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(oldOrderTable), BorderLayout.CENTER);
        */

        oldModel = new DefaultTableModel(oldOrderColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        oldModel.addRow(new Object[] {"F", "1", "2026-10-01", "2026-10-05"});
        oldModel.addRow(new Object[] {"F", "2", "2026-10-09", "2026-10-14"});
        oldOrderTable = new JTable(oldModel);


        oldOrderTable.addMouseListener(new MouseAdapter() {
            @Override 
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) openDraft();
            }
        });

        JButton returnButton = new JButton("Returner vare");
        returnButton.addActionListener(e -> openDraft());

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(oldOrderTable), BorderLayout.CENTER);
        add(returnButton, BorderLayout.SOUTH);
    }


    private void openDraft() {
        int row = oldOrderTable.getSelectedRow();
        if (row == -1) return;
    
        draftNumber = (String) oldModel.getValueAt(row, 1);
        Object[][] lines = orderLines.get(draftNumber);
        if (lines == null) return;
        
        DefaultTableModel draft = new DefaultTableModel(
            new String[] {"Produkt navn", "Bestilt", "Returner"}, 0
        );

        for (Object[] line : lines) {
            draft.addRow(new Object[] {line[0], line[1], 0});
        }

        newOrderTable.setModel(draft);
        sendReturnButton.setVisible(true);
    }

    private JPanel HeaderTextFieldPanel(String attributeText, JLabel valueField, Dimension valueFieldSize) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(new JLabel(attributeText));
        JPanel valuePanel = new JPanel(new BorderLayout());
        valuePanel.setPreferredSize(valueFieldSize);
        valuePanel.setBackground(new Color(211, 211, 211));
        //valueField.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.GRAY));
        valuePanel.add(Box.createHorizontalStrut(5), BorderLayout.WEST);
        valuePanel.add(valueField, BorderLayout.CENTER);
        panel.add(valuePanel);
        return panel;
    }

    private void SendReturn() {
        oldModel.addRow(new Object[] {"O", draftNumber + "*", LocalDate.now(), ""});
        newOrderTable.setModel(newOrderModel);
        sendReturnButton.setVisible(false);
    }
}