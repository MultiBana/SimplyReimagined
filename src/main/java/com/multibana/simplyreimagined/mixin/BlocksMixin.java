package com.multibana.simplyreimagined.mixin;

import net.minecraft.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Blocks.class)
public class BlocksMixin {

    // obsidian-based blocks and netherite block break faster
    @ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = 50.0f))
    private static float nerfHardestBlocks(float constant){
        return 40.0f;
    }
}
