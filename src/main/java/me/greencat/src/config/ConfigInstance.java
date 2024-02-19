package me.greencat.src.config;

import me.greencat.src.component.CategoryComponent;
import me.greencat.src.component.ComponentContainer;
import me.greencat.src.component.EnumSpacialContainer;
import me.greencat.src.component.Screen;
import me.greencat.src.component.config.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConfigInstance {
    public final String name;
    public final LinkedHashMap<String,ConfigEntry> configList = new LinkedHashMap<>();
    public final ConfigureFileInstance fileInstance;
    public ConfigInstance(String name){
        this.name = name;
        fileInstance = new ConfigureFileInstance(name);
    }
    public void addConfig(EnumConfigType type,String category,String name,Object defaultValue){
        configList.put(name,new ConfigEntry(type,category,name,defaultValue));
    }
    public void addConfigLimit(EnumConfigType type,String category,String name,Object defaultValue,Object max,Object min){
        configList.put(name,new ConfigEntryLimit(type,category,name,defaultValue,max,min));
    }
    public void addConfigEnum(EnumConfigType type,String category,String name,Object defaultValue,Class<? extends Enum<?>> enumClass){
        configList.put(name,new ConfigEntryEnum(type,category,name,defaultValue,enumClass));
    }
    public Screen generateGui(){
        GlobalContainerCache.containerHashMap.clear();
        Screen screen = new Screen();
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        ComponentContainer categoryContainer = new ComponentContainer(0,0, (int) (resolution.getScaledWidth() * 0.182), resolution.getScaledHeight(), "THE_CATEGORY_CONTAINER",EnumSpacialContainer.CATEGORY);
        List<ComponentContainer> containerList = new ArrayList<>();
        for(Map.Entry<String,ConfigEntry> entry : configList.entrySet()){
            boolean hasSameCategory = false;
            for(ComponentContainer container : containerList){
                if(container.name.equals(entry.getValue().category)){
                    hasSameCategory = true;
                    break;
                }
            }
            if(!hasSameCategory) {
                containerList.add(new ComponentContainer((int) (resolution.getScaledWidth() * 0.182), 0, (int) (resolution.getScaledWidth() * 0.818), resolution.getScaledHeight(), entry.getValue().category, EnumSpacialContainer.CONFIG));
                categoryContainer.addComponent(new CategoryComponent(entry.getValue().category));
            }
        }
        for(Map.Entry<String,ConfigEntry> entry : configList.entrySet()){
            for(ComponentContainer container : containerList){
                if(container.name.equals(entry.getValue().category)){
                    if(entry.getValue().type == EnumConfigType.BOOLEAN){
                        BooleanComponent component = new BooleanComponent(() -> fileInstance.getBoolean(entry.getValue().category + "." + entry.getValue().name, (Boolean) entry.getValue().defaultValue),it -> fileInstance.setBoolean(entry.getValue().category + "." + entry.getValue().name,it, (Boolean) entry.getValue().defaultValue));
                        component.name = entry.getValue().name;
                        container.addComponent(component);
                    }
                    if(entry.getValue().type == EnumConfigType.STRING){
                        StringComponent component = new StringComponent(() -> fileInstance.getString(entry.getValue().category + "." + entry.getValue().name, (String) entry.getValue().defaultValue), it -> fileInstance.setString(entry.getValue().category + "." + entry.getValue().name,it, (String) entry.getValue().defaultValue));
                        component.name = entry.getValue().name;
                        container.addComponent(component);
                    }
                    if(entry.getValue().type == EnumConfigType.INTEGER){
                        IntegerComponent component = new IntegerComponent(() -> fileInstance.getInt(entry.getValue().category + "." + entry.getValue().name, (Integer) entry.getValue().defaultValue), it -> fileInstance.setInt(entry.getValue().category + "." + entry.getValue().name,it, (Integer)entry.getValue().defaultValue));
                        component.name = entry.getValue().name;
                        container.addComponent(component);
                    }
                    if(entry.getValue().type == EnumConfigType.DOUBLE){
                        DoubleComponent component = new DoubleComponent(() -> fileInstance.getDouble(entry.getValue().category + "." + entry.getValue().name, (Double) entry.getValue().defaultValue), it -> fileInstance.setDouble(entry.getValue().category + "." + entry.getValue().name,it, (Double) entry.getValue().defaultValue));
                        component.name = entry.getValue().name;
                        container.addComponent(component);
                    }
                    if(entry.getValue().type == EnumConfigType.ENUM){
                        EnumComponent component = new EnumComponent(() -> fileInstance.getString(entry.getValue().category + "." + entry.getValue().name,entry.getValue().defaultValue.toString()), it -> fileInstance.setString(entry.getValue().category + "." + entry.getValue().name,it, entry.getValue().defaultValue.toString()),((ConfigEntryEnum) entry.getValue()).enumClass);
                        component.name = entry.getValue().name;
                        container.addComponent(component);
                    }
                    if(entry.getValue().type == EnumConfigType.COLOR){
                        ColorComponent component = new ColorComponent(() -> fileInstance.getInt(entry.getValue().category + "." + entry.getValue().name, entry.getValue().defaultValue.equals(Color.BLACK) ? -2 : entry.getValue().defaultValue.equals(Color.WHITE) ? -1 : (int) (ColorComponent.rgbToHsv((Color) entry.getValue().defaultValue)[0] * 360.0F)), it -> fileInstance.setInt(entry.getValue().category + "." + entry.getValue().name,it,entry.getValue().defaultValue.equals(Color.BLACK) ? -2 : entry.getValue().defaultValue.equals(Color.WHITE) ? -1 : (int) (ColorComponent.rgbToHsv((Color) entry.getValue().defaultValue)[0] * 360.0F)));
                        component.name = entry.getValue().name;
                        container.addComponent(component);
                    }
                    if(entry.getValue().type == EnumConfigType.LIMIT_DOUBLE && entry.getValue() instanceof ConfigEntryLimit){
                        LimitDoubleComponent component = new LimitDoubleComponent(() -> fileInstance.getDouble(entry.getValue().category + "." + entry.getValue().name, (Double) entry.getValue().defaultValue), it -> fileInstance.setDouble(entry.getValue().category + "." + entry.getValue().name,it, (Double) entry.getValue().defaultValue),(Double)((ConfigEntryLimit) entry.getValue()).maxValue,(Double)((ConfigEntryLimit) entry.getValue()).minValue);
                        component.name = entry.getValue().name;
                        container.addComponent(component);
                    }
                    if(entry.getValue().type == EnumConfigType.LIMIT_INTEGER && entry.getValue() instanceof ConfigEntryLimit){
                        LimitIntegerComponent component = new LimitIntegerComponent(() -> fileInstance.getInt(entry.getValue().category + "." + entry.getValue().name, (Integer) entry.getValue().defaultValue), it -> fileInstance.setInt(entry.getValue().category + "." + entry.getValue().name,it, (Integer) entry.getValue().defaultValue),(Integer)((ConfigEntryLimit) entry.getValue()).maxValue,(Integer)((ConfigEntryLimit) entry.getValue()).minValue);
                        component.name = entry.getValue().name;
                        container.addComponent(component);
                    }
                }
            }
        }
        containerList.forEach(it -> GlobalContainerCache.containerHashMap.put(it.name,it));
        screen.addContainer(categoryContainer);
        screen.addContainer(containerList.get(0));
        return screen;
    }
    public static class ConfigEntry{
        public final EnumConfigType type;
        public final String category;
        public final String name;
        public final Object defaultValue;
        public ConfigEntry(EnumConfigType type,String category,String name,Object defaultValue){
            this.type = type;
            this.category = category;
            this.name = name;
            this.defaultValue = defaultValue;
        }
    }
    public static class ConfigEntryLimit extends ConfigEntry{
        public final Object maxValue;
        public final Object minValue;
        public ConfigEntryLimit(EnumConfigType type,String category,String name,Object defaultValue,Object maxValue,Object minValue){
            super(type,category,name,defaultValue);
            this.maxValue = maxValue;
            this.minValue = minValue;
        }
    }
    public static class ConfigEntryEnum extends ConfigEntry{
        public final Class<? extends Enum<?>> enumClass;

        public ConfigEntryEnum(EnumConfigType type, String category, String name, Object defaultValue,Class<? extends Enum<?>> enumClass) {
            super(type, category, name, defaultValue);
            this.enumClass = enumClass;
        }
    }
    public Boolean getBoolean(String name){
        ConfigEntry entry = configList.get(name);
        return fileInstance.getBoolean(entry.category + "." + name, (Boolean) entry.defaultValue);
    }
    public String getString(String name){
        ConfigEntry entry = configList.get(name);
        return fileInstance.getString(entry.category + "." + name, (String) entry.defaultValue);
    }
    public Integer getInteger(String name){
        ConfigEntry entry = configList.get(name);
        return fileInstance.getInt(entry.category + "." + name, (Integer) entry.defaultValue);
    }
    public Double getDouble(String name){
        ConfigEntry entry = configList.get(name);
        return fileInstance.getDouble(entry.category + "." + name, (Double) entry.defaultValue);
    }

}
