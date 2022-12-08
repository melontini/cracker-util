package me.melontini.crackerutil.content;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RegistryUtil {
    public static @Nullable Item createItem(Class<?> itemClass, Identifier id, Object... params) {
        return createItem(true, itemClass, id, params);
    }

    public static @Nullable Item createItem(boolean shouldRegister, Class<?> itemClass, Identifier id, Object... params) {
        if (shouldRegister) {
            List<Class<?>> list = new ArrayList<>();
            for (Object o : params) {
                list.add(o.getClass());
            }
            Item item;
            try {
                item = (Item) ConstructorUtils.getMatchingAccessibleConstructor(itemClass, list.toArray(Class[]::new)).newInstance(params);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                throw new RuntimeException(String.format("[" + id.getNamespace() + "] couldn't create item. identifier: %s", id), e);
            }

            Registry.register(Registry.ITEM, id, item);
            return item;
        } else {
            return null;
        }
    }

    public static @Nullable <T extends Entity> EntityType<T> createEntityType(Identifier id, EntityType.Builder<T> builder) {
        return createEntityType(true, id, builder);
    }

    public static @Nullable <T extends Entity> EntityType<T> createEntityType(boolean shouldRegister, Identifier id, EntityType.Builder<T> builder) {
        if (shouldRegister) {
            EntityType<T> type = builder.build(Pattern.compile("[\\W]").matcher(id.toString()).replaceAll("_"));
            Registry.register(Registry.ENTITY_TYPE, id, type);
            return type;
        }
        return null;
    }

    public static @Nullable Block createBlock(Class<?> blockClass, Identifier id, Object... params) {
        return createBlock(true, blockClass, id, params);
    }

    public static @Nullable Block createBlock(boolean shouldRegister, Class<?> blockClass, Identifier id, Object... params) {
        if (shouldRegister) {
            List<Class<?>> list = new ArrayList<>();
            for (Object o : params) {
                list.add(o.getClass());
            }
            Block block;
            try {
                block = (Block) ConstructorUtils.getMatchingAccessibleConstructor(blockClass, list.toArray(Class[]::new)).newInstance(params);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                throw new RuntimeException(String.format("[" + id.getNamespace() + "] couldn't create block. identifier: %s", id), e);
            }

            Registry.register(Registry.BLOCK, id, block);
            return block;
        }
        return null;
    }

    public static @Nullable <T extends BlockEntity> BlockEntityType<T> createBlockEntity(Identifier id, BlockEntityType.Builder<T> builder) {
        return createBlockEntity(true, id, builder);
    }

    public static @Nullable <T extends BlockEntity> BlockEntityType<T> createBlockEntity(boolean shouldRegister, Identifier id, BlockEntityType.Builder<T> builder) {
        if (shouldRegister) {
            BlockEntityType<T> type = builder.build(null);
            Registry.register(Registry.BLOCK_ENTITY_TYPE, id, type);
            return type;
        }
        return null;
    }
}
