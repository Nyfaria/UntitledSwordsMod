package com.nyfaria.combat_oddities.item.ability;

import com.nyfaria.combat_oddities.item.SwordAbility;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class IgniteOnHitAbility implements SwordAbility {

    private final float chance;
    private final int seconds;

    public IgniteOnHitAbility(float chance, int seconds) {
        this.chance = chance;
        this.seconds = seconds;
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.level().isClientSide()) {
            return;
        }
        if (attacker.getRandom().nextFloat() < chance) {
            target.igniteForSeconds(seconds);
        }
    }

    @Override
    public void appendHoverText(List<Component> tooltip) {
        int percent = Math.round(chance * 100);
        tooltip.add(Component.literal(percent + "% chance to set targets ablaze").withStyle(ChatFormatting.GOLD));
    }
}
