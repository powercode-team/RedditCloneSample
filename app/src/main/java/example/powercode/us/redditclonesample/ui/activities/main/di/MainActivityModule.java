package example.powercode.us.redditclonesample.ui.activities.main.di;

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
import example.powercode.us.redditclonesample.ui.activities.base.BaseInjectableFragmentActivity;
import example.powercode.us.redditclonesample.ui.activities.base.di.BaseInjectableFragmentActivityModule;
import example.powercode.us.redditclonesample.ui.activities.base.di.BaseInjectableFragmentModule;
import example.powercode.us.redditclonesample.ui.activities.main.MainActivity;
import example.powercode.us.redditclonesample.ui.activities.main.TopicCreateFragment;
import example.powercode.us.redditclonesample.ui.activities.main.TopicListFragment;

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

    @Binds
    @PerActivity
    TopicCreateFragment.OnInteractionListener bindTopicCreateFragmentOnInteractionListener(@NonNull final MainActivity activity);

    @ContributesAndroidInjector(modules = {TopicListFragmentModule.class})
    @PerFragment
    TopicListFragment contributeTopicListFragmentInjector();

    @ContributesAndroidInjector(modules = {BaseInjectableFragmentModule.class})
    @PerFragment
    TopicCreateFragment contributeTopicCreateFragmentInjector();
}
