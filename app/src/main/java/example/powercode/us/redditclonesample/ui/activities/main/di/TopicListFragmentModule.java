package example.powercode.us.redditclonesample.ui.activities.main.di;

import android.support.annotation.NonNull;

import dagger.Binds;
import dagger.Module;
import example.powercode.us.redditclonesample.app.di.scopes.PerFragment;
import example.powercode.us.redditclonesample.ui.activities.base.di.BaseInjectableFragmentModule;
import example.powercode.us.redditclonesample.ui.activities.main.TopicListFragment;
import example.powercode.us.redditclonesample.ui.activities.main.TopicsAdapter;

/**
 * Binds activity context
 */
@Module(includes = {BaseInjectableFragmentModule.class})
public interface TopicListFragmentModule {
    @PerFragment
    @Binds
    TopicsAdapter.InteractionListener bindTopicsAdapterInteractionListener(@NonNull final TopicListFragment fragment);
}
