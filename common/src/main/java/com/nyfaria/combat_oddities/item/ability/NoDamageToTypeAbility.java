package com.nyfaria.combat_oddities.item.ability;

import com.nyfaria.combat_oddities.item.SwordAbility;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Predicate;

public class NoDamageToTypeAbility implements SwordAbility {

    private final Predicate<LivingEntity> protectedTargets;
    private final String targetName;

    public NoDamageToTypeAbility(String targetName, Predicate<LivingEntity> protectedTargets) {
        this.targetName = targetName;
        this.protectedTargets = protectedTargets;
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.level().isClientSide()) {
            return;
        }
        if (protectedTargets.test(target)) {
            target.setHealth(target.getMaxHealth());
            target.clearFire();
            target.hurtTime = 0;
        }
    }

    @Override
    public void appendHoverText(List<Component> tooltip) {
        tooltip.add(Component.literal("Will not harm " + targetName).withStyle(ChatFormatting.GREEN));
    }
}
