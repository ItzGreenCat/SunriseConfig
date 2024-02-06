package me.greencat.src.component;

import me.greencat.src.config.GlobalContainerCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.MathHelper;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.awt.*;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CategoryComponent extends Component<String>{
    public final String name;
    public CategoryComponent(String name) {
        super(() -> "",(it) -> {});
        this.name = name;
    }

    @Override
    public int getHeight() {
        return 30;
    }

    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        boolean selected = false;
        for(ComponentContainer container : this.container.parent.containers){
            if(container.name.equals(name)){
                selected = true;
                break;
            }
        }
        Gui.drawRect(this.getXCoord(),this.getYCoord(),this.getXCoord() + this.getWidth(),this.getYCoord() + this.getHeight(),Color.WHITE.getRGB());
        Gui.drawRect(this.getXCoord() + this.getWidth() - 2,this.getYCoord(),this.getXCoord() + this.getWidth(),this.getYCoord() + this.getHeight(),-2697514);
        if(selected){
            Gui.drawRect(this.getXCoord(),this.getYCoord() + 2,this.getXCoord() + this.getWidth() - 18,this.getYCoord() + this.getHeight() - 2,2019213055);
            GlStateManager.color(90.0F / 255.0F,190F / 255F, 1.0f,120F / 255F);
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldRenderer = tessellator.getWorldRenderer();
            worldRenderer.begin(9, DefaultVertexFormats.POSITION);
            for(int i = 90;i > -90;i--){
                worldRenderer.pos((float) ((getXCoord() + getWidth() - 18) + Math.cos((float)(i) * Math.PI / 180.0F) * 13), (float) ((getYCoord() + getHeight() / 2) + Math.sin((float)(i) * Math.PI / 180.0F) * 13),0.0F).endVertex();
            }
            tessellator.draw();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
        }
        if(isMouseInRange(mouseX,mouseY)){
            Gui.drawRect(this.getXCoord(),this.getYCoord() + 2,this.getXCoord() + this.getWidth() - 18,this.getYCoord() + this.getHeight() - 2,2023529628);
            GlStateManager.color(156F / 255.0F,156F / 255F, 156F / 255F,120F / 255F);
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldRenderer = tessellator.getWorldRenderer();
            worldRenderer.begin(9, DefaultVertexFormats.POSITION);
            for(int i = 90;i > -90;i--){
                worldRenderer.pos((float) ((getXCoord() + getWidth() - 18) + Math.cos((float)(i) * Math.PI / 180.0F) * 13), (float) ((getYCoord() + getHeight() / 2) + Math.sin((float)(i) * Math.PI / 180.0F) * 13),0.0F).endVertex();
            }
            tessellator.draw();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
        }
        Minecraft.getMinecraft().fontRendererObj.drawString(name,this.getXCoord() + 10,this.getYCoord() + getHeight() / 2 - 4,selected ? -11503361 : Color.BLACK.getRGB());
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    @Override
    public void onMousePress(int mouseX, int mouseY, EnumMouseButton button) {
        if(!isMouseInRange(mouseX,mouseY))
            return;
        Optional<ComponentContainer> optional = this.container.parent.containers.stream().filter(it -> it != this.container).findFirst();
        optional.ifPresent(componentContainer -> this.container.parent.containers.remove(componentContainer));
        this.container.parent.addContainer(GlobalContainerCache.containerHashMap.get(name));
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
