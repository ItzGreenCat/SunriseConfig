package me.greencat.src.animation;

public class InverseProportionFunction {
    private final double k;
    private double offsetX = 0.0D;
    private double offsetY = 0.0D;
    public InverseProportionFunction(double k){
        this.k = k;
    }
    public InverseProportionFunction(double x,double y){
        this.k = x * y;
    }
    public void setOffsetX(double value){
        offsetX = value;
    }
    public void setOffsetY(double value){
        offsetY = value;
    }
    public double getY(double x){
        if(x + offsetX != 0) {
            return (k / (x + offsetX)) + offsetY;
        } else {
            return 0;
        }
    }
    public double getX(double y){
        if(y + offsetY != 0) {
            return (k / (y + offsetY)) + offsetX;
        } else {
            return 0;
        }
    }

    @Override
    public String toString(){
        return "K: " + k + " OffsetX " + offsetX + " OffsetY " + offsetY;
    }
}
