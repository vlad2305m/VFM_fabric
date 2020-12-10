package com.vlad2305m.vladsfoodmod;

import me.sargunvohra.mcmods.autoconfig1.ConfigData;
import me.sargunvohra.mcmods.autoconfig1.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1.annotation.ConfigEntry;
import me.sargunvohra.mcmods.autoconfig1.serializer.PartitioningSerializer;
import me.sargunvohra.mcmods.autoconfig1.shadowed.blue.endless.jankson.Comment;

import java.util.Arrays;
import java.util.List;

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
    ModuleB moduleB = new ModuleB();

    enum ExampleEnum {
        FOO, BAR, BAZ
    }

    @Config(name = "Features toggler")
    public static class ModuleA implements ConfigData {

        @Comment("set this if you are using spice of fabric or alike")
        public boolean disable_food_system = true;

        @ConfigEntry.Gui.PrefixText
        public boolean delay_system = false;

        @ConfigEntry.Gui.Tooltip(count = 2)
        private ExampleEnum anEnum = ExampleEnum.FOO;

        @ConfigEntry.Gui.PrefixText
        public boolean water_branding = false;
    }

    @Config(name = "module_b")
    private static class ModuleB implements ConfigData {

        @ConfigEntry.BoundedDiscrete(min = -1000, max = 2000)
        private int intSlider = 500;

        @ConfigEntry.BoundedDiscrete(min = -1000, max = 2000)
        private Long longSlider = 500L;

        @ConfigEntry.Gui.TransitiveObject
        private PairOfIntPairs anObject = new PairOfIntPairs(new PairOfInts(), new PairOfInts(3, 4));

        @ConfigEntry.Gui.Excluded
        private List<PairOfInts> aList = Arrays.asList(new PairOfInts(), new PairOfInts(3, 4));
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

    private static class PairOfIntPairs {

        @ConfigEntry.Gui.CollapsibleObject()
        PairOfInts first;

        @ConfigEntry.Gui.CollapsibleObject()
        PairOfInts second;

        PairOfIntPairs() {
            this(new PairOfInts(), new PairOfInts());
        }

        PairOfIntPairs(PairOfInts first, PairOfInts second) {
            this.first = first;
            this.second = second;
        }
    }

}
