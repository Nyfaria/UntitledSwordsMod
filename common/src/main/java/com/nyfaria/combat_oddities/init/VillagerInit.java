package com.nyfaria.combat_oddities.init;

import com.google.common.collect.ImmutableSet;
import com.nyfaria.combat_oddities.Constants;
import com.nyfaria.combat_oddities.registration.RegistrationProvider;
import com.nyfaria.combat_oddities.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;
import java.util.stream.Stream;

public class VillagerInit {

    public static final RegistrationProvider<PoiType> POI_TYPES = RegistrationProvider.get(Registries.POINT_OF_INTEREST_TYPE, Constants.MODID);
    public static final RegistrationProvider<VillagerProfession> PROFESSIONS = RegistrationProvider.get(Registries.VILLAGER_PROFESSION, Constants.MODID);

    public static final RegistryObject<PoiType, PoiType> NETHERITE_ANVIL_POI = POI_TYPES.register("netherite_anvil",
            () -> new PoiType(netheriteAnvilStates(), 1, 1));

    public static final RegistryObject<VillagerProfession, VillagerProfession> MASTER_SWORDSMITH = PROFESSIONS.register("master_swordsmith",
            () -> new VillagerProfession("master_swordsmith",
                    poi -> poi.is(NETHERITE_ANVIL_POI.getResourceKey()),
                    poi -> poi.is(NETHERITE_ANVIL_POI.getResourceKey()),
                    ImmutableSet.of(),
                    ImmutableSet.of(),
                    SoundEvents.ANVIL_USE));

    private static Set<BlockState> netheriteAnvilStates() {
        return Stream.of(BlockInit.NETHERITE_ANVIL, BlockInit.CHIPPED_NETHERITE_ANVIL, BlockInit.DAMAGED_NETHERITE_ANVIL)
                .flatMap(block -> block.get().getStateDefinition().getPossibleStates().stream())
                .collect(ImmutableSet.toImmutableSet());
    }

    public static void loadClass() {
    }
}
