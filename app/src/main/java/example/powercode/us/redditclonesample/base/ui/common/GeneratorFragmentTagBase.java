package example.powercode.us.redditclonesample.base.ui.common;

import androidx.annotation.NonNull;

/**
 * Created by dev for RedditCloneSample on 14-Jun-18.
 */
public interface GeneratorFragmentTagBase<Arg> {
    @NonNull
    String generate(Arg args);
}
