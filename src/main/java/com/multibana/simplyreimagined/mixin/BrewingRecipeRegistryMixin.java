package com.multibana.simplyreimagined.mixin;

import net.minecraft.item.Items;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(BrewingRecipeRegistry.class)
public class BrewingRecipeRegistryMixin {
    // replaces phantom membrane with feather in potion recipes (affects potion of slow falling)
    @ModifyArgs(method = "registerDefaults", at = @At(value="INVOKE", target = "Lnet/minecraft/recipe/BrewingRecipeRegistry;registerPotionRecipe(Lnet/minecraft/potion/Potion;Lnet/minecraft/item/Item;Lnet/minecraft/potion/Potion;)V"))
    private static void brewWithFeather(Args args){
        if(args.get(1) == Items.PHANTOM_MEMBRANE){
            args.set(1, Items.FEATHER);
        }
    }
}
