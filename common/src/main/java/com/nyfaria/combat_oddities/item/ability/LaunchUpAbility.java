package com.nyfaria.combat_oddities.item.ability;

import com.nyfaria.combat_oddities.item.SwordAbility;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class LaunchUpAbility implements SwordAbility {

    private final double power;
    private final int cooldownTicks;

    public LaunchUpAbility(double power, int cooldownTicks) {
        this.power = power;
        this.cooldownTicks = cooldownTicks;
    }

    @Override
    public boolean onUse(Level level, Player player, InteractionHand hand) {
        if (player.getXRot() < 30.0F) {
            return false;
        }
        ItemStack stack = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(stack.getItem())) {
            return false;
        }
        if (!level.isClientSide()) {
            player.setDeltaMovement(player.getDeltaMovement().x, power, player.getDeltaMovement().z);
            player.hurtMarked = true;
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 120, 0, false, false, true));
            player.getCooldowns().addCooldown(stack.getItem(), cooldownTicks);
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.BREEZE_WIND_CHARGE_BURST, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        return true;
    }

    @Override
    public void appendHoverText(List<Component> tooltip) {
        tooltip.add(Component.literal("Look down and use to launch skyward (no fall damage)").withStyle(ChatFormatting.AQUA));
    }
}
