package com.nyfaria.combat_oddities.item.ability;

import com.nyfaria.combat_oddities.item.SwordAbility;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ExtraXpOnKillAbility implements SwordAbility {

    private final int levels;

    public ExtraXpOnKillAbility(int levels) {
        this.levels = levels;
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.level().isClientSide()) {
            return;
        }
        if (target.isDeadOrDying() && attacker instanceof Player player) {
            player.giveExperienceLevels(levels);
        }
    }

    @Override
    public void appendHoverText(List<Component> tooltip) {
        tooltip.add(Component.literal("Kills grant bonus experience").withStyle(ChatFormatting.GREEN));
    }
}
