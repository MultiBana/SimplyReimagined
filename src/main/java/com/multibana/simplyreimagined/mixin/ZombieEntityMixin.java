package com.multibana.simplyreimagined.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(ZombieEntity.class)
public class ZombieEntityMixin {
    // also applies to zombified piglins since it inherits zombie and doesnt override
    @ModifyReturnValue(method = "shouldBeBaby", at = @At("RETURN"))
    private static boolean noBabyZombie(boolean original){
        return false;
    }
    // if difficulty is hard, pretend it is normal for the sake of turning villagers (50/50 to turn instead of 100%)
    @Redirect(method = "onKilledOther", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;getDifficulty()Lnet/minecraft/world/Difficulty;"))
    private Difficulty pretendNormalIfHard(ServerWorld instance){
        Difficulty trueDifficulty = instance.getDifficulty();
        return trueDifficulty == Difficulty.HARD ? Difficulty.NORMAL : trueDifficulty;
    }

}
