package me.melontini.crackerutil.mixin.enhanced_enums;

import me.melontini.crackerutil.interfaces.ExtendableEnum;
import me.melontini.crackerutil.reflect.EnumUtils;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(Rarity.class)
public class RarityMixin implements ExtendableEnum<Rarity> {
    @Final
    @Shadow
    @Mutable
    private static Rarity[] field_8905;

    @Invoker("<init>")
    static Rarity EEnums$invokeCtx(String internalName, int id, Formatting formatting) {
        throw new IllegalStateException("<init> invoker not implemented");
    }

    private static Rarity EEnums$extendEnum(String internalName, Formatting formatting) {
        List<Rarity> groups = new ArrayList<>(Arrays.asList(field_8905));
        Rarity last = groups.get(groups.size() - 1);

        Rarity rarity = EEnums$invokeCtx(internalName, last.ordinal() + 1, formatting);
        groups.add(rarity);

        field_8905 = groups.toArray(Rarity[]::new);
        EnumUtils.clearEnumCache(Rarity.class);
        return rarity;
    }

    @Override
    public Rarity extend(String internalName, Object... params) {
        return EEnums$extendEnum(internalName, (Formatting) params[0]);
    }
}
