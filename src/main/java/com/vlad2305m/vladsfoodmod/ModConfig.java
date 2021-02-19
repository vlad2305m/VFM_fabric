package com.vlad2305m.vladsfoodmod;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import me.sargunvohra.mcmods.autoconfig1u.serializer.PartitioningSerializer;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;
import net.minecraft.item.Items;

import java.util.*;

@SuppressWarnings("unused")
@Config(name = "vladsfoodmod")
@Config.Gui.Background("minecraft:textures/block/dirt.png")
public class ModConfig extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("features")
    @ConfigEntry.Gui.TransitiveObject
    public Features features = new Features();

    @ConfigEntry.Gui.Excluded
    @ConfigEntry.Category("data")
    public FoodData foodData = new FoodData();

    @Config(name = "Features toggle")
    public static class Features implements ConfigData {

        @Comment("change this if you are using spice of fabric or alike (disables food bar behavior changes)")
        public boolean disable_food_system = false;

        @Comment("disables tracking essential nutrients")
        public boolean disable_nutrient_system = false;

        public boolean delay_system = false;

        @Comment("Choose the subtraction schedule you want to use")
        public boolean subtract_each_24h = true;
        public boolean subtract_on_wakeup = false;

        public boolean nutrient_damage_penalty = true;
        public boolean nutrient_death_penalty = true;

        public boolean water_branding = false;

        public double generic_nutrient_data_multiplier = 10;
    }

    @Config(name = "foods")
    public static class FoodData implements ConfigData {

        @Comment("one and only food database for this mod (in grams) [delete this file to reset]")
        public Map<String, NutrientStore> nutrientStoreMap = new HashMap<String, NutrientStore>() {{
            put(Items.APPLE.getTranslationKey(), new NutrientStore(1f,13.81f,0.26f,0.17f,85.56f,3e-6f,0f,0.18e-3f,2.2e-6f,0f,0f,4.6e-3f,107e-3f,6e-3f,11e-3f,5e-3f,0.027e-3f,0.12e-3f,0e-6f));
            put(Items.BAKED_POTATO.getTranslationKey(), new NutrientStore(1f,21.05f,2.49f,0.13f,74.55f,1e-6f,0f,0.04e-3f,2e-6f,0.31e-3f,0f,9.6e-3f,533e-3f,15e-3f,70e-3f,28e-3f,0.118e-3f,1.08e-3f,0.4e-6f));
            put(Items.BEEF.getTranslationKey(), new NutrientStore(1f,0f,20.85f,7f,71.72f,4e-6f,0.1e-6f,0.17e-3f,0.5e-6f,0.383e-3f,2.23e-6f,0e-3f,336e-3f,10e-3f,192e-3f,21e-3f,0.075e-3f,2.33e-3f,17.1e-6f));
            put(Items.BEETROOT.getTranslationKey(), new NutrientStore(1f,9.56f,1.61f,0.17f,87.58f,2e-6f,0e-6f,0.04e-3f,0.2e-6f,0.067e-3f,0e-6f,4.9e-3f,325e-3f,16e-3f,40e-3f,23e-3f,0.075e-3f,0.8e-3f,0.7e-6f));
            put(Items.BEETROOT_SOUP.getTranslationKey(), new NutrientStore(6f,9.56f,1.61f,0.17f,87.58f,2e-6f,0e-6f,0.04e-3f,0.2e-6f,0.067e-3f,0e-6f,4.9e-3f,325e-3f,16e-3f,40e-3f,23e-3f,0.075e-3f,0.8e-3f,0.7e-6f));
            put(Items.BREAD.getTranslationKey(), new NutrientStore(1f,49.2f,9.43f,3.59f,35.7f,0e-6f,0e-6f,0.22e-3f,0.2e-6f,0.092e-3f,0e-6f,0e-3f,117e-3f,211e-3f,113e-3f,27e-3f,0.124e-3f,3.36e-3f,23.2e-6f));
            put(Items.CAKE.getTranslationKey(), new NutrientStore());
            put(Items.CARROT.getTranslationKey(), new NutrientStore(1f,9.58f,0.93f,0.24f,88.29f,835e-6f,0e-6f,0.66e-3f,13.2e-6f,0.138e-3f,0e-6f,5.9e-3f,320e-3f,33e-3f,35e-3f,12e-3f,0.045e-3f,0.3e-3f,0.1e-6f));
            put(Items.CHICKEN.getTranslationKey(), new NutrientStore(1f,0f,21.39f,3.08f,75.46f,16e-6f,0.1e-6f,0.21e-3f,1.8e-6f,0.43e-3f,0.37e-6f,2.3e-3f,229e-3f,12e-3f,173e-3f,25e-3f,0.053e-3f,0.89e-3f,15.7e-6f));
            put(Items.CHORUS_FRUIT.getTranslationKey(), new NutrientStore());
            put(Items.COD.getTranslationKey(), new NutrientStore(1f,0f,17.81f,0.67f,81.22f,12e-6f,0.9e-6f,0.64e-3f,0.1e-6f,0.245e-3f,0.91e-6f,1e-3f,413e-3f,16e-3f,203e-3f,32e-3f,0.028e-3f,0.38e-3f,33.1e-6f));
            put(Items.COOKED_BEEF.getTranslationKey(), new NutrientStore(1f,0f,29.23f,6.75f,62.46f,1e-6f,0.1e-6f,0.3e-3f,1.5e-6f,0.676e-3f,1.98e-6f,0e-3f,371e-3f,16e-3f,238e-3f,23e-3f,0.083e-3f,2.38e-3f,33.7e-6f));
            put(Items.COOKED_CHICKEN.getTranslationKey(), new NutrientStore(1f,0f,25.01f,6.63f,67.41f,12e-6f,0e-6f,0e-3f,0e-6f,0.41e-3f,0.29e-6f,0e-3f,229e-3f,12e-3f,192e-3f,21e-3f,0.057e-3f,1.21e-3f,24.6e-6f));
            put(Items.COOKED_COD.getTranslationKey(), new NutrientStore(1f,0f,22.83f,0.86f,75.92f,14e-6f,1.2e-6f,0.81e-3f,0.1e-6f,0.283e-3f,1.05e-6f,1e-3f,244e-3f,14e-3f,138e-3f,42e-3f,0.036e-3f,0.49e-3f,37.6e-6f));
            put(Items.COOKED_MUTTON.getTranslationKey(), new NutrientStore(1f,0f,23.83f,18.07f,56.4f,0e-6f,0.1e-6f,0.14e-3f,4.4e-6f,0.139e-3f,2.59e-6f,0e-3f,280e-3f,16e-3f,186e-3f,23e-3f,0.111e-3f,1.96e-3f,26.5e-6f));
            put(Items.COOKED_PORKCHOP.getTranslationKey(), new NutrientStore(1f,0f,28.38f,9.55f,60.52f,2e-6f,0.7e-6f,0.2e-3f,0e-6f,0.547e-3f,0.72e-6f,0.6e-3f,421e-3f,18e-3f,247e-3f,28e-3f,0.059e-3f,1.08e-3f,34.8e-6f));
            put(Items.COOKED_RABBIT.getTranslationKey(), new NutrientStore(1f,0f,32.88f,3.5f,61.12f,0e-6f,0e-6f,0.41e-3f,1.5e-6f,0.339e-3f,6.48e-6f,0e-3f,342e-3f,18e-3f,239e-3f,31e-3f,0.175e-3f,4.83e-3f,15.1e-6f));
            put(Items.COOKED_SALMON.getTranslationKey(), new NutrientStore(1f,0f,25.06f,9.15f,65.39f,39e-6f,13.3e-6f,0.93e-3f,4.9e-6f,0.673e-3f,4.57e-6f,0.5e-3f,449e-3f,9e-3f,319e-3f,33e-3f,0.077e-3f,0.47e-3f,38.4e-6f));
            put(Items.COOKIE.getTranslationKey(), new NutrientStore(0.1f,73.4f,3.9f,16.8f,5.1f,1e-6f,0e-6f,1.06e-3f,4e-6f,0.018e-3f,0e-6f,0e-3f,199e-3f,46e-3f,109e-3f,21e-3f,0.218e-3f,3.5e-3f,2.6e-6f));
            put(Items.DRIED_KELP.getTranslationKey(), new NutrientStore(1f,52.39f,31.84f,4.01f,6.68f,14e-6f,0e-6f,5e-3f,25e-6f,0.334e-3f,0e-6f,5e-3f,1244e-3f,372e-3f,85e-3f,482e-3f,3.355e-3f,24.95e-3f,7.3e-6f));
            put(Items.GOLDEN_APPLE.getTranslationKey(), new NutrientStore(1f,29.61f,1.34f,2.15f,66.3f,5e-6f,0e-6f,0.25e-3f,2.1e-6f,0.045e-3f,0.08e-6f,3.6e-3f,134e-3f,39e-3f,37e-3f,8e-3f,0.025e-3f,0.12e-3f,0.4e-6f));
            put(Items.ENCHANTED_GOLDEN_APPLE.getTranslationKey(), this.get(Items.GOLDEN_APPLE.getTranslationKey()));
            put(Items.GOLDEN_CARROT.getTranslationKey(), this.get(Items.CARROT.getTranslationKey()));
            put(Items.HONEY_BOTTLE.getTranslationKey(), new NutrientStore(1f,82.4f,0.3f,0f,17.1f,0e-6f,0e-6f,0e-3f,0e-6f,0.024e-3f,0e-6f,0.5e-3f,52e-3f,6e-3f,4e-3f,2e-3f,0.036e-3f,0.42e-3f,0.8e-6f));
            put(Items.MELON_SLICE.getTranslationKey(), new NutrientStore(0.5f,7.55f,0.61f,0.15f,91.45f,28e-6f,0e-6f,0.05e-3f,0.1e-6f,0.045e-3f,0e-6f,8.1e-3f,112e-3f,7e-3f,11e-3f,10e-3f,0.042e-3f,0.24e-3f,0.4e-6f));
            put(Items.MILK_BUCKET.getTranslationKey(), new NutrientStore(10f,4.67f,3.28f,3.2f,88.1f,32e-6f,1.1e-6f,0.05e-3f,0.3e-6f,0.061e-3f,0.54e-6f,0e-3f,150e-3f,123e-3f,101e-3f,12e-3f,0.001e-3f,0e-3f,1.9e-6f));
            put(Items.MUSHROOM_STEW.getTranslationKey(), new NutrientStore(2f,3.33f,0.66f,2.59f,92.46f,1e-6f,0.1e-6f,0.24e-3f,9.6e-6f,0.007e-3f,0e-6f,0e-3f,31e-3f,7e-3f,12e-3f,2e-3f,0.016e-3f,0.09e-3f,1.4e-6f));
            put(Items.MUTTON.getTranslationKey(), new NutrientStore(1f,0f,16.56f,23.41f,59.47f,0e-6f,0.1e-6f,0.2e-3f,3.6e-6f,0.13e-3f,2.31e-6f,0e-3f,222e-3f,16e-3f,157e-3f,21e-3f,0.101e-3f,1.55e-3f,18.8e-6f));
            put(Items.PORKCHOP.getTranslationKey(), new NutrientStore(1f,0f,16.88f,21.19f,61.06f,2e-6f,0e-6f,0e-3f,0e-6f,0.383e-3f,0.7e-6f,0.7e-3f,187e-3f,14e-3f,175e-3f,19e-3f,0.045e-3f,0.88e-3f,24.6e-6f));
            put(Items.POTATO.getTranslationKey(), new NutrientStore(1f,17.49f,2.05f,0.09f,79.25f,0e-6f,0e-6f,0.01e-3f,2e-6f,0.298e-3f,0e-6f,19.7e-3f,425e-3f,12e-3f,57e-3f,23e-3f,0.11e-3f,0.81e-3f,0.4e-6f));
                put(Items.POISONOUS_POTATO.getTranslationKey(), this.get(Items.POTATO.getTranslationKey()));
            put(Items.PUFFERFISH.getTranslationKey(), new NutrientStore());
            put(Items.PUMPKIN_PIE.getTranslationKey(), new NutrientStore(3f,34.83f,3.9f,9.75f,50.39f,448e-6f,0.1e-6f,0.76e-3f,13.2e-6f,0.063e-3f,0.35e-6f,0e-3f,167e-3f,64e-3f,81e-3f,14e-3f,0.148e-3f,0.9e-3f,5.4e-6f));
            put(Items.RABBIT.getTranslationKey(), new NutrientStore(1f,0f,21.79f,2.32f,74.51f, 0e-6f,0e-6f,0e-3f,0e-6f,0e-3f,0e-6f,0e-3f,378e-3f,12e-3f,226e-3f,29e-3f,0e-3f,3.2e-3f,9.4e-6f));
            put(Items.RABBIT_STEW.getTranslationKey(), new NutrientStore(4f,4.44f,6.97f,1.81f,85.27f,50e-6f,0e-6f,0.15e-3f,4.3e-6f,0.121e-3f,1.35e-6f,2.4e-3f,162e-3f,14e-3f,64e-3f,10e-3f,0.079e-3f,0.71e-3f,9.1e-6f));
            put(Items.ROTTEN_FLESH.getTranslationKey(), new NutrientStore().subtractDaily(-0.33f));// ;P
            put(Items.SALMON.getTranslationKey(), new NutrientStore(1f,0f,20.5f,4.4f,75.52f,35e-6f,10.9e-6f,0.4e-3f,0.4e-6f,0.611e-3f,4.15e-6f,0e-3f,366e-3f,7e-3f,216e-3f,27e-3f,0.063e-3f,0.38e-3f,31.4e-6f));
            put(Items.SPIDER_EYE.getTranslationKey(), new NutrientStore());
            put(Items.SUSPICIOUS_STEW.getTranslationKey(), this.get(Items.MUSHROOM_STEW.getTranslationKey()));
            put(Items.SWEET_BERRIES.getTranslationKey(), new NutrientStore(1f,11.97f,0.46f,0.13f,87.32f,3e-6f,0e-6f,1.32e-3f,5e-6f,0.057e-3f,0e-6f,14e-3f,80e-3f,8e-3f,11e-3f,6e-3f,0.056e-3f,0.23e-3f,0.1e-6f));
            put(Items.TROPICAL_FISH.getTranslationKey(), new NutrientStore());
            put(Items.POTION.getTranslationKey(), new NutrientStore((NutrientStore store) -> store.water = 100));
        }};

        public boolean disable_muliplier = false;

    }
}
