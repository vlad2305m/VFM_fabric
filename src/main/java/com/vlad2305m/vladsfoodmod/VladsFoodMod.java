package com.vlad2305m.vladsfoodmod;

import com.vlad2305m.vladsfoodmod.interfaces.ShowNutrientInfo;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigHolder;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import me.sargunvohra.mcmods.autoconfig1u.serializer.PartitioningSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.registry.CommandRegistry;
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
		});

	}

	@Override
	public void onInitializeClient() {
		// how to get the gui registry for custom gui handlers
		AutoConfig.getGuiRegistry(ModConfig.class);
	}

}
