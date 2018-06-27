package example.powercode.us.redditclonesample.common;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import example.powercode.us.redditclonesample.common.functional.Consumer;
import example.powercode.us.redditclonesample.common.functional.Predicate;

/**
 * Collection of useful algorithms
 */
@SuppressWarnings(value = {"unused", "WeakerAccess"})
public final class Algorithms {
    private Algorithms() {
    }

    public static <T> void processElementIf(@NonNull Iterable<? extends T> sequence,
                                            @NonNull Predicate<? super T> condition,
                                            @NonNull Consumer<? super T> onTrue) {
        for (T el : sequence) {
            if (condition.test(el)) {
                onTrue.accept(el);
            }
        }
    }

    public static <T> void processElementIf(@NonNull T[] sequence,
                                            @NonNull Predicate<? super T> condition,
                                            @NonNull Consumer<? super T> onTrue) {
        processElementIf(Arrays.asList(sequence), condition, onTrue);
    }

    public static <T> void processElementIf(@NonNull Iterable<? extends T> sequence,
                                            @NonNull Predicate<? super T> condition,
                                            @NonNull Consumer<? super T> onTrue, @NonNull Consumer<? super T> onFalse) {
        for (T el : sequence) {
            if (condition.test(el)) {
                onTrue.accept(el);
            } else {
                onFalse.accept(el);
            }
        }
    }

    public static <T> void processElementIf(@NonNull T[] sequence,
                                            @NonNull Predicate<? super T> condition,
                                            @NonNull Consumer<? super T> onTrue, @NonNull Consumer<? super T> onFalse) {
        processElementIf(Arrays.asList(sequence), condition, onTrue, onFalse);
    }

    @Nullable
    public static <T> T findElement(@NonNull Iterable<? extends T> sequence, @NonNull Predicate<? super T> filter) {
        for (T el : sequence) {
            if (filter.test(el)) {
                return el;
            }
        }

        return null;
    }

    public static <T> int findIndex(@NonNull T[] array, @NonNull Predicate<? super T> filter) {
        return findIndex(Arrays.asList(array), filter);
    }

    public static <T> int findIndex(@NonNull List<T> array, @NonNull Predicate<? super T> filter) {
        for (int i = 0; i < array.size(); i++) {
            if (filter.test(array.get(i))) {
                return i;
            }
        }

        return -1;
    }

    @SuppressWarnings("UnusedReturnValue")
    public static <T> boolean removeElement(@NonNull Iterable<? extends T> sequence, @NonNull Predicate<? super T> filter) {
        boolean removed = false;
        final Iterator<? extends T> each = sequence.iterator();
        while (each.hasNext()) {
            if (filter.test(each.next())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }

    @SuppressWarnings("UnusedReturnValue")
    public static <T> boolean removeElementOnce(@NonNull Iterable<? extends T> sequence, @NonNull Predicate<? super T> filter) {
        final Iterator<? extends T> each = sequence.iterator();
        while (each.hasNext()) {
            if (filter.test(each.next())) {
                each.remove();
                return true;
            }
        }
        return false;
    }
}
