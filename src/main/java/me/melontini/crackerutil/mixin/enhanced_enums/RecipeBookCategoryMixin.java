package me.melontini.crackerutil.mixin.enhanced_enums;

import me.melontini.crackerutil.interfaces.ExtendableEnum;
import me.melontini.crackerutil.reflect.EnumUtils;
import net.minecraft.recipe.book.RecipeBookCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(RecipeBookCategory.class)
public class RecipeBookCategoryMixin implements ExtendableEnum<RecipeBookCategory> {
    @Shadow
    @Final
    @Mutable
    private static RecipeBookCategory[] field_25767;

    @Invoker("<init>")
    static RecipeBookCategory EEnums$invokeCtx(String internalName, int id) {
        throw new IllegalStateException("<init> invoker not implemented");
    }

    private static RecipeBookCategory EEnums$extendEnum(String internalName) {
        List<RecipeBookCategory> groups = new ArrayList<>(Arrays.asList(field_25767));
        RecipeBookCategory last = groups.get(groups.size() - 1);

        RecipeBookCategory category = EEnums$invokeCtx(internalName, last.ordinal() + 1);
        groups.add(category);

        field_25767 = groups.toArray(RecipeBookCategory[]::new);
        EnumUtils.clearEnumCache(RecipeBookCategory.class);
        return category;
    }

    @Override
    public RecipeBookCategory extend(String internalName, Object... params) {
        return EEnums$extendEnum(internalName);
    }
}
