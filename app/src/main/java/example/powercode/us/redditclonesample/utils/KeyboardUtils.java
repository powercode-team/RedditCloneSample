package example.powercode.us.redditclonesample.utils;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * Helper methods to work with soft keyboard
 */
public final class KeyboardUtils {
    private KeyboardUtils() {}

    public static boolean hideKeyboard(@NonNull Activity activity) {
        //Find the currently focused view, so we can grab the correct window token from it.
        View focusedView = activity.getCurrentFocus();
        if (focusedView == null) {
            focusedView = new View(activity);
        }

        return hideKeyboard(activity, focusedView);
    }

    public static boolean hideKeyboard(@NonNull Context ctx, @NonNull Fragment supportFragment) {
        View focusedView = supportFragment.getView();
        if (focusedView == null) {
            focusedView = new View(ctx);
        }
        else {
            focusedView = focusedView.getRootView();
        }

        return hideKeyboard(ctx, focusedView);
    }

    /**
     * @param ctx context to access InputMethodManager is protected against null reference context
     * @param v view which contains focus with the keyboard being shown
     * @return true if operation succeeds
     */
    public static boolean hideKeyboard(@NonNull Context ctx, @NonNull View v) {
        boolean opResult = doHideKeyboardForBinder(ctx, v.getWindowToken());
        v.clearFocus();

        return opResult;
    }

    private static boolean doHideKeyboardForBinder(@NonNull Context ctx, @NonNull IBinder binder) {
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Activity.INPUT_METHOD_SERVICE);
        return imm != null && imm.hideSoftInputFromWindow(binder, 0);
    }
}
