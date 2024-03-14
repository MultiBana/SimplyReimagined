package com.multibana.simplyreimagined.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.serialization.Codec;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Arrays;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
	// getting attack indicator setting always returns OFF
	@ModifyReturnValue(method = "getAttackIndicator", at=@At("RETURN"))
	private SimpleOption<AttackIndicator> getAttackIndicatorHook(SimpleOption<AttackIndicator> original){
		return new SimpleOption<AttackIndicator>("options.attackIndicator", SimpleOption.emptyTooltip(), SimpleOption.enumValueText(), new SimpleOption.PotentialValuesBasedCallbacks<>(Arrays.asList(AttackIndicator.values()), Codec.INT.xmap(AttackIndicator::byId, AttackIndicator::getId)), AttackIndicator.OFF, (value) -> {});
	}
}