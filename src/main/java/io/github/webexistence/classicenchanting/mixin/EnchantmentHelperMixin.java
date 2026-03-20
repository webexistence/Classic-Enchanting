package io.github.webexistence.classicenchanting.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.screen.EnchantmentScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EnchantmentScreenHandler.class)
public class EnchantmentHelperMixin {
    @Shadow
    @Final
    public int[] enchantmentPower;

    /**
     * Modifies enchantment cost (server-side) based on level required and a multiplier.
     * @param   originalEnchantmentCost "i"; Equal to (enchantmentIndex + 1). Values (1,2,3). Unused.
     * @param   enchantmentIndex        "id"; Index of enchantment. Values (0,1,2).
     * return                           The modified enchantment cost.
     */
    @ModifyVariable(
            method = "onButtonClick",
            at = @At(value = "STORE"),
            name = "i"
    )
    private int changeEnchantmentCostServer(int originalEnchantmentCost, @Local(name = "id") int enchantmentIndex) {
        float enchantmentLevelCostMultiplier = 0.5F; // TODO: make this configurable via json
        // enchantmentPower[0,1,2] contains the level requirements (max is 30).
        return (int) Math.floor(this.enchantmentPower[enchantmentIndex] * enchantmentLevelCostMultiplier);
    }
}
