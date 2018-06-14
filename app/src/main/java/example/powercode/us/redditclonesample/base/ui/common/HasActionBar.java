package example.powercode.us.redditclonesample.base.ui.common;

import android.support.annotation.Nullable;

/**
 * Created by dev for RedditCloneSample on 14-Jun-18.
 */
public interface HasActionBar<AppBar, ToolBar> {
    @Nullable
    AppBar getAppBar();

    void setAppBar(@Nullable ToolBar bar);
}
