import models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pages.*;
import repositories.*;
import helpers.DatabaseConnector;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.CardLayout;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

public class Main extends JFrame {

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

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Main main = context.getBean(Main.class);
        main.gui();
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        timestamp.setNanos(0);
        System.out.println(timestamp);
    }

    public void CreateOrderLines(Order order) {
        Optional<StockItem> stockItem = stockItemRepository.findById(1);
        if (stockItem.isEmpty()) {
            System.out.println("StockItem bestaat niet");
            return;
        }

        StockItem stockItem1 = stockItem.get();

        OrderLines orderLines = new OrderLines();
        orderLines.setOrderID(order);
        orderLines.setStockItem(stockItem1);
        orderLines.setDescription("Description");
        orderLines.setPackageTypeID(stockItem1.getUnitPackageID());
        orderLines.setQuantity(1);
        orderLines.setPickedQuantity(1);
        orderLines.setLastEditedBy(order.getLastEditedBy());
        orderLines.setLastEditedWhen(new Date());

        orderLinesRepository.save(orderLines);

    }

    public void execute() {
        Optional<People> people = peopleRepository.findById(1);
        if (people.isEmpty()) {
            System.out.println("Persoon bestaat niet");
            return;
        }
        Optional<Customer> customer = customerRepository.findById(1);
        if (customer.isEmpty()) {
            System.out.println("Klant bestaat niet");
            return;
        }

        People person = people.get();
        Customer klant = customer.get();

        // Nieuwe order aanmaken
        Order order = new Order();

        order.setCustomer(klant);//CustomerID
        order.setSalesperson(person);//SalesPersonID
        order.setPickedByPerson(person);//PickedByPersonID
        order.setContactPerson(person);//ContactPersonID
        order.setOrderDate(new java.sql.Date(new Date().getTime()));//OrderDate
        order.setExpectedDeliveryDate(LocalDate.now());//ExpectedDeliveryDate
        order.setUnderSupplyBackordered(false);//IsUnderSupplyBackorderd
        order.setComments("Verstuurd via de Java Applicatie");//Comments
        order.setDeliveryInstructions("Delivery Instructions");//DeliveryInstructions
        order.setInternalComments("Internal Comments");//InternalComments
        order.setLastEditedBy(person);//LastEditedBy
        order.setLastEditedWhen(new Date());//LastEditedWhen
        order.setStatus("NOT IMPLEMENTED");//Status

        order.setComments("Java Applicatie");
        order.setContactPerson(person);

        orderRepository.save(order);

        CreateOrderLines(order);
    }

    public void gui() {
        DatabaseConnector database = new DatabaseConnector();

        setTitle("NerdyGadgetsRobotics");
        setSize(1350, 720);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        this.execute();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setUI(new BasicTabbedPaneUI());
        tabbedPane.addTab("Dashboard", new DashboardPage(database.getQueueData(), database.getProcessingData()));
        tabbedPane.addTab("Bestellingen", new OrderPage(this.orderRepository, this.customerRepository, this.peopleRepository, this.stockItemRepository, this.orderLinesRepository));
        tabbedPane.addTab("Vooraad", new ProductPage(this.stockItemRepository));
        tabbedPane.setBorder(null);

        add(tabbedPane);
        setVisible(true);
    }
}