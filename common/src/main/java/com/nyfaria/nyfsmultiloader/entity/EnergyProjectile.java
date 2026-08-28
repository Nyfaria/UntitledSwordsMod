package com.nyfaria.nyfsmultiloader.entity;

import com.nyfaria.nyfsmultiloader.init.ProjectileInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class EnergyProjectile extends ThrowableItemProjectile {

    private float damage = 4.0F;
    private int igniteSeconds = 0;
    private boolean powerCreepers = false;
    private boolean lavaOnImpact = false;
    private Holder<MobEffect> effect = null;
    private int effectDuration = 100;
    private int effectAmplifier = 0;
    private ParticleOptions impactParticle = ParticleTypes.CRIT;

    public EnergyProjectile(EntityType<? extends EnergyProjectile> type, Level level) {
        super(type, level);
    }

    public EnergyProjectile(Level level, LivingEntity shooter) {
        super(ProjectileInit.ENERGY_PROJECTILE.get(), shooter, level);
    }

    public EnergyProjectile damage(float damage) {
        this.damage = damage;
        return this;
    }

    public EnergyProjectile ignite(int seconds) {
        this.igniteSeconds = seconds;
        return this;
    }

    public EnergyProjectile powerCreepers(boolean value) {
        this.powerCreepers = value;
        return this;
    }

    public EnergyProjectile lavaOnImpact(boolean value) {
        this.lavaOnImpact = value;
        return this;
    }

    public EnergyProjectile effect(Holder<MobEffect> effect, int duration, int amplifier) {
        this.effect = effect;
        this.effectDuration = duration;
        this.effectAmplifier = amplifier;
        return this;
    }

    public EnergyProjectile impactParticle(ParticleOptions particle) {
        this.impactParticle = particle;
        return this;
    }

    @Override
    protected Item getDefaultItem() {
        return Items.FIRE_CHARGE;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (level().isClientSide()) {
            return;
        }
        result.getEntity().hurt(damageSources().thrown(this, getOwner()), damage);
        if (result.getEntity() instanceof LivingEntity target) {
            if (igniteSeconds > 0) {
                target.igniteForSeconds(igniteSeconds);
            }
            if (effect != null) {
                target.addEffect(new MobEffectInstance(effect, effectDuration, effectAmplifier));
            }
            if (powerCreepers && target instanceof Creeper creeper && !creeper.isPowered() && level() instanceof ServerLevel serverLevel) {
                LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(serverLevel);
                if (bolt != null) {
                    bolt.moveTo(creeper.getX(), creeper.getY(), creeper.getZ());
                    bolt.setVisualOnly(true);
                    serverLevel.addFreshEntity(bolt);
                }
            }
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (level().isClientSide()) {
            return;
        }
        if (lavaOnImpact && result instanceof BlockHitResult blockHit) {
            BlockPos pos = blockHit.getBlockPos().relative(blockHit.getDirection());
            if (level().getBlockState(pos).canBeReplaced()) {
                level().setBlock(pos, Blocks.LAVA.defaultBlockState(), 3);
            }
        }
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(impactParticle, getX(), getY(), getZ(), 8, 0.1, 0.1, 0.1, 0.1);
        }
        discard();
    }
}
