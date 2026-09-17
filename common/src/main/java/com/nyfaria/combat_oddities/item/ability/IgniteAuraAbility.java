package com.nyfaria.combat_oddities.item.ability;

import com.nyfaria.combat_oddities.item.SwordAbility;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class IgniteAuraAbility implements SwordAbility {

    private final double radius;
    private final int seconds;
    private final int interval;

    public IgniteAuraAbility(double radius, int seconds, int interval) {
        this.radius = radius;
        this.seconds = seconds;
        this.interval = interval;
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, LivingEntity holder, boolean selected) {
        if (level.isClientSide() || !selected || holder.tickCount % interval != 0) {
            return;
        }
        AABB area = holder.getBoundingBox().inflate(radius);
        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, area)) {
            if (entity == holder || entity instanceof Player) {
                continue;
            }
            entity.igniteForSeconds(seconds);
        }
    }

    @Override
    public void appendHoverText(List<Component> tooltip) {
        tooltip.add(Component.literal("Sets nearby foes ablaze while held").withStyle(ChatFormatting.GOLD));
    }
}
