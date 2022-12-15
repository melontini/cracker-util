package me.melontini.crackerutil.interfaces;

import net.minecraft.item.ItemGroup;

public interface ItemGroupExtensions {
    default boolean shouldAnimateIcon() {
        throw new IllegalStateException("Interface not implemented");
    }

    default ItemGroup setIconAnimation(AnimatedItemGroup animation) {
        throw new IllegalStateException("Interface not implemented");
    }

    default AnimatedItemGroup getIconAnimation() {
        throw new IllegalStateException("Interface not implemented");
    }
}
