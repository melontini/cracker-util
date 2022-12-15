package me.melontini.crackerutil.mixin.recipe_book_helper.pages;

import me.melontini.crackerutil.interfaces.PaginatedRecipeGroupButtonWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeGroupButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(RecipeGroupButtonWidget.class)
public abstract class RecipeGroupButtonMixin implements PaginatedRecipeGroupButtonWidget {
    @Unique
    private int page = -1;

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public void setPage(int page) {
        this.page = page;
    }
}
