package com.nyfaria.nyfsmultiloader;

import com.nyfaria.nyfsmultiloader.init.BlockInit;
import com.nyfaria.nyfsmultiloader.init.EntityInit;
import com.nyfaria.nyfsmultiloader.init.ItemInit;
import com.nyfaria.nyfsmultiloader.init.ProjectileInit;
import com.nyfaria.nyfsmultiloader.init.SwordInit;
import com.nyfaria.nyfsmultiloader.init.TagInit;
import com.nyfaria.nyfsmultiloader.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;

public class CommonClass {

    public static void init() {
        ItemInit.loadClass();
        SwordInit.loadClass();
        BlockInit.loadClass();
        EntityInit.loadClass();
        ProjectileInit.loadClass();
        TagInit.loadClass();
    }
}