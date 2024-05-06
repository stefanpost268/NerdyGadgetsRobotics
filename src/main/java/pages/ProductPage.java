package pages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import models.StockItem;

public class ProductPage extends JPanel implements ActionListener {

    private JTextField searchField = new JTextField(20);
    private JButton searchButton = new JButton("Zoeken");
    private JTable table;
    private StockItem stockItem = new StockItem();

    public ProductPage() {
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
        List<StockItem> stockItems;
        if(this.searchField.getText().isEmpty()) {
            stockItems = this.stockItem.get();
        } else {
            stockItems = this.stockItem.like("StockItemName", this.searchField.getText()).get();
        }

        DefaultTableModel model = new DefaultTableModel(
                this.stockItem.toTableData(stockItems),
                this.stockItem.fillable()
        );

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