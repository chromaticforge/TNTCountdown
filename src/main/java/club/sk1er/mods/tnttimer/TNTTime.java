package club.sk1er.mods.tnttimer;

import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.text.DecimalFormat;

@Mod(modid = TNTTime.ID, name = TNTTime.NAME, version = TNTTime.VERSION)
public class TNTTime {
    public static final String ID = "@MOD_ID@";
    public static final String NAME = "@MOD_NAME@";
    public static final String VERSION = "@MOD_VERSION@";

    private final Minecraft mc = Minecraft.getMinecraft();
    private final DecimalFormat timeFormatter = new DecimalFormat("0.00");
    private int checkTimer;
    private boolean playingBedwars;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START
                // no need to check super often, they're likely not going to see tnt within 5 seconds
                // of moving out of a bedwars game/lobby
                || this.checkTimer++ < 250) {
            return;
        }

        this.checkTimer = 0;

        if (!HypixelUtils.INSTANCE.isHypixel()) {
            this.playingBedwars = false;
            return;
        }

        final WorldClient world = mc.theWorld;
        if (world == null) {
            this.playingBedwars = false;
            return;
        }

        final Scoreboard scoreboard = world.getScoreboard();
        if (scoreboard == null) {
            this.playingBedwars = false;
            return;
        }

        final ScoreObjective sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1);
        if (sidebarObjective != null) {
            playingBedwars = EnumChatFormatting.getTextWithoutFormattingCodes(sidebarObjective.getDisplayName()).contains("BED WARS");
        }
    }
}