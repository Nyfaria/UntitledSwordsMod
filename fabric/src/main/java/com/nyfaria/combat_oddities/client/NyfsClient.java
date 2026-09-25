package com.nyfaria.combat_oddities.client;

import com.nyfaria.combat_oddities.client.model.ProjectileModelLayers;
import com.nyfaria.combat_oddities.client.renderer.EnergyProjectileRenderer;
import com.nyfaria.combat_oddities.entity.ProjectileVariant;
import com.nyfaria.combat_oddities.init.BlockInit;
import com.nyfaria.combat_oddities.init.ProjectileInit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;

public class NyfsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        for (ProjectileVariant variant : ProjectileVariant.values()) {
            EntityModelLayerRegistry.registerModelLayer(ProjectileModelLayers.get(variant),
                    ProjectileModelLayers.definitions().get(variant)::get);
        }
        EntityRendererRegistry.register(ProjectileInit.ENERGY_PROJECTILE.get(), EnergyProjectileRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(),
                BlockInit.NETHERITE_ANVIL.get(),
                BlockInit.CHIPPED_NETHERITE_ANVIL.get(),
                BlockInit.DAMAGED_NETHERITE_ANVIL.get());
    }
}
