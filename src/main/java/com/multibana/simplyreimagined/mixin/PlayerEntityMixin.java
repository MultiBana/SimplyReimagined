package com.multibana.simplyreimagined.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.multibana.simplyreimagined.config.Config;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
	// always be full damage, cooldown is always over
	@ModifyReturnValue(method = "getAttackCooldownProgress", at=@At("RETURN"))
	private float removeAttackCooldown(float baseTime){
		return 1.0f;
	}
	// the "should crit" check makes you unable to crit if you're touching water, so this makes isTouchingWater true
	@Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isTouchingWater()Z"))
	private boolean injected(PlayerEntity instance) {
		return true;
	}

	// "should sweep" checks if you are holding a sword, so this makes its itemStack.getItem() return AIR
	@Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"))
	private Item injected(ItemStack instance) {
		return Items.AIR;
	}

	// Changes all 0.0f values in increaseTravelMotionStats, which are the values of exhaustion added per cm moved
	// walking and sneaking, to 0.005f. Makes walking/sneaking very slowly consume hunger.
	@ModifyConstant(method = "increaseTravelMotionStats", constant = @Constant(floatValue = 0.0f))
	private float injected(float value) {
		return Config.rules.get("enable_walk_hunger") ? 0.005f : 0.0f;
	}
	// only removes jump exhaustion if config calls for it
	@ModifyArg(method = "jump", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addExhaustion(F)V"))
	private float jumpExhaust(float exhaustion){
		return Config.rules.get("enable_jump_hunger") ? exhaustion : 0.0f;
	}
	@Unique
	PlayerEntity instance = (PlayerEntity) (Object) this;
	// disable sprint when no longer swimming (needed because sprinting is enabled in water @ LivingEntityMixin)
	// reduce swim-sprint speed from 1.3 to 1.1 (10% faster than walking instead of 30%)
	@Inject(method = "updateWaterSubmersionState", at = @At("TAIL"))
	private void underTheWater(CallbackInfoReturnable<Boolean> cir){
		if(instance.getMovementSpeed() > 0.1F){
			instance.setMovementSpeed(0.11F);
		}
		if(!instance.isSubmergedIn(FluidTags.WATER)){
			instance.setSprinting(instance.isSprinting());
		}
	}
	// diving isnt super fast
	@ModifyArg(method = "travel", at=@At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"))
	private Vec3d reduceVerticalSwimSpeed(Vec3d par1){
		return new Vec3d(par1.x, par1.y*0.78, par1.z);
	}


}