package example.powercode.us.redditclonesample.main.di;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import example.powercode.us.redditclonesample.R;
import example.powercode.us.redditclonesample.app.di.qualifiers.FragmentContainerRes;
import example.powercode.us.redditclonesample.app.di.scopes.PerActivity;
import example.powercode.us.redditclonesample.app.di.scopes.PerFragment;
import example.powercode.us.redditclonesample.base.di.BaseInjectableFragmentActivityModule;
import example.powercode.us.redditclonesample.base.ui.BaseInjectableFragmentActivity;
import example.powercode.us.redditclonesample.main.ui.MainActivity;
import example.powercode.us.redditclonesample.main.ui.TopicListFragment;

/**
 * Binds activity context
 */
@Module(includes = {BaseInjectableFragmentActivityModule.class})
public interface MainActivityModule {
    @Binds
    @PerActivity
    BaseInjectableFragmentActivity bindActivity(@NonNull final MainActivity activity);

    @Provides
    @PerActivity
    @FragmentContainerRes
    static @IdRes int provideFragmentContainer() {
        return R.id.fragment_container;
    }

    @Binds
    @PerActivity
    TopicListFragment.OnInteractionListener bindTopicListFragmentOnInteractionListener(@NonNull final MainActivity activity);

    @ContributesAndroidInjector()
    @PerFragment
    TopicListFragment contributeFragmentInjector();
}
