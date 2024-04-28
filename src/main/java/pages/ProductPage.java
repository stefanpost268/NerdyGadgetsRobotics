package pages;

import services.MysqlConnection;

import javax.swing.*;
import java.util.List;

public class ProductPage extends JPanel {

    private MysqlConnection mysql;

    public ProductPage(MysqlConnection mysql) {
        this.mysql = mysql;
        JLabel label = new JLabel("Vooraad");
        List<Object[]> data = this.mysql.executeQuery("SELECT * FROM stockitemholdings;");
        for (Object[] row : data) {
            for (Object column : row) {
                System.out.print(column + " ");
            }
            System.out.println();
        }
        add(label);
    }
}
