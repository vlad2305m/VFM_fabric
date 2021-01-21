package com.vlad2305m.vladsfoodmod;

import me.sargunvohra.mcmods.autoconfig1.ConfigData;
import me.sargunvohra.mcmods.autoconfig1.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1.annotation.ConfigEntry;
import me.sargunvohra.mcmods.autoconfig1.serializer.PartitioningSerializer;
import me.sargunvohra.mcmods.autoconfig1.shadowed.blue.endless.jankson.Comment;
import net.minecraft.item.Items;

import java.util.*;

@SuppressWarnings("unused")
@Config(name = "vladsfoodmod")
@Config.Gui.Background("minecraft:textures/block/dirt.png")
@Config.Gui.CategoryBackground(category = "b", background = "minecraft:textures/block/stone.png")
public class ModConfig extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("a")
    @ConfigEntry.Gui.TransitiveObject
    public ModuleA moduleA = new ModuleA();


    @ConfigEntry.Category("b")
    @ConfigEntry.Gui.TransitiveObject
    public ModuleB moduleB = new ModuleB();

    enum ExampleEnum {
        FOO, BAR, BAZ
    }

    @Config(name = "Features toggler")
    public static class ModuleA implements ConfigData {

        @Comment("change this if you are using spice of fabric or alike (disables food bar behavior changes)")
        public boolean disable_food_system = false;

        @ConfigEntry.Gui.PrefixText
        public boolean delay_system = false;

        @Comment("Choose the subtraction schedule you want to use")
        public boolean subtract_each_24h = true;

        public boolean subtract_on_wakeup = false;

        //@ConfigEntry.Gui.Tooltip(count = 2)
        //private ExampleEnum anEnum = ExampleEnum.FOO;

        @ConfigEntry.Gui.PrefixText
        public boolean water_branding = false;
    }

    @Config(name = "foods")
    public static class ModuleB implements ConfigData {

        /*@ConfigEntry.BoundedDiscrete(min = -1000, max = 2000)
        private int intSlider = 500;

        @ConfigEntry.BoundedDiscrete(min = -1000, max = 2000)
        private Long longSlider = 500L;*/

        @Comment("one and only food database for this mod (in grams) [delete this file to reset]")
        public Map<String, NutrientStore> nutrientStoreMap = new HashMap<String, NutrientStore>() {{
            put(Items.APPLE.getTranslationKey(), new NutrientStore(1f,13.81f,0.26f,0.17f,85.56f,3e-6f,0f,0.18e-3f,2.2e-6f,0f,0f,4.6e-3f,107e-3f,6e-3f,11e-3f,5e-3f,0.027e-3f,0.12e-3f,0f));
            put(Items.BAKED_POTATO.getTranslationKey(), new NutrientStore(1f,21.05f,2.49f,0.13f,74.55f,1e-6f,0f,0.04e-3f,2e-6f,0.31e-3f,0f,9.6e-3f,533e-3f,15e-3f,70e-3f,28e-3f,0.118e-3f,1.08e-3f,0.4f));
            put(Items.BEEF.getTranslationKey(), new NutrientStore(1f,0f,20.85f,7f,71.72f,4e-6f,0.1e-6f,0.17e-3f,0.5e-6f,0.383e-3f,2.23e-6f,0e-3f,336e-3f,10e-3f,192e-3f,21e-3f,0.075e-3f,2.33e-3f,17.1f));
            put(Items.BEETROOT.getTranslationKey(), new NutrientStore());
            put(Items.BEETROOT_SOUP.getTranslationKey(), new NutrientStore());
            put(Items.BREAD.getTranslationKey(), new NutrientStore());
            //put(Items.CAKE.getTranslationKey(), new NutrientStore());
            put(Items.CARROT.getTranslationKey(), new NutrientStore());
            put(Items.CHICKEN.getTranslationKey(), new NutrientStore());
            put(Items.CHORUS_FRUIT.getTranslationKey(), new NutrientStore());
            put(Items.COD.getTranslationKey(), new NutrientStore());
            put(Items.COOKED_BEEF.getTranslationKey(), new NutrientStore());
            put(Items.COOKED_CHICKEN.getTranslationKey(), new NutrientStore());
            put(Items.COOKED_COD.getTranslationKey(), new NutrientStore());
            put(Items.COOKED_MUTTON.getTranslationKey(), new NutrientStore());
            put(Items.COOKED_PORKCHOP.getTranslationKey(), new NutrientStore());
            put(Items.COOKED_RABBIT.getTranslationKey(), new NutrientStore());
            put(Items.COOKED_SALMON.getTranslationKey(), new NutrientStore());
            put(Items.COOKIE.getTranslationKey(), new NutrientStore());
            put(Items.DRIED_KELP.getTranslationKey(), new NutrientStore());
            put(Items.GOLDEN_APPLE.getTranslationKey(), new NutrientStore());
            put(Items.ENCHANTED_GOLDEN_APPLE.getTranslationKey(), new NutrientStore());
            put(Items.GOLDEN_CARROT.getTranslationKey(), new NutrientStore());
            put(Items.HONEY_BOTTLE.getTranslationKey(), new NutrientStore());
            put(Items.MELON_SLICE.getTranslationKey(), new NutrientStore());
            put(Items.MILK_BUCKET.getTranslationKey(), new NutrientStore());
            put(Items.MUSHROOM_STEW.getTranslationKey(), new NutrientStore());
            put(Items.MUTTON.getTranslationKey(), new NutrientStore());
            put(Items.POISONOUS_POTATO.getTranslationKey(), new NutrientStore());
            put(Items.PORKCHOP.getTranslationKey(), new NutrientStore());
            put(Items.POTATO.getTranslationKey(), new NutrientStore());
            put(Items.PUFFERFISH.getTranslationKey(), new NutrientStore());
            put(Items.PUMPKIN_PIE.getTranslationKey(), new NutrientStore());
            put(Items.RABBIT.getTranslationKey(), new NutrientStore());
            put(Items.RABBIT_STEW.getTranslationKey(), new NutrientStore());
            put(Items.ROTTEN_FLESH.getTranslationKey(), new NutrientStore());
            put(Items.SALMON.getTranslationKey(), new NutrientStore());
            put(Items.SPIDER_EYE.getTranslationKey(), new NutrientStore());
            put(Items.SUSPICIOUS_STEW.getTranslationKey(), new NutrientStore());
            put(Items.SWEET_BERRIES.getTranslationKey(), new NutrientStore());
            put(Items.TROPICAL_FISH.getTranslationKey(), new NutrientStore());
            //put(Items.POTION.getTranslationKey(), new NutrientStore());
        }};

        //@ConfigEntry.Gui.Excluded
        //private NutritionData[] aList = new NutritionData[] {new NutritionData("apple", 0), new NutritionData("3", 4)};

    }

    @SuppressWarnings("FieldCanBeLocal")
    private static class PairOfInts {
        private int foo;
        private int bar;

        PairOfInts() {
            this(1, 2);
        }

        PairOfInts(int foo, int bar) {
            this.foo = foo;
            this.bar = bar;
        }
    }

    public static class NutritionData {

        public String id;
        public float vitaminA;


        NutritionData(String id, float vitaminA) {
            this.id = id;
            this.vitaminA = vitaminA;
        }
        NutritionData() {
            this.id = "invalid_id";
            this.vitaminA = 0;
        }
    }

    /*public NutritionData searchById(String id){
        for(NutritionData i : this.moduleB.aList){
            if(i.id.equals(id)){return i;}
        }
        return new NutritionData();
    }*/

}
