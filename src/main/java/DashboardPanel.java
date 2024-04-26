import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
public class DashboardPanel extends JPanel {
    public DashboardPanel(List<Object[]> queueData, List<Object[]> processingData) {
        setBackground(Color.WHITE);
        setLayout(null);

        QueueBox queueBox = new QueueBox(queueData);
        add(queueBox);

        ProcessingBox processingBox = new ProcessingBox(processingData);
        add(processingBox);
    }
}
