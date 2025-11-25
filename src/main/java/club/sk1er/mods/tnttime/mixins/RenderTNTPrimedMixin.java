package club.sk1er.mods.tnttime.mixins;

import club.sk1er.mods.tnttime.utils.ConfigUtils;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderTNTPrimed;
import net.minecraft.entity.item.EntityTNTPrimed;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderTNTPrimed.class)
public abstract class RenderTNTPrimedMixin extends Render<EntityTNTPrimed> {
    protected RenderTNTPrimedMixin(RenderManager renderManager) {
        super(renderManager);
    }

    @Inject(
            method = "doRender(Lnet/minecraft/entity/item/EntityTNTPrimed;DDDFF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/Render;doRender(Lnet/minecraft/entity/Entity;DDDFF)V")
    )
    private void drawNametag(EntityTNTPrimed entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        String text = ConfigUtils.getTimerText(entity.fuse);

        if (text != null) { // text returns null when the fuse is 1 tick or lower after adjustment
            renderLivingLabel(entity, text, x, y, z, 64);
        }
    }
}
