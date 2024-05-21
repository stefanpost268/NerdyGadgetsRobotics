package visualComponents;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class QueueBox extends JPanel implements ActionListener {
    private DefaultTableModel queueTableModel;
    JButton executeButton = new JButton("Verwerken");
    JTable queueTable;

    public QueueBox(List<Object[]> queueData) {
        setBackground(Color.LIGHT_GRAY);
        setLayout(new FlowLayout(FlowLayout.LEFT)); // align left
        JLabel queueLabel = new JLabel("Bestelling wachtrij");
        executeButton.addActionListener(this);
        add(queueLabel);

        //table model
        queueTableModel = new DefaultTableModel(
                new Object[]{"Bestel Nr", "Product aantal", "Status"}, 0
        );

        //Create table
        queueTable = new JTable(queueTableModel);
        queueTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        queueTable.setDefaultEditor(Object.class, null); // read-only
        JScrollPane queueScrollPane = new JScrollPane(queueTable); // Add scroll to table
        queueScrollPane.setPreferredSize(new Dimension(320, 270));

        queueTable.getTableHeader().setReorderingAllowed(false); // Stop user column swipe
        add(queueScrollPane);




        fillQueueTable(queueData);
        //adding button to the bottom of the table
        add(executeButton);
    }

    private void fillQueueTable(List<Object[]> data) {
        for (Object[] row : data) {
            queueTableModel.addRow(row);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == executeButton) {
            int orderNumber = (int) queueTable.getValueAt(queueTable.getSelectedRow(), 0);
        }
    }
}
