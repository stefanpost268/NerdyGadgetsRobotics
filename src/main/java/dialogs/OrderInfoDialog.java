package dialogs;

import models.Customer;
import models.Order;
import models.OrderLines;
import models.StockItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Optional;

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
    private JLabel comment = new JLabel();
    private JLabel internalComment = new JLabel();
    private JLabel deliveryComment = new JLabel();

    public OrderInfoDialog(Order order) {
        Customer customer = order.getCustomer();
        List<OrderLines> orderLines = order.getOrderLines();

        setLayout(new BorderLayout());
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.orderID.setText(String.valueOf(order.getOrderID()));
        this.shippingDate.setText(order.getExpectedDeliveryDate().toString());
        this.orderState.setText("NOT IMPLEMENTED");

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

        add(scrollPane, BorderLayout.EAST);

        JPanel leftPanel = new JPanel();

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(addOrderInfo());
        leftPanel.add(addCustomerInfo());
        leftPanel.add(getPeople());
        leftPanel.add(getComments());

        leftPanel.setMinimumSize(new Dimension(500, 0));
        scrollPane.setMinimumSize(new Dimension(100, 0));

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
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components

        // Order Number Label
        JLabel orderNumberLabel = new JLabel("Bestelling Nummer: ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(orderNumberLabel, gbc);

        // Order Number Value
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(this.orderID, gbc);

        // Delivery Date Label
        JLabel deliveryDateLabel = new JLabel("Bezorg Datum: ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(deliveryDateLabel, gbc);

        // Delivery Date Value
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(this.shippingDate, gbc);

        // Order State Label
        JLabel orderStateLabel = new JLabel("Bestelling Status: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(orderStateLabel, gbc);

        // Order State Value
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(this.orderState, gbc);

        return panel;
    }

    private JPanel addCustomerInfo() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Naam Klant: "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(this.customerName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Telefoon nummer: "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(this.customerPhone, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Adress: "), gbc);

        // Order State Value
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(this.customerAdres, gbc);

        return panel;
    }

    private JPanel getPeople() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Contact Persoon: "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(this.contactPerson, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Verkoop Medewerker: "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(this.salesPerson, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Magazijn Medewerker: "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(this.pickedByPerson, gbc);

        return panel;
    }

    private JPanel getComments() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Opmerkingen: "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(this.comment, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Interne Opmerkingen: "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(this.internalComment, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Bezorg Opmerkingen: "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(this.deliveryComment, gbc);

        return panel;
    }
}
