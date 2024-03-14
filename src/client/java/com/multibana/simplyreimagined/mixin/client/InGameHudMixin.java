package com.multibana.simplyreimagined.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.JumpingMount;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
	@Shadow
	private int scaledHeight;
	@Final
	@Shadow
	private MinecraftClient client;

	// dont render exp bar since there is no exp anymore
	@Inject(method = "renderExperienceBar", at=@At("HEAD"), cancellable = true)
	private void noExpBar(DrawContext context, int x, CallbackInfo ci){
		ci.cancel();
	}

	// lower the hud bars since we dont have an experience bar anymore
	@ModifyConstant(method = "renderMountJumpBar", constant = @Constant(intValue = 32))
	private int lowerMountJumpBar(int constant){
		return constant-5;
	}

	@ModifyConstant(method = "renderStatusBars", constant = @Constant(intValue = 39))
	private int lowerStatusBars(int constant){
		return constant-6;
	}

	@ModifyConstant(method = "renderMountHealth", constant = @Constant(intValue = 39))
	private int lowerMountHealth(int constant){
		return constant-6;
	}


//	@Inject(method = "renderMountJumpBar", at = @At("HEAD"))
//	private void lowerMountJumpBarHeight(JumpingMount mount, DrawContext context, int x, CallbackInfo ci) {
//		scaledHeight = scaledHeight + 5;
//	}
//	@Inject(method = "renderMountJumpBar", at = @At("RETURN"))
//	private void lowerMountJumpBar(JumpingMount mount, DrawContext context, int x, CallbackInfo ci) {
//		scaledHeight = client.getWindow().getScaledHeight();
//	}
}