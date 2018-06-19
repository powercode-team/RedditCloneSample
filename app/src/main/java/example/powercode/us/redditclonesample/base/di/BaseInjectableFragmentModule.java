package example.powercode.us.redditclonesample.base.di;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import example.powercode.us.redditclonesample.app.di.scopes.PerFragment;
import example.powercode.us.redditclonesample.base.ui.fragments.BaseInjectableFragment;

/**
 * Created by dev for RedditCloneSample on 19-Jun-18.
 */
@Module
public interface BaseInjectableFragmentModule {
    @PerFragment
    @Binds
    Fragment bindFragment(@NonNull final BaseInjectableFragment fragment);
}
