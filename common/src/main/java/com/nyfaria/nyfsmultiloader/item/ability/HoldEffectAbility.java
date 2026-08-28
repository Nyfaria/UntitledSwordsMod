package com.nyfaria.nyfsmultiloader.item.ability;

import com.nyfaria.nyfsmultiloader.item.SwordAbility;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class HoldEffectAbility implements SwordAbility {

    private final Holder<MobEffect> effect;
    private final int amplifier;
    private final String description;

    public HoldEffectAbility(Holder<MobEffect> effect, int amplifier, String description) {
        this.effect = effect;
        this.amplifier = amplifier;
        this.description = description;
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, LivingEntity holder, boolean selected) {
        if (level.isClientSide() || !selected) {
            return;
        }
        holder.addEffect(new MobEffectInstance(effect, 40, amplifier, true, false, false));
    }

    @Override
    public void appendHoverText(List<Component> tooltip) {
        tooltip.add(Component.literal(description).withStyle(ChatFormatting.LIGHT_PURPLE));
    }
}
