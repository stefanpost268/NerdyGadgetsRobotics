import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pages.*;
import repositories.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.CardLayout;

public class Main extends JFrame {

    @Autowired
    private StockItemRepository stockItemRepository;
    @Autowired
    private OrderRepository orderRepository;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Main main = context.getBean(Main.class);
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
        tabbedPane.addTab("Bestellingen", new OrderPage(this.orderRepository));
        tabbedPane.addTab("Vooraad", new ProductPage(this.stockItemRepository));
        tabbedPane.setBorder(null);

        add(tabbedPane);
        setVisible(true);
    }
}