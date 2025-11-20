package club.sk1er.mods.tnttime.utils;

import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils;
import club.sk1er.mods.tnttime.TNTTime;
import club.sk1er.mods.tnttime.config.TNTTimeConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.text.DecimalFormat;

public class FuseUtils {
    private static final DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("0.00");

    // to adjust for Hypixel Bedwars
    public static boolean bedwars;

    private int checkTimer;

    public static int adjustFuse(int ticks) {
        return bedwars ? ticks - 28 : ticks;
    }

    public static Color getFuseColor(int ticks) {
        float divisor = bedwars ? 52f : 80f;
        float progress = Math.min(adjustFuse(ticks) / divisor, 1f);
        return interpolateColor(progress);
    }

    private static Color interpolateColor(float progress) {
        OneColor start = TNTTimeConfig.startColor;
        OneColor end = TNTTimeConfig.endColor;

        int r = (int)(end.getRed()   + progress * (start.getRed()   - end.getRed()));
        int g = (int)(end.getGreen() + progress * (start.getGreen() - end.getGreen()));
        int b = (int)(end.getBlue()  + progress * (start.getBlue()  - end.getBlue()));

        return new Color(r, g, b);
    }

    public static String getTimer(int ticks) {
        return DECIMAL_FORMATTER.format(ticks / 20f);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START || checkTimer++ < 250) return;
        checkTimer = 0;

        bedwars = false;

        if (!HypixelUtils.INSTANCE.isHypixel()) {
            return;
        }

        WorldClient world = Minecraft.getMinecraft().theWorld;
        if (world == null || world.getScoreboard() == null) {
            return;
        }

        bedwars = isBedwars(world.getScoreboard());
    }

    private boolean isBedwars(Scoreboard scoreboard) {
        ScoreObjective sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1);
        if (sidebarObjective == null) return false;
        String name = EnumChatFormatting.getTextWithoutFormattingCodes(sidebarObjective.getDisplayName());
        return name.contains("BED WARS");
    }
}
