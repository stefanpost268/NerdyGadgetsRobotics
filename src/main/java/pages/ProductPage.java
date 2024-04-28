package pages;

import services.MysqlConnection;

import javax.swing.*;
import java.util.List;
import java.awt.*;

public class ProductPage extends JPanel {

    private MysqlConnection mysql;
    private List<Object[]> data;

    public ProductPage(MysqlConnection mysql) {
        this.mysql = mysql;
        this.data = this.mysql.executeQuery(
                "SELECT " +
                "StockItemID, StockItemName, UnitPrice, RecommendedRetailPrice, TypicalWeightPerUnit, QuantityPerOuter, Size" +
                " FROM stockitems;"
        );

        String[] columnNames = {"Id", "Naam", "Inkoop prijs", "Verkoop prijs", "Gewicht (KG)", "Vooraad", "Locatie"};
        Object[][] tableData = new Object[this.data.size()][columnNames.length];
        for (int i = 0; i < this.data.size(); i++) {
            tableData[i] = this.data.get(i);
        }

        JTable table = new JTable(tableData, columnNames);
        table.setFillsViewportHeight(true);
        table.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }
}
