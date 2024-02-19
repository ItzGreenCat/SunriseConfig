package me.greencat.src.component.config;

import me.greencat.src.Translation;
import me.greencat.src.animation.AnimationEngine;
import me.greencat.src.component.Component;
import me.greencat.src.component.EnumMouseButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BooleanComponent extends Component<Boolean> {
    boolean currentStatus;
    private final AnimationEngine animationEngine;
    public BooleanComponent(Supplier<Boolean> baseValueGetter, Consumer<Boolean> valueSetter) {
        super(baseValueGetter, valueSetter);
        currentStatus = baseValueGetter.get();
        animationEngine = new AnimationEngine(currentStatus ? 15 : 30,0);
    }

    @Override
    public int getHeight() {
        return 30;
    }

    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        Gui.drawRect(getXCoord(),getYCoord(),getXCoord() + getWidth(),getHeight() + getYCoord(),-855310);
        Minecraft.getMinecraft().fontRendererObj.drawString(Translation.get(this.container.name + "." + this.name),getXCoord() + 3,getYCoord() + getHeight() / 2 - 5,Color.BLACK.getRGB());
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        if(currentStatus){
            Gui.drawRect(getXCoord() + getWidth() - 30, (int) (getYCoord() + getHeight() * 0.35),getXCoord() + getWidth() - 15, (int) (getYCoord() + getHeight() * 0.68),2019213055);
            GlStateManager.color(90.0F / 255.0F,190F / 255F, 1.0f,120F / 255F);
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldRenderer = tessellator.getWorldRenderer();
            worldRenderer.begin(9, DefaultVertexFormats.POSITION);
            for(int i = 90;i > -90;i--){
                worldRenderer.pos((float) ((getXCoord() + getWidth() - 15) + Math.cos((float)(i) * Math.PI / 180.0F) * 4.5), (float) ((getYCoord() + getHeight() / 2) + Math.sin((float)(i) * Math.PI / 180.0F) * 4.5),0.0F).endVertex();
            }
            tessellator.draw();
            WorldRenderer worldRenderer1 = tessellator.getWorldRenderer();
            worldRenderer1.begin(9, DefaultVertexFormats.POSITION);
            for(int i = 270;i > 90;i--){
                worldRenderer1.pos((float) ((getXCoord() + getWidth() - 30) + Math.cos((float)(i) * Math.PI / 180.0F) * 4.5), (float) ((getYCoord() + getHeight() / 2) + Math.sin((float)(i) * Math.PI / 180.0F) * 4.5),0.0F).endVertex();
            }
            tessellator.draw();
            WorldRenderer worldRenderer2 = tessellator.getWorldRenderer();
            worldRenderer2.begin(9, DefaultVertexFormats.POSITION);
            GlStateManager.color(80.0F / 255.0F,124F / 255F, 1.0f, 1.0f);
            for(int i = 360;i > 0;i--){
                worldRenderer2.pos((float) ((getXCoord() + getWidth() - animationEngine.xCoord) + Math.cos((float)(i) * Math.PI / 180.0F) * 7), (float) ((getYCoord() + getHeight() / 2) + Math.sin((float)(i) * Math.PI / 180.0F) * 7),0.0F).endVertex();
            }
            tessellator.draw();
        } else {
            Gui.drawRect(getXCoord() + getWidth() - 30, (int) (getYCoord() + getHeight() * 0.35),getXCoord() + getWidth() - 15, (int) (getYCoord() + getHeight() * 0.68),2023529628);
            GlStateManager.color(156F / 255.0F,156F / 255F, 156F / 255F,120F / 255F);
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldRenderer = tessellator.getWorldRenderer();
            worldRenderer.begin(9, DefaultVertexFormats.POSITION);
            for(int i = 90;i > -90;i--){
                worldRenderer.pos((float) ((getXCoord() + getWidth() - 15) + Math.cos((float)(i) * Math.PI / 180.0F) * 4.5), (float) ((getYCoord() + getHeight() / 2) + Math.sin((float)(i) * Math.PI / 180.0F) * 4.5),0.0F).endVertex();
            }
            tessellator.draw();
            WorldRenderer worldRenderer1 = tessellator.getWorldRenderer();
            worldRenderer1.begin(9, DefaultVertexFormats.POSITION);
            for(int i = 270;i > 90;i--){
                worldRenderer1.pos((float) ((getXCoord() + getWidth() - 30) + Math.cos((float)(i) * Math.PI / 180.0F) * 4.5), (float) ((getYCoord() + getHeight() / 2) + Math.sin((float)(i) * Math.PI / 180.0F) * 4.5),0.0F).endVertex();
            }
            tessellator.draw();
            WorldRenderer worldRenderer2 = tessellator.getWorldRenderer();
            worldRenderer2.begin(9, DefaultVertexFormats.POSITION);
            GlStateManager.color(156F / 255.0F,156F / 255F, 156F / 255F,1.0F);
            for(int i = 360;i > 0;i--){
                worldRenderer2.pos((float) ((getXCoord() + getWidth() - animationEngine.xCoord) + Math.cos((float)(i) * Math.PI / 180.0F) * 7), (float) ((getYCoord() + getHeight() / 2) + Math.sin((float)(i) * Math.PI / 180.0F) * 7),0.0F).endVertex();
            }
            tessellator.draw();
        }
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    @Override
    public void onMousePress(int mouseX, int mouseY, EnumMouseButton button) {
        if(isMouseInRange(mouseX,mouseY)) {
            currentStatus = !currentStatus;
            valueSetter.accept(currentStatus);
            if(currentStatus){
                animationEngine.moveTo(15,0,0.3,AnimationEngine.EASE_OUT);
            } else {
                animationEngine.moveTo(30,0,0.3,AnimationEngine.EASE_OUT);
            }
        }
    }

    @Override
    public void onMouseRelease(int mouseX, int mouseY, EnumMouseButton button) {

    }

    @Override
    public void onMouseClickedMove(int mouseX, int mouseY, EnumMouseButton clickedMouseButton, long timeSinceLastClick) {

    }

    @Override
    public void onTyping(int key, char c) {

    }
}
