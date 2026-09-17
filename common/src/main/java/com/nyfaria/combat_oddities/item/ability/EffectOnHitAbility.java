package com.nyfaria.combat_oddities.item.ability;

import com.nyfaria.combat_oddities.item.SwordAbility;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Predicate;

public class EffectOnHitAbility implements SwordAbility {

    private final Holder<MobEffect> effect;
    private final float chance;
    private final int durationTicks;
    private final int amplifier;
    private final String description;
    private final Predicate<LivingEntity> targetFilter;

    public EffectOnHitAbility(Holder<MobEffect> effect, float chance, int durationTicks, int amplifier, String description) {
        this(effect, chance, durationTicks, amplifier, description, target -> true);
    }

    public EffectOnHitAbility(Holder<MobEffect> effect, float chance, int durationTicks, int amplifier, String description, Predicate<LivingEntity> targetFilter) {
        this.effect = effect;
        this.chance = chance;
        this.durationTicks = durationTicks;
        this.amplifier = amplifier;
        this.description = description;
        this.targetFilter = targetFilter;
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.level().isClientSide()) {
            return;
        }
        if (targetFilter.test(target) && attacker.getRandom().nextFloat() < chance) {
            target.addEffect(new MobEffectInstance(effect, durationTicks, amplifier));
        }
    }

    @Override
    public void appendHoverText(List<Component> tooltip) {
        tooltip.add(Component.literal(description).withStyle(ChatFormatting.GREEN));
    }
}
