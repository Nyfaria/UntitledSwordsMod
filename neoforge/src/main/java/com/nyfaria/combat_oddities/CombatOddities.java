package com.nyfaria.combat_oddities;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MODID)
public class CombatOddities {

    public CombatOddities(IEventBus eventBus) {
        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();
    }
}