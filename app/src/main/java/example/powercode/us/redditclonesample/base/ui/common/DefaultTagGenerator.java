package example.powercode.us.redditclonesample.base.ui.common;

import androidx.annotation.NonNull;

/**
 * Created by dev for RedditCloneSample on 14-Jun-18.
 */
public enum DefaultTagGenerator {
    INSTANCE;

    @NonNull
    static final String PREFIX = "FRAGMENT_TAG_";

    @NonNull
    private final GeneratorFragmentTagBase<Class<?>> generator = args -> PREFIX + args.getSimpleName();

    @NonNull
    public static <T> String generate(@NonNull Class<T> args) {
        return INSTANCE.generator.generate(args);
    }
}
