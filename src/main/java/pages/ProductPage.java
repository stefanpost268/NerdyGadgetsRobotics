package pages;

import dialogs.ChangeStock;
import models.StockItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import repositories.StockItemRepository;
import services.Formatter;

public class ProductPage extends JPanel implements ActionListener {

    private StockItemRepository stockItem;
    private JTextField searchField = new JTextField(20);
    private JButton searchButton = new JButton("Zoeken");
    private JTable table;
    private JButton action = new JButton("Bewerk");


    public ProductPage(StockItemRepository stockItem) {
        this.stockItem = stockItem;
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(this.searchField);
        searchPanel.add(this.searchButton);
        searchPanel.add(Box.createHorizontalStrut(836)); //set margin so button is right side of screen
        searchPanel.add(this.action);
        add(searchPanel, BorderLayout.PAGE_START);
        this.action.addActionListener(this);
        this.searchButton.addActionListener(this);
        this.searchField.addActionListener(this);
        renderTable();
    }

    private void renderTable() {
        search();
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(this.table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.table.setFillsViewportHeight(true);
        this.table.setDefaultEditor(Object.class, null);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void search() {

        Iterable<StockItem> stockItems;
        if(this.searchField.getText().isEmpty()) {
            stockItems = this.stockItem.findAll();

        } else {
            stockItems = this.stockItem.findByStockItemNameContaining(this.searchField.getText());
        }

        DefaultTableModel model;
        if (stockItems.iterator().hasNext()) {
            model = new DefaultTableModel(
                    Formatter.modelListToGenericObject(stockItems),
                    stockItems.iterator().next().getFieldNames().toArray()
            );
        } else {
            model = new DefaultTableModel();
        }

        if (this.table == null) {
            this.table = new JTable(model);
        } else {
            this.table.setModel(model);
        }

        this.table.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.searchButton) || e.getSource().equals(this.searchField)) {
            search();
        }
        if (e.getSource().equals(this.action)) {
            int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
            Object id = table.getValueAt(selectedRow, 0);
            int intValue = Integer.parseInt(String.valueOf(id));

            ChangeStock dialog = new ChangeStock(new JFrame(), intValue);


        }
    }
}