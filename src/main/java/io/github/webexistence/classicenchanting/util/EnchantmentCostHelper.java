package io.github.webexistence.classicenchanting.util;

import io.github.webexistence.classicenchanting.config.ClassicEnchantingConfig;

public class EnchantmentCostHelper {
    private static final int MAX_ENCHANT_COST = 64;

    /**
     * Calculate new enchantment cost based on power and a multiplier.
     * Rounds down to be more generous to lower-level enchantments.
     * @param 	enchantmentPowerInt	The original level requirements (max 30).
     * @return						Integer enchantment cost.
     */
    public static int calculateEnchantmentCost(int enchantmentPowerInt) {
        int enchantmentCost = (int) Math.floor(enchantmentPowerInt * ClassicEnchantingConfig.enchantmentLevelCostMultiplier);
        return Math.max(Math.min(enchantmentCost, MAX_ENCHANT_COST), 1);
    }
}
