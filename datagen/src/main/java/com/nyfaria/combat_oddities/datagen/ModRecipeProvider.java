package com.nyfaria.combat_oddities.datagen;

import com.nyfaria.combat_oddities.init.BlockInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput p_248933_, CompletableFuture<HolderLookup.Provider> p_323846_) {
        super(p_248933_, p_323846_);
    }

    @Override
    protected void buildRecipes(RecipeOutput p_301172_) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockInit.NETHERITE_ANVIL.get())
                .define('B', Items.NETHERITE_BLOCK)
                .define('i', Items.NETHERITE_INGOT)
                .pattern("BBB")
                .pattern(" i ")
                .pattern("iii")
                .unlockedBy("has_netherite_block", has(Items.NETHERITE_BLOCK))
                .save(p_301172_);
    }
}
