package io.github.webexistence.classicenchanting.mixin;

import io.github.webexistence.classicenchanting.util.EnchantmentCostHelper;
import net.minecraft.screen.EnchantmentScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(EnchantmentScreenHandler.class)
public class EnchantmentScreenHandlerMixin {
    @Shadow
    @Final
    public int[] enchantmentPower;

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
        int enchantmentIndex = originalLevelCost - 1;
        return EnchantmentCostHelper.calculateEnchantmentCost(this.enchantmentPower[enchantmentIndex]);
    }
}
