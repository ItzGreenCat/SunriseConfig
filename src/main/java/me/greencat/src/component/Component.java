package me.greencat.src.component;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Component<T> {
    public ComponentContainer container = null;
    public int YCoord;
    public Supplier<T> baseValueGetter = null;
    public Consumer<T> valueSetter = null;

    public String name;

    public Component(Supplier<T> baseValueGetter,Consumer<T> valueSetter){
        this.baseValueGetter = baseValueGetter;
        this.valueSetter = valueSetter;
    }
    public Component setContainer(ComponentContainer container){
        this.container = container;
        return this;
    }
    public Component setYCoord(int y){
        this.YCoord = y;
        return  this;
    }
    public int getXCoord(){
        return container.x;
    }
    public int getYCoord(){
        return YCoord;
    }
    public int getWidth(){
        return container.width;
    }
    public abstract int getHeight();
    public abstract void render(int mouseX,int mouseY,float tickDelta);
    public abstract void onMousePress(int mouseX,int mouseY,EnumMouseButton button);
    public abstract void onMouseRelease(int mouseX,int mouseY,EnumMouseButton button);
    public abstract void onMouseClickedMove(int mouseX, int mouseY,EnumMouseButton clickedMouseButton,long timeSinceLastClick);
    public abstract void onTyping(int key,char c);
    public boolean isMouseInRange(int mouseX,int mouseY){
        return mouseX >= getXCoord() && mouseY >= getYCoord() && mouseX < getXCoord() + getWidth() && mouseY < getYCoord() + getHeight();
    }
}
