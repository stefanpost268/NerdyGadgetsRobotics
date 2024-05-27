package visualComponents;

import models.Order;
import models.OrderLines;
import models.StockItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProcessingBox extends JPanel {
    private DefaultTableModel processingTableModel;

    public ProcessingBox(Order order) {
        setBackground(Color.LIGHT_GRAY);
        setLayout(new FlowLayout(FlowLayout.LEFT)); // align left

        JLabel processingLabel = new JLabel("Producten processing in Bestelling");
        add(processingLabel);

        processingTableModel = new DefaultTableModel(
                new Object[]{"Product ID", "Product naam", "Aantal", "Locatie"}, 0
        );

        JTable processingTable = new JTable(processingTableModel);
        processingTable.setDefaultEditor(Object.class, null); // read-only
        processingTable.getTableHeader().setReorderingAllowed(false); // Stop user column swipe

        JScrollPane processingScrollPane = new JScrollPane(processingTable); // Add scroll to table
        processingScrollPane.setPreferredSize(new Dimension(320, 270));
        add(processingScrollPane);

        if(order != null) {
            fillProcessingTable(order.getOrderLines());
        }
    }

    private void fillProcessingTable(List<OrderLines> data) {
        for (OrderLines row : data) {
            StockItem stockItem = row.getStockItem();
            Object[] rowData = new Object[] {
                    stockItem.getStockItemID(),
                    stockItem.getStockItemName(),
                    row.getQuantity(),
                    "NOT IMPLEMENTED"
            };

            processingTableModel.addRow(rowData);
        }
    }
}
