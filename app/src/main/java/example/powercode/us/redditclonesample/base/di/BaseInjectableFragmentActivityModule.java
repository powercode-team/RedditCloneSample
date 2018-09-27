package example.powercode.us.redditclonesample.base.di;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import example.powercode.us.redditclonesample.app.di.qualifiers.ParentFragmentManager;
import example.powercode.us.redditclonesample.app.di.scopes.PerActivity;
import example.powercode.us.redditclonesample.base.ui.BaseInjectableActivity;
import example.powercode.us.redditclonesample.base.ui.BaseInjectableFragmentActivity;

/**
 * Binds activity context
 */
@Module (includes = {BaseInjectableActivityModule.class})
public interface BaseInjectableFragmentActivityModule {
    @Binds
    @PerActivity
    BaseInjectableActivity bindActivity(final BaseInjectableFragmentActivity activity);


    @Provides
    @PerActivity
    @ParentFragmentManager
    static FragmentManager provideFragmentManager(final @NonNull AppCompatActivity activity) {
        return activity.getSupportFragmentManager();
    }
}
