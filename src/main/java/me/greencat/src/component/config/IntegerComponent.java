package me.greencat.src.component.config;

import me.greencat.src.component.EnumMouseButton;
import me.greencat.src.component.InputComponent;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class IntegerComponent extends InputComponent<Integer> {
    String currentValue;
    public IntegerComponent(Supplier<Integer> baseValueGetter, Consumer<Integer> valueSetter) {
        super(baseValueGetter, valueSetter);
        currentValue = String.valueOf(baseValueGetter.get());
        field.setText(currentValue);
    }

    @Override
    public int getHeight() {
        return 30;
    }

    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        super.render(getXCoord(),getYCoord(),tickDelta);
    }

    @Override
    public void onMouseRelease(int mouseX, int mouseY, EnumMouseButton button) {

    }

    @Override
    public void onMouseClickedMove(int mouseX, int mouseY, EnumMouseButton clickedMouseButton, long timeSinceLastClick) {

    }

    @Override
    public void onTyping(int key, char c) {
       super.onTyping(key,c);
        try{
            currentValue = getText();
            valueSetter.accept(Integer.parseInt(currentValue));
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
