package example.powercode.us.redditclonesample.ui.activities.base.common;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by dev for RedditCloneSample on 19-Jun-18.
 */
@FunctionalInterface
public interface RVOnItemClickListener {
    void onClick(@NonNull View v, int adapterPosition);
}
