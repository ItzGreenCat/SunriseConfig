package me.greencat.src;

import me.greencat.src.animation.AnimationManager;
import net.minecraftforge.common.MinecraftForge;

public class SunriseConfigBootstrap {
    private static boolean isBooted = false;
    public static void bootstrap(){
        if(isBooted) {
            return;
        }
        MinecraftForge.EVENT_BUS.register(new AnimationManager());
        isBooted = true;
    }
}
