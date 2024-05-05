import models.StockItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pages.*;
import repositories.StockItemRepository;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.CardLayout;

public class Main extends JFrame {

    @Autowired
    private StockItemRepository stockItemRepository;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Main main = context.getBean(Main.class);
        Iterable<StockItem> stockItems = main.stockItemRepository.findByStockItemName("\"The Gu\" red shirt XML tag t-shirt (Black) 3XL");

        for(StockItem stockItem : stockItems) {
            System.out.println(stockItem.getStockItemName());
        }
        main.gui();
    }

    public void gui() {
        setTitle("NerdyGadgetsRobotics");
        setSize(1350, 720);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setUI(new BasicTabbedPaneUI());
        tabbedPane.addTab("Dashboard", new DashboardPage());
        tabbedPane.addTab("Bestellingen", new OrderPage());
        tabbedPane.addTab("Vooraad", new ProductPage());
        tabbedPane.setBorder(null);

        add(tabbedPane);
        setVisible(true);
    }
}