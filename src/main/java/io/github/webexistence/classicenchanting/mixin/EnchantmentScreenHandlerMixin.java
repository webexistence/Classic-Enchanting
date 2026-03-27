package io.github.webexistence.classicenchanting.mixin;

import io.github.webexistence.classicenchanting.util.EnchantmentCostHelper;
import net.minecraft.screen.EnchantmentScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(EnchantmentScreenHandler.class)
public class EnchantmentScreenHandlerMixin {
    @Shadow
    @Final
    public int[] enchantmentPower;

    /**
     * The mixins in this class for modifying cost both rely on source "i = id + 1"; This logic deduces "id", the
     * enchantmentIndex, from "i", the originalCost. We subtract 1 to cancel out the "+ 1" and set our own cost based
     * on the enchantmentPower.
     * @param originalCost Vanilla cost.
     * @return Modified cost.
     */
    @Unique
    private int calculateCostByIndex(int originalCost) {
        int enchantmentIndex = originalCost - 1;
        return EnchantmentCostHelper.calculateEnchantmentCost(this.enchantmentPower[enchantmentIndex]);
    }

    /**
     * Modifies enchantment level cost (server-side) based on level required and a multiplier.
     * @param originalLevelCost "i"; Vanilla level cost. Used to deduce enchantmentIndex.
     * @return Modified enchantment level cost.
     */
    @ModifyArg(
            method = "method_17410", // lambda method inside onButtonClick()
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;applyEnchantmentCosts(Lnet/minecraft/item/ItemStack;I)V"
            ),
            index = 1
    )
    private int changeEnchantmentCostServer(int originalLevelCost) {
        return calculateCostByIndex(originalLevelCost);
    }

    /**
     * Modifies lapis cost (server-side) based on level required and a multiplier.
     * @param originalLapisCost "i"; Vanilla lapis cost. Used to deduce enchantmentIndex.
     * @return Modified lapis cost.
     */
    @ModifyArg(
            method = "method_17410", // lambda method inside onButtonClick()
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;decrementUnlessCreative(ILnet/minecraft/entity/LivingEntity;)V"
            ),
            index = 0
    )
    private int changeLapisCostServer(int originalLapisCost) {
        return calculateCostByIndex(originalLapisCost);
    }
}
