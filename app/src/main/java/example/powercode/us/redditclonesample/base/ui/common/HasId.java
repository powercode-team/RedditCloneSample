package example.powercode.us.redditclonesample.base.ui.common;

import androidx.annotation.NonNull;

/**
 * Created by dev for RedditCloneSample on 19-Jun-18.
 */
public interface HasId<IdType> {
    @NonNull
    IdType getId();
}
