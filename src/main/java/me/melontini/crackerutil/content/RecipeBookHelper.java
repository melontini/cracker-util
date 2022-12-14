package me.melontini.crackerutil.content;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import me.melontini.crackerutil.CrackerLog;
import me.melontini.crackerutil.util.MakeSure;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.book.RecipeBookOptions;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@ApiStatus.Experimental
public class RecipeBookHelper {
    @Environment(EnvType.CLIENT)
    public static final Map<RecipeType<?>, List<Function<Recipe<?>, RecipeBookGroup>>> TYPE_HANDLERS = new ConcurrentHashMap<>();
    @Environment(EnvType.CLIENT)
    public static final Map<RecipeBookCategory, List<RecipeBookGroup>> CATEGORY_TO_LIST = new ConcurrentHashMap<>();
    private static final List<RecipeBookCategory> RESTRICTED_CATEGORIES = ImmutableList.of(RecipeBookCategory.CRAFTING, RecipeBookCategory.FURNACE, RecipeBookCategory.BLAST_FURNACE, RecipeBookCategory.SMOKER);


    @Environment(EnvType.CLIENT)
    public static void addRecipePredicate(RecipeType<?> type, Function<Recipe<?>, RecipeBookGroup> function) {
        if (TYPE_HANDLERS.containsKey(type)) {
            var list = TYPE_HANDLERS.get(type);
            if (!list.contains(function)) {
                list.add(function);
            }
        } else {
            TYPE_HANDLERS.computeIfAbsent(type, type1 -> new ArrayList<>()).add(function);
        }
    }

    public static void addToGetGroups(RecipeBookCategory category, List<RecipeBookGroup> groups) {
        if (RESTRICTED_CATEGORIES.contains(category)) throw new IllegalArgumentException("Tried to use addToGetGroups for vanilla groups. Use RecipeBookGroup.(YOUR_CATEGORY) instead. \nPossible method caller: " + CrackerLog.getCallerName());
        MakeSure.notEmpty(groups, "Empty group list provided. \nPossible method caller: " + CrackerLog.getCallerName());

        if (CATEGORY_TO_LIST.containsKey(category)) {
            CATEGORY_TO_LIST.get(category).addAll(groups);
        } else {
            CATEGORY_TO_LIST.computeIfAbsent(category, category1 -> new ArrayList<>()).addAll(groups);
        }
    }

    public static void addToGetGroups(RecipeBookCategory category, int index, List<RecipeBookGroup> groups) {
        if (RESTRICTED_CATEGORIES.contains(category)) throw new IllegalArgumentException("Tried to use addToGetGroups for vanilla groups. Use RecipeBookGroup.(YOUR_CATEGORY) instead. \nPossible method caller: " + CrackerLog.getCallerName());
        MakeSure.notEmpty(groups, "Empty group list provided. \nPossible method caller: " + CrackerLog.getCallerName());
        MakeSure.isFalse(index < 0, "Index can't be below 0! \nPossible method caller: " + CrackerLog.getCallerName());

        if (CATEGORY_TO_LIST.containsKey(category)) {
            CATEGORY_TO_LIST.get(category).addAll(index, groups);
        } else {
            CATEGORY_TO_LIST.computeIfAbsent(category, category1 -> new ArrayList<>()).addAll(groups);
        }
    }

    public static RecipeBookCategory createCategory(String internalName) {
        MakeSure.notEmpty(internalName, "tried to register a RecipeBookCategory with an empty string. \nPossible method caller: " + CrackerLog.getCallerName());

        RecipeBookCategory category = (RecipeBookCategory) RecipeBookCategory.CRAFTING.extend(internalName);
        RecipeBookOptions.CATEGORY_OPTION_NAMES.put(category, new Pair<>("is" + internalName + "GuiOpen", "is" + internalName + "FilteringCraftable"));
        return category;
    }
}
