package com.multibana.simplyreimagined;

import com.multibana.simplyreimagined.config.Config;
import net.fabricmc.api.ModInitializer;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;
import net.minecraft.item.map.MapIcon;
import net.minecraft.item.map.MapState;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.world.gen.structure.Structure;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SimplyReimagined implements ModInitializer {
	public static final Logger debugLogger = LoggerFactory.getLogger("simplyreimagined");
	@Override
	public void onInitialize() {
		Config.createIfAbsent();
		Config.reload();
		debugLogger.info(Config.rules.toString());
	}
	public static class BuyForMultipleEmeraldsFactory
			implements TradeOffers.Factory {
		private final Item buy;
		private final int price;
		private final int emeralds;
		private final int maxUses;
		private final int experience;
		private final float multiplier;

		public BuyForMultipleEmeraldsFactory(ItemConvertible item, int price, int emeralds, int maxUses, int experience) {
			this.buy = item.asItem();
			this.price = price;
			this.emeralds = emeralds;
			this.maxUses = maxUses;
			this.experience = experience;
			this.multiplier = 0.05f;
		}

		@Override
		public TradeOffer create(Entity entity, Random random) {
			ItemStack itemStack = new ItemStack(this.buy, this.price);
			return new TradeOffer(itemStack, new ItemStack(Items.EMERALD, this.emeralds), this.maxUses, this.experience, this.multiplier);
		}
	}


	public static class EnchantBookFactory
			implements TradeOffers.Factory {
		private final int experience;

		public EnchantBookFactory(int experience) {
			this.experience = experience;
		}

		@Override
		public TradeOffer create(Entity entity, Random random) {
			List<Enchantment> sold_enchantment_list = new ArrayList<>();
			sold_enchantment_list.add(Enchantments.UNBREAKING);
			sold_enchantment_list.add(Enchantments.SILK_TOUCH);
			sold_enchantment_list.add(Enchantments.LOOTING);
			List<Integer> price_list = new ArrayList<>();
			price_list.add(45);
			price_list.add(60);
			price_list.add(60);
			int random_enchantment_idx = random.nextInt(sold_enchantment_list.size());
			Enchantment enchantment = sold_enchantment_list.get(random_enchantment_idx);
			int price = price_list.get(random_enchantment_idx);
			ItemStack itemStack = EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(enchantment, 1));
			return new TradeOffer(new ItemStack(Items.EMERALD, price), new ItemStack(Items.BOOK), itemStack, 12, this.experience, 0.1f);
		}
	}

	public static class ProcessItemFactory
			implements TradeOffers.Factory {
		private final ItemStack secondBuy;
		private final int secondCount;
		private final int price;
		private final ItemStack sell;
		private final int sellCount;
		private final int maxUses;
		private final int experience;
		private final float multiplier;

		public ProcessItemFactory(ItemConvertible item, int secondCount, ItemConvertible sellItem, int sellCount, int maxUses, int experience) {
			this(item, secondCount, 1, sellItem, sellCount, maxUses, experience);
		}

		public ProcessItemFactory(ItemConvertible item, int secondCount, int price, ItemConvertible sellItem, int sellCount, int maxUses, int experience) {
			this.secondBuy = new ItemStack(item);
			this.secondCount = secondCount;
			this.price = price;
			this.sell = new ItemStack(sellItem);
			this.sellCount = sellCount;
			this.maxUses = maxUses;
			this.experience = experience;
			this.multiplier = 0.05f;
		}

		@Override
		@Nullable
		public TradeOffer create(Entity entity, Random random) {
			return new TradeOffer(new ItemStack(Items.EMERALD, this.price), new ItemStack(this.secondBuy.getItem(), this.secondCount), new ItemStack(this.sell.getItem(), this.sellCount), this.maxUses, this.experience, this.multiplier);
		}
	}


	// all i changed in this class is the multiplier from 0.05 to 0.04 just to have slightly less crazy discounts
	public static class BuyForOneEmeraldFactory
			implements TradeOffers.Factory {
		private final Item buy;
		private final int price;
		private final int maxUses;
		private final int experience;
		private final float multiplier;

		public BuyForOneEmeraldFactory(ItemConvertible item, int price, int maxUses, int experience) {
			this.buy = item.asItem();
			this.price = price;
			this.maxUses = maxUses;
			this.experience = experience;
			this.multiplier = 0.04f;
		}

		@Override
		public TradeOffer create(Entity entity, Random random) {
			ItemStack itemStack = new ItemStack(this.buy, this.price);
			return new TradeOffer(itemStack, new ItemStack(Items.EMERALD), this.maxUses, this.experience, this.multiplier);
		}
	}

	
}