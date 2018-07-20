package example.powercode.us.redditclonesample.ui.activities.main.di;

import android.support.annotation.NonNull;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import example.powercode.us.redditclonesample.ui.activities.base.BaseInjectableFragmentActivity;
import example.powercode.us.redditclonesample.ui.activities.base.di.BaseInjectableFragmentActivityModule;
import example.powercode.us.redditclonesample.ui.activities.main.MainActivity;
import example.powercode.us.redditclonesample.ui.activities.main.TopicCreateFragment;
import example.powercode.us.redditclonesample.ui.activities.main.TopicListFragment;

/**
 * Binds activity context
 */
@Module(includes = {BaseInjectableFragmentActivityModule.class})
public interface MainActivityModule {

    @Binds
    BaseInjectableFragmentActivity bindActivity(@NonNull final MainActivity activity);

    @Binds
    TopicListFragment.OnInteractionListener bindTopicListFragmentOnInteractionListener(@NonNull final MainActivity activity);

    @Binds
    TopicCreateFragment.OnInteractionListener bindTopicCreateFragmentOnInteractionListener(@NonNull final MainActivity activity);

    @ContributesAndroidInjector(modules = {TopicListFragmentModule.class})
    TopicListFragment contributeTopicListFragmentInjector();

    @ContributesAndroidInjector(modules = {TopicCreateFragmentModule.class})
    TopicCreateFragment contributeTopicCreateFragmentInjector();
}
