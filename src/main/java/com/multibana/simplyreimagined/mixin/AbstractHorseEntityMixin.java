package com.multibana.simplyreimagined.mixin;

import com.multibana.simplyreimagined.config.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.block.LeavesBlock.PERSISTENT;

@Mixin(AbstractHorseEntity.class)
public abstract class AbstractHorseEntityMixin extends Entity {
    public AbstractHorseEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tickCheckCollision(CallbackInfo ci){
        if(Config.rules.get("enable_horse_leaf_breaking")){
            checkForAndBreakNearbyLeaves();
        }
    }

    @Unique
    private void checkForAndBreakNearbyLeaves() {
        AbstractHorseEntity instance = (AbstractHorseEntity) (Object) this;
        Box box = instance.getBoundingBox();
        BlockPos blockPos = BlockPos.ofFloored(box.minX -0.1, box.minY, box.minZ-0.1);
        BlockPos blockPos2 = BlockPos.ofFloored(box.maxX +0.1, box.maxY, box.maxZ+0.1);
        if (instance.getWorld().isRegionLoaded(blockPos, blockPos2)) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (int i = blockPos.getX(); i <= blockPos2.getX(); ++i) {
                for (int j = blockPos.getY(); j <= blockPos2.getY(); ++j) {
                    for (int k = blockPos.getZ(); k <= blockPos2.getZ(); ++k) {
                        mutable.set(i, j, k);
                        BlockState blockState = instance.getWorld().getBlockState(mutable);
                        try {
                            Block collidingBlock = blockState.getBlock();
                            if(collidingBlock != null){
                                if(collidingBlock instanceof LeavesBlock){
                                    boolean isPersistent = blockState.get(PERSISTENT);
                                    if(!isPersistent){
                                        if(!instance.getPassengerList().isEmpty()){
                                            instance.getWorld().breakBlock(mutable, false);
                                            instance.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 2));
                                        }
                                    }
                                }
                            }
                        } catch (Throwable throwable) {
                            CrashReport crashReport = CrashReport.create(throwable, "Colliding entity with block");
                            CrashReportSection crashReportSection = crashReport.addElement("Block being collided with");
                            CrashReportSection.addBlockInfo(crashReportSection, instance.getWorld(), mutable, blockState);
                            throw new CrashException(crashReport);
                        }
                    }
                }
            }
        }
    }
}
