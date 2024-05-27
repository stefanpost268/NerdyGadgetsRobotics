package visualComponents;

import objects.GridProduct;
import services.SerialCommunication;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Timer;

public class WarehouseMap extends JPanel {

    private int gridHeight;
    private int gridWidth;
    public int width = 570;
    public int height = 265;
    private int robotX = 250;
    private int robotY = 250;
    private ArrayList<GridProduct> gridProducts = new ArrayList<>();
    private Timer timer = new Timer();
    private SerialCommunication serialCommunication = new SerialCommunication();


    public WarehouseMap(int gridHeight, int gridWidth) {
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        timer.scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                robotX = serialCommunication.getReceivedJson().getJSONObject("data").getInt("x-location");
                robotY = serialCommunication.getReceivedJson().getJSONObject("data").getInt("y-location");
                repaint();
            }
        }, 0, 1000);
    }

    public WarehouseMap(int gridHeight, int gridWidth, int width, int height) {
        this(gridHeight, gridWidth);
        this.width = width;
        this.height = height;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Drawing lines and numbers/letters
        drawGrid(g);

        //Drawing the robot
        drawRobot(g);
    }

    //Helper function to convert a number to the letter for the grid
    private String getCharForNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
    }

    public void addGridProduct(int x, int y, Color color) {
        for (int i = 0; i < gridProducts.size(); i++) {
            if (gridProducts.get(i).getGridX() == x && gridProducts.get(i).getGridY() == y) {
                System.out.println("x: " + x + " y: " + y + " already exists");
                return;
            }
        }
        if (x > gridWidth || y > gridHeight) {
            System.out.println("x: " + x + " y: " + y + " does not exist");
            return;
        }
        gridProducts.add(new GridProduct(x,y,color));
    }

    public void clearGridProducts() {
        gridProducts.clear();
    }

    public void drawGrid(Graphics g) {

        int blockWidth =  this.width / gridWidth;
        int blockHeight = this.height / gridHeight;

        //im doing calculations to make sure the letters/numbers are in the middle of the block and the grid is scaled properly
        //Horizontal lines
        for (int i = 0; i <= gridHeight + 1; i++) {
            g.drawLine(blockWidth - blockWidth/2, (blockHeight * i) - blockHeight/2  , (blockWidth * (gridWidth + 1)) - blockWidth/2 , (blockHeight * i) - blockHeight/2 );
        }

        //Vertical lines
        for (int i = 0; i <= gridWidth + 1; i++) {
            g.drawLine((blockWidth * i) - blockWidth/2 , blockHeight - blockHeight/2 , (blockWidth * i) - blockWidth/2 , (blockHeight * (gridHeight + 1)) - blockHeight/2 );
        }

        //Letters
        for (int i = 1; i < gridWidth + 1; i++) {
            g.drawString(getCharForNumber(i), blockWidth * i, blockHeight - blockHeight/2 - 10);
        }

        //Numbers
        for (int i = 1; i < gridHeight + 1; i++) {
            g.drawString(String.valueOf(i), blockWidth/2 - 20, blockHeight * i );
        }

        for (GridProduct gridProduct : gridProducts) {
            gridProduct.draw(g, this);
        }
    }

    public void drawRobot(Graphics g) {
        System.out.println("Drawing robot at x: " + robotX + " y: " + robotY);
        int blockWidth =  this.width / gridWidth;
        int blockHeight = this.height / gridHeight;
        g.setColor(Color.RED);
        g.drawOval(robotX, robotY, 50, 50);
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
