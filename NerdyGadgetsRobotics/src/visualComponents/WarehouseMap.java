package visualComponents;

import javax.swing.*;
import java.awt.*;

public class WarehouseMap extends JPanel {

    private int gridHeight;
    private int gridWidth;
    private int width = 670;
    private int height = 415;

    public WarehouseMap(int gridHeight, int gridWidth) {
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
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

    }

    private String getCharForNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
    }
}
