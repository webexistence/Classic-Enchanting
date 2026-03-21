package io.github.webexistence.classicenchanting.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// We have to target the anonymous inner class
@Mixin(targets = "net/minecraft/screen/GrindstoneScreenHandler$4")
public class GrindstoneScreenHandlerMixin {
    @ModifyReturnValue(
            method = "getExperience(Lnet/minecraft/item/ItemStack;)I",
            at = @At("RETURN")
    )
    private int changeExperience(int originalExperience) {
        float grindstoneExperienceMultiplier = 5.0F; // TODO: make this configurable via json
        // TODO: have grindstoneExperienceMultiplier calculate a default value based on enchantmentLevelCostMultiplier.
        return Math.max(1, (int) Math.ceil(originalExperience * grindstoneExperienceMultiplier));
    }
}
