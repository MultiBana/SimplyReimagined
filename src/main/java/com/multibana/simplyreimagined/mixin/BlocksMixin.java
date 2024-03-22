package com.multibana.simplyreimagined.mixin;

import net.minecraft.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Blocks.class)
public class BlocksMixin {

    // blocks that take a long time to break (obsidian-based blocks, netherite, anvil, etc) break faster
    @ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = 1200.0f))
    private static float nerfHardestBlocks(float constant){
        return 800.0f;
    }
}
