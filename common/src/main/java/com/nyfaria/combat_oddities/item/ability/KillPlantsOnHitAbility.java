package com.nyfaria.combat_oddities.item.ability;

import com.nyfaria.combat_oddities.item.SwordAbility;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class KillPlantsOnHitAbility implements SwordAbility {

    private final float chance;
    private final int radius;

    public KillPlantsOnHitAbility(float chance, int radius) {
        this.chance = chance;
        this.radius = radius;
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Level level = target.level();
        if (level.isClientSide() || attacker.getRandom().nextFloat() >= chance) {
            return;
        }
        BlockPos center = target.blockPosition();
        for (BlockPos pos : BlockPos.betweenClosed(center.offset(-radius, -1, -radius), center.offset(radius, 1, radius))) {
            BlockState state = level.getBlockState(pos);
            if (state.is(Blocks.GRASS_BLOCK)) {
                level.setBlock(pos, Blocks.DIRT.defaultBlockState(), 3);
            } else if (state.is(Blocks.SHORT_GRASS) || state.is(Blocks.TALL_GRASS) || state.is(Blocks.FERN)
                    || state.is(Blocks.LARGE_FERN) || state.is(Blocks.DANDELION) || state.is(Blocks.POPPY)) {
                level.destroyBlock(pos, false);
            }
        }
    }

    @Override
    public void appendHoverText(List<Component> tooltip) {
        tooltip.add(Component.literal("Withers plants around struck foes").withStyle(ChatFormatting.DARK_GREEN));
    }
}
