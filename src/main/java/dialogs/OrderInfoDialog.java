package dialogs;

import models.Customer;
import models.Order;
import models.OrderLines;
import models.StockItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class OrderInfoDialog extends JDialog {
    private DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Product Nr", "Product", "Aantal", "Gewicht (kg)"}, 0);
    private JTable ordersOnTable = new JTable(this.tableModel);
    private JLabel orderID = new JLabel();
    private JLabel shippingDate = new JLabel();
    private JLabel orderState = new JLabel();
    private JLabel customerName = new JLabel();
    private JLabel customerPhone = new JLabel();
    private JLabel customerAdres = new JLabel();
    private JLabel contactPerson = new JLabel();
    private JLabel salesPerson = new JLabel();
    private JLabel pickedByPerson = new JLabel();
    private JTextArea comment = new JTextArea(4, 20);
    private JTextArea internalComment = new JTextArea(4, 20);
    private JTextArea deliveryComment = new JTextArea(4, 20);

    public OrderInfoDialog(Order order) {
        Customer customer = order.getCustomer();
        List<OrderLines> orderLines = order.getOrderLines();

        setLayout(new BorderLayout());
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.orderID.setText(String.valueOf(order.getOrderID()));
        this.shippingDate.setText(order.getExpectedDeliveryDate().toString());
        this.orderState.setText(order.getOrderState());

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

        for(OrderLines orderLine : orderLines) {
            StockItem stockItem = orderLine.getStockItem();
            tableModel.addRow(new Object[]{
                stockItem.getStockItemID(),
                stockItem.getStockItemName(),
                orderLine.getQuantity(),
                stockItem.getTypicalWeightPerUnit()
            });
        }

        JScrollPane scrollPane = new JScrollPane(this.ordersOnTable);
        this.ordersOnTable.setEnabled(false);
        add(scrollPane, BorderLayout.EAST);

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

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, scrollPane);
        splitPane.setResizeWeight(0.67);
        splitPane.setEnabled(false);

        add(splitPane, BorderLayout.CENTER);

        setVisible(true);
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
        this.comment.setEnabled(false);

        // Comment 2
        gbc.gridy = 3;
        panel.add(new JLabel("Interne Opmerkingen: "), gbc);
        gbc.gridy = 4;
        panel.add(new JScrollPane(this.internalComment), gbc);
        this.internalComment.setEnabled(false);

        // Comment 3
        gbc.gridy = 5;
        panel.add(new JLabel("Bezorg Opmerkingen: "), gbc);
        gbc.gridy = 6;
        panel.add(new JScrollPane(this.deliveryComment), gbc);
        this.deliveryComment.setEnabled(false);

        return panel;
    }
}
