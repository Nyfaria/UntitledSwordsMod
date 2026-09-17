package com.nyfaria.combat_oddities.item.ability;

import com.nyfaria.combat_oddities.item.SwordAbility;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ComboBonusAbility implements SwordAbility {

    private static final Map<UUID, Integer> HIT_COUNTS = new HashMap<>();
    private final int everyN;
    private final float bonus;

    public ComboBonusAbility(int everyN, float bonus) {
        this.everyN = everyN;
        this.bonus = bonus;
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.level().isClientSide()) {
            return;
        }
        int count = HIT_COUNTS.merge(attacker.getUUID(), 1, Integer::sum);
        if (count % everyN == 0) {
            DamageSources sources = target.damageSources();
            DamageSource source = attacker instanceof Player player ? sources.playerAttack(player) : sources.mobAttack(attacker);
            target.invulnerableTime = 0;
            target.hurt(source, bonus);
        }
    }

    @Override
    public void appendHoverText(List<Component> tooltip) {
        tooltip.add(Component.literal("Every " + everyN + getOrdinal() + " strike deals bonus damage").withStyle(ChatFormatting.YELLOW));
    }

    private String getOrdinal() {
        return switch (everyN) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
    }
}
