import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
public class DashboardPanel extends JPanel {
    private DefaultTableModel queueTableModel;
    private DefaultTableModel processingTableModel;
    public DashboardPanel(List<Object[]> queueData, List<Object[]> processingData) {
        setBackground(Color.WHITE);
        setLayout(null);


        // create containers
        JPanel queueContainer = new JPanel();
        queueContainer.setBackground(Color.LIGHT_GRAY);
        queueContainer.setBounds(670, 10, 320, 350);
        queueContainer.setLayout(new FlowLayout(FlowLayout.LEFT)); // align left
        add(queueContainer);

        JPanel processingContainer = new JPanel();
        processingContainer.setBackground(Color.LIGHT_GRAY);
        processingContainer.setBounds(670, 370, 320, 270);
        processingContainer.setLayout(new FlowLayout(FlowLayout.LEFT)); // align left
        add(processingContainer);



        // Container Titels
        JLabel queueLabel = new JLabel("Bestelling queue");
        queueContainer.add(queueLabel);
        JLabel processingLabel = new JLabel("Producten processing in Bestelling");
        processingContainer.add(processingLabel);


        // Tablle models
        queueTableModel = new DefaultTableModel(
                new Object[]{"Bestel Nr", "Product aantal", "Status","Actie"}, 0
        );

        processingTableModel = new DefaultTableModel(
                new Object[]{"Product ID", "Product naam", "Aantal","Locatie"}, 0
        );


        // Create tables
        JTable queueTable = new JTable(queueTableModel);
        queueTable.setDefaultEditor(Object.class, null); // read-only
        JScrollPane queueScrollPane = new JScrollPane(queueTable); // Add scroll to table
        queueScrollPane.setPreferredSize(new Dimension(300, 310));
        queueTable.getTableHeader().setReorderingAllowed(false); // Stop user column swipe
        queueContainer.add(queueScrollPane);

        JTable processingTable = new JTable(processingTableModel);
        processingTable.setDefaultEditor(Object.class, null); // read-only
        JScrollPane processingScrollPane = new JScrollPane(processingTable); // Add scroll to table
        processingScrollPane.setPreferredSize(new Dimension(300, 230));
        processingTable.getTableHeader().setReorderingAllowed(false); // Stop user column swipe
        processingContainer.add(processingScrollPane);


        // feed data in tables
        fillQueueTable(queueData);
        fillProcessingTable(processingData);

    }
    private void fillQueueTable(List<Object[]> data) {
        for (Object[] row : data) {
            queueTableModel.addRow(row);
        }
    }
    private void fillProcessingTable(List<Object[]> data) {
        for (Object[] row : data) {
            processingTableModel.addRow(row);
        }
    }
}
