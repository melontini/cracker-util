package me.melontini.crackerutil.interfaces;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemGroup;

public interface ItemGroupExtensions {
    @Environment(EnvType.CLIENT)
    default boolean shouldAnimateIcon() {
        throw new IllegalStateException("Interface not implemented");
    }

    @Environment(EnvType.CLIENT)
    default ItemGroup setIconAnimation(AnimatedItemGroup animation) {
        throw new IllegalStateException("Interface not implemented");
    }

    @Environment(EnvType.CLIENT)
    default AnimatedItemGroup getIconAnimation() {
        throw new IllegalStateException("Interface not implemented");
    }
}
