package visualComponents;

import services.PdfRenderer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class pakketbonbutton extends JPanel {
    private final JButton pakketbon = new JButton("Export pakketbon");

    private final PdfRenderer pdfRenderer = new PdfRenderer();


    public pakketbonbutton() {
        super();

        this.pakketbon.addActionListener(e -> dialog());
        add(this.pakketbon);
    }

    JPanel pakket = new JPanel();

    private void dialog(){
        pakketbon.addActionListener(new java.awt.event.ActionListener() {
            String[] options = { "cancel", "exporteer bon"};
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                var selection = JOptionPane.showOptionDialog(null,null,"bon exporteren",0,2,null, options, options[0]);

                if (selection == 1) {
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
