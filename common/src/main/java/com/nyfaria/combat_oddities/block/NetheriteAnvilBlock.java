package com.nyfaria.combat_oddities.block;

import com.mojang.serialization.MapCodec;
import com.nyfaria.combat_oddities.init.BlockInit;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.concurrent.ThreadLocalRandom;

public class NetheriteAnvilBlock extends AnvilBlock {

    public static final MapCodec<AnvilBlock> CODEC = simpleCodec(NetheriteAnvilBlock::new);

    private static final float DAMAGE_CHANCE_MULTIPLIER = 0.2F;

    public NetheriteAnvilBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<AnvilBlock> codec() {
        return CODEC;
    }

    public static BlockState degrade(BlockState state) {
        if (ThreadLocalRandom.current().nextFloat() >= DAMAGE_CHANCE_MULTIPLIER) {
            return state;
        }
        if (state.is(BlockInit.NETHERITE_ANVIL.get())) {
            return BlockInit.CHIPPED_NETHERITE_ANVIL.get().defaultBlockState().setValue(FACING, state.getValue(FACING));
        }
        if (state.is(BlockInit.CHIPPED_NETHERITE_ANVIL.get())) {
            return BlockInit.DAMAGED_NETHERITE_ANVIL.get().defaultBlockState().setValue(FACING, state.getValue(FACING));
        }
        return null;
    }
}
