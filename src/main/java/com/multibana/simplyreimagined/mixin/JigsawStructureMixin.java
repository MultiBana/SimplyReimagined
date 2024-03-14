package com.multibana.simplyreimagined.mixin;

import net.minecraft.world.gen.structure.JigsawStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(JigsawStructure.class)
public class JigsawStructureMixin {
    // change maximum size of jigsaw structures from 7 to 8 so villages can be  bigger
    @ModifyConstant(constant = @Constant(intValue = 7),method = "method_41662(Lcom/mojang/serialization/codecs/RecordCodecBuilder$Instance;)Lcom/mojang/datafixers/kinds/App;")
    private static int aa(int constant){

        return 8;
    }
}
