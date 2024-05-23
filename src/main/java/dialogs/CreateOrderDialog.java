package dialogs;

import visualComponents.CreateOrderJTable;
import models.*;
import repositories.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

public class CreateOrderDialog extends JDialog implements ActionListener {
    private String[] orderStates = {"Open", "InProgess", "Done", "Error"};
    private CustomerRepository customerRepository;
    private PeopleRepository peopleRepository;
    private StockItemRepository stockitemRepository;
    private OrderLinesRepository orderLinesRepository;
    private OrderRepository orderRepository;

    private DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Product Nr", "Product", "Aantal", "Gewicht (kg)"}, 0);
    private CreateOrderJTable ordersOnTable = new CreateOrderJTable(this.tableModel);
    private JLabel orderID = new JLabel();
    private JTextField shippingDate = new JTextField(10);
    private JComboBox<String> orderState = new JComboBox<String>(orderStates);
    private JTextField customerName = new JTextField(10);
    private JTextField customerPhone = new JTextField(10);
    private JTextField customerAdres = new JTextField(10);
    private JTextField contactPerson = new JTextField(10);
    private JTextField salesPerson = new JTextField(10);
    private JTextField pickedByPerson = new JTextField(10);
    private JTextArea comment = new JTextArea(4, 20);
    private JTextArea internalComment = new JTextArea(4, 20);
    private JTextArea deliveryComment = new JTextArea(4, 20);
    private JButton saveButton = new JButton("Opslaan");
    private JButton closeButton = new JButton("Sluiten");
    private JButton toevoegenButton = new JButton("Toevoegen");
    private JButton removeStockitem = new JButton("Verwijderen");
    private JLabel productIDLabel = new JLabel();
    private JLabel productQuantityLabel = new JLabel("Aantal: ");
    private JTextField productID = new JTextField(5);
    private JSpinner productQuantity = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

    public CreateOrderDialog(CustomerRepository customerRepository, PeopleRepository peopleRepository, StockItemRepository stockItemRepository, OrderLinesRepository orderLinesRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.peopleRepository = peopleRepository;
        this.stockitemRepository = stockItemRepository;
        this.orderLinesRepository = orderLinesRepository;
        this.orderRepository = orderRepository;

        setTitle("Bestelling aanmaken");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        this.orderID.setText("AUTOINCREMENT");
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        this.ordersOnTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(this.ordersOnTable);
        rightPanel.add(scrollPane, BorderLayout.NORTH);
        rightPanel.add(addRightPanelFooter(), BorderLayout.SOUTH);

        add(rightPanel, BorderLayout.EAST);
        add(addFooter(), BorderLayout.SOUTH);

        JPanel leftPanel = new JPanel();

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(addOrderInfo());
        leftPanel.add(new JSeparator());
        leftPanel.add(addCustomerInfo());
        leftPanel.add(new JSeparator());
        leftPanel.add(getPeople());
        leftPanel.add(new JSeparator());
        leftPanel.add(getComments());

        leftPanel.setMinimumSize(new Dimension(350, 0));
        scrollPane.setMinimumSize(new Dimension(250, 0));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.67);
        splitPane.setEnabled(false);

        add(splitPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel addRightPanelFooter() {
        JPanel rightPanelFooter = new JPanel();
        JPanel rightPanelFooterButtons = new JPanel();
        JPanel rightPanelStockitemID = new JPanel();
        JPanel rightPanelQuantity = new JPanel();
        rightPanelFooter.setLayout(new GridLayout(2, 2));
        rightPanelFooter.setBorder(new EmptyBorder(10, 10, 10, 10));
        rightPanelStockitemID.setLayout(new FlowLayout(FlowLayout.LEFT));
        rightPanelQuantity.setLayout(new FlowLayout(FlowLayout.LEFT));
        productIDLabel.setText("Product ID: ");

        removeStockitem.addActionListener(this);
        toevoegenButton.addActionListener(this);
        rightPanelFooter.add(toevoegenButton, BorderLayout.WEST);
        rightPanelFooter.add(removeStockitem, BorderLayout.CENTER);
        rightPanelStockitemID.add(productIDLabel);
        rightPanelStockitemID.add(productID);
        rightPanelFooter.add(rightPanelStockitemID);
        rightPanelQuantity.add(productQuantityLabel);
        rightPanelQuantity.add(productQuantity);
        rightPanelFooter.add(rightPanelQuantity);

        return rightPanelFooter;
    }

    private JPanel addFooter() {
        JPanel footer = new JPanel();
        footer.setLayout(new FlowLayout(FlowLayout.RIGHT));
        closeButton.addActionListener(this);
        saveButton.addActionListener(this);
        footer.add(closeButton);
        footer.add(saveButton);

        return footer;
    }

    private JPanel addOrderInfo() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components
        gbc.weightx = 1.0;

        // Order Number Label
        JLabel orderNumberLabel = new JLabel("Bestelling Nummer: ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(orderNumberLabel, gbc);

        // Order Number Value
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(this.orderID, gbc);

        // Delivery Date Label
        JLabel deliveryDateLabel = new JLabel("Bezorg Datum (yyyy-mm-dd): ");
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(deliveryDateLabel, gbc);

        // Delivery Date Value
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(this.shippingDate, gbc);

        // Order State Label
        JLabel orderStateLabel = new JLabel("Bestelling Status: ");
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(orderStateLabel, gbc);

        // Order State Value
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(this.orderState, gbc);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        return panel;
    }

    private JPanel addCustomerInfo() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components
        gbc.weightx = 1.0;

        // Customer Name Label
        JLabel customerNameLabel = new JLabel("Klant ID: ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(customerNameLabel, gbc);

        // Customer Name Value
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(this.customerName, gbc);

        // Phone Number Label
        JLabel phoneNumberLabel = new JLabel("Telefoon nummer: ");
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(phoneNumberLabel, gbc);

        // Phone Number Value
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(this.customerPhone, gbc);

        // Address Label
        JLabel addressLabel = new JLabel("Adress: ");
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(addressLabel, gbc);

        // Address Value
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(this.customerAdres, gbc);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        return panel;
    }

    private JPanel getPeople() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components
        gbc.weightx = 1.0;

        // Contact Person Label
        JLabel contactPersonLabel = new JLabel("Contact Persoon ID: ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(contactPersonLabel, gbc);

        // Contact Person Value
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(this.contactPerson, gbc);

        // Sales Person Label
        JLabel salesPersonLabel = new JLabel("Verkoop Medewerker ID: ");
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(salesPersonLabel, gbc);

        // Sales Person Value
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(this.salesPerson, gbc);

        // Picked By Person Label
        JLabel pickedByPersonLabel = new JLabel("Magazijn Medewerker ID: ");
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(pickedByPersonLabel, gbc);

        // Picked By Person Value
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(this.pickedByPerson, gbc);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        return panel;
    }

    private JPanel getComments() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components

        // Comment 1
        gbc.gridy = 1;
        panel.add(new JLabel("Opmerkingen: "), gbc);
        gbc.gridy = 2;
        panel.add(new JScrollPane(this.comment), gbc);

        // Comment 2
        gbc.gridy = 3;
        panel.add(new JLabel("Interne Opmerkingen: "), gbc);
        gbc.gridy = 4;
        panel.add(new JScrollPane(this.internalComment), gbc);

        // Comment 3
        gbc.gridy = 5;
        panel.add(new JLabel("Bezorg Opmerkingen: "), gbc);
        gbc.gridy = 6;
        panel.add(new JScrollPane(this.deliveryComment), gbc);

        return panel;
    }

    private People getPersonID(JTextField field) {
        try {
            int ID = Integer.parseInt(field.getText());
            Optional<People> people = peopleRepository.findById(ID);
            field.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            return people.orElse(null);
        } catch (NumberFormatException e) {
            field.setBorder(BorderFactory.createLineBorder(Color.RED));
            return null;
        }
    }

    private Customer getCustomerID(JTextField field) {
        try {
            int ID = Integer.parseInt(field.getText());
            Optional<Customer> customer = customerRepository.findById(ID);
            field.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            return customer.orElse(null);
        } catch (NumberFormatException e) {
            field.setBorder(BorderFactory.createLineBorder(Color.RED));
            return null;
        }
    }

    private LocalDate getLocalDate(JTextField field) {
        try {
            field.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            return LocalDate.parse(field.getText());
        } catch (Exception e) {
            field.setBorder(BorderFactory.createLineBorder(Color.RED));
            return null;
        }
    }

    public void createOrder() {
        if (getLocalDate(shippingDate) == null || getCustomerID(customerName) == null || getPersonID(contactPerson) == null || getPersonID(salesPerson) == null || getPersonID(pickedByPerson) == null || shippingDate.getText().isEmpty()) {
            return;
        }
        Order order = new Order(
                getCustomerID(customerName),                                                        // CustomerID
                getPersonID(salesPerson),                                                           // SalesPersonID
                getPersonID(pickedByPerson),                                                        // PickedByPersonID
                getPersonID(contactPerson),                                                         // ContactPersonID
                new java.sql.Date(new java.util.Date().getTime()),                                  // OrderDate
                LocalDate.parse(shippingDate.getText()), // ExpectedDeliveryDate
                false,                                                                              // IsUnderSupplyBackorderd
                comment.getText(),                                                                  // Comments
                deliveryComment.getText(),                                                          // DeliveryInstructions
                internalComment.getText(),                                                          // InternalComments
                getPersonID(pickedByPerson),                                                        // LastEditedBy
                new java.util.Date(),                                                               // LastEditedWhen
                orderStates[orderState.getSelectedIndex()]);                                        // Status

        orderRepository.save(order);

        createOrderlines(order);
    }

    public void createOrderlines(Order order) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Optional<StockItem> stockItem = stockitemRepository.findById((int) tableModel.getValueAt(i, 0));
            if (stockItem.isEmpty()) {
                System.out.println("StockItem bestaat niet");
                return;
            }

            StockItem stockItem1 = stockItem.get();

            OrderLine orderLines = new OrderLine();
            orderLines.setOrderID(order);
            orderLines.setStockItem(stockItem1);
            orderLines.setDescription(stockItem1.getStockItemName());
            orderLines.setPackageTypeID(stockItem1.getUnitPackageID());
            orderLines.setQuantity((int) tableModel.getValueAt(i, 2));
            orderLines.setPickedQuantity((int) tableModel.getValueAt(i, 2));
            orderLines.setLastEditedBy(order.getLastEditedBy());
            orderLines.setLastEditedWhen(new Date());

            orderLinesRepository.save(orderLines);
        }
        setVisible(false);
    }

    private void addStockitemToTable() {
        try {
            int productId = Integer.parseInt(productID.getText());
            Optional<StockItem> optionalStockItem = stockitemRepository.findById(productId);

            if (optionalStockItem.isPresent()) {
                StockItem stockItem = optionalStockItem.get();

                tableModel.addRow(new Object[]{
                        stockItem.getStockItemID(),
                        stockItem.getStockItemName(),
                        productQuantity.getValue(),
                        stockItem.getTypicalWeightPerUnit()
                });
            }
            productID.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        } catch (NumberFormatException e) {
            productID.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
    }

    private void showOrderEvent() {
        try {
            String input = this.ordersOnTable.getValueAt(this.ordersOnTable.getSelectedRow(), 0).toString();
            int StockitemId = Integer.parseInt(input);

            Optional<Order> Stockitem = this.orderRepository.findById(StockitemId);

            if (Stockitem.isEmpty()) {
                return;
            }

            tableModel.removeRow(this.ordersOnTable.getSelectedRow());
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.saveButton) {
            createOrder();
        }
        if(e.getSource() == this.closeButton) {
            setVisible(false);
        }
        if(e.getSource() == this.toevoegenButton) {
            addStockitemToTable();
        }
        if(e.getSource() == this.removeStockitem) {
            showOrderEvent();
        }
    }
}
