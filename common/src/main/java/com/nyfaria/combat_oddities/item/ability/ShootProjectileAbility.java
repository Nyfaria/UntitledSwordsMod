package com.nyfaria.combat_oddities.item.ability;

import com.nyfaria.combat_oddities.entity.EnergyProjectile;
import com.nyfaria.combat_oddities.item.SwordAbility;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import com.nyfaria.combat_oddities.entity.ProjectileVariant;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Consumer;

public class ShootProjectileAbility implements SwordAbility {

    private final int cooldownTicks;
    private final boolean requiresRain;
    private final String description;
    private final ProjectileVariant variant;
    private final Consumer<EnergyProjectile> config;

    public ShootProjectileAbility(int cooldownTicks, boolean requiresRain, String description,
                                  ProjectileVariant variant, Consumer<EnergyProjectile> config) {
        this.cooldownTicks = cooldownTicks;
        this.requiresRain = requiresRain;
        this.description = description;
        this.variant = variant;
        this.config = config;
    }

    @Override
    public boolean onUse(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(stack.getItem())) {
            return false;
        }
        if (requiresRain && !level.isRainingAt(player.blockPosition())) {
            return false;
        }
        player.getCooldowns().addCooldown(stack.getItem(), cooldownTicks);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 0.6F, 1.0F);
        if (!level.isClientSide()) {
            EnergyProjectile projectile = new EnergyProjectile(level, player);
            projectile.variant(variant);
            config.accept(projectile);
            projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(projectile);
        }
        return true;
    }

    @Override
    public void appendHoverText(List<Component> tooltip) {
        tooltip.add(Component.literal(description).withStyle(ChatFormatting.LIGHT_PURPLE));
    }
}
