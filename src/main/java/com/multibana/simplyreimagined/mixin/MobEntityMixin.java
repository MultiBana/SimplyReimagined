package com.multibana.simplyreimagined.mixin;

import net.minecraft.entity.mob.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public class MobEntityMixin {
	@Unique
	MobEntity instance = (MobEntity) (Object) this;

	// skeletons dont drop bows
	// zombified piglins dont drop golden swords
	// wither skeletons dont drop stone swords
	// i think it works that way already, but just in case - dont let drowned drop tridents unless attacked by player
	@ModifyArg(method = "dropEquipment", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;dropStack(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/entity/ItemEntity;"))
	private ItemStack dontDropShittyGear(ItemStack par1){
		String itemKey = par1.getItem().getTranslationKey();
		if((itemKey.contains("item.minecraft.bow") && instance instanceof SkeletonEntity)
						|| (itemKey.contains("item.minecraft.golden_sword") && instance instanceof ZombifiedPiglinEntity)
						|| (itemKey.contains("item.minecraft.trident") && instance instanceof DrownedEntity && ((LivingEntityAccessor)instance).getPlayerHitTimer() <= 0)
						|| (itemKey.contains("item.minecraft.stone_sword") && instance instanceof WitherSkeletonEntity)
		){
			return ItemStack.EMPTY;
		}
		return par1;
	}
	// no enchants
	@Inject(method = "updateEnchantments", at = @At("HEAD"), cancellable = true)
	private void mobsDontGetEnchants(Random random, LocalDifficulty localDifficulty, CallbackInfo ci){
		ci.cancel();
	}
}