package example.powercode.us.redditclonesample.common;

import androidx.annotation.NonNull;
import example.powercode.us.redditclonesample.common.functional.Predicate;

/**
 * Created by dev for RedditCloneSample on 26-Jun-18.
 */
public abstract class ParamPredicate<T> implements Predicate<T> {
    @NonNull
    public final T param;

    protected ParamPredicate(@NonNull T p) {
        this.param = p;
    }
}
