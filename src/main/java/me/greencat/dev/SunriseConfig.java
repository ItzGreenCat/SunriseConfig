package me.greencat.dev;

import me.greencat.src.utils.ClassCategory;
import me.greencat.src.utils.ConfigEntry;
import me.greencat.src.utils.LimitConfigEntry;

@ClassCategory("test1")
public class SunriseConfig {
    @LimitConfigEntry(max = "200",min = "20",value = "滑动1Integer")
    public static Integer intTest = 60;
    @LimitConfigEntry(max = "30.3",min = "0.5",value = "滑动2Double")
    public static Double doubleTest = 20D;
    @ConfigEntry("这是Double")
    public static Double a = 231515D;
}
