package xyz.lilyflower.fotcore.init.mixin;

public enum FoTMixins {
    BOTANIA_MAVD_MANAWEAVE("botania.ManaArmourVisDiscounts$Manaweave", Phase.LATE),
    BOTANIA_MAVD_TERRASTEEL("botania.ManaArmourVisDiscounts$Terrasteel", Phase.LATE),
    BOTANIA_FLUXFIELD_NERF("botania.FluxfieldNerf", Phase.LATE)

    ;

    public final String mixinClass;
    private final Side side;
    public final Phase phase;

    FoTMixins(String mixinClass, Side side, Phase phase) {
        this.mixinClass = mixinClass;
        this.side = side;
        this.phase = phase;
    }

    FoTMixins(String mixinClass) {
        this.mixinClass = mixinClass;
        this.side = Side.BOTH;
        this.phase = Phase.NORMAL;
    }

    FoTMixins(String mixinClass, Phase phase) {
        this.mixinClass = mixinClass;
        this.side = Side.BOTH;
        this.phase = phase;
    }

    public enum Side {
        BOTH,
        CLIENT,
        SERVER
    }

    public enum Phase {
        EARLY,
        NORMAL,
        LATE
    }
}

