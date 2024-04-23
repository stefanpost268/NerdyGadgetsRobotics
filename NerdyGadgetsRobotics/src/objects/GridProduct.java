package objects;

import visualComponents.WarehouseMap;

import java.awt.*;

public class GridProduct {
    private int width = 25;
    private int height = 25;
    private int gridX;
    private int gridY;
    private Color color;

    public GridProduct(int gridX, int gridY, Color color) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.color = color;
    }

    public void draw(Graphics g, WarehouseMap map) {
        //smart math to make sure the rectangle is in the middle
        int x = (((map.getMapWidth() / map.getGridWidth()) ) * gridX) - width/2;
        int y = (((map.getMapHeight() / map.getGridHeight()) ) * gridY) - height/2;

        g.setColor(color);
        g.fillRect(x,y,width,height);
    }
}
