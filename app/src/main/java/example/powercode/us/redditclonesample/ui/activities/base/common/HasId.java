package example.powercode.us.redditclonesample.ui.activities.base.common;

import android.support.annotation.NonNull;

/**
 * Created by dev for RedditCloneSample on 19-Jun-18.
 */
public interface HasId<IdType> {
    @NonNull IdType getId();
}
