package com.multibana.simplyreimagined.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.chunk.ChunkSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkSection.class)
public abstract class ChunkSectionMixin {

    @Shadow
    public abstract BlockState setBlockState(int x, int y, int z, BlockState state, boolean lock);

    @Inject(method = "setBlockState(IIILnet/minecraft/block/BlockState;Z)Lnet/minecraft/block/BlockState;", at = @At("HEAD"), cancellable = true)
    private void dontGenerate(int x, int y, int z, BlockState state, boolean lock, CallbackInfoReturnable<BlockState> cir){
        if(state.isOf(Blocks.TUFF)){
            cir.setReturnValue(setBlockState(x, y, z, Blocks.DEEPSLATE.getDefaultState(), lock));
        }
        else if(state.isOf(Blocks.COPPER_ORE)){
            cir.setReturnValue(setBlockState(x, y, z, Blocks.STONE.getDefaultState(), lock));
        }
        else if(state.isOf(Blocks.DEEPSLATE_COPPER_ORE)){
            cir.setReturnValue(setBlockState(x, y, z, Blocks.DEEPSLATE.getDefaultState(), lock));
        }
        else if(state.isOf(Blocks.GRANITE)){
            cir.setReturnValue(setBlockState(x, y, z, Blocks.STONE.getDefaultState(), lock));
        }
        else if(state.isOf(Blocks.DIORITE)){
            cir.setReturnValue(setBlockState(x, y, z, Blocks.STONE.getDefaultState(), lock));
        }
        else if(state.isOf(Blocks.ANDESITE)){
            cir.setReturnValue(setBlockState(x, y, z, Blocks.STONE.getDefaultState(), lock));
        }
        else if(state.isOf(Blocks.POWDER_SNOW)){
            cir.setReturnValue(setBlockState(x, y, z, Blocks.SNOW_BLOCK.getDefaultState(), lock));
        }
        else if(state.isOf(Blocks.GLOW_LICHEN)){
            cir.setReturnValue(setBlockState(x, y, z, Blocks.AIR.getDefaultState(), lock));
        }
    }
}
