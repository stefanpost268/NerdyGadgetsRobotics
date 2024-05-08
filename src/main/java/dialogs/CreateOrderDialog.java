package dialogs;

import javax.swing.*;
import java.awt.*;

public class CreateOrderDialog extends JDialog {
    private JLabel customerNameLabel = new JLabel("Klantnaam");
    private JTextField customerNameField = new JTextField(20);

    public CreateOrderDialog() {
        setLayout(new FlowLayout());
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(customerNameLabel);
        add(customerNameField);
    }
}
