package me.greencat.src.component;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class SlideComponent<T> extends Component<T>{
    public boolean isFocused = false;
    private float value = 0.0F;
    public SlideComponent(Supplier<T> baseValueGetter, Consumer<T> valueSetter) {
        super(baseValueGetter, valueSetter);
    }

    @Override
    public void onMousePress(int mouseX, int mouseY, EnumMouseButton button) {
        if(isMouseInRange(mouseX,mouseY) && mouseX >= (getXCoord() + getWidth() - 300.0F)){
            isFocused = true;
            float originValue = (mouseX - (getXCoord() + getWidth() - 300.0F)) / 290.0F;
            value = Math.max(0, Math.min(originValue, 1));
        }
    }

    @Override
    public void onMouseRelease(int mouseX, int mouseY, EnumMouseButton button) {
        if (isFocused) {
            isFocused = false;
        }
    }

    @Override
    public void onMouseClickedMove(int mouseX, int mouseY, EnumMouseButton clickedMouseButton, long timeSinceLastClick) {
        if(isFocused && mouseX >= (getXCoord() + getWidth() - 300.0F)) {
            float originValue = (mouseX - (getXCoord() + getWidth() - 300.0F)) / 290.0F;
            value = Math.max(0, Math.min(originValue, 1));
        }
    }
    public float getValue(){
        return value;
    }
    public void setValue(float value){
        this.value = value;
    }
}
