package example.powercode.us.redditclonesample.main.di;

import androidx.annotation.NonNull;
import dagger.Binds;
import dagger.Module;
import example.powercode.us.redditclonesample.app.di.scopes.PerFragment;
import example.powercode.us.redditclonesample.base.di.BaseInjectableFragmentModule;
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
