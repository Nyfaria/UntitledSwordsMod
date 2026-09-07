package com.nyfaria.nyfsmultiloader.item.ability;

import com.nyfaria.nyfsmultiloader.item.SwordAbility;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class DashAbility implements SwordAbility {

    private final double strength;
    private final float trailDamage;
    private final int cooldownTicks;

    public DashAbility(double strength, float trailDamage, int cooldownTicks) {
        this.strength = strength;
        this.trailDamage = trailDamage;
        this.cooldownTicks = cooldownTicks;
    }

    @Override
    public boolean onUse(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(stack.getItem())) {
            return false;
        }
        player.getCooldowns().addCooldown(stack.getItem(), cooldownTicks);
        Vec3 look = player.getLookAngle();
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.PHANTOM_FLAP, SoundSource.PLAYERS, 1.0F, 1.4F);
        if (!level.isClientSide()) {
            player.setDeltaMovement(look.x * strength, Math.max(0.35, look.y * strength), look.z * strength);
            player.hurtMarked = true;
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 60, 0, false, false, true));
            DamageSource source = player.damageSources().playerAttack(player);
            AABB path = player.getBoundingBox().inflate(1.5, 0.5, 1.5).expandTowards(look.scale(strength));
            for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, path)) {
                if (entity != player) {
                    entity.hurt(source, trailDamage);
                }
            }
        }
        return true;
    }

    @Override
    public void appendHoverText(List<Component> tooltip) {
        tooltip.add(Component.literal("Right-click to dash forward").withStyle(ChatFormatting.AQUA));
    }
}
