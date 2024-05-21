package dialogs;

import helpers.ChangeOrderInfoDatabase;
import objects.OrderInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeOrderInfoDialog extends JDialog implements ActionListener {
    private JButton save;
    private JButton cancel;
    private JButton addProduct;
    private JSpinner productAmount;
    private JTextArea externalcomment = new JTextArea(4, 20);
    private JTextArea internalComment = new JTextArea(4, 20);
    private JTextArea deliveryComment = new JTextArea(4, 20);

    private OrderInfo orderInfo = new OrderInfo();
    private ChangeOrderInfoDatabase changeOrderInfoDatabase = new ChangeOrderInfoDatabase();


    public ChangeOrderInfoDialog(JFrame frame, int orderID) {
        super(frame, false);


        setTitle("Bestelling bewerken");
        setSize(800, 600);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        orderInfo.setOrderID(orderID);

        JPanel superMainPanel = new JPanel();
        superMainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10)); // Add padding
        getContentPane().add(superMainPanel);
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        //leftpanel
        JPanel leftPanel = new JPanel(new GridLayout(4, 1));

        JPanel leftpanel1 = new JPanel(new GridLayout(2, 3));

        JLabel showOrderID = new JLabel("Bestelling nummer");
        JLabel expectedDeliveryDate = new JLabel("Bezorg datum");
        JLabel orderstate = new JLabel("Status Bestelling");

        JPanel leftpanel2 = new JPanel(new GridLayout(2, 3));
        JLabel phoneNumber = new JLabel("Telefoonnummer");
        JLabel customerName = new JLabel("Naam klant");
        JLabel adress = new JLabel("Adres");

        JPanel leftpanel3 = new JPanel(new GridLayout(2, 3));
        JLabel contactPerson = new JLabel("Contactpersoon");
        JLabel salesPerson = new JLabel("Verkoop medewerker");
        JLabel warehouseEmployee = new JLabel("Magazijn medewerker");

        JPanel leftpanel4 = new JPanel(new GridLayout(6, 1));
        JLabel internalComment = new JLabel("Interne beschrijving");
        JTextField intComment = new JTextField();
        JLabel externalComment = new JLabel("Externe beschrijving");
        JTextField extComment = new JTextField();
        JLabel deliveryInstruction = new JLabel("Bezorg beschrijving");
        JTextField deliverycomment = new JTextField();


        //rightpanel
        JPanel rightPanel = new JPanel(new BorderLayout());

        JPanel addproducts = new JPanel(new BorderLayout());
        JPanel productButtons = new JPanel(new FlowLayout());

        addProduct = new JButton("Toevoegen");

        JComboBox selectProducts = new JComboBox();


        //spinner
        SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, 1000000, 1);
        productAmount = new JSpinner(spinnerModel);
        JFormattedTextField spinnerTextField = ((JSpinner.DefaultEditor) productAmount.getEditor()).getTextField();
        spinnerTextField.setColumns(5);
        spinnerTextField.setHorizontalAlignment(JTextField.LEFT);
        ((DefaultFormatter) spinnerTextField.getFormatter()).setAllowsInvalid(false);

        //table
        String[] columnNames = {"Product Nr", "Product", "aantal", "gewicht (kg)"};

        Object[][] data = {
                {"Data 1", "Data 2", "Data 3", "Data 4", "Data 5"},
                {"Data 5", "Data 6", "Data 7", "Data 8", "Data 9"},
        };
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable productlist = new JTable(model);
        productlist.getColumnModel().setColumnMargin(10);

        superMainPanel.add(mainPanel, BorderLayout.CENTER);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        leftPanel.add(leftpanel1);
        leftpanel1.add(showOrderID);
        leftpanel1.add(expectedDeliveryDate);
        leftpanel1.add(orderstate);

        leftpanel1.add(new JLabel(String.valueOf(orderInfo.getOrderID())));

        leftpanel2.add(phoneNumber);
        leftpanel2.add(customerName);
        leftpanel2.add(adress);

        leftpanel3.add(contactPerson);
        leftpanel3.add(salesPerson);
        leftpanel3.add(warehouseEmployee);

        leftpanel4.add(internalComment);
        leftpanel4.add(intComment);
        leftpanel4.add(externalComment);
        leftpanel4.add(extComment);
        leftpanel4.add(deliveryInstruction);
        leftpanel4.add(deliverycomment);

        leftPanel.add(leftpanel2);
        leftPanel.add(leftpanel3);
        leftPanel.add(leftpanel4);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        rightPanel.add(addproducts, BorderLayout.CENTER);
        rightPanel.add(productButtons, BorderLayout.SOUTH);
        addproducts.add(new JScrollPane(productlist));
        productButtons.add(addProduct, FlowLayout.LEFT);
        productButtons.add(selectProducts);
        productButtons.add(productAmount, FlowLayout.RIGHT);


        //buttonPanel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        cancel = new JButton("Annuleren");
        cancel.addActionListener(this);
        buttonPanel.add(cancel);
        save = new JButton("Opslaan");
        save.addActionListener(this);
        buttonPanel.add(save);

        superMainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {


            dispose();
        }

        if (e.getSource() == addProduct) {
            //product toevoegen aan bestelling
        }

        if (e.getSource() == cancel) {
            dispose();
        }


    }
}


