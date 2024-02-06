package me.greencat.src;

import me.greencat.src.utils.ClassCategory;
import me.greencat.src.utils.LimitConfigEntry;

@ClassCategory("test")
public class SunriseConfig {
    @LimitConfigEntry(max = "200",min = "20")
    public static Integer intTest = 60;
    @LimitConfigEntry(max = "30.3",min = "0.5")
    public static Double doubleTest = 20D;
}
