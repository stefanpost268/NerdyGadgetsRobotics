package visualComponents;

import repositories.OrderRepository;
import services.SerialCommunication;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.Serial;
import java.util.Timer;

import static services.Formatter.map;

public class LegendBox extends JPanel {
    private PackingListButton packingListButton;
    private int robotX = 0;
    private int robotY = 0;
    private java.util.Timer timer = new Timer();
    private SerialCommunication serialCommunication;


    public LegendBox(SerialCommunication serialCommunication, OrderRepository orderRepository) {
        this.serialCommunication = serialCommunication;
        this.packingListButton = new PackingListButton(orderRepository);

        setLayout(new BorderLayout());
        add(packingListButton, BorderLayout.PAGE_END);
        setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK));
        setSize(50, 100);

        initializeTimer();
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        g.setColor(java.awt.Color.BLACK);
        g.drawString("Magazijn robot", 40, 57);
        g.drawString("Robot Positie:", 15, 230);
        g.drawString("X " + robotX + ", Y " + robotY, 15, 250);
        g.setColor(java.awt.Color.RED);
        g.fillOval(10, 40, 25, 25);

    }

    private void initializeTimer() {
        timer.scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                try {
                    if (serialCommunication.getReceivedJson() == null || !serialCommunication.getReceivedJson().getString("label").equals("LOCATION")) {
                        return;
                    }

                    robotX = serialCommunication.getReceivedJson().getJSONObject("data").getInt("x-location");
                    robotY = serialCommunication.getReceivedJson().getJSONObject("data").getInt("y-location");

                    repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1000);
    }
}
