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

        @ConfigEntry.Gui.Tooltip(count = 2)
        private ExampleEnum anEnum = ExampleEnum.FOO;

        @ConfigEntry.Gui.PrefixText
        public boolean water_branding = false;
    }

    @Config(name = "foods")
    public static class ModuleB implements ConfigData {

        /*@ConfigEntry.BoundedDiscrete(min = -1000, max = 2000)
        private int intSlider = 500;

        @ConfigEntry.BoundedDiscrete(min = -1000, max = 2000)
        private Long longSlider = 500L;*/

        @Comment("one and only food database for this mod (in grams)")
        public Map<String, NutrientStore> nutrientStoreMap = new HashMap<String, NutrientStore>() {{
            put(Items.APPLE.getTranslationKey(), new NutrientStore().subtractDaily(-1));
            put(Items.BAKED_POTATO.getTranslationKey(), new NutrientStore(21.05F, 2.49F, 0.13F, 74.55F, 7E-6F, 0F, 0.04E-6F, 2E-6F, 0F, 0F, 0F, 0F, 0.31E-6F, 28E-6F, 0F, 0F ));
            put(Items.BEEF.getTranslationKey(), new NutrientStore(0F, 20.85F, 7F, 71.72F, 4E-6F, 0.2E-6F, 0.17E-6F, 0.5E-6F, 0F, 0F, 0F, 0F, 0.383E-6F, 0F, 2.23E-6F, 0F ));
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
            put(Items.POTION.getTranslationKey(), new NutrientStore());
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
