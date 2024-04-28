package pages;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import models.StockItem;

public class ProductPage extends JPanel {
    public ProductPage() {
        StockItem stockItem = new StockItem();

        JTable table = new JTable(
            stockItem.toTableData(stockItem.get()),
            stockItem.fillable()
        );

        table.setFillsViewportHeight(true);
        table.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }
}
