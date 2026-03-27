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

//    /**
//     * Modifies enchantment cost (server-side) based on level required and a multiplier.
//     * The original source assignment here is "enchantmentCost = enchantmentIndex + 1". The math here modifies the "1".
//     * This is done because my previous implementation using @ModifyVariable had issues changing it directly.
//     * @param original
//     * @param enchantmentIndex
//     * @return
//     */
//    @ModifyConstant(
//            method = "onButtonClick",
//            constant = @Constant(intValue = 1, ordinal = 0)
//    )
//    private int changeEnchantmentCostServer(int original, @Local(name = "id") int enchantmentIndex) {
//        int enchantmentCost = EnchantmentCostHelper.calculateEnchantmentCost(this.enchantmentPower[enchantmentIndex]);
//        System.out.println("original:" + original);
//        System.out.println("enchantmentIndex:" + enchantmentIndex);
//        System.out.println("enchantmentCost:" + enchantmentCost);
//        return enchantmentCost - enchantmentIndex;
//    }

    /**
     * TODO
     * @param experienceLevels
     * @return
     */
    @ModifyArg(
            method = "method_17410",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;applyEnchantmentCosts(Lnet/minecraft/item/ItemStack;I)V"
            ),
            index = 1
    )
    private int changeEnchantmentCostServer(int experienceLevels) {
        int enchantmentIndex = experienceLevels - 1;
        return EnchantmentCostHelper.calculateEnchantmentCost(this.enchantmentPower[enchantmentIndex]);
    }
}
