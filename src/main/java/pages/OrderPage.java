package pages;

import javax.swing.*;
import dialogs.OrderInfoDialog;
import models.Order;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class OrderPage extends JPanel implements ActionListener {

    private JButton orderButton = new JButton("Open order id 1");
    private List<Order> order = new Order().get();

    public OrderPage() {
        add(this.orderButton);
        this.orderButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.orderButton) {
            OrderInfoDialog infoDialog = new OrderInfoDialog();
            infoDialog.add(new JLabel("Order Info Dialog"));
            infoDialog.setSize(300, 200);
            infoDialog.setVisible(true);
            infoDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }
}