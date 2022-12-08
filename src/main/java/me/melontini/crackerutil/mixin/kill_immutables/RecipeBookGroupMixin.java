package me.melontini.crackerutil.mixin.kill_immutables;

import net.minecraft.client.recipebook.RecipeBookGroup;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.minecraft.client.recipebook.RecipeBookGroup.*;

@Mixin(value = RecipeBookGroup.class, priority = 800)
public class RecipeBookGroupMixin {
    @Shadow
    @Final
    @Mutable
    public static List<RecipeBookGroup> SMOKER = new ArrayList<>(List.of(SMOKER_SEARCH, SMOKER_FOOD));
    @Shadow
    @Final
    @Mutable
    public static List<RecipeBookGroup> BLAST_FURNACE = new ArrayList<>(List.of(BLAST_FURNACE_SEARCH, BLAST_FURNACE_BLOCKS, BLAST_FURNACE_MISC));
    @Shadow
    @Final
    @Mutable
    public static List<RecipeBookGroup> FURNACE = new ArrayList<>(List.of(FURNACE_SEARCH, FURNACE_FOOD, FURNACE_BLOCKS, FURNACE_MISC));
    @Shadow
    @Final
    @Mutable
    public static List<RecipeBookGroup> CRAFTING = new ArrayList<>(List.of(CRAFTING_SEARCH, CRAFTING_EQUIPMENT, CRAFTING_BUILDING_BLOCKS, CRAFTING_MISC, CRAFTING_REDSTONE));
    @Shadow
    @Final
    @Mutable
    public static Map<RecipeBookGroup, List<RecipeBookGroup>> SEARCH_MAP = new HashMap<>(Map.of(
            CRAFTING_SEARCH,
            new ArrayList<>(List.of(CRAFTING_EQUIPMENT, CRAFTING_BUILDING_BLOCKS, CRAFTING_MISC, CRAFTING_REDSTONE)),
            FURNACE_SEARCH,
            new ArrayList<>(List.of(FURNACE_FOOD, FURNACE_BLOCKS, FURNACE_MISC)),
            BLAST_FURNACE_SEARCH,
            new ArrayList<>(List.of(BLAST_FURNACE_BLOCKS, BLAST_FURNACE_MISC)),
            SMOKER_SEARCH,
            new ArrayList<>(List.of(SMOKER_FOOD))));
}
