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
import org.spongepowered.asm.mixin.injection.*;
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
    private void removeEnchantClue(
            DrawContext context,
            int mouseX,
            int mouseY,
            float deltaTicks,
            CallbackInfo ci,
            @Local(ordinal = 0) List<Text> enchantTooltipTextList // name = "list"
    ) {
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
            ordinal = 6 // name = "m"
    )
    private int changeEnchantmentCostClient(
            int originalEnchantmentCost,
            @Local(ordinal = 4) int enchantmentPowerInt // name = "k"
    ) {
        return EnchantmentCostHelper.calculateEnchantmentCost(enchantmentPowerInt);
    }

    /**
     * Set enchantment slot to have "disabled" texture if you do not have enough lapis (following the cost multiplier).
     * Without this, the source code uses the vanilla lapis costs of (1,2,3) to determine whether it will highlight
     * the enchantment slot (to indicate that the player can currently afford it).
     * The original source comparison is "lapisCount < enchantmentIndex + 1". The math here modifies the "1".
     * @param original The "1" from the source comparison.
     * @param enchantmentIndex "l"; values (0,1,2).
     * @param enchantmentPowerInt "o"; Experience level required to enchant.
     * @return New integer to place into source comparison.
     */
    @ModifyConstant(
            method = "drawBackground",
            constant = @Constant(intValue = 1, ordinal = 0)
    )
    private int enchantmentSlotDisabledTexture(
            int original,
//            @Local(ordinal = 0) int zero,
//            @Local(ordinal = 1) int one,
//            @Local(ordinal = 2) int two,
//            @Local(ordinal = 3) int three,
//            @Local(ordinal = 4) int four,
//            @Local(ordinal = 5) int five,
//            @Local(ordinal = 6) int six,
//            @Local(ordinal = 7) int seven,
            @Local(ordinal = 5) int enchantmentIndex, // name = "l"
            @Local(ordinal = 8) int enchantmentPowerInt // name = "o"
    ) {
//        System.out.println();
//        System.out.println("zero: " + zero);
//        System.out.println("one: " + one);
//        System.out.println("two: " + two);
//        System.out.println("three: " + three);
//        System.out.println("four: " + four);
//        System.out.println("five: " + five);
//        System.out.println("six: " + six);
//        System.out.println("seven: " + seven);
//        System.out.println("DEBUG: enchantmentIndex: " + enchantmentIndex);
//        System.out.println("DEBUG: enchantmentPowerInt: " + enchantmentPowerInt);

        return enchantmentPowerInt - enchantmentIndex;
    }

//    @ModifyVariable(
//            method = "drawBackground",
//            at = @At(value = "STORE"),
//            ordinal = 2 // name = "k"
//    )
//    private int enchantmentSlotDisabledTexture(
//            int originalEnchantmentCost,
//            @Local(ordinal = 0) int zero,
//            @Local(ordinal = 1) int one,
//            @Local(ordinal = 2) int two,
//            @Local(ordinal = 2) int enchantmentPowerInt // name = "o"
//    ) {
//        System.out.println();
//        System.out.println("zero: " + zero);
//        System.out.println("one: " + one);
//        System.out.println("two: " + two);
//        return 1;
//    }


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
            @Local(ordinal = 0) List<Text> enchantTooltipTextList, // name = "list"
//            @Local(ordinal = 0) int zero,
//            @Local(ordinal = 1) int one,
//            @Local(ordinal = 2) int two,
//            @Local(ordinal = 3) int three,
//            @Local(ordinal = 4) int four,
//            @Local(ordinal = 5) int five,
//            @Local(ordinal = 6) int six,
            @Local(ordinal = 4) int enchantmentPowerInt // name = "k"
    ) {
//        System.out.println();
//        System.out.println("zero: " + zero);
//        System.out.println("one: " + one);
//        System.out.println("two: " + two);
//        System.out.println("three: " + three);
//        System.out.println("four: " + four);
//        System.out.println("five: " + five);
//        System.out.println("six: " + six);
        // TODO: currently, this seems to work on both dev and real env. However, real env seems to only work after enchanting something. figure this out? idk

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
