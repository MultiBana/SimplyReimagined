package com.multibana.simplyreimagined.mixin;

import com.multibana.simplyreimagined.config.Config;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ElytraItem.class)
public class ElytraItemMixin {
    @Inject(method = "canRepair", at = @At("TAIL"), cancellable = true)
    private void cantRepair(ItemStack stack, ItemStack ingredient, CallbackInfoReturnable<Boolean> cir){
        if(Config.rules.get("i_kinda_liked_phantoms") && ingredient.isOf(Items.LEATHER)){
            cir.setReturnValue(true);
        }
        cir.setReturnValue(false);
    }
}
