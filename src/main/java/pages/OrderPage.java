package pages;

import javax.swing.*;
import dialogs.OrderInfoDialog;
import repositories.OrderRepository;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderPage extends JPanel implements ActionListener {

    private OrderRepository orderRepository;
    private JButton orderButton = new JButton("Open order id 1");

    public OrderPage(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;

        this.orderButton.addActionListener(this);
        add(this.orderButton);
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