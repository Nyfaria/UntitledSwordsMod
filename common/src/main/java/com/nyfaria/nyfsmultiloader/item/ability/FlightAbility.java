package com.nyfaria.nyfsmultiloader.item.ability;

import com.nyfaria.nyfsmultiloader.item.SwordAbility;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class FlightAbility implements SwordAbility {

    @Override
    public void onInventoryTick(ItemStack stack, Level level, LivingEntity holder, boolean selected) {
        if (level.isClientSide() || !(holder instanceof Player player)) {
            return;
        }
        if (player.isCreative() || player.isSpectator()) {
            return;
        }
        boolean shouldFly = selected;
        if (player.getAbilities().mayfly != shouldFly) {
            player.getAbilities().mayfly = shouldFly;
            if (!shouldFly) {
                player.getAbilities().flying = false;
            }
            player.onUpdateAbilities();
        }
    }

    @Override
    public void appendHoverText(List<Component> tooltip) {
        tooltip.add(Component.literal("Grants flight while held").withStyle(ChatFormatting.WHITE));
    }
}
