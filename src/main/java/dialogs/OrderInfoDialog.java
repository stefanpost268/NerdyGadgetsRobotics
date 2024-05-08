package dialogs;

import models.Customer;
import models.Order;

import javax.swing.*;
import java.awt.*;

public class OrderInfoDialog extends JDialog {

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

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setSize(300, 200);
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

        add(addOrderInfo());
        add(addCustomerInfo());
        add(getPeople());
        add(getComments());
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
