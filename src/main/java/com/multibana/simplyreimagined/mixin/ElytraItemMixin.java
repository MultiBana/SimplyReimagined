package com.multibana.simplyreimagined.mixin;

import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ElytraItem.class)
public class ElytraItemMixin {
    @Inject(method = "canRepair", at = @At("TAIL"), cancellable = true)
    private void cantRepair(ItemStack stack, ItemStack ingredient, CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(true);
    }
}
