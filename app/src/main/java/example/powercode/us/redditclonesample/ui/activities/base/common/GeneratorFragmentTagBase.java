package example.powercode.us.redditclonesample.ui.activities.base.common;

import android.support.annotation.NonNull;

/**
 * Created by dev for RedditCloneSample on 14-Jun-18.
 */
public interface GeneratorFragmentTagBase<Arg> {
    @NonNull
    String generate(Arg args);
}