package club.sk1er.mods.tnttime;

import cc.polyfrost.oneconfig.events.EventManager;
import cc.polyfrost.oneconfig.events.event.WorldLoadEvent;
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe;
import club.sk1er.mods.tnttime.config.TNTTimeConfig;
import club.sk1er.mods.tnttime.utils.ConfigUtils;
import club.sk1er.mods.tnttime.utils.ServerFuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = TNTTime.ID, name = TNTTime.NAME, version = TNTTime.VERSION)
public class TNTTime {
    public static final String ID = "@MOD_ID@";
    public static final String NAME = "@MOD_NAME@";
    public static final String VERSION = "@MOD_VERSION@";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        new TNTTimeConfig();
        EventManager.INSTANCE.register(this);
    }

    @Subscribe
    public void onWorldChange(WorldLoadEvent event) {
        ConfigUtils.ADJUSTMENT = ServerFuses.getAdjustment();
        ConfigUtils.MAX_FUSE = 80 + ConfigUtils.ADJUSTMENT;
    }
}