package com.multibana.simplyreimagined.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.TridentItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.UUID;

@Mixin(TridentItem.class)
public class TridentItemMixin extends Item {
    @Unique
    private TridentItem instance = (TridentItem) (Object) this;
    public TridentItemMixin(Settings settings) {
        super(settings);
    }
    // trident can be repaired either with nautilus shells or heart of the sea
    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isOf(Items.HEART_OF_THE_SEA) || ingredient.isOf(Items.NAUTILUS_SHELL);
    }

    // lower damage to account for no cooldown
    @Redirect(method = "<init>", at = @At(value = "NEW", target = "(Ljava/util/UUID;Ljava/lang/String;DLnet/minecraft/entity/attribute/EntityAttributeModifier$Operation;)Lnet/minecraft/entity/attribute/EntityAttributeModifier;"))
    private static EntityAttributeModifier reduceDamageBonus(UUID uuid, String name, double value, EntityAttributeModifier.Operation operation) {
        return new EntityAttributeModifier(uuid, name, 5.0, operation);
    }
    // add half as much velocity if flying
    @ModifyArgs(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addVelocity(DDD)V"))
    private void nerfVelocity(Args args, ItemStack stack, World world, LivingEntity user, int remainingUseTicks){
        if(user instanceof PlayerEntity){
            if(user.isFallFlying()){
                args.set(0, (Double)args.get(0)/2);
                args.set(1, (Double)args.get(1)/2);
                args.set(2, (Double)args.get(2)/2);
            }
        }
    }

    // remove slow falling and set a 9 second cooldown if flying
    @Inject(method = "onStoppedUsing", at=@At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addVelocity(DDD)V", shift = At.Shift.BEFORE))
    private void dontBeCompletelyBroken(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci){
        if(user instanceof PlayerEntity){
            if(user.isFallFlying()){
                if(user.hasStatusEffect(StatusEffects.SLOW_FALLING)){
                    user.removeStatusEffect(StatusEffects.SLOW_FALLING);
                }
                ((PlayerEntity) user).getItemCooldownManager().set(instance, 20 * 9);
            }
        }
    }

    // double durability damage if flying
    // kind of overkill of a nerf, since mending is removed and repairing tridents is costly
    // also looting max is 1 so even if you make a drowned farm its probably gonna take a while to get one
//    @ModifyArgs(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V"))
//    private void damageMore(Args args){
//        PlayerEntity player = args.get(1);
//        if(player.isFallFlying()){
//            args.set(0, 2 * (Integer)args.get(0));
//        }
//    }

}
