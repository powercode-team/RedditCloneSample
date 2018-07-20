package example.powercode.us.redditclonesample.ui.activities.base.di;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import example.powercode.us.redditclonesample.app.di.qualifiers.ParentFragmentManager;
import example.powercode.us.redditclonesample.ui.activities.base.BaseInjectableActivity;
import example.powercode.us.redditclonesample.ui.activities.base.BaseInjectableFragmentActivity;

/**
 * Binds activity context
 */
@Module (includes = {BaseInjectableActivityModule.class})
public interface BaseInjectableFragmentActivityModule {

    @Binds
    BaseInjectableActivity bindActivity(final BaseInjectableFragmentActivity activity);


    @Provides
    @ParentFragmentManager
    static FragmentManager provideFragmentManager(final @NonNull AppCompatActivity activity) {
        return activity.getSupportFragmentManager();
    }
}
