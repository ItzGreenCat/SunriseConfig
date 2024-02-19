package me.greencat.src.component.config;

import me.greencat.src.Translation;
import me.greencat.src.component.Component;
import me.greencat.src.component.EnumMouseButton;
import me.greencat.src.utils.render.Scissor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class EnumComponent extends Component<String> {

    String currentValue;
    public Class<? extends Enum<?>> enumClass;

    public int index = 0;
    public int maxIndex = 0;
    public EnumComponent(Supplier<String> baseValueGetter, Consumer<String> valueSetter,Class<? extends Enum<?>> enumClass) {
        super(baseValueGetter, valueSetter);
        this.enumClass = enumClass;
        currentValue = baseValueGetter.get();
        Arrays.stream(enumClass.getEnumConstants()).filter(it -> it.toString().equals(currentValue)).findFirst().ifPresent(anEnum -> index = anEnum.ordinal());
        maxIndex = enumClass.getEnumConstants().length - 1;
    }


    @Override
    public int getHeight() {
        return 30;
    }

    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        Gui.drawRect(getXCoord(),getYCoord(),getXCoord() + getWidth(),getHeight() + getYCoord(),-855310);
        Minecraft.getMinecraft().fontRendererObj.drawString(Translation.get(this.container.name + "." + this.name),getXCoord() + 3,getYCoord() + getHeight() / 2 - 5, Color.BLACK.getRGB());
        Scissor.enableScissor();
        Scissor.cut(getXCoord() + getWidth() - 153,getYCoord(),150,getHeight());
        Minecraft.getMinecraft().fontRendererObj.drawString(currentValue,getXCoord() + getWidth() - 74 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(currentValue) / 2,getYCoord() + getHeight() / 2 - 5,Color.BLACK.getRGB());
        Minecraft.getMinecraft().fontRendererObj.drawString("<",getXCoord() + getWidth() - 153,getYCoord() + getHeight() / 2 - 5,Color.BLACK.getRGB());
        Minecraft.getMinecraft().fontRendererObj.drawString(">",getXCoord() + getWidth() - 10,getYCoord() + getHeight() / 2 - 5,Color.BLACK.getRGB());
        Gui.drawRect(getXCoord() + getWidth() - 145,getYCoord() + getHeight() / 2 + 5,getXCoord() + getWidth() - 15,getYCoord() + getHeight() / 2 + 6,Color.BLACK.getRGB());
        Scissor.disableScissor();
    }

    @Override
    public void onMousePress(int mouseX, int mouseY, EnumMouseButton button) {
        if(isMouseInRange(mouseX,mouseY)) {
            if(mouseX >= getXCoord() + getWidth() - 158 && mouseY >= getYCoord() && mouseX < getXCoord() + getWidth() - 148 && mouseY < getYCoord() + getHeight()) {
                if (index + 1 > maxIndex) {
                    index = 0;
                } else {
                    index++;
                }
            }
            if(mouseX >= getXCoord() + getWidth() - 15 && mouseY >= getYCoord() && mouseX < getXCoord() + getWidth() - 5 && mouseY < getYCoord() + getHeight()) {
                if (index - 1 < 0) {
                    index = maxIndex;
                } else {
                    index--;
                }
            }
            currentValue = enumClass.getEnumConstants()[index].toString();
            valueSetter.accept(currentValue);
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
