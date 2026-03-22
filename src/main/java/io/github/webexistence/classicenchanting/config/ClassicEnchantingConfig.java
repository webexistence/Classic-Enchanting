package io.github.webexistence.classicenchanting.config;

import eu.midnightdust.lib.config.MidnightConfig;

/** Every option in a MidnightConfig class has to be public and static, so we can access it from other classes.
 * The config class also has to extend MidnightConfig*/

public class ClassicEnchantingConfig extends MidnightConfig {
    public static final String GENERAL = "general";

    @Entry(category = GENERAL, isSlider = true, min=0.0f, max=2.5f, precision = 4)
    public static float enchantmentLevelCostMultiplier = 1.0F;
    @Entry(category = GENERAL)
    public static boolean enableGrindstoneMultiplier = true;
}
