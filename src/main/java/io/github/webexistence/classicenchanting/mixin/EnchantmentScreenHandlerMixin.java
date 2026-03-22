package io.github.webexistence.classicenchanting.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.webexistence.classicenchanting.util.EnchantmentCostHelper;
import net.minecraft.screen.EnchantmentScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EnchantmentScreenHandler.class)
public class EnchantmentScreenHandlerMixin {
    @Shadow @Final public int[] enchantmentPower;

    /**
     * Modifies enchantment cost (server-side) based on level required and a multiplier.
     * @param   originalEnchantmentCost "i"; Equal to (enchantmentIndex + 1). Values (1,2,3). Unused; this is the target.
     * @param   enchantmentIndex        "id"; Index of enchantment. Values (0,1,2).
     * return                           The modified enchantment cost.
     */
    @ModifyVariable(
            method = "onButtonClick",
            at = @At(value = "STORE"),
            name = "i"
    )
    private int changeEnchantmentCostServer(int originalEnchantmentCost, @Local(name = "id") int enchantmentIndex) {
        return EnchantmentCostHelper.calculateEnchantmentCost(this.enchantmentPower[enchantmentIndex]);
    }
}
