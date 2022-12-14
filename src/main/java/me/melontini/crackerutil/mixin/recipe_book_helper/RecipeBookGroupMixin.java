package me.melontini.crackerutil.mixin.recipe_book_helper;

import me.melontini.crackerutil.content.RecipeBookHelper;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.recipe.book.RecipeBookCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(RecipeBookGroup.class)
public class RecipeBookGroupMixin {
    @Inject(at = @At("HEAD"), method = "getGroups", cancellable = true)
    private static void cracker_util$getGroups(RecipeBookCategory category, CallbackInfoReturnable<List<RecipeBookGroup>> cir) {
        if (RecipeBookHelper.CATEGORY_TO_LIST.containsKey(category)) cir.setReturnValue(RecipeBookHelper.CATEGORY_TO_LIST.get(category));
    }
}
