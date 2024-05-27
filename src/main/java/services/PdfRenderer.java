package services;

import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.File;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class PdfRenderer extends ITextRenderer {
    public PdfRenderer() {
        super();
    }

    public void download(String outputFile) {
        try {
            String html = Files.readString(getHtmlFile().toPath());

            PdfRenderer renderer = new PdfRenderer();

            renderer.setDocumentFromString(html);

            renderer.layout();
            try(OutputStream os = new java.io.FileOutputStream(outputFile)) {
                renderer.createPDF(os);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File getHtmlFile() {
        return new File("src/main/resources/templates/pdf.html");
    }
}
