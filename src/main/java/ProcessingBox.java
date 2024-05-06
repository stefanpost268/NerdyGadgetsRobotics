import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProcessingBox extends JPanel {
    private DefaultTableModel processingTableModel;

    public ProcessingBox(List<Object[]> processingData) {
        setBackground(Color.LIGHT_GRAY);
        setLayout(new FlowLayout(FlowLayout.LEFT)); // align left
        setBounds(670, 370, 320, 270);

        JLabel processingLabel = new JLabel("Producten processing in Bestelling");
        add(processingLabel);

        processingTableModel = new DefaultTableModel(
                new Object[]{"Product ID", "Product naam", "Aantal", "Locatie"}, 0
        );

        JTable processingTable = new JTable(processingTableModel);
        processingTable.setDefaultEditor(Object.class, null); // read-only
        processingTable.getTableHeader().setReorderingAllowed(false); // Stop user column swipe

        JScrollPane processingScrollPane = new JScrollPane(processingTable); // Add scroll to table
        processingScrollPane.setPreferredSize(new Dimension(300, 230));
        add(processingScrollPane);

        fillProcessingTable(processingData);
    }

    private void fillProcessingTable(List<Object[]> data) {
        for (Object[] row : data) {
            processingTableModel.addRow(row);
        }
    }
}
