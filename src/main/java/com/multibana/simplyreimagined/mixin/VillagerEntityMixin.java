package com.multibana.simplyreimagined.mixin;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.multibana.simplyreimagined.SimplyReimagined;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityInteraction;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapIcon;
import net.minecraft.registry.tag.StructureTags;
import net.minecraft.util.Util;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Map;

@Mixin(VillagerEntity.class)
public class VillagerEntityMixin {

    @Unique
    private static Int2ObjectMap<TradeOffers.Factory[]> copyToFastUtilMap(ImmutableMap<Integer, TradeOffers.Factory[]> map) {
        return new Int2ObjectOpenHashMap<TradeOffers.Factory[]>(map);
    }

    @Unique
    private static final Map<VillagerProfession, Int2ObjectMap<TradeOffers.Factory[]>> PROFESSION_TO_TRADES =
            Util.make(Maps.newHashMap(), map -> {
                map.put(VillagerProfession.FARMER, copyToFastUtilMap(ImmutableMap.of(
                        1, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.WHEAT, 22, 6, 2),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.POTATO, 28, 6, 2),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.CARROT, 24, 6, 2),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.BEETROOT, 19, 6, 2),
                                new TradeOffers.SellItemFactory(Items.BREAD, 1, 8, 16, 1)},
                        2, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Blocks.PUMPKIN, 12, 6, 5),
                                new TradeOffers.SellItemFactory(Items.PUMPKIN_PIE, 1, 6, 5),
                                new TradeOffers.SellItemFactory(Items.APPLE, 1, 6, 16, 5)},
                        3, new TradeOffers.Factory[]{
                                new TradeOffers.SellItemFactory(Items.COOKIE, 2, 18, 10),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Blocks.MELON, 10, 6, 5)},
                        4, new TradeOffers.Factory[]{
                                new TradeOffers.SellItemFactory(Blocks.CAKE, 1, 1, 12, 15),
                                new TradeOffers.SellItemFactory(Items.BONE_MEAL, 1, 8, 12, 5),
                                new TradeOffers.SellSuspiciousStewFactory(StatusEffects.NIGHT_VISION, 100, 15),
                                new TradeOffers.SellSuspiciousStewFactory(StatusEffects.JUMP_BOOST, 160, 15),
                                new TradeOffers.SellSuspiciousStewFactory(StatusEffects.SATURATION, 7, 15)},
                        5, new TradeOffers.Factory[]{
                                new TradeOffers.SellItemFactory(Items.GOLDEN_CARROT, 3, 4,8, 30),
                                new TradeOffers.SellItemFactory(Items.MILK_BUCKET, 1, 1, 16, 30),
                                new TradeOffers.SellItemFactory(Items.GLISTERING_MELON_SLICE, 3, 4, 30)})));


                map.put(VillagerProfession.FISHERMAN, copyToFastUtilMap(ImmutableMap.of(
                        1, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.STRING, 24, 6, 3),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.COAL, 14, 8, 2),
                                new TradeOffers.ProcessItemFactory(Items.COD, 10, Items.COOKED_COD, 11, 7, 4),
                                new TradeOffers.SellItemFactory(Items.COD_BUCKET, 1, 1, 16, 5)},
                        2, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.COD, 11, 8, 5),
                                new TradeOffers.ProcessItemFactory(Items.SALMON, 10, Items.COOKED_SALMON, 11, 7, 5),
                                new TradeOffers.SellItemFactory(Items.CAMPFIRE, 1, 12, 5)},
                        3, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.SALMON, 13, 10, 15),
                                new TradeOffers.SellItemFactory(Items.TNT, 1, 2, 16,15)},
                        4, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.PUFFERFISH, 4, 3, 20),
                                new SimplyReimagined.BuyForMultipleEmeraldsFactory(Items.TROPICAL_FISH, 1, 8, 12, 25)},
                        5, new TradeOffers.Factory[]{
                                new TradeOffers.SellItemFactory(Items.PUFFERFISH_BUCKET, 6, 1, 30),
                                new TradeOffers.SellEnchantedToolFactory(Items.FISHING_ROD, 40, 3, 30, 0.1f)})));


                map.put(VillagerProfession.SHEPHERD, copyToFastUtilMap(ImmutableMap.of(
                        1, new TradeOffers.Factory[]{
                                new TradeOffers.SellItemFactory(Items.SHEARS, 1, 1, 16, 5),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Blocks.WHITE_WOOL, 24, 6, 2),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Blocks.BROWN_WOOL, 21, 6, 2),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Blocks.BLACK_WOOL, 22, 6, 2),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Blocks.GRAY_WOOL, 22, 6, 2)},
                        2, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.WHITE_DYE, 16, 4, 10),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.GRAY_DYE, 18, 6, 10),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.BLACK_DYE, 6, 6, 10),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.LIGHT_BLUE_DYE, 7, 3, 10),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.LIME_DYE, 11, 3, 10),
                                new TradeOffers.SellItemFactory(Blocks.WHITE_WOOL, 1, 8, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.ORANGE_WOOL, 1, 8, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.MAGENTA_WOOL, 1, 8, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.LIGHT_BLUE_WOOL, 1, 8, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.YELLOW_WOOL, 1, 8, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.LIME_WOOL, 1, 8, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.PINK_WOOL, 1, 8, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.GRAY_WOOL, 1, 8, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.LIGHT_GRAY_WOOL, 1, 8, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.CYAN_WOOL, 1, 8, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.PURPLE_WOOL, 1, 8, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.BLUE_WOOL, 1, 8, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.BROWN_WOOL, 1, 8, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.GREEN_WOOL, 1, 8, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.RED_WOOL, 1, 8, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.BLACK_WOOL, 1, 8, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.WHITE_CARPET, 1, 12, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.ORANGE_CARPET, 1, 12, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.MAGENTA_CARPET, 1, 12, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.LIGHT_BLUE_CARPET, 1, 12, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.YELLOW_CARPET, 1, 12, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.LIME_CARPET, 1, 12, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.PINK_CARPET, 1, 12, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.GRAY_CARPET, 1, 12, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.LIGHT_GRAY_CARPET, 1, 12, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.CYAN_CARPET, 1, 12, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.PURPLE_CARPET, 1, 12, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.BLUE_CARPET, 1, 12, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.BROWN_CARPET, 1, 12, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.GREEN_CARPET, 1, 12, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.RED_CARPET, 1, 12, 16, 5),
                                new TradeOffers.SellItemFactory(Blocks.BLACK_CARPET, 1, 12, 16, 5)},
                        3, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.YELLOW_DYE, 16, 3, 20),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.LIGHT_GRAY_DYE, 22, 4, 20),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.ORANGE_DYE, 24, 5, 20),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.RED_DYE, 16, 3, 20),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.PINK_DYE, 22, 5, 20),
                                new TradeOffers.SellItemFactory(Blocks.WHITE_BED, 1, 1, 12, 10),
                                new TradeOffers.SellItemFactory(Blocks.YELLOW_BED, 1, 1, 12, 10),
                                new TradeOffers.SellItemFactory(Blocks.RED_BED, 1, 1, 12, 10),
                                new TradeOffers.SellItemFactory(Blocks.BLACK_BED, 1, 1, 12, 10),
                                new TradeOffers.SellItemFactory(Blocks.BLUE_BED, 1, 1, 12, 10),
                                new TradeOffers.SellItemFactory(Blocks.BROWN_BED, 1, 1, 12, 10),
                                new TradeOffers.SellItemFactory(Blocks.CYAN_BED, 1, 1, 12, 10),
                                new TradeOffers.SellItemFactory(Blocks.GRAY_BED, 1, 1, 12, 10),
                                new TradeOffers.SellItemFactory(Blocks.GREEN_BED, 1, 1, 12, 10),
                                new TradeOffers.SellItemFactory(Blocks.LIGHT_BLUE_BED, 1, 1, 12, 10),
                                new TradeOffers.SellItemFactory(Blocks.LIGHT_GRAY_BED, 1, 1, 12, 10),
                                new TradeOffers.SellItemFactory(Blocks.LIME_BED, 1, 1, 12, 10),
                                new TradeOffers.SellItemFactory(Blocks.MAGENTA_BED, 1, 1, 12, 10),
                                new TradeOffers.SellItemFactory(Blocks.ORANGE_BED, 1, 1, 12, 10),
                                new TradeOffers.SellItemFactory(Blocks.PINK_BED, 1, 1, 12, 10),
                                new TradeOffers.SellItemFactory(Blocks.PURPLE_BED, 1, 1, 12, 10)},
                        4, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.BROWN_DYE, 18, 2, 30),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.PURPLE_DYE, 17, 3, 30),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.BLUE_DYE, 14, 3, 30),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.GREEN_DYE, 12, 2, 30),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.MAGENTA_DYE, 16, 3, 30),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.CYAN_DYE, 12, 2, 30),
                                new TradeOffers.SellItemFactory(Items.WHITE_BANNER, 1, 1, 12, 15),
                                new TradeOffers.SellItemFactory(Items.BLUE_BANNER, 1, 1, 12, 15),
                                new TradeOffers.SellItemFactory(Items.LIGHT_BLUE_BANNER, 1, 1, 12, 15),
                                new TradeOffers.SellItemFactory(Items.RED_BANNER, 1, 1, 12, 15),
                                new TradeOffers.SellItemFactory(Items.PINK_BANNER, 1, 1, 12, 15),
                                new TradeOffers.SellItemFactory(Items.GREEN_BANNER, 1, 1, 12, 15),
                                new TradeOffers.SellItemFactory(Items.LIME_BANNER, 1, 1, 12, 15),
                                new TradeOffers.SellItemFactory(Items.GRAY_BANNER, 1, 1, 12, 15),
                                new TradeOffers.SellItemFactory(Items.BLACK_BANNER, 1, 1, 12, 15),
                                new TradeOffers.SellItemFactory(Items.PURPLE_BANNER, 1, 1, 12, 15),
                                new TradeOffers.SellItemFactory(Items.MAGENTA_BANNER, 1, 1, 12, 15),
                                new TradeOffers.SellItemFactory(Items.CYAN_BANNER, 1, 1, 12, 15),
                                new TradeOffers.SellItemFactory(Items.BROWN_BANNER, 1, 1, 12, 15),
                                new TradeOffers.SellItemFactory(Items.YELLOW_BANNER, 1, 1, 12, 15),
                                new TradeOffers.SellItemFactory(Items.ORANGE_BANNER, 1, 1, 12, 15),
                                new TradeOffers.SellItemFactory(Items.LIGHT_GRAY_BANNER, 1, 1, 12, 15)},
                        5, new TradeOffers.Factory[]{
                                new TradeOffers.SellItemFactory(Items.PAINTING, 1, 8, 30),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.SHEARS, 1, 2, 30)}
                )));


                map.put(VillagerProfession.FLETCHER, copyToFastUtilMap(ImmutableMap.of(
                        1, new TradeOffers.Factory[]{
                                new TradeOffers.SellItemFactory(Items.ARROW, 1, 16, 1),
                                new TradeOffers.ProcessItemFactory(Blocks.GRAVEL, 20, Items.FLINT, 20, 12, 5)},
                        2, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.FLINT, 24, 6, 5),
                                new TradeOffers.SellItemFactory(Items.CROSSBOW, 1, 1, 5)},
                        3, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.STRING, 29, 6, 15),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.TRIPWIRE_HOOK, 12, 7, 5)},
                        4, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.FEATHER, 26, 12, 15),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Blocks.TARGET, 2, 2, 20)},
                        5, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.GUNPOWDER, 10, 8, 30),
                                new TradeOffers.SellItemFactory(Items.ARROW, 1, 32, 2, 30),
                        })));


                map.put(VillagerProfession.LIBRARIAN, copyToFastUtilMap(ImmutableMap.of(
                        1, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.PAPER, 33, 6, 3),
                                new TradeOffers.SellItemFactory(Blocks.BOOKSHELF, 1, 3, 8, 2)},
                        2, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.BOOK, 5, 7, 3),
                                new TradeOffers.SellItemFactory(Items.LANTERN, 1, 6, 5)},
                        3, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.INK_SAC, 8, 8, 3),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.WRITABLE_BOOK, 1, 2, 2),
                                new TradeOffers.SellItemFactory(Items.COMPASS, 1, 1, 5)},
                        4, new TradeOffers.Factory[]{
                                new TradeOffers.SellItemFactory(Items.CLOCK, 5, 1, 10),
                                new TradeOffers.SellItemFactory(Items.NAME_TAG, 20, 1, 20)},
                        5, new TradeOffers.Factory[]{
                                new SimplyReimagined.EnchantBookFactory(30)
                            })));


                map.put(VillagerProfession.CARTOGRAPHER, copyToFastUtilMap(ImmutableMap.of(
                        1, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.PAPER, 36, 10, 5),
                                new TradeOffers.SellItemFactory(Items.MAP, 1, 1, 5)},
                        2, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.AMETHYST_SHARD, 3, 6, 10),
                                new TradeOffers.SellMapFactory(4, StructureTags.VILLAGE, "filled_map.village", MapIcon.Type.BANNER_YELLOW, 12, 10)},
                        3, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.COMPASS, 1, 12, 10),
                                new TradeOffers.SellMapFactory(8, StructureTags.ON_OCEAN_EXPLORER_MAPS, "filled_map.monument", MapIcon.Type.MONUMENT, 12, 10),
                                new TradeOffers.SellMapFactory(9, StructureTags.ON_WOODLAND_EXPLORER_MAPS, "filled_map.mansion", MapIcon.Type.MANSION, 12, 10)},
                        4, new TradeOffers.Factory[]{
                                new TradeOffers.SellItemFactory(Items.ITEM_FRAME, 1, 4, 15),
                                new TradeOffers.SellItemFactory(Items.WHITE_BANNER, 1, 1, 15),
                                new TradeOffers.SellItemFactory(Items.BLUE_BANNER, 1, 1, 15),
                                new TradeOffers.SellItemFactory(Items.LIGHT_BLUE_BANNER, 1, 1, 15),
                                new TradeOffers.SellItemFactory(Items.RED_BANNER, 1, 1, 15),
                                new TradeOffers.SellItemFactory(Items.PINK_BANNER, 1, 1, 15),
                                new TradeOffers.SellItemFactory(Items.GREEN_BANNER, 1, 1, 15),
                                new TradeOffers.SellItemFactory(Items.LIME_BANNER, 1, 1, 15),
                                new TradeOffers.SellItemFactory(Items.GRAY_BANNER, 1, 1, 15),
                                new TradeOffers.SellItemFactory(Items.BLACK_BANNER, 1, 1, 15),
                                new TradeOffers.SellItemFactory(Items.PURPLE_BANNER, 1, 1, 15),
                                new TradeOffers.SellItemFactory(Items.MAGENTA_BANNER, 1, 1, 15),
                                new TradeOffers.SellItemFactory(Items.CYAN_BANNER, 1, 1, 15),
                                new TradeOffers.SellItemFactory(Items.BROWN_BANNER, 1, 1, 15),
                                new TradeOffers.SellItemFactory(Items.YELLOW_BANNER, 1, 1, 15),
                                new TradeOffers.SellItemFactory(Items.ORANGE_BANNER, 1, 1, 15),
                                new TradeOffers.SellItemFactory(Items.LIGHT_GRAY_BANNER, 1, 1, 15)},
                        5, new TradeOffers.Factory[]{
                                new TradeOffers.SellItemFactory(Items.GLOBE_BANNER_PATTERN, 12, 1, 30),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.ECHO_SHARD, 1, 8, 30)})));


                map.put(VillagerProfession.CLERIC, copyToFastUtilMap(ImmutableMap.of(
                        1, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.ROTTEN_FLESH, 32, 6, 3),
                                new TradeOffers.SellItemFactory(Items.REDSTONE, 1, 24, 1)},
                        2, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.GOLD_INGOT, 1, 12, 2),
                                new TradeOffers.SellItemFactory(Items.LAPIS_LAZULI, 1, 5, 2)},
                        3, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.POISONOUS_POTATO, 1, 12, 5),
                                new TradeOffers.SellItemFactory(Blocks.GLOWSTONE, 1, 2, 12, 3)},
                        4, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.MAGMA_CREAM, 1, 12, 5),
                                new TradeOffers.SellItemFactory(Items.ENDER_PEARL, 2, 1, 4)},
                        5, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.NETHER_WART, 26, 10, 30),
                                new TradeOffers.SellItemFactory(Items.GOLDEN_APPLE, 8, 1, 30),
                                new TradeOffers.SellItemFactory(Items.ENCHANTED_GOLDEN_APPLE, 60, 1, 1,30)})));


                map.put(VillagerProfession.ARMORER, copyToFastUtilMap(ImmutableMap.of(
                        1, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.COAL, 19, 8, 2),
                                new TradeOffers.SellItemFactory(new ItemStack(Items.IRON_LEGGINGS), 1, 1, 12, 3, 0.05f),
                                new TradeOffers.SellItemFactory(new ItemStack(Items.IRON_BOOTS), 1, 1, 12, 3, 0.05f),
                                new TradeOffers.SellItemFactory(new ItemStack(Items.IRON_HELMET), 1, 1, 12, 3, 0.05f),
                                new TradeOffers.SellItemFactory(new ItemStack(Items.IRON_CHESTPLATE), 1, 1, 12, 3, 0.05f)},
                        2, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.IRON_INGOT, 7, 9, 5),
                                new TradeOffers.SellItemFactory(new ItemStack(Items.BELL), 5, 1, 1, 10, 0.05f),
                                new TradeOffers.SellItemFactory(new ItemStack(Items.CHAINMAIL_BOOTS), 1, 1, 1, 5, 0.1f),
                                new TradeOffers.SellItemFactory(new ItemStack(Items.CHAINMAIL_LEGGINGS), 1, 1, 1, 5, 0.1f)},
                        3, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.SCUTE, 3, 7, 20),
                                new SimplyReimagined.BuyForMultipleEmeraldsFactory(Items.DIAMOND, 1, 8,1, 20),
                                new TradeOffers.SellItemFactory(new ItemStack(Items.CHAINMAIL_HELMET), 2, 1, 1, 10, 0.1f),
                                new TradeOffers.SellItemFactory(new ItemStack(Items.CHAINMAIL_CHESTPLATE), 2, 1, 1, 10, 0.1f),
                                new TradeOffers.SellItemFactory(new ItemStack(Items.SHIELD), 8, 1, 2, 10, 0.1f)},
                        4, new TradeOffers.Factory[]{
                                new TradeOffers.SellItemFactory(new ItemStack(Items.DIAMOND_LEGGINGS), 56, 1, 3, 15, 0.1f),
                                new TradeOffers.SellItemFactory(new ItemStack(Items.DIAMOND_BOOTS), 32, 1, 3, 15, 0.1f)},
                        5, new TradeOffers.Factory[]{
                                new TradeOffers.SellItemFactory(new ItemStack(Items.DIAMOND_HELMET), 40, 1, 3, 30, 0.1f),
                                new TradeOffers.SellItemFactory(new ItemStack(Items.DIAMOND_CHESTPLATE), 64, 1, 3, 30, 0.1f)})));


                map.put(VillagerProfession.WEAPONSMITH, copyToFastUtilMap(ImmutableMap.of(
                        1, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.COAL, 17, 9, 2),
                                new TradeOffers.SellItemFactory(new ItemStack(Items.IRON_AXE), 1, 1, 6, 5, 0.1f),
                                new TradeOffers.SellItemFactory(new ItemStack(Items.IRON_SWORD), 1, 1, 6, 5, 0.01f)},
                        2, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.IRON_INGOT, 7, 9, 5),
                                new TradeOffers.SellItemFactory(new ItemStack(Items.BELL), 5, 1, 1, 10, 0.1f)},
                        3, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.FLINT, 24, 7, 5),
                                new SimplyReimagined.BuyForMultipleEmeraldsFactory(Items.ANVIL, 1, 5, 2, 20)},
                        4, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForMultipleEmeraldsFactory(Items.DIAMOND, 1, 8,1, 30),
                                new TradeOffers.SellItemFactory(new ItemStack(Items.DIAMOND_AXE), 24, 1, 1, 15,0.1f)},
                        5, new TradeOffers.Factory[]{
                                new TradeOffers.SellItemFactory(new ItemStack(Items.DIAMOND_SWORD), 17, 1, 1, 30,0.1f),
                                new SimplyReimagined.BuyForMultipleEmeraldsFactory(Items.CHIPPED_ANVIL, 1, 4, 1, 30)
                        })));


                map.put(VillagerProfession.TOOLSMITH, copyToFastUtilMap(ImmutableMap.of(
                        1, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.COAL, 15, 6, 2),
                                new TradeOffers.SellItemFactory(new ItemStack(Items.STONE_AXE), 1, 1, 12, 5, 0.1f),
                                new TradeOffers.SellItemFactory(new ItemStack(Items.STONE_SHOVEL), 1, 1, 12, 5, 0.1f),
                                new TradeOffers.SellItemFactory(new ItemStack(Items.STONE_PICKAXE), 1, 1, 12, 5, 0.1f),
                                new TradeOffers.SellItemFactory(new ItemStack(Items.STONE_HOE), 1, 1, 12, 5, 0.1f)},
                        2, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.IRON_INGOT, 8, 6, 5),
                                new TradeOffers.SellItemFactory(new ItemStack(Items.BELL), 5, 1, 1, 10, 0.1f)},
                        3, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.FLINT, 32, 12, 5),
                                new TradeOffers.SellItemFactory(Items.IRON_AXE, 1, 1, 7 ),
                                new TradeOffers.SellItemFactory(Items.IRON_SHOVEL, 1, 1, 7),
                                new TradeOffers.SellItemFactory(Items.IRON_PICKAXE, 1, 1, 7),
                                new TradeOffers.SellItemFactory(new ItemStack(Items.DIAMOND_HOE), 16, 1, 1, 10, 0.1f)},
                        4, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForMultipleEmeraldsFactory(Items.DIAMOND, 1, 8, 1, 30),
                                new TradeOffers.SellItemFactory(new ItemStack(Items.DIAMOND_AXE), 24, 3, 1,15, 0.1f ),
                                new TradeOffers.SellItemFactory(new ItemStack(Items.DIAMOND_SHOVEL), 8, 3, 1,15, 0.1f)},
                        5, new TradeOffers.Factory[]{
                                new TradeOffers.SellItemFactory(new ItemStack(Items.DIAMOND_PICKAXE), 24, 3, 1,30, 0.1f)})));


                map.put(VillagerProfession.BUTCHER, copyToFastUtilMap(ImmutableMap.of(
                        1, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.CHICKEN, 23, 12, 2),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.PORKCHOP, 11, 12, 2),
                                new TradeOffers.SellItemFactory(Items.COOKED_BEEF, 1, 4, 1)},
                        2, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.COAL, 18, 10, 2),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.HONEY_BOTTLE, 2, 3, 5),
                                new TradeOffers.SellItemFactory(Items.COOKED_PORKCHOP, 1, 5, 16, 5),
                                new TradeOffers.SellItemFactory(Items.COOKED_CHICKEN, 1, 8, 16, 5)},
                        3, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.MUTTON, 11, 9, 20),
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.BEEF, 10, 7, 20)},
                        4, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.DRIED_KELP_BLOCK, 32, 6, 30)},
                        5, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.SWEET_BERRIES, 16, 8, 30)})));


                map.put(VillagerProfession.LEATHERWORKER, copyToFastUtilMap(ImmutableMap.of(
                        1, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.LEATHER, 8, 12, 2),
                                new TradeOffers.SellDyedArmorFactory(Items.LEATHER_LEGGINGS, 1, 12, 5),
                                new TradeOffers.SellDyedArmorFactory(Items.LEATHER_CHESTPLATE, 1, 12, 5)},
                        2, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.FLINT, 26, 6, 5),
                                new TradeOffers.SellDyedArmorFactory(Items.LEATHER_HELMET, 1, 12, 10),
                                new TradeOffers.SellDyedArmorFactory(Items.LEATHER_BOOTS, 1, 12, 10)},
                        3, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Blocks.CAULDRON, 1, 2, 20),
                                new TradeOffers.SellDyedArmorFactory(Items.LEATHER_CHESTPLATE, 1, 12, 10)},
                        4, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.SCUTE, 2, 9, 30),
                                new TradeOffers.SellDyedArmorFactory(Items.LEATHER_HORSE_ARMOR, 6, 4, 15)},
                        5, new TradeOffers.Factory[]{
                                new TradeOffers.SellItemFactory(new ItemStack(Items.SADDLE), 6, 1, 2, 30, 0.1f),
                                new TradeOffers.SellDyedArmorFactory(Items.LEATHER_HELMET, 1, 12, 30)})));


                map.put(VillagerProfession.MASON, copyToFastUtilMap(ImmutableMap.of(
                        1, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.CLAY_BALL, 42, 6, 2),
                                new TradeOffers.SellItemFactory(Items.BRICK, 1, 32, 12, 1)},
                        2, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Blocks.STONE, 64, 4, 3),
                                new TradeOffers.SellItemFactory(Blocks.CHISELED_STONE_BRICKS, 1, 32, 16, 5)},
                        3, new TradeOffers.Factory[]{
                                new TradeOffers.SellItemFactory(Blocks.DRIPSTONE_BLOCK, 1, 4, 16, 10),
                                new TradeOffers.SellItemFactory(Blocks.QUARTZ_BLOCK, 1, 8, 12, 15)},
                        4, new TradeOffers.Factory[]{
                                new SimplyReimagined.BuyForOneEmeraldFactory(Items.QUARTZ, 16, 6, 20),
                                new TradeOffers.SellItemFactory(Blocks.ORANGE_TERRACOTTA, 1, 8, 12, 15),
                                new TradeOffers.SellItemFactory(Blocks.WHITE_TERRACOTTA, 1, 8, 12, 15),
                                new TradeOffers.SellItemFactory(Blocks.BLUE_TERRACOTTA, 1, 8, 12, 15),
                                new TradeOffers.SellItemFactory(Blocks.LIGHT_BLUE_TERRACOTTA, 1, 8, 12, 15),
                                new TradeOffers.SellItemFactory(Blocks.GRAY_TERRACOTTA, 1, 8, 12, 15),
                                new TradeOffers.SellItemFactory(Blocks.LIGHT_GRAY_TERRACOTTA, 1, 8, 12, 15),
                                new TradeOffers.SellItemFactory(Blocks.BLACK_TERRACOTTA, 1, 8, 12, 15),
                                new TradeOffers.SellItemFactory(Blocks.RED_TERRACOTTA, 1, 8, 12, 15),
                                new TradeOffers.SellItemFactory(Blocks.PINK_TERRACOTTA, 1, 8, 12, 15),
                                new TradeOffers.SellItemFactory(Blocks.MAGENTA_TERRACOTTA, 1, 8, 12, 15),
                                new TradeOffers.SellItemFactory(Blocks.LIME_TERRACOTTA, 1, 8, 12, 15),
                                new TradeOffers.SellItemFactory(Blocks.GREEN_TERRACOTTA, 1, 8, 12, 15),
                                new TradeOffers.SellItemFactory(Blocks.CYAN_TERRACOTTA, 1, 8, 12, 15),
                                new TradeOffers.SellItemFactory(Blocks.PURPLE_TERRACOTTA, 1, 8, 12, 15),
                                new TradeOffers.SellItemFactory(Blocks.YELLOW_TERRACOTTA, 1, 8, 12, 15),
                                new TradeOffers.SellItemFactory(Blocks.BROWN_TERRACOTTA, 1, 8, 12, 15)},
                        5, new TradeOffers.Factory[]{
                                new TradeOffers.SellItemFactory(Blocks.QUARTZ_PILLAR, 1, 12, 12, 30),
                                new SimplyReimagined.ProcessItemFactory(Blocks.DIRT, 32, Blocks.MUD_BRICKS, 32, 16, 10)})));
            });

    @ModifyArgs(method = "fillRecipes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/VillagerEntity;fillRecipesFromPool(Lnet/minecraft/village/TradeOfferList;[Lnet/minecraft/village/TradeOffers$Factory;I)V"))
    private void fillWithReimaginedTrades(Args args, @Local LocalRef<VillagerData> data){
        Int2ObjectMap<TradeOffers.Factory[]> int2ObjectMap = PROFESSION_TO_TRADES.get(data.get().getProfession());
        TradeOffers.Factory[] newFactories = (TradeOffers.Factory[])int2ObjectMap.get(data.get().getLevel());
        args.set(1, newFactories);
    }

    @Inject(method = "onInteractionWith", at=@At("HEAD"), cancellable = true)
    private void noCureDiscounts(EntityInteraction interaction, Entity entity, CallbackInfo ci){
        if (interaction == EntityInteraction.ZOMBIE_VILLAGER_CURED) {
            ci.cancel();
        }
    }

}
