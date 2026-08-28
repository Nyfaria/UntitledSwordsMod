package com.nyfaria.nyfsmultiloader.client;

import com.nyfaria.nyfsmultiloader.Constants;
import com.nyfaria.nyfsmultiloader.init.ProjectileInit;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Constants.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ProjectileInit.ENERGY_PROJECTILE.get(), ThrownItemRenderer::new);
    }
}
