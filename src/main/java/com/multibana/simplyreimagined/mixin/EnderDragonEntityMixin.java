package com.multibana.simplyreimagined.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragonEntity.class)
public class EnderDragonEntityMixin extends MobEntity {
    @Unique
    EnderDragonEntity dragon = (EnderDragonEntity) (Object) this;

    protected EnderDragonEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }
    // reduce damage taken by bed explosion to 0
    @Override
    public boolean damage(DamageSource source, float amount) {
        if(source.getType().msgId().contains("badRespawnPoint")){
            return super.damage(source, 0.0f);
        }
        return super.damage(source, amount);
    }
    @Override
    public void applyDamage(DamageSource source, float amount) {
        if(source.getType().msgId().contains("badRespawnPoint")){
            return;
        }
        super.applyDamage(source, amount);
    }
    // dragon drops its loot when it gets to the animation of exploding etc
    @Override
    protected void dropLoot(DamageSource damageSource, boolean causedByPlayer) {
        if(dragon.ticksSinceDeath > 150){
            super.dropLoot(damageSource, causedByPlayer);
        }
    }
    @Inject(method = "updatePostDeath", at = @At("HEAD"))
    private void callDropLootIfExploding(CallbackInfo ci){
        if(dragon.getWorld() instanceof ServerWorld){
            if(dragon.ticksSinceDeath == 155){
                dropLoot(dragon.getWorld().getDamageSources().generic(), true);
            }
        }
    }
}
