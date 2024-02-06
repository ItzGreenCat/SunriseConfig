package me.greencat.src.component;

import me.greencat.src.component.Component;
import me.greencat.src.component.EnumMouseButton;
import me.greencat.src.utils.render.Scissor;
import me.greencat.src.utils.widget.TransparentTextField;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class InputComponent<T> extends Component<T> {
    public TransparentTextField field = new TransparentTextField(0, Minecraft.getMinecraft().fontRendererObj);
    public boolean isFocus = false;
    public InputComponent(Supplier<T> baseValueGetter, Consumer<T> valueSetter) {
        super(baseValueGetter, valueSetter);
    }
    @Override
    public void onMousePress(int mouseX, int mouseY, EnumMouseButton button) {
        isFocus = isMouseInRange(mouseX, mouseY);
        field.setFocused(isFocus);
    }
    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        Gui.drawRect(getXCoord(),getYCoord(),getXCoord() + getWidth(),getHeight() + getYCoord(),-855310);
        Minecraft.getMinecraft().fontRendererObj.drawString(this.name,getXCoord() + 3,getYCoord() + getHeight() / 2 - 5,Color.BLACK.getRGB());
        Scissor.enableScissor();
        Scissor.cut(getXCoord() + getWidth() - 153,getYCoord(),150,getHeight());
        field.drawText(getXCoord() + getWidth() - 150,getYCoord() + getHeight() / 2 - 5,Color.BLACK);
        Gui.drawRect(getXCoord() + getWidth() - 153,getYCoord() + getHeight() / 2 + 5,getXCoord() + getWidth() - 5,getYCoord() + getHeight() / 2 + 6,Color.BLACK.getRGB());
        Scissor.disableScissor();
    }
    public String getText(){
        return field.getText();
    }
    @Override
    public void onTyping(int key, char c) {
        if (isFocus) {
            field.textboxKeyTyped(c,key);
        }
    }

}
