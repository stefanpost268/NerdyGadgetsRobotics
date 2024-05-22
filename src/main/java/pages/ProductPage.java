package pages;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;
import models.StockItem;
import repositories.StockItemRepository;
import services.Formatter;

public class ProductPage extends JPanel implements ActionListener {

    private StockItemRepository stockItem;
    private JTextField searchField = new JTextField(20);
    private JButton searchButton = new JButton("Zoeken");
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
        if (this.searchField.getText().isEmpty()) {
            stockItems = this.stockItem.findAll();
        } else {
            stockItems = this.stockItem.findByStockItemNameContaining(this.searchField.getText());
        }

        DefaultTableModel model;
        if (stockItems.iterator().hasNext()) {
            List<Object[]> dataList = new ArrayList<>();
            for (StockItem item : stockItems) {
                Object[] row = new Object[7]; // Adjust the size to the number of columns
                row[0] = item.getStockItemID();
                row[1] = item.getStockItemName();
                row[2] = item.getUnitPrice();
                row[3] = item.getRecommendedRetailPrice();
                row[4] = item.getTypicalWeightPerUnit();
                row[5] = (item.getStockItemHolding() != null) ? item.getStockItemHolding().getQuantityOnHand() : null;
                row[6] = item.getSize();
                // Retrieve QuantityOnHand from StockItemHolding
                dataList.add(row);
            }

            Object[][] data = new Object[dataList.size()][];
            dataList.toArray(data);

            String[] columnIdentifiers = {"Product ID", "Product Naam", "Inkoop prijs", "Verkoop prijs", "Gewicht", "Voorraad", "Aantal op voorraad"};
        model = new DefaultTableModel(data, columnIdentifiers) {
                @Override
                public Object getValueAt(int row, int column) {
                    // weight at column index 4
                    if (column == 4 && super.getValueAt(row, column) != null) {
                        return super.getValueAt(row, column) + " kg";
                    }
                    return super.getValueAt(row, column);
                }
            };

            model.setColumnIdentifiers(columnIdentifiers);

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