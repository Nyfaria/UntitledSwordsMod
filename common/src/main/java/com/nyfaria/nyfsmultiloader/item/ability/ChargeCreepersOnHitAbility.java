package com.nyfaria.nyfsmultiloader.item.ability;

import com.nyfaria.nyfsmultiloader.item.SwordAbility;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ChargeCreepersOnHitAbility implements SwordAbility {

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!(target.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        if (target instanceof Creeper creeper && !creeper.isPowered()) {
            LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(serverLevel);
            if (bolt != null) {
                bolt.moveTo(creeper.getX(), creeper.getY(), creeper.getZ());
                bolt.setVisualOnly(true);
                serverLevel.addFreshEntity(bolt);
            }
        }
    }

    @Override
    public void appendHoverText(List<Component> tooltip) {
        tooltip.add(Component.literal("Charges creepers on hit").withStyle(ChatFormatting.AQUA));
    }
}
