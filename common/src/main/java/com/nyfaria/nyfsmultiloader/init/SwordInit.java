package com.nyfaria.nyfsmultiloader.init;

import com.nyfaria.nyfsmultiloader.item.AbilitySwordItem;
import com.nyfaria.nyfsmultiloader.item.SwordAbility;
import com.nyfaria.nyfsmultiloader.item.ability.BonemealAbility;
import com.nyfaria.nyfsmultiloader.item.ability.BonusDamageAbility;
import com.nyfaria.nyfsmultiloader.item.ability.ChargeCreepersOnHitAbility;
import com.nyfaria.nyfsmultiloader.item.ability.ComboBonusAbility;
import com.nyfaria.nyfsmultiloader.item.ability.DashAbility;
import com.nyfaria.nyfsmultiloader.item.ability.EffectOnHitAbility;
import com.nyfaria.nyfsmultiloader.item.ability.ExtraXpOnKillAbility;
import com.nyfaria.nyfsmultiloader.item.ability.FlightAbility;
import com.nyfaria.nyfsmultiloader.item.ability.FlintAndSteelAbility;
import com.nyfaria.nyfsmultiloader.item.ability.GlassBreakDropAbility;
import com.nyfaria.nyfsmultiloader.item.ability.HealPetOnUseAbility;
import com.nyfaria.nyfsmultiloader.item.ability.HitParticleAbility;
import com.nyfaria.nyfsmultiloader.item.ability.HoldEffectAbility;
import com.nyfaria.nyfsmultiloader.item.ability.HoldParticleAbility;
import com.nyfaria.nyfsmultiloader.item.ability.IgniteAuraAbility;
import com.nyfaria.nyfsmultiloader.item.ability.IgniteOnHitAbility;
import com.nyfaria.nyfsmultiloader.item.ability.KillPlantsOnHitAbility;
import com.nyfaria.nyfsmultiloader.item.ability.LaunchUpAbility;
import com.nyfaria.nyfsmultiloader.item.ability.LightningStrikeAbility;
import com.nyfaria.nyfsmultiloader.item.ability.NoDamageToTypeAbility;
import com.nyfaria.nyfsmultiloader.item.ability.PlaceLavaOnHitAbility;
import com.nyfaria.nyfsmultiloader.item.ability.ShatterChanceAbility;
import com.nyfaria.nyfsmultiloader.item.ability.ShootFireballAbility;
import com.nyfaria.nyfsmultiloader.item.ability.ShootProjectileAbility;
import com.nyfaria.nyfsmultiloader.item.ability.SpeedOnHitAbility;
import com.nyfaria.nyfsmultiloader.item.ability.StripLogAbility;
import com.nyfaria.nyfsmultiloader.item.ability.SwordTargets;
import com.nyfaria.nyfsmultiloader.item.ability.TameWolfAbility;
import com.nyfaria.nyfsmultiloader.item.ability.ThrowEnderPearlAbility;
import com.nyfaria.nyfsmultiloader.item.ability.ThunderstormAbility;
import com.nyfaria.nyfsmultiloader.registration.RegistryObject;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class SwordInit {

    public static final List<RegistryObject<Item, AbilitySwordItem>> SWORDS = new ArrayList<>();

    public static final RegistryObject<Item, AbilitySwordItem> UV_TRIBLADE = registerSword("uv_triblade", Tiers.DIAMOND, 3, -2.4F,
            new BonusDamageAbility("charged creepers", SwordTargets::isChargedCreeper, 8.0F),
            new HitParticleAbility(ParticleTypes.END_ROD, 6),
            new IgniteOnHitAbility(0.2F, 4));

    public static final RegistryObject<Item, AbilitySwordItem> ACID_EDGE = registerSword("acid_edge", Tiers.IRON, 3, -2.4F,
            new EffectOnHitAbility(MobEffects.POISON, 0.5F, 60, 0, "Chance to poison targets"),
            new ShootProjectileAbility(40, true, "While it rains, use to spit acid",
                    p -> p.damage(3.0F).effect(MobEffects.POISON, 100, 0).impactParticle(ParticleTypes.ITEM_SLIME)));

    public static final RegistryObject<Item, AbilitySwordItem> BLOODROSE_BLADE = registerSword("bloodrose_blade", Tiers.DIAMOND, 3, -2.4F,
            new BonusDamageAbility("undead mobs", SwordTargets::isUndead, 6.0F),
            new HitParticleAbility(new DustParticleOptions(new Vector3f(0.8F, 0.0F, 0.0F), 1.0F), 6),
            new HoldEffectAbility(MobEffects.REGENERATION, 0, "Grants regeneration while held"));

    public static final RegistryObject<Item, AbilitySwordItem> CHARGED_GREATBLADE = registerSword("charged_greatblade", Tiers.DIAMOND, 4, -2.6F,
            new ShootProjectileAbility(40, false, "Use to fire a charged bolt",
                    p -> p.damage(5.0F).powerCreepers(true).impactParticle(ParticleTypes.SOUL_FIRE_FLAME)),
            new ChargeCreepersOnHitAbility(),
            new IgniteAuraAbility(3.0, 3, 40));

    public static final RegistryObject<Item, AbilitySwordItem> CHARGED_HALO_SWORD = registerSword("charged_halo_sword", Tiers.NETHERITE, 3, -2.4F,
            new FlightAbility(),
            new LightningStrikeAbility(40.0, 40));

    public static final RegistryObject<Item, AbilitySwordItem> CINDER_BLADE = registerSword("cinder_blade", Tiers.DIAMOND, 3, -2.4F,
            new IgniteOnHitAbility(0.33F, 5),
            new HoldParticleAbility(ParticleTypes.FLAME, 5),
            new FlintAndSteelAbility());

    public static final RegistryObject<Item, AbilitySwordItem> CORROSIVE_SABER = registerSword("corrosive_saber", Tiers.IRON, 3, -2.4F,
            new EffectOnHitAbility(MobEffects.POISON, 0.5F, 60, 0, "Chance to poison targets"),
            new KillPlantsOnHitAbility(0.25F, 2));

    public static final RegistryObject<Item, AbilitySwordItem> CYCLONE_BLADE = registerSword("cyclone_blade", Tiers.DIAMOND, 3, -2.4F,
            new ThunderstormAbility(200),
            new ShootProjectileAbility(60, false, "Use to hurl a cyclone burst",
                    p -> p.damage(4.0F).impactParticle(ParticleTypes.HAPPY_VILLAGER)));

    public static final RegistryObject<Item, AbilitySwordItem> DOUBLE_FLAME_SWORD = registerSword("double_flame_sword", Tiers.NETHERITE, 3, -2.4F,
            new HoldParticleAbility(ParticleTypes.FLAME, 5),
            new HoldEffectAbility(MobEffects.FIRE_RESISTANCE, 0, "Grants fire resistance while held"),
            new ShootFireballAbility(2, 40),
            new IgniteAuraAbility(4.0, 4, 40),
            new NoDamageToTypeAbility("blazes", SwordTargets::isBlaze));

    public static final RegistryObject<Item, AbilitySwordItem> FLAME_ZAPPER = registerSword("flame_zapper", Tiers.WOOD, 0, -2.4F,
            new IgniteOnHitAbility(1.0F, 5),
            new ChargeCreepersOnHitAbility());

    public static final RegistryObject<Item, AbilitySwordItem> FLUORESCENT_ZAPPER = registerSword("fluorescent_zapper", Tiers.IRON, 3, -2.4F,
            new ShootProjectileAbility(30, false, "Use to fire a zapping beam",
                    p -> p.damage(4.0F).ignite(3).impactParticle(ParticleTypes.END_ROD)),
            new IgniteOnHitAbility(0.25F, 4));

    public static final RegistryObject<Item, AbilitySwordItem> FORGED_BLADE = registerSword("forged_blade", Tiers.DIAMOND, 3, -2.4F,
            new ShootProjectileAbility(40, false, "Use to sling molten metal",
                    p -> p.damage(4.0F).ignite(4).lavaOnImpact(true).impactParticle(ParticleTypes.LAVA)),
            new IgniteOnHitAbility(0.33F, 5));

    public static final RegistryObject<Item, AbilitySwordItem> FOSSILIZED_DAGGER = registerSword("fossilized_dagger", Tiers.STONE, 2, -2.0F,
            new BonusDamageAbility("skeletons", SwordTargets::isSkeleton, 6.0F),
            new BonemealAbility(40),
            new TameWolfAbility());

    public static final RegistryObject<Item, AbilitySwordItem> GALACTIC_TRIBLADE = registerSword("galactic_triblade", Tiers.NETHERITE, 3, -2.4F,
            new ComboBonusAbility(3, 6.0F),
            new LightningStrikeAbility(48.0, 60));

    public static final RegistryObject<Item, AbilitySwordItem> GILDED_FANG = registerSword("gilded_fang", Tiers.GOLD, 3, -2.4F,
            new EffectOnHitAbility(MobEffects.POISON, 1.0F, 100, 0, "Poisons animals", SwordTargets::isAnimal),
            new NoDamageToTypeAbility("undead mobs", SwordTargets::isUndead));

    public static final RegistryObject<Item, AbilitySwordItem> GLASS_SWORD = registerSword("glass_sword", Tiers.DIAMOND, 4, -2.4F,
            new GlassBreakDropAbility(),
            new ShatterChanceAbility(0.1F));

    public static final RegistryObject<Item, AbilitySwordItem> IONIZED_WHIPBLADE = registerSword("ionized_whipblade", Tiers.DIAMOND, 3, -2.4F,
            new DashAbility(2.0, 5.0F, 40),
            new ExtraXpOnKillAbility(1));

    public static final RegistryObject<Item, AbilitySwordItem> MAUVE_TAUPE_SWORD = registerSword("mauve_taupe_sword", Tiers.IRON, 3, -2.4F,
            new EffectOnHitAbility(MobEffects.MOVEMENT_SLOWDOWN, 0.3F, 60, 0, "Chance to slow targets"),
            new ShootProjectileAbility(30, false, "Use to fling a shard",
                    p -> p.damage(4.0F).impactParticle(ParticleTypes.CRIT)));

    public static final RegistryObject<Item, AbilitySwordItem> MIDNIGHT_ENDERBLADE = registerSword("midnight_enderblade", Tiers.NETHERITE, 3, -2.4F,
            new BonusDamageAbility("ender mobs", SwordTargets::isEnder, 6.0F),
            new ThrowEnderPearlAbility(60));

    public static final RegistryObject<Item, AbilitySwordItem> MOLTEN_SWORD = registerSword("molten_sword", Tiers.DIAMOND, 3, -2.4F,
            new HoldParticleAbility(ParticleTypes.LAVA, 5),
            new HoldEffectAbility(MobEffects.FIRE_RESISTANCE, 0, "Grants fire resistance while held"),
            new ShootFireballAbility(1, 40));

    public static final RegistryObject<Item, AbilitySwordItem> OOZING_BLADE = registerSword("oozing_blade", Tiers.IRON, 3, -2.4F,
            new PlaceLavaOnHitAbility(0.1F),
            new FlintAndSteelAbility());

    public static final RegistryObject<Item, AbilitySwordItem> PURPLE_AXEBLADE = registerSword("purple_axeblade", Tiers.DIAMOND, 5, -2.8F,
            new StripLogAbility());

    public static final RegistryObject<Item, AbilitySwordItem> SEAGLASS_BLADE = registerSword("seaglass_blade", Tiers.DIAMOND, 3, -2.4F,
            new BonusDamageAbility("guardians", SwordTargets::isGuardian, 6.0F),
            new EffectOnHitAbility(MobEffects.DIG_SLOWDOWN, 0.25F, 100, 0, "Chance to inflict mining fatigue"));

    public static final RegistryObject<Item, AbilitySwordItem> SWORD_OF_SHARDS = registerSword("sword_of_shards", Tiers.IRON, 3, -2.4F,
            new ShootProjectileAbility(25, false, "Use to loose a damaging shard",
                    p -> p.damage(5.0F).impactParticle(ParticleTypes.CRIT)));

    public static final RegistryObject<Item, AbilitySwordItem> VALENTINE_SWORD = registerSword("valentine_sword", Tiers.IRON, 3, -2.4F,
            new NoDamageToTypeAbility("animals", SwordTargets::isAnimal),
            new HealPetOnUseAbility(6.0F),
            new HitParticleAbility(ParticleTypes.HEART, 3));

    public static final RegistryObject<Item, AbilitySwordItem> VIOLET_ELECTROCUTER = registerSword("violet_electrocuter", Tiers.DIAMOND, 3, -2.4F,
            new ShootProjectileAbility(30, false, "Use to fire a violet beam",
                    p -> p.damage(5.0F).effect(MobEffects.MOVEMENT_SLOWDOWN, 40, 0).impactParticle(ParticleTypes.WITCH)));

    public static final RegistryObject<Item, AbilitySwordItem> WHISPERER = registerSword("whisperer", Tiers.NETHERITE, 3, -2.4F,
            new EffectOnHitAbility(MobEffects.WITHER, 0.5F, 80, 0, "Chance to wither foes"),
            new HoldParticleAbility(ParticleTypes.SOUL, 6));

    private static RegistryObject<Item, AbilitySwordItem> registerSword(String name, Tier tier, int attackDamage, float attackSpeed, SwordAbility... abilities) {
        List<SwordAbility> abilityList = List.of(abilities);
        RegistryObject<Item, AbilitySwordItem> sword = ItemInit.ITEMS.register(name,
                () -> new AbilitySwordItem(tier, attackDamage, attackSpeed, abilityList, ItemInit.getItemProperties()));
        SWORDS.add(sword);
        return sword;
    }

    public static void loadClass() {
    }
}
