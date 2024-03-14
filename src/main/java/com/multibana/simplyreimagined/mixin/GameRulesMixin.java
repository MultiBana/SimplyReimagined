package com.multibana.simplyreimagined.mixin;

import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(GameRules.class)
public class GameRulesMixin {
    @Shadow
    @Final
    private Map<GameRules.Key<?>, GameRules.Rule<?>> rules;
    @Shadow @Final public static GameRules.Key<GameRules.BooleanRule> DO_INSOMNIA;

    @Shadow @Final public static GameRules.Key<GameRules.BooleanRule> DISABLE_RAIDS;

    @Shadow @Final public static GameRules.Key<GameRules.BooleanRule> DO_PATROL_SPAWNING;

    @Shadow @Final public static GameRules.Key<GameRules.BooleanRule> DO_TRADER_SPAWNING;

    @Inject(method = "<init>()V", at = @At("TAIL"))
    private void overrideDefaultGameRules(CallbackInfo ci){
        Object disableRaidsRule = rules.get(DISABLE_RAIDS);

        Object doInsomniaRule = rules.get(DO_INSOMNIA);
        Object doPillagerPatrolsRule = rules.get(DO_PATROL_SPAWNING);
        Object doWanderingTraderRule = rules.get(DO_TRADER_SPAWNING);

        if(disableRaidsRule instanceof GameRules.BooleanRule){
            ((GameRules.BooleanRule) disableRaidsRule).set(true, null);
        }

        if(doInsomniaRule instanceof GameRules.BooleanRule){
            ((GameRules.BooleanRule) doInsomniaRule).set(false, null);
        }
        if(doPillagerPatrolsRule instanceof GameRules.BooleanRule){
            ((GameRules.BooleanRule) doPillagerPatrolsRule).set(false, null);
        }
        if(doWanderingTraderRule instanceof GameRules.BooleanRule){
            ((GameRules.BooleanRule) doWanderingTraderRule).set(false, null);
        }
    }
}
