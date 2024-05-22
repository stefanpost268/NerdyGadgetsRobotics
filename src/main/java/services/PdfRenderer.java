package services;

import helpers.DatabaseConnector;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

            PdfRenderer renderer = new PdfRenderer();

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

            int OrderID = 4;
            String[] customerData = database.getCustomerData(OrderID);
            List<Object[]> orderlines = database.getOrderlines(OrderID);

            String replacementID = Objects.equals(value, "Bestellings nummer:") ? "Bestellings nummer: " + customerData[0] : "" ; // vervang customerid
            String replacementNaam = Objects.equals(value, "Naam:") ? "Naam: " + customerData[1]: "" ; // vervang customernaam
            String replacementPhonenumber = Objects.equals(value, "Telefoonnummer:") ? "Telefoonnummer: " + customerData[2]: "" ; // vervang telefoonnummer
            String replacementadres = Objects.equals(value, "Adres:") ? "Adres: " + customerData[3]: "" ; // vervang adres
            String replacementpostcode = Objects.equals(value, "postcode:") ? "postcode: " + customerData[4]: "" ; // vervang postcode
            String replacementOrderdatum = Objects.equals(value, "Bestel datum:") ? "Bestel datum: " + customerData[5]: "" ; // vervang datum

            if (Objects.equals(value, "orderlines")) {
                for (Object[] row : orderlines) {
                    int stockItemID = (int) row[0];
                    int quantity = (int) row[1];
                    String description = (String) row[2];
                    modifiedHtmlContent.append("<tr>")
                            .append("<td>").append(stockItemID).append("</td>")
                            .append("<td>").append(description).append("</td>")
                            .append("<td>").append(quantity).append("</td>")
                            .append("</tr>");
                }
            }
            //String replacementStockitemid = Objects.equals(value, "ITEMID") ? Arrays.toString(customerDataList) : "" ; // vervang itemid
            //String replacementquantity = Objects.equals(value, "PRODUCT") ? Arrays.toString(customerDataList) : "" ; // vervang product
            // String replacementdescription = Objects.equals(value, "AANTAL") ? Arrays.toString(customerDataList) : "" ; // vervang aantal


            modifiedHtmlContent.append(replacementID).append(replacementadres).append(replacementNaam).append(replacementPhonenumber).append(replacementpostcode).append(replacementOrderdatum) ;//.append(replacementStockitemid). append(replacementquantity).append(replacementdescription);

            lastIndex = matcher.end();
        }

        // Append the remaining content after the last match
        modifiedHtmlContent.append(htmlContent.substring(lastIndex));

        return modifiedHtmlContent.toString();
    }
}
