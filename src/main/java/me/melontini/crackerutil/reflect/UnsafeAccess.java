package me.melontini.crackerutil.reflect;

import me.melontini.crackerutil.util.MakeSure;
import org.jetbrains.annotations.Nullable;
import sun.misc.Unsafe;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.*;
import java.util.function.Supplier;

import static me.melontini.crackerutil.reflect.ReflectionUtil.getOverrideOffset;

public class UnsafeAccess {
    private static final Unsafe UNSAFE = ((Supplier<Unsafe>) () -> {
        try {
            Field unsafe = Unsafe.class.getDeclaredField("theUnsafe");
            unsafe.setAccessible(true);
            return (Unsafe) unsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            try {
                Constructor<Unsafe> constructor = Unsafe.class.getDeclaredConstructor();
                constructor.setAccessible(true);
                return constructor.newInstance();
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException ex) {
                throw new RuntimeException("Couldn't access Unsafe", ex);
            }
        }
    }).get();
    private static Object internalUnsafe;

    /**
     * You can use this method to write private final fields.
     *
     * @param o this is the Object which contains the field. you should provide a class if your field is static
     */
    public static void writeField(Field field, Object o, Object value) {
        try {
            field.setAccessible(true);
            MethodHandles.lookup().unreflectVarHandle(field).set(o, value);
        } catch (Exception e) {
            long l = Modifier.isStatic(field.getModifiers()) ? UNSAFE.staticFieldOffset(field) : UNSAFE.objectFieldOffset(field);
            UNSAFE.putObject(o, l, value);
        }
    }

    /**
     * You can use this method to read private fields.
     *
     * @param o this is the Object which contains the field. you should provide a class if your field is static
     */
    public static Object getObject(Field field, Object o) {
        try {
            field.setAccessible(true);
            return MethodHandles.lookup().unreflectVarHandle(field).get(o);
        } catch (Exception e) {
            long l = Modifier.isStatic(field.getModifiers()) ? UNSAFE.staticFieldOffset(field) : UNSAFE.objectFieldOffset(field);
            return UNSAFE.getObject(o, l);
        }
    }

    /**
     * @return free {@link Unsafe} for everyone!
     */
    public static Unsafe getUnsafe() {
        return UNSAFE;
    }

    /**
     * Attempts to access the {@link jdk.internal.misc.Unsafe} object.
     *
     * @return the internal Unsafe, or null if it cannot be accessed
     * @throws RuntimeException if an error occurs while trying to access the internal Unsafe object
     */
    @SuppressWarnings("JavadocReference")
    public static @Nullable Object internalUnsafe() {
        if (internalUnsafe == null) {
            try {
                int i = getOverrideOffset();
                Field f2 = Unsafe.class.getDeclaredField("theInternalUnsafe");
                UnsafeAccess.getUnsafe().putBoolean(f2, i, true);//write directly into override to bypass perms
                internalUnsafe = f2.get(null);
            } catch (Exception e) {
                throw new RuntimeException("Couldn't access internal Unsafe", e);
            }
        }
        return internalUnsafe;
    }

    private static Method objectFieldOffset;
    /**
     * Gets the offset of the given field in the given class.
     *
     * @param clazz the class containing the field
     * @param name  the name of the field
     * @return the offset of the given field in the given class
     * @throws RuntimeException if an error occurs while trying to determine the field offset
     */
    public static long getObjectFieldOffset(Class<?> clazz, String name) {
        try {
            if (objectFieldOffset == null) {
                Method method = MakeSure.notNull(internalUnsafe()).getClass().getDeclaredMethod("objectFieldOffset", Class.class, String.class);
                ReflectionUtil.setAccessible(method);
                objectFieldOffset = method;
            }
            return (long) objectFieldOffset.invoke(internalUnsafe(), clazz, name);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
