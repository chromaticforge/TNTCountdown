package club.sk1er.mods.tnttime.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Button;
import cc.polyfrost.oneconfig.config.annotations.Color;
import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.oneconfig.libs.universal.UDesktop;
import club.sk1er.mods.tnttime.TNTTime;
import net.minecraftforge.fml.common.Loader;

import java.net.URI;

public class TNTTimeConfig extends Config {
    @Button(
            name = "Looking to customize the nametag?",
            text = "Install PolyNametag!",
            size = 2
    )
    public Runnable polyNametag = () -> { // polyfrost will become a billion-dollar company after this advertisement
        UDesktop.browse(URI.create("https://modrinth.com/mod/polynametag"));
    };

    @Color(
            name = "Start Color",
            description = "The color the timer starts at.",
            allowAlpha = false
    )
    public static OneColor startColor = new OneColor(0, 255, 0);

    @Color(
            name = "End Color",
            description = "The color the timer ends at.",
            allowAlpha = false
    )
    public static OneColor endColor = new OneColor(255, 0, 0);

    public TNTTimeConfig() {
        super(new Mod(TNTTime.NAME, ModType.HYPIXEL), TNTTime.ID + ".json");
        initialize();

        hideIf("polyNametag", () -> Loader.isModLoaded("polynametag"));
    }
}
