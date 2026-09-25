package com.nyfaria.combat_oddities.events;

import com.nyfaria.combat_oddities.Constants;
import com.nyfaria.combat_oddities.init.VillagerInit;
import com.nyfaria.combat_oddities.trade.SwordsmithTrades;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

@EventBusSubscriber(modid = Constants.MODID)
public class CommonGameEvents {

    @SubscribeEvent
    public static void villagerTrades(VillagerTradesEvent event) {
        if (event.getType() != VillagerInit.MASTER_SWORDSMITH.get()) {
            return;
        }
        for (int level = SwordsmithTrades.MIN_LEVEL; level <= SwordsmithTrades.MAX_LEVEL; level++) {
            event.getTrades().get(level).addAll(SwordsmithTrades.getTrades(level));
        }
    }
}
