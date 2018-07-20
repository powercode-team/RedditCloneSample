package example.powercode.us.redditclonesample.ui.activities.main.di;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import example.powercode.us.redditclonesample.ui.activities.base.di.BaseInjectableFragmentModule;
import example.powercode.us.redditclonesample.ui.activities.base.fragments.BaseInjectableFragment;
import example.powercode.us.redditclonesample.ui.activities.main.TopicCreateFragment;
import example.powercode.us.redditclonesample.ui.activities.main.binding.TopicCreateBinding;
import example.powercode.us.redditclonesample.ui.activities.main.binding.TopicCreateBindingImpl;
import example.powercode.us.redditclonesample.ui.activities.main.vm.TopicCreateViewModel;

/**
 * @author meugen
 */
@Module(includes = BaseInjectableFragmentModule.class)
public interface TopicCreateFragmentModule {

    @Binds
    BaseInjectableFragment bindFragment(TopicCreateFragment fragment);

    @Binds
    TopicCreateBinding bindBinding(TopicCreateBindingImpl impl);

    @Provides
    static TopicCreateViewModel provideTopicCreateViewModel(
            final Fragment fragment, final ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(TopicCreateViewModel.class);
    }
}
