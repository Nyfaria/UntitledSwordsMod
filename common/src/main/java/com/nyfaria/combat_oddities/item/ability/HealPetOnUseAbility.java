package com.nyfaria.combat_oddities.item.ability;

import com.nyfaria.combat_oddities.item.SwordAbility;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class HealPetOnUseAbility implements SwordAbility {

    private final float healAmount;

    public HealPetOnUseAbility(float healAmount) {
        this.healAmount = healAmount;
    }

    @Override
    public InteractionResult onInteractEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (!(target instanceof TamableAnimal pet) || !pet.isTame() || pet.getOwner() != player) {
            return InteractionResult.PASS;
        }
        if (pet.getHealth() >= pet.getMaxHealth()) {
            return InteractionResult.PASS;
        }
        if (!player.level().isClientSide()) {
            pet.heal(healAmount);
            if (player.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.HEART,
                        pet.getX(), pet.getY() + pet.getBbHeight(), pet.getZ(), 3, 0.3, 0.3, 0.3, 0.1);
            }
        }
        return InteractionResult.sidedSuccess(player.level().isClientSide());
    }

    @Override
    public void appendHoverText(List<Component> tooltip) {
        tooltip.add(Component.literal("Interact with your pets to heal them").withStyle(ChatFormatting.LIGHT_PURPLE));
    }
}
