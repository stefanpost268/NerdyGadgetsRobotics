package visualComponents;

import javax.swing.*;
import java.awt.*;

public class WarehouseMap extends JPanel {

    private int gridHeight;
    private int gridWidth;

    public WarehouseMap(int gridHeight, int gridWidth) {
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = gridWidth;
        int height = gridHeight;
        width++;
        height++;

        //Drawing lines and numbers/letters

        //Horizontal lines
        for (int i = 1; i <= height; i++) g.drawLine(70, 70 * i , 70 * width, 70 * i);

        //Vertical lines
        for (int i = 1; i <= width; i++) g.drawLine(70 * i , 70, 70 * i, 70 * height);

        //Letters
        for (int i = 1; i < width; i++) g.drawString(getCharForNumber(i), 70 * i + 30, 60);

        //Numbers
        for (int i = 1; i < width; i++) g.drawString(String.valueOf(i), 50, 70 * i + 40);

    }

    private String getCharForNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
    }
}
