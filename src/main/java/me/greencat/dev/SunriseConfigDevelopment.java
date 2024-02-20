package me.greencat.dev;

import me.greencat.src.ScreenManager;
import me.greencat.src.SunriseConfigBootstrap;
import me.greencat.src.utils.ClassCategory;
import me.greencat.src.utils.ConfigEntry;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

@ClassCategory("test1")
//@Mod(modid = SunriseConfigDevelopment.MODID, version = SunriseConfigDevelopment.VERSION,name = "SunriseConfigDevelopmentTestModFile")
public class SunriseConfigDevelopment extends SunriseConfig
{
    public static final String MODID = "sunrisedevelopment";
    public static final String VERSION = "1.0";
    KeyBinding keyBinding = new KeyBinding("SunriseTest", Keyboard.KEY_F12,"Sunrise");

    public static ScreenManager config = new ScreenManager();

    @ConfigEntry("这是Double")
    public static Double a = 231515D;
    @ConfigEntry("String测试")
    public static String b = "sagasgasg";

    @ConfigEntry("第二个String测试")
    public static String c = "sagsag";
    @ConfigEntry("测试d")
    public static Boolean d = true;

    @ConfigEntry("枚举")
    public static EnumTest testEnum = EnumTest.TEST1;
    @ConfigEntry("颜色")
    public static Color color = Color.BLACK;


    static {
        config.autoAdd(SunriseConfigDevelopment.class);
        config.postInitialization();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
        ClientRegistry.registerKeyBinding(keyBinding);
        SunriseConfigBootstrap.bootstrap();
    }
    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event){
        if(keyBinding.isPressed()){
            config.display();
        }
    }
    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent event){
        Gui.drawRect(100,100,200,200,color.getRGB());
    }
}
