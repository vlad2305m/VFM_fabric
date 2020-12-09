package com.vlad2305m.vladsfoodmod;

import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1.ConfigHolder;
import me.sargunvohra.mcmods.autoconfig1.serializer.JanksonConfigSerializer;
import me.sargunvohra.mcmods.autoconfig1.serializer.PartitioningSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

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


		System.out.println("Hello VFM world!");
	}

	@Override
	public void onInitializeClient() {
		// how to get the gui registry for custom gui handlers
		AutoConfig.getGuiRegistry(ModConfig.class);
	}

}
