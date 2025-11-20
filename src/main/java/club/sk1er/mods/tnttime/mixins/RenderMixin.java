package club.sk1er.mods.tnttime.mixins;

import club.sk1er.mods.tnttime.utils.FuseUtils;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(value = Render.class, priority = 995)
public class RenderMixin {
    @Unique private Entity tntTime$capturedEntity;

    @Inject(method = "renderLivingLabel", at = @At("HEAD"))
    private void captureTarget(Entity entityIn, String str, double x, double y, double z, int maxDistance, CallbackInfo ci) {
        tntTime$capturedEntity = entityIn;
    }

    @ModifyArg(
            method = "renderLivingLabel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I", ordinal = 1),
            index = 3
    )
    private int updateColor(int color) {
        if (tntTime$capturedEntity instanceof EntityTNTPrimed) {
            EntityTNTPrimed tnt = (EntityTNTPrimed) tntTime$capturedEntity;

            color = FuseUtils.getFuseColor(tnt.fuse).getRGB();
        }

        return color;
    }
}
