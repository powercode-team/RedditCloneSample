package example.powercode.us.redditclonesample.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by dev for RedditCloneSample on 03-Jul-18.
 */
public final class ViewUtils extends ViewCompat {
    private ViewUtils() {}

    // There are convenient methods for this in TypedValue#complexToDimensionPixelSize()
//    /**
//     * @param dips value in {@see https://developer.android.com/guide/practices/screens_support}
//     * @param densityScaleFactor is a ratio of screen density to default 160dp (mdpi)
//     * {@see https://developer.android.com/training/multiscreen/screendensities}
//     * @return value in pixels
//     */
//    public static int convertDpToPx(int dips, float densityScaleFactor) {
//        return (int)(dips * densityScaleFactor + 0.5f);
//    }

    @SuppressLint("ObsoleteSdkInt")
    public static void removeOnGlobalLayoutListener(@NonNull View v, @NonNull ViewTreeObserver.OnGlobalLayoutListener victim) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(victim);
        } else {
            //noinspection deprecation
            v.getViewTreeObserver().removeGlobalOnLayoutListener(victim);
        }
    }

    @Nullable
    public static View findViewById(@NonNull View parent, @IdRes int viewId2Find) {
        while (parent != null) {
            View targetView = parent.findViewById(viewId2Find);
            if (targetView != null) {
                return targetView;
            }

            parent = (parent.getParent() instanceof View) ? (View)parent.getParent() : null;
        }

        return null;
    }
}
