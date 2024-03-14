package com.multibana.simplyreimagined.mixin;
import com.multibana.simplyreimagined.config.Config;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @ModifyVariable(method = "setSprinting", at = @At("HEAD"), argsOnly = true)
    private boolean hookSetSprinting(boolean sprinting) {
        return (
                !(((LivingEntity) (Object) this) instanceof PlayerEntity playerEntity)
                        || playerEntity.isCreative()
                        || playerEntity.isSpectator()
                        || (playerEntity.isSwimming() && Config.rules.get("enable_swimming"))
                        || (playerEntity.isSubmergedInWater()) && Config.rules.get("enable_swimming")
        ) && sprinting; }
}