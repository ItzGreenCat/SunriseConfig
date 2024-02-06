package me.greencat.src;

import me.greencat.src.component.Screen;
import me.greencat.src.component.ScreenWrapper;
import me.greencat.src.config.ConfigInstance;
import me.greencat.src.config.EnumConfigType;
import me.greencat.src.utils.ClassCategory;
import me.greencat.src.utils.ConfigEntry;
import me.greencat.src.utils.LimitConfigEntry;
import net.minecraft.client.Minecraft;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScreenManager {
    LinkedHashMap<String,ConfigInstance> instances = new LinkedHashMap<>();
    LinkedHashMap<String, Field> navigate = new LinkedHashMap<>();//Key:config + (category + name)
    public void createInstance(String name){
        ConfigInstance instance = new ConfigInstance(name);
        instance.fileInstance.screenManager = this;
        instances.put(name,instance);
    }
    public void addConfig(String instanceName,EnumConfigType type, String category, String name, Object defaultValue){
        instances.get(instanceName).addConfig(type,category,name,defaultValue);
        Logger.getGlobal().log(Level.INFO,"added" + name + "to" + instanceName);
    }
    public void addConfigLimit(String instanceName,EnumConfigType type,String category,String name,Object defaultValue,Object max,Object min){
        instances.get(instanceName).addConfigLimit(type,category,name,defaultValue,max,min);
        Logger.getGlobal().log(Level.INFO,"added" + name + "to" + instanceName);
    }
    public void autoAdd(Class<?> clazz){
        try{
            AtomicReference<String> classCategory = new AtomicReference<>("Default");
            if(clazz.isAnnotationPresent(ClassCategory.class)){
                Optional<Annotation> optional = Arrays.stream(clazz.getAnnotations()).filter(it -> it instanceof  ClassCategory).findFirst();
                optional.ifPresent(it -> classCategory.set(((ClassCategory) it).value()));
            }
            String classCategoryString = classCategory.get();
            for(Field field : clazz.getDeclaredFields()){
                field.setAccessible(true);
                if(field.isAnnotationPresent(ConfigEntry.class)){
                    if(!instances.containsKey(classCategoryString)){
                        createInstance(classCategoryString);
                    }
                    if(field.getType() == String.class){
                        addConfig(classCategoryString,EnumConfigType.STRING, clazz.getSimpleName(), field.getName(),field.get(null));
                        navigate.put(classCategoryString + "." + clazz.getSimpleName() + "." + field.getName(),field);
                    }
                    if(field.getType() == Boolean.class){
                        addConfig(classCategoryString,EnumConfigType.BOOLEAN, clazz.getSimpleName(), field.getName(),field.get(null));
                        navigate.put(classCategoryString + "." + clazz.getSimpleName() + "." + field.getName(),field);
                    }
                    if(field.getType() == Integer.class){
                        addConfig(classCategoryString,EnumConfigType.INTEGER, clazz.getSimpleName(), field.getName(),field.get(null));
                        navigate.put(classCategoryString + "." + clazz.getSimpleName() + "." + field.getName(),field);
                    }
                    if(field.getType() == Double.class){
                        addConfig(classCategoryString,EnumConfigType.DOUBLE, clazz.getSimpleName(), field.getName(),field.get(null));
                        navigate.put(classCategoryString + "." + clazz.getSimpleName() + "." + field.getName(),field);
                    }
                }
                if(field.isAnnotationPresent(LimitConfigEntry.class)){
                    if(!instances.containsKey(classCategoryString)){
                        createInstance(classCategoryString);
                    }
                    LimitConfigEntry annotation = field.getAnnotation(LimitConfigEntry.class);
                    if(field.getType() == Integer.class){
                        addConfigLimit(classCategoryString,EnumConfigType.LIMIT_INTEGER, clazz.getSimpleName(), field.getName(),field.get(null),Integer.parseInt(annotation.max()),Integer.parseInt(annotation.min()));
                        navigate.put(classCategoryString + "." + clazz.getSimpleName() + "." + field.getName(),field);
                    }
                    if(field.getType() == Double.class){
                        addConfigLimit(classCategoryString,EnumConfigType.LIMIT_DOUBLE, clazz.getSimpleName(), field.getName(),field.get(null),Double.parseDouble(annotation.max()),Double.parseDouble(annotation.min()));
                        navigate.put(classCategoryString + "." + clazz.getSimpleName() + "." + field.getName(),field);
                    }
                }
            }
        } catch(Exception | Error ignored){}
    }
    public void display(){
        Screen screen = new Screen();
        screen.addContainer(new RootScreen(instances));
        screen.addContainer(new RootScreen.TipScreen());
        ScreenWrapper wrapper = new ScreenWrapper(screen);
        Minecraft.getMinecraft().displayGuiScreen(wrapper);
    }
    public void makeDirty(String configName,String name,Object value){
        try {
            Field field = navigate.get(configName + "." + name);
            field.setAccessible(true);
            field.set(null, value);
        } catch(Exception | Error ignored){}
    }
}
