package me.greencat.src.animation;



public class AnimationEngine {
    public double xCoord;
    public double yCoord;
    public double targetX;
    public double targetY;
    public int type;
    public long endTime = 0L;
    public long startTime = 0L;

    public LinearFunction xLinearFunction;
    public LinearFunction yLinearFunction;
    public InverseProportionFunction xInverseProportionFunction;
    public InverseProportionFunction yInverseProportionFunction;

    public static final int EASE_OUT = 1;
    public static final int EASE_IN = 2;
    public static final int LINEAR = 3;

    public AnimationEngine(int x,int y) {
        xCoord = x;
        yCoord = y;
        targetX = x;
        targetY = y;
    }
    public AnimationEngine() {
        xCoord = 0;
        yCoord = 0;
        targetX = 0;
        targetY = 0;
    }
    public void setPosition(int x,int y) {
        xCoord = x;
        yCoord = y;
        targetX = x;
        targetY = y;
    }
    public void register(){
        AnimationManager.add(this);
    }

    public void moveTo(float x,float y,double second,int type){
        if(targetX == x && targetY == y){
            return;
        }
        startTime = System.currentTimeMillis();
        endTime = (long) (System.currentTimeMillis() + (1000 * second));
        targetX = x;
        targetY = y;
        xLinearFunction = null;
        yLinearFunction = null;
        xInverseProportionFunction = null;
        yInverseProportionFunction = null;
        this.type = type;
        if(type == LINEAR){
            xLinearFunction = new LinearFunction(1,xCoord,100,targetX);
            yLinearFunction = new LinearFunction(1,yCoord,100,targetY);
        }
        if(type == EASE_OUT){
            xInverseProportionFunction = new InverseProportionFunction(2500);
            xInverseProportionFunction.setOffsetX(20);
            yInverseProportionFunction = new InverseProportionFunction(2500);
            yInverseProportionFunction.setOffsetX(20);
            double XFunctionYPositionAt1 = xInverseProportionFunction.getY(1);
            double XFunctionYPositionAt100 = xInverseProportionFunction.getY(100);
            xLinearFunction = new LinearFunction(XFunctionYPositionAt1,xCoord,XFunctionYPositionAt100,targetX);
            double YFunctionYPositionAt1 = yInverseProportionFunction.getY(1);
            double YFunctionYPositionAt100 = yInverseProportionFunction.getY(100);
            yLinearFunction = new LinearFunction(YFunctionYPositionAt1,yCoord,YFunctionYPositionAt100,targetY);
        }
        if(type == EASE_IN){
            xInverseProportionFunction = new InverseProportionFunction(-2500);
            xInverseProportionFunction.setOffsetX(-101 - 20);
            yInverseProportionFunction = new InverseProportionFunction(-2500);
            yInverseProportionFunction.setOffsetX(-101 - 20);
            double XFunctionYPositionAt1 = xInverseProportionFunction.getY(1);
            double XFunctionYPositionAt100 = xInverseProportionFunction.getY(100);
            xLinearFunction = new LinearFunction(XFunctionYPositionAt1,xCoord,XFunctionYPositionAt100,targetX);
            double YFunctionYPositionAt1 = yInverseProportionFunction.getY(1);
            double YFunctionYPositionAt100 = yInverseProportionFunction.getY(100);
            yLinearFunction = new LinearFunction(YFunctionYPositionAt1,yCoord,YFunctionYPositionAt100,targetY);
        }
        register();
    }

    public void RenderTick(){
        if(System.currentTimeMillis() > endTime){
            destroy();
            xCoord = targetX;
            yCoord = targetY;
        }
        if(xCoord != targetX){
            xCoord = getXPosition();
        }
        if(yCoord != targetY){
            yCoord = getYPosition();
        }
        if(getXPosition() > xCoord && xCoord > targetX){
            xCoord = targetX;
        }
        if(getYPosition() > yCoord && yCoord > targetY){
            yCoord = targetY;
        }
        if(getXPosition() < xCoord && xCoord < targetX){
            xCoord = targetX;
        }
        if(getYPosition() < yCoord && yCoord < targetY){
            yCoord = targetY;
        }
        if(xCoord == targetX && yCoord == targetY){
            destroy();
        }
        callback();
    }
    public void callback(){}
    public void destroy(){
        AnimationManager.destroy(this);
    }
    public double getXPosition(){
        long currentTime = System.currentTimeMillis() - startTime;
        long allTime = endTime - startTime;
        double progress = ((double)currentTime / (double)allTime) * 100.0D;
        if(type == LINEAR){
            double exactlyPosition = xLinearFunction.getY(progress);
            return exactlyPosition;
        }
        if(progress == 0.0f){
            return xCoord;
        } else if (progress >= 100.0f){
            return targetX;
        } else {
            double numberInInverseProportion = xInverseProportionFunction.getY(progress);
            double exactlyPosition = xLinearFunction.getY(numberInInverseProportion);
            return exactlyPosition;
        }
    }
    public double getYPosition(){
        long currentTime = System.currentTimeMillis() - startTime;
        long allTime = endTime - startTime;
        double progress = ((double)currentTime / (double)allTime) * 100.0D;
        if(type == LINEAR){
            double exactlyPosition = yLinearFunction.getY(progress);
            return exactlyPosition;
        }
        if(progress == 0.0f){
            return yCoord;
        } else if(progress >= 100.0f){
            return targetY;
        } else {
            double numberInInverseProportion = yInverseProportionFunction.getY(progress);
            double exactlyPosition = yLinearFunction.getY(numberInInverseProportion);
            return exactlyPosition;
        }
    }
}
