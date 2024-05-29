package visualComponents;

import models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import repositories.OrderRepository;
import services.PdfRenderer;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class PackingListButton extends JPanel {
    private final JButton pakketbon = new JButton("Export pakketbon");
    private int OrderID;
    private final OrderRepository order;
    public Page<Order> orders;

    public PackingListButton(OrderRepository orderRepository) {
        super();
        this.order = orderRepository;
        this.pakketbon.addActionListener(e -> dialog());
        add(this.pakketbon);
    }

    private void dialog() {
        pakketbon.addActionListener(new ActionListener() {
            final String[] options = {"cancel", "exporteer bon"};

            @Override
            public void actionPerformed(ActionEvent evt) {
                orders = order.findFinishedOrders(Pageable.ofSize(1000));

                List<Order> orderList = orders.getContent();
                Object[][] orderArray = new Object[orderList.size()][3];

                int index = 0;
                for (Order order : orderList) {
                    Object[] item = new Object[3];
                    item[0] = order.getOrderID();
                    item[1] = order.getPickingCompletedWhen();
                    item[2] = order.getOrderLines().size();
                    orderArray[index] = item;
                    index++;
                }

                String[] columnNames = {"OrderID", "PickingCompletedWhen", "Aantal Producten"};
                DefaultTableModel tableModel = new DefaultTableModel(orderArray, columnNames);

                JTable table = new JTable(tableModel);
                JScrollPane scrollPane = new JScrollPane(table);
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(scrollPane, BorderLayout.CENTER);

                int selection = JOptionPane.showOptionDialog(null, panel, "bon exporteren", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

                if (selection == 1) {
                    String input = table.getValueAt(table.getSelectedRow(), 0).toString();
                    OrderID = Integer.parseInt(input);
                    savePdf();
                }
            }
        });
    }

    private void savePdf() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save PDF");
        fileChooser.setSelectedFile(new File("export.pdf"));

        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        fileChooser.addChoosableFileFilter(
                new FileNameExtensionFilter("PDF files", "pdf")
        );

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }

            File file = new File(filePath);
            if (file.exists()) {
                int response = JOptionPane.showConfirmDialog(null,
                        "Wil je de bestaande PDF overschrijven?",
                        "Overschrijf",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.CANCEL_OPTION) {
                    return;
                }
            }

            Order order = this.order.findById(OrderID).orElse(null);
            PdfRenderer renderer = new PdfRenderer(order);
            renderer.download(filePath);
        }
    }
}
