package com.nyfaria.combat_oddities.entity;

public enum ProjectileVariant {

    ACID_PIECE("acid_piece"),
    CHARGED_BULLET("charged_bullet"),
    CORROSIVE_BOLT("corrosive_bolt"),
    SHARD("shard"),
    ZAPPING_BEAM("zapping_beam");

    private static final ProjectileVariant[] VALUES = values();

    private final String modelName;

    ProjectileVariant(String modelName) {
        this.modelName = modelName;
    }

    public String getModelName() {
        return modelName;
    }

    public static ProjectileVariant byId(int id) {
        return id >= 0 && id < VALUES.length ? VALUES[id] : SHARD;
    }
}
