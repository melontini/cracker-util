package me.melontini.crackerutil.mixin.recipe_book_helper;

import me.melontini.crackerutil.content.RecipeBookHelper;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.recipe.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin {
    @Inject(at = @At("HEAD"), method = "getGroupForRecipe", cancellable = true)
    private static void cracker_util$getGroupForRecipe(Recipe<?> recipe, CallbackInfoReturnable<RecipeBookGroup> cir) {
        if (RecipeBookHelper.TYPE_HANDLERS.containsKey(recipe.getType())) {
            RecipeBookGroup group = null;

            for (Function<Recipe<?>, RecipeBookGroup> function : RecipeBookHelper.TYPE_HANDLERS.get(recipe.getType())) {
                group = function.apply(recipe);
                if (group != null) break;
            }

            if (group != null) cir.setReturnValue(group);
        }
    }
}
