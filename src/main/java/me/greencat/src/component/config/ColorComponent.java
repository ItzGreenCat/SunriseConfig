package me.greencat.src.component.config;

import me.greencat.src.Translation;
import me.greencat.src.component.EnumMouseButton;
import me.greencat.src.component.SlideComponent;
import me.greencat.src.utils.render.RectUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ColorComponent extends SlideComponent<Integer> {
    public int max;
    public int min;
    public int mouseClickX = 0;
    public ColorComponent(Supplier<Integer> baseValueGetter, Consumer<Integer> valueSetter) {
        super(baseValueGetter, valueSetter);
        this.max = 360;
        this.min = 0;
        if(baseValueGetter.get() < 0) {
            setValue(baseValueGetter.get());
        } else {
            setValue(((float) (baseValueGetter.get() - min)) / ((float) (max - min)));
        }
    }

    @Override
    public int getHeight() {
        return 30;
    }

    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        Gui.drawRect(getXCoord(),getYCoord(),getXCoord() + getWidth(),getHeight() + getYCoord(),-855310);
        float perBlock = 0.805F;
        for(int i = 0;i < 360;i++){
            Color color = Color.getHSBColor(((float)i) / 360.0F,1.0F,1.0F);
            Gui.drawRect((int) (getXCoord() + getWidth() - 300 + (i * perBlock)),getYCoord() + getHeight() / 2 - 5, (int) (getXCoord() + getWidth() - 300 + ((i + 1) * perBlock)),getYCoord() + getHeight() / 2 + 5,color.getRGB());
        }

        RectUtil.drawOutlinedRoundedRect(getXCoord() + getWidth() - 300, getYCoord() + getHeight() / 2.0F - 5, 289.8F, 10, 4, 2.5F, -855310);
        RectUtil.drawRoundRect(getXCoord() + getWidth() - 320, getYCoord() + getHeight() / 2.0F - 5, getXCoord() + getWidth() - 310, getYCoord() + getHeight() / 2.0F + 5, 3, Color.WHITE.getRGB());
        if(getValue() == -1) {
            RectUtil.drawOutlinedRoundedRect(getXCoord() + getWidth() - 320, getYCoord() + getHeight() / 2.0F - 5, 10, 10,4,2.5F,-11503361);
        }
        RectUtil.drawRoundRect(getXCoord() + getWidth() - 340,getYCoord() + getHeight() / 2.0F - 5,getXCoord() + getWidth() - 330,getYCoord() + getHeight() / 2.0F + 5,3,Color.BLACK.getRGB());
        if(getValue() == -2) {
            RectUtil.drawOutlinedRoundedRect(getXCoord() + getWidth() - 340, getYCoord() + getHeight() / 2.0F - 5, 10, 10,4,2.5F,-11503361);
        }
        if(getValue() >= 0) {
            RectUtil.drawBorderedRoundedRect((getXCoord() + getWidth() - 10 - (290 * (1 - getValue()))) - 1F, getYCoord() + getHeight() / 2.0F - 7, 3.5F, 14F, 2.0F, 1.5F, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        }
        GlStateManager.resetColor();
        Minecraft.getMinecraft().fontRendererObj.drawString(Translation.get(this.container.name + "." + this.name),getXCoord() + 3,getYCoord() + getHeight() / 2 - 5, Color.BLACK.getRGB());
    }
    @Override
    public void onMouseClickedMove(int mouseX, int mouseY, EnumMouseButton clickedMouseButton, long timeSinceLastClick) {
        if(mouseClickX <= getXCoord() + getWidth() - 300){
            return;
        }
        super.onMouseClickedMove(mouseX,mouseY,clickedMouseButton,timeSinceLastClick);
        valueSetter.accept((int) ((max - min) * getValue() + min));
    }
    @Override
    public void onMousePress(int mouseX, int mouseY, EnumMouseButton clickedMouseButton){
        super.onMousePress(mouseX,mouseY,clickedMouseButton);
        mouseClickX = mouseX;
        valueSetter.accept((int) ((max - min) * getValue() + min));
        if(mouseX >= getXCoord() + getWidth() - 320 && mouseY >= getYCoord() && mouseX < getXCoord() + getWidth() - 310 && mouseY < getYCoord() + getHeight()){
            setValue(-1);
            valueSetter.accept(-1);
        }
        if(mouseX >= getXCoord() + getWidth() - 340 && mouseY >= getYCoord() && mouseX < getXCoord() + getWidth() - 330 && mouseY < getYCoord() + getHeight()){
            setValue(-2);
            valueSetter.accept(-2);
        }
    }

    @Override
    public void onTyping(int key, char c) {

    }
    public static float[] rgbToHsv(Color color) {
        int R = color.getRed();
        int G = color.getGreen();
        int B = color.getBlue();
        float R_1 = R / 255f;
        float G_1 = G / 255f;
        float B_1 = B / 255f;
        float[] all = {R_1, G_1, B_1};
        float max = all[0];
        float min = all[0];
        for (float v : all) {
            if (max <= v) {
                max = v;
            }
            if (min >= v) {
                min = v;
            }
        }
        float C_max = max;
        float C_min = min;
        float diff = C_max - C_min;
        float hue = 0f;
        if (diff == 0f) {
            hue = 0f;
        } else {
            if (C_max == R_1) {
                hue = (((G_1 - B_1) / diff) % 6) * 60f;
            }
            if (C_max == G_1) {
                hue = (((B_1 - R_1) / diff) + 2f) * 60f;
            }
            if (C_max == B_1) {
                hue = (((R_1 - G_1) / diff) + 4f) * 60f;
            }
        }
        float saturation;
        if (C_max == 0f) {
            saturation = 0f;
        } else {
            saturation = diff / C_max;
        }
        return new float[]{hue, saturation, C_max};
    }
}
