package example.powercode.us.redditclonesample.ui.activities.main.di;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import example.powercode.us.redditclonesample.ui.activities.base.di.BaseInjectableFragmentModule;
import example.powercode.us.redditclonesample.ui.activities.base.fragments.BaseInjectableFragment;
import example.powercode.us.redditclonesample.ui.activities.main.TopicListFragment;
import example.powercode.us.redditclonesample.ui.activities.main.TopicsAdapter;
import example.powercode.us.redditclonesample.ui.activities.main.binding.TopicListBinding;
import example.powercode.us.redditclonesample.ui.activities.main.binding.TopicListBindingImpl;
import example.powercode.us.redditclonesample.ui.activities.main.vm.TopicsViewModel;

/**
 * Binds activity context
 */
@Module(includes = {BaseInjectableFragmentModule.class})
public interface TopicListFragmentModule {

    @Binds
    BaseInjectableFragment bindFragment(TopicListFragment fragment);

    @Binds
    TopicsAdapter.InteractionListener bindTopicsAdapterInteractionListener(
            @NonNull final TopicListFragment fragment);

    @Binds
    TopicListBinding bindBinding(TopicListBindingImpl impl);

    static TopicsViewModel provideTopicsViewModel(
            final Fragment fragment, final ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(TopicsViewModel.class);
    }
}
