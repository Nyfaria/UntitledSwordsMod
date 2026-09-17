package com.nyfaria.combat_oddities.item.ability;

import com.nyfaria.combat_oddities.item.SwordAbility;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TameWolfAbility implements SwordAbility {

    @Override
    public InteractionResult onInteractEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (!(target instanceof Wolf wolf) || wolf.isTame()) {
            return InteractionResult.PASS;
        }
        if (!player.level().isClientSide()) {
            if (wolf.getRandom().nextInt(3) == 0) {
                wolf.tame(player);
                wolf.setOrderedToSit(true);
                wolf.level().broadcastEntityEvent(wolf, (byte) 7);
            } else {
                wolf.level().broadcastEntityEvent(wolf, (byte) 6);
            }
        }
        return InteractionResult.sidedSuccess(player.level().isClientSide());
    }

    @Override
    public void appendHoverText(List<Component> tooltip) {
        tooltip.add(Component.literal("Interact with wolves to tame them").withStyle(ChatFormatting.WHITE));
    }
}
