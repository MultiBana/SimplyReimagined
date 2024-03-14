package com.multibana.simplyreimagined.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageEnchantment.class)
public class DamageEnchantmentMixin {
	@ModifyReturnValue(method = "getMaxLevel", at=@At("RETURN"))
	private int sharpnessMaxLevel(int original){
		return 3;
	}
	@Inject(method = "isAcceptableItem", at=@At("HEAD"), cancellable = true)
	private void sharpnessAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
		if(stack.getItem() instanceof TridentItem){
			cir.setReturnValue(true);
		}
	}
}