package pages;

import dialogs.ChangeStock;
import models.StockItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductPage extends JPanel implements ActionListener {

    private JTextField searchField = new JTextField(20);
    private JButton searchButton = new JButton("Zoeken");
    private JTable table;
    private JButton action = new JButton("Bewerk");
    private StockItem stockItem = new StockItem();

    public ProductPage() {
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(this.searchField);
        searchPanel.add(this.searchButton);
        searchPanel.add(Box.createHorizontalStrut(936)); //set margin so button is right side of screen
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
        List<StockItem> stockItems;
        if (this.searchField.getText().isEmpty()) {
            stockItems = this.stockItem.get();
        } else {
            stockItems = this.stockItem.like("StockItemName", this.searchField.getText()).get();
        }

        DefaultTableModel model = new DefaultTableModel(
                this.stockItem.toTableData(stockItems),
                this.stockItem.fillable()
        );

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