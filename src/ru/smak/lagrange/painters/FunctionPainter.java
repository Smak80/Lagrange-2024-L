package ru.smak.lagrange.painters;

import math.Polynomial;
import ru.smak.lagrange.coordinates.Converter;

import java.awt.*;

public class FunctionPainter implements Painter{

    private int width;
    private int height;
    private Polynomial p = null;
    private Converter c = null;

    public Converter getConverter() {
        return c;
    }

    public void setConverter(Converter c) {
        this.c = c;
    }

    public Polynomial getFunction() {
        return p;
    }

    public void setFunction(Polynomial p) {
        this.p = p;
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
        g.setColor(Color.RED);
        for (int i = 0; i < width-1; i++){
            var x1 = c.xScr2Crt(i);
            var y1 = p.invoke(x1); // вычисление полинома в точке
            var yS1 = c.yCrt2Scr(y1);
            var x2 = c.xScr2Crt(i+1);
            var y2 = p.invoke(x2);
            var yS2 = c.yCrt2Scr(y2);
            g.drawLine(i, yS1, i+1, yS2);
        }
    }
}
