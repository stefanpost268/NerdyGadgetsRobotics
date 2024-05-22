package visualComponents;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class QueueBox extends JPanel {
    private DefaultTableModel queueTableModel;

    public QueueBox(List<Object[]> queueData) {
        setBackground(Color.LIGHT_GRAY);
        setLayout(new FlowLayout(FlowLayout.LEFT)); // align left
        JLabel queueLabel = new JLabel("Bestelling wachtrij");
        add(queueLabel);

        //table model
        queueTableModel = new DefaultTableModel(
                new Object[]{"Bestel Nr", "Product aantal", "Status", "Actie"}, 0
        );

        //Create table
        JTable queueTable = new JTable(queueTableModel);
        queueTable.setDefaultEditor(Object.class, null); // read-only
        JScrollPane queueScrollPane = new JScrollPane(queueTable); // Add scroll to table
        queueScrollPane.setPreferredSize(new Dimension(320, 310));

        queueTable.getTableHeader().setReorderingAllowed(false); // Stop user column swipe
        add(queueScrollPane);

        fillQueueTable(queueData);
    }

    private void fillQueueTable(List<Object[]> data) {
        for (Object[] row : data) {
            queueTableModel.addRow(row);
        }
    }
}
