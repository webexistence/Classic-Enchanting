package io.github.webexistence.classicenchanting.mixin;

import io.github.webexistence.classicenchanting.util.EnchantmentCostHelper;
import net.minecraft.screen.EnchantmentScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EnchantmentScreenHandler.class)
public class EnchantmentScreenHandlerMixin {
    @Shadow
    @Final
    public int[] enchantmentPower;

    /**
     * The mixin in this class for modifying cost relies on source "i = id + 1"; This logic deduces "id", the
     * enchantmentIndex, from "i", the originalCost. We subtract 1 to cancel out the "+ 1" and set our own cost based
     * on the enchantmentPower.
     * @param originalCost Vanilla cost (1,2,3).
     * @return Modified cost.
     */
    @Unique
    private int calculateCostByIndex(int originalCost) {
        int enchantmentIndex = originalCost - 1;
        return EnchantmentCostHelper.calculateEnchantmentCost(this.enchantmentPower[enchantmentIndex]);
    }

    /**
     * Modifies the original level/lapis cost (server-side). Both level & lapis cost use the same variable.
     * @param originalCost "i"; Vanilla cost (1,2,3).
     * @return Modified level/lapis cost.
     */
    @ModifyVariable(
            method="onButtonClick",
            at=@At("STORE"),
            ordinal=1 // "i"
    )
    private int changeCost(int originalCost) {
        return calculateCostByIndex(originalCost);
    }
}
