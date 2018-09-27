package example.powercode.us.redditclonesample.base.ui.common;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * Created by dev for RedditCloneSample on 19-Jun-18.
 */
@FunctionalInterface
public interface RVOnItemClickListener {
    void onClick(@NonNull View v, int adapterPosition);
}
