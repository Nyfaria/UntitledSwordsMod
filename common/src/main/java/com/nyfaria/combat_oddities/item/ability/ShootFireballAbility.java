package com.nyfaria.combat_oddities.item.ability;

import com.nyfaria.combat_oddities.item.SwordAbility;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ShootFireballAbility implements SwordAbility {

    private final int count;
    private final int cooldownTicks;

    public ShootFireballAbility(int count, int cooldownTicks) {
        this.count = count;
        this.cooldownTicks = cooldownTicks;
    }

    @Override
    public boolean onUse(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(stack.getItem())) {
            return false;
        }
        player.getCooldowns().addCooldown(stack.getItem(), cooldownTicks);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
        if (!level.isClientSide()) {
            Vec3 look = player.getLookAngle();
            for (int i = 0; i < count; i++) {
                double spread = (i - (count - 1) / 2.0) * 0.15;
                Vec3 dir = look.add(spread, 0, spread).normalize();
                SmallFireball fireball = new SmallFireball(level, player, dir.scale(1.0));
                fireball.setPos(player.getX() + look.x, player.getEyeY(), player.getZ() + look.z);
                level.addFreshEntity(fireball);
            }
        }
        return true;
    }

    @Override
    public void appendHoverText(List<Component> tooltip) {
        tooltip.add(Component.literal("Use to launch " + (count > 1 ? count + " fire charges" : "a fire charge")).withStyle(ChatFormatting.GOLD));
    }
}
