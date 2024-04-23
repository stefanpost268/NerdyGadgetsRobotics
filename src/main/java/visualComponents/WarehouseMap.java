package visualComponents;

import objects.GridProduct;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class WarehouseMap extends JPanel {

    private int gridHeight;
    private int gridWidth;
    private int width = 670;
    private int height = 415;
    private ArrayList<GridProduct> gridProducts = new ArrayList<GridProduct>();

    public WarehouseMap(int gridHeight, int gridWidth) {
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;

        //dummy data
        this.addGridProduct(1,1,Color.BLUE);
        this.addGridProduct(3,2,Color.RED);
        this.addGridProduct(3,50,Color.YELLOW);
        this.addGridProduct(5,5,Color.GREEN);
    }

    public WarehouseMap(int gridHeight, int gridWidth, int width, int height) {
        this(gridHeight, gridWidth);
        this.width = width;
        this.height = height;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int blockWidth =  this.width / gridWidth;
        int blockHeight = this.height / gridHeight;


        int width = gridWidth;
        int height = gridHeight;

        width++;
        height++;

        //Drawing lines and numbers/letters

        //Horizontal lines
        for (int i = 0; i <= height; i++) g.drawLine(blockWidth - blockWidth/2, (blockHeight * i) - blockHeight/2  , (blockWidth * width) - blockWidth/2 , (blockHeight * i) - blockHeight/2 );

        //Vertical lines
        for (int i = 0; i <= width; i++) g.drawLine((blockWidth * i) - blockWidth/2 , blockHeight - blockHeight/2 , (blockWidth * i) - blockWidth/2 , (blockHeight * height) - blockHeight/2 );

        //Letters
        for (int i = 1; i < width; i++) g.drawString(getCharForNumber(i), blockWidth * i, blockHeight - blockHeight/2 - 10);

        //Numbers
        for (int i = 1; i < height; i++) g.drawString(String.valueOf(i), blockWidth/2 - 20, blockHeight * i );

        for (GridProduct gridProduct : gridProducts) gridProduct.draw(g, this);


    }

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
