package visualComponents;

import objects.GridProduct;
import services.SerialCommunication;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Timer;

import static services.Formatter.map;

public class WarehouseMap extends JPanel {

    private int gridHeight;
    private int gridWidth;
    public int width = 570;
    public int height = 265;
    private int robotX = 250;
    private int robotY = 250;
    private int warehouseMaxX = 0;
    private int warehouseMaxY = 0;
    private ArrayList<GridProduct> gridProducts = new ArrayList<>();
    private Timer timer = new Timer();
    private SerialCommunication serialCommunication = new SerialCommunication();

    public WarehouseMap(SerialCommunication serialCommunication, int gridHeight, int gridWidth) {
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        this.serialCommunication = serialCommunication;
        initializeTimer();
    }

    public WarehouseMap(SerialCommunication serialCommunication, int gridHeight, int gridWidth, int width, int height) {
        this(serialCommunication, gridHeight, gridWidth);
        this.width = width;
        this.height = height;
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

                    //map the robot's location to the map's coordinates
                    robotX = map(robotX, 0, 1000, 57, 627);
                    robotY = Math.abs(map(robotY, 0, 1000, 27, 292) - 292);
                    System.out.println(Math.abs(map(robotY, 0, 1000, 27, 292) - 292));

                    repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1000);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawRobot(g);
    }

    private String getCharForNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
    }

    public void addGridProduct(int x, int y, Color color) {
        for (GridProduct gridProduct : gridProducts) {
            if (gridProduct.getGridX() == x && gridProduct.getGridY() == y) {
                System.out.println("x: " + x + " y: " + y + " already exists");
                return;
            }
        }
        if (x > gridWidth || y > gridHeight) {
            System.out.println("x: " + x + " y: " + y + " does not exist");
            return;
        }
        gridProducts.add(new GridProduct(x, y, color));
    }

    public void clearGridProducts() {
        gridProducts.clear();
    }

    public void drawGrid(Graphics g) {
        int blockWidth = this.width / gridWidth;
        int blockHeight = this.height / gridHeight;

        for (int i = 0; i <= gridHeight + 1; i++) {
            g.drawLine(blockWidth - blockWidth / 2, (blockHeight * i) - blockHeight / 2, (blockWidth * (gridWidth + 1)) - blockWidth / 2, (blockHeight * i) - blockHeight / 2);
        }

        for (int i = 0; i <= gridWidth + 1; i++) {
            g.drawLine((blockWidth * i) - blockWidth / 2, blockHeight - blockHeight / 2, (blockWidth * i) - blockWidth / 2, (blockHeight * (gridHeight + 1)) - blockHeight / 2);
        }

        for (int i = 1; i < gridWidth + 1; i++) {
            g.drawString(getCharForNumber(i), blockWidth * i, blockHeight - blockHeight / 2 - 10);
        }

        for (int i = 1; i < gridHeight + 1; i++) {
            g.drawString(String.valueOf(i), blockWidth / 2 - 20, blockHeight * i);
        }

        for (GridProduct gridProduct : gridProducts) {
            gridProduct.draw(g, this);
        }
    }

    public void drawRobot(Graphics g) {
        System.out.println("Drawing robot at x: " + robotX + " y: " + robotY);
        int blockWidth = this.width / gridWidth;
        int blockHeight = this.height / gridHeight;
        g.setColor(Color.RED);
        g.fillOval(robotX - 12, robotY - 12, 25, 25);
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getMapWidth() {
        return width;
    }

    public int getMapHeight() {
        return height;
    }

    public ArrayList<GridProduct> getGridProducts() {
        return gridProducts;
    }
}
