package xyz.lilyflower.fotcore.init;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.lilyflower.fotcore.init.config.ConfigLoader;

@Mod(modid = "fotcore", version = FoTCore.VERSION, dependencies = "")
public class FoTCore {
    public static final String VERSION = "2.1";

    public static final Logger LOGGER = LogManager.getLogger("FoT Core");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigLoader.run(event.getSuggestedConfigurationFile());
    }


}