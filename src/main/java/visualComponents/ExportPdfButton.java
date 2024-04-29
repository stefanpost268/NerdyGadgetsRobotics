package visualComponents;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import services.PdfRenderer;

public class ExportPdfButton extends JPanel {
    private JButton exportPdfButton = new JButton("Export PDF");
    private final PdfRenderer pdfRenderer = new PdfRenderer();

    public ExportPdfButton() {
        super();

        this.exportPdfButton.addActionListener(e -> savePdf());
        add(this.exportPdfButton);
    }

    private void savePdf() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save PDF");
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
            pdfRenderer.download(filePath);
        }
    }
}
