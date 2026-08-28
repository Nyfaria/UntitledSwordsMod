package com.nyfaria.nyfsmultiloader.item.ability;

import com.nyfaria.nyfsmultiloader.item.SwordAbility;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class PlaceLavaOnHitAbility implements SwordAbility {

    private final float chance;

    public PlaceLavaOnHitAbility(float chance) {
        this.chance = chance;
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Level level = target.level();
        if (level.isClientSide() || attacker.getRandom().nextFloat() >= chance) {
            return;
        }
        BlockPos pos = target.blockPosition();
        if (level.getBlockState(pos).canBeReplaced()) {
            level.setBlock(pos, Blocks.LAVA.defaultBlockState(), 3);
        }
    }

    @Override
    public void appendHoverText(List<Component> tooltip) {
        tooltip.add(Component.literal("Rarely leaves lava where the target stood").withStyle(ChatFormatting.GOLD));
    }
}
