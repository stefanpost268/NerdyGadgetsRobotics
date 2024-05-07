package dialogs;

import models.Customer;
import models.Order;

import javax.swing.*;
import java.awt.*;

public class OrderInfoDialog extends JDialog {

    private JLabel orderID = new JLabel();
    private JLabel shippingDate = new JLabel();
    private JLabel orderState = new JLabel();

    private JLabel shippingDateLabel = new JLabel("Bezorg Datum: ");
    public OrderInfoDialog(Order order) {
        Customer customer = order.getCustomer();
        System.out.println(customer.getCustomerID());

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.orderID.setText(String.valueOf(order.getOrderID()));
        this.shippingDate.setText(order.getExpectedDeliveryDate().toString());
        this.orderState.setText("NOT IMPLEMENTED");


        add(addOrderInfo());
        add(addCustomerInfo());
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

        panel.add(new JLabel("Naam Klant: "));
        panel.add(new JLabel("Klant ID: "));

        panel.add(new JLabel("Telefoon nummer:"));
        panel.add(new JLabel("NULL"));

        panel.add(new JLabel("Adress:"));
        panel.add(new JLabel("NULL"));

        panel.add(new JLabel("Email"));
        panel.add(new JLabel("NULL"));

        return panel;
    }
}
