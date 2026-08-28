package com.nyfaria.nyfsmultiloader.client;

import com.nyfaria.nyfsmultiloader.init.ProjectileInit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class NyfsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ProjectileInit.ENERGY_PROJECTILE.get(), ThrownItemRenderer::new);
    }
}
