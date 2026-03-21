package io.github.webexistence.classicenchanting;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassicEnchanting implements ModInitializer {
	public static final String MOD_ID = "classic-enchanting";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
	}

	/**
	 * Calculate new enchantment cost based on power and a multiplier.
	 * Rounds down to be more generous to lower-level enchantments.
	 * @param 	enchantmentPowerInt	The original level requirements (max 30).
	 * @return						Integer enchantment cost.
	 */
	public static int calculateEnchantmentCost(int enchantmentPowerInt) {
		float enchantmentLevelCostMultiplier = 0.5F; // TODO: make this configurable via json
		int enchantmentCost = (int) Math.floor(enchantmentPowerInt * enchantmentLevelCostMultiplier);
		return Math.max(Math.min(enchantmentCost, 64), 1);
	}
}