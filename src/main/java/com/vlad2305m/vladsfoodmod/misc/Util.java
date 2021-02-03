package com.vlad2305m.vladsfoodmod.misc;

import com.vlad2305m.vladsfoodmod.ModConfig;
import com.vlad2305m.vladsfoodmod.NutrientStore;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;

public class Util {
    public static NutrientStore getNutrientContents(Item item) {
        NutrientStore stackValue = AutoConfig.getConfigHolder(ModConfig.class).getConfig().foodData.nutrientStoreMap.get(item.getTranslationKey());
        if (stackValue == null && item instanceof PotionItem) stackValue = AutoConfig.getConfigHolder(ModConfig.class).getConfig().foodData.nutrientStoreMap.get(Items.POTION.getTranslationKey());
        if (stackValue != null) return stackValue;
        else return new NutrientStore();
    }
}

