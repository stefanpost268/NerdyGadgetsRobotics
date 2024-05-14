package dialogs;

import models.*;
import repositories.CustomerRepository;
import repositories.PeopleRepository;
import repositories.StockItemRepository;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;

public class CreateOrderDialog extends JDialog implements ActionListener {
    private String[] orderStates = {"In Wachtrij", "Afgerond"};
    private CustomerRepository customerRepository;
    private PeopleRepository peopleRepository;
    private StockItemRepository stockitemRepository;

    private DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Product Nr", "Product", "Aantal", "Gewicht (kg)"}, 0);
    private JTable ordersOnTable = new JTable(this.tableModel);
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
    private JLabel productIDLabel = new JLabel();
    private JTextField productID = new JTextField(5);
    private JSpinner productQuantity = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

    public CreateOrderDialog(Order order, CustomerRepository customerRepository, PeopleRepository peopleRepository, StockItemRepository stockItemRepository) {
        Customer customer = order.getCustomer();
        List<OrderLines> orderLines = order.getOrderLines();
        this.customerRepository = customerRepository;
        this.peopleRepository = peopleRepository;
        this.stockitemRepository = stockItemRepository;

        setTitle("Bestelling aanmaken");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        this.orderID.setText("AUTOINCREMENT");
        this.shippingDate.setText(order.getExpectedDeliveryDate().toString());

        this.customerName.setText(customer.getCustomerName());
        this.customerPhone.setText(customer.getPhoneNumber());
        this.customerAdres.setText(customer.getDeliveryPostalCode());
        this.contactPerson.setText(order.getContactPerson().getFullName());
        this.salesPerson.setText(order.getSalesperson().getFullName());
        if(order.getPickedByPerson() != null) {
            this.pickedByPerson.setText(order.getPickedByPerson().getFullName());
        }

        this.comment.setText(order.getComments());
        this.internalComment.setText(order.getInternalComments());
        this.deliveryComment.setText(order.getDeliveryInstructions());

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(this.ordersOnTable);
        this.ordersOnTable.setEnabled(false);
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
        rightPanelFooter.setLayout(new BorderLayout());
        rightPanelFooter.setBorder(new EmptyBorder(10, 10, 10, 10));
        productIDLabel.setText("Product ID: ");

        toevoegenButton.addActionListener(this);
        rightPanelFooter.add(toevoegenButton, BorderLayout.WEST);
        rightPanelFooterButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
        rightPanelFooterButtons.add(productIDLabel);
        rightPanelFooterButtons.add(productID);
        rightPanelFooterButtons.add(productQuantity);
        rightPanelFooter.add(rightPanelFooterButtons, BorderLayout.EAST);

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
        JLabel deliveryDateLabel = new JLabel("Bezorg Datum: ");
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
        JLabel customerNameLabel = new JLabel("Naam Klant: ");
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
        JLabel contactPersonLabel = new JLabel("Contact Persoon: ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(contactPersonLabel, gbc);

        // Contact Person Value
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(this.contactPerson, gbc);

        // Sales Person Label
        JLabel salesPersonLabel = new JLabel("Verkoop Medewerker: ");
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(salesPersonLabel, gbc);

        // Sales Person Value
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(this.salesPerson, gbc);

        // Picked By Person Label
        JLabel pickedByPersonLabel = new JLabel("Magazijn Medewerker: ");
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

    People getPersonID(JTextField field) {
        try {
            int ID = Integer.parseInt(field.getText());
            Optional<People> people = peopleRepository.findById(ID);
            return people.orElse(null);
        } catch (NumberFormatException e) {
            contactPerson.setBorder(BorderFactory.createLineBorder(Color.RED));
            return null;
        }
    }

    Customer getCustomerID(JTextField field) {
        try {
            int ID = Integer.parseInt(field.getText());
            Optional<Customer> customer = customerRepository.findById(ID);
            return customer.orElse(null);
        } catch (NumberFormatException e) {
            customerName.setBorder(BorderFactory.createLineBorder(Color.RED));
            return null;
        }
    }

    public void createOrder() {
        if (getCustomerID(customerName) == null || getPersonID(salesPerson) == null || getPersonID(pickedByPerson) == null || getPersonID(contactPerson) == null || shippingDate.getText().isEmpty()) {
            return;
        }
        new Order(
                getCustomerID(customerName),                        // CustomerID
                getPersonID(salesPerson),                           // SalesPersonID
                getPersonID(pickedByPerson),                        // PickedByPersonID
                getPersonID(contactPerson),                         // ContactPersonID
                new java.sql.Date(new java.util.Date().getTime()),  // OrderDate
                java.time.LocalDate.now(),                          // ExpectedDeliveryDate
                false,                                              // IsUnderSupplyBackorderd
                comment.getText(),                                  // Comments
                deliveryComment.getText(),                          // DeliveryInstructions
                internalComment.getText(),                          // InternalComments
                getPersonID(pickedByPerson),                        // LastEditedBy
                new java.util.Date(),                               // LastEditedWhen
                orderStates[orderState.getSelectedIndex()]);        // Status
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.saveButton) {
            if (getCustomerID(customerName) == null || getPersonID(salesPerson) == null || getPersonID(pickedByPerson) == null || getPersonID(contactPerson) == null) {
                return;
            }
            System.out.println(new Order(getCustomerID(customerName), getPersonID(salesPerson), getPersonID(pickedByPerson), getPersonID(contactPerson), new java.sql.Date(new java.util.Date().getTime()), java.time.LocalDate.now(), false, comment.getText(), deliveryComment.getText(), internalComment.getText(), getPersonID(salesPerson), new java.util.Date(), orderStates[orderState.getSelectedIndex()]));
        }
        if(e.getSource() == this.closeButton) {
            setVisible(false);
        }
        if(e.getSource() == this.toevoegenButton) {
            stockitemRepository.findById(Integer.parseInt(productID.getText())).ifPresent(stockItem -> {
                tableModel.addRow(new Object[]{
                        stockItem.getStockItemID(),
                        stockItem.getStockItemName(),
                        productQuantity.getValue(),
                        stockItem.getTypicalWeightPerUnit()
                });
            });
        }
    }
}
