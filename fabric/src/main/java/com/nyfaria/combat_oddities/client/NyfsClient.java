package com.nyfaria.combat_oddities.client;

import com.nyfaria.combat_oddities.client.model.ProjectileModelLayers;
import com.nyfaria.combat_oddities.client.renderer.EnergyProjectileRenderer;
import com.nyfaria.combat_oddities.entity.ProjectileVariant;
import com.nyfaria.combat_oddities.init.ProjectileInit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class NyfsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        for (ProjectileVariant variant : ProjectileVariant.values()) {
            EntityModelLayerRegistry.registerModelLayer(ProjectileModelLayers.get(variant),
                    ProjectileModelLayers.definitions().get(variant)::get);
        }
        EntityRendererRegistry.register(ProjectileInit.ENERGY_PROJECTILE.get(), EnergyProjectileRenderer::new);
    }
}
