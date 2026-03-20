package io.github.webexistence.classicenchanting.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.EnchantmentScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EnchantmentScreen.class)
public class EnchantmentScreenMixin {
    @Inject(method = "render", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/DrawContext;drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;II)V"))
    private void removeEnchantClue(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci, @Local(name = "list") List<Text> enchantTooltipTextList) {
        enchantTooltipTextList.subList(0, Math.min(2, enchantTooltipTextList.size())).clear();
    }

    /**
     * Modifies the displayed enchantment cost (client-side) based on level required and a multiplier.
     * @param   originalEnchantmentCost "m"; Equal to (enchantmentIndex + 1). Values (1,2,3). Unused.
     * @param   enchantmentPowerInt     "k"; Experience level required to enchant.
     * return                           The modified enchantment cost.
     */
    @ModifyVariable(
            method = "render",
            at = @At(value = "STORE"),
            name = "m"
    )
    private int changeEnchantmentCostClient(
            int originalEnchantmentCost,
            @Local(name = "k") int enchantmentPowerInt
    ) {
        float enchantmentLevelCostMultiplier = 0.5F; // TODO: make this configurable via json
        return Math.round(enchantmentPowerInt * enchantmentLevelCostMultiplier);
    }
}
