import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
public class DashboardPanel extends JPanel {

    private DefaultTableModel wachtrijTableModel;
    private DefaultTableModel verwerkingTableModel;
    public DashboardPanel(List<Object[]> wachtrijData, List<Object[]> verwerkingData) {
        setBackground(Color.WHITE);
        setLayout(null);


        // Containers maken
        JPanel wachtrijContainer = new JPanel();
        wachtrijContainer.setBackground(Color.LIGHT_GRAY);
        wachtrijContainer.setBounds(670, 10, 320, 350);
        wachtrijContainer.setLayout(new FlowLayout(FlowLayout.LEFT)); // align left
        add(wachtrijContainer);

        JPanel verwerkingContainer = new JPanel();
        verwerkingContainer.setBackground(Color.LIGHT_GRAY);
        verwerkingContainer.setBounds(670, 370, 320, 270);
        verwerkingContainer.setLayout(new FlowLayout(FlowLayout.LEFT)); // align left
        add(verwerkingContainer);



        // Container Titels
        JLabel wachtrijLabel = new JLabel("Bestelling wachtrij");
        wachtrijContainer.add(wachtrijLabel);
        JLabel verwerkingLabel = new JLabel("Producten verwerking in Bestelling");
        verwerkingContainer.add(verwerkingLabel);


        // tabellen opzetten / tabel model
        wachtrijTableModel = new DefaultTableModel(
                new Object[]{"Bestel Nr", "Product aantal", "Status","Actie"}, 0
        );

        verwerkingTableModel = new DefaultTableModel(
                new Object[]{"Product ID", "Product naam", "Aantal","Locatie"}, 0
        );


        // tabellen aanmaken
        JTable wachtrijTable = new JTable(wachtrijTableModel);
        wachtrijTable.setDefaultEditor(Object.class, null); // read-only
        JScrollPane wachtrijScrollPane = new JScrollPane(wachtrijTable); // Scroll toevoegen aan tabel als nodig
        wachtrijScrollPane.setPreferredSize(new Dimension(300, 310));
        wachtrijTable.getTableHeader().setReorderingAllowed(false); // Stop user coloms slepen
        wachtrijContainer.add(wachtrijScrollPane);

        JTable verwerkingTable = new JTable(verwerkingTableModel);
        verwerkingTable.setDefaultEditor(Object.class, null); // read-only
        JScrollPane verwerkingScrollPane = new JScrollPane(verwerkingTable); // Scroll toevoegen aan tabel als nodig
        verwerkingScrollPane.setPreferredSize(new Dimension(300, 230));
        verwerkingTable.getTableHeader().setReorderingAllowed(false); // Stop user coloms slepen
        verwerkingContainer.add(verwerkingScrollPane);


        // Voer data in table
        vulWachtrijTable(wachtrijData);
        vulVerwerkingTable(verwerkingData);

    }

    private void vulWachtrijTable(List<Object[]> data) {
        for (Object[] row : data) {
            wachtrijTableModel.addRow(row);
        }
    }

    private void vulVerwerkingTable(List<Object[]> data) {
        for (Object[] row : data) {
            verwerkingTableModel.addRow(row);
        }
    }
}
