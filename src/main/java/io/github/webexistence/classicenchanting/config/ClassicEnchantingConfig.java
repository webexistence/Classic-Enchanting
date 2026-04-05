package io.github.webexistence.classicenchanting.config;

import eu.midnightdust.lib.config.MidnightConfig;

/** Every option in a MidnightConfig class has to be public and static, so we can access it from other classes.
 * The config class also has to extend MidnightConfig*/

public class ClassicEnchantingConfig extends MidnightConfig {
    public static final String GENERAL = "general";

    @Entry(category = GENERAL, isSlider = true, min=0.5, max=1.0, precision = 4)
    public static double enchantmentLevelCostMultiplier = 0.5;
    @Entry(category = GENERAL)
    public static boolean enableGrindstoneMultiplier = true;
}
