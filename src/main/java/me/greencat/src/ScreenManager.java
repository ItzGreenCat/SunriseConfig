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
import java.util.Map;
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
    }
    public void addConfigLimit(String instanceName,EnumConfigType type,String category,String name,Object defaultValue,Object max,Object min){
        instances.get(instanceName).addConfigLimit(type,category,name,defaultValue,max,min);
    }
    public void addConfigEnum(String instanceName,EnumConfigType type,String category,String name,Object defaultValue,Class<? extends Enum<?>> enumClass){
        instances.get(instanceName).addConfigEnum(type,category,name,defaultValue,enumClass);
    }
    public void autoAdd(Class<?> clazz0){
        try{
            AtomicReference<String> classCategory = new AtomicReference<>("Default");
            if(clazz0.isAnnotationPresent(ClassCategory.class)){
                Optional<Annotation> optional = Arrays.stream(clazz0.getAnnotations()).filter(it -> it instanceof ClassCategory).findFirst();
                optional.ifPresent(it -> classCategory.set(((ClassCategory) it).value()));
            }
            String classCategoryString = classCategory.get();
            String classSimpleName = clazz0.getSimpleName();
            Class<?> clazz = clazz0;
            while(true) {
                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(ConfigEntry.class)) {
                        if (!instances.containsKey(classCategoryString)) {
                            createInstance(classCategoryString);
                        }
                        if (field.getType() == String.class) {
                            addConfig(classCategoryString, EnumConfigType.STRING, classSimpleName, field.getName(), field.get(null));
                            navigate.put(classCategoryString + "." + classSimpleName + "." + field.getName(), field);
                        }
                        if (field.getType() == Boolean.class) {
                            addConfig(classCategoryString, EnumConfigType.BOOLEAN, classSimpleName, field.getName(), field.get(null));
                            navigate.put(classCategoryString + "." + classSimpleName + "." + field.getName(), field);
                        }
                        if (field.getType() == Integer.class) {
                            addConfig(classCategoryString, EnumConfigType.INTEGER, classSimpleName, field.getName(), field.get(null));
                            navigate.put(classCategoryString + "." + classSimpleName + "." + field.getName(), field);
                        }
                        if (field.getType() == Double.class) {
                            addConfig(classCategoryString, EnumConfigType.DOUBLE, classSimpleName, field.getName(), field.get(null));
                            navigate.put(classCategoryString + "." + classSimpleName + "." + field.getName(), field);
                        }
                        if (field.getType().isEnum()) {
                            addConfigEnum(classCategoryString, EnumConfigType.ENUM, classSimpleName, field.getName(), field.get(null), (Class<? extends Enum<?>>) field.getType());
                            navigate.put(classCategoryString + "." + classSimpleName + "." + field.getName(), field);
                        }
                        ConfigEntry annotation = field.getAnnotation(ConfigEntry.class);
                        if(!annotation.value().equals("dummyThing")){
                            Translation.add(classSimpleName + "." + field.getName(),annotation.value());
                        } else {
                            Translation.add(classSimpleName + "." + field.getName(),field.getName());
                        }
                    }
                    if (field.isAnnotationPresent(LimitConfigEntry.class)) {
                        if (!instances.containsKey(classCategoryString)) {
                            createInstance(classCategoryString);
                        }
                        LimitConfigEntry annotation = field.getAnnotation(LimitConfigEntry.class);
                        if(!annotation.value().equals("dummyThing")){
                            Translation.add(classSimpleName + "." + field.getName(),annotation.value());
                        } else {
                            Translation.add(classSimpleName + "." + field.getName(),field.getName());
                        }
                        if (field.getType() == Integer.class) {
                            addConfigLimit(classCategoryString, EnumConfigType.LIMIT_INTEGER, classSimpleName, field.getName(), field.get(null), Integer.parseInt(annotation.max()), Integer.parseInt(annotation.min()));
                            navigate.put(classCategoryString + "." + classSimpleName + "." + field.getName(), field);
                        }
                        if (field.getType() == Double.class) {
                            addConfigLimit(classCategoryString, EnumConfigType.LIMIT_DOUBLE, classSimpleName, field.getName(), field.get(null), Double.parseDouble(annotation.max()), Double.parseDouble(annotation.min()));
                            navigate.put(classCategoryString + "." + classSimpleName + "." + field.getName(), field);
                        }
                    }
                }
                if(clazz.getSuperclass().getSimpleName().equals("java.lang.Object")){
                    break;
                } else {
                    clazz = clazz.getSuperclass();
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

    public void postInitialization(){
        for(Map.Entry<String,ConfigInstance> instance : instances.entrySet()){
            ConfigInstance config = instance.getValue();
            for(ConfigInstance.ConfigEntry entry : config.configList.values()){
                makeDirty(config.fileInstance.configName,entry.category + "." + entry.name,entry.type == EnumConfigType.BOOLEAN ? config.fileInstance.getBoolean(entry.category + "." + entry.name, (Boolean) entry.defaultValue) : (entry.type == EnumConfigType.STRING || entry.type == EnumConfigType.ENUM) ? entry.type == EnumConfigType.STRING ? config.fileInstance.getString(entry.category + "." + entry.name, (String) entry.defaultValue) : getEnumByEnumClass(((ConfigInstance.ConfigEntryEnum)entry).enumClass,config.fileInstance.getString(entry.category + "." + entry.name,entry.defaultValue.toString())) : (entry.type == EnumConfigType.INTEGER || entry.type == EnumConfigType.LIMIT_INTEGER) ? config.fileInstance.getInt(entry.category + "." + entry.name, (Integer) entry.defaultValue) : (entry.type == EnumConfigType.DOUBLE || entry.type == EnumConfigType.LIMIT_DOUBLE) ? config.fileInstance.getDouble(entry.category + "." + entry.name, (Double) entry.defaultValue) : null);
            }
        }
    }
    public Enum<?> getEnumByEnumClass(Class<? extends Enum<?>> enumClass,String name){
        for(Enum<?> enumValue : enumClass.getEnumConstants()){
            if(enumValue.toString().equals(name)){
                return enumValue;
            }
        }
        return null;
    }
    public void makeDirty(String configName,String name,Object value){
        if(instances.get(configName).configList.get(name.split("\\.")[1]).type == EnumConfigType.ENUM){
            Optional<? extends Enum<?>> enumOptional = Arrays.stream(((ConfigInstance.ConfigEntryEnum) instances.get(configName).configList.get(name.split("\\.")[1])).enumClass.getEnumConstants()).filter(it -> it.toString().equals(value.toString())).findFirst();
            if(enumOptional.isPresent()){
                Enum<?> enumValue = enumOptional.get();
                Field field = navigate.get(configName + "." + name);
                field.setAccessible(true);
                try {
                    field.set(null, enumValue);
                }   catch(Exception | Error ignored){}
            }
            return;
        }
        try {
            Field field = navigate.get(configName + "." + name);
            field.setAccessible(true);
            field.set(null, value);
        } catch(Exception | Error ignored){}
    }
}
