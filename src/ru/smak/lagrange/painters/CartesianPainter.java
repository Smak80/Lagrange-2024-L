package ru.smak.lagrange.painters;

import java.awt.*;

public class CartesianPainter implements Painter{

    private int width;
    private int height;

    @Override
    public Dimension getSize() {
        return new Dimension(width, height);
    }

    @Override
    public void setSize(Dimension d) {
        width = d.width;
        height = d.height;
    }

    @Override
    public void setSize(int width, int height) {
        setSize(new Dimension(width, height));
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawLine(0, height/2, width, height/2);
        g.drawLine(width/2, 0, width/2, height);
    }
}
