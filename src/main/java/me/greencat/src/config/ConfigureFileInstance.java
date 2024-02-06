package me.greencat.src.config;

import me.greencat.src.ScreenManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.HashMap;

public class ConfigureFileInstance {
    public final String configName;
    public final Configuration configFile;
    public final ConfigureCache cache = new ConfigureCache();

    public ScreenManager screenManager = null;

    public ConfigureFileInstance(String configName) {
        this.configName = configName;
        this.configFile = new Configuration(new File(Minecraft.getMinecraft().mcDataDir, configName + ".cfg"));
    }

    //Common settings
    public void setBoolean(String key, Boolean value, Boolean defaultValue) {
        configFile.get(Configuration.CATEGORY_GENERAL, key, defaultValue, "").set(value);
        configFile.save();
        cache.removeConfigFromCache(key);
        configFile.load();
        if (screenManager != null) {
            screenManager.makeDirty(configName, key, value);
        }
    }

    public boolean getBoolean(String key, Boolean defaultValue) {
        boolean cache = this.cache.configureCache.containsKey(key);
        if (cache) {
            return (boolean) this.cache.getConfigFromCache(key);
        }
        boolean status = configFile.get(Configuration.CATEGORY_GENERAL, key, defaultValue, "").getBoolean();
        configFile.load();
        this.cache.addConfigToCache(key, status);
        return status;
    }

    public void setString(String key, String value, String defaultValue) {
        configFile.get(Configuration.CATEGORY_GENERAL, key, defaultValue, "").set(value);
        configFile.save();
        cache.removeConfigFromCache(key);
        configFile.load();
        if (screenManager != null) {
            screenManager.makeDirty(configName, key, value);
        }
    }

    public String getString(String key, String defaultValue) {
        boolean cache = this.cache.configureCache.containsKey(key);
        if (cache) {
            return (String) this.cache.getConfigFromCache(key);
        }
        String str = configFile.get(Configuration.CATEGORY_GENERAL, key, defaultValue, "").getString();
        configFile.load();
        this.cache.addConfigToCache(key, str);
        return str;
    }

    public void setInt(String key, int value, int defaultValue) {
        configFile.get(Configuration.CATEGORY_GENERAL, key, defaultValue, "").set(value);
        configFile.save();
        cache.removeConfigFromCache(key);
        configFile.load();
        if (screenManager != null) {
            screenManager.makeDirty(configName, key, value);
        }
    }

    public int getInt(String key, int defaultValue) {
        boolean cache = this.cache.configureCache.containsKey(key);
        if (cache) {
            return (int) this.cache.getConfigFromCache(key);
        }
        int number = configFile.get(Configuration.CATEGORY_GENERAL, key, defaultValue, "").getInt();
        configFile.load();
        this.cache.addConfigToCache(key, number);
        return number;
    }

    public void setDouble(String key, double value, double defaultValue) {
        configFile.get(Configuration.CATEGORY_GENERAL, key, defaultValue, "").set(value);
        configFile.save();
        cache.removeConfigFromCache(key);
        configFile.load();
        if (screenManager != null) {
            screenManager.makeDirty(configName, key, value);
        }
    }

    public double getDouble(String key, double defaultValue) {
        boolean cache = this.cache.configureCache.containsKey(key);
        if (cache) {
            return (double) this.cache.getConfigFromCache(key);
        }
        double number = configFile.get(Configuration.CATEGORY_GENERAL, key, defaultValue, "").getDouble();
        configFile.load();
        this.cache.addConfigToCache(key, number);
        return number;
    }

    public static class ConfigureCache {
        public final HashMap<String, Object> configureCache = new HashMap<>();

        public void addConfigToCache(String id, Object value) {
            configureCache.put(id, value);
        }

        public void removeConfigFromCache(String id) {
            configureCache.remove(id);
        }

        public Object getConfigFromCache(String id) {
            return configureCache.get(id);
        }
    }

}
