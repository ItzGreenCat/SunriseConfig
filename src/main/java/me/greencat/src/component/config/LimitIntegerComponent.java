package me.greencat.src.component.config;

import me.greencat.src.Translation;
import me.greencat.src.component.EnumMouseButton;
import me.greencat.src.component.SlideComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.event.entity.minecart.MinecartEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LimitIntegerComponent extends SlideComponent<Integer> {
    public int max;
    public int min;
    public LimitIntegerComponent(Supplier<Integer> baseValueGetter, Consumer<Integer> valueSetter,int max,int min) {
        super(baseValueGetter, valueSetter);
        this.max = max;
        this.min = min;
        setValue(((float)(baseValueGetter.get() - min)) / ((float)(max - min)));
    }

    @Override
    public int getHeight() {
        return 30;
    }

    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        Gui.drawRect(getXCoord(),getYCoord(),getXCoord() + getWidth(),getHeight() + getYCoord(),-855310);
        Gui.drawRect(getXCoord() + getWidth() - 300, getYCoord() + getHeight() / 2 - 1, (int) (getXCoord() + getWidth() - 10 - (290 * (1 - getValue()))),getYCoord() + getHeight() / 2 + 1,2019213055);
        Gui.drawRect((int) (getXCoord() + getWidth() - 10 - (290 * (1 - getValue()))), getYCoord() + getHeight() / 2 - 1,getXCoord() + getWidth() - 10,getYCoord() + getHeight() / 2 + 1,2023529628);
        GlStateManager.color(80.0F / 255.0F,124F / 255F, 1.0f, 1.0f);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(9, DefaultVertexFormats.POSITION);
        for(int i = 360;i > 0;i--){
            worldRenderer.pos((float) ((getXCoord() + getWidth() - 10 - (290 * (1 - getValue()))) + Math.cos((float)(i) * Math.PI / 180.0F) * 4), (float) ((getYCoord() + getHeight() / 2) + Math.sin((float)(i) * Math.PI / 180.0F) * 4),0.0F).endVertex();
        }
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        Minecraft.getMinecraft().fontRendererObj.drawString(Translation.get(this.container.name + "." + this.name),getXCoord() + 3,getYCoord() + getHeight() / 2 - 5,Color.BLACK.getRGB());
        if(isFocused){
            Minecraft.getMinecraft().fontRendererObj.drawString(String.valueOf((int) ((max - min) * getValue() + min)),(int) (getXCoord() + getWidth() - 10 - (290 * (1 - getValue()))) - (Minecraft.getMinecraft().fontRendererObj.getStringWidth(String.valueOf((int) ((max - min) * getValue() + min))) / 2),getYCoord() + getHeight() / 2 - 12,Color.BLACK.getRGB());
        }
    }
    @Override
    public void onMouseClickedMove(int mouseX, int mouseY, EnumMouseButton clickedMouseButton, long timeSinceLastClick) {
        super.onMouseClickedMove(mouseX,mouseY,clickedMouseButton,timeSinceLastClick);
        valueSetter.accept((int) ((max - min) * getValue() + min));
    }

    @Override
    public void onTyping(int key, char c) {

    }
}
