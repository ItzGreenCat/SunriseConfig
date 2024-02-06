package me.greencat.src;

import me.greencat.src.component.*;
import me.greencat.src.config.ConfigInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.util.LinkedHashMap;
import java.util.Map;

public class RootScreen extends ComponentContainer {
    public RootScreen(LinkedHashMap<String, ConfigInstance> instances) {
        super(0,0, (int) (new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() * 0.182),new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight(), "THE_CATEGORY_CONTAINER",EnumSpacialContainer.CATEGORY);
        for(Map.Entry<String,ConfigInstance> instance : instances.entrySet()){
            RootComponent component = new RootComponent(instance.getKey(),instance.getValue());
            this.addComponent(component);
        }
    }
    private static class RootComponent extends CategoryComponent {
        private final ConfigInstance instance;
        public RootComponent(String name,ConfigInstance instance) {
            super(name);
            this.instance = instance;
        }
        @Override
        public void onMousePress(int mouseX, int mouseY, EnumMouseButton button) {
            if (!isMouseInRange(mouseX, mouseY))
                return;
            Screen screen = instance.generateGui();
            ScreenWrapper wrapper = new ScreenWrapper(screen);
            Minecraft.getMinecraft().displayGuiScreen(wrapper);
        }
    }
    public static class TipScreen extends ComponentContainer {
        public TipScreen() {
            super((int) (new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() * 0.182), 0, (int) (new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() * 0.818), new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight(),"TIP_CONTAINER", EnumSpacialContainer.CONFIG);
        }
        @Override
        public void render(int mouseX,int mouseY,float tickDelta){
            super.render(mouseX,mouseY,tickDelta);
            drawStringScaled("轻触一个类别以开始",this.x + this.width / 2.0F - ((float) Minecraft.getMinecraft().fontRendererObj.getStringWidth("轻触一个类别以开始") / 2.0F * 3.0F),this.y + this.height / 2.0F - 4 * 3,0,3);
        }
        public static void drawStringScaled(String str, float x, float y, int colour, float scale) {
            GlStateManager.scale(scale, scale, 1);
            Minecraft.getMinecraft().fontRendererObj.drawString(str, (int) (x / scale), (int) (y / scale), colour);
            GlStateManager.scale(1 / scale, 1 / scale, 1);
        }
    }
}
