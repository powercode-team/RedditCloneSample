package example.powercode.us.redditclonesample.app.di.modules;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import example.powercode.us.redditclonesample.app.di.mapkey.ViewModelKey;
import example.powercode.us.redditclonesample.base.ui.MainViewModelFactory;
import example.powercode.us.redditclonesample.main.vm.MainViewModel;
import example.powercode.us.redditclonesample.main.vm.TopicCreateViewModel;
import example.powercode.us.redditclonesample.main.vm.TopicsViewModel;

/**
 * Module to provide view models
 */
@Module
public interface ViewModelModule {
    @Binds
    ViewModelProvider.Factory bindViewModelFactory(@NonNull MainViewModelFactory factory);

    @Binds @IntoMap @ViewModelKey(MainViewModel.class)
    ViewModel bindMainViewModel(@NonNull MainViewModel viewModel);

    @Binds @IntoMap @ViewModelKey(TopicsViewModel.class)
    ViewModel bindTopicsViewModel(@NonNull TopicsViewModel viewModel);

    @Binds @IntoMap @ViewModelKey(TopicCreateViewModel.class)
    ViewModel bindTopicCreateViewModel(@NonNull TopicCreateViewModel viewModel);
}
