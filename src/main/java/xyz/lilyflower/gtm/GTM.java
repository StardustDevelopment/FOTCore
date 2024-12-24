package xyz.lilyflower.gtm;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

@Mod(modid = "gtm", version = GTM.VERSION, dependencies = "")
public class GTM {
    public static final String VERSION = "2.1";

    public static final Logger LOGGER = LogManager.getLogger("GenericTweakMod");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.Thaumcraft.init();

        Config.run(event.getSuggestedConfigurationFile());
    }

    public static final class Config {
        public static void add(String mod, Consumer<Configuration> runner) {
            ArrayList<Consumer<Configuration>> runners = CONFIG_RUNNERS.getOrDefault(mod, new ArrayList<>());
            runners.add(runner);
            CONFIG_RUNNERS.put(mod, runners);
        }

        public static void run(File config) {
            Configuration cfg = new Configuration(config);

            CONFIG_RUNNERS.forEach((mod, runners) -> {
                if (Loader.isModLoaded(mod)) {
                    for (Consumer<Configuration> runner : runners) {
                        runner.accept(cfg);
                    }
                }
            });

            if (cfg.hasChanged()) {
                cfg.save();
            }
        }

        private static final HashMap<String, ArrayList<Consumer<Configuration>>> CONFIG_RUNNERS = new HashMap<>();

        public static final class Thaumcraft {
            public enum AspectGenerationMode {
                RANDOM,
                STATIC,
                NONHUMAN,
                NONE
            }

            public static AspectGenerationMode ASPECT_GENERATION_MODE = AspectGenerationMode.RANDOM;
            public static AspectList STATIC_ASPECTS = new AspectList();
            public static Aspect NON_HUMAN_ASPECT = Aspect.getAspect("alienis");

            public static HashMap<String, AspectList> PLAYER_ASPECTS = new HashMap<>();

            public static final Consumer<Configuration> ASPECTS = configuration -> {

                try {
                    ASPECT_GENERATION_MODE = AspectGenerationMode.valueOf(configuration.getString("aspectMode", "thaumcraft.aspects", "RANDOM", """
                        Player aspect generation mode.
                        Valid values: RANDOM (default), STATIC (all players have the same),
                        NONHUMAN (like RANDOM but replace Humanus), and NONE (disable the feature)."""));
                } catch (IllegalArgumentException ignored) {}

                String[] values = configuration.getStringList("playerSpecificAspects", "thaumcraft.aspects", new String[]{}, "Player-specific aspect overrides. Takes priority over aspectMode! Format:\n<PLAYERNAME> <ASPECT> <ASPECT> <ASPECT> ...");
                for (String value : values) {
                    String[] parsed = value.split(" ");
                    String[] aspects = new String[parsed.length-1];

                    System.arraycopy(parsed, 1, aspects, 0, parsed.length-1);

                    PLAYER_ASPECTS.put(parsed[0], parseAspects(aspects));
                }

                STATIC_ASPECTS = parseAspects(configuration.getString("staticAspects", "thaumcraft.aspects", "humanus", "Static aspects for aspectMode STATIC.").split(" "));

                try {
                    NON_HUMAN_ASPECT = Aspect.getAspect(configuration.getString("staticAspects", "thaumcraft.aspects", "alienis", "Non-human aspect for aspectMode NONHUMAN."));
                } catch (IllegalArgumentException ignored) {}
            };

            private static AspectList parseAspects(String... aspects) {
                AspectList parsed = new AspectList();

                for (String aspect : aspects) {
                    try {
                        parsed.add(Aspect.getAspect(aspect.toLowerCase()), 4);
                    } catch (IllegalArgumentException ignored) {}
                }

                return parsed;
            }

            public static void init() {
                Config.add("Thaumcraft", ASPECTS);
            }
        }
    }
}
