package io.github.webexistence.classicenchanting.mixin;

import net.minecraft.screen.EnchantmentScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EnchantmentScreenHandler.class)
public class EnchantmentHelperMixin {
    @ModifyArg(
            method = "method_17410",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;applyEnchantmentCosts(Lnet/minecraft/item/ItemStack;I)V"
            ),
            index = 1
    )
    private int changeEnchantmentLevelCost(int originalCost) {
        return originalCost * 3;
    }
}
