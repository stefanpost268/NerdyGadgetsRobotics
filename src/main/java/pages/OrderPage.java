package pages;

import dialogs.OrderInfoDialog;
import models.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import repositories.OrderRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Optional;

public class OrderPage extends JPanel implements ActionListener {

    private int page = 0;
    private int recordsOnPage = 37;
    private long maxPages = 0;
    private OrderRepository orderRepository;
    private OrderInfoDialog infoDialog;
    private JButton addOrderButton = new JButton("Aanmaken Bestelling");
    private JButton showOrderButton = new JButton("Bekijken Bestelling");
    private JButton nextPageButton = new JButton("Volgende");
    private JLabel pageLabel = new JLabel();
    private JButton previousPageButton = new JButton("Vorige");
    private JTable table;

    public OrderPage(OrderRepository orderRepository) {
        setLayout(new BorderLayout());
        this.orderRepository = orderRepository;

        this.addOrderButton.addActionListener(this);
        this.showOrderButton.addActionListener(this);
        this.nextPageButton.addActionListener(this);
        this.previousPageButton.addActionListener(this);


        JPanel footerPanel = new JPanel(new BorderLayout());
        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footerPanel.add(rightButtonPanel, BorderLayout.EAST);
        footerPanel.add(this.pageLabel, BorderLayout.CENTER);
        footerPanel.add(leftButtonPanel, BorderLayout.WEST);

        rightButtonPanel.add(this.addOrderButton);
        rightButtonPanel.add(this.showOrderButton);
        leftButtonPanel.add(this.previousPageButton);
        leftButtonPanel.add(this.nextPageButton);

        add(footerPanel, BorderLayout.PAGE_END);

        this.maxPages = this.orderRepository.count() / this.recordsOnPage;

        refreshTable();
    }

    private void renderTable(ArrayList<Order> orders) {
        String[] columnNames = { "Bestel Nr","Naam","Status","Product aantal","Bestel datum" };
        Object[][] data = new Object[orders.size()][5];
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            data[i] = order.toObjectArray();
        }
        if(this.table == null) {
            this.table = new JTable(data, columnNames);
            this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            JScrollPane scrollPane = new JScrollPane(this.table);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            this.table.setFillsViewportHeight(true);
            this.table.setDefaultEditor(Object.class, null);

            add(scrollPane, BorderLayout.CENTER);
        } else {
            this.table.setModel(new DefaultTableModel(data, columnNames));
        }

        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.showOrderButton)) {
            showOrderEvent();
        }
        if(e.getSource().equals(this.nextPageButton)) {
            if(this.maxPages > this.page + 1) {
                this.page++;
            }
            refreshTable();
        }
        if(e.getSource().equals(this.previousPageButton)) {
            if(this.page != 0) {
                this.page--;
                refreshTable();
            }
        }
    }

    private void refreshTable() {
        this.pageLabel.setText("Pagina: " + (this.page + 1) + " van " + this.maxPages);
        Iterable<Order> orders = this.orderRepository.findAllByDesc(PageRequest.of(this.page, this.recordsOnPage));
        ArrayList<Order> target = new ArrayList<>();
        orders.forEach(target::add);
        renderTable(target);
    }

    private void showOrderEvent() {
        String input = this.table.getValueAt(this.table.getSelectedRow(), 0).toString();
        int orderId = Integer.parseInt(input);

        Optional<Order> order  = this.orderRepository.findById(orderId);

        if(this.infoDialog != null) {
            this.infoDialog.dispose();
        }

        this.infoDialog = new OrderInfoDialog(order.get());
    }
}