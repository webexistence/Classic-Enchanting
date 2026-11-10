package io.github.webexistence.classicenchanting.mixin;

import io.github.webexistence.classicenchanting.util.EnchantmentCostHelper;
import net.minecraft.world.inventory.EnchantmentMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EnchantmentMenu.class)
public class EnchantmentMenuMixin {
    @Shadow
    @Final
    public int[] costs;

    /**
     * The mixin in this class for modifying cost relies on source "enchantmentCost = buttonId + 1";
     * This logic deduces "buttonId", the enchantmentIndex, from "enchantmentCost", the originalCost.
     * We subtract 1 to cancel out the "+ 1" and set our own cost based on the enchantmentPower.
     * @param originalCost Vanilla cost (1,2,3).
     * @return Modified cost.
     */
    @Unique
    private int calculateCostByIndex(int originalCost) {
        int enchantmentIndex = originalCost - 1;
        return EnchantmentCostHelper.calculateEnchantmentCost(this.costs[enchantmentIndex]);
    }

    /**
     * Modifies the original level/lapis cost (server-side). Both level & lapis cost use the same variable.
     * @param originalCost Vanilla cost (1,2,3).
     * @return Modified level/lapis cost.
     */
    @ModifyVariable(
            method = "clickMenuButton",
            at = @At("STORE"),
            name = "enchantmentCost"
    )
    private int changeCost(int originalCost) {
        return calculateCostByIndex(originalCost);
    }
}
