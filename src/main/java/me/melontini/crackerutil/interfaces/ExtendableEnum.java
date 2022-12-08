package me.melontini.crackerutil.interfaces;

public interface ExtendableEnum<T> {
    /**
     * Extends an enum with a new element.
     *
     * <p>
     * This method allows adding new elements to an enum at runtime. It should only be used if absolutely necessary, as extending enums can cause unpredictable behavior and can break code that relies on a fixed set of enum values.
     * In particular, switch statements and maps or lists that use enums as keys or values may fail when new elements are added.
     * </p>
     *
     * <p>All inner enums need to be cast to this interface, since interface injection doesn't seem to apply to them.</p>
     *
     * @param internalName The internal name of the new enum element. This name is used by the {@link java.lang.Enum#valueOf(Class, String)} method to map from a string representation of the enum to its corresponding enum constant.
     * Note that some enums may provide their own names (e.g {@link net.minecraft.util.Formatting}), which are different from the internal names.
     * @param params the parameters to pass to the constructor of the new enum element
     * @return the newly created enum element
     * @throws IllegalStateException if the `extend` method is not implemented by the enum class
     */
    default T extend(String internalName, Object... params) {
        throw new IllegalStateException("extend method not implemented");
    }
}
