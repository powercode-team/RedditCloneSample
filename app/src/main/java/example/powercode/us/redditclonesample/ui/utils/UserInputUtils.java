package example.powercode.us.redditclonesample.ui.utils;

import android.support.annotation.NonNull;

/**
 * Created by dev for RedditCloneSample on 26-Jun-18.
 */
public final class UserInputUtils {
    private UserInputUtils() {}

    @NonNull
    public static String removeDuplicateSpaces(@NonNull String input) {
        return input.replaceAll("\\s+", " ");
    }
}
