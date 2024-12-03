package ru.smak.lagrange.painters;

import ru.smak.lagrange.coordinates.Converter;

import java.awt.*;

public class CartesianPainter implements Painter{

    private int width;
    private int height;

    private Converter c = null;

    public Converter getConverter() {
        return c;
    }

    public void setConverter(Converter c) {
        this.c = c;
    }

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
        g.drawLine(0, c.yCrt2Scr(0.0), width, c.yCrt2Scr(0.0));
        g.drawLine(c.xCrt2Scr(0.0), 0, c.xCrt2Scr(0.0), height);
    }
}
