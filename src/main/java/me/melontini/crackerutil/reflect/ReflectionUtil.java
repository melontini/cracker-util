package me.melontini.crackerutil.reflect;

import me.melontini.crackerutil.util.MakeSure;
import org.apache.commons.lang3.ClassUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sun.misc.Unsafe;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="https://stackoverflow.com/questions/55918972/unable-to-find-method-sun-misc-unsafe-defineclass">source</a>
 */
public class ReflectionUtil {
    private static int offset = -1;
    private static Method forName0;
    private static Constructor<?> handlesMockConstructor;

    /**
     * Attempts to find a constructor for the given class that matches the given array of arguments.
     *
     * @param clazz the class for which to find a constructor
     * @param args  the array of arguments that the constructor must be able to accept
     * @return a constructor for the given class that matches the given array of arguments, or null if no such constructor is found
     */
    public static @Nullable <T> Constructor<T> findConstructor(Class<T> clazz, Object... args) {
        return findConstructor(clazz, Arrays.stream(args).toList());
    }

    /**
     * Attempts to find a constructor for the given class that matches the given list of arguments.
     *
     * @param clazz the class for which to find a constructor
     * @param args  the list of arguments that the constructor must be able to accept
     * @return a constructor for the given class that matches the given list of arguments, or null if no such constructor is found
     */
    public static @Nullable <T> Constructor<T> findConstructor(@NotNull Class<T> clazz, List<Object> args) {
        Constructor<T> c = null;
        if (clazz.getDeclaredConstructors().length == 1) {
            c = (Constructor<T>) clazz.getDeclaredConstructors()[0];// we can skip loops if there's only 1 constructor in a class.
        } else {
            Class<?>[] classes = args.stream().map(Object::getClass).toArray(Class[]::new);

            try {
                c = clazz.getDeclaredConstructor(classes);
            } catch (Exception e) {
                try {
                    for (Constructor<?> declaredConstructor : clazz.getDeclaredConstructors()) {
                        if (declaredConstructor.getParameterCount() == args.size()) {
                            boolean bool = true;
                            Class<?>[] pt = declaredConstructor.getParameterTypes();
                            for (int i = 0; i < declaredConstructor.getParameterCount(); i++) {
                                if (!ClassUtils.isAssignable(classes[i], pt[i])) {
                                    bool = false;
                                    break;
                                }
                            }
                            if (bool) {
                                c = (Constructor<T>) declaredConstructor;
                                break;
                            }
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        }
        return c;
    }

    /**
     * Attempts to set a constructor as accessible.
     *
     * <p>
     * This method uses the vanilla `Constructor.setAccessible(true)` method, but falls back to using `Unsafe.putBoolean(i, true)` in case reflection fails.
     * </p>
     *
     * @param constructor the constructor to set as accessible
     */
    public static void setAccessible(Constructor<?> constructor) {
        try {
            constructor.setAccessible(true);
        } catch (Exception e) {
            int i = getOverrideOffset();
            UnsafeAccess.getUnsafe().putBoolean(constructor, i, true);
        }
    }

    /**
     * Attempts to set a method as accessible.
     *
     * <p>
     * This method uses the vanilla `Method.setAccessible(true)` method, but falls back to using `Unsafe.putBoolean(i, true)` in case reflection fails.
     * </p>
     *
     * @param method the method to set as accessible
     */
    public static void setAccessible(Method method) {
        try {
            method.setAccessible(true);
        } catch (Exception e) {
            int i = getOverrideOffset();
            UnsafeAccess.getUnsafe().putBoolean(method, i, true);
        }
    }

    /**
     * Attempts to set a field as accessible.
     *
     * <p>
     * This method uses the vanilla `Field.setAccessible(true)` method, but falls back to using `Unsafe.putBoolean(i, true)` in case reflection fails.
     * </p>
     *
     * @param field the field to set as accessible
     */
    public static void setAccessible(Field field) {
        try {
            field.setAccessible(true);
        } catch (Exception e) {
            int i = getOverrideOffset();
            UnsafeAccess.getUnsafe().putBoolean(field, i, true);
        }
    }

    //https://stackoverflow.com/questions/55918972/unable-to-find-method-sun-misc-unsafe-defineclass
    public static int getOverrideOffset() {
        if (offset == -1) {
            try {
                Field f = Unsafe.class.getDeclaredField("theUnsafe"), f1 = Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);
                f1.setAccessible(false);
                Unsafe unsafe = (Unsafe) f.get(null);
                int i;//override boolean byte offset. should result in 12 for java 17
                for (i = 0; unsafe.getBoolean(f, i) == unsafe.getBoolean(f1, i); i++) ;
                offset = i;
            } catch (Exception ignored) {
                offset = 12; //fallback to 12 just in case
            }
        }
        MakeSure.isFalse(offset == -1);
        return offset;
    }

    /**
     * Creates a mock {@link MethodHandles.Lookup} for the given class.
     *
     * @param clazz the class for which to create a mock {@link MethodHandles.Lookup}
     * @return a mock {@link MethodHandles.Lookup} for the given class
     * @throws RuntimeException if an error occurs while creating the mock lookup class
     */
    public static @NotNull MethodHandles.Lookup mockLookupClass(Class<?> clazz) {
        try {
            if (handlesMockConstructor == null) {
                Constructor<?> ctx = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class);
                ReflectionUtil.setAccessible(ctx);
                handlesMockConstructor = ctx;
            }
            return ((MethodHandles.Lookup) handlesMockConstructor.newInstance(clazz));
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Attempts to access a restricted class with the given name.
     *
     * @param name the name of the class to access
     * @return the Class object for the class with the given name
     * @throws RuntimeException if the class cannot be accessed or if an error occurs while accessing it
     */
    public static Class<?> accessRestrictedClass(String name) {
        if (forName0 == null) {
            try {
                Method method = Class.class.getDeclaredMethod("forName0", String.class, boolean.class, ClassLoader.class, Class.class);
                ReflectionUtil.setAccessible(method);
                forName0 = method;
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            return (Class<?>) forName0.invoke(null, name, false, Class.class.getClassLoader(), Class.class);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
