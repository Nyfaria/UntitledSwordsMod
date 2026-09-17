package com.nyfaria.combat_oddities.item.ability;

import com.nyfaria.combat_oddities.item.SwordAbility;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class LightningStrikeAbility implements SwordAbility {

    private final double range;
    private final int cooldownTicks;

    public LightningStrikeAbility(double range, int cooldownTicks) {
        this.range = range;
        this.cooldownTicks = cooldownTicks;
    }

    @Override
    public boolean onUse(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(stack.getItem())) {
            return false;
        }
        HitResult hit = player.pick(range, 1.0F, false);
        if (hit.getType() != HitResult.Type.BLOCK) {
            return false;
        }
        player.getCooldowns().addCooldown(stack.getItem(), cooldownTicks);
        if (level instanceof ServerLevel serverLevel) {
            Vec3 pos = ((BlockHitResult) hit).getLocation();
            LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(serverLevel);
            if (bolt != null) {
                bolt.moveTo(pos.x, pos.y, pos.z);
                bolt.setCause(player instanceof net.minecraft.server.level.ServerPlayer sp ? sp : null);
                serverLevel.addFreshEntity(bolt);
            }
        }
        return true;
    }

    @Override
    public void appendHoverText(List<Component> tooltip) {
        tooltip.add(Component.literal("Use to call down lightning where you aim").withStyle(ChatFormatting.YELLOW));
    }
}
