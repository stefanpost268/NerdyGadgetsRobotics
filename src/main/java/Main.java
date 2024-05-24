import models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pages.*;
import repositories.*;
import helpers.DatabaseConnector;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.CardLayout;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

public class Main extends JFrame implements ChangeListener {

    @Autowired
    private StockItemRepository stockItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PeopleRepository peopleRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderLinesRepository orderLinesRepository;
    private JTabbedPane tabbedPane;

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

        this.tabbedPane = new JTabbedPane();
        this.tabbedPane.addChangeListener(this);
        tabbedPane.setUI(new BasicTabbedPaneUI());
        tabbedPane.addTab("Dashboard", new DashboardPage(this.orderRepository));
        tabbedPane.addTab("Bestellingen", new OrderPage(this.orderRepository, this.customerRepository, this.peopleRepository, this.stockItemRepository, this.orderLinesRepository));
        tabbedPane.addTab("Vooraad", new ProductPage(this.stockItemRepository));
        tabbedPane.setBorder(null);

        add(tabbedPane);
        setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource().equals(this.tabbedPane)) {
            if(this.tabbedPane.getSelectedIndex() == 1) {
                OrderPage orderPage = (OrderPage) tabbedPane.getSelectedComponent();
                orderPage.refreshTable();
            }
        }
    }
}