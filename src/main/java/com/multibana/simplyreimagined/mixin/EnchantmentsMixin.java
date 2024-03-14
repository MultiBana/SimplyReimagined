package com.multibana.simplyreimagined.mixin;

import net.minecraft.enchantment.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantments.class)
public class EnchantmentsMixin {
    @Inject(method = "register", at = @At("HEAD"), cancellable = true)
    private static void removeEnchantments(String name, Enchantment enchantment, CallbackInfoReturnable<Enchantment> cir){
        if(enchantment instanceof MendingEnchantment
        || enchantment instanceof QuickChargeEnchantment
                || (enchantment instanceof ProtectionEnchantment && ((ProtectionEnchantment) enchantment).protectionType == ProtectionEnchantment.Type.ALL)
                || enchantment instanceof ThornsEnchantment
                || (enchantment instanceof DamageEnchantment && ((DamageEnchantment) enchantment).typeIndex == DamageEnchantment.UNDEAD_INDEX)
                || (enchantment instanceof DamageEnchantment && ((DamageEnchantment) enchantment).typeIndex == DamageEnchantment.ARTHROPODS_INDEX)
                || enchantment instanceof  FrostWalkerEnchantment
                || enchantment instanceof ImpalingEnchantment
                || enchantment instanceof SweepingEnchantment
        ){
            cir.setReturnValue(enchantment);
        }
    }
}
