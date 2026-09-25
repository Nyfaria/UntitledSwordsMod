package com.nyfaria.combat_oddities.trade;

import com.nyfaria.combat_oddities.init.SwordInit;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class SwordsmithTrades {

    public static final int MIN_LEVEL = 1;
    public static final int MAX_LEVEL = 5;

    private static final int MIN_PRICE = 10;
    private static final int MAX_PRICE = 12;
    private static final int MAX_USES = 3;
    private static final float PRICE_MULTIPLIER = 0.05F;
    private static final int[] XP_BY_LEVEL = {0, 1, 5, 10, 15, 30};

    private static final Map<Integer, List<Supplier<? extends Item>>> SWORDS_BY_LEVEL = Map.of(
            1, List.of(SwordInit.FLAME_ZAPPER, SwordInit.GILDED_FANG, SwordInit.VALENTINE_SWORD,
                    SwordInit.FOSSILIZED_DAGGER, SwordInit.GLASS_SWORD, SwordInit.OOZING_BLADE),
            2, List.of(SwordInit.SEAGLASS_BLADE, SwordInit.PURPLE_AXEBLADE, SwordInit.UV_TRIBLADE,
                    SwordInit.CINDER_BLADE, SwordInit.ACID_EDGE, SwordInit.FLUORESCENT_ZAPPER),
            3, List.of(SwordInit.MAUVE_TAUPE_SWORD, SwordInit.CORROSIVE_SABER, SwordInit.SWORD_OF_SHARDS,
                    SwordInit.FORGED_BLADE, SwordInit.VIOLET_ELECTROCUTER),
            4, List.of(SwordInit.CYCLONE_BLADE, SwordInit.CHARGED_GREATBLADE, SwordInit.BLOODROSE_BLADE,
                    SwordInit.WHISPERER, SwordInit.MOLTEN_SWORD),
            5, List.of(SwordInit.IONIZED_WHIPBLADE, SwordInit.MIDNIGHT_ENDERBLADE, SwordInit.DOUBLE_FLAME_SWORD,
                    SwordInit.GALACTIC_TRIBLADE, SwordInit.CHARGED_HALO_SWORD)
    );

    private SwordsmithTrades() {
    }

    public static List<VillagerTrades.ItemListing> getTrades(int level) {
        int xp = XP_BY_LEVEL[level];
        return SWORDS_BY_LEVEL.getOrDefault(level, List.of()).stream()
                .map(sword -> sell(sword, xp))
                .toList();
    }

    private static VillagerTrades.ItemListing sell(Supplier<? extends Item> sword, int xp) {
        return (trader, random) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, random.nextIntBetweenInclusive(MIN_PRICE, MAX_PRICE)),
                new ItemStack(sword.get()),
                MAX_USES,
                xp,
                PRICE_MULTIPLIER);
    }
}
