package me.melontini.crackerutil.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * A utility class for ensuring that certain conditions are true, throwing an exception if not.
 */
public class MakeSure {
    /**
     * Ensures that the given object is not null, throwing a {@link NullPointerException} if it is.
     *
     * @param thing the object to check for null
     * @param <T>   the type of the object
     * @return the given object if it is not null
     * @throws NullPointerException if the given object is null
     */
    public static <T> T notNull(@Nullable T thing) {
        if (thing == null) throw new NullPointerException();
        return thing;
    }

    /**
     * Ensures that the given object is not null, throwing a {@link NullPointerException} with the given
     * message if it is.
     *
     * @param thing the object to check for null
     * @param msg   the message to use in the {@link NullPointerException} if the object is null
     * @param <T>   the type of the object
     * @return the given object if it is not null
     * @throws NullPointerException if the given object is null
     */
    public static <T> T notNull(@Nullable T thing, String msg) {
        if (thing == null) throw new NullPointerException(msg);
        return thing;
    }

    /**
     * Ensures that all the given objects are not null, throwing a {@link NullPointerException} if any
     * of them are.
     *
     * @param things the objects to check for null
     * @throws NullPointerException if any of the given objects are null
     */
    @Contract(pure = true)
    public static void notNulls(@Nullable Object... things) {
        for (Object thing : things) {
            if (thing == null) throw new NullPointerException();
        }
    }

    /**
     * Ensures that all the given objects are not null, throwing a {@link NullPointerException} with
     * the given message if any of them are.
     *
     * @param msg    the message to use in the {@link NullPointerException} if any of the objects are null
     * @param things the objects to check for null
     * @throws NullPointerException if any of the given objects are null
     */
    @Contract(pure = true)
    public static void notNulls(String msg, @Nullable Object... things) {
        for (Object thing : things) {
            if (thing == null) throw new NullPointerException(msg);
        }
    }

    /**
     * Ensures that the given boolean is true, throwing a {@link RuntimeException} if it is not.
     *
     * @param bool the boolean to check
     * @throws RuntimeException if the given boolean is not true
     */
    public static void isTrue(boolean bool) {
        if (!bool) throw new RuntimeException();
    }

    /**
     * Ensures that the given boolean is true, throwing a {@link RuntimeException} if it is not.
     *
     * @param bool the boolean to check
     * @param msg  msg the message to use in the {@link RuntimeException} if the boolean is not true
     * @throws RuntimeException if the given boolean is not true
     */
    public static void isTrue(boolean bool, String msg) {
        if (!bool) throw new RuntimeException(msg);
    }

    /**
     * Ensures that the given boolean is false, throwing a {@link RuntimeException} if it is not.
     *
     * @param bool the boolean to check
     * @throws RuntimeException if the given boolean is not false
     */
    public static void isFalse(boolean bool) {
        if (bool) throw new RuntimeException();
    }

    /**
     * Ensures that the given boolean is false, throwing a {@link RuntimeException} with the given message
     * if it is not.
     *
     * @param bool the boolean to check
     * @param msg  the message to use in the {@link RuntimeException} if the boolean is not false
     * @throws RuntimeException if the given boolean is not false
     */
    public static void isFalse(boolean bool, String msg) {
        if (bool) throw new RuntimeException(msg);
    }

    /**
     * Ensures that the given array is not null or empty, throwing an {@link IllegalArgumentException} if it is.
     *
     * @param array the array to check for null or emptiness
     * @param <T>   the type of the elements in the array
     * @return the given array if it is not null or empty
     * @throws IllegalArgumentException if the given array is null or empty
     */
    @Contract(value = "null -> fail", pure = true)
    public static <T> T @NotNull [] notEmpty(@Nullable T[] array) {
        if (array == null || array.length == 0) throw new IllegalArgumentException();
        return array;
    }

    /**
     * Ensures that the given array is not null or empty, throwing an {@link IllegalArgumentException} with
     * the given message if it is.
     *
     * @param array the array to check for null or emptiness
     * @param msg   the message to use in the {@link IllegalArgumentException} if the array is null or empty
     * @param <T>   the type of the elements in the array
     * @return the given array if it is not null or empty
     * @throws IllegalArgumentException if the given array is null or empty
     */
    @Contract(value = "null, _ -> fail", pure = true)
    public static <T> T @NotNull [] notEmpty(@Nullable T[] array, String msg) {
        if (array == null || array.length == 0) throw new IllegalArgumentException(msg);
        return array;
    }

    /**
     * Ensures that the given collection is not null or empty, throwing an {@link IllegalArgumentException} if it is.
     *
     * @param collection the collection to check for null or emptiness
     * @param <T>        the type of the elements in the collection
     * @return the given collection if it is not null or empty
     * @throws IllegalArgumentException if the given collection is null or empty
     */
    @Contract("null -> fail")
    public static <T extends Collection<?>> @NotNull T notEmpty(@Nullable T collection) {
        if (collection == null || collection.isEmpty()) throw new IllegalArgumentException();
        return collection;
    }

    /**
     * Ensures that the given collection is not nullor empty, throwing an {@link IllegalArgumentException} with the given message if it is.
     *
     * @param collection the collection to check for null or emptiness
     * @param msg        the message to use in the {@link IllegalArgumentException} if the collection is null or empty
     * @param <T>        the type of the elements in the collection
     * @return the given collection if it is not null or empty
     * @throws IllegalArgumentException if the given collection is null or empty
     */
    @Contract("null, _ -> fail")
    public static <T extends Collection<?>> @NotNull T notEmpty(@Nullable T collection, String msg) {
        if (collection == null || collection.isEmpty()) throw new IllegalArgumentException(msg);
        return collection;
    }

    /**
     * Ensures that the given string is not null or empty, throwing an {@link IllegalArgumentException} if it is.
     *
     * @param string the string to check for null or emptiness
     * @return the given string if it is not null or empty
     * @throws IllegalArgumentException if the given string is null or empty
     */
    @Contract("null -> fail")
    public static @NotNull String notEmpty(@Nullable String string) {
        if (string == null || string.isEmpty()) throw new IllegalArgumentException();
        return string;
    }


    /**
     * Ensures that the given string is not null or empty, throwing an {@link IllegalArgumentException} with
     * the given message if it is.
     *
     * @param string the string to check for null or emptiness
     * @param msg the message to use in the {@link IllegalArgumentException} if the string is null or empty
     * @return the given string if it is not null or empty
     * @throws IllegalArgumentException if the given string is null or empty
     */
    @Contract("null, _ -> fail")
    public static @NotNull String notEmpty(@Nullable String string, String msg) {
        if (string == null || string.isEmpty()) throw new IllegalArgumentException(msg);
        return string;
    }
}
