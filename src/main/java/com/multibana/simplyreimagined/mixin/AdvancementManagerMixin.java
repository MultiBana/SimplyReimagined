package com.multibana.simplyreimagined.mixin;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(AdvancementManager.class)
public class AdvancementManagerMixin {
    // never load any advancements at all
    @Inject(method = "load", at = @At("HEAD"), cancellable = true)
    private void sneed(Map<Identifier, Advancement.Builder> advancements, CallbackInfo ci){
        ci.cancel();
    }
}
