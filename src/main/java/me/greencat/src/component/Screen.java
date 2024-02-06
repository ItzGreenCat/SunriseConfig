package me.greencat.src.component;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Screen {
    public List<ComponentContainer> containers = new CopyOnWriteArrayList<>();
    public List<String> blockingKey = new LinkedList<>();
    public void addContainer(ComponentContainer container){
        container.setParent(this);
        containers.add(container);
    }
    public void render(int mouseX,int mouseY,float tickDelta){
        containers.forEach(it -> it.render(mouseX,mouseY,tickDelta));
    }
    public void onMouseScroll(int mouseX,int mouseY,int value){
        containers.stream().filter(it -> mouseX >= it.x && mouseX <= it.x + it.width && mouseY >= it.y && mouseY <= it.y + it.height).forEach(it -> it.onMouseScroll(value));
    }
    public void onMousePress(int mouseX,int mouseY,EnumMouseButton button){
        containers.stream().filter(it -> mouseX >= it.x && mouseX <= it.x + it.width && mouseY >= it.y && mouseY <= it.y + it.height).forEach(it -> it.onMousePress(mouseX,mouseY,button));
    }
    public void onMouseRelease(int mouseX,int mouseY,EnumMouseButton button){
        containers.stream().filter(it -> mouseX >= it.x && mouseX <= it.x + it.width && mouseY >= it.y && mouseY <= it.y + it.height).forEach(it -> it.onMouseRelease(mouseX,mouseY,button));
    }
    public void onMouseClickedMove(int mouseX,int mouseY,EnumMouseButton clickedMouseButton,long timeSinceLastClick){
        containers.stream().filter(it -> mouseX >= it.x && mouseX <= it.x + it.width && mouseY >= it.y && mouseY <= it.y + it.height).forEach(it -> it.onMouseClickedMove(mouseX,mouseY,clickedMouseButton,timeSinceLastClick));
    }
    public void onTyping(int mouseX,int mouseY,int key,char c){
        containers.stream().filter(it -> mouseX >= it.x && mouseX <= it.x + it.width && mouseY >= it.y && mouseY <= it.y + it.height).forEach(it -> it.onTyping(key,c));
    }
}
