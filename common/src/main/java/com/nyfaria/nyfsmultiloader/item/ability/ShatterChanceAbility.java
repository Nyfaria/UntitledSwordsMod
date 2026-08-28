package com.nyfaria.nyfsmultiloader.item.ability;

import com.nyfaria.nyfsmultiloader.item.SwordAbility;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class ShatterChanceAbility implements SwordAbility {

    private final float chance;

    public ShatterChanceAbility(float chance) {
        this.chance = chance;
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Level level = attacker.level();
        if (level.isClientSide() || attacker.getRandom().nextFloat() >= chance) {
            return;
        }
        stack.setCount(0);
        level.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(),
                SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    @Override
    public void appendHoverText(List<Component> tooltip) {
        int percent = Math.round(chance * 100);
        tooltip.add(Component.literal("Fragile: " + percent + "% chance to shatter on hit").withStyle(ChatFormatting.RED));
    }
}
