package com.multibana.simplyreimagined.mixin;

import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EnderPearlEntity.class)
public class EnderPearlEntityMixin {
    // reduce chance to spawn endermite on pearl use to 0
    @ModifyConstant(method = "onCollision", constant = @Constant(floatValue = 0.05f))
    public float noChanceForEndermite(float constant){
        return 0.0f;
    }
}
