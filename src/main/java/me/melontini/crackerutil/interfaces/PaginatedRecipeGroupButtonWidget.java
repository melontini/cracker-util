package me.melontini.crackerutil.interfaces;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public interface PaginatedRecipeGroupButtonWidget {
    default int getPage() {
        throw new IllegalStateException();
    }

    default void setPage(int page) {
        throw new IllegalStateException();
    }
}
