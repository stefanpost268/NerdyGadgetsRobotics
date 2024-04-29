import pages.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.CardLayout;

public class Main extends JFrame {
    public static void main(String[] args) {
        new Main().gui();
    }

    public void gui() {
        setTitle("NerdyGadgetsRobotics");
        setSize(1350, 720);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setUI(new BasicTabbedPaneUI());
        tabbedPane.addTab("Dashboard", new DashboardPage());
        tabbedPane.addTab("Vooraad", new ProductPage());
        tabbedPane.setBorder(null);

        add(tabbedPane);
        setVisible(true);
    }
}
