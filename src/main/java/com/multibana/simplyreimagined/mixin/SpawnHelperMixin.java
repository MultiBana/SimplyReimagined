package com.multibana.simplyreimagined.mixin;

import com.multibana.simplyreimagined.config.Config;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnHelper.class)
public class SpawnHelperMixin {
    @Inject(method = "canSpawn(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/world/biome/SpawnSettings$SpawnEntry;Lnet/minecraft/util/math/BlockPos$Mutable;D)Z", at = @At("RETURN"), cancellable = true)
    private static void cantSpawnSHITBLOATMOBS(ServerWorld world, SpawnGroup group, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, SpawnSettings.SpawnEntry spawnEntry, BlockPos.Mutable pos, double squaredDistance, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            if (spawnEntry.type == EntityType.ZOMBIE_VILLAGER
                    || spawnEntry.type == EntityType.POLAR_BEAR
                    || spawnEntry.type == EntityType.LLAMA
                    || spawnEntry.type == EntityType.RABBIT
                    || spawnEntry.type == EntityType.BAT
                    || (spawnEntry.type == EntityType.DOLPHIN && !Config.rules.get("i_like_dolphins"))
                    || spawnEntry.type == EntityType.TROPICAL_FISH
                    || spawnEntry.type == EntityType.PANDA
                    || spawnEntry.type == EntityType.GLOW_SQUID
                    || spawnEntry.type == EntityType.OCELOT
            )
                cir.setReturnValue(false);
        }
    }
    @Inject(method = "canSpawn(Lnet/minecraft/entity/SpawnRestriction$Location;Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/EntityType;)Z", at = @At("RETURN"), cancellable = true)
    private static void stillCantSpawnSHITBLOATMOBS(SpawnRestriction.Location location, WorldView world, BlockPos pos, @Nullable EntityType<?> entityType, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            if (entityType == EntityType.ZOMBIE_VILLAGER
                    || entityType == EntityType.POLAR_BEAR
                    || entityType == EntityType.LLAMA
                    || entityType == EntityType.RABBIT
                    || entityType == EntityType.BAT
                    || entityType == EntityType.DOLPHIN
                    || entityType == EntityType.TROPICAL_FISH
                    || entityType == EntityType.PANDA
                    || entityType == EntityType.GLOW_SQUID
                    || entityType == EntityType.OCELOT)
                cir.setReturnValue(false);
        }
    }
}
