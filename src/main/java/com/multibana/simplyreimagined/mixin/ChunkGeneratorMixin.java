package com.multibana.simplyreimagined.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.structure.Structure;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {
    // TODO - DISABLE LOCATING STRUCTURES I REMOVED, CURRENTLY CRASHES THE GAME IF DONE
//    @Inject(method = "locateStructure(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/registry/entry/RegistryEntryList;Lnet/minecraft/util/math/BlockPos;IZ)Lcom/mojang/datafixers/util/Pair;", at = @At("HEAD"), cancellable = true)
//    public void dontLocateDisabledStructures(ServerWorld world, RegistryEntryList<Structure> structures, BlockPos center, int radius, boolean skipReferencedStructures, CallbackInfoReturnable<@Nullable Pair<BlockPos, RegistryEntry<Structure>>> cir) {
//            structures.forEach(structure -> {
//        StructureType structureType = structure.value().getType();
//         if(ITS A REMOVED STRUCTURE)
//        cir.cancel();
//        });
//    }
    // adds a minimum radius to locateStructure so village-finding maps wont just return the village you're in
    @ModifyConstant(method = "locateStructure(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/registry/entry/RegistryEntryList;Lnet/minecraft/util/math/BlockPos;IZ)Lcom/mojang/datafixers/util/Pair;", constant = @Constant(intValue = 0))
    public int addMinRadius(int constant){
        return 5;
    }

}
