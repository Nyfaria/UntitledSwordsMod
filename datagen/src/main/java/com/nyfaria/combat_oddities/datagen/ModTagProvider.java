package com.nyfaria.combat_oddities.datagen;

import com.nyfaria.combat_oddities.Constants;
import com.nyfaria.combat_oddities.init.BlockInit;
import com.nyfaria.combat_oddities.init.VillagerInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ModTagProvider {

    public static class ModItemTags extends TagsProvider<Item>{

        public ModItemTags(PackOutput p_256596_, CompletableFuture<HolderLookup.Provider> p_256513_, @Nullable ExistingFileHelper existingFileHelper) {
            super(p_256596_, Registries.ITEM, p_256513_, Constants.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider pProvider) {
            populateTag(ItemTags.ANVIL,
                    () -> BlockInit.NETHERITE_ANVIL.get().asItem(),
                    () -> BlockInit.CHIPPED_NETHERITE_ANVIL.get().asItem(),
                    () -> BlockInit.DAMAGED_NETHERITE_ANVIL.get().asItem());
        }

        public void populateTag(TagKey<Item> tag, Supplier<Item>... items){
            for (Supplier<Item> item : items) {
                tag(tag).add(BuiltInRegistries.ITEM.getResourceKey(item.get()).get());
            }
        }
    }

    public static class ModBlockTags extends TagsProvider<Block>{

        public ModBlockTags(PackOutput pGenerator, CompletableFuture<HolderLookup.Provider> p_256513_, @Nullable ExistingFileHelper existingFileHelper) {
            super(pGenerator, Registries.BLOCK, p_256513_, Constants.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider pProvider) {
            populateTag(BlockTags.ANVIL, BlockInit.NETHERITE_ANVIL, BlockInit.CHIPPED_NETHERITE_ANVIL, BlockInit.DAMAGED_NETHERITE_ANVIL);
            populateTag(BlockTags.MINEABLE_WITH_PICKAXE, BlockInit.NETHERITE_ANVIL, BlockInit.CHIPPED_NETHERITE_ANVIL, BlockInit.DAMAGED_NETHERITE_ANVIL);
        }
        public  <T extends Block>void populateTag(TagKey<Block> tag, Supplier<?>... items){
            for (Supplier<?> item : items) {
                tag(tag).add(BuiltInRegistries.BLOCK.getResourceKey((Block)item.get()).get());
            }
        }
    }

    public static class ModPoiTypeTags extends TagsProvider<PoiType> {

        public ModPoiTypeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, Registries.POINT_OF_INTEREST_TYPE, lookupProvider, Constants.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider pProvider) {
            tag(PoiTypeTags.ACQUIRABLE_JOB_SITE).add(VillagerInit.NETHERITE_ANVIL_POI.getResourceKey());
        }
    }
}
