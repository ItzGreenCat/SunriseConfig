package me.greencat.src.component;

import me.greencat.src.animation.AnimationEngine;
import me.greencat.src.animation.AnimationManager;
import me.greencat.src.utils.render.Scissor;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ComponentContainer {
    public List<Component> components = new ArrayList<>();
    public int scrollOffset = 0;
    public final int x;
    public final int y;
    public final int width;
    public final int height;
    public int componentLength = 0;
    public String name;
    public Screen parent;
    private final AnimationEngine animationEngine;

    public final EnumSpacialContainer special;
    public ComponentContainer(int x,int y,int width,int height,String name,EnumSpacialContainer special){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
        this.special = special;
        this.animationEngine = new AnimationEngine();
    }
    public void setParent(Screen screen){
        this.parent = screen;
    }
    public void addComponent(Component<?> component){
        component.setContainer(this).setYCoord(y + componentLength);
        components.add(component);
        componentLength = componentLength + component.getHeight();
    }
    public void render(int mouseX,int mouseY,float tickDelta){
        if(special == EnumSpacialContainer.CATEGORY){
            Gui.drawRect(x,y,x + width,y + height, Color.WHITE.getRGB());
            Gui.drawRect(x + width - 2,y,x + width,y + height,-2697514);
        } else if(special == EnumSpacialContainer.CONFIG){
            Gui.drawRect(x,y,x + width,y + height,-855310);
        }
        scrollOffset = (int) animationEngine.xCoord;
        Scissor.enableScissor();
        Scissor.cut(x,y,width,height);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0,-scrollOffset,0);
        components.forEach(it -> it.render(mouseX,mouseY + scrollOffset,tickDelta));
        GlStateManager.popMatrix();
        Scissor.disableScissor();
    }
    public void enableBlockKey(){
        parent.blockingKey.add(this.name);
    }
    public void disableBlockKey(){
        parent.blockingKey.remove(this.name);
    }
    public void onMouseScroll(int value){
        if(value < 0) {
            animationEngine.moveTo(Math.min(scrollOffset + 10,Math.max(componentLength - height,0)),0,0.3,AnimationEngine.EASE_OUT);
        } else if(value > 0) {
            animationEngine.moveTo(Math.max(scrollOffset - 10,0),0,0.3,AnimationEngine.EASE_OUT);
        }
    }
    public void onMousePress(int mouseX,int mouseY,EnumMouseButton button){
        components.forEach(it -> it.onMousePress(mouseX,mouseY + scrollOffset,button));
    }
    public void onMouseRelease(int mouseX,int mouseY,EnumMouseButton button){
        components.forEach(it -> it.onMouseRelease(mouseX,mouseY + scrollOffset,button));
    }
    public void onMouseClickedMove(int mouseX,int mouseY,EnumMouseButton clickedMouseButton,long timeSinceLastClick){
        components.forEach(it -> it.onMouseClickedMove(mouseX,mouseY + scrollOffset,clickedMouseButton,timeSinceLastClick));
    }
    public void onTyping(int key,char c){
        components.forEach(it -> it.onTyping(key,c));
    }
}
