package com.nyfaria.combat_oddities;

import com.nyfaria.combat_oddities.init.BlockInit;
import com.nyfaria.combat_oddities.init.EntityInit;
import com.nyfaria.combat_oddities.init.ItemInit;
import com.nyfaria.combat_oddities.init.ProjectileInit;
import com.nyfaria.combat_oddities.init.SwordInit;
import com.nyfaria.combat_oddities.init.TagInit;
import com.nyfaria.combat_oddities.platform.Services;
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