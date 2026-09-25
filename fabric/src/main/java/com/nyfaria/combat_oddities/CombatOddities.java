package com.nyfaria.combat_oddities;

import com.nyfaria.combat_oddities.init.VillagerInit;
import com.nyfaria.combat_oddities.mixin.PoiTypesInvoker;
import com.nyfaria.combat_oddities.trade.SwordsmithTrades;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;

public class CombatOddities implements ModInitializer {
    
    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();
        PoiTypesInvoker.combatOddities$registerBlockStates(VillagerInit.NETHERITE_ANVIL_POI.asHolder(), VillagerInit.NETHERITE_ANVIL_POI.get().matchingStates());
        for (int level = SwordsmithTrades.MIN_LEVEL; level <= SwordsmithTrades.MAX_LEVEL; level++) {
            var trades = SwordsmithTrades.getTrades(level);
            TradeOfferHelper.registerVillagerOffers(VillagerInit.MASTER_SWORDSMITH.get(), level, factories -> factories.addAll(trades));
        }
    }
}
