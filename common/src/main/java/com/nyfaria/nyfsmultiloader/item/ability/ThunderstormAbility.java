package com.nyfaria.nyfsmultiloader.item.ability;

import com.nyfaria.nyfsmultiloader.item.SwordAbility;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class ThunderstormAbility implements SwordAbility {

    private final int cooldownTicks;

    public ThunderstormAbility(int cooldownTicks) {
        this.cooldownTicks = cooldownTicks;
    }

    @Override
    public boolean onUse(Level level, Player player, InteractionHand hand) {
        if (!player.isShiftKeyDown()) {
            return false;
        }
        ItemStack stack = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(stack.getItem())) {
            return false;
        }
        player.getCooldowns().addCooldown(stack.getItem(), cooldownTicks);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.setWeatherParameters(0, 6000, true, true);
        }
        return true;
    }

    @Override
    public void appendHoverText(List<Component> tooltip) {
        tooltip.add(Component.literal("Sneak + use to summon a thunderstorm").withStyle(ChatFormatting.DARK_AQUA));
    }
}
