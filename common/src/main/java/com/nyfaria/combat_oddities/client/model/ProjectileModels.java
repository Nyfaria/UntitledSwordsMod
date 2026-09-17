package com.nyfaria.combat_oddities.client.model;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public final class ProjectileModels {

    private ProjectileModels() {
    }

    public static LayerDefinition acidPiece() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition part_0 = root.addOrReplaceChild("acid_piece", CubeListBuilder.create()
                .texOffs(0, 11).addBox(-3.0F, -7.0F, -1.0F, 4.0F, 3.0F, 5.0F)
                .texOffs(18, 11).addBox(-3.0F, -7.0F, -1.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.5F))
                .texOffs(0, 19).addBox(-4.0F, -8.0F, -3.0F, 4.0F, 3.0F, 3.0F)
                .texOffs(14, 19).addBox(-4.0F, -8.0F, -3.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.5F)), PartPose.offset(2.0F, 5.75F, -0.5F));
        part_0.addOrReplaceChild("cube_r1", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-3.0F, -2.5F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-2.0F, -5.75F, 1.0F, 0.0F, 0.87266F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    public static LayerDefinition chargedBullet() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition part_0 = root.addOrReplaceChild("bone", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-3.0F, -4.0F, -3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.2F))
                .texOffs(0, 8).addBox(-3.0F, -4.0F, -3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.4F)), PartPose.offset(0.5F, 2.5F, 0.5F));
        PartDefinition part_1 = root.addOrReplaceChild("bone2", CubeListBuilder.create()
                .texOffs(0, 16).addBox(0.0F, -4.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(16, 0).addBox(0.0F, -4.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.4F)), PartPose.offset(0.5F, 1.5F, 3.5F));
        return LayerDefinition.create(mesh, 32, 32);
    }

    public static LayerDefinition corrosiveBolt() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition part_0 = root.addOrReplaceChild("corrosive_bolt", CubeListBuilder.create()
                .texOffs(0, 10).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 2.0F, 2.0F)
                .texOffs(8, 10).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(1.0F)), PartPose.offset(0.25F, 4.0F, -0.25F));
        part_0.addOrReplaceChild("cube_r1", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(-0.25F, -4.0F, 0.25F, 0.0F, -0.82903F, 0.0F));
        return LayerDefinition.create(mesh, 32, 32);
    }

    public static LayerDefinition shard() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition part_0 = root.addOrReplaceChild("shard", CubeListBuilder.create()
                .texOffs(0, 10).addBox(-3.375F, -0.625F, 2.625F, 5.0F, 1.0F, 2.0F)
                .texOffs(0, 5).addBox(-1.375F, -1.125F, -0.375F, 6.0F, 1.0F, 4.0F)
                .texOffs(0, 0).addBox(-5.375F, -0.125F, -3.375F, 8.0F, 1.0F, 4.0F)
                .texOffs(0, 13).addBox(-1.375F, -0.125F, -4.375F, 4.0F, 1.0F, 1.0F), PartPose.offset(0.375F, 0.125F, -0.125F));
        return LayerDefinition.create(mesh, 32, 32);
    }

    public static LayerDefinition zappingBeam() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition part_0 = root.addOrReplaceChild("zapping_beam", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-0.5F, -0.5F, -4.75F, 1.0F, 1.0F, 12.0F)
                .texOffs(0, 13).addBox(-0.5F, -0.5F, -4.75F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 0.0F, -1.25F));
        return LayerDefinition.create(mesh, 32, 32);
    }

}