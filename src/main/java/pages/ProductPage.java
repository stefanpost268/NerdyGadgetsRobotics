package pages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import models.StockItem;
import repositories.StockItemRepository;

public class ProductPage extends JPanel implements ActionListener {

    private StockItemRepository stockItem;
    private JTextField searchField = new JTextField(20);
    private JButton searchButton = new JButton("Search");
    private JTable table;

    public ProductPage(StockItemRepository stockItem) {
        this.stockItem = stockItem;
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(this.searchField);
        searchPanel.add(this.searchButton);
        add(searchPanel, BorderLayout.PAGE_START);
        this.searchButton.addActionListener(this);
        this.searchField.addActionListener(this);

        renderTable();
    }

    private void renderTable() {
        search();

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

        Object[][] data = new Object[(int) stockItems.spliterator().estimateSize()][];
        int i = 0;
        for(StockItem stockItem : stockItems) {
            data[i] = stockItem.getFieldValues().toArray();
            i++;
        }

        DefaultTableModel model;
        if (stockItems.iterator().hasNext()) {
            model = new DefaultTableModel(
                    data,
                    stockItems.iterator().next().getFieldNames().toArray()
            );
        } else {
            model = new DefaultTableModel();
        }

        if(this.table == null) {
            this.table = new JTable(model);
        } else {
            this.table.setModel(model);
        }

        this.table.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(this.searchButton) || e.getSource().equals(this.searchField)) {
            search();
        }
    }
}