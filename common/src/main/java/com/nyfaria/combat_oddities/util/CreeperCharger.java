package com.nyfaria.combat_oddities.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.monster.Creeper;

public final class CreeperCharger {

    private CreeperCharger() {
    }

    public static void charge(ServerLevel level, Creeper creeper) {
        LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);
        if (bolt == null) {
            return;
        }
        bolt.moveTo(creeper.getX(), creeper.getY(), creeper.getZ());
        level.addFreshEntity(bolt);
    }
}
