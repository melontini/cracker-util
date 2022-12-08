package me.melontini.crackerutil.mixin.kill_immutables;

import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mixin(value = TextColor.class, priority = 800)
public class TextColorMixin {
    @Shadow
    @Final
    @Mutable
    private static Map<Formatting, TextColor> FORMATTING_TO_COLOR = Stream.of(Formatting.values())
            .filter(Formatting::isColor)
            .collect(Collectors.toMap(Function.identity(), formatting -> new TextColor(formatting.getColorValue(), formatting.getName())));
    @Shadow
    @Final
    @Mutable
    private static Map<String, TextColor> BY_NAME = FORMATTING_TO_COLOR.values()
            .stream()
            .collect(Collectors.toMap(textColor -> textColor.name, Function.identity()));

    //ImmutableMap.toImmutableMap(textColor -> textColor.name, Function.identity())
}
