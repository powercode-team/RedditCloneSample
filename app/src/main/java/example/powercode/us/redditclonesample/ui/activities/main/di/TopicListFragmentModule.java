package example.powercode.us.redditclonesample.ui.activities.main.di;

import android.support.annotation.NonNull;

import dagger.Binds;
import dagger.Module;
import example.powercode.us.redditclonesample.app.di.scopes.PerFragment;
import example.powercode.us.redditclonesample.ui.activities.base.di.BaseInjectableFragmentModule;
import example.powercode.us.redditclonesample.ui.activities.main.TopicListFragment;
import example.powercode.us.redditclonesample.ui.activities.main.TopicsAdapter;
import example.powercode.us.redditclonesample.ui.activities.main.binding.TopicListBinding;
import example.powercode.us.redditclonesample.ui.activities.main.binding.TopicListBindingImpl;

/**
 * Binds activity context
 */
@Module(includes = {BaseInjectableFragmentModule.class})
public interface TopicListFragmentModule {

    @Binds @PerFragment
    TopicsAdapter.InteractionListener bindTopicsAdapterInteractionListener(
            @NonNull final TopicListFragment fragment);

    @Binds @PerFragment
    TopicListBinding bindBinding(TopicListBindingImpl impl);
}
