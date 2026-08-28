package com.nyfaria.nyfsmultiloader.init;

import com.nyfaria.nyfsmultiloader.entity.EnergyProjectile;
import com.nyfaria.nyfsmultiloader.registration.RegistryObject;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ProjectileInit {

    public static final RegistryObject<EntityType<?>, EntityType<EnergyProjectile>> ENERGY_PROJECTILE =
            EntityInit.registerEntity("energy_projectile", () ->
                    EntityType.Builder.<EnergyProjectile>of(EnergyProjectile::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(4)
                            .updateInterval(10));

    public static void loadClass() {
    }
}
