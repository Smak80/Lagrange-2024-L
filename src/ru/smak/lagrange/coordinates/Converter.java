package ru.smak.lagrange.coordinates;

import static java.lang.Math.abs;

public class Converter {
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
    private int width;
    private int height;

    public Converter(
            double xMin,
            double xMax,
            double yMin,
            double yMax,
            int width,
            int height
            ){
        setXShape(xMin, xMax);
        setYShape(yMin, yMax);
        setWidth(width);
        setHeight(height);
    }

    public double getXMin() {
        return xMin;
    }

    public double getXMax() {
        return xMax;
    }

    public void setXShape(double xMin, double xMax) {
        this.xMin = Math.min(xMin, xMax);
        this.xMax = Math.max(xMin, xMax);
        if (abs(xMin-xMax) < 1e-1){
            this.xMin -= 0.05;
            this.xMax += 0.05;
        }
    }

    public double getYMin() {
        return yMin;
    }

    public double getYMax() {
        return yMax;
    }

    public void setYShape(double yMin, double yMax) {
        this.yMin = Math.min(yMin, yMax);
        this.yMax = Math.max(yMin, yMax);
        if (abs(yMin-yMax) < 1e-1){
            this.yMin -= 0.05;
            this.yMax += 0.05;
        }
    }

    public int getWidth() {
        return width - 1;
    }

    public void setWidth(int width) {
        this.width = abs(width);
    }

    public int getHeight() {
        return height - 1;
    }

    public void setHeight(int height) {
        this.height = abs(height);
    }

    public double getXDen(){
        return width / (xMax - xMin);
    }

    public double getYDen(){
        return height / (yMax - yMin);
    }

    /**
     * Метод преобразования координаты из декартовой системы в экранную
     * @param x декартовая система координат
     * @return экранная система координат, соответствующая указанной декартовой координате
     */
    public int xCrt2Scr(double x){
        return (int)((x - xMin) * getXDen());
    }
}