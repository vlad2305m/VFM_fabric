package com.vlad2305m.vladsfoodmod.misc;

import com.vlad2305m.vladsfoodmod.ModConfig;
import com.vlad2305m.vladsfoodmod.NutrientStore;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import org.apache.commons.lang3.StringUtils;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.round;

public class Util {
    public static NutrientStore getNutrientContents(Item item) {
        NutrientStore stackValue = AutoConfig.getConfigHolder(ModConfig.class).getConfig().foodData.nutrientStoreMap.get(item.getTranslationKey());
        if (stackValue == null && item instanceof PotionItem) stackValue = AutoConfig.getConfigHolder(ModConfig.class).getConfig().foodData.nutrientStoreMap.get(Items.POTION.getTranslationKey());
        if (stackValue != null) return stackValue;
        else return new NutrientStore();
    }

    public static String find_5_best(NutrientStore.nutrients n) {
        List<Map.Entry<String, Double>> best = new LinkedList<>();

        Map<String, NutrientStore> list = AutoConfig.getConfigHolder(ModConfig.class).getConfig().foodData.nutrientStoreMap;

        int i = 0;
        for (Map.Entry<String, NutrientStore> m : list.entrySet()) {
            if (i <= 5){ best.add(new AbstractMap.SimpleImmutableEntry<>(m.getKey(), m.getValue().getNutrientPercentage().get(n))); i++; continue;}
            best.sort(Map.Entry.comparingByValue());
            best.set(0, new AbstractMap.SimpleImmutableEntry<>(m.getKey(), m.getValue().getNutrientPercentage().get(n)));
        }
        best.sort(Map.Entry.comparingByValue());

        return to_bar(best.get(5)) + ", " + to_bar(best.get(4)) + ", " + to_bar(best.get(3)) + ", " + to_bar(best.get(2)) + ", " + to_bar(best.get(1)) + ".";


    }

    public static String to_bar(Map.Entry<String, Double> e){
        return e.getKey() + " (§a" + StringUtils.repeat("|", (int)(e.getValue()*5)) + StringUtils.repeat(".", (int) round((e.getValue()*5 - (int)(e.getValue()*5))*4)) + "§r)";
    }

}

