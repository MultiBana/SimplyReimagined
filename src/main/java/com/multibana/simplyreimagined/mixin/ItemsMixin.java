package com.multibana.simplyreimagined.mixin;

import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Items.class)
public class ItemsMixin {
    // reduce added attack damage of axes by half to make them slightly weaker than swords
    @Redirect(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/item/ToolMaterial;FFLnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/AxeItem;"))
    private static AxeItem injected(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
        return new AxeItem(material, (float)(int)(attackDamage * 0.5), attackSpeed, settings);
    }
    // increase shield durability to 1344
    @Redirect(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/ShieldItem;"))
    private static ShieldItem injected(Item.Settings settings) {
        return new ShieldItem((new Item.Settings()).maxDamage(1344));
    }
}
