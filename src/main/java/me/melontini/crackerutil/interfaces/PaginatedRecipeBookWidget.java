package me.melontini.crackerutil.interfaces;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public interface PaginatedRecipeBookWidget {
    default void updatePages() {
        throw new IllegalStateException();
    }

    default void updatePageSwitchButtons() {
        throw new IllegalStateException();
    }

    default int getPage() {
        throw new IllegalStateException();
    }

    default void setPage(int page) {
        throw new IllegalStateException();
    }

    default int getPageCount() {
        throw new IllegalStateException();
    }
}
