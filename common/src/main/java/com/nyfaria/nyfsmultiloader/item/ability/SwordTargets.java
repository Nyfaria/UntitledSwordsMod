package com.nyfaria.nyfsmultiloader.item.ability;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.Zombie;

public final class SwordTargets {

    private SwordTargets() {
    }

    public static boolean isUndead(LivingEntity entity) {
        return entity instanceof Zombie
                || entity instanceof AbstractSkeleton
                || entity instanceof Phantom
                || entity instanceof Zoglin
                || entity instanceof ZombifiedPiglin;
    }

    public static boolean isSkeleton(LivingEntity entity) {
        return entity instanceof AbstractSkeleton;
    }

    public static boolean isEnder(LivingEntity entity) {
        return entity instanceof EnderMan || entity instanceof EnderDragon;
    }

    public static boolean isGuardian(LivingEntity entity) {
        return entity instanceof Guardian;
    }

    public static boolean isAnimal(LivingEntity entity) {
        return entity instanceof Animal;
    }

    public static boolean isBlaze(LivingEntity entity) {
        return entity instanceof Blaze;
    }

    public static boolean isCreeper(LivingEntity entity) {
        return entity instanceof Creeper;
    }

    public static boolean isChargedCreeper(LivingEntity entity) {
        return entity instanceof Creeper creeper && creeper.isPowered();
    }
}
