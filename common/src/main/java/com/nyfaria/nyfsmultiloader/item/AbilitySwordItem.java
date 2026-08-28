package com.nyfaria.nyfsmultiloader.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class AbilitySwordItem extends SwordItem {

    private final List<SwordAbility> abilities;

    public AbilitySwordItem(Tier tier, int attackDamage, float attackSpeed, List<SwordAbility> abilities, Properties properties) {
        super(tier, properties.attributes(SwordItem.createAttributes(tier, attackDamage, attackSpeed)));
        this.abilities = abilities;
    }

    public List<SwordAbility> getAbilities() {
        return abilities;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        for (SwordAbility ability : abilities) {
            ability.onHit(stack, target, attacker);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof LivingEntity holder) {
            for (SwordAbility ability : abilities) {
                ability.onInventoryTick(stack, level, holder, isSelected);
            }
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        boolean handled = false;
        for (SwordAbility ability : abilities) {
            if (ability.onUse(level, player, hand)) {
                handled = true;
            }
        }
        if (handled) {
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }
        return super.use(level, player, hand);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        for (SwordAbility ability : abilities) {
            InteractionResult result = ability.onUseOn(context);
            if (result != InteractionResult.PASS) {
                return result;
            }
        }
        return super.useOn(context);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        for (SwordAbility ability : abilities) {
            InteractionResult result = ability.onInteractEntity(stack, player, target, hand);
            if (result != InteractionResult.PASS) {
                return result;
            }
        }
        return super.interactLivingEntity(stack, player, target, hand);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        for (SwordAbility ability : abilities) {
            ability.onMineBlock(stack, level, state, pos, miningEntity);
        }
        return super.mineBlock(stack, level, state, pos, miningEntity);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        for (SwordAbility ability : abilities) {
            ability.appendHoverText(tooltip);
        }
        super.appendHoverText(stack, context, tooltip, flag);
    }
}
