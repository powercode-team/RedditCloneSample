package example.powercode.us.redditclonesample.ui.activities.base.di;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import example.powercode.us.redditclonesample.ui.activities.base.fragments.BaseInjectableFragment;

/**
 * Created by dev for RedditCloneSample on 19-Jun-18.
 */
@Module
public interface BaseInjectableFragmentModule {

    @Binds
    Fragment bindFragment(@NonNull final BaseInjectableFragment fragment);
}
