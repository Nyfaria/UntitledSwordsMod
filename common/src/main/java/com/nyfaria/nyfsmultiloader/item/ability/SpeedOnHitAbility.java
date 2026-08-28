package com.nyfaria.nyfsmultiloader.item.ability;

import com.nyfaria.nyfsmultiloader.item.SwordAbility;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class SpeedOnHitAbility implements SwordAbility {

    private final int durationTicks;
    private final int amplifier;

    public SpeedOnHitAbility(int durationTicks, int amplifier) {
        this.durationTicks = durationTicks;
        this.amplifier = amplifier;
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.level().isClientSide()) {
            return;
        }
        attacker.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, durationTicks, amplifier, false, true));
    }

    @Override
    public void appendHoverText(List<Component> tooltip) {
        tooltip.add(Component.literal("Grants a burst of speed on hit").withStyle(ChatFormatting.WHITE));
    }
}
