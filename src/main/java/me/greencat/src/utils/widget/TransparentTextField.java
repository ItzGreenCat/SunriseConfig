package me.greencat.src.utils.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class TransparentTextField extends GuiTextField {
    public TransparentTextField(int componentId, FontRenderer fontrendererObj) {
        super(componentId, fontrendererObj,0 ,0,0,0);
    }
    public void drawText(int x, int y, Color color){
        if(getVisible()) {
            Minecraft.getMinecraft().fontRendererObj.drawString(this.getText(),x,y,color.getRGB());
        }
    }
}
