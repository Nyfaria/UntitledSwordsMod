package com.nyfaria.nyfsmultiloader.item.ability;

import com.nyfaria.nyfsmultiloader.item.SwordAbility;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HoldParticleAbility implements SwordAbility {

    private final ParticleOptions particle;
    private final int interval;

    public HoldParticleAbility(ParticleOptions particle, int interval) {
        this.particle = particle;
        this.interval = interval;
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, LivingEntity holder, boolean selected) {
        if (!selected || !(level instanceof ServerLevel serverLevel)) {
            return;
        }
        if (holder.tickCount % interval != 0) {
            return;
        }
        serverLevel.sendParticles(particle,
                holder.getX(), holder.getY() + holder.getBbHeight() * 0.5, holder.getZ(),
                2, 0.3, 0.4, 0.3, 0.01);
    }
}
