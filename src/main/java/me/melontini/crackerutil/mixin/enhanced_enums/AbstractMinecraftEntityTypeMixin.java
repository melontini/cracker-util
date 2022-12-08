package me.melontini.crackerutil.mixin.enhanced_enums;

import me.melontini.crackerutil.interfaces.ExtendableEnum;
import me.melontini.crackerutil.reflect.EnumUtils;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(AbstractMinecartEntity.Type.class)
public class AbstractMinecraftEntityTypeMixin implements ExtendableEnum<AbstractMinecartEntity.Type> {
    @Shadow
    @Final
    @Mutable
    private static AbstractMinecartEntity.Type[] field_7673;

    @Invoker("<init>")
    static AbstractMinecartEntity.Type EEnums$invokeCtx(String internalName, int id) {
        throw new IllegalStateException("<init> invoker not implemented");
    }

    private static AbstractMinecartEntity.Type EEnums$extendEnum(String internalName) {
        List<AbstractMinecartEntity.Type> groups = new ArrayList<>(Arrays.asList(field_7673));
        AbstractMinecartEntity.Type last = groups.get(groups.size() - 1);

        AbstractMinecartEntity.Type category = EEnums$invokeCtx(internalName, last.ordinal() + 1);
        groups.add(category);

        field_7673 = groups.toArray(AbstractMinecartEntity.Type[]::new);
        EnumUtils.clearEnumCache(AbstractMinecartEntity.Type.class);
        return category;
    }

    @Override
    public AbstractMinecartEntity.Type extend(String internalName, Object... params) {
        return EEnums$extendEnum(internalName);
    }
}
