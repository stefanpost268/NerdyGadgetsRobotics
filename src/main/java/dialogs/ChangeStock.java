package dialogs;

import helpers.DatabaseChangeStock;
import objects.ProductInfo;

import javax.swing.*;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeStock extends JDialog implements ActionListener {
    private JButton save;
    private JButton cancel;
    private JSpinner spinner;
    private JComboBox<String> cb;
    private ProductInfo productInfo = new ProductInfo();
    private DatabaseChangeStock database = new DatabaseChangeStock();
    private int productID;

    public ChangeStock(JFrame frame, int productInfoID) {
        super(frame, false);
        setResizable(false);
        setTitle("Voorraad bewerken");

        //set stockitemID
        productID = productInfoID;
        productInfo.setStockItemID(productInfoID);
        database.getProductInfo(productInfo);

        // Creating panels
        JPanel mainPanel = new JPanel(new GridLayout(2, 0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10)); // Add padding
        getContentPane().add(mainPanel);

        JPanel infoPanel = new JPanel(new GridLayout(6, 2));
        mainPanel.add(new JScrollPane(infoPanel), BorderLayout.NORTH);

        JPanel changesPanel = new JPanel(new GridLayout(4, 3));

        mainPanel.add(new JScrollPane(changesPanel), BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        add(buttonsPanel, BorderLayout.SOUTH);

        JPanel spinnerpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel combobox = new JPanel(new FlowLayout(FlowLayout.LEFT));


        //changespanel setup
        changesPanel.add(new JLabel(" Voorraad:"));
        changesPanel.add(new JLabel(" locatie:"));
        changesPanel.add(new JLabel(""));// fill empty grid
        changesPanel.add(spinnerpanel);
        changesPanel.add(combobox);
        changesPanel.add(new JLabel(""));// fill empty grid
        changesPanel.add(new JLabel(""));// fill empty grid
        changesPanel.add(new JLabel(""));// fill empty grid
        changesPanel.add(new JLabel(""));// fill empty grid

        // Spinner setup
        SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, 1000000, 1);
        spinner = new JSpinner(spinnerModel);
        JFormattedTextField spinnerTextField = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
        spinnerTextField.setColumns(5);
        spinnerTextField.setHorizontalAlignment(JTextField.LEFT);
        ((DefaultFormatter) spinnerTextField.getFormatter()).setAllowsInvalid(false);
        spinnerpanel.add(spinner);

        // Combobox setup
        String[] choices = new String[26]; // 25 locations + 1 null option
        choices[0] = "null"; // First option is null
        int index = 1;
        for (char letter = 'A'; letter <= 'E'; letter++) {
            for (int number = 1; number <= 5; number++) {
                choices[index++] = String.valueOf(letter) + number;
            }
        }
        cb = new JComboBox<>(choices);
        combobox.add(cb);

        // infoPanel setup
        infoPanel.add(new JLabel("Product ID:"));
        infoPanel.add(new JLabel("Product Naam:"));
        infoPanel.add(new JLabel(String.valueOf(productInfo.getStockItemID())));
        infoPanel.add(new JLabel(productInfo.getStockItemName()));
        infoPanel.add(new JLabel("Inkoop prijs:"));
        infoPanel.add(new JLabel("Verkoop Prijs:"));
        infoPanel.add(new JLabel(String.valueOf(productInfo.getUnitPrice())));
        infoPanel.add(new JLabel(String.valueOf(productInfo.getRecommendedRetailPrice())));
        infoPanel.add(new JLabel("Gewicht (Kg):"));
        infoPanel.add(new JLabel(""));// fill empty grid
        infoPanel.add(new JLabel(String.valueOf(productInfo.getTypicalWeightPerUnit())));

        // Set initial values for spinner and combobox
        spinner.setValue(productInfo.getQuantityPerOuter());
        cb.setSelectedItem(productInfo.getLocation());

        // buttonPanel setup
        cancel = new JButton("Annuleren");
        cancel.addActionListener(this);
        buttonsPanel.add(cancel);

        save = new JButton("Opslaan");
        save.addActionListener(this);
        buttonsPanel.add(save);

        setSize(450, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {
            String selectedLocation = (String) cb.getSelectedItem();
            int quantity = (int) spinner.getValue();
            database.changeLocationAndAmount(selectedLocation, quantity, productID);

            dispose();
        } else if (e.getSource() == cancel) {
            dispose();
        }
    }
}
