package com.nyfaria.combat_oddities.client.model;

import com.nyfaria.combat_oddities.Constants;
import com.nyfaria.combat_oddities.entity.ProjectileVariant;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public final class ProjectileModelLayers {

    private static final Map<ProjectileVariant, ModelLayerLocation> LAYERS = new EnumMap<>(ProjectileVariant.class);
    private static final Map<ProjectileVariant, Supplier<LayerDefinition>> DEFINITIONS = new EnumMap<>(ProjectileVariant.class);

    static {
        register(ProjectileVariant.ACID_PIECE, ProjectileModels::acidPiece);
        register(ProjectileVariant.CHARGED_BULLET, ProjectileModels::chargedBullet);
        register(ProjectileVariant.CORROSIVE_BOLT, ProjectileModels::corrosiveBolt);
        register(ProjectileVariant.SHARD, ProjectileModels::shard);
        register(ProjectileVariant.ZAPPING_BEAM, ProjectileModels::zappingBeam);
    }

    private ProjectileModelLayers() {
    }

    private static void register(ProjectileVariant variant, Supplier<LayerDefinition> definition) {
        LAYERS.put(variant, new ModelLayerLocation(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, variant.getModelName()), "main"));
        DEFINITIONS.put(variant, definition);
    }

    public static ModelLayerLocation get(ProjectileVariant variant) {
        return LAYERS.get(variant);
    }

    public static Map<ProjectileVariant, Supplier<LayerDefinition>> definitions() {
        return DEFINITIONS;
    }
}
