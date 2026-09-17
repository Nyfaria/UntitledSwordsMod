package com.nyfaria.combat_oddities.item.ability;

import com.nyfaria.combat_oddities.item.SwordAbility;
import com.nyfaria.combat_oddities.util.CreeperCharger;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
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
        if (target instanceof Creeper creeper) {
            CreeperCharger.charge(serverLevel, creeper);
        }
    }

    @Override
    public void appendHoverText(List<Component> tooltip) {
        tooltip.add(Component.literal("Charges creepers on hit").withStyle(ChatFormatting.AQUA));
    }
}
