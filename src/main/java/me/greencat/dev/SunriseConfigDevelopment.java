package me.greencat.dev;

import me.greencat.src.ScreenManager;
import me.greencat.src.SunriseConfig;
import me.greencat.src.component.Screen;
import me.greencat.src.component.ScreenWrapper;
import me.greencat.src.config.ConfigInstance;
import me.greencat.src.config.EnumConfigType;
import me.greencat.src.utils.ClassCategory;
import me.greencat.src.utils.ConfigEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

@ClassCategory("test")
@Mod(modid = SunriseConfigDevelopment.MODID, version = SunriseConfigDevelopment.VERSION,name = "SunriseConfigDevelopmentTestModFile")
public class SunriseConfigDevelopment
{
    public static final String MODID = "sunrisedevelopment";
    public static final String VERSION = "1.0";
    KeyBinding keyBinding = new KeyBinding("SunriseTest", Keyboard.KEY_F12,"Sunrise");

    public static ScreenManager config = new ScreenManager();

    @ConfigEntry
    public static Double a = 231515D;
    @ConfigEntry
    public static String b = "sagasgasg";

    @ConfigEntry
    public static String c = "sagsag";
    @ConfigEntry
    public static Boolean d = true;


    static {
        config.autoAdd(SunriseConfigDevelopment.class);
        config.autoAdd(SunriseConfig.class);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
        ClientRegistry.registerKeyBinding(keyBinding);
    }
    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event){
        if(keyBinding.isPressed()){
            config.display();
            System.out.println("a:" + a);
            System.out.println("b:" + b);
            System.out.println("c:" + c);
        }
    }
}
