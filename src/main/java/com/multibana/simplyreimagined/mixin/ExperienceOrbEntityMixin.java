package com.multibana.simplyreimagined.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin {

    @Unique
    ExperienceOrbEntity instance = (ExperienceOrbEntity) (Object) this;

    // removes experience orbs before they even spawn
    @Inject(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V", at=@At("TAIL"))
    private void killOnInit(EntityType<? extends ExperienceOrbEntity> entityType, World world, CallbackInfo ci){
        instance.remove(Entity.RemovalReason.DISCARDED);
    }

    @Inject(method = "<init>(Lnet/minecraft/world/World;DDDI)V", at=@At("TAIL"))
    private void killOnInit2(World world, double x, double y, double z, int amount, CallbackInfo ci){
        instance.remove(Entity.RemovalReason.DISCARDED);
    }
}
