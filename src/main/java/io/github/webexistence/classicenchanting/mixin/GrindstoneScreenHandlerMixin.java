package io.github.webexistence.classicenchanting.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.webexistence.classicenchanting.config.ClassicEnchantingConfig;
import io.github.webexistence.classicenchanting.util.EnchantmentCostHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Target the anonymous inner class
@Mixin(targets = "net.minecraft.screen.GrindstoneScreenHandler$4")
public class GrindstoneScreenHandlerMixin {
    private static final int MAX_ENCHANT_COST_VANILLA = 3;
    private static final int MAX_ENCHANT_LEVEL = 30;

    /**
     * Calculate increased experience value to return from the grindstone.
     * The calculation is based on the configurable enchantment cost multiplier. For example, if using the default
     * cost multiplier of 1.0, then the max enchantment cost is 30. The original cost in vanilla would be only 3.
     * The new cost is 10 times greater than the original cost (30 / 3 = 10). Thus, set the grindstone xp multiplier
     * to be 10.0. The goal is to counter-balance the grindstone in a world with extra expensive enchanting.
     * @param   originalExperience  "i"; The original amount of experienced to be return for a particular item.
     * @return                      Increased experience value.
     */
    @ModifyReturnValue(
            method = "getExperience(Lnet/minecraft/item/ItemStack;)I",
            at = @At("RETURN")
    )
    private int changeGrindstoneExperience(int originalExperience) {
        if (!ClassicEnchantingConfig.enableGrindstoneMultiplier) {
            return originalExperience;
        }
        int maxEnchantCost = EnchantmentCostHelper.calculateEnchantmentCost(MAX_ENCHANT_LEVEL);
        double grindstoneExperienceMultiplier = (double) maxEnchantCost / MAX_ENCHANT_COST_VANILLA;
        return Math.max(1, (int) Math.ceil(originalExperience * grindstoneExperienceMultiplier));
    }
}
