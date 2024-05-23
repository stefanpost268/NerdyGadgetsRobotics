package visualComponents;

import helpers.DatabaseConnector;
import services.PdfRenderer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Timestamp;
import java.util.List;

public class pakketbonbutton extends JPanel {
    private final JButton pakketbon = new JButton("Export pakketbon");


    private int OrderID;


    public pakketbonbutton() {
        super();

        this.pakketbon.addActionListener(e -> dialog());
        add(this.pakketbon);
    }


    private void dialog() {
        pakketbon.addActionListener(new ActionListener() {
            final String[] options = {"cancel", "exporteer bon"};

            @Override
            public void actionPerformed(ActionEvent evt) {
                DatabaseConnector database = new DatabaseConnector();
                List<Object[]> orderlines = database.finishorders();

                // Add example order lines
                orderlines.add(new Object[]{1, new Timestamp(System.currentTimeMillis()), 5});
                orderlines.add(new Object[]{2, new Timestamp(System.currentTimeMillis()), 3});
                orderlines.add(new Object[]{3, new Timestamp(System.currentTimeMillis()), 7});

                String[] columnNames = {"OrderID", "PickingCompletedWhen", "Aantal Producten"};
                Object[][] data = orderlines.toArray(new Object[0][]);
                DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);

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
            PdfRenderer renderer = new PdfRenderer(OrderID);
            renderer.download(filePath);
        }
    }
}
