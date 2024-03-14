package com.multibana.simplyreimagined.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(ProtectionEnchantment.class)
public class ProtectionEnchantmentMixin {
    @ModifyReturnValue(method = "getMaxLevel", at=@At("RETURN"))
    private int hookMaxLevel(int original){
        return 2;
    }
    @ModifyArgs(method = "<init>", at=@At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;<init>(Lnet/minecraft/enchantment/Enchantment$Rarity;Lnet/minecraft/enchantment/EnchantmentTarget;[Lnet/minecraft/entity/EquipmentSlot;)V"))
    private static void changeSuper(Args args, Enchantment.Rarity weight, ProtectionEnchantment.Type protectionType, EquipmentSlot... slotTypes){
        EnchantmentTarget target = EnchantmentTarget.ARMOR;
        switch (protectionType) {
            case PROJECTILE -> target = EnchantmentTarget.ARMOR_HEAD;
            case EXPLOSION -> target = EnchantmentTarget.ARMOR_CHEST;
            case FIRE -> target = EnchantmentTarget.ARMOR_LEGS;
            case FALL -> target = EnchantmentTarget.ARMOR_FEET;
        }
        args.set(1, target);
    }
}
