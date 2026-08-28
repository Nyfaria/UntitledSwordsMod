package com.nyfaria.nyfsmultiloader.item.ability;

import com.nyfaria.nyfsmultiloader.item.SwordAbility;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class HitParticleAbility implements SwordAbility {

    private final ParticleOptions particle;
    private final int count;

    public HitParticleAbility(ParticleOptions particle, int count) {
        this.particle = particle;
        this.count = count;
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!(target.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        serverLevel.sendParticles(particle,
                target.getX(), target.getY() + target.getBbHeight() * 0.5, target.getZ(),
                count, 0.3, 0.3, 0.3, 0.05);
    }
}
