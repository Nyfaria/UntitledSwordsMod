package com.nyfaria.combat_oddities.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.nyfaria.combat_oddities.Constants;
import com.nyfaria.combat_oddities.client.model.ProjectileModelLayers;
import com.nyfaria.combat_oddities.entity.EnergyProjectile;
import com.nyfaria.combat_oddities.entity.ProjectileVariant;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.EnumMap;
import java.util.Map;

public class EnergyProjectileRenderer extends EntityRenderer<EnergyProjectile> {

    private final Map<ProjectileVariant, ModelPart> models = new EnumMap<>(ProjectileVariant.class);
    private final Map<ProjectileVariant, ResourceLocation> textures = new EnumMap<>(ProjectileVariant.class);

    public EnergyProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        for (ProjectileVariant variant : ProjectileVariant.values()) {
            models.put(variant, context.bakeLayer(ProjectileModelLayers.get(variant)));
            textures.put(variant, ResourceLocation.fromNamespaceAndPath(Constants.MODID,
                    "textures/entity/projectile/" + variant.getModelName() + ".png"));
        }
    }

    @Override
    public void render(EnergyProjectile entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        ProjectileVariant variant = entity.getVariant();
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucent(textures.get(variant)));
        models.get(variant).render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(EnergyProjectile entity) {
        return textures.get(entity.getVariant());
    }
}
