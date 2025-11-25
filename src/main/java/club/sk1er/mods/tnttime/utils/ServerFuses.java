package club.sk1er.mods.tnttime.utils;

import java.util.function.Supplier;

public enum ServerFuses {
    HYPIXEL_BEDWARS(-28, ServerUtils::isHypixelBedwars),
    MINEMEN(-29, ServerUtils::isMinemen);

    private final int adjustment;
    private final Supplier<Boolean> condition;

    ServerFuses(int adjustment, Supplier<Boolean> condition) {
        this.adjustment = adjustment;
        this.condition = condition;
    }

    public static int getAdjustment() {
        for (ServerFuses sf : values()) {
            if (sf.condition.get()) {
                return sf.adjustment;
            }
        }
        return 0;
    }
}
