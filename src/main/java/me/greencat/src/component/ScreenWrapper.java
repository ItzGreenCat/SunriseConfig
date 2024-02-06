package me.greencat.src.component;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;

import java.io.IOException;

public class ScreenWrapper extends GuiScreen {
    public Screen screen;
    public int mouseX = 0;
    public int mouseY = 0;
    public ScreenWrapper(Screen screen){
        this.screen = screen;
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        int dWheel = Mouse.getDWheel();
        screen.onMouseScroll(mouseX,mouseY,dWheel);
        screen.render(mouseX,mouseY,partialTicks);
        super.drawScreen(mouseX,mouseY,partialTicks);
    }
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
        screen.onMousePress(mouseX,mouseY,mouseButton == 0 ? EnumMouseButton.LEFT : mouseButton == 1 ? EnumMouseButton.RIGHT : EnumMouseButton.MIDDLE);
        super.mouseClicked(mouseX,mouseY,mouseButton);
    }
    @Override
    protected void mouseReleased(int mouseX, int mouseY, int mouseButton){
        screen.onMouseRelease(mouseX,mouseY,mouseButton == 0 ? EnumMouseButton.LEFT : mouseButton == 1 ? EnumMouseButton.RIGHT : EnumMouseButton.MIDDLE);
        super.mouseReleased(mouseX,mouseY,mouseButton);
    }
    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int mouseButton, long timeSinceLastClick)
    {
        screen.onMouseClickedMove(mouseX,mouseY,mouseButton == 0 ? EnumMouseButton.LEFT : mouseButton == 1 ? EnumMouseButton.RIGHT : EnumMouseButton.MIDDLE,timeSinceLastClick);
    }
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        screen.onTyping(mouseX,mouseY,keyCode,typedChar);
        if(screen.blockingKey.isEmpty()){
           super.keyTyped(typedChar,keyCode);
        }
    }
}
