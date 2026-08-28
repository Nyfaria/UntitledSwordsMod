package com.nyfaria.nyfsmultiloader.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public interface SwordAbility {

    default void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
    }

    default void onInventoryTick(ItemStack stack, Level level, LivingEntity holder, boolean selected) {
    }

    default boolean onUse(Level level, Player player, InteractionHand hand) {
        return false;
    }

    default InteractionResult onUseOn(UseOnContext context) {
        return InteractionResult.PASS;
    }

    default InteractionResult onInteractEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        return InteractionResult.PASS;
    }

    default void onMineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miner) {
    }

    default void appendHoverText(List<Component> tooltip) {
    }
}
