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
import example.powercode.us.redditclonesample.base.di.BaseInjectableFragmentModule;
import example.powercode.us.redditclonesample.base.ui.BaseInjectableFragmentActivity;
import example.powercode.us.redditclonesample.base.ui.fragments.BaseInjectableFragment;
import example.powercode.us.redditclonesample.main.ui.MainActivity;
import example.powercode.us.redditclonesample.main.ui.TopicListFragment;
import example.powercode.us.redditclonesample.main.ui.TopicsAdapter;

/**
 * Binds activity context
 */
@Module(includes = {BaseInjectableFragmentModule.class})
public interface TopicListFragmentModule {
    @PerFragment
    @Binds
    TopicsAdapter.InteractionListener bindTopicsAdapterInteractionListener(@NonNull final TopicListFragment fragment);
}
