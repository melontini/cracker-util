package me.melontini.crackerutil.mixin.enhanced_enums;

import me.melontini.crackerutil.interfaces.ExtendableEnum;
import me.melontini.crackerutil.reflect.EnumUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(BoatEntity.Type.class)
public class BoatEntityTypeMixin implements ExtendableEnum<BoatEntity.Type> {
    @Shadow
    @Final
    @Mutable
    private static BoatEntity.Type[] field_7724;

    @Invoker("<init>")
    static BoatEntity.Type EEnums$invokeCtx(String internalName, int id, Block base, String name) {
        throw new IllegalStateException("<init> invoker not implemented");
    }

    private static BoatEntity.Type EEnums$extendEnum(String internalName, Block base, String name) {
        List<BoatEntity.Type> groups = new ArrayList<>(Arrays.asList(field_7724));
        BoatEntity.Type last = groups.get(groups.size() - 1);

        BoatEntity.Type category = EEnums$invokeCtx(internalName, last.ordinal() + 1, base, name);
        groups.add(category);

        field_7724 = groups.toArray(BoatEntity.Type[]::new);
        EnumUtils.clearEnumCache(BoatEntity.Type.class);
        return category;
    }

    @Override
    public BoatEntity.Type extend(String internalName, Object... params) {
        return EEnums$extendEnum(internalName, (Block) params[0], (String) params[1]);
    }
}
