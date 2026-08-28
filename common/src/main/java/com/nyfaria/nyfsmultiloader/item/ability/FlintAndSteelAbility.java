package com.nyfaria.nyfsmultiloader.item.ability;

import com.nyfaria.nyfsmultiloader.item.SwordAbility;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class FlintAndSteelAbility implements SwordAbility {

    @Override
    public InteractionResult onUseOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos clicked = context.getClickedPos();
        BlockPos firePos = clicked.relative(context.getClickedFace());
        if (!BaseFireBlock.canBePlacedAt(level, firePos, context.getHorizontalDirection())) {
            return InteractionResult.PASS;
        }
        level.playSound(player, firePos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS,
                1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
        if (!level.isClientSide()) {
            BlockState fireState = BaseFireBlock.getState(level, firePos);
            level.setBlock(firePos, fireState, Direction.UP.get3DDataValue() | 1);
            level.gameEvent(player, net.minecraft.world.level.gameevent.GameEvent.BLOCK_PLACE, firePos);
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public void appendHoverText(List<Component> tooltip) {
        tooltip.add(Component.literal("Doubles as flint and steel").withStyle(ChatFormatting.GOLD));
    }
}
