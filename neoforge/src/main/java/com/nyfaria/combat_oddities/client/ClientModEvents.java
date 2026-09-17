package com.nyfaria.combat_oddities.client;

import com.nyfaria.combat_oddities.Constants;
import com.nyfaria.combat_oddities.client.model.ProjectileModelLayers;
import com.nyfaria.combat_oddities.client.renderer.EnergyProjectileRenderer;
import com.nyfaria.combat_oddities.entity.ProjectileVariant;
import com.nyfaria.combat_oddities.init.ProjectileInit;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Constants.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        for (ProjectileVariant variant : ProjectileVariant.values()) {
            event.registerLayerDefinition(ProjectileModelLayers.get(variant),
                    ProjectileModelLayers.definitions().get(variant)::get);
        }
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ProjectileInit.ENERGY_PROJECTILE.get(), EnergyProjectileRenderer::new);
    }
}
