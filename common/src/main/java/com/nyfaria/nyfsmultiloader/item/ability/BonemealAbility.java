package com.nyfaria.nyfsmultiloader.item.ability;

import com.nyfaria.nyfsmultiloader.item.SwordAbility;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class BonemealAbility implements SwordAbility {

    private final int cooldownTicks;

    public BonemealAbility(int cooldownTicks) {
        this.cooldownTicks = cooldownTicks;
    }

    @Override
    public InteractionResult onUseOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null || player.getCooldowns().isOnCooldown(context.getItemInHand().getItem())) {
            return InteractionResult.PASS;
        }
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof BonemealableBlock bonemealable)
                || !bonemealable.isValidBonemealTarget(level, pos, state)) {
            return InteractionResult.PASS;
        }
        player.getCooldowns().addCooldown(context.getItemInHand().getItem(), cooldownTicks);
        if (level instanceof ServerLevel serverLevel) {
            if (bonemealable.isBonemealSuccess(level, level.random, pos, state)) {
                bonemealable.performBonemeal(serverLevel, level.random, pos, state);
            }
            level.levelEvent(1505, pos, 15);
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public void appendHoverText(List<Component> tooltip) {
        tooltip.add(Component.literal("Doubles as bone meal").withStyle(ChatFormatting.GREEN));
    }
}
