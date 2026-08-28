package com.nyfaria.nyfsmultiloader.item.ability;

import com.nyfaria.nyfsmultiloader.item.SwordAbility;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public class BonusDamageAbility implements SwordAbility {

    private final Predicate<LivingEntity> targetPredicate;
    private final float bonus;
    private final String targetName;

    public BonusDamageAbility(String targetName, Predicate<LivingEntity> targetPredicate, float bonus) {
        this.targetName = targetName;
        this.targetPredicate = targetPredicate;
        this.bonus = bonus;
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.level().isClientSide()) {
            return;
        }
        if (!targetPredicate.test(target)) {
            return;
        }
        DamageSources sources = target.damageSources();
        DamageSource source = attacker instanceof Player player ? sources.playerAttack(player) : sources.mobAttack(attacker);
        target.invulnerableTime = 0;
        target.hurt(source, bonus);
    }

    @Override
    public void appendHoverText(java.util.List<Component> tooltip) {
        tooltip.add(Component.literal("Deals bonus damage to " + targetName).withStyle(ChatFormatting.AQUA));
    }
}
