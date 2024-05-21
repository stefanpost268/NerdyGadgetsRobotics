package services;

import helpers.DatabaseConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PdfRenderer extends ITextRenderer {

    public PdfRenderer() {
        super();
    }

    public void download(String outputFile) {
        try {
            // Modify the HTML content in-memory
            String html = modifyHtmlContent(Files.readString(getHtmlFile().toPath()));

            PdfRenderer renderer = new PdfRenderer(this.orderLinesRepository, this.customerRepository);

            renderer.setDocumentFromString(html);
            renderer.layout();
            try (OutputStream os = new java.io.FileOutputStream(outputFile)) {
                renderer.createPDF(os);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File getHtmlFile() {
        return new File("src/main/resources/templates/pdf.html");
    }

    private String modifyHtmlContent(String htmlContent) {
        DatabaseConnector database = new DatabaseConnector();

        // zoeken wat je wilt vervangen
        Pattern pattern = Pattern.compile("\\{\\{([^{}]*)\\}\\}");
        Matcher matcher = pattern.matcher(htmlContent);

        // maak een string van de html
        StringBuilder modifiedHtmlContent = new StringBuilder();


        int lastIndex = 0;
        while (matcher.find()) {
            String value = matcher.group(1);
            modifiedHtmlContent.append(htmlContent, lastIndex, matcher.start());

            int OrderID = 1;
            String[] customerData = database.getCustomerData(OrderID);

            String replacementID = Objects.equals(value, "Bestellings nummer:") ? "Bestellings nummer: " + customerData[0]: "" ; // vervang customerid
            String replacementNaam = Objects.equals(value, "Naam:") ? "Naam: " + customerData[1]: "" ; // vervang customernaam
            String replacementPhonenumber = Objects.equals(value, "Telefoonnummer:") ? "Telefoonnummer: " + customerData[2]: "" ;
            String replacementadres = Objects.equals(value, "Adres:") ? "Adres: " + customerData[3]: "" ;
            String replacementpostcode = Objects.equals(value, "postcode:") ? "postcode: " + customerData[4]: "" ;
            String replacementOrderdatum = Objects.equals(value, "Bestel datum:") ? "Bestel datum: " + customerData[5]: "" ;

            modifiedHtmlContent.append(replacementID).append(replacementadres).append(replacementNaam).append(replacementPhonenumber).append(replacementpostcode).append(replacementOrderdatum);

            lastIndex = matcher.end();
        }

        // Append the remaining content after the last match
        modifiedHtmlContent.append(htmlContent.substring(lastIndex));

        return modifiedHtmlContent.toString();
    }
}
