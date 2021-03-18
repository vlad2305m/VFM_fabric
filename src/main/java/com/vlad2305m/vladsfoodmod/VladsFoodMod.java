package com.vlad2305m.vladsfoodmod;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.vlad2305m.vladsfoodmod.interfaces.ShowNutrientInfo;
import com.vlad2305m.vladsfoodmod.interfaces.VfmFoodStore;
import com.vlad2305m.vladsfoodmod.misc.Util;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigHolder;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import me.sargunvohra.mcmods.autoconfig1u.serializer.PartitioningSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import org.apache.commons.lang3.StringUtils;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.vlad2305m.vladsfoodmod.misc.Util.find_5_best;
import static com.vlad2305m.vladsfoodmod.misc.Util.to_bar;

@SuppressWarnings("unused")
public class VladsFoodMod implements ModInitializer, ClientModInitializer {
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ConfigHolder<ModConfig> configHolder = AutoConfig.register(
				ModConfig.class,
				PartitioningSerializer.wrap(JanksonConfigSerializer::new)
		);
		// how to read a config:
		//configHolder.getConfig();
		// or
		//AutoConfig.getConfigHolder(ModConfig.class).getConfig();

		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			dispatcher.register(CommandManager
					.literal("vfmstats")
					.executes((commandContext) -> ((ShowNutrientInfo) commandContext.getSource().getPlayer()).showNutrientInfo()));
			dispatcher.register(CommandManager
					.literal("vfmsubtractdaily").then(CommandManager
					.argument("multiplier", IntegerArgumentType.integer())
					.executes((commandContext) -> {
						((VfmFoodStore) commandContext.getSource().getPlayer().getHungerManager()).getNutrientStore().subtractDaily(commandContext.getArgument("multiplier", Integer.class));
						commandContext.getSource().getPlayer().sendMessage(Text.of("Success"), true);
						return 1;
					})));
			dispatcher.register(CommandManager
					.literal("vfmget5best").then(CommandManager
					.argument("nutrient", IntegerArgumentType.integer(1))
					.executes(commandContext -> {
						Set<Map.Entry<NutrientStore.nutrients, Double>> entrySet = ((VfmFoodStore) commandContext.getSource().getPlayer().getHungerManager()).getNutrientStore().getNutrientPercentage().entrySet();
						Iterator<Map.Entry<NutrientStore.nutrients, Double>> iterator = entrySet.iterator();
						for (int i = commandContext.getArgument("nutrient", Integer.class); i > 1; i--) iterator.next();
						NutrientStore.nutrients nutrient = iterator.next().getKey();
						commandContext.getSource().getPlayer().sendMessage(Text.of("Top 5 sources of " + nutrient.toString() + ": " + find_5_best(nutrient)), false);
						return 1;
					})));
			dispatcher.register(CommandManager
					.literal("vfmgetcontents")
					.executes(context -> {
						StringBuilder levels = new StringBuilder();
						levels.append("Nutrient contents of ").append(context.getSource().getPlayer().getMainHandStack().getItem()).append(":\n");
						Util.getNutrientContents(context.getSource().getPlayer().getMainHandStack().getItem()).getNutrientPercentage()
								.forEach((nutrient, amount) -> {levels
										.append(to_bar(new AbstractMap.SimpleImmutableEntry<>(nutrient.toString(), amount))).append(", ");});
						levels.delete(levels.length()-2,levels.length()).append(".");
						context.getSource().getPlayer().sendMessage(Text.of(levels.toString()), false);
						return 1;
					}));

		});

	}

	@Override
	public void onInitializeClient() {
		// how to get the gui registry for custom gui handlers
		AutoConfig.getGuiRegistry(ModConfig.class);
	}

}
