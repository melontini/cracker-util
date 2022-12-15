package me.melontini.crackerutil.mixin.kill_immutables;

import com.mojang.datafixers.util.Pair;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.book.RecipeBookOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;
import java.util.Map;

@Mixin(value = RecipeBookOptions.class, priority = 800)
public class RecipeBookOptionsMixin {
    @Shadow
    @Final
    @Mutable
    private static final Map<RecipeBookCategory, Pair<String, String>> CATEGORY_OPTION_NAMES = new HashMap<>(Map.of(
            RecipeBookCategory.CRAFTING,
            Pair.of("isGuiOpen", "isFilteringCraftable"),
            RecipeBookCategory.FURNACE,
            Pair.of("isFurnaceGuiOpen", "isFurnaceFilteringCraftable"),
            RecipeBookCategory.BLAST_FURNACE,
            Pair.of("isBlastingFurnaceGuiOpen", "isBlastingFurnaceFilteringCraftable"),
            RecipeBookCategory.SMOKER,
            Pair.of("isSmokerGuiOpen", "isSmokerFilteringCraftable")
    ));
}
