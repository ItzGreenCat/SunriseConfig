package me.greencat.src.animation;

public class LinearFunction {
    private final double k;
    private final double b;
    private double offsetX = 0.0D;
    private double offsetY = 0.0D;
    public LinearFunction(double k,double b){
        this.k = k;
        this.b = b;
    }
    public LinearFunction (double x1,double y1,double x2,double y2){
        this.k = (y2 - y1) / (x2 - x1);
        this.b = y1 - this.k * x1;
    }
    public void setOffsetX(double value){
        offsetX = value;
    }
    public void setOffsetY(double value){
        offsetY = value;
    }
    public double getY(double x){
        return (x + offsetX) * k + b + offsetY;
    }
    public double getX(double y){
        if(k + offsetX != 0) {
            return ((y + offsetY) - b) / k + offsetX;
        } else {
            return 0;
        }
    }
    @Override
    public String toString(){
        return "K: " + k + " B: " + b + " OffsetX " + offsetX + " OffsetY " + offsetY;
    }
}
