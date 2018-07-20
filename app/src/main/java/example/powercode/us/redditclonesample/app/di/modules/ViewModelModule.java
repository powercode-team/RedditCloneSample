package example.powercode.us.redditclonesample.app.di.modules;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import example.powercode.us.redditclonesample.app.di.mapkey.ViewModelKey;
import example.powercode.us.redditclonesample.app.di.scopes.PerApplication;
import example.powercode.us.redditclonesample.ui.activities.base.MainViewModelFactory;
import example.powercode.us.redditclonesample.ui.activities.main.vm.MainViewModel;
import example.powercode.us.redditclonesample.ui.activities.main.vm.TopicCreateViewModel;
import example.powercode.us.redditclonesample.ui.activities.main.vm.TopicsViewModel;

/**
 * Module to provide view models
 */
@Module
public interface ViewModelModule {

    @Binds @PerApplication
    ViewModelProvider.Factory bindViewModelFactory(@NonNull MainViewModelFactory factory);

    @Binds @IntoMap @ViewModelKey(MainViewModel.class)
    ViewModel bindMainViewModel(@NonNull MainViewModel viewModel);

    @Binds @IntoMap @ViewModelKey(TopicsViewModel.class)
    ViewModel bindTopicsViewModel(@NonNull TopicsViewModel viewModel);

    @Binds @IntoMap @ViewModelKey(TopicCreateViewModel.class)
    ViewModel bindTopicCreateViewModel(@NonNull TopicCreateViewModel viewModel);
}
