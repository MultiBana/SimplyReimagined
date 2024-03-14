package com.multibana.simplyreimagined.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {
    @ModifyConstant(method = "method_24922",constant = @Constant(floatValue = 0.12F))
    private static float doubleAnvilBreakChance(float constant){
        return constant * 2;
    }
    @Redirect(method = "updateResult", at = @At(value = "FIELD", target = "Lnet/minecraft/screen/AnvilScreenHandler;levelCost:Lnet/minecraft/screen/Property;"))
    private Property costIsFree(AnvilScreenHandler instance){
        Property a = Property.create();
        a.set(0);
        return a;
    }
    @Inject(method = "canTakeOutput", at = @At("HEAD"), cancellable = true)
    private void canAlwaysTakeOut(PlayerEntity player, boolean present, CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(true);
    }
}
