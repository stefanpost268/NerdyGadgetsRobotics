package services;

import models.Customer;
import models.Order;
import models.OrderLine;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PdfRenderer extends ITextRenderer {

    private final Order order;

    public PdfRenderer(Order order) {
        super();
        this.order = order;
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

        Pattern pattern = Pattern.compile("\\{\\{([^{}]*)\\}\\}");
        Matcher matcher = pattern.matcher(htmlContent);
        StringBuilder modifiedHtmlContent = new StringBuilder();
        int lastIndex = 0;

        while (matcher.find()) {
            String placeholder = matcher.group(1);
            modifiedHtmlContent.append(htmlContent, lastIndex, matcher.start());

            String replacement = getReplacementForPlaceholder(placeholder);
            modifiedHtmlContent.append(replacement);

            lastIndex = matcher.end();
        }

        modifiedHtmlContent.append(htmlContent.substring(lastIndex));
        return modifiedHtmlContent.toString();
    }

    private String getReplacementForPlaceholder(String placeholder) {
        Customer customer = this.order.getCustomer();

        switch (placeholder) {
            case "Bestellings nummer:":
                return "Bestellings nummer: " + this.order.getOrderID();
            case "Naam:":
                return "Naam: " + customer.getCustomerName();
            case "Telefoonnummer:":
                return "Telefoonnummer: " + customer.getPhoneNumber();
            case "Adres:":
                return "Adres: " + customer.getDeliveryAddressLine2();
            case "postcode:":
                return "postcode: " + customer.getDeliveryPostalCode();
            case "Bestel datum:":
                return "Bestel datum: " + order.getOrderDate();
            case "orderlines":
                return generateOrderLinesTable();
            default:
                return "{{" + placeholder + "}}";
        }
    }

    private String generateOrderLinesTable() {
        StringBuilder tableHtml = new StringBuilder();
        for (OrderLine orderLine : this.order.getOrderLines()) {
            int stockItemID = orderLine.getStockItem().getStockItemID();
            int quantity = orderLine.getQuantity();
            String description = orderLine.getStockItem().getStockItemName();
            tableHtml.append("<tr>")
                    .append("<td>").append(stockItemID).append("</td>")
                    .append("<td>").append(description).append("</td>")
                    .append("<td>").append(quantity).append("</td>")
                    .append("</tr>");
        }
        return tableHtml.toString();
    }
}
