package com.vlad2305m.vladsfoodmod;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.vlad2305m.vladsfoodmod.interfaces.ShowNutrientInfo;
import com.vlad2305m.vladsfoodmod.interfaces.VfmFoodStore;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigHolder;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import me.sargunvohra.mcmods.autoconfig1u.serializer.PartitioningSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

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
			dispatcher.register(CommandManager.literal("vfmstats").executes((commandContext) -> ((ShowNutrientInfo)((ServerCommandSource)commandContext.getSource()).getPlayer()).showNutrientInfo()));
			dispatcher.register(CommandManager.literal("vfmsubtractdaily").then(CommandManager.argument("multiplier", IntegerArgumentType.integer()).executes((commandContext) -> ((VfmFoodStore)((ServerCommandSource)commandContext.getSource()).getPlayer().getHungerManager()).getNutrientStore().subtractDaily(commandContext.getArgument("multiplier", Integer.class)) == null ? 0 : 1)));

		});

	}

	@Override
	public void onInitializeClient() {
		// how to get the gui registry for custom gui handlers
		AutoConfig.getGuiRegistry(ModConfig.class);
	}

}
