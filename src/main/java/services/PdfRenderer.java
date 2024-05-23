package services;

import helpers.DatabaseConnector;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PdfRenderer extends ITextRenderer {

    private final int orderID;

    public PdfRenderer(int orderID) {
        super();
        this.orderID = orderID;
    }

    public void download(String outputFile) {
        try {
            String html = modifyHtmlContent(Files.readString(getHtmlFile().toPath()));
            this.setDocumentFromString(html);
            this.layout();
            try (OutputStream os = new java.io.FileOutputStream(outputFile)) {
                this.createPDF(os);
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
        String[] customerData = database.getCustomerData(orderID);
        List<Object[]> orderLines = database.getOrderlines(orderID);

        Pattern pattern = Pattern.compile("\\{\\{([^{}]*)\\}\\}");
        Matcher matcher = pattern.matcher(htmlContent);
        StringBuilder modifiedHtmlContent = new StringBuilder();
        int lastIndex = 0;

        while (matcher.find()) {
            String placeholder = matcher.group(1);
            modifiedHtmlContent.append(htmlContent, lastIndex, matcher.start());

            String replacement = getReplacementForPlaceholder(placeholder, customerData, orderLines);
            modifiedHtmlContent.append(replacement);

            lastIndex = matcher.end();
        }

        modifiedHtmlContent.append(htmlContent.substring(lastIndex));
        return modifiedHtmlContent.toString();
    }

    private String getReplacementForPlaceholder(String placeholder, String[] customerData, List<Object[]> orderLines) {
        switch (placeholder) {
            case "Bestellings nummer:":
                return "Bestellings nummer: " + customerData[0];
            case "Naam:":
                return "Naam: " + customerData[1];
            case "Telefoonnummer:":
                return "Telefoonnummer: " + customerData[2];
            case "Adres:":
                return "Adres: " + customerData[3];
            case "postcode:":
                return "postcode: " + customerData[4];
            case "Bestel datum:":
                return "Bestel datum: " + customerData[5];
            case "orderlines":
                return generateOrderLinesTable(orderLines);
            default:
                return "{{" + placeholder + "}}";
        }
    }

    private String generateOrderLinesTable(List<Object[]> orderLines) {
        StringBuilder tableHtml = new StringBuilder();
        for (Object[] row : orderLines) {
            int stockItemID = (int) row[0];
            int quantity = (int) row[1];
            String description = (String) row[2];
            tableHtml.append("<tr>")
                    .append("<td>").append(stockItemID).append("</td>")
                    .append("<td>").append(description).append("</td>")
                    .append("<td>").append(quantity).append("</td>")
                    .append("</tr>");
        }
        return tableHtml.toString();
    }
}
