package me.melontini.crackerutil.mixin.enhanced_enums;

import me.melontini.crackerutil.interfaces.ExtendableEnum;
import me.melontini.crackerutil.reflect.EnumUtils;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(Formatting.class)
public class FormattingMixin implements ExtendableEnum<Formatting> {

    @Shadow
    @Final
    @Mutable
    private static Formatting[] field_1072;

    @Invoker("<init>")
    static Formatting EEnums$invokeCtx(String internalName, int id, String name, char code, boolean modifier, int colorIndex, @Nullable Integer colorValue) {
        throw new IllegalStateException("<init> invoker not implemented");
    }

    private static Formatting EEnums$extendEnum(String internalName, String name, char code, boolean modifier, int colorIndex, @Nullable Integer colorValue) {
        List<Formatting> groups = new ArrayList<>(Arrays.asList(field_1072));
        Formatting last = groups.get(groups.size() - 1);

        Formatting formatting = EEnums$invokeCtx(internalName, last.ordinal() + 1, name, code, modifier, colorIndex, colorValue);
        groups.add(formatting);

        field_1072 = groups.toArray(Formatting[]::new);
        EnumUtils.clearEnumCache(Formatting.class);
        return formatting;
    }

    private static Formatting EEnums$extendEnum(String internalName, String name, char code, int colorIndex, @Nullable Integer colorValue) {
        List<Formatting> groups = new ArrayList<>(Arrays.asList(field_1072));
        Formatting last = groups.get(groups.size() - 1);

        Formatting formatting = EEnums$invokeCtx(internalName, last.ordinal() + 1, name, code, false, colorIndex, colorValue);
        groups.add(formatting);

        field_1072 = groups.toArray(Formatting[]::new);
        EnumUtils.clearEnumCache(Formatting.class);
        return formatting;
    }

    private static Formatting EEnums$extendEnum(String internalName, String name, char code, boolean modifier) {
        List<Formatting> groups = new ArrayList<>(Arrays.asList(field_1072));
        Formatting last = groups.get(groups.size() - 1);

        Formatting formatting = EEnums$invokeCtx(internalName, last.ordinal() + 1, name, code, modifier, -1, null);
        groups.add(formatting);

        field_1072 = groups.toArray(Formatting[]::new);
        EnumUtils.clearEnumCache(Formatting.class);
        return formatting;
    }

    @Override
    public Formatting extend(String internalName, Object... params) {
        try {
            return EEnums$extendEnum(internalName, (String) params[0], (Character) params[1], (boolean) params[2], (int) params[3], (Integer) params[4]);
        } catch (Exception e) {
            try {
                return EEnums$extendEnum(internalName, (String) params[0], (Character) params[1], (int) params[2], (Integer) params[3]);
            } catch (Exception e2) {
                try {
                    return EEnums$extendEnum(internalName, (String) params[0], (Character) params[1], (boolean) params[2]);
                } catch (Exception e3) {
                    throw new RuntimeException("Enum not extended", e3);
                }
            }
        }
    }
}
