package example.powercode.us.redditclonesample.main.di;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import example.powercode.us.redditclonesample.app.di.qualifiers.ParentFragmentManager;
import example.powercode.us.redditclonesample.app.di.scopes.PerActivity;
import example.powercode.us.redditclonesample.base.di.BaseInjectableActivityModule;
import example.powercode.us.redditclonesample.base.di.BaseInjectableFragmentActivityModule;
import example.powercode.us.redditclonesample.base.ui.BaseInjectableActivity;
import example.powercode.us.redditclonesample.base.ui.BaseInjectableFragmentActivity;
import example.powercode.us.redditclonesample.main.MainActivity;

/**
 * Binds activity context
 */
@Module (includes = {BaseInjectableFragmentActivityModule.class})
public interface MainActivityModule {
    @Binds
    @PerActivity
    BaseInjectableFragmentActivity bindActivity(final MainActivity activity);
}
