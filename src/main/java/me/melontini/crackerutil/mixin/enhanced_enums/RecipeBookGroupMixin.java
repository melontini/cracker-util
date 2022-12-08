package me.melontini.crackerutil.mixin.enhanced_enums;

import me.melontini.crackerutil.interfaces.ExtendableEnum;
import me.melontini.crackerutil.reflect.EnumUtils;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(RecipeBookGroup.class)
public class RecipeBookGroupMixin implements ExtendableEnum<RecipeBookGroup> {

    @Final
    @Shadow
    @Mutable
    private static RecipeBookGroup[] field_1805;

    @Invoker("<init>")
    static RecipeBookGroup EEnums$invokeCtx(String internalName, int id, ItemStack... stacks) {
        throw new IllegalStateException("<init> invoker not implemented");
    }

    private static RecipeBookGroup EEnums$extendEnum(String internalName, ItemStack... stacks) {
        List<RecipeBookGroup> groups = new ArrayList<>(Arrays.asList(field_1805));
        RecipeBookGroup last = groups.get(groups.size() - 1);

        RecipeBookGroup category = EEnums$invokeCtx(internalName, last.ordinal() + 1, stacks);
        groups.add(category);

        field_1805 = groups.toArray(RecipeBookGroup[]::new);
        EnumUtils.clearEnumCache(RecipeBookGroup.class);
        return category;
    }

    @Override
    public RecipeBookGroup extend(String internalName, Object... params) {
        return EEnums$extendEnum(internalName, (ItemStack[]) params);
    }
}
