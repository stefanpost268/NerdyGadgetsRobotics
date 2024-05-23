package visualComponents;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import services.PdfRenderer;

import java.io.File;

public class ExportPdfButton extends JPanel {
    private JButton exportPdfButton = new JButton("Export PDF");
    private final PdfRenderer pdfRenderer = new PdfRenderer(this.OrderID);

    private int OrderID;
    public ExportPdfButton() {
        super();

        this.exportPdfButton.addActionListener(e -> savePdf());
        add(this.exportPdfButton);
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
            // add .pdf if not defined
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
            pdfRenderer.download(filePath);
        }
    }
}
