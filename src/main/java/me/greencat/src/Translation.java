package me.greencat.src;

import java.util.HashMap;

public class Translation {
    private static final HashMap<String,String> translationMap = new HashMap<>();
    public static String get(String key){
        if(!translationMap.containsKey(key)){
            return key;
        }
        return translationMap.get(key);
    }
    public static void add(String key,String value){
        translationMap.put(key,value);
    }
}

