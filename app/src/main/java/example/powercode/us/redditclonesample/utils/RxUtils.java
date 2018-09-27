package example.powercode.us.redditclonesample.utils;

import androidx.annotation.Nullable;
import io.reactivex.disposables.Disposable;

/**
 * Created by dev for RedditCloneSample on 05-Jul-18.
 */
public class RxUtils {
    private RxUtils() {}

    /**
     * @param d {@link Nullable} {@link Disposable} to be disposed, but checks against nullability
     * @return true if disposable was actually disposed
     */
    @SuppressWarnings(value = {"UnusedReturnValue"})
    public static boolean clearDisposableSafe(@Nullable Disposable d) {
        if (d != null && !d.isDisposed()) {
            d.dispose();

            return true;
        }

        return false;
    }
}
