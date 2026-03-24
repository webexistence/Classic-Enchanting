package io.github.webexistence.classicenchanting.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.webexistence.classicenchanting.util.EnchantmentCostHelper;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.EnchantmentScreen;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EnchantmentScreen.class)
public class EnchantmentScreenMixin {
    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;II)V"
            )
    )
    private void removeEnchantClue(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci, @Local(name = "list") List<Text> enchantTooltipTextList) {
        enchantTooltipTextList.subList(0, Math.min(2, enchantTooltipTextList.size())).clear();
    }

    /**
     * Modifies the displayed enchantment cost (client-side) based on level required and a multiplier.
     * @param   originalEnchantmentCost "m"; Equal to (enchantmentIndex + 1). Values (1,2,3). Unused; this is the target.
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
        return EnchantmentCostHelper.calculateEnchantmentCost(enchantmentPowerInt);
    }

    /**
     * When your experience level is below required amount, we still want to show the actual cost of it.
     * Exception when the cost multiplier is 1.0, which makes XP lvl cost the same as the requirement. In this case,
     * the extra tooltip info is redundant.
     */
    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    shift = At.Shift.AFTER
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=container.enchant.level.requirement"
                    ),
                    to = @At(
                            value = "CONSTANT",
                            args = "stringValue=container.enchant.lapis.one"
                    )
            )
    )
    private void levelCostTooltip(
            DrawContext context,
            int mouseX,
            int mouseY,
            float deltaTicks,
            CallbackInfo ci,
            @Local(name = "list") List<Text> enchantTooltipTextList,
            @Local(name = "k") int enchantmentPowerInt
    ) {
        int enchantmentCost = EnchantmentCostHelper.calculateEnchantmentCost(enchantmentPowerInt);
        if (enchantmentCost >= 30) {
            return;
        }
        MutableText mutableText;
        if (enchantmentCost == 1) {
            mutableText = Text.translatable("container.enchant.level.one");
        } else {
            mutableText = Text.translatable("container.enchant.level.many", new Object[]{enchantmentCost});
        }
        enchantTooltipTextList.add(ScreenTexts.EMPTY);
        enchantTooltipTextList.add(mutableText.formatted(Formatting.RED));
    }

}
