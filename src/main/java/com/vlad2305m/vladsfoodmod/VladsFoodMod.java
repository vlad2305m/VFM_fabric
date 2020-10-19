package com.vlad2305m.vladsfoodmod;

import net.fabricmc.api.ModInitializer;

public class VladsFoodMod implements ModInitializer {
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		System.out.println("Hello VFM world!");
	}
}
